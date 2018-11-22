package com.snake.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.snake.SimpleSnakeGame;
import com.snake.common.GameManager;
import com.snake.screen.menu.MenuScreen;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;

    private GameRenderer renderer;
    private GameController controller;

    public GameScreen(SimpleSnakeGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==

    @Override
    public void show() {
        controller = new GameController();
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);

        if(GameManager.INSTANCE.isGameOver()){
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
