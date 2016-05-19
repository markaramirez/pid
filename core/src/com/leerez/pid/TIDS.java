package com.leerez.pid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TIDS extends Game {
	
	SpriteBatch batch;
	BitmapFont font;
	private OrthographicCameraWithVirtualViewport camera;
    private MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;

    public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();

        multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800, 480, 854, 600);
        VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera = new OrthographicCameraWithVirtualViewport(virtualViewport);
		camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0f);

        this.setScreen(new MainMenu(this, camera));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {
        super.render();
        camera.update();
    }

	@Override
	public void resize(int width, int height) {
        super.resize(width, height);

        VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setVirtualViewport(virtualViewport);

        camera.updateViewport();
        // centers the camera at 0, 0 (the center of the virtual viewport)
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}