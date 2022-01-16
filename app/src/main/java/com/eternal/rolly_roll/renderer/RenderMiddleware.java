package com.eternal.rolly_roll.renderer;

import android.content.Context;
import android.graphics.Shader;
import static android.opengl.GLES20.*;
import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.shader.ShaderProgram;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class RenderMiddleware {
    public class Camera {
        private float near;
        private float far;
        public final boolean isOrthogonal;
        private float[] projectionM;
        public float[] GetProjectionM() { return projectionM; }
        private float[] viewM;
        public float[] GetViewM() { return viewM; }

        private float[] position;

        //Orthogonal
        public Camera(int width, int height, float near, float far) {
            isOrthogonal = true;
            this.near = near; this.far = far;
            projectionM = new float[16];
            viewM = new float[16];

            if (height > width) { //portrait
                final float aspectRatio = (float)height / (float)width;
                Matrix.orthoM(projectionM, 0, -1f, 1f, -aspectRatio, aspectRatio, near, far);
            } else { //landscape
                final float aspectRatio = (float)width / (float)height;
                Matrix.orthoM(projectionM, 0, -aspectRatio, aspectRatio, -1f, 1f, near, far);
            }
        }
        //Perspective
        public Camera(int width, int height, float near, float far, float fov) {
            isOrthogonal = false;
            this.near = near; this.far = far;
            projectionM = new float[16];
            viewM = new float[16];

            final float aspectRatio = (float)width / (float)height;
            Matrix.perspectiveM(projectionM, 0, fov, aspectRatio, near, far);
        }

    }
    private static final String TAG = "RenderMiddleware";

    private Context context;
    private Game game;
    private ShaderProgram shaderProgram;
    // call inside onDraw
    public void SetShaderProgram(ShaderProgram shader) { this.shaderProgram = shader; }
    public Camera camera;
    private FloatBuffer quad;

    public RenderMiddleware(Context context, Game game) {
        this.context = context;
        this.game = game;
        //this.shaderProgram = new ShaderProgram(context, R.raw.sprite_vertex, R.raw.sprite_fragment);
        quad = ByteBuffer.allocateDirect(
                Data.quadVertices.length * 4
        ).order(ByteOrder.nativeOrder()).asFloatBuffer();
        quad.put(Data.quadVertices);

        if(LoggerConfig.ON) {
            Log.v(TAG, "allocate buffer quad : " + quad.toString());
        }

    }

    // call this method every frame in OnDraw() of interface Renderer
    public void Render() {
        if (LoggerConfig.ON) {
            Log.v(TAG, "onDraw Render(Game)");
        }
        game.Render(this);
    }

    public void RenderQuad() {
        if (LoggerConfig.ON) {
            Log.v(TAG, "start draw quad");
        }
        glUseProgram(shaderProgram.ID);

        final int aPositionLocation = glGetAttribLocation(shaderProgram.ID, "aPos");
        quad.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 5 * 4, quad);

        final int aColorLocation = glGetAttribLocation(shaderProgram.ID, "aColor");
        quad.position(2);
        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 5 * 4, quad);

        glEnableVertexAttribArray(aPositionLocation);
        glEnableVertexAttribArray(aColorLocation);

        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public void RenderSquare() {

    }
}
