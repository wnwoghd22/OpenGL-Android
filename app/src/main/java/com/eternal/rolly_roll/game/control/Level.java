package com.eternal.rolly_roll.game.control;

import android.util.Log;

import com.eternal.rolly_roll.game.model.Tile;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.ui.panel.PanelContainer;
import com.eternal.rolly_roll.game.view.ui.text.TextContainer;
import com.eternal.rolly_roll.util.LoggerConfig;

public class Level extends GameObject {
    private final String TAG = "Level";

    private int boardSize;
    public int getBoardSize() { return boardSize; }
    private int coloredTile = 0;
    private int moveLeft = 3; // if move on colored tile, then minus one
    private TextContainer moveLeftText;
    public void setMoveLeftText(TextContainer moveLeft) {
        this.moveLeftText = moveLeft;
    }

    private int currentScore = 0;
    private int highScore = 0;
    private TextContainer scoreText;
    private TextContainer highScoreText;
    public void setScoreText(TextContainer score) {
        this.scoreText = score;
    }
    public void setHighScoreText(TextContainer highScore) {
        this.highScoreText = highScore;
    }
    private  TextContainer gameOverText;
    private PanelContainer gameOverPanel;
    public void setGameOverText(TextContainer gameOverText) {
        this.gameOverText = gameOverText;
    }
    public void setGameOverPanel(PanelContainer gameOverPanel) {
        this.gameOverPanel = gameOverPanel;
    }
    private boolean gameOver = false;
    public boolean isGameOver() { return gameOver; }

    private final int bombRange = 2;

    private Tile[][] board;

    public Tile[][] GenerateBoard(int size) {
        boardSize = size;

        board = new Tile[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                board[i][j] = new Tile(new Vector3D(
                        -(float)(size - 1) / 2.0f + j,
                        0f,
                        -(float)(size - 1) / 2.0f + i
                ));

        // bind tiles
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(i > 0) // if up exists
                    board[i][j].bindUp(board[i - 1][j]);
                if(i < size - 1) // if down exists
                    board[i][j].bindDown(board[i + 1][j]);
                if(j > 0) // if left exists
                    board[i][j].bindLeft(board[i][j - 1]);
                if(j < size - 1) // if right exists
                    board[i][j].bindRight(board[i][j + 1]);
            }
        }

        return board;
    }

    public void initLevel() {
        coloredTile = 0;
        moveLeft = 3;
        moveLeftText.setText("MOVE LEFT : " + moveLeft);
        currentScore = 0;
        scoreText.setText(currentScore);

        gameOver = false;
        if (gameOverText != null)
            gameOverText.setActive(false);
        if (gameOverPanel != null)
            gameOverPanel.setActive(false);
    }

    @Override
    public void Update() {

    }

    public void stamp(int i, int j, Axis down) {
        if(board[j][i].isColored()) {
            --moveLeft;
        } else {
            if (moveLeft < 3)
                ++moveLeft;

            board[j][i].setColor(down);
            ++coloredTile;

            int adjacent = board[j][i].checkAdjacent();
            if (LoggerConfig.LEVEL_LOG) {
                Log.w(TAG, "adjacent : " + adjacent);
            }

            int require;
            if (adjacent >= 3) {
                require = board[j][i].clear();
                getScore(adjacent * adjacent * 10);
                coloredTile -= adjacent;
            } else {
                board[j][i].uncheck();
            }
        }
        moveLeftText.setText("MOVE LEFT : " + moveLeft);
        // if game mode is "challenge", then require > adjacent -> game over

        gameOver = checkIsGameOver();

        if (gameOver) {
            gameOverPanel.setActive(true);
            gameOverText.setActive(true);
        }
    }

    public void bomb(int i, int j) {
        int removed = board[j][i].bomb(bombRange);
        coloredTile -= removed;
        getScore(removed * 10);
    }

    private void getScore(int score) {
        if (LoggerConfig.LEVEL_LOG) {
            Log.w(TAG, "current score : " + score);
        }

        currentScore += score;
        scoreText.setText(currentScore);
        if (highScore < currentScore) {
            highScore = currentScore;
            highScoreText.setText(currentScore);
        }
    }

    private boolean checkIsGameOver() {
        if (coloredTile == boardSize * boardSize) { // all tiles are colored
            if (LoggerConfig.LEVEL_LOG) {
                Log.w(TAG, "Game Over : all tile colored");
            }
            return true;
        } else if (moveLeft == 0) {
            if (LoggerConfig.LEVEL_LOG) {
                Log.w(TAG, "Game Over : no moving chance");
            }
            return true;
        }
        return false;
    }

    public void restart() {
        for (int i  = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                board[j][i].resetTile();
            }
        }
        initLevel();
    }
}
