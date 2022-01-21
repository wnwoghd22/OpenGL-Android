package com.eternal.rolly_roll.game.model;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape2d.Quad;

public class Tile extends GameObject {

    private boolean checked = false;

    private Tile up = null;
    private Tile right = null;
    private Tile left = null;
    private Tile down = null;

    private int currentColor = 0;

    public Tile(Vector3D position) {
        this.shape = new Quad();

        shape.transform.position = position;
        shape.transform.rotation.x = -1f;
        shape.color = new float[] { 0.3f, 0.3f, 0.3f, 1f };
    }

    @Override
    public void Update() {

    }

    public int checkAdjacent() {
        checked = true;
        int result = 1;
        if (up != null && !up.checked && up.currentColor == this.currentColor)
            result += up.checkAdjacent();
        if (right != null && !right.checked && right.currentColor == this.currentColor)
            result += right.checkAdjacent();
        if (left != null && !left.checked && left.currentColor == this.currentColor)
            result += left.checkAdjacent();
        if (down != null && !down.checked && down.currentColor == this.currentColor)
            result += down.checkAdjacent();
        return result;
    }

    public void bindUp(Tile tile) { this.up = tile; }
    public void bindRight(Tile tile) { this.right = tile; }
    public void bindLeft(Tile tile) { this.left = tile; }
    public void bindDown(Tile tile) { this.down = tile; }
}
