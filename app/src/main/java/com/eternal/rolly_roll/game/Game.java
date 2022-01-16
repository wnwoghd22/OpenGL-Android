package com.eternal.rolly_roll.game;

import android.content.Context;
import android.util.Log;

import com.eternal.rolly_roll.renderer.RenderMiddleware;
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

    private List<GameObject> objects;

    public Game(Context context) {
        this.context = context;

        objects = new ArrayList<GameObject>();
        objects.add(new TestGameObject());
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
        // set values
        updatePeriod = 30L; //about 30fps

        timer = new Timer();
        timer.schedule(new UpdateTask(), updatePeriod);
    }

    public void Update() {

    }

    public void Render(RenderMiddleware r) {
        if(LoggerConfig.ON) {
            Log.v(TAG, "Start Rendering each game object, size : " + objects.size());
        }
        for (GameObject object : objects) {
            object.Render(r);
        }
    }
}
