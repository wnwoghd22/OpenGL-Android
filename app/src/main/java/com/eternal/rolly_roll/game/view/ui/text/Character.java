package com.eternal.rolly_roll.game.view.ui.text;

public class Character {
    private float charWidth;
    private float charHeight;
    private int textureId;

    public Character(float charWidth, float charHeight, int textureId) {
        this.charWidth = charWidth;
        this.charHeight = charHeight;
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }
    public float getCharWidth() { return charWidth; }
}
