package com.eternal.rolly_roll.game.object.object2d;

import com.eternal.rolly_roll.game.object.RenderableObject;
import com.eternal.rolly_roll.renderer.RenderMiddleware;

import static com.eternal.rolly_roll.util.Data.*;

public abstract class Quad extends RenderableObject {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    protected Quad() {
        super(QUAD_VERTICES);
    }

    @Override
    public void Update() {
        super.Update();
    }

    @Override
    public void Render(RenderMiddleware r) {

    }
}
