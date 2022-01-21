package com.eternal.rolly_roll.game.model;

import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Transform;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape3d.Cube;

public class TestCube extends GameObject {

    public TestCube(Vector3D position, Vector3D rotation, Vector3D scale) {
        this.shape = new Cube();
        shape.transform.position = position;
        shape.transform.scale = scale;
    }

    @Override
    public void Update() {

    }
}
