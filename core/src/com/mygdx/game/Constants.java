package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ArrayMap;
import com.mygdx.game.guns.Gun;
import com.mygdx.game.guns.LaserGunR_1;
import com.mygdx.game.guns.LaserGunWeak;
import com.mygdx.game.guns.RocketGun1;
import com.mygdx.game.ships.BasicShipG_1;
import com.mygdx.game.ships.RedShipBase;
import com.mygdx.game.ships.Ship;

public class Constants {
    public static ArrayMap<String,TextureAtlas> textureAtlases;
    public static ArrayMap<String, Texture> textures;
    public static ArrayMap<String, Gun> guns;
    public static ArrayMap<String, Ship> ships;
    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public static BitmapFont font,titleFont;

    public static float smallButtonWidth;
    public static float smallButtonHeight;

    public static float middleButtonWidth;
    public static float middleButtonHeight;

    public static void initConstants(){
        initFonts();
        initButtonSizes();
        initTextureAtlas();
        initTextures();
        initGuns();
        initShips();
    }

    private static void initButtonSizes(){
        smallButtonWidth = Gdx.graphics.getWidth() * 0.1f;
        smallButtonHeight = smallButtonWidth / 2;

        middleButtonWidth = Gdx.graphics.getWidth() * 0.3f;
        middleButtonHeight = middleButtonWidth / 5;
    }

    private static void initTextureAtlas(){
        textureAtlases = new ArrayMap<>();
        textureAtlases.put("laser explosion",new TextureAtlas(Gdx.files.internal("Laser " +
                "Exposion.atlas")));
        textureAtlases.put("explosion",new TextureAtlas(Gdx.files.internal("Asteroid Explosion" +
                ".atlas")));
    }

    private static void initGuns(){
        guns = new ArrayMap<>();
        guns.put("Laser gun R0",new LaserGunWeak());
        guns.put("Laser gun R1",new LaserGunR_1());
        guns.put("Rocket gun",new RocketGun1());
    }

    private static void initShips(){
        ships = new ArrayMap<>();
        ships.put("Red ship",new RedShipBase());
        ships.put("Green ship", new BasicShipG_1());
    }

    private static void initTextures(){
        textures = new ArrayMap<>();
        textures.put("red ship",new Texture("red1.png"));
        textures.put("button move",new Texture("Button Move.png"));
        textures.put("button attack",new Texture("Button Attack.png"));
        textures.put("button left",new Texture("Button Left.png"));
        textures.put("button right",new Texture("Button Right.png"));
        textures.put("health bonus",new Texture("Health Bonus.png"));
        textures.put("button start menu",new Texture("Start Button.png"));
        textures.put("button exit",new Texture("Exit Button.png"));
        textures.put("button shop",new Texture("Shop Button.png"));
        textures.put("shop background",new Texture("Shop Background.png"));
        textures.put("button buy",new Texture("Button buy.png"));
        textures.put("button back",new Texture("Button back.png"));
        textures.put("button use",new Texture("Button use.png"));
        textures.put("button start",new Texture("Button start.png"));
        textures.put("money",new Texture("Money.png"));
        textures.put("laser bullet",new Texture("LL.jpg"));
        textures.put("rocket bullet",new Texture("Rocket.png"));
        textures.put("laser turret",new Texture("Turret1.jpg"));
        textures.put("rocket turret",new Texture("Turret1.jpg"));
        textures.put("button menu",new Texture("Button menu.png"));
        textures.put("button again",new Texture("Button again.png"));

    }

    private static void initFonts(){
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Koulen-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        font = generator.generateFont(parameter);
        parameter.size = 60;
        titleFont = generator.generateFont(parameter);
    }

    public static void dispose(){
        for(int i = 0; i < textureAtlases.size;i++){
            textureAtlases.getValueAt(i).dispose();
        }
        for (int i = 0; i < textures.size;i++){
            textures.getValueAt(i).dispose();
        }
        textures.clear();
        textureAtlases.clear();
    }
}
