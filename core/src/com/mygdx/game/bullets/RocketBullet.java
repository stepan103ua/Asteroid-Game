package com.mygdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.AnimationPlayer;
import com.mygdx.game.Constants;
import com.mygdx.game.Wave;
import com.mygdx.game.asteroids.Enemy;

public class RocketBullet extends Bullet{
    private static Texture TEXTURE = Constants.textures.get("rocket bullet");
    private static float SPEED = 100f;
    private static final float WIDTH = 20f, HEIGHT = 40f;

    public RocketBullet(float startX, float startY, float angle) {
        super(TEXTURE, startX, startY, angle, SPEED, 1f, 1f);
        setSize(WIDTH,HEIGHT);
        setOrigin(0,0);
        animationPlayer = new AnimationPlayer("explosion",getX(),
                getY(),1/10f,90f);
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        bounds.set(getX(),getY(),WIDTH,HEIGHT);
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(new TextureRegion(TEXTURE),getX(),getY(),getOriginX(),getOriginY(),WIDTH,HEIGHT,1f,1f,
                getRotation());
    }

    @Override
    public boolean isOverlapping(Enemy object) {
        if(Intersector.overlaps(object.getBounds(),getBoundingRectangle())){
            Wave.animations.add(animationPlayer);
            return true;
        }
        return false;
    }

    @Override
    public AnimationPlayer getAnimationPlayer() {
        return animationPlayer;
    }
}
