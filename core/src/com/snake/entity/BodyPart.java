package com.snake.entity;

import com.snake.config.GameConfig;

public class BodyPart extends EntityBase{

    // == Construktor ==
    public BodyPart() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE);
    }
}
