package com.leerez.pid;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class LightningMode extends MainGameScreen {

    public LightningMode(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        super(gam, cam);
    }

    private void colorChange() {
        System.out.println(alien1millls);
        System.out.println(a2millls);
        System.out.println(a3millls);
        r1 -= .0005; //was .001
        System.out.println("ospeed" + ospeed);
        if(ospeed < 1800) {
            ospeed += .1;
        }
        System.out.println("ospeed" + ospeed);
        if (ak1millls >= 5600) {
            ak1millls -= .00005;
            ak2millls -= .00002;
            ak3millls -= .00008;
        } else {
            ak1millls -= .00025;
            ak2millls -= .00028;
            ak3millls -= .00022;
        }
        if (ak1millls == 1200) {
            ak1millls = 1350;
            ak2millls = 1400;
            ak3millls = 1500;
        }
        if (alien1millls < 330) {
            alien1millls = 180; //was 290
            a2millls = 380; //was 490
            a3millls = 280; //was 390
        }
        if (alien1millls <= 475 && alien1millls >= 330) {
            alien1millls -= .01; //was.005
            a2millls -= .007; //.002
            a3millls -= .013; //008
        }
        if (alien1millls <= 600 && alien1millls > 475) {
            alien1millls -= .015;
            a2millls -= .015;
            a3millls -= .015;
        }
        if (alien1millls > 600) {
            alien1millls -= .2; //.04
            a2millls -= .2; //.042
            a3millls -= .2; //.037
        }
        lastColorChange = TimeUtils.millis();
    }

    public void nicemethod() {
        if (TimeUtils.millis() - lastAlienTime > alien1millls) {
            spawnAlien();
        }
        if (TimeUtils.millis() - la2t > a2millls) {
            spawnAlien2();
        }
        if (TimeUtils.millis() - la3t > a3millls) {
            spawnAlien3();
        }
        if (TimeUtils.millis() - lak1t > ak1millls) {
            spawnAk1();
        }
        if (TimeUtils.millis() - lak2t > ak2millls) {
            spawnAk2();
        }
        if (TimeUtils.millis() - lak3t > ak3millls) {
            spawnAk3();
        }
        if (TimeUtils.millis() - lastColorChange > 10) {
            colorChange();
        }
        if(flip) {
            if (TimeUtils.millis() - lastani > animillis) {
                changeTextureSize();
            }
        }
        else {
            if (TimeUtils.millis() - lastani > animillis * 5) {
                changeTextureSize();
            }
        }
    }

    public void moveCollision() {
        Iterator<Rectangle> iter = aliens.iterator();
        while (iter.hasNext()) {
            Rectangle alien = iter.next();
            alien.y -= 980 * Gdx.graphics.getDeltaTime();
            if (alien.y + assetSize < 0) {
                iter.remove();
                dodges++;
            }
            if (alien.overlaps(ship)) {
                Gdx.input.vibrate(500);
                lives--;
                iter.remove();
            }
        }
        Iterator<Rectangle> iter2 = aliens2.iterator();
        while (iter2.hasNext()) {
            Rectangle alien2 = iter2.next();
            alien2.y -= 980 * Gdx.graphics.getDeltaTime();
            if(right1) alien2.x += 1000 * Gdx.graphics.getDeltaTime();
            if(!right1) alien2.x -= 1000 * Gdx.graphics.getDeltaTime();
            if(alien2.x + assetSize > WORLD_WIDTH) {
                right1 = false;
            }
            if(alien2.x <= 0) {
                right1 = true;
            }
            if (alien2.y + assetSize < 0) {
                iter2.remove();
                dodges++;
            }
            if (alien2.overlaps(ship)) {
                Gdx.input.vibrate(500);
                lives--;
                iter2.remove();
            }
        }
        Iterator<Rectangle> iter3 = aliens3.iterator();
        while (iter3.hasNext()) {
            Rectangle alien3 = iter3.next();
            alien3.y -= ospeed * Gdx.graphics.getDeltaTime();
            if (alien3.y + assetSize < 0) {
                iter3.remove();
                dodges++;
            }
            if (alien3.overlaps(ship)) {
                Gdx.input.vibrate(500);
                lives--;
                iter3.remove();
            }
        }
        Iterator<Rectangle> iterk1 = aks1.iterator();
        while (iterk1.hasNext()) {
            Rectangle killer = iterk1.next();
            killer.y -= 900 * Gdx.graphics.getDeltaTime();
            if (killer.y + assetSize < 0) {
                iterk1.remove();
            }
            if (killer.overlaps(ship)) {
                aliens.clear();
                xpgained += 5;
                iterk1.remove();
            }
        }
        Iterator<Rectangle> iterk2 = aks2.iterator();
        while (iterk2.hasNext()) {
            Rectangle killer2 = iterk2.next();
            killer2.y -= 900 * Gdx.graphics.getDeltaTime();
            if (killer2.y + assetSize < 0) {
                iterk2.remove();
            }
            if (killer2.overlaps(ship)) {
                aliens2.clear();
                xpgained += 5;
                iterk2.remove();
            }
        }
        Iterator<Rectangle> iterk3 = aks3.iterator();
        while (iterk3.hasNext()) {
            Rectangle killer3 = iterk3.next();
            killer3.y -= 900 * Gdx.graphics.getDeltaTime();
            if (killer3.y + assetSize < 0) {
                iterk3.remove();
            }
            if (killer3.overlaps(ship)) {
                aliens3.clear();
                xpgained += 5;
                iterk3.remove();
            }
        }
    }

    public void dieMethod() {
        GameOver.GOdodges = dodges;
        if (dodges > pidPrefs.getHighscore()) {
            pidPrefs.setHighscore(dodges);
        }
        xpgained += dodges / 2;
        GameOver.GOxpgained = xpgained;
        pidPrefs.setXP(pidPrefs.getXP() + xpgained);
        Leveler.updateRank();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(r1, r1, r1, r1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x < WORLD_WIDTH / 2)
                ship.x -= 1000 * Gdx.graphics.getDeltaTime();
            else
                ship.x += 1000 * Gdx.graphics.getDeltaTime();
        }
        if (ship.x < 0)
            ship.x = 0;
        if (ship.x > WORLD_WIDTH - playerTextureSize)
            ship.x = WORLD_WIDTH - playerTextureSize;
        nicemethod();
        moveCollision();
        if (lives <= 0) {
            dieMethod();
            game.setScreen(new GameOver(game, camera));
            dispose();
        }
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        white.draw(game.batch, "Dodges: " + dodges, 0, WORLD_HEIGHT - 10);
        game.batch.draw(shipImage, ship.x, ship.y, playerTextureSize, playerTextureSize);
        for (Rectangle alien : aliens) {
            game.batch.draw(alienImage, alien.x, alien.y, textureSize, textureSize);
        }
        for (Rectangle alien2 : aliens2) {
            game.batch.draw(alien2Image, alien2.x, alien2.y, textureSize, textureSize);
        }
        for (Rectangle alien3 : aliens3) {
            game.batch.draw(alien3Image, alien3.x, alien3.y, textureSize, textureSize);
        }
        for (Rectangle ak1 : aks1) {
            game.batch.draw(killer1Image, ak1.x, ak1.y, textureSize, textureSize);
        }
        for (Rectangle ak2 : aks2) {
            game.batch.draw(killer2Image, ak2.x, ak2.y, textureSize, textureSize);
        }
        for (Rectangle ak3 : aks3) {
                game.batch.draw(killer3Image, ak3.x, ak3.y, textureSize, textureSize);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        if (pidPrefs.getMusicPref()) {
            creep2000.play();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        shipImage.dispose();
        alienImage.dispose();
        alien2Image.dispose();
        alien3Image.dispose();
        killer1Image.dispose();
        killer2Image.dispose();
        killer3Image.dispose();
        white.dispose();
        creep2000.dispose();
    }

}
