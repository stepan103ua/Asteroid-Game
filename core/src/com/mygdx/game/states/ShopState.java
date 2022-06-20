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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.DataProvider;
import com.mygdx.game.MainGame;
import com.mygdx.game.Player;
import com.mygdx.game.ShopItem;

public class ShopState extends State {
    private Player player;
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private BitmapFont titleFont;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Label moneyLabel, errorLabel;
    ShopState(GameStateManager stateManager, Player player) {
        super(stateManager);
        this.player = player;
        initializeFonts();
        errorLabel = new Label("",new Label.LabelStyle(font,Color.RED));
        viewport = new ScreenViewport();
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(MainGame.background);
        background.setPosition(-500,-500);
        stage.addActor(background);
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(new Image(Constants.textures.get("shop background"))).size(Gdx.graphics.getWidth()*0.85f,Gdx.graphics.getHeight() * 0.85f);
        stage.addActor(table);
        stage.addActor(initializeExitTable());
        stage.addActor(initializeErrorTable());
        stage.addActor(initializeMoneyTable());
        stage.addActor(initializeGunTable());
        stage.addActor(initializeShipTable());
    }

    @Override
    public void update(float delta) {
        moneyLabel.setText("Money: " + player.getMoney());
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {

    }

    private Table initializeErrorTable(){
        Table table = new Table();
        table.setFillParent(true);
        table.center().top();
        table.add(errorLabel);
        return table;
    }

    private void initializeFonts(){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Koulen-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        parameter.size = 35;
        titleFont = generator.generateFont(parameter);
    }

    private Table initializeExitTable(){
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right();
        Image image = new Image(Constants.textures.get("button back"));
        image.addListener(new InputListener(){
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
        table.add(image).size(Constants.smallButtonWidth,Constants.smallButtonHeight).padBottom(Gdx.graphics.getHeight()*0.15f).padRight(Gdx.graphics.getWidth()*0.15f);
        return table;
    }

    private Table initializeMoneyTable(){
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        moneyLabel = new Label("Money: " + player.getMoney(),new Label.LabelStyle(font,
                Color.GOLD));

        table.add(moneyLabel).padLeft(Gdx.graphics.getWidth() * 0.15f).padTop(Gdx.graphics.getHeight() * 0.15f);

        return table;
    }

    private Table initializeGunTable(){
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().padLeft(Gdx.graphics.getWidth() * 0.15f).padTop(Gdx.graphics.getHeight() * 0.25f);
        table.add(new Label("Guns",new Label.LabelStyle(titleFont,
                Color.GRAY) ));
        table.row();
        for(final ShopItem gun : DataProvider.allGuns()){
            Label gunLabel = new Label(gun.name,new Label.LabelStyle(font,
                    Color.WHITE) );
            Label priceLabel = new Label("Price: " + gun.price,new Label.LabelStyle(font,
                    Color.GOLD) );
            table.add(gunLabel).padRight(20f).left();
            Image buyButton = new Image(Constants.textures.get("button buy"));
            buyButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println(gun.name);
                    if (player.getMoney() >= gun.price){
                        player.minusMoney(gun.price);
                        player.getPurchasedGuns().add(gun.name);
                        DataProvider.save(player);
                    }
                    else{
                        errorLabel.setText("Not enough money! You need " + (gun.price - player.getMoney()) + " more!");
                    }
                }
            });

            table.add(buyButton).size(Constants.smallButtonWidth,Constants.smallButtonHeight).row();
            table.add(priceLabel).left().padBottom(30f).row();

        }

        return table;
    }

    private Table initializeShipTable(){
        Table table = new Table();
        table.setFillParent(true);
        table.top().right().padRight(Gdx.graphics.getWidth() * 0.15f).padTop(Gdx.graphics.getHeight() * 0.25f);
        table.add(new Label("Ships",new Label.LabelStyle(titleFont,
                Color.GRAY) ));
        table.row();
        for(final ShopItem ship : DataProvider.allShips()){
            if(player.getPurchasedShips().contains(ship.name)) continue;
            Label gunLabel = new Label(ship.name,new Label.LabelStyle(font,
                    Color.WHITE) );
            Label priceLabel = new Label("Price: " + ship.price,new Label.LabelStyle(font,
                    Color.GOLD) );

            table.add(gunLabel).padRight(20f).left();
            Image buyButton = new Image(Constants.textures.get("button buy"));
            buyButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (player.getMoney() >= ship.price){
                        player.minusMoney(ship.price);
                        player.getPurchasedShips().add(ship.name);
                        DataProvider.save(player);
                        stateManager.set(new ShopState(stateManager,player));
                    }
                    else{
                        errorLabel.setText("Not enough money! You need " + (ship.price - player.getMoney()) + " more!");
                    }
                }
            });
            table.add(buyButton).size(Constants.smallButtonWidth,Constants.smallButtonHeight).row();
            table.add(priceLabel).left().padBottom(30f).row();
        }

        return table;
    }
}
