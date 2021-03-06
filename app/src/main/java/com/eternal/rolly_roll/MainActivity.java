package com.eternal.rolly_roll;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.game.control.TouchHandler;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.view.Camera;
import com.eternal.rolly_roll.game.view.RenderMiddleware;

public class MainActivity extends AppCompatActivity {

    private OpenGLView openGLView;
    private Game game;
    private TouchHandler touchHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(this);
        game.Init();
        touchHandler = new TouchHandler(this, game);

        RenderMiddleware r = new RenderMiddleware(this, game);
        Camera camera = new Camera(
                new Vector3D(10f, 5f, 10f),
                new Vector3D(0f, 0f, 0f),
                this.getWindow().getAttributes().width,
                this.getWindow().getAttributes().height,
                1f,
                5000f
        );
        r.camera = camera;

        openGLView = (OpenGLView) this.<android.view.View>findViewById(R.id.openGLView);
        openGLView.setRenderer(new OpenGLRenderer(this, r, camera));

        openGLView.setOnTouchListener(touchHandler);
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