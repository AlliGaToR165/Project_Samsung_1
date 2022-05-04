package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MenuState extends BaseMenuState{
    public MenuState(GameStateManager gsm) {
        super(gsm, "START", "start_bg.png");
        mainButton.setPosition(Gdx.graphics.getWidth()/2 - mainButton.getWidth()/2, Gdx.graphics.getHeight()/2 + mainButton.getHeight() + 1);
        setCount.setPosition(Gdx.graphics.getWidth()/2 - setCount.getWidth()/2, Gdx.graphics.getHeight()/2 - setCount.getHeight() - 1);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if(mainButton.isPressed()) gsm.set(new PlayState(gsm, ducks));
    }
}
