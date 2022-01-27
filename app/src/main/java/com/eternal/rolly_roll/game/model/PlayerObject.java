package com.eternal.rolly_roll.game.model;

import android.util.Log;
import android.view.MotionEvent;

import static com.eternal.rolly_roll.game.control.TouchHandler.*;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.control.Axis;
import com.eternal.rolly_roll.game.control.Level;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Quaternion;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape3d.Cube;
import com.eternal.rolly_roll.game.view.ui.Button;
import com.eternal.rolly_roll.game.view.ui.panel.Panel;
import com.eternal.rolly_roll.game.view.ui.text.TextContainer;
import com.eternal.rolly_roll.util.LoggerConfig;

public class PlayerObject extends GameObject {
    enum Direction {
        UP {
            @Override
            void initRoll(PlayerObject player) {
                Vector3D currentPos = player.shape.transform.position;
                player.originRotation = player.shape.transform.rotation;

                player.targetRotation = player.shape.transform.rotation.product(new Quaternion(new Vector3D(90f, 0f, 0f)));
                player.anchor = new Vector3D(currentPos.x, 0, currentPos.z - 0.5f);
            }

            @Override
            public void roll(PlayerObject player, float slerpDelta) {
                float moveDelta = (float)Math.toRadians(slerpDelta * 90f + 45f);
                player.shape.transform.rotation = Quaternion.slerp(player.originRotation, player.targetRotation, slerpDelta);

                player.shape.transform.position = new Vector3D(
                    player.anchor.x,
                    (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                    player.anchor.z + (float)Math.cos(moveDelta) / (float)Math.sqrt(2f)
                );
            }

            @Override
            void shift(PlayerObject player, float moveDelta) {
                player.shape.transform.position.z -= moveDelta;
            }

            @Override
            void rotateAxis(Axis[] axes) {
                Axis temp = axes[0];
                axes[0] = axes[1];
                axes[1] = axes[5];
                axes[5] = axes[3];
                axes[3] = temp;
            }
        },
        DOWN {
            @Override
            void initRoll(PlayerObject player) {
                Vector3D currentPos = player.shape.transform.position;
                player.originRotation = player.shape.transform.rotation;

                player.targetRotation = player.shape.transform.rotation.product(new Quaternion(-90f, new Vector3D(1f, 0f, 0f)));
                player.anchor = new Vector3D(currentPos.x, 0, currentPos.z + 0.5f);
            }

            @Override
            public void roll(PlayerObject player, float slerpDelta) {
                float moveDelta = (float)Math.toRadians(slerpDelta * 90f + 45f);
                player.shape.transform.rotation = Quaternion.slerp(player.originRotation, player.targetRotation, slerpDelta);

                player.shape.transform.position = new Vector3D(
                        player.anchor.x,
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        player.anchor.z - (float)Math.cos(moveDelta) / (float)Math.sqrt(2f)
                );
            }

            @Override
            void shift(PlayerObject player, float moveDelta) {
                player.shape.transform.position.z += moveDelta;
            }

            @Override
            void rotateAxis(Axis[] axes) {
                Axis temp = axes[0];
                axes[0] = axes[3];
                axes[3] = axes[5];
                axes[5] = axes[1];
                axes[1] = temp;
            }
        },
        LEFT {
            @Override
            void initRoll(PlayerObject player) {
                Vector3D currentPos = player.shape.transform.position;
                player.originRotation = player.shape.transform.rotation;

                player.targetRotation = player.shape.transform.rotation.product(new Quaternion(new Vector3D(0f, 0f, -90f)));
                player.anchor = new Vector3D(currentPos.x - 0.5f, 0, currentPos.z);
            }

            @Override
            public void roll(PlayerObject player, float slerpDelta) {
                float moveDelta = (float)Math.toRadians(slerpDelta * 90f + 45f);
                player.shape.transform.rotation = Quaternion.slerp(player.originRotation, player.targetRotation, slerpDelta);

                player.shape.transform.position = new Vector3D(
                        player.anchor.x + (float)Math.cos(moveDelta) / (float)Math.sqrt(2f),
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        player.anchor.z
                );
            }

            @Override
            void shift(PlayerObject player, float moveDelta) {
                player.shape.transform.position.x -= moveDelta;
            }

            @Override
            void rotateAxis(Axis[] axes) {
                Axis temp = axes[0];
                axes[0] = axes[2];
                axes[2] = axes[5];
                axes[5] = axes[4];
                axes[4] = temp;
            }
        },
        RIGHT {
            @Override
            void initRoll(PlayerObject player) {
                Vector3D currentPos = player.shape.transform.position;
                player.originRotation = player.shape.transform.rotation;

                player.targetRotation = player.shape.transform.rotation.product(new Quaternion(90f, new Vector3D(0f, 0f, 1f)));
                player.anchor = new Vector3D(currentPos.x + 0.5f, 0, currentPos.z);
            }

            @Override
            public void roll(PlayerObject player, float slerpDelta) {
                float moveDelta = (float)Math.toRadians(slerpDelta * 90f + 45f);
                player.shape.transform.rotation = Quaternion.slerp(player.originRotation, player.targetRotation, slerpDelta);

                player.shape.transform.position = new Vector3D(
                        player.anchor.x - (float)Math.cos(moveDelta) / (float)Math.sqrt(2f),
                        (float)Math.sin(moveDelta) / (float)Math.sqrt(2f),
                        player.anchor.z
                );
            }

            @Override
            void shift(PlayerObject player, float moveDelta) {
                player.shape.transform.position.x += moveDelta;
            }

            @Override
            void rotateAxis(Axis[] axes) {
                Axis temp = axes[0];
                axes[0] = axes[4];
                axes[4] = axes[5];
                axes[5] = axes[2];
                axes[2] = temp;
            }
        };
        abstract void initRoll(PlayerObject player);
        abstract void roll(PlayerObject player, float slerpDelta);
        abstract void shift(PlayerObject player, float moveDelta);
        abstract void rotateAxis(Axis[] axes);
    }
    private static String TAG = "player";

