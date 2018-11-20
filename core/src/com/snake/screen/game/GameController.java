package com.snake.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Logger;
import com.snake.config.GameConfig;
import com.snake.entity.Coin;
import com.snake.entity.Direction;
import com.snake.entity.SnakeHead;

public class GameController {

    // == constants ==
    private static final Logger log = new Logger(GameController.class.getSimpleName());

    // == attributes ==
    private SnakeHead head;
    private float timer;
    private Coin coin;

    // == contructoe ==


    public GameController() {
        head = new SnakeHead();
        coin = new Coin();
    }

    // == public methods ==
    public void update(float delta){
        queryInput();

        timer += delta;
        if(timer >= GameConfig.MOVE_TIME){
            timer = 0;
            head.move();

            checkOutOfBounds();
        }

        spawnCoin();
    }

    public SnakeHead getHead() {
        return head;
    }

    public Coin getCoin(){
        return coin;
    }

    // == private methods ==
    private void queryInput(){
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if(leftPressed){
            head.setDirection(Direction.LEFT);
        }
        if(rightPressed){
            head.setDirection(Direction.RIGHT);
        }
        if(upPressed){
            head.setDirection(Direction.UP);
        }
        if(downPressed){
            head.setDirection(Direction.DOWN);
        }
    }

    private void checkOutOfBounds(){
        // check x bounds left/right
        if(head.getX() >= GameConfig.WORLD_WIDTH){
            head.setX(0);
        } else if(head.getX() < 0){
            head.setX(GameConfig.WORLD_WIDTH - GameConfig.SNAKE_SIZE);
        }
        // check y bounds up/down
        if(head.getY() >= GameConfig.WORLD_HEIGHT){
            head.setY(0);
        } else if(head.getY() < 0){
            head.setY(GameConfig.WORLD_HEIGHT - GameConfig.SNAKE_SIZE);
        }
    }

    private void spawnCoin(){
        if(!coin.isAvailable()){
            float coinX = MathUtils.random((int)(GameConfig.WORLD_WIDTH - GameConfig.COIN_SIZE));
            float coinY = MathUtils.random((int)(GameConfig.WORLD_HEIGHT - GameConfig.COIN_SIZE));
            coin.setAvailable(true);

            coin.setPosition(coinX, coinY);
        }
    }
}
