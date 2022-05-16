package org.dhunt.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import org.dhunt.game.Shared;

public class MenuState extends BaseMenuState{
    public MenuState(GameStateManager gsm) {
        super(gsm, "START", "start_bg.png");
        mainButton.setPosition(Shared.stage.getWidth()/2 - mainButton.getWidth()/2, Shared.stage.getHeight()/2 + mainButton.getHeight() + 1);
        setCount.setPosition(Shared.stage.getWidth()/2 - setCount.getWidth()/2, Shared.stage.getHeight()/2 - setCount.getHeight() - 1);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if(mainButton.isPressed()) {Shared.stage.clear(); gsm.set(new PlayState(gsm));}
    }
}
