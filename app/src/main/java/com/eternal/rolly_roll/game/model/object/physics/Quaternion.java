package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;
import android.util.Log;

import androidx.annotation.NonNull;

import com.eternal.rolly_roll.util.LoggerConfig;

import static java.lang.StrictMath.*;

public class Quaternion {
    private static final String TAG = "QUATERNION";

    private final float s, x, y, z;
    // input four elements
    public Quaternion(float s, float x, float y, float z) {
        this.s = s; this.x = x; this.y = y; this.z = z;

        if (LoggerConfig.QUATERNION_LOG) {
            Log.w(TAG, "" + this);
        }
    }
    // input rotation vector and rotation scalar
    public Quaternion(float scalarInDegree, Vector3D vector) {
        float alpha = (float)toRadians(scalarInDegree);
        Vector3D projective = vector.normalize(); // cosine value
        this.s = (float) cos(alpha / 2f);
        this.x = (float)(sin(alpha / 2f) * projective.x);
        this.y = (float)(sin(alpha / 2f) * projective.y);
        this.z = (float)(sin(alpha / 2f) * projective.z);

        if (LoggerConfig.QUATERNION_LOG) {
            Log.w(TAG, "" + this);
        }
    }
    // need to correct
    // input euler angles in degree and then convert to quaternion
    public Quaternion(Vector3D eulerAnglesInDegree) {
        float yaw = (float) toRadians(eulerAnglesInDegree.x);
        float pitch = (float) toRadians(eulerAnglesInDegree.y);
        float roll = (float) toRadians(eulerAnglesInDegree.z);

        float cy = (float) cos(yaw * 0.5);
        float sy = (float) sin(yaw * 0.5);
        float cp = (float) cos(pitch * 0.5);
        float sp = (float) sin(pitch * 0.5);
        float cr = (float) cos(roll * 0.5);
        float sr = (float) sin(roll * 0.5);

        this.s = cr * cp * cy + sr * sp * sy;
        this.x = sr * cp * cy - cr * sp * sy;
        this.y = cr * sp * cy + sr * cp * sy;
        this.z = cr * cp * sy - sr * sp * cy;

        if (LoggerConfig.QUATERNION_LOG) {
            Log.w(TAG, "" + this);
        }
    }
    public static Quaternion identity() {
        return new Quaternion(1f, 0f, 0f, 0f);
    }
    public Quaternion conjugate() {
        return new Quaternion(s, -x, -y, -z);
    }
    public float norm() {
        return (float) sqrt(s * s + x * x + y * y + z * z);
    }
    public Quaternion scale(float scalar) {
        if (LoggerConfig.QUATERNION_LOG) {
            Log.w(TAG, "scale : ");
        }
        return new Quaternion(scalar * s, scalar * x, scalar * y, scalar * z);
    }
    public Quaternion inverse() {
        if (LoggerConfig.QUATERNION_LOG) {
            Log.w(TAG, "inverse : ");
        }
        return conjugate().scale(1f / norm() * norm());
    }
    public Quaternion product(Quaternion q) {
        return new Quaternion(
            s * q.s - x * q.x - y * q.y - z * q.z,
            s * q.x + x * q.s + y * q.z - z * q.y,
            s * q.y - x * q.z + y * q.s + z * q.x,
            s * q.z + x * q.y - y * q.x + z * q.s
        );
    }
    public Quaternion rotate(Quaternion q) {
        Quaternion result =  q.product(this).product(q.inverse());
        if (LoggerConfig.QUATERNION_LOG) {
            Log.w(TAG, "\nrotate by : " + q + "\nresult : " + result);
        }
        return result;
    }

    // need to correct...? only euler to quaternion has problem, then...
    public float[] getRotateM() {
        return new float[] {
            1f - 2f * (y * y - z * z), 2f * (x * y - s * z), 2f * (x * z + s * y), 0f,
            2f * (x * y + s * z), 1f - 2f * (x * x + z * z), 2f * (y * z - s * x), 0f,
            2f * (x * z - s * y), 2f * (y * z + s * x), 1f - 2f * (x * x + y * y), 0f,
            0f, 0f, 0f, 1f
        };
    }

    public static void rotateM(float[] matrix, int mOffset, Vector3D eulerAngleInDegree) {
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, matrix, mOffset, new Quaternion(eulerAngleInDegree).getRotateM(), 0);
        for (int i = 0; i < 16; ++i) {
            matrix[i + mOffset] = result[i];
        }
    }
    public static void rotateM(float[] matrix, int mOffset, Quaternion q) {
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, matrix, mOffset, q.getRotateM(), 0);
        for (int i = 0; i < 16; ++i) {
            matrix[i + mOffset] = result[i];
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Quaternion(" + s + ", " + x + ", " + y + ", " + z + ")";
    }
}
