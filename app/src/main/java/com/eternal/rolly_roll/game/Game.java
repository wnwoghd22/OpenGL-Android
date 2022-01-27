package com.eternal.rolly_roll.game;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.control.Level;
import com.eternal.rolly_roll.game.control.TouchHandler;
import com.eternal.rolly_roll.game.model.PlayerObject;
import com.eternal.rolly_roll.game.model.Tile;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.ui.Button;
import com.eternal.rolly_roll.game.view.ui.panel.PanelContainer;
import com.eternal.rolly_roll.game.view.ui.text.TextContainer;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private Timer timer;
    private long updatePeriod;
    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            Update();
        }
    }

    private static final String TAG = "Game";

    private final Context context;

    // is it better using Vector in multi-thread environment?
    //private Vector<GameObject> objectsSync;
    private List<GameObject> objects;
    public List<GameObject> getObjects() { return objects; }
    private List<GameObject> uiObjects;
    public List<GameObject> getUiObjects() { return uiObjects; }
    private List<Button> buttons;
    public List<Button> getButtons() { return buttons; }
    private PlayerObject player;

    private Level level;
    private int boardSize;

    public Game(Context context) {
        this.context = context;

        level = new Level();
        objects = new ArrayList<GameObject>();
        uiObjects = new ArrayList<GameObject>();
        buttons = new ArrayList<Button>();
    }

    public void Init() {
        boardSize = 7;

        objects.add(level);
        for (Tile[] tiles : level.GenerateBoard(boardSize)) {
            for (Tile tile : tiles) {
                objects.add(tile);
            }
        }

        player = new PlayerObject(
                new Vector3D(
            -(float)(boardSize - 1) / 2.0f,
            0.5f,
            -(float)(boardSize - 1) / 2.0f
            )
        );

        player.setLevel(level);

        objects.add(player);

        Start();
    }

    public void setUIComponents() {
        // add ui components
        TextContainer moveLeft = new TextContainer("MOVE LEFT : 3");
        moveLeft.setPosition(new Vector3D(-0.5f, 0.6f, 0f));
        level.setMoveLeftText(moveLeft);
        uiObjects.add(moveLeft);

        TextContainer SCORE = new TextContainer("SCORE");
        TextContainer HIGH_SCORE = new TextContainer("HIGH SCORE");

        TextContainer currentScore = new TextContainer("0");
        TextContainer highScore = new TextContainer("0");

        SCORE.setPosition(new Vector3D(-0.5f, 0.8f, 0f));
        HIGH_SCORE.setPosition(new Vector3D(0.5f, 0.8f, 0f));

        currentScore.setPosition(new Vector3D(-0.5f, 0.7f, 0f));
        highScore.setPosition(new Vector3D(0.5f, 0.7f, 0f));

        level.setScoreText(currentScore);
        level.setHighScoreText(highScore);

        uiObjects.add(SCORE);
        uiObjects.add(HIGH_SCORE);
        uiObjects.add(currentScore);
        uiObjects.add(highScore);

        PanelContainer gameOverPanel = new PanelContainer();
        gameOverPanel.setScale(new Vector3D(2f, 2f, 1f));
        gameOverPanel.setColor(1f, 1f, 1f, 0.7f);
        gameOverPanel.setActive(false);
        TextContainer gameOverText = new TextContainer("Game Over!");
        gameOverText.setColor(0f, 0f, 0f, 1f);
        gameOverText.setActive(false);
        level.setGameOverPanel(gameOverPanel);
        level.setGameOverText(gameOverText);
        uiObjects.add(gameOverPanel);
        uiObjects.add(gameOverText);

        Button restartButton = new Button();
        DisplayMetrics screen = context.getResources().getDisplayMetrics();
        float aspectRatio = (float) screen.heightPixels / screen.widthPixels;
        float restartButtonSize = 0.3f;
        Vector3D restartButtonPosition = new Vector3D(0.7f, 0.3f, 0f);
        restartButton.setPosition(restartButtonPosition);
        restartButton.setScale(new Vector3D(1f, 1f / aspectRatio, 1f).scale(restartButtonSize));
        restartButton.setTouchBound();
        restartButton.setTexture(R.drawable.restart_icon);
        restartButton.setColor(1f, 1f, 1f, 0.7f);
        restartButton.setAction(this::restartGame);
        uiObjects.add(restartButton);
        buttons.add(restartButton);

        Button shiftButton = new Button();
        Vector3D shiftButtonPosition = new Vector3D(0.7f, -0.3f, 0f);
        shiftButton.setPosition(shiftButtonPosition);
        shiftButton.setScale(new Vector3D(1f, 1f / aspectRatio, 1f).scale(restartButtonSize));
        shiftButton.setTouchBound();
        player.setShiftButton(shiftButton);
        shiftButton.setAction(player::switchShift);
        uiObjects.add(shiftButton);
        buttons.add(shiftButton);
    }

    public void onPause() {
        if (timer != null) timer.cancel();
    }

    public void onResume() {
        timer = new Timer();
        timer.schedule(new UpdateTask(), 0, updatePeriod);
    }

    // frame start
    public void Start() {
        if (LoggerConfig.ON) {
            Log.w(TAG, "game start");
        }

        // set values
        updatePeriod = 30L; //about 30fps

        timer = new Timer();
        timer.schedule(new UpdateTask(), 0, updatePeriod);
    }

    public void Update() {
        if (LoggerConfig.ON) {
            Log.w(TAG, "Game Update");
        }

        for (GameObject object : objects) {
            object.Update();
        }
    }

    public void GetTouch(TouchHandler.Touch touch) {
        if (LoggerConfig.TOUCH_LOG) {
            Log.w(TAG, "Game Touch : " + touch.state + ", " + touch.pos);
        }
        // UI layer
        for (Button button : buttons) {
            if (button.isTouching(touch.pos.normalized())) {
                button.onPressed();
                return;
            }
        }

        // game layer
        player.handleTouch(touch);
    }

    public void restartGame() {
        level.restart();
        player.resetPlayer();
    }
}
