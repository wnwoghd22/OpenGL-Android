package com.eternal.rolly_roll.game.model;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Quaternion;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape2d.Quad;

public class Tile extends GameObject {
    enum Color {
        GRAY,
        WHITE,
        BLUE,
        ORANGE,
        GREEN,
        RED,
        YELLOW,
    }

    private boolean checked = false;
    private Color currentColor;

    private Tile up = null;
    private Tile right = null;
    private Tile left = null;
    private Tile down = null;

    public Tile(Vector3D position) {
        this.shape = new Quad();

        shape.transform.position = position;
        shape.transform.rotation = new Quaternion(90f, new Vector3D(-1f, 0f, 0f));

        shape.color = new float[] { 0.3f, 0.3f, 0.3f, 1f };

        currentColor = Color.GRAY;
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
