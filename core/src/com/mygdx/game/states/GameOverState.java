package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.InputHandler;
import com.mygdx.game.Score;

public class GameOverState extends BaseMenuState{
    private Label gameOver;

    public GameOverState(GameStateManager gsm, int ducks, boolean victory) {
        super(gsm, "RETRY", "retry_bg.png", ducks);
        mainButton.setPosition(Gdx.graphics.getWidth()/2 - mainButton.getWidth()/2, Gdx.graphics.getHeight()/2 + mainButton.getHeight());
        setCount.setPosition(Gdx.graphics.getWidth()/2 - setCount.getWidth()/2, Gdx.graphics.getHeight()/2 - setCount.getHeight() - 1);

        if(!victory) gameOver = new Label("Game over!\nScore: "+Score.getScores(), new Label.LabelStyle(defaultFont, Color.GOLD));
        else gameOver = new Label("You won!\nScore: "+Score.getScores(), new Label.LabelStyle(defaultFont, Color.GOLD));
        gameOver.setPosition(Gdx.graphics.getWidth()/2 - gameOver.getWidth()/2, Gdx.graphics.getHeight()/2 + gameOver.getHeight() + mainButton.getHeight() + 1);
        stage.addActor(gameOver);
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if(mainButton.isPressed()) gsm.set(new PlayState(gsm, ducks));
    }
}
