package com.snake.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import com.snake.collision.CollisionListener;
import com.snake.common.GameManager;
import com.snake.config.GameConfig;
import com.snake.entity.BodyPart;
import com.snake.entity.Coin;
import com.snake.entity.Direction;
import com.snake.entity.Snake;
import com.snake.entity.SnakeHead;

public class GameController {

    // == constants ==
    private static final Logger log = new Logger(GameController.class.getSimpleName(), Logger.DEBUG);

    // == attributes ==
    private final CollisionListener listener;

    private Snake snake;
    private float timer;
    private Coin coin;

    // == contructoe ==


    public GameController(CollisionListener listener) {
        this.listener = listener;
        snake = new Snake();
        coin = new Coin();

        restart();
    }

    // == public methods ==
    public void update(float delta){
        GameManager.INSTANCE.updateDisplayScore(delta);

        if(GameManager.INSTANCE.isPlaying()) {
            queryInput();
            queryDebugInput();

            timer += delta;
            if (timer >= GameConfig.MOVE_TIME) {
                timer = 0;
                snake.move();

                checkOutOfBounds();
                checkCollision();
            }

            spawnCoin();
        }else{
            checkForRestart();
        }
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
        if(head.getY() >= GameConfig.MAX_Y){
            head.setY(0);
        } else if(head.getY() < 0){
            head.setY(GameConfig.MAX_Y - GameConfig.SNAKE_SIZE);
        }
    }

    private void spawnCoin(){
        if(!coin.isAvailable()){
            float coinX = MathUtils.random((int)(GameConfig.WORLD_WIDTH - GameConfig.COIN_SIZE));
            float coinY = MathUtils.random((int)(GameConfig.MAX_Y - GameConfig.COIN_SIZE));
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
            listener.hitCoin();
            snake.insertBodyParts();
            coin.setAvailable(false);
            GameManager.INSTANCE.incrementScore(GameConfig.COIN_SCORE);
        }

        // head - bodyparts
        for(BodyPart bodyPart : snake.getBodyParts()){
            if(bodyPart.isJustAdded()){
                bodyPart.setJustAdded(false);
                continue;
            }
            Rectangle bodyPartBounds = bodyPart.getBounds();
            if(Intersector.overlaps(bodyPartBounds, headBound)){
                listener.loose();
                GameManager.INSTANCE.updateHighScore();
                GameManager.INSTANCE.setGameOver();
            }
        }
    }

    private void queryDebugInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)){
            snake.insertBodyParts();
        }
    }

    private  void checkForRestart(){
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            restart();
        }
    }

    private void restart(){
        GameManager.INSTANCE.reset();
        snake.reset();
        coin.setAvailable(false);
        timer = 0;
    }
}
