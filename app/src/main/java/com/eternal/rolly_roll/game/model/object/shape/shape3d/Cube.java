package com.eternal.rolly_roll.game.model.object.shape.shape3d;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.Shape;

import static com.eternal.rolly_roll.util.Data.CUBE_INDICES;
import static com.eternal.rolly_roll.util.Data.CUBE_VERTICES;

public class Cube extends Shape {
    public Cube() {
        // super(CUBE_VERTICES, CUBE_INDICES);
        super(Shape.STATIC_MESH_CUBE);
        color = new float[] {1f, 1f, 1f, 1f};
        textureID = R.drawable.square;
    }
}
