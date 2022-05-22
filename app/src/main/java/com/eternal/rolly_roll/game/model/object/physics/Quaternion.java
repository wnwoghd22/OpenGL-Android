package com.eternal.rolly_roll.game.model.object.physics;

import android.opengl.Matrix;

import androidx.annotation.NonNull;

import static java.lang.StrictMath.acos;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;
import static java.lang.StrictMath.toDegrees;
import static java.lang.StrictMath.toRadians;

public class Quaternion {
    private static final String TAG = "QUATERNION";

    private final float s, x, y, z;

    private static float[] tempM = new float[16]; // is it bad for multi-thread?
    private static float[] tempRotateM = new float[16];

    // input four elements
    public Quaternion(float s, float x, float y, float z) {
        this.s = s; this.x = x; this.y = y; this.z = z;
    }
    public Quaternion(Quaternion q) {
        this.s = q.s; this.x = q.x; this.y = q.y; this.z = q.z;
    }
    // input rotation vector and rotation scalar
    public Quaternion(float scalarInDegree, Vector3D vector) {
        float alpha = (float)toRadians(scalarInDegree);
        Vector3D projective = vector.normalize(); // cosine value
        this.s = (float) cos(alpha / 2f);
        this.x = (float)(sin(alpha / 2f) * projective.x);
        this.y = (float)(sin(alpha / 2f) * projective.y);
        this.z = (float)(sin(alpha / 2f) * projective.z);
    }
    // input euler angles in degree and then convert to quaternion
    public Quaternion(Vector3D eulerAnglesInDegree) {
        float yaw = (float) toRadians(eulerAnglesInDegree.z);
        float pitch = (float) toRadians(eulerAnglesInDegree.y);
        float roll = (float) toRadians(eulerAnglesInDegree.x);

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
        return new Quaternion(scalar * s, scalar * x, scalar * y, scalar * z);
    }
    public Quaternion inverse() {
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
    public Quaternion power(float alpha) {
        return new VersorForm(this).power(alpha).toQuaternion();
    }
    public static Quaternion slerp(Quaternion origin, Quaternion target, float alpha) {
        return target.product(origin.inverse()).power(alpha).product(origin);
    }

    public float[] getRotateM() {
        return new float[] {
            1f - 2f * (y * y + z * z), 2f * (x * y - s * z), 2f * (x * z + s * y), 0f,
            2f * (x * y + s * z), 1f - 2f * (x * x + z * z), 2f * (y * z - s * x), 0f,
            2f * (x * z - s * y), 2f * (y * z + s * x), 1f - 2f * (x * x + y * y), 0f,
            0f, 0f, 0f, 1f
        };
    }
    public void getRotateM(float[] m) {
        m[0] = 1f - 2f * (y * y + z * z); m[1] = 2f * (x * y - s * z); m[2] = 2f * (x * z + s * y); m[3] = 0f;
        m[4] = 2f * (x * y + s * z); m[5] = 1f - 2f * (x * x + z * z); m[6] = 2f * (y * z - s * x); m[7] = 0f;
        m[8] = 2f * (x * z - s * y); m[9] = 2f * (y * z + s * x); m[10] = 1f - 2f * (x * x + y * y); m[11] = 0f;
        m[12] = 0f; m[13] = 0f; m[14] = 0f; m[15] = 1f;
    }

    public static void rotateM(float[] matrix, int mOffset, Quaternion q) {
        q.getRotateM(tempRotateM);
        Matrix.multiplyMM(tempM, 0, matrix, mOffset, tempRotateM, 0);
        for (int i = 0; i < 16; ++i)
            matrix[i + mOffset] = tempM[i];
    }

    @NonNull
    @Override
    public String toString() {
        return "Quaternion(" + s + ", " + x + ", " + y + ", " + z + ")";
    }

    public static class VersorForm {
        private final float tensor;
        private final Vector3D versor;
        public VersorForm(float tensor, Vector3D versor) {
            this.tensor = tensor; this.versor = versor;
        }
        public VersorForm(Quaternion q) {
            this.tensor = (float) acos(q.s) * 2f;
            this.versor = tensor < 0.0001f ? new Vector3D(0f, 0f, 0f) : new Vector3D(q.x, q.y, q.z).scale(1f / (float) sin(tensor / 2f));
        }
        public VersorForm power(float alpha) {
            return new VersorForm(tensor * alpha, versor);
        }
        public Quaternion toQuaternion() {
            float tensorDegree = (float) toDegrees(tensor);
            return new Quaternion(tensorDegree, versor);
        }

        @NonNull
        @Override
        public String toString() {
            return "Quaternion VersorForm(" + tensor + ", " + versor + ")";
        }
    }
}
