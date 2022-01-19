package com.eternal.rolly_roll.game.model;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import static com.eternal.rolly_roll.game.control.TouchHandler.*;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape3d.Cube;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

public class PlayerObject extends GameObject {
    private static String TAG = "player";

    private TouchPos initPos;
    private TouchPos endPos;

    public PlayerObject() {
        this.shape = new Cube();
        shape.color = new float[] { 1f, 1f, 1f, 1f };
    }
    public PlayerObject(Vector3D position) {
        this.shape = new Cube();
        shape.transform.position = position;
        shape.color = new float[] { 1f, 1f, 1f, 1f };
    }

    @Override
    public void Update() {

    }

    public void handleTouch(Touch touch) {
        switch(touch.state) {
            case MotionEvent.ACTION_DOWN:
                initPos = touch.pos;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endPos = touch.pos;
                Shift();
                break;

        }
    }

    public void Shift() {
        if (initPos == null || endPos == null) return;

        initPos = initPos.normalized();
        endPos = endPos.normalized();

        float deltaX = endPos.x - initPos.x;
        float deltaY = endPos.y - initPos.y;

        if (deltaY > 0) {
            if (deltaX > 0) { // up
                shape.transform.position.z -= 1f;
            } else { // left
                shape.transform.position.x -= 1f;
            }
        } else {
            if (deltaX > 0) { // right
                shape.transform.position.x += 1f;
            } else { // down
                shape.transform.position.z += 1f;
            }
        }
    }
}
