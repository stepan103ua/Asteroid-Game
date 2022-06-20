package com.mygdx.game.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MainGame;
import com.mygdx.game.Player;
import com.mygdx.game.Wave;
import com.mygdx.game.bonuses.HealthBonus;
import com.mygdx.game.bullets.Bullet;
import com.mygdx.game.guns.Gun;
import com.mygdx.game.guns.LaserGunR_1;
import com.mygdx.game.guns.LaserGunWeak;
import com.mygdx.game.ships.RedShipBase;
import com.mygdx.game.ships.Ship;

public class EasyEnemy extends Enemy{
    private Ship ship;
    private Circle radar;
    private boolean onPoint = true;
    private Vector2 pointToMove;
    private Player target;
    private Circle bounds;
    public EasyEnemy(float x, float y, Player target) {
        super();

        this.target = target;
        Array<Gun> guns = new Array<>();
        ship = new RedShipBase();
        ship.type = "enemy";
        ship.setPosition(x,y);
        health = ship.getMaxHealth();
        guns.add(new LaserGunWeak(ship));
        ship.setGuns(guns);
        bounds = new Circle();
        size = Math.min(ship.getHeight(),ship.getWidth());
        bounds.setRadius(size/2);
        radar = new Circle();
        radar.setRadius(600f);
        pointToMove = new Vector2(x,y);
        ship.setNPC(true);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bounds.setPosition(ship.getX() + ship.getWidth()/2 ,ship.getY() + ship.getHeight());
        updateRadar();
        move();
        ship.update(delta);
        updateBullets();
        setX(ship.getX());
        setY(ship.getY());
        radar.setPosition(ship.getX(),ship.getY());

        health = ship.getHealth();
    }

    @Override
    public boolean destroy() {
        if (super.destroy()){
            Wave.bonuses.add(new HealthBonus(getX(),getY()));
            return true;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        ship.render(batch);
        float healthPercent = ship.getHealth()/ship.getMaxHealth();
        if((int)healthPercent != 1) {
            font.setColor(1, healthPercent, healthPercent, 1);
            font.draw(batch, (int) (healthPercent * 100f) + " %", getX() + size / 2f - 10f,
                    getY() + size + 10f);
        }
    }

    public void updateRadar(){
        if(!Intersector.overlaps(radar,target.currentShip.getBoundingRectangle())){
            if(ship.hasTargetNPC()) onPoint = true;
            ship.setHasTargetNPC(false);
            ship.setHoldAttack(false);
            return;
        }
        ship.setHoldAttack(true);
        ship.setHasTargetNPC(true);
        Ship targetShip = target.currentShip;
        float angle = MathUtils.atan2(ship.getY() - targetShip.getY(),
                ship.getX() - targetShip.getX()) * (180 / 3.14159f);
        ship.setRotation(angle + 90f);

    }

    public void move(){
        if (Math.abs(pointToMove.x - ship.getX()) <= 15 && Math.abs(pointToMove.y - ship.getY()) <= 15 ){
            onPoint = true;
        }
        if (onPoint){
            onPoint = false;
            float pointX = MathUtils.random(-MainGame.background.getWidth()/2,
                    MainGame.background.getWidth()/2);
            float pointY = MathUtils.random(-MainGame.background.getHeight()/2,
                    MainGame.background.getHeight()/2);
            pointToMove.set((int)pointX,(int)pointY);
            float angle = MathUtils.atan2(ship.getY() - pointY,
                    ship.getX() - pointX) * (180 / 3.14159f);
            ship.setRotation(angle + 90f);
        }
    }

    public void updateBullets(){
        for(Gun gun : ship.getGuns()){
            for(int i = 0;i < gun.getBullets().size;i++){
                if(target.currentShip.checkCollision(gun.getBullets().get(i))){
                    gun.getBullets().removeIndex(i);
                }
            }
        }
    }


    @Override
    public Circle getBounds() {
        return bounds;
    }

    @Override
    public void takeDamage(float damage) {
        ship.takeDamage(damage);

    }

    @Override
    public boolean isOutOfRange() {
        return false;
    }
}
