package com.eternal.rolly_roll.game.model.object;

import com.eternal.rolly_roll.game.model.object.physics.Vector3D;
import com.eternal.rolly_roll.game.model.object.shape.Shape;
import com.eternal.rolly_roll.game.view.RenderMiddleware;

public abstract class GameObject {
    protected Shape shape;
    protected boolean active = true;
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean b) {
        active = b;
    }

    public abstract void Update();
    
    public final void Render(RenderMiddleware r) {
        if (active) {
            if (shape != null) {
                shape.Render(r);
            }
        }
    }

    public void setPosition(Vector3D position) {
        shape.transform.position = position;
    }
    public void setScale(Vector3D scale) {
        shape.transform.scale = scale;
    }
    public void setColor(float r, float g, float b, float a) {
        shape.color = new float[] { r, g, b, a };
    }
}
