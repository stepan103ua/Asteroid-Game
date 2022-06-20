package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.asteroids.Asteroid;
import com.mygdx.game.asteroids.EasyEnemy;
import com.mygdx.game.asteroids.Enemy;
import com.mygdx.game.bonuses.Bonus;
import com.mygdx.game.bullets.Bullet;
import com.mygdx.game.states.GameState;

public class Wave{
    public static Array<Enemy> enemies;
    public static Array<Bonus> bonuses;
    public int waveLevel = 0;
    private final Timer asteroidTimer,shootersTimer;
    private boolean isTimeToSpawnAsteroids,isTimeToSpawnShooters;
    public static Array<AnimationPlayer> animations;
    private final Player player;
    Timer.Task task,spawnShootersTask;

    public Wave(final Player player){
        this.player = player;
        enemies = new Array<>();
        bonuses = new Array<>();
        animations = new Array<>();
        asteroidTimer = new Timer();
        shootersTimer = new Timer();
        spawnWave();
        task = new Timer.Task() {
            @Override
            public void run() {
                spawnWave();
                isTimeToSpawnAsteroids = true;
            }
        };
        spawnShootersTask = new Timer.Task() {
            @Override
            public void run() {
                spawnShooters();
                isTimeToSpawnShooters = true;
            }
        };
        isTimeToSpawnAsteroids = true;
        isTimeToSpawnShooters = true;
    }

    public void update(float delta){
        waveLevel = (int)GameState.time / 60;
        for (int i = 0; i < enemies.size; i++) {
            Enemy asteroid = enemies.get(i);
            asteroid.update(delta);
            if(asteroid.isOutOfRange()) {
                enemies.removeIndex(i);
                continue;
            }

            if(player.currentShip.checkCollision(enemies.get(i))){
                animations.add(new AnimationPlayer("explosion",
                        asteroid.getX()+ asteroid.size/2,asteroid.getY()+asteroid.size/2,1/10f,
                        128));
                player.currentScore -= (int) enemies.get(i).size;
                enemies.removeIndex(i);
                continue;
            }
            if (asteroid.destroy()) {

                animations.add(new AnimationPlayer("explosion",
                        asteroid.getX()+ asteroid.size/2,asteroid.getY()+asteroid.size/2,1/10f,
                        128));
                player.currentScore += (int) enemies.get(i).size;
                enemies.removeIndex(i);

            }
        }

        for(int i = 0; i < bonuses.size; i++){
            bonuses.get(i).update(delta);
            if(player.currentShip.takeBonus(bonuses.get(i))){
                bonuses.removeIndex(i);
            }
        }

        if (isTimeToSpawnAsteroids){
            isTimeToSpawnAsteroids = false;
            asteroidTimer.scheduleTask(task,10f);
            asteroidTimer.start();
        }

        if (isTimeToSpawnShooters){
            isTimeToSpawnShooters = false;
            shootersTimer.scheduleTask(spawnShootersTask,60f);
            shootersTimer.start();
        }
        for (int i = 0; i < animations.size; i++){

            animations.get(i).update(delta);
            if(animations.get(i).isOver()){
                animations.removeIndex(i);
            }
        }

    }

    public void render(SpriteBatch batch){
        for(Enemy enemy : enemies){
            enemy.render(batch);
        }

        for(Bonus bonus : bonuses){
            bonus.render(batch);
        }

        for (AnimationPlayer animation : animations){
            animation.render(batch);
        }
    }


    public static boolean IS_OVERLAPPING(Bullet bullet){
        for(Enemy enemy : enemies){
            if(bullet.isOverlapping(enemy)){
                enemy.takeDamage(bullet.getDamage());
                return true;
            }
        }
        return false;
    }

    private void spawnWave(){
        // top
        int maxAsteroids = 7 + waveLevel;
        int minAsteroids = waveLevel;
        for (int i = 0; i < MathUtils.random(minAsteroids,maxAsteroids); i++){
            float angle = MathUtils.random(110f,250f);
            float x = MathUtils.random(-2000f,2000f);
            float y = MathUtils.random(2000f,2100f);
            enemies.add(new Asteroid(angle,x,y));
        }
        // bottom
        for (int i = 0; i < MathUtils.random(minAsteroids,maxAsteroids); i++){
            float angle = MathUtils.random(70f,-70f);
            float x = MathUtils.random(-2000f,2000f);
            float y = MathUtils.random(-2000f,-2100f);
            enemies.add(new Asteroid(angle,x,y));
        }

        // left
        for (int i = 0; i < MathUtils.random(minAsteroids,maxAsteroids); i++){
            float angle = MathUtils.random(-30f,-150f);
            float x = MathUtils.random(-2100f,-2000f);
            float y = MathUtils.random(-2000f,2000f);
            enemies.add(new Asteroid(angle,x,y));
        }

        // right
        for (int i = 0; i < MathUtils.random(minAsteroids,maxAsteroids); i++){
            float angle = MathUtils.random(30f,150f);
            float x = MathUtils.random(2000f,2100f);
            float y = MathUtils.random(-2000f,2000f);
            enemies.add(new Asteroid(angle,x,y));
        }

    }

    private void spawnShooters(){
        for (int i = 0; i < MathUtils.random(0,waveLevel); i++){
            float x = MathUtils.random(-2000f,2000f);
            float y = MathUtils.random(2000f,2100f);
            enemies.add(new EasyEnemy(x,y,player));
        }
        for (int i = 0; i < MathUtils.random(0,waveLevel); i++){
            float x = MathUtils.random(-2000f,2000f);
            float y = MathUtils.random(-2000f,-2100f);
            enemies.add(new EasyEnemy(x,y,player));
        }
        for (int i = 0; i < MathUtils.random(0,waveLevel); i++){
            float x = MathUtils.random(-2100f,-2000f);
            float y = MathUtils.random(-2000f,2000f);
            enemies.add(new EasyEnemy(x,y,player));
        }
        for (int i = 0; i < MathUtils.random(0,waveLevel); i++){
            float x = MathUtils.random(2000f,2100f);
            float y = MathUtils.random(-2000f,2000f);
            enemies.add(new EasyEnemy(x,y,player));
        }
    }
}
