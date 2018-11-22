package com.snake.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.snake.SimpleSnakeGame;
import com.snake.assets.AssetDescriptors;
import com.snake.assets.ButtonStylaNames;
import com.snake.assets.RegionNames;
import com.snake.config.GameConfig;
import com.snake.screen.game.GameScreen;
import com.jga.snake.util.GdxUtils;

public class MenuScreen extends ScreenAdapter {

    // == attributes ==
    private final SimpleSnakeGame game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private TextureAtlas gamePlayAtlas;

    // == constructor ==
    public MenuScreen(SimpleSnakeGame game){
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        Gdx.input.setInputProcessor(stage);
        stage.addActor(createUi());
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // == private methods ==
    private Actor createUi(){
        Table table = new Table(skin);
        table.defaults().pad(10);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Image titleImage = new Image(skin, RegionNames.TITLE);

        Button playButton = new Button(skin, ButtonStylaNames.PLAY);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        Button quitButton = new Button(skin, ButtonStylaNames.QUIT);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        table.add(titleImage).row();
        table.add(playButton).row();
        table.add(quitButton);

        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void play(){
        game.setScreen(new GameScreen(game));
    }

    private void quit(){
        Gdx.app.exit();
    }
}
