package com.snake.entity;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public boolean isUp(){
        return this == UP;
    }

    public boolean isRight(){
        return this == RIGHT;
    }

    public boolean isDown(){
        return this == DOWN;
    }

    public boolean isLeft(){
        return this == LEFT;
    }
}
