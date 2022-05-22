package com.eternal.rolly_roll.game.model.object.shape.shape3d;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.util.Data;

public class Cube extends Shape {
    public Cube() {
        // super(CUBE_VERTICES, CUBE_INDICES);
        super(Data.STATIC_MESH_CUBE);
        color = new float[] {1f, 1f, 1f, 1f};
        textureID = R.drawable.square;
    }
}
