package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;

public class Transform {
    private static String TAG = "Transform";

    public Vector3D position;
    public Quaternion rotation;
    public Vector3D scale;

    private float[] transformM;

    public Transform() {
        position = new Vector3D();
        rotation = Quaternion.identity();
        scale = new Vector3D(1, 1, 1);
        transformM = new float[16];
    }

    public float[] getTransformM() {
        // float[] transformM = new float[16];
        Matrix.setIdentityM(transformM, 0);
        Matrix.translateM(transformM, 0, position.x, position.y, position.z);
        Quaternion.rotateM(transformM, 0, rotation);
        Matrix.scaleM(transformM, 0, scale.x, scale.y, scale.z);

        return transformM;
    }

    public void getTransformM(float[] m) {
        Matrix.setIdentityM(m, 0);
        Matrix.translateM(m, 0, position.x, position.y, position.z);
        Quaternion.rotateM(m, 0, rotation);
        Matrix.scaleM(m, 0, scale.x, scale.y, scale.z);
    }
}
