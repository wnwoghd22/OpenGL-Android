package com.eternal.rolly_roll.game.view.shader;

import android.content.Context;
import android.graphics.*;

import com.eternal.rolly_roll.game.view.ui.text.Character;
import com.eternal.rolly_roll.util.ResourceManager;

import java.util.HashMap;

public class TextShader extends ShaderProgram {
    private static final String TAG = "Text Shader";

    private final String font;
    private final HashMap<java.lang.Character, Character> characterSet = new HashMap<java.lang.Character, Character>();

    private float ascent;
    private float descent;

    public TextShader(Context context, int vertexResourceId, int fragmentResourceId, String font) {
        super(context, vertexResourceId, fragmentResourceId);

        this.font = font;
        Typeface tf = ResourceManager.getTypeFace(context, font);

        getCharacterSet(tf);
    }

    public void getCharacterSet(Typeface tf) {
        characterSet.clear();

        Paint textPaint = new Paint();
        Canvas canvas = new Canvas();
        Rect rect = new Rect();
        textPaint.setTypeface(tf);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        this.ascent = fontMetrics.ascent;
        this.descent = fontMetrics.descent;

        int charWidth, charHeight;

        for (char c = 0; c <= java.lang.Character.MAX_VALUE; ++c) {
            textPaint.getTextBounds(c + "", 0, 1, rect);

            charWidth = rect.width();
            charHeight = rect.height();

            Bitmap bitmap = Bitmap.createBitmap(charWidth, charHeight, Bitmap.Config.ARGB_4444);
            canvas.setBitmap(bitmap);
            canvas.drawText(c + "", 0f, ascent, textPaint);

            //

            Character element = new Character(

            );
            characterSet.put(c, element);
        }
    }
}
