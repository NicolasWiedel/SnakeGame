package com.snake.entity;

import com.badlogic.gdx.utils.Array;
import com.snake.config.GameConfig;

public class Snake {

    // == attributes ==
    private final Array<BodyPart> bodyParts = new Array<BodyPart>();
    private Direction direction = Direction.RIGHT;

    private SnakeHead head;

    private float xBeforeUpdate;
    private float yBeforeUpdate;

    // == construktor ==
    public Snake() {
        head = new SnakeHead();
    }

    public Snake(Direction direction) {
        this.direction = direction;
    }

    // == public methods ==
    public void move(){
        xBeforeUpdate = head.getX();
        yBeforeUpdate = head.getY();

        if(direction.isRight()){
            head.updateX(GameConfig.SNAKE_SPEED);
        } else if(direction.isLeft()){
            head.updateX(-GameConfig.SNAKE_SPEED);
        } else if(direction.isUp()){
            head.updateY(GameConfig.SNAKE_SPEED);
        } else if(direction.isDown()){
            head.updateY(-GameConfig.SNAKE_SPEED);
        }
        updateBodyParts();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public SnakeHead getHead() {
        return head;
    }

    public Array<BodyPart> getBodyParts(){
        return bodyParts;
    }

    public void insertBodyParts(){
        BodyPart bodyPart = new BodyPart();
        bodyPart.setPosition(head.getX(), head.getY());
        bodyParts.insert(0, bodyPart);
    }

    // == private methods ==
    private void updateBodyParts(){
        if(bodyParts.size > 0){
            BodyPart tail = bodyParts.removeIndex(0);
            tail.setPosition(xBeforeUpdate, yBeforeUpdate);
            bodyParts.add(tail);
        }
    }
}
