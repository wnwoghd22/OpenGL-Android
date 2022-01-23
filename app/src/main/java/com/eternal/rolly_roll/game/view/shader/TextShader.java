package com.eternal.rolly_roll.game.view.shader;

import android.content.Context;
import android.graphics.Typeface;

import com.eternal.rolly_roll.game.view.ui.text.Character;
import com.eternal.rolly_roll.util.ResourceManager;

import java.util.HashMap;

import static com.eternal.rolly_roll.game.view.ui.text.Text.getCharacterSet;

public class TextShader extends ShaderProgram {
    private static final String TAG = "Text Shader";

    private final String font;
    private final HashMap<java.lang.Character, Character> CharacterSet = new HashMap<java.lang.Character, Character>();

    public TextShader(Context context, int vertexResourceId, int fragmentResourceId, String font) {
        super(context, vertexResourceId, fragmentResourceId);

        this.font = font;
        Typeface tf = ResourceManager.getTypeFace(context, font);

        getCharacterSet(CharacterSet, tf);
    }
}
