package com.eternal.rolly_roll.game.view;

import android.opengl.Matrix;

import com.eternal.rolly_roll.game.model.object.physics.Vector3D;

public class Camera {
    private Vector3D eyePosition;
    private Vector3D centerPosition;
    private Vector3D up = new Vector3D(0f, 1f, 0f);

    private float near, far;
    private float fov = 45f;
    public boolean isOrthogonal;
    private float orthoRange = 5f;
    private float[] projectionM;
    public float[] getProjectionM() { return projectionM; }
    private float[] viewM;
    public float[] getViewM() {
        Matrix.setLookAtM(
                viewM, 0,
                eyePosition.x, eyePosition.y, eyePosition.z,
                centerPosition.x, centerPosition.y, centerPosition.z,
                up.x, up.y, up.z
        );
        return viewM;
    }

    //Orthogonal
    public Camera(Vector3D eye, Vector3D center, int width, int height, float near, float far) {
        eyePosition = eye;
        centerPosition = center;

        isOrthogonal = true;
        this.near = near; this.far = far;
        projectionM = new float[16];
        viewM = new float[16];

        setAspect(width, height);
        getViewM();
    }
    //Perspective
    public Camera(Vector3D eye, Vector3D center, int width, int height, float near, float far, float fov) {
        eyePosition = eye;
        centerPosition = center;

        isOrthogonal = false;
        this.near = near; this.far = far;
        this.fov = fov;
        projectionM = new float[16];
        viewM = new float[16];

        setAspect(width, height);
        getViewM();
    }

    public void setAspect(float width, float height) {
        if (isOrthogonal) {
            if (height > width) { //portrait
                final float aspectRatio = (float)height / (float)width;
                Matrix.orthoM(
                    projectionM, 0,
                    -orthoRange, orthoRange,
                    -aspectRatio * orthoRange, aspectRatio * orthoRange,
                    near, far
                );
            } else { //landscape
                final float aspectRatio = (float)width / (float)height;
                Matrix.orthoM(
                    projectionM, 0,
                    -aspectRatio * orthoRange, aspectRatio * orthoRange,
                    -orthoRange, orthoRange,
                    near, far
                );
            }
        } else {
            final float aspectRatio = (float)width / (float)height;
            Matrix.perspectiveM(projectionM, 0, fov, aspectRatio, near, far);
        }
    }
}
