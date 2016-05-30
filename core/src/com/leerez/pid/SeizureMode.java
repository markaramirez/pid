package com.leerez.pid;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class SeizureMode extends MainGameScreen {

    public SeizureMode(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        super(gam, cam);
    }

    long lastBCChange = TimeUtils.millis();

    private void colorChange() {
        r1 -= .00125;
        blockmillis = 50;
        lastColorChange = TimeUtils.millis();
    }

    public void timeUpdate() {
        if (TimeUtils.millis() - lastBulletTime > 25) {
            spawnBullet();
        }
        if (TimeUtils.millis() - lastBCChange > 500) {
            BCChange();
        }
        if (TimeUtils.millis() - lastBlockTime > blockmillis) {
            spawnBlock();
        }
        if (TimeUtils.millis() - lastColorChange > 50) {
            colorChange();
        }
        if (flip) {
            if (TimeUtils.millis() - lastani > animillis) {
                changeTextureSize();
            }
        } else {
            if (TimeUtils.millis() - lastani > animillis * 5) {
                changeTextureSize();
            }
        }
    }

    public void BCChange() {
        for (Block b : blocks) {
            b.color = MathUtils.random(1, 7);
        }
        lastBCChange = TimeUtils.millis();
    }

    public void moveCollision() {
        Iterator<Block> iter = blocks.iterator();
        while (iter.hasNext()) {
            Block block = iter.next();
            block.hitbox.y -= block.yspeed * 1.5 * Gdx.graphics.getDeltaTime();
            block.hitbox.x += block.xspeed * 1.3 * Gdx.graphics.getDeltaTime();
            if (block.hitbox.x + assetSize > WORLD_WIDTH || block.hitbox.x <= 0) {
                block.xspeed *= -1;
            }
            if (block.hitbox.y + assetSize < 0) {
                iter.remove();
                dodges++;
            }
            if (block.hitbox.overlaps(you)) {
                if (block.killer) {
                    iter.remove();
                    blocks.clear();
                    xpgained += 5;
                } else {
                    Gdx.input.vibrate(500);
                    iter.remove();
                    lives--;
                }
            }
        }
        Iterator<Rectangle> iter2 = bullets.iterator();
        while (iter2.hasNext()) {
            Rectangle bullet = iter2.next();
            bullet.y += 1300 * Gdx.graphics.getDeltaTime();
            if (bullet.y > WORLD_HEIGHT) iter2.remove();
        }
        iter = blocks.iterator();
        iter2 = bullets.iterator();
        while (iter.hasNext()) {
            Block block = iter.next();
            while (iter2.hasNext()) {
                Rectangle bullet = iter2.next();
                if (block.hitbox.overlaps(bullet)) {
                    if (iter.hasNext()) iter.remove();
                    if (iter2.hasNext()) iter2.remove();
                }
            }
            iter2 = bullets.iterator();
        }
    }

    public void dieMethod() {
        GameOver.GOdodges = dodges;
        GameOver.showScore = false;
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
                you.x -= 1000 * Gdx.graphics.getDeltaTime();
            else
                you.x += 1000 * Gdx.graphics.getDeltaTime();
        }
        if (you.x < 0)
            you.x = 0;
        if (you.x > WORLD_WIDTH - playerTextureSize)
            you.x = WORLD_WIDTH - playerTextureSize;
        timeUpdate();
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
        game.batch.draw(youImage, you.x, you.y, playerTextureSize, playerTextureSize);
        for (Block block : blocks) {
            if (block.color == 1) blockImage = redImage;
            if (block.color == 2) blockImage = blueImage;
            if (block.color == 3) blockImage = orangeImage;
            if (block.color == 4) blockImage = aquaImage;
            if (block.color == 5) blockImage = pinkImage;
            if (block.color == 6) blockImage = purpleImage;
            if (block.color == 7) blockImage = yellowImage;
            if (block.color == 8) blockImage = killerImage;
            game.batch.draw(blockImage, block.hitbox.x, block.hitbox.y, textureSize, textureSize);
        }
        for (Rectangle bullet : bullets) {
            game.batch.draw(youImage, bullet.x, bullet.y, bulletTSize, bulletTSize);
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
        youImage.dispose();
        blockImage.dispose();
        white.dispose();
        creep2000.dispose();
    }

}
