package com.mygdx.game.bonuses;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Constants;
import com.mygdx.game.Player;
import com.mygdx.game.ships.Ship;

public class HealthBonus extends Bonus {

    public HealthBonus(float x, float y) {
        super(Constants.textures.get("health bonus"));
        setPosition(x,y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }


    public void effect(Player player) {
        player.currentShip.addHealth(player.currentShip.getMaxHealth() * 0.1f);
        if(player.currentShip.getHealth() > player.currentShip.getMaxHealth())player.currentShip.setHealth(player.currentShip.getMaxHealth());
    }
}
