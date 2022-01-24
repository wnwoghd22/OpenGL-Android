package com.eternal.rolly_roll.game.view.ui.text;

import com.eternal.rolly_roll.game.model.object.GameObject;

public class TextContainer extends GameObject {

    public TextContainer(String text) {
        this.shape = new Text(text);
    }

    @Override
    public void Update() {

    }
}
