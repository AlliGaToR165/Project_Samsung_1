package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.InputHandler;
import com.mygdx.game.Score;
import com.mygdx.game.sprite.Duck;
import com.mygdx.game.Background;

public class PlayState extends AbstractState{
    private BitmapFont font;
    private Background bg;
    private Texture sight, grass, ground;
    private int score = 0, ducks_count, counter = 0;
    private float multiplier = 1;
    private Duck current;

    private Duck generateDuck(){
        int isReversed = MathUtils.random(0,1);
        return (isReversed==1 ?
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
                Score.setScores(score);
                counter++;
            }
        }
        font.draw(batch, "Scores: " + score, 10,Gdx.graphics.getHeight() - 20);

            int x = 0;
            do {
                batch.draw(ground, x, 0);
                batch.draw(grass, -30 + x, -80);
                x += ground.getWidth();
            } while (x <= Gdx.graphics.getWidth());

            batch.draw(sight, InputHandler.getMousePosition().x - sight.getWidth() / 2, InputHandler.getMousePosition().y - sight.getHeight() / 2);

            if (((current.getBounds().x - current.getBounds().width > Gdx.graphics.getWidth()) && current.getReversed() == 0)
                    || (current.getBounds().x + current.getBounds().width < 0 && current.getReversed() == 1)) {
                gsm.set(new GameOverState(gsm, ducks_count, false));
            }
            else if(counter == ducks_count) gsm.set(new GameOverState(gsm, ducks_count, true));
            else if((((current.getBounds().x -current.getBounds().width> Gdx.graphics.getWidth()) && current.getReversed()==0)
                    ||( (current.getBounds().x+current.getBounds().width<0) && (current.getReversed()==1)))){
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
    }
}
