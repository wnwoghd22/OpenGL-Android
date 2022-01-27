package com.eternal.rolly_roll.game.control;

public interface IButton {
    void setAction(Runnable action);
    boolean isTouching(TouchHandler.TouchPos pos);
    boolean handleTouch(TouchHandler.Touch touch);
    void onPressed();
}
