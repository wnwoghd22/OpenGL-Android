package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

import static java.lang.StrictMath.*;

public class Quaternion {
    private final float s, x, y, z;
    // input four elements
    public Quaternion(float scalar, float x, float y, float z) {
        this.s = scalar; this.x = x; this.y = y; this.z = z;
    }
    // input rotation vector and rotation scalar
    public Quaternion(float scalar, Vector3D vector) {
        this.s = scalar; this.x = vector.x; this.y = vector.y; this.z = vector.z;
    }
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
    }

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
        Matrix.multiplyMM(result, 0, matrix, 0, new Quaternion(eulerAngleInDegree).getRotateM(), 0);
        for (int i = 0; i < 16; ++i) {
            matrix[i] = result[i];
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Quaternion(" + s + ", " + x + ", " + y + ", " + z + ")";
    }
}
