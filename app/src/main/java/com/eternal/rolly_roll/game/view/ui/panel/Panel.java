package com.eternal.rolly_roll.game.view.ui.panel;

import android.opengl.Matrix;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;

import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.QUAD_INDICES;
import static com.eternal.rolly_roll.util.Data.QUAD_VERTICES;

public class Panel extends Shape {

    public Panel() {
        super(QUAD_VERTICES, QUAD_INDICES);
        textureID = R.drawable.square;
    }

    @Override
    protected void bindData(RenderMiddleware r) {
        //set position
        setVertexAttribPointer(
                0,
                r.getUiShader().aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                r.getUiShader().aTexCoordLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
        //set color
        setUniformVec4(
                r.getUiShader().uColorLocation,
                color
        );

        //set texture
        // set the active texture unit to texture unit 0
        glActiveTexture(GL_TEXTURE0);
        // bind the texture to this unit
        glBindTexture(GL_TEXTURE_2D, r.textureMap.get(textureID));

        glUniform1i(r.getUiShader().uTextureUnitLocation, 0);

        float[] tempM = new float[16];

        Matrix.setIdentityM(tempM, 0);
        Matrix.translateM(tempM, 0, transform.position.x, transform.position.y, transform.position.z);

        Matrix.scaleM(tempM, 0, transform.scale.x, transform.scale.y, transform.scale.z);

        glUniformMatrix4fv(r.getUiShader().uMatrixLocation, 1, false, tempM, 0);
    }
}
