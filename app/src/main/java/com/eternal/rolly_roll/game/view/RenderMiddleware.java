package com.eternal.rolly_roll.game.view;

import android.content.Context;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.multiplyMM;

import android.graphics.Shader;
import android.util.Log;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.util.Data;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.nio.*;

public class RenderMiddleware {
    private static final String TAG = "RenderMiddleware";

    private Context context;
    private Game game;
    private ShaderProgram shaderProgram;
    private SpriteShader spriteShader;
    // call inside onDraw
    public void SetShaderProgram(ShaderProgram shader) { this.shaderProgram = shader; }
    public void setSpriteShader(SpriteShader shader) { this.spriteShader = shader; }
    public SpriteShader getSpriteShader() { return spriteShader; }

    public Camera camera;
    float[] viewProjectionMatrix = new float[16];

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

        for (GameObject object : game.getObjects()) {
            object.Render(this);
        }
    }
}
