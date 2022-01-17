package com.eternal.rolly_roll.game;

import android.util.Log;

import com.eternal.rolly_roll.renderer.RenderMiddleware;
import com.eternal.rolly_roll.util.LoggerConfig;

public class TestGameObject extends GameObject {

    private int i = 0;

    private static final String TAG = "Test game object";
    @Override
    public void Render(RenderMiddleware r) {
        if(LoggerConfig.ON) {
            Log.w(TAG, "render quad");
        }
        r.RenderQuad(i);
    }


    @Override
    public void Update() {
        ++i;
        if (i > 30) i = 0;
    }
}
