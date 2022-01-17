package com.eternal.rolly_roll.game.object;

import java.nio.*;

import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.BYTES_PER_FLOAT;

public abstract class RenderableObject extends GameObject implements IRenderable {
    protected float[] position;
    protected float[] rotation;
    protected float[] scale;

    private final FloatBuffer floatBuffer;

    public RenderableObject(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componentCount, int stride) {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);

        floatBuffer.position(0);
    }
}
