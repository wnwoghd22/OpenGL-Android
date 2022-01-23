package com.eternal.rolly_roll.game.view.ui.text;

import com.eternal.rolly_roll.game.model.object.GameObject;

public class TextContainer extends GameObject {
    private Text text;

    public TextContainer(String text) {
        this.text = new Text(text);
        this.shape = this.text;
    }

    @Override
    public void Update() {

    }

    public void setText(Object o) {
        this.text.setText(o.toString());
    }
}
