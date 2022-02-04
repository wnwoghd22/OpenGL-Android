package com.eternal.rolly_roll.game.view;

import android.content.Context;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.multiplyMM;

import android.util.Log;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.shader.ShaderProgram;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.game.view.shader.UIShader;
import com.eternal.rolly_roll.util.LoggerConfig;

import java.util.HashMap;

public class RenderMiddleware {
    private static final String TAG = "RenderMiddleware";

    private Context context;
    private Game game;
    private ShaderProgram shaderProgram;
    private SpriteShader spriteShader;
    private UIShader uiShader;
    // call inside onDraw
    public void SetShaderProgram(ShaderProgram shader) { this.shaderProgram = shader; }
    public SpriteShader getSpriteShader() { return spriteShader; }
    public void setSpriteShader(SpriteShader shader) { this.spriteShader = shader; }
    public UIShader getUiShader() {
        return uiShader;
    }
    public void setUiShader(UIShader uiShader) {
        this.uiShader = uiShader;
    }

    public final HashMap<Integer, Integer> textureMap = new HashMap<Integer, Integer>();

    public Camera camera;
    float[] viewProjectionMatrix = new float[16];
    public float[] getVP() { return viewProjectionMatrix; }
    float[] modelViewProjectionMatrix = new float[16];
    public float[] getMVP() { return modelViewProjectionMatrix; }

    public Vector3D directionalLightVector;

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

        uiShader.use();
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        //glBlendFunc(GL_ONE, GL_ONE);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        for (GameObject object : game.getUiObjects()) {
            object.Render(this);
        }
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
    }

    public void setGameUI() {
        if (this.game != null)
            this.game.setUIComponents();
    }
}
