package com.kotb.game;

import com.kotb.game.Screen.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class KnightOfTheBinouze extends Game {
	private SpriteBatch batch;
	private BitmapFont font;

	public SpriteBatch getSpriteBatch() {
		return this.batch;
	}
	public BitmapFont getFont() {
		return this.font;
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));


		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}




	public Skin getSkin() {
		return null;
	}

	public void saveGame() {
	}
}
