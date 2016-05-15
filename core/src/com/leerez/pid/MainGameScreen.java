package com.leerez.pid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGameScreen implements Screen
{
	final TIDS game;
	Texture shipImage, alienImage, alien2Image, alien3Image, killer1Image, killer2Image, killer3Image;
	OrthographicCamera camera;
	Rectangle ship;
	Array<Rectangle> aliens, aliens2, aliens3, aks1, aks2, aks3;
	long lastAlienTime, la2t, la3t, lak1t, lak2t, lak3t;
	long lastColorChange;
	int dodges;
	long alien1millls, a2millls, a3millls, ak1millls, ak2millls, ak3millls;
	float r1;
	int lives;
	int coins;
	int xpgained;
	BitmapFont white;
	Music creep2000;
	Vector3 touchPos = new Vector3();

    Viewport viewport;

    final float WORLD_WIDTH = Gdx.graphics.getWidth();
    final float WORLD_HEIGHT = Gdx.graphics.getHeight();

    float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
 
	public MainGameScreen(final TIDS gam) {
		this.game = gam;
		shipImage = new Texture(Gdx.files.internal("markship.png"));
		alienImage = new Texture(Gdx.files.internal("markalien.png"));
		alien2Image = new Texture(Gdx.files.internal("markalien2.png"));
		alien3Image = new Texture(Gdx.files.internal("markalien3.png"));
		killer1Image = new Texture(Gdx.files.internal("1killer.png"));
		killer2Image = new Texture(Gdx.files.internal("2killer.png"));
		killer3Image = new Texture(Gdx.files.internal("3killer.png"));
		creep2000 = Gdx.audio.newMusic(Gdx.files.internal("PIDv.1.wav"));
		creep2000.setLooping(true);
		white = new BitmapFont(Gdx.files.internal("white.fnt"));
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
        viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
		ship = new Rectangle();
		ship.x = WORLD_WIDTH / 2 - 32;
		ship.y = 20;
		ship.width = 64;
		ship.height = 64;
		alien1millls = 1500;
		a2millls = 1600;
		a3millls = 1700;
		ak1millls = 6500;
		ak2millls = 6500;
		ak3millls = 6500;
		r1 = 1;
		coins = 0;
		xpgained = 0;
		aliens = new Array<Rectangle>();
		aliens2 = new Array<Rectangle>();
		aliens3 = new Array<Rectangle>();
		aks1 = new Array<Rectangle>();
		aks2 = new Array<Rectangle>();
		aks3 = new Array<Rectangle>();
		lives = 1;
		dodges = 0;
		spawnAlien();
		la2t = TimeUtils.millis();
		la3t = TimeUtils.millis();
		lak1t = TimeUtils.millis();
		lak2t = TimeUtils.millis();
		lak3t = TimeUtils.millis();
	}
 
	protected void spawnAlien() {
		Rectangle alien = new Rectangle();
		alien.x = MathUtils.random(0, WORLD_WIDTH - 64);
		alien.y = WORLD_HEIGHT + 100;
		alien.width = 64;
		alien.height = 64;
		aliens.add(alien);
		lastAlienTime = TimeUtils.millis();
	}
	
	protected void spawnAlien2() {
		Rectangle alien2 = new Rectangle();
		alien2.x = MathUtils.random(0, WORLD_WIDTH- 64);
		alien2.y = WORLD_HEIGHT + 100;
		alien2.width = 64;
		alien2.height = 64;
		aliens2.add(alien2);
		la2t = TimeUtils.millis();
	}
	
	protected void spawnAlien3() {
		Rectangle alien3 = new Rectangle();
		alien3.x = MathUtils.random(0, WORLD_WIDTH - 64);
		alien3.y = WORLD_HEIGHT + 100;
		alien3.width = 64;
		alien3.height = 64;
		aliens3.add(alien3);
		la3t = TimeUtils.millis();
	}
	
	protected void spawnAk1() {
		int ch1 = MathUtils.random(0, 3);
		if(ch1 == 1)
		{
			Rectangle ak1 = new Rectangle();
			ak1.x = MathUtils.random(0, WORLD_WIDTH - 64);
			ak1.y = WORLD_HEIGHT + 100;
			ak1.width = 64;
			ak1.height = 64;
			aks1.add(ak1);
		}
		lak1t = TimeUtils.millis();
		
	}
	
	protected void spawnAk2() {
		int ch2 = MathUtils.random(0, 3);
		if(ch2 == 1)
		{
			Rectangle ak2 = new Rectangle();
			ak2.x = MathUtils.random(0, WORLD_WIDTH - 64);
			ak2.y = WORLD_HEIGHT + 100;
			ak2.width = 64;
			ak2.height = 64;
			aks2.add(ak2);
		}
		lak2t = TimeUtils.millis();
	}
	
	protected void spawnAk3() {
		int ch3 = MathUtils.random(0, 3);
		if(ch3 == 1)
		{
			Rectangle ak3 = new Rectangle();
			ak3.x = MathUtils.random(0, WORLD_WIDTH - 64);
			ak3.y = WORLD_HEIGHT + 100;
			ak3.width = 64;
			ak3.height = 64;
			aks3.add(ak3);
		}
		lak3t = TimeUtils.millis();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
