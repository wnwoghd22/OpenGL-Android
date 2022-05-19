package com.eternal.rolly_roll.game.model.object.shape;

import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.physics.Transform;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.nio.*;

import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.BYTES_PER_FLOAT;

public abstract class Shape implements IRenderable {
    private static String TAG = "Shape";

    protected static final int POSITION_COMPONENT_COUNT = 3;
    protected static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    protected static final int NORMAL_COMPONENT_COUNT = 3;
    protected static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT + NORMAL_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public Transform transform;
    public float[] color;
    public int textureID;

    private final FloatBuffer floatBuffer;
    private final ByteBuffer indexArray;
    private final int indexLength;

    private float[] modelM;
    private float[] tempM;
    private float[] it_modelM;

    protected Shape(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
        indexArray = null;
        indexLength = 0;

        transform = new Transform();
        color = new float[] { 1f, 1f, 1f, 1f };

        modelM = new float[16];
        tempM = new float[16];
        it_modelM = new float[16];
    }
    protected Shape(float[] vertexData, byte[] indexArray) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
        indexLength = indexArray.length;
        this.indexArray = ByteBuffer.allocateDirect(indexLength).put(indexArray);
        this.indexArray.position(0);
        transform = new Transform();
        color = new float[] { 1f, 1f, 1f, 1f };

        modelM = new float[16];
        tempM = new float[16];
        it_modelM = new float[16];
    }

    @Override
    public void Render(RenderMiddleware r) {
        bindData(r);

        glDrawElements(GL_TRIANGLES, indexLength, GL_UNSIGNED_BYTE, indexArray);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    protected void bindData(RenderMiddleware r) {
        //set position
        setVertexAttribPointer(
                0,
                r.getSpriteShader().aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                r.getSpriteShader().aTexCoordLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );

        //set normal vector
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT,
                r.getSpriteShader().aNormalLocation,
                NORMAL_COMPONENT_COUNT,
                STRIDE
        );

        //set color
        setUniformVec4(
                r.getSpriteShader().uColorLocation,
                color
        );

        // float[] modelM = transform.getTransformM();
        transform.getTransformM(modelM);

        Matrix.multiplyMM(
                r.getMVP(), 0,
                r.getVP(), 0,
                modelM, 0
        );

        //set texture
        // set the active texture unit to texture unit 0
        glActiveTexture(GL_TEXTURE0);
        // bind the texture to this unit
        glBindTexture(GL_TEXTURE_2D, r.textureMap.get(textureID));

        glUniform1i(r.getSpriteShader().uTextureUnitLocation, 0);
        glUniformMatrix4fv(r.getSpriteShader().uMatrixLocation, 1, false, r.getMVP(), 0);

        // float[] tempM = new float[16];
        // float[] it_modelM = new float[16];

        Matrix.invertM(tempM, 0, modelM, 0);
        Matrix.transposeM(it_modelM, 0, tempM, 0);

        glUniformMatrix4fv(r.getSpriteShader().uIT_ModelLocation, 1, false, it_modelM, 0);

        // tempM = null;
        // modelM = null;
        // it_modelM = null;

        //set directional light
        if (r.directionalLightVector != null) {
            setUniformVec3(
                    r.getSpriteShader().uDirectionalLightLocation,
                    r.directionalLightVector
            );
        }
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
    protected void setUniformVec3(int uniformLocation, Vector3D vector) {
        glUniform3f(uniformLocation, vector.x, vector.y, vector.z);
    }
}
