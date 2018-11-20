package com.snake.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import com.snake.config.GameConfig;
import com.snake.entity.Coin;
import com.snake.entity.Direction;
import com.snake.entity.Snake;
import com.snake.entity.SnakeHead;

public class GameController {

    // == constants ==
    private static final Logger log = new Logger(GameController.class.getSimpleName());

    // == attributes ==
    private Snake snake;
    private float timer;
    private Coin coin;

    // == contructoe ==


    public GameController() {
        snake = new Snake();
        coin = new Coin();
    }

    // == public methods ==
    public void update(float delta){
        queryInput();
        queryDebugInput();

        timer += delta;
        if(timer >= GameConfig.MOVE_TIME){
            timer = 0;
            snake.move();

            checkOutOfBounds();
            checkCollision();
        }

        spawnCoin();
    }

    public Snake getSnake() {
        return snake;
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
            snake.setDirection(Direction.LEFT);
        }
        if(rightPressed){
            snake.setDirection(Direction.RIGHT);
        }
        if(upPressed){
            snake.setDirection(Direction.UP);
        }
        if(downPressed){
            snake.setDirection(Direction.DOWN);
        }
    }

    private void checkOutOfBounds(){
        SnakeHead head = snake.getHead();
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

    private void checkCollision(){
        // head - coin -collision
        SnakeHead head = snake.getHead();
        Rectangle headBound = head.getBounds();
        Rectangle coinBounds = coin.getBounds();

        boolean overlaps = Intersector.overlaps(headBound, coinBounds);

        if(coin.isAvailable() &&overlaps){
            snake.insertBodyParts();
            coin.setAvailable(false);
        }
    }

    private void queryDebugInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.PLUS)){
            snake.insertBodyParts();
        }
    }
}
