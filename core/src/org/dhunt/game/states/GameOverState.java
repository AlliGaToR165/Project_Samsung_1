package org.dhunt.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import org.dhunt.game.InputHandler;
import org.dhunt.game.Shared;

public class GameOverState extends BaseMenuState{
    private Label gameOver;

    public GameOverState(GameStateManager gsm, int score, boolean victory) {
        super(gsm, "RETRY", "retry_bg.png");
        mainButton.setPosition(Shared.stage.getWidth()/2 - mainButton.getWidth()/2, Shared.stage.getHeight()/2 + mainButton.getHeight());
        setCount.setPosition(Shared.stage.getWidth()/2 - setCount.getWidth()/2, Shared.stage.getHeight()/2 - setCount.getHeight() - 1);

        gameOver = new Label((victory ? "You won" : "Game over") + "!\nScore: " + score, new Label.LabelStyle(Shared.font, Color.GOLD));
        gameOver.setPosition(Shared.stage.getWidth()/2 - gameOver.getWidth()/2, Shared.stage.getHeight()/2 + gameOver.getHeight() + mainButton.getHeight() + 1);
        Shared.stage.addActor(gameOver);

        theme = Gdx.audio.newSound(Gdx.files.internal("sound/"+(victory ? "Round Clear.mp3" : "Game Over.mp3")));
        theme.play();
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if(mainButton.isPressed()) {Shared.stage.clear(); gsm.set(new PlayState(gsm));}
    }
}
