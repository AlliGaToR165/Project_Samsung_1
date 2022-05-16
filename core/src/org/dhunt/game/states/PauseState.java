package org.dhunt.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import org.dhunt.game.states.PlayState;
import org.dhunt.game.sprite.Duck;
import org.dhunt.game.Shared;

public class PauseState extends AbstractState{
    private TextButton cont;
    private PlayState.PlayStateData data;

    public PauseState(GameStateManager gsm, PlayState.PlayStateData data){
        super(gsm);
	    Gdx.input.setInputProcessor(Shared.stage);

        cont = new TextButton("CONTINUE", new TextButton.TextButtonStyle(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("cont_bg.png")))), null,
                                                                         new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("cont_bg.png")))), Shared.font));
        Shared.stage.addActor(cont);
        cont.setPosition(Shared.stage.getWidth()/2 - cont.getWidth()/2, Shared.stage.getHeight()/2 + cont.getHeight()/2);

        this.data = data;
    }

    protected void handleInput(){
        if(cont.isPressed()) {Shared.stage.clear(); gsm.set(new PlayState(gsm, data));}
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
    public void dispose() {}
}
