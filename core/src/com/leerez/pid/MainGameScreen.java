package com.leerez.pid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGameScreen implements Screen {
    final TIDS game;
    Texture youImage, redImage, blueImage, orangeImage, killerImage, blockImage;
    OrthographicCameraWithVirtualViewport camera;
    // --- Use the newly implemented ortho camera with virtual viewport. --- //
    //OrthographicCameraWithVirtualViewport camera;
    //MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;
    Rectangle you;
    Array<Block> blocks;
    long lastBlockTime, lastColorChange, lastani;
    int dodges ,lives, coins, xpgained;
    float blockmillis, animillis, r1;
    BitmapFont white;
    Music creep2000;
    Vector3 touchPos = new Vector3();
    Viewport viewport;
    float WORLD_WIDTH, WORLD_HEIGHT, assetSize, textureSize, playerTextureSize;
    boolean flip;

    public MainGameScreen(final TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        camera = cam;
        WORLD_WIDTH = camera.virtualViewport.getWidth();
        WORLD_HEIGHT = camera.virtualViewport.getHeight();
        assetSize = WORLD_WIDTH * .08f;
        textureSize = WORLD_WIDTH * .1f;
        playerTextureSize = WORLD_WIDTH * .1f;
        this.game = gam;
        youImage = new Texture(Gdx.files.internal("you.png"));
        redImage = new Texture(Gdx.files.internal("red.png"));
        blueImage = new Texture(Gdx.files.internal("blue.png"));
        orangeImage = new Texture(Gdx.files.internal("orange.png"));
        killerImage = new Texture(Gdx.files.internal("redKiller.png"));
        creep2000 = Gdx.audio.newMusic(Gdx.files.internal("PIDv.1.wav"));
        creep2000.setLooping(true);
        white = new BitmapFont(Gdx.files.internal("white.fnt"));
        animillis = 150;
        flip = false;
        // --- Created the virtual viewport to add to camera --- //
        //multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800, 480, 854, 600);
        //VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        //camera = new OrthographicCameraWithVirtualViewport(virtualViewport);
        //camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
        //viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        //viewport.apply();
        blocks = new Array<Block>();
        you = new Rectangle();
        you.x = WORLD_WIDTH / 2 - (assetSize / 2);
        you.y = 20;
        you.width = assetSize;
        you.height = assetSize;
        r1 = 1;
        coins = 0;
        xpgained = 0;
        lives = 1;
        dodges = 0;
    }

    protected void spawnBlock() {
        Block block = new Block();
        block.hitbox.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
        block.hitbox.y = WORLD_HEIGHT + assetSize + 10;
        block.hitbox.width = assetSize;
        block.hitbox.height = assetSize;
        blocks.add(block);
        lastBlockTime = TimeUtils.millis();
    }

    protected void changeTextureSize() {
        if (flip) {
            textureSize = WORLD_WIDTH * .1f;
        } else {
            textureSize = WORLD_WIDTH * .12f;
        }
        flip = !flip;
        lastani = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
    }

    @Override
    public void resize(int width, int height) {
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0f);
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
