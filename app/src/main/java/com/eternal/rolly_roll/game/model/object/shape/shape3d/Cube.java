package com.eternal.rolly_roll.game.model.object.shape.shape3d;

import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;

import static com.eternal.rolly_roll.util.Data.CUBE_VERTICES;

public class Cube extends Shape {
    public Cube() {

        super(CUBE_VERTICES);
    }

}
