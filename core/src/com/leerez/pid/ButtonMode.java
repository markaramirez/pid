package com.leerez.pid;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

public class ButtonMode extends MainGameScreen{
	TextButton buttonLeft, buttonRight;
	public Stage stage;
	public TextureAtlas atlas;
	public Skin skin;
	boolean left, right;
	
	public ButtonMode(TIDS gam) {
		super(gam);
		stage = new Stage();
		atlas = new TextureAtlas("button.pack");
		skin = new Skin(atlas);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonDown");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.BLACK;
		buttonLeft = new TextButton("LEFT", textButtonStyle);
		buttonLeft.setBounds(0, 0, 125, 125);
		buttonLeft.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				left = true;
				right = false;
			}
		});
		buttonRight = new TextButton("RIGHT", textButtonStyle);
		buttonRight.setBounds(Gdx.graphics.getWidth() - 125, 0, 125, 125);
		buttonRight.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				right = true;
				left = false;
			}
		});
		stage.addActor(buttonLeft);
		stage.addActor(buttonRight);
	}
	
	private void colorChange() {
		r1 -= .001;
		if(ak1millls >= 5600)
		{
			ak1millls -= .00005;
			ak2millls -= .00002;
			ak3millls -= .00008;
		}
		else
		{
			ak1millls -= .00025;
			ak2millls -= .00028;
			ak3millls -= .00022;
		}
		if (ak1millls == 1200)
		{
			ak1millls = 1350;
			ak2millls = 1400;
			ak3millls = 1500;
		}
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
		if (TimeUtils.millis() - lak1t > ak1millls)
		{
			spawnAk1();
		}
		if (TimeUtils.millis() - lak2t > ak2millls)
		{
			spawnAk2();
		}
		if (TimeUtils.millis() - lak3t > ak3millls)
		{
			spawnAk3();
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
			alien.y -= 900 * Gdx.graphics.getDeltaTime();
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
			alien2.y -= 940 * Gdx.graphics.getDeltaTime();
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
			alien3.y -= 980 * Gdx.graphics.getDeltaTime();
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
		Iterator<Rectangle> iterk1 = aks1.iterator();
		while (iterk1.hasNext()) {
			Rectangle killer = iterk1.next();
			killer.y -= 500 * Gdx.graphics.getDeltaTime();
			if (killer.y + 64 < 0)
			{
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
			killer2.y -= 500 * Gdx.graphics.getDeltaTime();
			if (killer2.y + 64 < 0)
			{
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
			killer3.y -= 500 * Gdx.graphics.getDeltaTime();
			if (killer3.y + 64 < 0)
			{
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
		stage.act(delta);
		if (left)
		{
			ship.x -= 400 * Gdx.graphics.getDeltaTime();
		}
		if (right)
		{
			ship.x += 400 * Gdx.graphics.getDeltaTime();
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
			game.setScreen(new GameOver(game));
			dispose();
		}
		camera.update();
		stage.draw();
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
