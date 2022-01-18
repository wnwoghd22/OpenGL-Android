package com.eternal.rolly_roll.game.model;

import android.util.Log;

import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape2d.Quad;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.util.LoggerConfig;

public class TestGameObject extends GameObject {
    private static final String TAG = "Test game object";

    public TestGameObject() {
        this.shape = new Quad();
        shape.transform.rotation.x = -1f;
    }

    @Override
    public void Update() {

    }
}