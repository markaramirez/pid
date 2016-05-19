package com.leerez.pid;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class EmptyMode extends MainGameScreen {
	
	public EmptyMode(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
		super(gam, cam);
	}
	
	@Override
	protected void spawnAk1() {
	}
	@Override
	protected void spawnAk2() {
	}
	@Override
	protected void spawnAk3() {
	}
	private void colorChange() {
		r1 -= .001;
		if (alien1millls < 330)
		{
			alien1millls = 330;
			a2millls = 580;
			a3millls = 430;
		}
		if (alien1millls <= 475 && alien1millls > 330)
		{
			alien1millls -= .005;
			a2millls -= .002;
			a3millls -= .008;
		}
		if (alien1millls <= 600 && alien1millls > 475)
		{
			alien1millls -= .01;
			a2millls -= .01;
			a3millls -= .01;
		}
		if (alien1millls > 600)
		{
			alien1millls -= .035;
			a2millls -= .037;
			a3millls -= .032;
		}
		lastColorChange = TimeUtils.millis();
	}

	public void nicemethod() {
		if (TimeUtils.millis() - lastAlienTime > alien1millls)
		{
			spawnAlien();
		}
		if (TimeUtils.millis() - la2t > a2millls)
		{
			spawnAlien2();
		}
		if (TimeUtils.millis() - la3t > a3millls)
		{
			spawnAlien3();
		}
		if (TimeUtils.millis() - lastColorChange > 50)
		{
			colorChange();
		}
	}
	
	public void moveCollision() {
		Iterator<Rectangle> iter = aliens.iterator();
		while (iter.hasNext()) {
			Rectangle alien = iter.next();
			alien.y -= 450 * Gdx.graphics.getDeltaTime();
			if (alien.y + 64 < 0)
			{
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
			alien2.y -= 470 * Gdx.graphics.getDeltaTime();
			if (alien2.y + 64 < 0)
			{
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
			alien3.y -= 490 * Gdx.graphics.getDeltaTime();
			if (alien3.y + 64 < 0)
			{
				iter3.remove();
				dodges++;
			}
			if (alien3.overlaps(ship)) {
				Gdx.input.vibrate(500);
				lives--;
				iter3.remove();
			}
		}
	}
	
	public void dieMethod() {
		GameOver.GOdodges = dodges;
		if(dodges > pidPrefs.getHighscore())
		{
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
				ship.x -= 500 * Gdx.graphics.getDeltaTime();
			else
				ship.x += 500 * Gdx.graphics.getDeltaTime();
		}
		if (ship.x < 0)
			ship.x = 0;
		if (ship.x > WORLD_WIDTH - 64)
			ship.x = WORLD_WIDTH - 64;
		alien1millls = 750;
		a2millls = 800;
		a3millls = 850;
		nicemethod();
		moveCollision();
		if (lives <= 0)
		{
			dieMethod();
			game.setScreen(new GameOver(game, camera));
			dispose();
		}
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		white.draw(game.batch, "Dodges: " + dodges, 0, WORLD_HEIGHT - 10);
		game.batch.draw(shipImage, ship.x, ship.y);
		for (Rectangle alien : aliens) {
			game.batch.draw(alienImage, alien.x, alien.y);
		}
		for (Rectangle alien2 : aliens2) {
			game.batch.draw(alien2Image, alien2.x, alien2.y);
		}
		for (Rectangle alien3 : aliens3) {
			game.batch.draw(alien3Image, alien3.x, alien3.y);
		}
		for (Rectangle ak1 : aks1) {
			game.batch.draw(killer1Image, ak1.x, ak1.y);
		}
		for (Rectangle ak2 : aks2) {
			game.batch.draw(killer2Image, ak2.x, ak2.y);
		}
		for (Rectangle ak3 : aks3) {
			game.batch.draw(killer3Image, ak3.x, ak3.y);
		}
		game.batch.end();
	}
 
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
 
	@Override
	public void show() {
		if(pidPrefs.getMusicPref())
		{
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

