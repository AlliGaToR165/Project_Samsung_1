package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;

import com.mygdx.game.InputHandler;
import com.mygdx.game.sprite.Duck;
import com.mygdx.game.Background;

public class PlayState extends AbstractState{
    private class Hud{
        private Stage stage = new Stage(new ScreenViewport());
        private BitmapFont hack = new BitmapFont(Gdx.files.internal("Hack.fnt"),Gdx.files.internal("Hack.png"),false);
        private Label label = new Label("", new Label.LabelStyle(hack, Color.FIREBRICK));
        public Hud(){
            stage.addActor(label);
            label.setPosition(10, Gdx.graphics.getHeight() - 3*hack.getCapHeight());
        }

        public void update(){
            label.setText("Score: " + score + "\n" +
                          "Speed multiplier: " + String.format("%.1f", multiplier) + "\n" +
                          "FPS: " + Gdx.graphics.getFramesPerSecond());
        }

        public void draw(){
            float delta = Gdx.graphics.getDeltaTime();
            stage.act(delta);
    	    stage.draw();
        }

        public void dispose(){
            stage.dispose();
            hack.dispose();
        }
    }

    private BitmapFont font;
    private Background bg;
    private Texture sight, grass, ground;
    private int ducks_count, counter = 0, score = 0;
    private float multiplier = 1;
    private Duck current;
    private Hud hud;

    private Duck generateDuck(){
        boolean isReversed = (boolean)MathUtils.randomBoolean();
        return (isReversed ?
        new Duck(new Vector2(Gdx.graphics.getWidth()+256, -252), new Vector2(-1.5f, 1.0f).scl(multiplier),isReversed) :
        new Duck(new Vector2(-256, -252), new Vector2(1.5f, 1.0f).scl(multiplier),isReversed));
    }

    public PlayState(GameStateManager gsm, int ducks_count) {
        super(gsm);
        this.ducks_count = ducks_count;
        sight = new Texture("sight.png");
        font = new BitmapFont(Gdx.files.internal("myfont.fnt"),Gdx.files.internal("myfont.png"),false);
        grass = new Texture("grass.png");
        ground = new Texture("ground.png");
        current = generateDuck();
        bg = new Background();
        hud = new Hud();
    }

    @Override
    public void update(float delta) {
        bg.update(delta);
        current.update(delta);
        if(current.isDead()) {current = generateDuck(); multiplier += 0.1;}
    }

    @Override
    public void render(SpriteBatch batch) {
        bg.draw(batch);
        batch.begin();
        current.render(batch);
        if (InputHandler.isClicked()) {
            if (current.getBounds().contains(InputHandler.getMousePosition().x,InputHandler.getMousePosition().y))
            {
                current.kill();
                score++;
                counter++;
            }
        }
        hud.update();
        hud.draw();
            int x = 0;
            do {
                batch.draw(ground, x, 0);
                batch.draw(grass, -30 + x, -80);
                x += ground.getWidth();
            } while (x <= Gdx.graphics.getWidth());

            batch.draw(sight, InputHandler.getMousePosition().x - sight.getWidth() / 2, InputHandler.getMousePosition().y - sight.getHeight() / 2);

            if (((current.getBounds().x - current.getBounds().width > Gdx.graphics.getWidth()) && !current.getReversed())
                    || (current.getBounds().x + current.getBounds().width < 0 && current.getReversed())) {
                gsm.set(new GameOverState(gsm, ducks_count, score, false));
            }
            else if(counter == ducks_count) gsm.set(new GameOverState(gsm, ducks_count, score, true));
            else if((((current.getBounds().x -current.getBounds().width> Gdx.graphics.getWidth()) && !current.getReversed())
                    ||( (current.getBounds().x+current.getBounds().width<0) && (current.getReversed())))){
                counter++;
            }
        batch.end();
    }

    @Override
    public void dispose() {
        sight.dispose();
        current.dispose();
        font.dispose();
        grass.dispose();
        bg.dispose();
        ground.dispose();
        hud.dispose();
    }
}
