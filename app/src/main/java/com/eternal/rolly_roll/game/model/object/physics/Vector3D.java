package com.eternal.rolly_roll.game.model.object.physics;

import androidx.annotation.NonNull;

public class Vector3D {

    public float x;
    public float y;
    public float z;

    public Vector3D() { x = 0f; y = 0f; z = 0f; }
    public Vector3D(float x) { this.x = x; y = 0f; z = 0f; }
    public Vector3D(float x, float y) { this.x = x; this.y = y; z = 0f; }
    public Vector3D(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }

    public static Vector3D zero() {
        return new Vector3D();
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D normalize() {
        x /= magnitude();
        y /= magnitude();
        z /= magnitude();

        return this;
    }

    public Vector3D scale(float scalar) {
        return new Vector3D(x * scalar, y *scalar, z * scalar);
    }

    @NonNull
    @Override
    public String toString() {
        return "Vector3D(" + x + ", " + y + ", " + z + ")";
    }
}
