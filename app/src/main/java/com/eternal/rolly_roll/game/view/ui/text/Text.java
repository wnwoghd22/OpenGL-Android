package com.eternal.rolly_roll.game.view.ui.text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import static android.opengl.GLES20.*;
import android.opengl.GLUtils;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.IRenderable;
import com.eternal.rolly_roll.game.view.RenderMiddleware;

import java.util.HashMap;

public class Text implements IRenderable {
    private String font;

    public Text() {

    }


    @Override
    public void Render(RenderMiddleware r) {

    }

    public static void getCharacterSet(HashMap<java.lang.Character, Character> characterSet, Typeface tf) {
        characterSet.clear();

        Paint textPaint = new Paint();
        textPaint.setTypeface(tf);

        textPaint.ascent();

        for (char c = 0; c <= java.lang.Character.MAX_VALUE; ++c) {
            Character element = new Character(

            );
            characterSet.put(c, element);
        }
    }
}
