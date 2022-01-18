package com.eternal.rolly_roll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.Camera;
import com.eternal.rolly_roll.game.view.RenderMiddleware;

public class MainActivity extends AppCompatActivity {

    private OpenGLView openGLView;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(this);
        game.Init();

        RenderMiddleware r = new RenderMiddleware(this, game);
        Camera camera = new Camera(
                new Vector3D(10f, 5f, -10f),
                new Vector3D(0f, 0f, 0f),
                this.getWindow().getAttributes().width,
                this.getWindow().getAttributes().height,
                1f,
                10f
        );
        r.camera = camera;

        openGLView = (OpenGLView) this.<android.view.View>findViewById(R.id.openGLView);
        openGLView.setRenderer(new OpenGLRenderer(this, r));
    }

    @Override
    protected void onResume() {
        super.onResume();
        game.onResume();
        openGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.onPause();
        openGLView.onPause();
    }
}