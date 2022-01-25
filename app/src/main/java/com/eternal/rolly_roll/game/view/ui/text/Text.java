package com.eternal.rolly_roll.game.view.ui.text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import static android.opengl.GLES20.*;
import static com.eternal.rolly_roll.util.Data.QUAD_VERTICES;

import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.physics.Quaternion;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.IRenderable;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.game.view.shader.TextShader;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.util.HashMap;

public class Text extends Shape {
    private static final String TAG = "Text";

    private String font;
    private String text;

    public Text(String text) {
        super(QUAD_VERTICES);
        this.text = text;
        this.color = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

        transform.position = Vector3D.zero();
        transform.rotation = Quaternion.identity();
    }


    @Override
    public void Render(RenderMiddleware r) {
        HashMap<java.lang.Character, Character> characterSet = r.getTextShader().getCharacterSet();

        if(LoggerConfig.UI_LOG) {
            Log.w(TAG, "trying to render text : " + text);
        }

        float[] tempM = new float[16];
        float posX = 0;

        for(char c : text.toCharArray()) {
            if (!characterSet.containsKey(c)) {
                posX += 0.1f;
                continue;
            }

            Character tempC = characterSet.get(c);

            textureID = tempC.getTextureId();
            int tempWidth = tempC.getCharWidth();

            if(LoggerConfig.UI_LOG) {
                Log.w(TAG, "trying to render character : " + c + ", texture id : " + textureID);
            }
            bindData(r.getTextShader());

            //set texture
            // set the active texture unit to texture unit 0
            glActiveTexture(GL_TEXTURE0);
            // bind the texture to this unit
            glBindTexture(GL_TEXTURE_2D, textureID);
            //glBindTexture(GL_TEXTURE_2D, r.textureMap.get(R.drawable.square));

            glUniform1i(r.getTextShader().uTextureUnitLocation, 0);

            Matrix.setIdentityM(tempM, 0);
            Matrix.translateM(tempM, 0, posX - 0.5f, 0f, 0f);
            posX += 0.1f;
            Matrix.scaleM(tempM, 0, ((float)tempWidth / 32) * 0.1f, 0.1f, 1f);

            glUniformMatrix4fv(r.getTextShader().uMatrixLocation, 1, false, tempM, 0);

            //glUniformMatrix4fv(r.getSpriteShader().uMatrixLocation, 1, false, transform.getTransformM(), 0);
            glDrawArrays(GL_TRIANGLES, 0, 6);

            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }

    protected void bindData(TextShader textShader) {
        //set position
        setVertexAttribPointer(
                0,
                textShader.aPositionLocation,
                POSITION_COMPONENT_COUNT,
                STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
                POSITION_COMPONENT_COUNT,
                textShader.aTexCoordLocation,
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE
        );
        //set color
        setUniformVec4(
                textShader.uColorLocation,
                color
        );
    }

    public void setText(String text) {
        this.text = text;
    }
}
