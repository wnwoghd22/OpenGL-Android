package com.eternal.rolly_roll.game.view.ui.text;

public class Character {
    private int charWidth;
    private int charHeight;
    private int textureId;

    public Character(int charWidth, int charHeight, int textureId) {
        this.charWidth = charWidth;
        this.charHeight = charHeight;
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }
}
