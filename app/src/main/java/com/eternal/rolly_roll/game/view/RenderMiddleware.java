package com.eternal.rolly_roll.game.view;

import android.content.Context;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.multiplyMM;

import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.game.view.shader.TextShader;
import com.eternal.rolly_roll.util.Data;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.nio.*;
import java.util.Collection;
import java.util.HashMap;

public class RenderMiddleware {
    private static final String TAG = "RenderMiddleware";

    private Context context;
    private Game game;
    private ShaderProgram shaderProgram;
    private SpriteShader spriteShader;
    private TextShader textShader;
    // call inside onDraw
    public void SetShaderProgram(ShaderProgram shader) { this.shaderProgram = shader; }
    public SpriteShader getSpriteShader() { return spriteShader; }
    public void setSpriteShader(SpriteShader shader) { this.spriteShader = shader; }
    public TextShader getTextShader() {
        return textShader;
    }
    public void setTextShader(TextShader textShader) {
        this.textShader = textShader;
    }

    public final HashMap<Integer, Integer> textureMap = new HashMap<Integer, Integer>();
    public final HashMap<String, Typeface> fontMap = new HashMap<String, Typeface>();

    public Camera camera;
    float[] viewProjectionMatrix = new float[16];
    public float[] getVP() { return viewProjectionMatrix; }
    float[] modelViewProjectionMatrix = new float[16];
    public float[] getMVP() { return modelViewProjectionMatrix; }

    public RenderMiddleware(Context context, Game game) {
        this.context = context;
        this.game = game;
    }

    // call this method every frame in OnDraw() of interface Renderer
    public void Render() {
        multiplyMM(viewProjectionMatrix, 0, camera.getProjectionM(), 0, camera.getViewM(), 0);

        if (LoggerConfig.ON) {
            Log.v(TAG, "onDraw Render(Game)");
        }

        // sprite layer
        spriteShader.use();
        for (GameObject object : game.getObjects()) {
            object.Render(this);
        }

        // post processing layer

        // ui layer
    }
}
