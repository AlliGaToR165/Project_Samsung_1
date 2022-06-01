package org.dhunt.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

//responsible for sound
import com.badlogic.gdx.audio.Sound;

import org.dhunt.game.Shared;

public class BaseMenuState extends AbstractState{
    private class DuckCountIL implements TextInputListener {
       @Override
       public void input (String text) {
           Shared.ducks = Integer.parseInt(text);
           if(Shared.ducks == 0) Shared.ducks = 3;
           else if(Shared.ducks < 0) Shared.ducks = -Shared.ducks;
           setCount.setText("Hunting until "+Integer.toString(Shared.ducks)+" ducks");
       }
       @Override
       public void canceled (){}
    }

    private DuckCountIL duckCountInput;
    protected TextButton mainButton, setCount;
    protected Sound theme;

    public BaseMenuState(GameStateManager gsm, String buttonText, String buttonImgPath) {
        super(gsm);
	    Gdx.input.setInputProcessor(Shared.stage);

        mainButton = new TextButton(buttonText, new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(buttonImgPath)))), null,
                                                                               new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(buttonImgPath)))), Shared.font));
        setCount = new TextButton("Hunting until "+Integer.toString(Shared.ducks)+" ducks",
                                  new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("set_count.png")))), null,
                                                                 new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("set_count.png")))), Shared.hack));
        Shared.stage.addActor(mainButton);
        Shared.stage.addActor(setCount);

        duckCountInput = new DuckCountIL();
    }

    protected void handleInput() {
        if(setCount.isPressed()) {
            Gdx.input.getTextInput(duckCountInput, "Playing until:", "", "3");
            try{ Thread.sleep(1000);} catch(InterruptedException e){}
        }
    }

    @Override
    public void update(float delta) {
        Shared.bg.update(delta);
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        Shared.bg.draw(batch);
        Shared.stage.act(Gdx.graphics.getDeltaTime());
	    Shared.stage.draw();
    }

    @Override
    public void dispose() {
        theme.dispose();
    }
}
