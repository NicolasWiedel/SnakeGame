package com.snake;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.snake.screen.game.GameScreen;

public class SimpleSnakeGame extends Game {

	public SimpleSnakeGame() {
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		setScreen(new GameScreen(this));
	}
}
