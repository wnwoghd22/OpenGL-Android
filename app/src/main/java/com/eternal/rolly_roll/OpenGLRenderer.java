package com.eternal.rolly_roll;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.renderer.Data;
import com.eternal.rolly_roll.renderer.RenderMiddleware;
import com.eternal.rolly_roll.shader.ShaderProgram;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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
    private float[] quadVertices = {
            // pos      // color
            // Triangle 1
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f,  0.5f, 1.0f, 1.0f, 1.0f,
            -0.5f,  0.5f, 1.0f, 1.0f, 1.0f,
            // Triangle 2
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 1.0f, 1.0f, 1.0f,
            0.5f,  0.5f, 1.0f, 1.0f, 1.0f
    };
    private static FloatBuffer quad;

    private int aPositionLocation;
    private int aColorLocation;
    private int uColorLocation;

    public OpenGLRenderer(Context context, RenderMiddleware renderer)  {
        this.context = context;
        this.renderer = renderer;

        quad = ByteBuffer.allocateDirect(
                quadVertices.length * 4
        ).order(ByteOrder.nativeOrder()).asFloatBuffer();
        quad.put(quadVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor((float) redValue, 0, 0, 0f);

        shader = new ShaderProgram(context, R.raw.sprite_vertex, R.raw.sprite_fragment);
        shader.validateProgram();
        renderer.SetShaderProgram(shader);

        glUseProgram(shader.ID);
        uColorLocation = glGetUniformLocation(shader.ID, "uColor");

        aPositionLocation = glGetAttribLocation(shader.ID, "aPos");
        quad.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 5, quad);

//        aColorLocation = glGetAttribLocation(shader.ID, "aColor");
//        quad.position(2);
//        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 5, quad);

        if (LoggerConfig.ON) {
            Log.v(TAG, "position location : " + aPositionLocation +
                    "\ncolor location : " + aColorLocation +
                    "\nuniform color location : " + uColorLocation);
        }

        glEnableVertexAttribArray(aPositionLocation);
        //glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        glViewport(0, 0, i, i1);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //GLES20.glClearColor((float) redValue, 0, 0, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        //redValue = Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION) * 0.5 + 0.5;

        //renderer.Render();
        /*
        glUseProgram(shader.ID);
        final int aPositionLocation = glGetAttribLocation(shader.ID, "aPos");
        quad.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 5, quad);

        final int aColorLocation = glGetAttribLocation(shader.ID, "aColor");
        quad.position(2);
        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 5, quad);

        glEnableVertexAttribArray(aPositionLocation);
        glEnableVertexAttribArray(aColorLocation);
        */

        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
