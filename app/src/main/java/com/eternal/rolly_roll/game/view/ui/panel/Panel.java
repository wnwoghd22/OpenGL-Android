package com.eternal.rolly_roll.game.view.ui.panel;

import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.UIShader;
import com.eternal.rolly_roll.util.LoggerConfig;

import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.QUAD_VERTICES;

public class Panel extends Shape {

    protected Panel() {
        super(QUAD_VERTICES);
        textureID = R.drawable.square;
    }

    @Override
    public void Render(RenderMiddleware r) {
        bindData(r.getUiShader());

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

        glDrawArrays(GL_TRIANGLES, 0, 6);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    protected void bindData(UIShader uiShader) {
        //set position
        setVertexAttribPointer(
                0,
                uiShader.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                uiShader.aTexCoordLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
        //set color
        setUniformVec4(
                uiShader.uColorLocation,
                color
        );
    }
}
