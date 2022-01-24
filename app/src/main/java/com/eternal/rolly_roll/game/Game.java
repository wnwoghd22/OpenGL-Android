package com.eternal.rolly_roll.game;

import android.content.Context;
import android.util.Log;

import com.eternal.rolly_roll.game.control.Level;
import com.eternal.rolly_roll.game.control.TouchHandler;
import com.eternal.rolly_roll.game.model.PlayerObject;
import com.eternal.rolly_roll.game.model.Tile;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.ui.text.Text;
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
    private PlayerObject player;

    private Level level;
    private int boardSize;

    public Game(Context context) {
        this.context = context;

        level = new Level();
        objects = new ArrayList<GameObject>();
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

//        TextContainer testText = new TextContainer("c");
//
//        objects.add(testText);

        Start();
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

        for (GameObject object : objects
             ) {
            object.Update();
        }
    }

    public void GetTouch(TouchHandler.Touch touch) {
        if (LoggerConfig.TOUCH_LOG) {
            Log.w(TAG, "Game Touch : " + touch.state + ", " + touch.pos);
        }
        // UI layer

        player.handleTouch(touch);
    }
}
