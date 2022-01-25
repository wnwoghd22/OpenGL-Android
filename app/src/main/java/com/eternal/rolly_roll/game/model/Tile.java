package com.eternal.rolly_roll.game.model;

import android.util.Log;

import com.eternal.rolly_roll.R;
import com.eternal.rolly_roll.game.control.Axis;
import com.eternal.rolly_roll.game.model.object.GameObject;
import com.eternal.rolly_roll.game.model.object.physics.Quaternion;
import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.shape2d.Quad;
import com.eternal.rolly_roll.util.LoggerConfig;

public class Tile extends GameObject {
    private static final String TAG = "Tile";
    public static enum Color {
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
    public boolean isColored() {
        return currentColor != Color.GRAY;
    }
    private final float[] COLOR_GRAY = { 0.3f, 0.3f, 0.3f, 1f };
    // for challenge mode
    private int requireNum = 0;
    private void setRequireNum(int require) { requireNum = require; }

    private Tile up = null;
    private Tile right = null;
    private Tile left = null;
    private Tile down = null;

    public Tile(Vector3D position) {
        this.shape = new Quad();

        shape.transform.position = position;
        shape.transform.rotation = new Quaternion(90f, new Vector3D(-1f, 0f, 0f));

        shape.color = COLOR_GRAY;

        currentColor = Color.GRAY;
    }

    @Override
    public void Update() {

    }

    public int checkAdjacent() {
        checked = true;
        int result = 1;
        if (up != null && !up.checked && up.currentColor == this.currentColor) {
            if (LoggerConfig.LEVEL_LOG) {
                Log.w(TAG, "check up");
            }
            result += up.checkAdjacent();
        }
        if (right != null && !right.checked && right.currentColor == this.currentColor) {
            if (LoggerConfig.LEVEL_LOG) {
                Log.w(TAG, "check right");
            }
            result += right.checkAdjacent();
        }
        if (left != null && !left.checked && left.currentColor == this.currentColor)
            result += left.checkAdjacent();
        if (down != null && !down.checked && down.currentColor == this.currentColor)
            result += down.checkAdjacent();
        return result;
    }
    public void uncheck() {
        checked = false;
        if (up != null && up.checked)
            up.uncheck();
        if (right != null && right.checked)
            right.uncheck();
        if (left != null && left.checked)
            left.uncheck();
        if (down != null && down.checked)
            down.uncheck();
    }
    public int clear() {
        checked = false;
        currentColor = Color.GRAY;
        shape.color = COLOR_GRAY;
        int result = requireNum;
        int compare;
        if (up != null && up.checked) {
            compare = up.clear();
            result = result < compare ? compare : result;
        }
        if (right != null && right.checked) {
            compare = right.clear();
            result = result < compare ? compare : result;
        }
        if (left != null && left.checked){
            compare = left.clear();
            result = result < compare ? compare : result;
        }
        if (down != null && down.checked){
            compare = down.clear();
            result = result < compare ? compare : result;
        }
        return result;
    }

    public void bindUp(Tile tile) { this.up = tile; }
    public void bindRight(Tile tile) { this.right = tile; }
    public void bindLeft(Tile tile) { this.left = tile; }
    public void bindDown(Tile tile) { this.down = tile; }

    public void setColor(Axis down) {
        switch (down) {
            case F: // blue
                currentColor = Color.BLUE;
                shape.color = new float[] { 0f, 0f, 1f, 1f };
                break;
            case B: // green
                currentColor = Color.GREEN;
                shape.color = new float[] { 0f, 1f, 0f, 1f };
                break;
            case R: // orange
                currentColor = Color.ORANGE;
                shape.color = new float[] { 1f, 0.75f, 0f, 1f };
                break;
            case L: //red
                currentColor = Color.RED;
                shape.color = new float[] { 1f, 0f, 0f, 1f };
                break;
            case U: // white
                currentColor = Color.WHITE;
                shape.color = new float[] { 1f, 1f, 1f, 1f };
                break;
            case D: // yellow
                currentColor = Color.YELLOW;
                shape.color = new float[] { 1f, 1f, 0f, 1f };
                break;
        }
    }
}
