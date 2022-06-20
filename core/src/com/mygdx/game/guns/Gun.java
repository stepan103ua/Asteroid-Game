package com.mygdx.game.guns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameObject;
import com.mygdx.game.Wave;
import com.mygdx.game.bullets.Bullet;
import com.mygdx.game.ships.Ship;

public abstract class Gun extends GameObject{
    private Texture texture;
    private float coolDown;
    private float currentCoolDown;
    private float damage;
    private float range;
    protected Ship ship;
    protected Array<Bullet> bullets;

    Gun(Texture texture, float coolDown, float damage,
        float range){
        this.texture = texture;
        this.coolDown = coolDown;
        currentCoolDown = coolDown;
        this.damage = damage;
        this.range = range;
        this.setScale(0.5f,0.5f);
        this.setOrigin(getWidth()/2f,getHeight()/2f);
        this.bullets = new Array<>();
    }
    Gun(Texture texture, Ship ship, float coolDown, float damage,
               float range){
        super(texture);
        this.texture = texture;
        this.coolDown = coolDown;
        currentCoolDown = coolDown;
        this.damage = damage;
        this.ship = ship;
        this.range = range;
        this.setScale(0.5f,0.5f);
        this.setOrigin(getWidth()/2f,getHeight()/2f);
        this.bullets = new Array<>();
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }


    @Override
    public void update(float delta){
        super.update(delta);
        currentCoolDown += delta;
        this.setRotation(ship.getRotation());
        updateBullets(delta);


    }

    public void updateBullets(float delta){
        for (int i = 0; i<bullets.size;i++){
            bullets.get(i).update(delta);
            if (isOutOfRange(bullets.get(i)) || Wave.IS_OVERLAPPING(bullets.get(i)))
            {
                bullets.removeIndex(i);
                continue;
            }

        }
    }

    @Override
    public void render(SpriteBatch batch) {
        for (Bullet i : bullets){
            i.render(batch);
        }
    }

    protected boolean isAbleToAttack(){
        if (currentCoolDown >= coolDown){
            currentCoolDown = 0;
            return true;
        }
        return false;
    }

    public abstract void attack(float startX,float startY);

    protected boolean isOutOfRange(Bullet bullet){
        float distance = (float) Math.sqrt(Math.pow(Math.abs(bullet.startY - bullet.getY()),2) + Math.pow(Math.abs(bullet.startX - bullet.getX()),2));
        if (distance > range) return true;
        return false;
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }

    public abstract Object clone();
}
