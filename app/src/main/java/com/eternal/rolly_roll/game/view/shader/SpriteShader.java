package com.eternal.rolly_roll.game.view.shader;

import android.content.Context;
import static android.opengl.GLES20.*;

import com.eternal.rolly_roll.R;

public class SpriteShader extends ShaderProgram {
    public final int aPositionLocation;
    public final int aTexCoordLocation;
    public final int aNormalLocation;

    public final int uColorLocation;
    public final int uMatrixLocation;
    public final int uModelLocation;
    public final int uTextureUnitLocation;
    public final int uDirectionalLightLocation;

    public SpriteShader(Context context) {
        super(context, R.raw.sprite_vertex, R.raw.sprite_fragment);

        aPositionLocation = glGetAttribLocation(ID, "aPos");
        aTexCoordLocation = glGetAttribLocation(ID, "aTexCoord");
        aNormalLocation = glGetAttribLocation(ID, "aNormal");

        uColorLocation = glGetUniformLocation(ID, "uColor");
        uMatrixLocation = glGetUniformLocation(ID, "uMat");
        uModelLocation = glGetUniformLocation(ID, "uModel");
        uTextureUnitLocation = glGetUniformLocation(ID, "uTextureUnit");
        uDirectionalLightLocation = glGetUniformLocation(ID, "uDirectionalLight");
    }
}
