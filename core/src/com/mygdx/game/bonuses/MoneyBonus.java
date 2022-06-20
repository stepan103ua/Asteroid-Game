package com.mygdx.game.bonuses;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Constants;
import com.mygdx.game.Player;
import com.mygdx.game.ships.Ship;

public class MoneyBonus extends Bonus {
    public MoneyBonus(float x, float y) {
        super(Constants.textures.get("money"));
        setPosition(x,y);
    }

    @Override
    public void effect(Player player) {
        player.addMoney(1);
    }
}