    private TouchPos initPos;
    private TouchPos endPos;

    private int shiftItem = 1;
    public int getShiftItemCount() {
        return shiftItem;
    }
    private boolean isShifting;
    private Button shiftButton;
    private TextContainer shiftLeft;
    public void setShiftButton(Button button) {
        shiftButton = button;
    }
    public void setShiftText(TextContainer text) {
        shiftLeft = text;
    }

    private int bombItem = 1;
    public int getBombItemCount() {
        return bombItem;
    }
    private Button bombButton;
    private TextContainer bombLeft;
    public void setBombButton(Button button) {
        bombButton = button;
    }
    public void setBombText(TextContainer text) {
        bombLeft = text;
    }

    private boolean isMoving;
    private Direction moveDirection;
    private final int moveFrame = 15;
    private int frameDelta = 0;
    private Vector3D anchor;
    private Quaternion originRotation;
    private Quaternion targetRotation;

    private Level level;
    private int boardSize;
    private int posX = 0, posY = 0;
    public void setLevel(Level l) {
        level = l;
        boardSize = l.getBoardSize();
    }

    private final Axis[] axisState = { Axis.U, Axis.F, Axis.R, Axis.B, Axis.L, Axis.D };
    private void resetAxisState() {
        axisState[0] = Axis.U;
        axisState[1] = Axis.F;
        axisState[2] = Axis.R;
        axisState[3] = Axis.B;
        axisState[4] = Axis.L;
        axisState[5] = Axis.D;
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

        initPos = null; endPos = null;

        if (deltaX * deltaX + deltaY * deltaY < 0.01f) return; // too short drag

        if (deltaY > 0) {
            if (deltaX > 0) { // up
                if (posY == 0) return;
                --posY;
                moveDirection = Direction.UP;
            } else { // left
                if (posX == 0) return;
                --posX;
                moveDirection = Direction.LEFT;
            }
        } else {
            if (deltaX > 0) { // right
                if (posX == boardSize - 1) return;
                ++posX;
                moveDirection = Direction.RIGHT;
            } else { // down
                if (posY == boardSize - 1) return;
                ++posY;
                moveDirection = Direction.DOWN;
            }
        }
        isMoving = true;

        if (!isShifting) {
            moveDirection.initRoll(this);

            if (LoggerConfig.QUATERNION_LOG) {
                Log.w(TAG, "target-versorform : " + new Quaternion.VersorForm(targetRotation));
            }
        } else {
            if (shiftButton != null)
                shiftButton.setColor(1f, 1f, 1f, 0.7f);
        }
    }

    // call in Update
    private void Move() {
        if (frameDelta++ < moveFrame) {
            if (!isShifting) Roll();
            else Shift();
        } else {
            frameDelta = 0;
            isMoving = false;
            if (!isShifting)
                moveDirection.rotateAxis(axisState);
            else {
                isShifting = false;
                if (shiftLeft != null)
                    shiftLeft.setText(shiftItem);
            }

            level.stamp(posX, posY, axisState[5]);
        }
    }

    public void switchShift() {
        if (!isShifting) {
            if (shiftItem > 0) {
                --shiftItem;
                isShifting = true;
                if (shiftButton != null)
                    shiftButton.setColor(1f, 0f, 0f, 0.7f);
            }
        } else {
            ++shiftItem;
            isShifting = false;
            if (shiftButton != null)
                shiftButton.setColor(1f, 1f, 1f, 0.7f);
        }
    }

    private void Shift() {
        float moveDelta = 1f / (float)moveFrame;
        moveDirection.shift(this, moveDelta);
    }
    private void Roll() {
        float slerpDelta = (float)frameDelta / (float)moveFrame;
        moveDirection.roll(this, slerpDelta);
    }

    public void useBombItem() {
        if (bombItem == 0)
            return;
        --bombItem;
        if (bombLeft != null) {
            bombLeft.setText(bombItem);
        }

        level.bomb(posX, posY);
    }

    public void resetPlayer() {
        setPosition(new Vector3D(-(float)(boardSize - 1) / 2.0f, 0.5f, -(float)(boardSize - 1) / 2.0f));
        setRotation(Quaternion.identity());
        posX = 0; posY = 0;
        resetAxisState();
        shiftItem = 1;
        if (shiftLeft != null)
            shiftLeft.setText(shiftItem);
        bombItem = 1;
        if (bombLeft != null)
            bombLeft.setText(bombItem);
    }
}
