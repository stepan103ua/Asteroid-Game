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
import com.mygdx.game.ships.Ship;

public class PreparationShipState extends State{

    private final Player player;
    private Stage stage;
    private Viewport viewport;
    PreparationShipState(GameStateManager stateManager, Player player) {
        super(stateManager);
        this.player = player;
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Image background = new Image(MainGame.background);
        background.setPosition(-500,-500);
        stage.addActor(background);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(initializeShips());
        stage.addActor(initializeTitle());
        stage.addActor(initializeButtons());
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
        batch.end();
    }

    private Table initializeButtons() {
        Table table = new Table();
        table.setFillParent(true);
        Image backImage = new Image(Constants.textures.get("button back"));
        table.bottom();

        backImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stateManager.pop();
                stateManager.push(new MenuState(stateManager,player));
            }
        });

        table.add(backImage).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(20f);
        return table;
    }


    private Table initializeTitle(){
        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(new Label("Choose a ship to play",new Label.LabelStyle(Constants.titleFont,
                Color.GRAY)));

        return table;
    }

    private Table initializeShips(){
        Table table = new Table();
        table.setFillParent(true);

        for(final String ship : player.getPurchasedShips()){
            Label shipLabel = new Label(ship,new Label.LabelStyle(Constants.font,Color.WHITE));
            Image image = new Image(Constants.textures.get("button use"));
            Image shipImage = new Image(Constants.ships.get(ship).getTexture());
            image.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    player.currentShip = (Ship) Constants.ships.get(ship).clone();
                    player.currentScore = 0;

                    stateManager.pop();
                    stateManager.push(new PreparationGunsState(stateManager,player));
                }
            });
            table.add(shipImage).padRight(20f).size(Gdx.graphics.getWidth() * 0.05f,
                    Gdx.graphics.getWidth() * 0.05f);
            table.add(shipLabel).padRight(20f);
            table.add(image).size(Constants.smallButtonWidth,Constants.smallButtonHeight).row();
        }
        return table;
    }

    @Override
    public void dispose() {

    }
}
