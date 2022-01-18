package com.eternal.rolly_roll.game;

import android.content.Context;
import android.util.Log;

import com.eternal.rolly_roll.game.model.object.GameObject;
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

    public Game(Context context) {
        this.context = context;

        objects = new ArrayList<GameObject>();
    }

    public void Init() {

        Start();
    }

    public void onPause() {
        if (timer != null) timer.cancel();
    }

    public void onResume() {
        timer = new Timer();
        timer.schedule(new UpdateTask(), updatePeriod);
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
