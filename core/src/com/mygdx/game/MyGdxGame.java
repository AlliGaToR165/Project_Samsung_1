package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;


public class MyGdxGame extends ApplicationAdapter {
	private GameStateManager gsm;
	private SpriteBatch batch;
	Viewport viewport;

	@Override
	public void create () {
		viewport = new FitViewport(1280,720);
		gsm = new GameStateManager();
		batch = new SpriteBatch();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width,height,true);
		getViewport().apply();
	}

	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}

