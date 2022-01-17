package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;

public class Transform {
    public Vector3D position;
    public Vector3D rotation;
    public Vector3D scale;

    public Transform() {
        position = new Vector3D();
        rotation = new Vector3D();
        scale = new Vector3D();
    }

    public float[] getTransformM() {
        float[] transformM = new float[16];
        Matrix.setIdentityM(transformM, 0);
        Matrix.translateM(transformM, 0, position.x, position.y, position.z);
        //rotate?

        Matrix.scaleM(transformM, 0, scale.x, scale.y, scale.z);

        return transformM;
    }
}
