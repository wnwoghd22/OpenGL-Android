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
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public Transform transform;
    protected float[] color;
    protected int texture;

    private final FloatBuffer floatBuffer;

    public Shape(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    @Override
    public final void Render(RenderMiddleware r) {
        bindData(r.getSpriteShader());
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    protected void bindData(SpriteShader spriteShader) {
        //set position
        setVertexAttribPointer(
                0,
                POSITION_COMPONENT_COUNT,
                spriteShader.aPositionLocation,
                STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                spriteShader.aTexCoordLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );

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
}
