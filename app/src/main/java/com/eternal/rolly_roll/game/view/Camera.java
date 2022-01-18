package com.eternal.rolly_roll.game.view;

import android.opengl.Matrix;

import com.eternal.rolly_roll.game.model.object.physics.Transform;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;

import java.util.Vector;

public class Camera {
    private Vector3D eyePosition;
    private Vector3D centerPosition;
    private Vector3D up = new Vector3D(0f, 1f, 0f);

    private float near, far;
    private float fov = 45f;
    public boolean isOrthogonal;
    private float[] projectionM;
    public float[] getProjectionM() { return projectionM; }
    private float[] viewM;
    public float[] getViewM() {
        float[] viewM = new float[16];
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

        if (height > width) { //portrait
            final float aspectRatio = (float)height / (float)width;
            Matrix.orthoM(projectionM, 0, -1f, 1f, -aspectRatio, aspectRatio, near, far);
        } else { //landscape
            final float aspectRatio = (float)width / (float)height;
            Matrix.orthoM(projectionM, 0, -aspectRatio, aspectRatio, -1f, 1f, near, far);
        }
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

        final float aspectRatio = (float)width / (float)height;
        Matrix.perspectiveM(projectionM, 0, fov, aspectRatio, near, far);
    }
}
