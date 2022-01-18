package com.eternal.rolly_roll.game.model;

import android.opengl.GLSurfaceView;

import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.shape.shape3d.Cube;

public class PlayerObject extends GameObject {

    public PlayerObject() {
        this.shape = new Cube();
        shape.color = new float[] { 1f, 1f, 1f, 1f };
    }

    @Override
    public void Update() {

    }
}
