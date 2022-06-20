package com.mygdx.game.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Wave;
import com.mygdx.game.bonuses.MoneyBonus;

public class Asteroid extends Enemy {
    protected float speed;
    private float health;
    private static Texture TEXTURE = new Texture("asteroid1.png");
    private final float startX,startY;
    private float angle;
    private Circle bounds;
    public Asteroid(float angle,float x,float y) {
        super(TEXTURE);
        setPosition(x,y);
        this.angle = angle;
        startX = x;
        startY = y;
        size = MathUtils.random(32f,128f);
        bounds = new Circle();
        bounds.setRadius(size/2);
        setOrigin(size/2,size/2);
        setSize(size,size);
        speed = MathUtils.lerp(80f,10f,size/128);
        health = (MathUtils.random(0f,40f) + size) * MathUtils.random(7f,15f);
        maxHealth = health;
    }
    @Override
    public void render(SpriteBatch batch) {
        float healthPercent = health/maxHealth;
        if((int)healthPercent != 1) {
            font.setColor(1, healthPercent, healthPercent, 1);
            font.draw(batch, (int) (healthPercent * 100f) + " %", getX() + size / 2f - 10f,
                    getY() + size + 10f);
        }
        draw(batch);

    }

    public void takeDamage(float damage){
        health -= damage;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bounds.setPosition(getX()+size/2,getY()+size/2);
        move(delta);
    }

    public boolean destroy(){

        if (health <= 0){
            if(MathUtils.random(0,100) <= 25)
                Wave.bonuses.add(new MoneyBonus(getX(),getY()));
            return true;
        }
        return false;
    }

    @Override
    public Circle getBounds() {
        return bounds;
    }



    public void setDirection(float direction,float speed){
        angle = direction;
        this.speed = speed;
    }

    private void move(float delta){
        float dx = (float) (Math.cos((angle+90) * 0.017453f) * 6) * delta * speed;
        float dy = (float) (Math.sin((angle+90) * 0.017453f) * 6) * delta * speed;
        this.setPosition(getX()+dx,getY()+dy);
    }

    public boolean isOutOfRange(){
        float distance = (float) Math.sqrt(Math.pow(Math.abs(startY - getY()),2) + Math.pow(Math.abs(startX - getX()),2));
        return distance > 5000f;
    }
}
