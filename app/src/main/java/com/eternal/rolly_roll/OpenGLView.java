package com.eternal.rolly_roll;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.eternal.rolly_roll.game.Game;

public class OpenGLView extends GLSurfaceView {
    public OpenGLView(Context context) {
        super(context);
        init();
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
    }
}
