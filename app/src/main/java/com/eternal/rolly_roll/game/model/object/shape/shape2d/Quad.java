package com.eternal.rolly_roll.game.model.object.shape.shape2d;

import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;
import com.eternal.rolly_roll.game.view.shader.SpriteShader;

import static com.eternal.rolly_roll.util.Data.*;

public class Quad extends Shape {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    public Quad() {
        super(QUAD_VERTICES);
    }

    @Override
    public void Render(RenderMiddleware r) {

    }

    @Override
    protected void bindData(SpriteShader spriteShader) {
        //set position
        setVertexAttribPointer(
            0,
            POSITION_COMPONENT_COUNT,
            spriteShader.aPositionLocation,
            STRIDE
        );

        //set texture coordinates
        setVertexAttribPointer(
            POSITION_COMPONENT_COUNT,
            spriteShader.aTexCoordLocation,
            TEXTURE_COORDINATES_COMPONENT_COUNT,
            STRIDE
        );

        setUniformVec4(
            spriteShader.uColorLocation,
            color
        );
    }
}
