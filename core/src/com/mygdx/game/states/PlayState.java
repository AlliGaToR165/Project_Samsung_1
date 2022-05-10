package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.mygdx.game.InputHandler;
import com.mygdx.game.sprite.Duck;
import com.mygdx.game.Shared;

public class PlayState extends AbstractState{
    public class PlayStateData{
        public int counter, score;
        public Duck duck;
        public PlayStateData(int counter, int score, Duck duck){
            this.counter = counter;
            this.score = score;
            this.duck = duck;
        }
    }

    private class Hud{
        private Label label = new Label("", new Label.LabelStyle(Shared.hack, Color.FIREBRICK));
        public Hud(){
            Shared.stage.addActor(label);
            label.setPosition(10, Shared.stage.getHeight() - 3*Shared.hack.getCapHeight());
        }

        public void update(){
            label.setText("Score: " + score + "\n" +
                          "Speed multiplier: " + String.format("%.1f", multiplier) + "\n" +
                          "FPS: " + Gdx.graphics.getFramesPerSecond());
        }
    }

    private ImageButton pause;
    private Texture grass, ground;
    private int counter = 0, score = 0;
    private float multiplier = 1;
    private Duck current;
    private Hud hud;

    private Duck generateDuck(){
        boolean isReversed = (boolean)MathUtils.randomBoolean();
        return (isReversed ?
        new Duck(new Vector2(Gdx.graphics.getWidth()+256, -252), new Vector2(-1.5f, 1.0f).scl(multiplier),isReversed) :
        new Duck(new Vector2(-256, -252), new Vector2(1.5f, 1.0f).scl(multiplier),isReversed));
    }

    public PlayState(GameStateManager gsm) {
        super(gsm);
	    Gdx.input.setInputProcessor(Shared.stage);

        pause = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("pause.png")))));
        Shared.stage.addActor(pause);
        pause.setPosition(Shared.stage.getWidth() - 10 - pause.getWidth(), Shared.stage.getHeight() - 10 - pause.getHeight());

        grass = new Texture("grass.png");
        ground = new Texture("ground.png");
        current = generateDuck();
        hud = new Hud();
    }

    public PlayState(GameStateManager gsm, PlayStateData data){
        this(gsm);
        counter = data.counter;
        multiplier = 1 + 0.1f * counter;
        score = data.score;

        current = data.duck;
        current.loadTexture();
    }

    private void handleInput(){
        if(pause.isPressed()){
            Shared.stage.clear();
            gsm.set(new PauseState(gsm, new PlayStateData(counter, score, current)));
        }
    }

    @Override
    public void update(float delta) {
        Shared.bg.update(delta);
        current.update(delta);
        if(current.isDead()) {current = generateDuck(); multiplier += 0.1;}
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        Shared.bg.draw(batch);
        batch.begin();
        current.render(batch);
        if (InputHandler.isClicked()) {
            if (current.getBounds().contains(InputHandler.getMousePosition().x,InputHandler.getMousePosition().y)){
                current.kill();
                score++;
                counter++;
            }
        }

        hud.update();
        Shared.stage.act(Gdx.graphics.getDeltaTime());
        Shared.stage.draw();

            int x = 0;
            do {
                batch.draw(ground, x, 0);
                batch.draw(grass, -30 + x, -80);
                x += ground.getWidth();
            } while (x <= Gdx.graphics.getWidth());

            if (((current.getBounds().x - current.getBounds().width > Gdx.graphics.getWidth()) && !current.getReversed())
                    || (current.getBounds().x + current.getBounds().width < 0 && current.getReversed())) {
                Shared.stage.clear();
                gsm.set(new GameOverState(gsm, score, false));
            }
            else if(counter == Shared.ducks) {Shared.stage.clear(); gsm.set(new GameOverState(gsm, score, true));}
            else if((((current.getBounds().x -current.getBounds().width> Gdx.graphics.getWidth()) && !current.getReversed())
                    ||( (current.getBounds().x+current.getBounds().width<0) && (current.getReversed())))){
                counter++;
            }
        batch.end();
    }

    @Override
    public void dispose() {
        current.dispose();
        grass.dispose();
        ground.dispose();
    }
}
