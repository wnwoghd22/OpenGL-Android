package com.eternal.rolly_roll.game.control;

import android.util.Log;

import com.eternal.rolly_roll.game.model.PlayerObject;
import com.eternal.rolly_roll.game.model.Tile;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.ui.text.TextContainer;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.util.logging.Logger;

public class Level extends GameObject {
    private final String TAG = "Level";

    private int boardSize;
    public int getBoardSize() { return boardSize; }

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

    @Override
    public void Update() {

    }

    public void stamp(int i, int j, Axis down) {
        if(board[j][i].isColored())
            return;

        board[j][i].setColor(down);

        int adjacent = board[j][i].checkAdjacent();
        if (LoggerConfig.LEVEL_LOG) {
            Log.w(TAG, "adjacent : " + adjacent);
        }

        int require;
        if (adjacent >= 3) {
            require = board[j][i].clear();
            getScore(adjacent * adjacent * 10);
        } else {
            board[j][i].uncheck();
        }
        // if game mode is "challenge", then require > adjacent -> game over
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
}
