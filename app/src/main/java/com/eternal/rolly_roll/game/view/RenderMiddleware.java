package com.eternal.rolly_roll.game.view;

import android.content.Context;

import static android.opengl.GLES20.*;

import android.graphics.Shader;
import android.util.Log;

import com.eternal.rolly_roll.game.Game;
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
    private FloatBuffer quad;

    public RenderMiddleware(Context context, Game game) {
        this.context = context;
        this.game = game;
        //this.shaderProgram = new ShaderProgram(context, R.raw.sprite_vertex, R.raw.sprite_fragment);
        quad = ByteBuffer.allocateDirect(
                Data.QUAD_VERTICES.length * 4
        ).order(ByteOrder.nativeOrder()).asFloatBuffer();
        quad.put(Data.QUAD_VERTICES);

        if(LoggerConfig.ON) {
            Log.v(TAG, "allocate buffer quad : " + quad.toString());
        }

    }

    // call this method every frame in OnDraw() of interface Renderer
    public void Render() {
        if (LoggerConfig.ON) {
            Log.v(TAG, "onDraw Render(Game)");
        }
        game.Render(this);
    }

    public void RenderQuad() {
        if (LoggerConfig.ON) {
            Log.v(TAG, "start draw quad");
        }
        glUseProgram(shaderProgram.ID);

        final int aPositionLocation = glGetAttribLocation(shaderProgram.ID, "aPos");
        quad.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 5 * 4, quad);

        final int aColorLocation = glGetAttribLocation(shaderProgram.ID, "aColor");
        quad.position(2);
        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 5 * 4, quad);

        glEnableVertexAttribArray(aPositionLocation);
        glEnableVertexAttribArray(aColorLocation);

        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
    public void RenderQuad(int i) {
        if (LoggerConfig.ON) {
            Log.w(TAG, "start draw quad : frame" + i);
        }
        glUseProgram(shaderProgram.ID);

        final int aPositionLocation = glGetAttribLocation(shaderProgram.ID, "aPos");
        quad.position(0);
        glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 5 * 4, quad);

        final int aColorLocation = glGetAttribLocation(shaderProgram.ID, "aColor");
        quad.position(2);
        glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 5 * 4, quad);

        glEnableVertexAttribArray(aPositionLocation);
        glEnableVertexAttribArray(aColorLocation);

        glDrawArrays(GL_TRIANGLES, 0, 6);
    }

    public void RenderSquare() {

    }
}
