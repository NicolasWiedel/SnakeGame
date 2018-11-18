package com.snake.entity;

import com.snake.config.GameConfig;

public class SnakeHead extends EntityBase{

    public SnakeHead() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE);
    }
}
