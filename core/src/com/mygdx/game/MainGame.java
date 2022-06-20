package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	private GameStateManager stateManager;
	public static Texture background;
	private Player player;

	@Override
	public void create () {
		Constants.initConstants();
		background = new Texture("Space background.png");
		stateManager = new GameStateManager();
		Player loadedPlayer = DataProvider.loadPlayer();
		player = loadedPlayer == null ? new Player() : loadedPlayer;
		batch = new SpriteBatch();
		stateManager.push(new MenuState(stateManager,player));
	}

	public void update(float delta){

		stateManager.update(delta);


	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());
		ScreenUtils.clear(0, 0, 0, 1);


		batch.begin();
		stateManager.render(batch);
	}
	
	@Override
	public void dispose () {
		System.out.println("SAVING");
		DataProvider.save(player);
		Constants.dispose();
		batch.dispose();
		background.dispose();
		stateManager.peek().dispose();
	}

	@Override
	public void pause() {
		DataProvider.save(player);
	}
}
