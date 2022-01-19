package com.eternal.rolly_roll.game;

import android.content.Context;
import android.util.Log;

import com.eternal.rolly_roll.game.control.Level;
import com.eternal.rolly_roll.game.model.PlayerObject;
import com.eternal.rolly_roll.game.model.TestCube;
import com.eternal.rolly_roll.game.model.TestGameObject;
import com.eternal.rolly_roll.game.model.Tile;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.IRenderable;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

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

    private Level level;

    public Game(Context context) {
        this.context = context;

        level = new Level();
        objects = new ArrayList<GameObject>();
    }

    public void Init() {

        objects.add(level);
        for (Tile[] tiles : level.GenerateBoard(5)) {
            for (Tile tile : tiles) {
                objects.add(tile);
            }
        }

        objects.add(new TestGameObject());
        objects.add(new TestGameObject(new Vector3D(1.3f, 0f, 0f)));

        objects.add(new TestGameObject(new Vector3D(0f, 0f, -1.3f)));

        objects.add(new TestGameObject(new Vector3D(-1.3f, 0f, 0f)));

        objects.add(new PlayerObject());

        objects.add(new TestCube(
                new Vector3D(1f, 2f, 3f),
                new Vector3D(0.3f, 0.3f),
                new Vector3D(1.5f, 1f, 1f)
        ));
        objects.add(new TestCube(
                new Vector3D(-3f, 3f, -5f),
                new Vector3D(0.3f, 0.7f),
                new Vector3D(2f, 2f, 2f)
        ));
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
}
