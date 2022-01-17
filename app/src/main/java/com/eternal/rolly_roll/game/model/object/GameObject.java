package com.eternal.rolly_roll.game.model.object;

import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;

public abstract class GameObject {
    protected Shape shape;

    public abstract void Update();
    
    public final void Render(RenderMiddleware r) {
        if (shape != null) {
            shape.Render(r);
        }
    }
}
