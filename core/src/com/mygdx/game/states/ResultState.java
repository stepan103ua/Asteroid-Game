package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.MainGame;
import com.mygdx.game.Player;


public class ResultState extends State {
    private Player player;
    private Stage stage;
    private Viewport viewport;
    public ResultState(GameStateManager stateManager, Player player) {
        super(stateManager);
        this.player = player;
        this.viewport = new ScreenViewport();
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(MainGame.background);
        background.setPosition(-500,-500);
        stage.addActor(background);
        stage.addActor(initializeTexts());
        stage.addActor(initializeButtons());
    }

    private Table initializeTexts(){
        Table table = new Table();
        table.setFillParent(true);
        Label maxScoreLabel = new Label("Your max score: " + player.maxScore,
                new Label.LabelStyle(Constants.font, Color.GOLD));
        Label currentScoreLabel = new Label("Your current score: " + player.currentScore,
                new Label.LabelStyle(Constants.font,Color.WHITE));

        table.add(maxScoreLabel).left().row();
        table.add(currentScoreLabel).left().row();

        return table;
    }

    private Table initializeButtons() {
        final Table table = new Table();
        table.setFillParent(true);
        table.bottom();

        Image menuButton = new Image(Constants.textures.get("button menu"));
        Image againButton = new Image(Constants.textures.get("button again"));

        menuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stateManager.set(new MenuState(stateManager,player));
            }
        });

        againButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stateManager.set(new PreparationShipState(stateManager,player));
            }
        });

        table.add(menuButton).padRight(150f).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(30f);
        table.add(againButton).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(30f);

        return table;
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

    }
}
