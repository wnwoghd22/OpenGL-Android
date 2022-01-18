package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.util.LoggerConfig;

public class Transform {
    private static String TAG = "Transform";

    public Vector3D position;
    public Vector3D rotation;
    public Vector3D scale;

    public Transform() {
        position = new Vector3D();
        rotation = new Vector3D();
        scale = new Vector3D(1, 1, 1);
    }

    public float[] getTransformM() {
        float[] transformM = new float[16];
        Matrix.setIdentityM(transformM, 0);
        Matrix.translateM(transformM, 0, position.x, position.y, position.z);
        //rotate?
        Matrix.rotateM(transformM, 0, 90f, rotation.x, rotation.y, rotation.z);

        Matrix.scaleM(transformM, 0, scale.x, scale.y, scale.z);

        if (LoggerConfig.ON) {
            Log.w(TAG, "transform matrix : \n" +
                    transformM[0] + " " + transformM[1] + " "  + transformM[2] + " " + transformM[3] + "\n"
                    + transformM[4] + " " + transformM[5] + " " + transformM[6] + " " + transformM[7] + "\n"
                    + transformM[8] + " " + transformM[9] + " " + transformM[10] + " " + transformM[11] + "\n"
                    + transformM[12] + " " + transformM[13] + " " + transformM[14] + " " + transformM[15]
            );
        }

        return transformM;
    }
}
