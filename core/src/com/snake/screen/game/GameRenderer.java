package com.snake.screen.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.snake.config.GameConfig;
import com.jga.snake.util.debug.DebugCameraController;
import com.jga.snake.util.GdxUtils;
import com.jga.snake.util.ViewportUtils;
import com.snake.entity.SnakeHead;

public class GameRenderer implements Disposable {

    // == attributes ==
    private final GameController controller;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private DebugCameraController debugCameraController;

    // == contructor ==
    public GameRenderer(GameController controller) {
        this.controller = controller;
        init();
    }

    // == init ==
    private void init(){
        camera = new OrthographicCamera();
        viewport= new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

    }

    // == public methods ==
    public void render(float delta){
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        GdxUtils.clearScreen();

        renderDebug();
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // == private methods
    private void renderDebug(){
        ViewportUtils.drawGrid(viewport, renderer);

        viewport.apply();

        Color oldColor = new Color(renderer.getColor());
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
        renderer.setColor(oldColor);
    }

    // == private methods ==
    private void drawDebug(){
        renderer.setColor(Color.GREEN);

        SnakeHead head = controller.getHead();
        Rectangle headBounds = head.getBounds();
        renderer.rect(headBounds.x, headBounds.y, headBounds.width, headBounds.height);
    }
}