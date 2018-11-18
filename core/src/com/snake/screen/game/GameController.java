package com.snake.screen.game;

import com.badlogic.gdx.utils.Logger;
import com.snake.entity.SnakeHead;

public class GameController {

    // == constants ==
    private static final Logger log = new Logger(GameController.class.getSimpleName());

    // == attributes ==
    private SnakeHead head;

    // == contructoe ==


    public GameController() {
        head = new SnakeHead();
    }

    // == public methods ==
    public void update(float delta){

    }

    public SnakeHead getHead() {
        return head;
    }
}
