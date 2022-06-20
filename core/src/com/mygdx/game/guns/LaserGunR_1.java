package com.mygdx.game.guns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Constants;
import com.mygdx.game.bullets.LaserBulletR_1;
import com.mygdx.game.ships.BasicShipG_1;
import com.mygdx.game.ships.Ship;

public class LaserGunR_1 extends Gun{
    private static final Texture TEXTURE = Constants.textures.get("laser turret");
    private static final float COOLDOWN = 0.15f;
    private static final float BASE_DAMAGE = 100f;
    private static final float RANGE = 1200f;


    public LaserGunR_1(Ship ship) {
        super(TEXTURE, ship, COOLDOWN, BASE_DAMAGE, RANGE);
    }

    public LaserGunR_1(){
        super(TEXTURE,COOLDOWN,BASE_DAMAGE,RANGE);
    }


    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void attack(float startX, float startY) {
        if (!isAbleToAttack()) return;
        this.bullets.add(new LaserBulletR_1(startX, startY, ship.getRotation()));
        bullets.peek().type = ship.type;
        float damage = BASE_DAMAGE + MathUtils.random(-15f,15f);
        bullets.peek().setDamage(damage);
    }

    @Override
    public Object clone() {
        return new LaserGunR_1();
    }
}
