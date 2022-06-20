package com.mygdx.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AnimationPlayer;
import com.mygdx.game.GameObject;
import com.mygdx.game.asteroids.Enemy;


public abstract class Bullet extends GameObject {

    private float speed;
    public float startX,startY;
    private float damage = 0;
    public String type;
    public Rectangle bounds;
    protected AnimationPlayer animationPlayer;
    Bullet(Texture texture, float startX,float startY,float angle, float speed,float scaleX,
           float scaleY){
        super(texture);
        bounds = new Rectangle();
        this.startX = startX;
        this.startY = startY;
        this.speed = speed;
        this.setPosition(startX,startY);
        this.setRotation(angle);
        this.setScale(scaleX,scaleY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        move(delta);
        animationPlayer.setX(getX());
        animationPlayer.setY(getY());
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    private void move(float delta){
        float dx = (float) (Math.cos((getRotation()+90) * 0.017453f) * 6) * delta * speed;
        float dy = (float) (Math.sin((getRotation()+90) * 0.017453f) * 6) * delta * speed;
        this.setPosition(getX()+dx,getY()+dy);
    }


    public abstract boolean isOverlapping(Enemy object);
    public abstract AnimationPlayer getAnimationPlayer();
}
