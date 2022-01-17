package com.eternal.rolly_roll.game.model.object.shape;

import androidx.annotation.Nullable;

import com.eternal.rolly_roll.game.model.object.physics.Transform;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;

import java.nio.*;

import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.BYTES_PER_FLOAT;

public abstract class Shape implements IRenderable {
    public Transform transform;
    protected float[] color;

    private final FloatBuffer floatBuffer;

    public Shape(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    @Override
    public void Render(RenderMiddleware r) {
        bindData(r.getSpriteShader());
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    protected abstract void bindData(SpriteShader spriteShader);

    protected void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }
    protected void setUniformVec4(int uniformLocation, float[] vector) {
        glUniform4f(uniformLocation, vector[0], vector[1], vector[2], vector[3]);
    }
}
