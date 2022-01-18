package com.eternal.rolly_roll.game.model.object.shape;

import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.game.model.object.physics.Transform;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.nio.*;

import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.BYTES_PER_FLOAT;

public abstract class Shape implements IRenderable {
    private static String TAG = "Shape";

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public Transform transform;
    public float[] color;
    public int texture;

    private final FloatBuffer floatBuffer;

    protected Shape(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
        transform = new Transform();
    }

    @Override
    public void Render(RenderMiddleware r) {

        if(LoggerConfig.ON) {
            Log.w(TAG, "draw shape");
        }

        Matrix.multiplyMM(
                r.getMVP(), 0,
                r.getVP(), 0,
                transform.getTransformM(), 0
        );

        bindData(r.getSpriteShader());
        glUniformMatrix4fv(r.getSpriteShader().uMatrixLocation, 1, false, r.getMVP(), 0);

        //glUniformMatrix4fv(r.getSpriteShader().uMatrixLocation, 1, false, transform.getTransformM(), 0);
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    protected void bindData(SpriteShader spriteShader) {
        //set position
        setVertexAttribPointer(
                0,
                spriteShader.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                spriteShader.aTexCoordLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
        //set color
        setUniformVec4(
                spriteShader.uColorLocation,
                color
        );
    }

    protected void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }
    protected void setUniformVec4(int uniformLocation, float[] vector) {
        glUniform4f(uniformLocation, vector[0], vector[1], vector[2], vector[3]);
    }
    protected void setUniformMf4(int uniformLocation, float[] matrix) {
        glUniformMatrix4fv(uniformLocation, 1, false, matrix, 0);
    }
}
