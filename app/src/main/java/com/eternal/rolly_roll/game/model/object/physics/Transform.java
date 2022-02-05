package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;

public class Transform {
    private static String TAG = "Transform";

    public Vector3D position;
    public Quaternion rotation;
    public Vector3D scale;

    public Transform() {
        position = new Vector3D();
        rotation = Quaternion.identity();
        scale = new Vector3D(1, 1, 1);
    }

    public float[] getTransformM() {
        float[] transformM = new float[16];
        Matrix.setIdentityM(transformM, 0);
        Matrix.translateM(transformM, 0, position.x, position.y, position.z);
        Quaternion.rotateM(transformM, 0, rotation);
        Matrix.scaleM(transformM, 0, scale.x, scale.y, scale.z);

        return transformM;
    }
}
