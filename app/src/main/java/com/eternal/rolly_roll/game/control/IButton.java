package com.eternal.rolly_roll.game.control;

public interface IButton {
    public void setAction(Runnable action);
    public void onPressed();
}
