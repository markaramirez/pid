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
    Texture shipImage, alienImage, alien2Image, alien3Image, killer1Image, killer2Image, killer3Image;
    OrthographicCameraWithVirtualViewport camera;
    // --- Use the newly implemented ortho camera with virtual viewport. --- //
    //OrthographicCameraWithVirtualViewport camera;
    //MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;
    Rectangle ship;
    Array<Rectangle> aliens, aliens2, aliens3, aks1, aks2, aks3;
    long lastAlienTime, la2t, la3t, lak1t, lak2t, lak3t;
    long lastColorChange;
    int dodges;
    long alien1millls, a2millls, a3millls, ak1millls, ak2millls, ak3millls, lastani, animillis;
    float r1;
    int lives;
    int coins;
    int xpgained;
    BitmapFont white;
    Music creep2000;
    Vector3 touchPos = new Vector3();
    Viewport viewport;
    float WORLD_WIDTH, WORLD_HEIGHT, assetSize, textureSize;
    boolean flip;

    public MainGameScreen(final TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        camera = cam;
        WORLD_WIDTH = camera.virtualViewport.getWidth();
        WORLD_HEIGHT = camera.virtualViewport.getHeight();
        assetSize = WORLD_WIDTH * .08f;
        textureSize = WORLD_WIDTH * .1f;
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
        lastani = TimeUtils.millis();
        animillis = 500;
        flip = false;
        // --- Created the virtual viewport to add to camera --- //
        //multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800, 480, 854, 600);
        //VirtualViewport virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        //camera = new OrthographicCameraWithVirtualViewport(virtualViewport);

        //camera.translate(camera.viewportWidth/2, camera.viewportHeight/2);
        //viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        //viewport.apply();

        ship = new Rectangle();
        ship.x = WORLD_WIDTH / 2 - (assetSize / 2);
        ship.y = 20;
        ship.width = assetSize;
        ship.height = assetSize;
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
        alien.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
        alien.y = WORLD_HEIGHT + assetSize + 10;
        alien.width = assetSize;
        alien.height = assetSize;
        aliens.add(alien);
        lastAlienTime = TimeUtils.millis();
    }

    protected void spawnAlien2() {
        Rectangle alien2 = new Rectangle();
        alien2.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
        alien2.y = WORLD_HEIGHT + 10 + assetSize;
        alien2.width = assetSize;
        alien2.height = assetSize;
        aliens2.add(alien2);
        la2t = TimeUtils.millis();
    }

    protected void spawnAlien3() {
        Rectangle alien3 = new Rectangle();
        alien3.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
        alien3.y = WORLD_HEIGHT + 10 + assetSize;
        alien3.width = assetSize;
        alien3.height = assetSize;
        aliens3.add(alien3);
        la3t = TimeUtils.millis();
    }

    protected void spawnAk1() {
        int ch1 = MathUtils.random(0, 3);
        if (ch1 == 1) {
            Rectangle ak1 = new Rectangle();
            ak1.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
            ak1.y = WORLD_HEIGHT + 10 + assetSize;
            ak1.width = assetSize;
            ak1.height = assetSize;
            aks1.add(ak1);
        }
        lak1t = TimeUtils.millis();

    }

    protected void spawnAk2() {
        int ch2 = MathUtils.random(0, 3);
        if (ch2 == 1) {
            Rectangle ak2 = new Rectangle();
            ak2.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
            ak2.y = WORLD_HEIGHT + 10 + assetSize;
            ak2.width = assetSize;
            ak2.height = assetSize;
            aks2.add(ak2);
        }
        lak2t = TimeUtils.millis();
    }

    protected void spawnAk3() {
        int ch3 = MathUtils.random(0, 3);
        if (ch3 == 1) {
            Rectangle ak3 = new Rectangle();
            ak3.x = MathUtils.random(0, WORLD_WIDTH - assetSize);
            ak3.y = WORLD_HEIGHT + 10 + assetSize;
            ak3.width = assetSize;
            ak3.height = assetSize;
            aks3.add(ak3);
        }
        lak3t = TimeUtils.millis();
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
        // TODO Auto-generated method stub
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
