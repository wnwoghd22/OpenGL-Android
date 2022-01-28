package com.eternal.rolly_roll.util;

public class Data {
    public static final float[] QUAD_VERTICES = {
            // pos              // tex
            -0.5f,  0.5f, 0.0f, 0.0f, 1.0f,
             0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,

            -0.5f,  0.5f, 0.0f, 0.0f, 1.0f,
             0.5f,  0.5f, 0.0f, 1.0f, 1.0f,
             0.5f, -0.5f, 0.0f, 1.0f, 0.0f
    };
    public static final float[] CUBE_VERTICES = {
            //pos               //tex
            //top
            -0.5f,  0.5f, -0.5f, 0f, 0f, // left - top
            -0.5f,  0.5f,  0.5f, 0f, 0.5f, // left - bottom
             0.5f,  0.5f,  0.5f, 0.33f, 0.5f, // right - bottom

            -0.5f,  0.5f, -0.5f, 0f, 0f, // left - top
             0.5f,  0.5f,  0.5f, 0.33f, 0.5f, // right - bottom
             0.5f,  0.5f, -0.5f, 0.33f, 0f, // right - top

            //front
            -0.5f,  0.5f,  0.5f, 0.34f, 0f,
            -0.5f, -0.5f,  0.5f, 0.34f, 0.5f,
             0.5f, -0.5f,  0.5f, 0.66f, 0.5f,

            -0.5f,  0.5f,  0.5f, 0.34f, 0f,
             0.5f, -0.5f,  0.5f, 0.66f, 0.5f,
             0.5f,  0.5f,  0.5f, 0.66f, 0f,

            //right
             0.5f,  0.5f,  0.5f, 0.67f, 0f,
             0.5f, -0.5f,  0.5f, 0.67f, 0.5f,
             0.5f, -0.5f, -0.5f, 1f, 0.5f,

             0.5f,  0.5f,  0.5f, 0.67f, 0f,
             0.5f, -0.5f, -0.5f, 1f, 0.5f,
             0.5f,  0.5f, -0.5f, 1f, 0f,

            //back
            -0.5f,  0.5f, -0.5f, 0f, 1f,
            -0.5f, -0.5f, -0.5f, 0f, 0.5f,
             0.5f, -0.5f, -0.5f, 0.33f, 0.5f,

            -0.5f,  0.5f, -0.5f, 0f, 1f,
            0.5f, -0.5f, -0.5f, 0.33f, 0.5f,
             0.5f,  0.5f, -0.5f, 0.33f, 1f,

            //left
            -0.5f,  0.5f,  0.5f, 0.34f, 1f,
            -0.5f, -0.5f,  0.5f, 0.34f, 0.5f,
            -0.5f, -0.5f, -0.5f, 0.66f, 0.5f,

            -0.5f,  0.5f,  0.5f, 0.34f, 1f,
            -0.5f, -0.5f, -0.5f, 0.66f, 0.5f,
            -0.5f,  0.5f, -0.5f, 0.66f, 1f,

            //bottom
            -0.5f, -0.5f, -0.5f, 0.7f, 1f,
            -0.5f, -0.5f,  0.5f, 0.7f, 0.5f,
             0.5f, -0.5f,  0.5f, 1f, 0.5f,

            -0.5f, -0.5f, -0.5f, 0.7f, 1f,
            0.5f, -0.5f,  0.5f, 1f, 0.5f,
             0.5f, -0.5f, -0.5f, 1f, 1f
    };

    public static final int BYTES_PER_FLOAT = 4;
}
