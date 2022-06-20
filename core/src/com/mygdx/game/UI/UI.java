package com.mygdx.game.UI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Constants;
import com.mygdx.game.Player;
import com.mygdx.game.states.GameState;


public class UI {

    public Stage stage;
    private Stage controllerStage;

    private ScreenViewport viewport;
    private Player player;
    private float width,height;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Label healthLabel,scoreLabel,fps,timeLabel;

    public UI(Player player){
        this.player = player;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Koulen-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.app.getType() == Application.ApplicationType.Android ? 50 : 35;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        initializeFonts();
        initializeStage();
        if(Gdx.app.getType() == Application.ApplicationType.Android)initializeControllerStage();
    }

    private void initializeControllerStage() {
        controllerStage = new Stage(viewport);
        Gdx.input.setInputProcessor(controllerStage);
        Table table = new Table();
        table.setFillParent(true);
        table.left().bottom();
        Image moveImage = new Image(Constants.textures.get("button move"));
        moveImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setAutoMove(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setAutoMove(false);
            }
        });
        Image leftImage = new Image(Constants.textures.get("button left"));
        leftImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setAutoRotateLeft(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setAutoRotateLeft(false);
            }
        });
        Image rightImage = new Image(Constants.textures.get("button right"));
        rightImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setAutoRotateRight(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setAutoRotateRight(false);
            }
        });
        Image attackImage = new Image(Constants.textures.get("button attack"));
        attackImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setHoldAttack(!player.currentShip.isHoldAttack());
                return true;
            }
        });
        float heightOfButton = height / 4;
        table.add(leftImage).size(heightOfButton).pad(30f);
        table.add(rightImage).size(heightOfButton).pad(30f);

        Table rightTable = new Table();
        rightTable.right().bottom();
        rightTable.setFillParent(true);
        rightTable.add(attackImage).size(heightOfButton).pad(30f);
        rightTable.row();
        rightTable.add(moveImage).size(heightOfButton).pad(30f);
        controllerStage.addActor(table);
        controllerStage.addActor(rightTable);
    }

    public void update(float delta){
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        healthLabel.setText("HEALTH : " + (int)(player.currentShip.getHealth() / player.currentShip.getMaxHealth() * 100) + "%");
        scoreLabel.setText("SCORE : " + player.currentScore);
        fps.setText("FPS : " + Gdx.graphics.getFramesPerSecond());
        timeLabel.setText((int)GameState.time + " S");

    }

    public void render(SpriteBatch batch){
        batch.end();
        stage.getViewport().setScreenHeight((int)height);
        stage.getViewport().setScreenWidth((int)width);

        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getViewport().apply();
        stage.act();

        stage.draw();

        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            controllerStage.getBatch().setProjectionMatrix(controllerStage.getCamera().combined);
            controllerStage.act();
            controllerStage.draw();
        }
    }

    private void initializeFonts(){
        font = generator.generateFont(parameter);
    }

    private void initializeStage(){
        viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport);

        healthLabel =
                new Label( "HEALTH : "+ (int)(player.currentShip.getHealth()/player.currentShip.getMaxHealth() * 100) + "%",
                        new Label.LabelStyle(font,
                                Color.valueOf("008000")));
        scoreLabel = new Label("SCORE : " + player.currentScore,new Label.LabelStyle(font,
                Color.valueOf("FFD700")));
        fps = new Label("FPS : " + Gdx.graphics.getFramesPerSecond(),new Label.LabelStyle(font,
                Color.BLUE));

        timeLabel = new Label((int)GameState.time + "",
                new Label.LabelStyle(font,
                        Color.BLUE));

        Table table = new Table();
        table.left();
        table.top();
        table.setFillParent(true);
        table.add(healthLabel).padLeft(width * 0.1f).padTop(50f);
        stage.addActor(table);
        Table scoreTable = new Table();
        scoreTable.top();
        scoreTable.right();
        scoreTable.setFillParent(true);
        scoreTable.add(scoreLabel).pad(width * 0.1f).padTop(50f);
        stage.addActor(scoreTable);

        Table timeTable = new Table();
        timeTable.setFillParent(true);
        timeTable.center().top();

        timeTable.add(timeLabel).pad(10f);
        stage.addActor(timeTable);
    }


}
