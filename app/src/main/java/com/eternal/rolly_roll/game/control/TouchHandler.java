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
        public final MotionEvent state;
        public final TouchPos pos;
        public Touch() {

        }
    }
    public static class TouchPos {
        final float x, y;
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
    public TouchPos touchPos;
    private boolean isTouching;

    public TouchHandler(Context context, Game game) {
        this.context = context;
        this.game = game;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent != null) {
            screenWidth = view.getWidth();
            screenHeight = view.getHeight();

            touchPos = new TouchPos(motionEvent.getX(), motionEvent.getY());

            if (LoggerConfig.TOUCHLOG) {
                Log.w(TAG, "" + touchPos);
            }

            switch(motionEvent.getAction()) {

            }
        }

        return false;
    }

}
