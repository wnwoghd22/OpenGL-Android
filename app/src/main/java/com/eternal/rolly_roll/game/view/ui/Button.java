package com.eternal.rolly_roll.game.view.ui;

import android.util.Log;
import static android.view.MotionEvent.*;

import com.eternal.rolly_roll.game.control.IButton;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.IRenderable;
import com.eternal.rolly_roll.game.model.object.shape.shape2d.Quad;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.ui.panel.Panel;
import com.eternal.rolly_roll.util.LoggerConfig;

import static com.eternal.rolly_roll.game.control.TouchHandler.*;

public class Button extends GameObject implements IButton {
    public enum eState {
        NONE,
        DOWN,
        PRESSED,
        UP,
    }

    private static final String TAG = "Button";
    private static final float QUAD_BOUND = 0.5f;

    private TouchPos leftBottom = new TouchPos(-QUAD_BOUND, -QUAD_BOUND);
    private TouchPos rightTop = new TouchPos(QUAD_BOUND, QUAD_BOUND);
    public void setTouchBound() {
        Vector3D position = this.shape.transform.position;
        Vector3D scale = this.shape.transform.scale;

        float leftBound = position.x - QUAD_BOUND * scale.x;
        float rightBound = position.x + QUAD_BOUND * scale.x;
        float topBound = position.y + QUAD_BOUND * scale.y;
        float bottomBound = position.y - QUAD_BOUND * scale.y;

        leftBottom = new TouchPos(leftBound, bottomBound);
        rightTop = new TouchPos(rightBound, topBound);
    }

    @Override
    public boolean isTouching(TouchPos pos) {
        if (LoggerConfig.TOUCH_LOG) {
            Log.w(TAG, "\ntouch pos : " + pos +
                    "\nleftBottom pos : " + leftBottom +
                    "\nrightTop pos : " + rightTop);
        }
        boolean isTouching = (pos.x > leftBottom.x && pos.x < rightTop.x) &&
                (pos.y > leftBottom.y && pos.y < rightTop.y);
        if (LoggerConfig.TOUCH_LOG) {
            Log.w(TAG, "\nis touching : " + isTouching);
        }
        return isTouching;
    }

    @Override
    public boolean handleTouch(Touch touch) {
        if (!isTouching(touch.pos.normalized())) { // touch outside
            switch (state) {
                case NONE:
                    break;
                case DOWN:
                case PRESSED:
                case UP:
                    state = eState.NONE;
                    break;
            }
            return false;
        } else {
            switch (state) {
                case NONE:
                    if (touch.state == ACTION_DOWN)
                        state = eState.DOWN;
                    break;
                case DOWN:
                    break;
                case PRESSED:
                    if (touch.state == ACTION_UP)
                        state = eState.UP;
                    break;
                case UP:
                    break;
            }
            return true;
        }
    }

    private eState state = eState.NONE;
    public eState getState() {
        return state;
    }

    private Runnable action;

    public Button() {
        this.shape = new Panel();
    }
    public Button(Vector3D position, Vector3D scale) {
        this.shape = new Panel();

        this.shape.transform.position = position;
        this.shape.transform.scale = scale;

        setTouchBound();
    }

    @Override
    public void Update() {
        switch (state) {
            case NONE:
                break;
            case DOWN:
                state = eState.PRESSED;
                break;
            case PRESSED:
                break;
            case UP:
                state = eState.NONE;
                onPressed();
                break;
        }
    }

    @Override
    public void setAction(Runnable action) {
        this.action = action;
    }

    @Override
    public void onPressed() {
        if (active)
            action.run();
    }


}
