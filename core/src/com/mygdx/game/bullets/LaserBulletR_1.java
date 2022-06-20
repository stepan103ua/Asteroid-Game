package com.mygdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.AnimationPlayer;
import com.mygdx.game.Constants;
import com.mygdx.game.Wave;
import com.mygdx.game.asteroids.Enemy;


public class LaserBulletR_1 extends Bullet {
    private static final Texture TEXTURE = Constants.textures.get("laser bullet");
    private static final float SCALE_X = 0.5f;
    private static final float SCALE_Y = 0.5f;
    private static final float SPEED = 150f;

    public LaserBulletR_1(float startX, float startY, float angle) {
        super(TEXTURE, startX, startY, angle, SPEED, SCALE_X, SCALE_Y);
        this.setOrigin(10,20);
        animationPlayer = new AnimationPlayer("laser explosion",getX(),
                getY(),1/15f,30f);
    }


    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    public boolean isOverlapping(Enemy object){
        if(Intersector.overlaps(object.getBounds(),getBoundingRectangle()) && type != Enemy.TYPE){

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
