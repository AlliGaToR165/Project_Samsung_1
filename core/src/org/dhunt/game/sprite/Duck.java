package org.dhunt.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.audio.Sound;

public class Duck {
    private static Texture texture;
    private Vector2 position, velocity;
    private Rectangle bounds;
    private boolean isKilled, isReversed;
    private float time, angle = 0;
    private final int WIDTH = 252, HEIGHT = 256;
    private static Sound fall = Gdx.audio.newSound(Gdx.files.internal("sound/SFX- Dead Duck Falls.mp3"));

    public Duck(Vector2 position, Vector2 velocity, boolean isReversed) {
        this.position = position;
        this.isReversed = isReversed;
        this.velocity = velocity;

        //angle = MathUtils.random(30f, 60f);
        //this.velocity.setAngleDeg(isReversed? angle+90 : angle);

        loadTexture();
        bounds = new Rectangle(position.x,position.y, WIDTH,HEIGHT);
    }

    public void render(SpriteBatch batch) {
        int frame = (int) (time / 0.1f);
        frame = (this.isReversed ? 3 -(frame % 4) : frame % 4);
        batch.draw(texture, position.x, position.y, WIDTH/2, HEIGHT/2, WIDTH, HEIGHT,1,1,angle,frame * WIDTH,0, WIDTH,HEIGHT,false,false);
    }

    public void dispose() {
        texture.dispose();
        fall.dispose();
    }

    public void update(float delta) {
        time+= delta;
        position.add(velocity);
        if(isReversed){
            velocity.x -= 0.5f * delta;
        }
        else{
            velocity.x += 0.5f * delta;
        }
        bounds.setPosition(position.x,position.y);
        if (isKilled) {
            angle+=10;
            velocity.y--;
        }
    }

    public void kill(){
        fall.play();
        isKilled = true;
        velocity.set((isReversed ? -0.5f : 0.5f), velocity.y);
    }
    public boolean isDead(){
        return ((position.y + HEIGHT < 0) && isKilled);
    }

    public boolean getReversed(){return isReversed;}
    public Rectangle getBounds() {return bounds;}
    public void loadTexture(){
        this.texture = new Texture(isReversed ? "duck_reversed.png" : "duck.png");
    }
}
