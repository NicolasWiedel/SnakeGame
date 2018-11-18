package com.snake.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.snake.SimpleSnakeGame;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final SimpleSnakeGame game;

    public GameScreen(SimpleSnakeGame game) {
        this.game = game;
    }
}
