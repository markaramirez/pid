package com.leerez.pid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jae on 12/21/2015.
 */

public class Test implements Screen {
    TIDS game;
    public static final int v = Gdx.graphics.getWidth();
    public static final int h = Gdx.graphics.getHeight();
    Viewport viewport;
    PerspectiveCamera camera;
    Rectangle alien;
    Texture alienImage;

    Test(TIDS gam){
        this.game = gam;
        alien = new Rectangle();
        camera = new PerspectiveCamera();
         viewport = new FitViewport(v,h, camera);

        alien.width = 64;
        alien.height = 64;
        alien.x = 100;
        alien.y = 100;
        alienImage = new Texture(Gdx.files.internal("markalien.png"));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(alienImage, alien.x, alien.y);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
