package com.leerez.pid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Settings implements Screen{

	final TIDS game;
	public Stage stage;
	public TextureAtlas atlas;
	public Skin skin;
	public BitmapFont white, bigger;
	public Table table;
	Sound selectSound;
	TextButton mButton, sButton, buttonBack;
	OrthographicCameraWithVirtualViewport camera;
	public Settings(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
		game = gam;
		camera = cam;
        System.out.println(Gdx.graphics.getWidth() +" "+ Gdx.graphics.getHeight());
		stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(stage);
		white = new BitmapFont(Gdx.files.internal("white.fnt"));
		bigger = new BitmapFont(Gdx.files.internal("bigger.fnt"));
		atlas = new TextureAtlas("button.pack");
		selectSound = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
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
		mButton = new TextButton("Toggle Music On/Off", textButtonStyle); 
		mButton.pad(25);
		mButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(pidPrefs.getSoundPref())
				{
					selectSound.play();
				}
				pidPrefs.toggleMusic();
			}
		});
		sButton = new TextButton("Toggle Sound On/Off", textButtonStyle); 
		sButton.pad(25);
		sButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(pidPrefs.getSoundPref())
				{
					selectSound.play();
				}
				pidPrefs.toggleSound();
			}
		});
		buttonBack = new TextButton("Back", textButtonStyle); 
		//buttonBack.setBounds(0, 0, 125, 100);
		buttonBack.pad(25);
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(pidPrefs.getSoundPref())
				{
					selectSound.play();
				}
				game.setScreen(new MainMenu(game, camera));
				dispose();
			}
		});
		table.add(mButton);
		table.row();
		table.getCell(mButton).spaceBottom(170);
		table.add(sButton);
		table.row();
		table.getCell(sButton).spaceBottom(280);
        table.add(buttonBack);
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
        stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		
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
		bigger.dispose();
	}


}
