package com.eternal.rolly_roll;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private static String TAG = "OpenGL Renderer";

    private Context context;
    private RenderMiddleware renderer;

    public OpenGLRenderer(Context context, RenderMiddleware renderer)  {
        this.context = context;
        this.renderer = renderer;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0f, 0f, 0f, 0f);

        SpriteShader spriteShader = new SpriteShader(context);
        renderer.setSpriteShader(spriteShader);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        glViewport(0, 0, i, i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);

        renderer.Render();
    }
}
