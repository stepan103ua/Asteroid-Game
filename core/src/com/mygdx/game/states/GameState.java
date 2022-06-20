package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.Player;
import com.mygdx.game.UI.UI;
import com.mygdx.game.Wave;

public class GameState extends State {
    private UI ui;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Player player;
    private Wave wave;

    public static float time = 0;

    public GameState(GameStateManager stateManager,Player player) {
        super(stateManager);
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        this.player = player;
        wave = new Wave(player);
        ui = new UI(player);
    }

    @Override
    public void update(float delta) {
        time += delta;
        updateBoundaries();
        wave.update(delta);
        player.update(delta);
        ui.update(delta);
        checkIfPlayerLost();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.draw(MainGame.background,-2000,-2000);
        player.render(batch);
        wave.render(batch);
        ui.render(batch);

    }

    @Override
    public void dispose() {
        player.dispose();
    }

    private void checkIfPlayerLost(){
        if(player.currentShip.getHealth() > 0) return;

        player.maxScore = Math.max(player.currentScore,player.maxScore);

        stateManager.set(new ResultState(stateManager,player));
    }

    private void updateBoundaries(){
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float playerX = player.currentShip.getX();
        float playerY = player.currentShip.getY();
        float x = playerX, y = playerY;

        if (playerX + width >= MainGame.background.getWidth() / 2 || playerX  <= -(MainGame.background.getWidth()/2 - width)) {
            x = camera.position.x;
        }
        if (playerY + height >= MainGame.background.getHeight() / 2 || playerY <= -(MainGame.background.getHeight()/2 - height)){
            y = camera.position.y;
        }

        camera.position.set(x,y,0);
        camera.update();
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport.apply();
    }

}
