package com.eternal.rolly_roll.game.model.object.shape.shape2d;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.util.Data;

public class Quad extends Shape {
    public Quad() {
        // super(QUAD_VERTICES, QUAD_INDICES);
        super(Data.STATIC_MESH_QUAD);
        color = new float[] {1f, 1f, 1f, 1f};
        textureID = R.drawable.square;
    }
}
