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

    public Direction getOposite(){
        if(isLeft()){
            return RIGHT;
        }else if(isRight()){
            return LEFT;
        }else if(isUp()){
            return DOWN;
        }else if(isDown()){
            return UP;
        }

        //this will never happen
        throw new IllegalArgumentException("Cant find opposite direction =" + this);
    }

    public boolean isOpposite(Direction direction){
        return getOposite() == direction;
    }
}
