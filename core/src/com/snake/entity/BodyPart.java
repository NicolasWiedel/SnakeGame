package com.snake.entity;

import com.snake.config.GameConfig;

public class BodyPart extends EntityBase{

    private boolean justAdded = true;

    // == Construktor ==
    public BodyPart() {
        setSize(GameConfig.SNAKE_SIZE, GameConfig.SNAKE_SIZE);
    }

    public boolean isJustAdded() {
        return justAdded;
    }

    public void setJustAdded(boolean justAdded) {
        this.justAdded = justAdded;
    }
}
