package com.leerez.pid;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOver implements Screen{

	public static long GOdodges;
	public static int GOxpgained;
	BitmapFont white;
	final TIDS game;
	public Stage stage;
	public TextureAtlas atlas;
	public Skin skin;
	TextButton buttonMM;
	Sound selectSound;
	OrthographicCameraWithVirtualViewport camera;
    float MYwidth, MYheight;
	
	public GameOver(final TIDS gam, OrthographicCameraWithVirtualViewport cam) {
		game = gam;
		camera = cam;
        MYwidth = camera.virtualViewport.getWidth();
        MYheight = camera.virtualViewport.getHeight();
		white = new BitmapFont(Gdx.files.internal("white.fnt"));
		selectSound = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas("button.pack");
		skin = new Skin(atlas);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("buttonUp");
		textButtonStyle.down = skin.getDrawable("buttonDown");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = - 1;
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.BLACK;
		buttonMM = new TextButton("Return to Main Menu", textButtonStyle); 
		buttonMM.setBounds(0, 0, Gdx.graphics.getWidth(), 125);
		buttonMM.addListener(new ClickListener(){
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
		stage.addActor(buttonMM);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act(delta);
		stage.draw();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		white.draw(game.batch, "You evaded " + GOdodges + " possible deaths", 0.025f * MYwidth, MYheight * 0.36562502f);
		white.draw(game.batch, "Highscore: " + pidPrefs.getHighscore(), 0.025f * MYwidth, MYheight * 0.34453127f);
		white.draw(game.batch, "You gained " + GOxpgained + " xp", 0.025f * MYwidth, MYheight * 0.28125f);
		white.draw(game.batch,"You need " + Leveler.getNeeded() + " xp until next rank-up", 0.025f * MYwidth, MYheight * 0.26015627f);
        white.draw(game.batch, "You current rank is " + pidPrefs.getRank(), 0.025f * MYwidth, MYheight * 0.2390625f);
		game.batch.end();
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
		stage.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
	}

}
