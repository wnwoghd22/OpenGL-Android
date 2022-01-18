package com.eternal.rolly_roll.game.model.object.shape.shape3d;

import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.util.LoggerConfig;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static com.eternal.rolly_roll.util.Data.CUBE_VERTICES;

public class Cube extends Shape {
    private static String TAG = "Shape Cube";

    public Cube() {
        super(CUBE_VERTICES);
        color = new float[] {1f, 1f, 1f, 1f};
        textureID = R.drawable.square;
    }
    @Override
    public void Render(RenderMiddleware r) {

        if(LoggerConfig.ON) {
            Log.w(TAG, "draw shape cube");
        }

        Matrix.multiplyMM(
                r.getMVP(), 0,
                r.getVP(), 0,
                transform.getTransformM(), 0
        );

        bindData(r.getSpriteShader());
        glUniformMatrix4fv(r.getSpriteShader().uMatrixLocation, 1, false, r.getMVP(), 0);

        //glUniformMatrix4fv(r.getSpriteShader().uMatrixLocation, 1, false, transform.getTransformM(), 0);
        glDrawArrays(GL_TRIANGLES, 0, 36);
    }

}
