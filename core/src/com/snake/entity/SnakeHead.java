package com.snake.entity;

import com.snake.config.GameConfig;

public class SnakeHead extends EntityBase{

    // == attributes ==
    private Direction direction = Direction.RIGHT;

    // == constructor ==
    public SnakeHead() {

        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE);
    }

    // == public methods ==
    public void move(){
        if(direction.isRight()){
            updateX(GameConfig.SNAKE_SPEED);
        } else if(direction.isLeft()){
            updateX(-GameConfig.SNAKE_SPEED);
        } else if(direction.isUp()){
            updateY(GameConfig.SNAKE_SPEED);
        } else if(direction.isDown()){
            updateY(-GameConfig.SNAKE_SPEED);
        }
    }

    public void updateX(float amount){
        x += amount;
        updateBounds();
    }

    public void updateY(float amount){
        y += amount;
        updateBounds();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
