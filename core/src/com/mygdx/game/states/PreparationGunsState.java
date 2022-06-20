package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.MainGame;
import com.mygdx.game.Player;
import com.mygdx.game.guns.Gun;
import java.util.Vector;


public class PreparationGunsState extends State {
    private Player player;
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Vector<String> chosenGuns,guns;
    private Table tableEquipped;
    private Label numberOfEquippedGuns;

    PreparationGunsState(GameStateManager stateManager, Player player) {
        super(stateManager);
        chosenGuns = new Vector<>();
        this.player = player;
        guns = (Vector<String>) player.getPurchasedGuns().clone();
        initializeFonts();
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(MainGame.background);
        background.setPosition(-500,-500);
        stage.addActor(background);
        stage.addActor(initializeEquippedGuns());
        stage.addActor(initializeGunsTable());
        stage.addActor(initializeTitle());
        stage.addActor(initializeButtons());
        System.out.println(player.getPurchasedGuns().size());
    }

    private Table initializeTitle(){
        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(new Label("Choose guns to equip the ship",
                new Label.LabelStyle(Constants.titleFont,
                Color.GRAY)));

        return table;
    }

    private Table initializeEquippedGuns(){
        tableEquipped = new Table();
        tableEquipped.setFillParent(true);
        tableEquipped.right().top().padTop(Gdx.graphics.getHeight() * 0.15f);
        numberOfEquippedGuns =
                new Label(chosenGuns.size() + " / " + player.currentShip.getMaxAmountOfGuns(),
                        new Label.LabelStyle(Constants.font,Color.WHITE));
        tableEquipped.add(numberOfEquippedGuns).padRight(Gdx.graphics.getWidth() * 0.15f).row();


        return tableEquipped;
    }

    private Table initializeButtons(){
        Table table = new Table();
        table.setFillParent(true);
        Image backImage = new Image(Constants.textures.get("button back"));
        Image startImage = new Image(Constants.textures.get("button start"));
        table.bottom();
        backImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                stateManager.pop();
                stateManager.push(new PreparationShipState(stateManager,player));
            }
        });

        startImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.currentShip.setPlayer(player);
                Array<Gun> gunsArray = new Array<>();
                GameState.time = 0;
                for(String gunKey : chosenGuns){
                    gunsArray.add( (Gun) Constants.guns.get(gunKey).clone());
                    gunsArray.peek().setShip(player.currentShip);
                }
                player.currentShip.setGuns(gunsArray);

                stateManager.set(new GameState(stateManager,player));
            }
        });

        table.add(backImage).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(20f).padRight(100f);
        table.add(startImage).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(20f).right();
        return table;
    }

    private Table initializeGunsTable(){
        Table table = new Table();
        table.setFillParent(true);
        table.left().top().padTop(Gdx.graphics.getHeight() * 0.15f);
        table.add(new Label("Guns",new Label.LabelStyle(font,Color.GRAY))).padLeft(Gdx.graphics.getWidth() * 0.15f).row();
        for (final String gun : player.getUniquePurchasedGuns()){
            int countM = 0;
            for (String i : guns)if(gun.equals(i)) countM++;
            final Label gunLabel = new Label(gun + "  " + countM + "x",
                    new Label.LabelStyle(font,
                    Color.WHITE));
            Image addButton = new Image(Constants.textures.get("button use"));
            addButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    int count = 0;
                    for (String i : guns)if(gun.equals(i)) count++;
                    if(count == 0 || chosenGuns.size() == player.currentShip.getMaxAmountOfGuns()) return;
                    chosenGuns.add(gun);
                    guns.removeElementAt(guns.indexOf((String)gun));
                    gunLabel.setText(gun + "  " + --count + "x");
                    tableEquipped.add(new Label(gun,new Label.LabelStyle(Constants.font,
                            Color.WHITE))).left().row();
                    numberOfEquippedGuns.setText(chosenGuns.size() + " / " + player.currentShip.getMaxAmountOfGuns());
                    System.out.println(guns.size());
                }
            });
            table.add(gunLabel).padLeft(Gdx.graphics.getWidth() * 0.15f).left().padRight(20f).padBottom(20f);
            table.add(addButton).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(20f).row();
        }
        return table;
    }

    private void initializeFonts(){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Koulen-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
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
