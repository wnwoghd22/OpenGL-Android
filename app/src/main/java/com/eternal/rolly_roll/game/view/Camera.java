package com.eternal.rolly_roll.game.view;

import android.opengl.Matrix;

import com.eternal.rolly_roll.game.model.object.physics.Transform;

public class Camera {
    public Transform transform; //need to make logic which calculate projection matrix based on transform

    private float near;
    private float far;
    public final boolean isOrthogonal;
    private float[] projectionM;
    public float[] GetProjectionM() { return projectionM; }
    private float[] viewM;
    public float[] GetViewM() { return viewM; }

    private float[] position;

    //Orthogonal
    public Camera(int width, int height, float near, float far) {
        isOrthogonal = true;
        this.near = near; this.far = far;
        projectionM = new float[16];
        viewM = new float[16];

        if (height > width) { //portrait
            final float aspectRatio = (float)height / (float)width;
            Matrix.orthoM(projectionM, 0, -1f, 1f, -aspectRatio, aspectRatio, near, far);
        } else { //landscape
            final float aspectRatio = (float)width / (float)height;
            Matrix.orthoM(projectionM, 0, -aspectRatio, aspectRatio, -1f, 1f, near, far);
        }
    }
    //Perspective
    public Camera(int width, int height, float near, float far, float fov) {
        isOrthogonal = false;
        this.near = near; this.far = far;
        projectionM = new float[16];
        viewM = new float[16];

        final float aspectRatio = (float)width / (float)height;
        Matrix.perspectiveM(projectionM, 0, fov, aspectRatio, near, far);
    }
}
