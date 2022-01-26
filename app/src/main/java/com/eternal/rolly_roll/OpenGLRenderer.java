package com.eternal.rolly_roll;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.eternal.rolly_roll.game.view.Camera;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;
import com.eternal.rolly_roll.game.view.shader.UIShader;
import com.eternal.rolly_roll.util.LoggerConfig;
import com.eternal.rolly_roll.util.ResourceManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class OpenGLRenderer implements GLSurfaceView.Renderer {
    private static String TAG = "OpenGL Renderer";

    private Context context;
    private RenderMiddleware renderer;
    private Camera camera;

    public OpenGLRenderer(Context context, RenderMiddleware renderer, Camera camera)  {
        this.context = context;
        this.renderer = renderer;
        this.camera = camera;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0f, 0f, 0f, 0f);
        glEnable(GL_DEPTH_TEST);

        SpriteShader spriteShader = new SpriteShader(context);
        renderer.setSpriteShader(spriteShader);

        renderer.textureMap.put(R.drawable.square, ResourceManager.loadTexture(context, R.drawable.square));
        renderer.textureMap.put(R.drawable.dice_texture, ResourceManager.loadTexture(context, R.drawable.dice_texture));
        renderer.textureMap.put(R.drawable.restart_icon, ResourceManager.loadTexture(context, R.drawable.restart_icon));
        if (LoggerConfig.TEXTURE_LOG) {
            Log.w(TAG, "quad texture : " + renderer.textureMap.get(R.drawable.square));
        }

        UIShader uiShader = new UIShader(context, "verdana.ttf");
        renderer.setUiShader(uiShader);

        renderer.setGameUI();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        camera.setAspect(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        renderer.Render();
    }
}
