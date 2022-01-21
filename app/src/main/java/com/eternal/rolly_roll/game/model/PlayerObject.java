package com.eternal.rolly_roll.game.model;

import android.util.Log;
import android.view.MotionEvent;

import static com.eternal.rolly_roll.game.control.TouchHandler.*;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Quaternion;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape3d.Cube;
import com.eternal.rolly_roll.util.LoggerConfig;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

enum Axis {
    F, // front
    B, // back
    R, // right
    L, // left
    U, // up
    D  // down
}

public class PlayerObject extends GameObject {
    private static String TAG = "player";

    private TouchPos initPos;
    private TouchPos endPos;

    private boolean isShifting;
    private boolean isMoving;
    private Direction moveDirection;
    private final int moveFrame = 15;
    private int frameDelta = 0;
    private Vector3D anchor;

    private Axis[] axisState = { Axis.U, Axis.F, Axis.R, Axis.B, Axis.L, Axis.D };
    private void rotateAxis(Direction d) {
        Axis temp = axisState[0];
        switch (d) {
            case UP:
                axisState[0] = axisState[1];
                axisState[1] = axisState[5];
                axisState[5] = axisState[3];
                axisState[3] = temp;
                break;
            case DOWN:
                axisState[0] = axisState[3];
                axisState[3] = axisState[5];
                axisState[5] = axisState[1];
                axisState[1] = temp;
                break;
            case LEFT:
                axisState[0] = axisState[2];
                axisState[2] = axisState[5];
                axisState[5] = axisState[4];
                axisState[4] = temp;
                break;
            case RIGHT:
                axisState[0] = axisState[4];
                axisState[4] = axisState[5];
                axisState[5] = axisState[2];
                axisState[2] = temp;
                break;
        }
    }

    public PlayerObject() {
        this.shape = new Cube();
        shape.color = new float[] { 1f, 1f, 1f, 1f };
        isMoving = false;
        isShifting = false;
    }
    public PlayerObject(Vector3D position) {
        this.shape = new Cube();
        shape.transform.position = position;
        //shape.transform.rotation = new Quaternion(new Vector3D(90f, 0f, 0f));
        shape.textureID = R.drawable.dice_texture;
        shape.color = new float[] { 1f, 1f, 1f, 1f };
        isMoving = false;
        isShifting = false;
    }

    @Override
    public void Update() {
        if (isMoving)
            Move();
    }

    public void handleTouch(Touch touch) {
        if (isMoving) return;
        switch(touch.state) {
            case MotionEvent.ACTION_DOWN:
                initPos = touch.pos;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                endPos = touch.pos;
                initMoving();
                break;
        }
    }
    private void initMoving() {
        if (initPos == null || endPos == null) return;

        frameDelta = 0;

        initPos = initPos.normalized();
        endPos = endPos.normalized();

        float deltaX = endPos.x - initPos.x;
        float deltaY = endPos.y - initPos.y;

        if (deltaY > 0) {
            if (deltaX > 0) { // up
                moveDirection = Direction.UP;
            } else { // left
                moveDirection = Direction.LEFT;
            }
        } else {
            if (deltaX > 0) { // right
                moveDirection = Direction.RIGHT;
            } else { // down
                moveDirection = Direction.DOWN;
            }
        }
        isMoving = true;
        //ShiftGrid();
        if (!isShifting) {
            Vector3D currentPos = shape.transform.position;
            switch (moveDirection) {
                case UP:
                    //shape.transform.rotation = shape.transform.rotation.rotate(new Quaternion(-90f, new Vector3D(1f, 0f, 0f)));
                    shape.transform.rotation = shape.transform.rotation.product(new Quaternion(90f, new Vector3D(1f, 0f, 0f)));
                    anchor = new Vector3D(currentPos.x, 0, currentPos.z - 0.5f);
                    break;
                case DOWN:
                    shape.transform.rotation = shape.transform.rotation.product(new Quaternion(-90f, new Vector3D(1f, 0f, 0f)));
                    anchor = new Vector3D(currentPos.x, 0, currentPos.z + 0.5f);
                    break;
                case LEFT:
                    shape.transform.rotation = shape.transform.rotation.product(new Quaternion(-90f, new Vector3D(0f, 0f, 1f)));
                    anchor = new Vector3D(currentPos.x - 0.5f, 0, currentPos.z);
                    break;
                case RIGHT:
                    shape.transform.rotation = shape.transform.rotation.product(new Quaternion(90f, new Vector3D(0f, 0f, 1f)));
                    anchor = new Vector3D(currentPos.x + 0.5f, 0, currentPos.z);
                    break;
            }
        }
    }

    // call in Update
    private void Move() {
        if (frameDelta++ < moveFrame) {
            if (!isShifting) {
                Roll();
            } else {
                Shift();
            }
        } else {
            frameDelta = 0;
            isMoving = false;
            if (!isShifting)
                rotateAxis(moveDirection);

        }
    }

    private void ShiftGrid() {
        switch (moveDirection) {
            case UP:
                shape.transform.position.z -= 1f;
                break;
            case DOWN:
                shape.transform.position.z += 1f;
                break;
            case LEFT:
                shape.transform.position.x -= 1f;
                break;
            case RIGHT:
                shape.transform.position.x += 1f;
                break;
        }
    }

    private void Shift() {
        float moveDelta = 1f / (float)moveFrame;
        switch (moveDirection) {
            case UP:
                shape.transform.position.z -= moveDelta;
                break;
            case DOWN:
                shape.transform.position.z += moveDelta;
                break;
            case LEFT:
                shape.transform.position.x -= moveDelta;
                break;
            case RIGHT:
                shape.transform.position.x += moveDelta;
                break;
        }
        if (LoggerConfig.TOUCH_LOG) {
            Log.w(TAG, "shift, frame: " + frameDelta + "player position : " + shape.transform.position);
        }
    }
    private void Roll() {
        float rotateDelta = 1f / (float)moveFrame;
        float moveDelta = (float)Math.toRadians((float)frameDelta / (float)moveFrame * 90f + 45f);

        switch (moveDirection) {
            case UP:
                shape.transform.position = new Vector3D(
                        anchor.x,
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        anchor.z + (float)Math.cos(moveDelta) / (float)Math.sqrt(2f)
                );
                break;
            case DOWN:
                shape.transform.position = new Vector3D(
                        anchor.x,
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        anchor.z - (float)Math.cos(moveDelta) / (float)Math.sqrt(2f)
                );
                break;
            case LEFT:
                shape.transform.position = new Vector3D(
                        anchor.x + (float)Math.cos(moveDelta) / (float)Math.sqrt(2f),
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        anchor.z
                );
                break;
            case RIGHT:
                shape.transform.position = new Vector3D(
                    anchor.x - (float)Math.cos(moveDelta) / (float)Math.sqrt(2f),
                    (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                    anchor.z
                );
                break;
        }
    }
}
