package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;
import android.util.Log;

import com.eternal.rolly_roll.util.LoggerConfig;

import java.lang.invoke.MethodHandles;

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
        //rotate
        if (rotation.magnitude() > 0.001f) { //threshold?
            if (LoggerConfig.TOUCHLOG) {
                Log.w(TAG, "rotation : " + rotation);
            }
            //Matrix.setRotateEulerM(transformM, 0, rotation.x * 90f, rotation.y * 90f, rotation.z * 90f);
            Matrix.rotateM(transformM, 0,  90f, rotation.x, rotation.y, rotation.z);
        }
        

//        if (Math.abs(rotation.z) > 0.001f)
//            Matrix.rotateM(transformM, 0, rotation.z * 90f, 0f, 0f, 1f);
//        if (Math.abs(rotation.y) > 0.001f)
//            Matrix.rotateM(transformM, 0, rotation.y * 90f, 0f, 1f, 0f);
//        if (Math.abs(rotation.x) > 0.001f)
//            Matrix.rotateM(transformM, 0, rotation.x * 90f, 1f, 0f, 0f);

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
