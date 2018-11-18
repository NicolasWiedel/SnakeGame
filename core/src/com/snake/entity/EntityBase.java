package com.snake.entity;

import com.badlogic.gdx.math.Rectangle;

public abstract class EntityBase {

    // == attributes ==
    protected float x;
    protected float y;

    protected float width = 1;
    protected float height = 1;

    protected Rectangle bounds;

    // == constructor ==
    public EntityBase() {
       bounds = new Rectangle(x, y, width, height);
    }

    // == public methods ==
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // == private methods ==
    private void updateBounds(){
        bounds.setPosition(x, y);
        bounds.setSize(width, height);
    }
}
