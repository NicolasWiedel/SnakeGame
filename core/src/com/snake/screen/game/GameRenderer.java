package com.snake.screen.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.snake.assets.AssetDescriptors;
import com.snake.assets.RegionNames;
import com.snake.common.GameManager;
import com.snake.config.GameConfig;
import com.jga.snake.util.debug.DebugCameraController;
import com.jga.snake.util.GdxUtils;
import com.jga.snake.util.ViewportUtils;
import com.snake.entity.BodyPart;
import com.snake.entity.Coin;
import com.snake.entity.Snake;
import com.snake.entity.SnakeHead;

public class GameRenderer implements Disposable {

    // == constants ==
    private final float PADDING = 20.0f;

    // == attributes ==
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final GameController controller;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;

    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    private TextureRegion backgroundRegion;
    private TextureRegion bodyRegion;
    private TextureRegion coinRegion;
    private TextureRegion headRegion;

    private DebugCameraController debugCameraController;

    // == contructor ==
    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController controller) {
        this.batch = batch;
        this.assetManager = assetManager;
        this.controller = controller;
        init();
    }

    // == init ==
    private void init(){
        camera = new OrthographicCamera();
        viewport= new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        renderer = new ShapeRenderer();

        font = assetManager.get(AssetDescriptors.UI_FONT);
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        bodyRegion = gamePlayAtlas.findRegion(RegionNames.BODY);
        coinRegion = gamePlayAtlas.findRegion(RegionNames.COIN);
        headRegion = gamePlayAtlas.findRegion(RegionNames.HEAD);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

    }

    // == public methods ==
    public void render(float delta){
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        GdxUtils.clearScreen();

        renderGamePlay();
        renderHud();
//        renderDebug();
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        hudViewport.update(width, height,true);
        ViewportUtils.debugPixelsPerUnit(viewport);
        ViewportUtils.debugPixelsPerUnit(hudViewport);

    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // == private methods
    private void renderGamePlay(){
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();
    }

    private void drawGamePlay(){
        // backfround
        batch.draw(backgroundRegion,
                0, 0,
                GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT
        );

        // coin
        Coin coin = controller.getCoin();
        if(coin.isAvailable()){
            batch.draw(coinRegion,
                    coin.getX(), coin.getY(),
                    coin.getWidth(), coin.getHeight()
            );
        }

        // snake
        Snake snake = controller.getSnake();
        for(BodyPart bodyPart : snake.getBodyParts()){
            batch.draw(bodyRegion,
                    bodyPart.getX(), bodyPart.getY(),
                    bodyPart.getWidth(), bodyPart.getHeight()
            );
        }
        SnakeHead head = snake.getHead();
        batch.draw(headRegion,
                head.getX(), head.getY(),
                head.getWidth(), head.getHeight()
        );
    }

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

        Snake snake = controller.getSnake();

        renderer.setColor(Color.YELLOW);
        for(BodyPart bodyPart: snake.getBodyParts()){
            Rectangle bodyPartBounds = bodyPart.getBounds();
            renderer.rect(bodyPartBounds.x, bodyPartBounds.y, bodyPartBounds.width, bodyPartBounds.height);
        }

        renderer.setColor(Color.GREEN);
        SnakeHead head = snake.getHead();
        Rectangle headBounds = head.getBounds();
        renderer.rect(headBounds.x, headBounds.y, headBounds.width, headBounds.height);

        Coin coin = controller.getCoin();
        if(coin.isAvailable()) {
            renderer.setColor(Color.BLUE);
            Rectangle coinBounds = coin.getBounds();
            renderer.rect(coinBounds.x, coinBounds.y, coinBounds.width, coinBounds.height);
        }
    }

    private void renderHud(){
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        drawHud();

        batch.end();

    }

    private void drawHud(){
        String highScoreString = "HIGH SCORE: " + GameManager.INSTANCE.getDisplayHighScore();
        layout.setText(font, highScoreString);
        font.draw(batch, layout, PADDING, hudViewport.getWorldHeight() - PADDING);

        float scoreX = hudViewport.getWorldWidth() - layout.width;
        float scoreY = hudViewport.getWorldHeight() - PADDING;
        String scoreString ="SCORE: " + GameManager.INSTANCE.getDisplayScore();
        layout.setText(font, scoreString);
        font.draw(batch, layout,scoreX, scoreY);

    }
}