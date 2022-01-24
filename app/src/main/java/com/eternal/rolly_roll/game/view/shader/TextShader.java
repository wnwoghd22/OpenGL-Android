package com.eternal.rolly_roll.game.view.shader;

import android.content.Context;
import android.graphics.*;
import android.util.Log;

import androidx.annotation.Nullable;

import static android.opengl.GLUtils.*;
import static android.opengl.GLES20.*;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.view.ui.text.Character;
import com.eternal.rolly_roll.util.LoggerConfig;
import com.eternal.rolly_roll.util.ResourceManager;

import java.util.HashMap;

public class TextShader extends ShaderProgram {
    private static final String TAG = "Text Shader";

    public final int aPositionLocation;
    public final int aTexCoordLocation;
    public final int uColorLocation;
    public final int uMatrixLocation;
    public final int uTextureUnitLocation;

    private final String font;
    private final HashMap characterSet = new HashMap<java.lang.Character, Character>();
    public HashMap<java.lang.Character, Character> getCharacterSet() {
        return characterSet;
    }

    private float ascent;
    private float descent;

    public TextShader(Context context, String font) {
        super(context, R.raw.text_vertex, R.raw.text_fragment);

        aPositionLocation = glGetAttribLocation(ID, "aPos");
        aTexCoordLocation = glGetAttribLocation(ID, "aTexCoord");
        uColorLocation = glGetUniformLocation(ID, "uColor");
        uMatrixLocation = glGetUniformLocation(ID, "uMat");
        uTextureUnitLocation = glGetUniformLocation(ID, "uTextureUnit");

        this.font = font;
        Typeface tf = ResourceManager.getTypeFace(context, font);

        createCharacterSet(tf);
    }

    public void createCharacterSet(Typeface tf) {
        characterSet.clear();

        Paint textPaint = new Paint();
        Canvas canvas = new Canvas();
        Rect rect = new Rect();
        textPaint.setTypeface(tf);
        textPaint.setARGB(0xff, 0xff, 0x00, 0x00);
        textPaint.setTextSize(32f);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        this.ascent = fontMetrics.ascent;
        this.descent = fontMetrics.descent;

        if (LoggerConfig.UI_LOG) {
            Log.w(TAG, "ascent : " + this.ascent + ", descent : " + this.descent);
        }

        int charWidth = 0, charHeight = 0;
        int[] textureId = new int[1];

        for (char c = 0; c < 128; ++c) {
            textPaint.getTextBounds(c + "", 0, 1, rect);

            charWidth = rect.width();
            charHeight = rect.height();

            if (LoggerConfig.UI_LOG) {
                Log.w(TAG, "char : " + c + ", char width : " + charWidth + ", char height : " + charHeight);
            }

            if (charWidth * charHeight == 0) {
                continue;
            }

            Bitmap bitmap = Bitmap.createBitmap(charWidth, charHeight, Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            bitmap.eraseColor(0xFFFFFFFF);
            //canvas.drawText(c + "", charWidth  / 2, descent, textPaint);
            //canvas.drawText(c + "", 0, descent, textPaint);
            //canvas.drawText(c + "", 0, 0, textPaint);
            //canvas.drawText(c + "", 0, -descent, textPaint);
            canvas.drawText(c + "", -2, charHeight, textPaint);

            // generate glyph texture
            glGenTextures(1, textureId, 0);

            if (textureId[0] == 0) { // failed to create new texture
                if (LoggerConfig.UI_LOG) {
                    Log.w(TAG, "failed to create new texture");
                }
                bitmap.recycle();
                glDeleteTextures(1, textureId, 0);
                return;
            }
            glBindTexture(GL_TEXTURE_2D, textureId[0]);

            //Create Nearest Filtered Texture
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_LINEAR);

            //Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
            //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            //Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
            texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);

            Character element = new Character(
                charWidth,
                charHeight,
                textureId[0]
            );
            characterSet.put(c, element);

            glBindTexture(GL_TEXTURE_2D, 0);
            bitmap.recycle();
        }
    }
}
