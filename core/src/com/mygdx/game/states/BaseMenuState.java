package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import com.mygdx.game.Background;

public class BaseMenuState extends AbstractState{
    private class DuckCountIL implements TextInputListener {
       @Override
       public void input (String text) {
           ducks = Integer.parseInt(text);
           if(ducks == 0) ducks = 3;
           else if(ducks < 0) ducks = -ducks;
           setCount.setText("Hunting until "+Integer.toString(ducks)+" ducks");
       }
       @Override
       public void canceled () {}
    }

    private Background bg;

    private DuckCountIL duckCountInput;
    protected BitmapFont defaultFont, hack;
    protected TextButton mainButton, setCount;
    protected int ducks=3;
    protected Stage stage;

    public BaseMenuState(GameStateManager gsm, String buttonText, String buttonImgPath) {
        super(gsm);
        stage = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(stage);

        defaultFont = new BitmapFont(Gdx.files.internal("myfont.fnt"),Gdx.files.internal("myfont.png"),false);
        hack = new BitmapFont(Gdx.files.internal("Hack.fnt"),Gdx.files.internal("Hack.png"),false);
        mainButton = new TextButton(buttonText, new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(buttonImgPath)))), null,
                                                                               new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(buttonImgPath)))), defaultFont));
        setCount = new TextButton("Hunting until "+Integer.toString(ducks)+" ducks",
                                  new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("set_count.png")))), null,
                                                                 new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("set_count.png")))), hack));
        stage.addActor(mainButton);
        stage.addActor(setCount);

        duckCountInput = new DuckCountIL();
        bg = new Background();
    }

    public BaseMenuState(GameStateManager gsm, String buttonText, String buttonImgPath, int ducks) {
        this(gsm, buttonText, buttonImgPath);
        this.ducks = ducks;
        setCount.setText("Hunting until "+Integer.toString(ducks)+" ducks");
    }

    protected void handleInput() {
        if(setCount.isPressed()) {
            Gdx.input.getTextInput(duckCountInput, "Playing before:", "3", "");
            try{ Thread.sleep(1000);} catch(InterruptedException e){}
        }
    }

    @Override
    public void update(float delta) {
        bg.update(delta);
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        bg.draw(batch);
        float delta = Gdx.graphics.getDeltaTime();
        stage.act(delta);
	    stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        bg.dispose();
        defaultFont.dispose();
        hack.dispose();
    }
}
