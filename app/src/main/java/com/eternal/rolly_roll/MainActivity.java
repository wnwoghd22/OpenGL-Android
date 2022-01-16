package com.eternal.rolly_roll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CancellationSignal;

import com.eternal.rolly_roll.game.Game;
import com.eternal.rolly_roll.renderer.RenderMiddleware;

import java.util.TimerTask;
import java.util.function.Consumer;

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
        r.camera = r.new Camera(
                this.getWindow().getAttributes().width,
                this.getWindow().getAttributes().height,
                1f,
                10f
        );

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