package com.eternal.rolly_roll.game.model;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import static com.eternal.rolly_roll.game.control.TouchHandler.*;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Quaternion;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape3d.Cube;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.util.concurrent.atomic.AtomicReferenceArray;

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
            int rotX = Math.round(shape.transform.rotation.x),
                    rotY = Math.round(shape.transform.rotation.y),
                    rotZ = Math.round(shape.transform.rotation.z);
            switch (moveDirection) {
                case UP:
                    anchor = new Vector3D(
                        currentPos.x,
                        0,
                        currentPos.z - 0.5f
                    );
                    break;
                case DOWN:
                    anchor = new Vector3D(
                            currentPos.x,
                            0,
                            currentPos.z + 0.5f
                    );
                    break;
                case LEFT:
                    anchor = new Vector3D(
                            currentPos.x - 0.5f,
                            0,
                            currentPos.z
                    );
                    break;
                case RIGHT:
                    anchor = new Vector3D(
                            currentPos.x + 0.5f,
                            0,
                            currentPos.z
                    );
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
            if (LoggerConfig.TOUCHLOG) {
                float[] transformM = shape.transform.getTransformM();
                Log.w(TAG, "roll direction : " + moveDirection + "player rotation : " + shape.transform.rotation +
                        "\n" + new Quaternion(shape.transform.rotation) +
                        "\n rotation Axis : " + axisState[0] + ", " + axisState[1] + ", " + axisState[2] + " " + axisState[3] + " " + axisState[4] + " " + axisState[5]);
            }
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
        if (LoggerConfig.TOUCHLOG) {
            Log.w(TAG, "shift, frame: " + frameDelta + "player position : " + shape.transform.position);
        }
    }
    private void Roll() {
        float rotateDelta = 1f / (float)moveFrame;
        float moveDelta = (float)Math.toRadians((float)frameDelta / (float)moveFrame * 90f + 45f);

        switch (moveDirection) {
            case UP:
                switch (axisState[2]) { // if right axis is...
                    case F: // +z
                        shape.transform.rotation.z -= rotateDelta;
                        break;
                    case B: // -z
                        shape.transform.rotation.z += rotateDelta;
                        break;
                    case R: // +x (base movement)
                        shape.transform.rotation.x -= rotateDelta;
                        break;
                    case L: // -x
                        shape.transform.rotation.x -= rotateDelta; // ????
                        break;
                    case U: // +y
                        shape.transform.rotation.y -= rotateDelta;
                        break;
                    case D: // -y
                        shape.transform.rotation.y += rotateDelta;
                        break;
                }
                shape.transform.position = new Vector3D(
                        anchor.x,
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        anchor.z + (float)Math.cos(moveDelta) / (float)Math.sqrt(2f)
                );
                break;
            case DOWN:
                switch (axisState[2]) { // if right axis is...
                    case F: // +z
                        shape.transform.rotation.z += rotateDelta;
                        break;
                    case B: // -z
                        shape.transform.rotation.z -= rotateDelta;
                        break;
                    case R: // +x (base movement)
                        shape.transform.rotation.x += rotateDelta;
                        break;
                    case L: // -x
                        shape.transform.rotation.x += rotateDelta; // ???
                        break;
                    case U: // +y
                        shape.transform.rotation.y += rotateDelta;
                        break;
                    case D: // -y
                        shape.transform.rotation.y -= rotateDelta;
                        break;
                }
                shape.transform.position = new Vector3D(
                        anchor.x,
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        anchor.z - (float)Math.cos(moveDelta) / (float)Math.sqrt(2f)
                );
                break;
            case LEFT:
                switch (axisState[1]) { // if front axis is...
                    case F: // +z (base movement)
                        shape.transform.rotation.z += rotateDelta;
                        break;
                    case B: // -z
                        shape.transform.rotation.z -= rotateDelta;
                        break;
                    case R: // +x
                        shape.transform.rotation.x -= rotateDelta;
                        break;
                    case L: // -x
                        shape.transform.rotation.x += rotateDelta;
                        break;
                    case U: // +y
                        shape.transform.rotation.y += rotateDelta;
                        break;
                    case D: // -y
                        shape.transform.rotation.y -= rotateDelta;
                        break;
                }
                shape.transform.position = new Vector3D(
                        anchor.x + (float)Math.cos(moveDelta) / (float)Math.sqrt(2f),
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        anchor.z
                );
                break;
            case RIGHT:
                switch (axisState[1]) { // if front axis is...
                    case F: // +z (base movement)
                        shape.transform.rotation.z -= rotateDelta;
                        break;
                    case B: // -z
                        shape.transform.rotation.z += rotateDelta;
                        break;
                    case R: // +x
                        shape.transform.rotation.x += rotateDelta;
                        break;
                    case L: // -x
                        shape.transform.rotation.x -= rotateDelta;
                        break;
                    case U: // +y
                        shape.transform.rotation.y -= rotateDelta;
                        break;
                    case D: // -y
                        shape.transform.rotation.y += rotateDelta;
                        break;
                }
                shape.transform.position = new Vector3D(
                    anchor.x - (float)Math.cos(moveDelta) / (float)Math.sqrt(2f),
                    (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                    anchor.z
                );
                break;
        }
    }
}
