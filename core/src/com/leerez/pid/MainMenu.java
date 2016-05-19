package com.leerez.pid;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import sun.security.jgss.GSSCaller;

public class MainMenu implements Screen{
	
	final TIDS game;
	public Stage stage;
	public TextureAtlas atlas;
	public Skin skin;
	public BitmapFont white, bigger;
	public Label heading, rank;
	public Table table;
	Texture alienImage;
	Array<Rectangle> aliens;
	Sound selectSound;
	Music mmdrone;
	TextButton buttonManual, buttonPlay, buttonQuit, buttonSettings;
	long lastAlienTime;
	OrthographicCameraWithVirtualViewport camera;
	float assetSize, textureSize;
    float MYwidth, MYheight;

	public MainMenu(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        camera = cam;
		assetSize = camera.virtualViewport.getWidth() * 0.08f;
        textureSize = camera.virtualViewport.getWidth() * 0.1f;
        MYwidth = camera.virtualViewport.getWidth();
        MYheight = camera.virtualViewport.getHeight();
		game = gam;
		stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(stage);
		white = new BitmapFont(Gdx.files.internal("white.fnt"));
		bigger = new BitmapFont(Gdx.files.internal("bigger.fnt"));
		selectSound = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
		mmdrone = Gdx.audio.newMusic(Gdx.files.internal("PIDmm.wav"));
		//falling red blocks
		aliens = new Array<Rectangle>();
		alienImage = new Texture(Gdx.files.internal("markalien.png"));
		spawnAlien();
		//end that stuff
		mmdrone.setLooping(true);
		mmdrone.setVolume(.25f);
		atlas = new TextureAtlas("button.pack");
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setBounds(0 , 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonDown");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = - 1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.BLACK;
		LabelStyle headingStyle = new LabelStyle(bigger, Color.WHITE);
		heading = new Label("PLUMMET\nINTO\nDARKNESS", headingStyle);
		buttonQuit = new TextButton("QUIT", textButtonStyle); 
		buttonQuit.pad(25);
		buttonQuit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(pidPrefs.getSoundPref()) {
					selectSound.play();
				}
				Gdx.app.exit();
			}
		});
		buttonPlay = new TextButton("PLAY", textButtonStyle); 
		buttonPlay.pad(25);
		buttonPlay.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(pidPrefs.getSoundPref()) {
					selectSound.play();
				}
				game.setScreen(new ModeChooser(game, camera));
				dispose();
			}
		});
		buttonManual = new TextButton("HOW TO PLAY", textButtonStyle); 
		buttonManual.pad(25);
		buttonManual.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(pidPrefs.getSoundPref()) {
					selectSound.play();
				}
				game.setScreen(new Test(game));
			}
		});
		buttonSettings = new TextButton("SETTINGS", textButtonStyle); 
		buttonSettings.pad(25);
		buttonSettings.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (pidPrefs.getSoundPref()) {
					selectSound.play();
				}
				game.setScreen(new Settings(game, camera));
				dispose();
			}
		});
		table.add(heading).left();
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).spaceBottom(15);
		table.row();
		table.add(buttonManual);
		table.getCell(buttonManual).spaceBottom(15);
		table.row();
		table.add(buttonSettings);
		table.getCell(buttonSettings).spaceBottom(15);
		table.row();
		table.add(buttonQuit);
		stage.addActor(table);
	}

	private void spawnAlien() {
		Rectangle alien = new Rectangle();
		alien.x = MathUtils.random(0, MYwidth - assetSize);
		alien.y = MYheight + 100;
		alien.width = assetSize;
		alien.height = assetSize;
		aliens.add(alien);
		lastAlienTime = TimeUtils.millis();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		Iterator<Rectangle> iter = aliens.iterator();
		while (iter.hasNext()) {
			Rectangle alien = iter.next();
			alien.y -= 450 * Gdx.graphics.getDeltaTime();
			if (alien.y + assetSize < 0) {
				iter.remove();
			}
		}
		if (TimeUtils.millis() - lastAlienTime > 1500) {
			spawnAlien();
		}
		stage.draw();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		white.draw(game.batch, "Rank: " + pidPrefs.getRank(), 0, MYheight);
		white.draw(game.batch, "Total XP: " + pidPrefs.getXP(), 0, MYheight - 30);
		white.draw(game.batch, "XP until next rank-up: " + Leveler.getNeeded(), 0, MYheight - 60);
		for (Rectangle alien : aliens) {
			game.batch.draw(alienImage, alien.x, alien.y, textureSize, textureSize);
		}
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		if(pidPrefs.getMusicPref())
		{
			mmdrone.play();
		}
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
		stage.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		mmdrone.dispose();
	}

}
