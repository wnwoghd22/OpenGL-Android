package com.eternal.rolly_roll.game.control;

import android.content.Context;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.util.LoggerConfig;

public class TouchHandler implements View.OnTouchListener {
    private static String TAG = "Touch handler";
    public static class Touch {
        public final int state;
        public final TouchPos pos;
        public Touch(int state, TouchPos pos) {
            this.state = state;
            this.pos = pos;
        }
    }
    public static class TouchPos {
        public final float x, y;
        public TouchPos(float x, float y) {
            this.x =x; this.y = y;
        }
        public TouchPos normalized() {
            return new TouchPos(
                (x / (float)screenWidth) * 2 - 1,
                -((y / (float)screenHeight) * 2 - 1)
            );
        }

        @NonNull
        @Override
        public String toString() {
            return "TouchPos(" + x + ", " + y + ")";
        }
    }
    private Context context;
    private Game game;
    private static int screenWidth;
    private static int screenHeight;
    public Touch touch;
    private boolean isTouching;

    public TouchHandler(Context context, Game game) {
        this.context = context;
        this.game = game;
        touch = new Touch(MotionEvent.INVALID_POINTER_ID, new TouchPos(0f, 0f));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent != null) {
            screenWidth = view.getWidth();
            screenHeight = view.getHeight();

            if (LoggerConfig.TOUCHLOG) {
                Log.w(TAG, "" + touch.pos);
            }

            int action = motionEvent.getAction();

            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    touch = new Touch(
                            MotionEvent.ACTION_DOWN,
                            new TouchPos(motionEvent.getX(), motionEvent.getY())
                    );
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch = new Touch(
                            MotionEvent.ACTION_MOVE,
                            new TouchPos(motionEvent.getX(), motionEvent.getY())
                    );
                    break;
                case MotionEvent.ACTION_UP:
                    touch = new Touch(
                            MotionEvent.ACTION_UP,
                            new TouchPos(motionEvent.getX(), motionEvent.getY())
                    );
                    break;
                default:
                    touch = new Touch(
                            action,
                            new TouchPos(0f, 0f)
                    );
                    break;
            }
            game.GetTouch(touch);

            return true;
        }

        return false;
    }

}
