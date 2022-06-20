package com.mygdx.game.states;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.MainGame;
import com.mygdx.game.Player;

public class MenuState extends State{

    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Player player;
    public MenuState(GameStateManager stateManager, Player player) {
        super(stateManager);
        this.player = player;
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Koulen-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.app.getType() == Application.ApplicationType.Android ? 50 : 35;
        font = generator.generateFont(parameter);
        initializeStage();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();

        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initializeStage(){
        Gdx.input.setInputProcessor(stage);
        final Image startButton = new Image(Constants.textures.get("button start menu"));
        final Image exitButton = new Image(Constants.textures.get("button exit"));
        final Image shopButton = new Image(Constants.textures.get("button shop"));
        final Image background = new Image(MainGame.background);
        background.setPosition(-500,-500);
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Start");
                stateManager.pop();
                stateManager.push(new PreparationShipState(stateManager,player));
            }
        });
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Exit");
                Gdx.app.exit();
            }
        });
        shopButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Shop");
                stateManager.pop();
                stateManager.push(new ShopState(stateManager,player));

            }
        });
        stage.addActor(background);
        Table table = new Table();
        table.setFillParent(true);
        table.add(startButton).size(Gdx.graphics.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.15f).padTop(50f);
        table.row();
        table.add(shopButton).size(Gdx.graphics.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.15f).padTop(50f);
        table.row();
        table.add(exitButton).size(Gdx.graphics.getWidth() * 0.5f,
                Gdx.graphics.getHeight() * 0.15f).padTop(50f).padBottom(50f);
        stage.addActor(table);
    }
}
