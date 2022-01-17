package com.eternal.rolly_roll;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.eternal.rolly_roll.renderer.RenderMiddleware;
import com.eternal.rolly_roll.shader.ShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private static String TAG = "OpenGL Renderer";

    private double redValue = 1f;
    private static final double FLASH_DURATION = 1000; // in ms

    private Context context;
    private RenderMiddleware renderer;
    private ShaderProgram shader;

    public OpenGLRenderer(Context context, RenderMiddleware renderer)  {
        this.context = context;
        this.renderer = renderer;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor((float) redValue, 0, 0, 0f);

        shader = new ShaderProgram(context, R.raw.sprite_vertex, R.raw.sprite_fragment);
        renderer.SetShaderProgram(shader);
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
