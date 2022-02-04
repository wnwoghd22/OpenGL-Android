package com.eternal.rolly_roll.util;

public class Data {
    public static final float[] QUAD_VERTICES = {
        // pos              // tex      //normal
        -0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 0f, 0f, 1f, // top left
        -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0f, 0f, 1f, // bottom left
         0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0f, 0f, 1f, // bottom right
         0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 0f, 0f, 1f  // top right
    };
    public static final byte[] QUAD_INDICES = {
        0, 1, 3,
        1, 2, 3
    };

    public static final float[] CUBE_VERTICES = {
        //pos                //tex        //normal
        //top
        -0.5f,  0.5f, -0.5f,    0f,   0f, 0f, 1f, 0f, // left - top
        -0.5f,  0.5f,  0.5f,    0f, 0.5f, 0f, 1f, 0f, // left - bottom
         0.5f,  0.5f,  0.5f, 0.33f, 0.5f, 0f, 1f, 0f, // right - bottom
         0.5f,  0.5f, -0.5f, 0.33f,   0f, 0f, 1f, 0f, // right - top

        //front
        -0.5f,  0.5f,  0.5f, 0.34f,   0f, 0f, 0f, 1f,
        -0.5f, -0.5f,  0.5f, 0.34f, 0.5f, 0f, 0f, 1f,
         0.5f, -0.5f,  0.5f, 0.66f, 0.5f, 0f, 0f, 1f,
         0.5f,  0.5f,  0.5f, 0.66f,   0f, 0f, 0f, 1f,

        //right
        0.5f,  0.5f,  0.5f, 0.67f,   0f, 1f, 0f, 0f,
        0.5f, -0.5f,  0.5f, 0.67f, 0.5f, 1f, 0f, 0f,
        0.5f, -0.5f, -0.5f,    1f, 0.5f, 1f, 0f, 0f,
        0.5f,  0.5f, -0.5f,    1f,   0f, 1f, 0f, 0f,

        //back
        -0.5f,  0.5f, -0.5f,    0f,   1f, 0f, 0f, -1f,
        -0.5f, -0.5f, -0.5f,    0f, 0.5f, 0f, 0f, -1f,
         0.5f, -0.5f, -0.5f, 0.33f, 0.5f, 0f, 0f, -1f,
         0.5f,  0.5f, -0.5f, 0.33f,   1f, 0f, 0f, -1f,

        //left
        -0.5f,  0.5f,  0.5f, 0.34f,   1f, -1f, 0f, 0f,
        -0.5f, -0.5f,  0.5f, 0.34f, 0.5f, -1f, 0f, 0f,
        -0.5f, -0.5f, -0.5f, 0.66f, 0.5f, -1f, 0f, 0f,
        -0.5f,  0.5f, -0.5f, 0.66f,   1f, -1f, 0f, 0f,

        //bottom
        -0.5f, -0.5f, -0.5f, 0.7f,   1f, 0f, -1f, 0f,
        -0.5f, -0.5f,  0.5f, 0.7f, 0.5f, 0f, -1f, 0f,
         0.5f, -0.5f,  0.5f,   1f, 0.5f, 0f, -1f, 0f,
         0.5f, -0.5f, -0.5f,   1f,   1f, 0f, -1f, 0f
    };
    public static final byte[] CUBE_INDICES = {
        0, 1, 3,
        1, 2, 3,

        4, 5, 7,
        5, 6, 7,

        8, 9, 11,
        9, 10, 11,

        12, 13, 15,
        13, 14, 15,

        16, 17, 19,
        17, 18, 19,

        20, 21, 23,
        21, 22, 23
    };

    public static final int BYTES_PER_FLOAT = 4;
}
