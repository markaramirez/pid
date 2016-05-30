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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import sun.security.jgss.GSSCaller;

public class MainMenu implements Screen {

    final TIDS game;
    public Stage stage;
    public TextureAtlas atlas;
    public Skin skin;
    public BitmapFont white, bigger;
    public Label heading;
    public Table table;
    Texture blockImage, mOn, sOn, mOff, sOff, mCur, sCur;
    Array<Rectangle> blocks;
    Sound selectSound;
    Music mmdrone;
    TextButton buttonPlay, buttonQuit;
    long lastBlockTime;
    OrthographicCameraWithVirtualViewport camera;
    float assetSize, textureSize, toggleSize;
    float MYwidth, MYheight;
    Image mToggle, sToggle;

    public MainMenu(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        camera = cam;
        assetSize = camera.virtualViewport.getWidth() * 0.08f;
        textureSize = camera.virtualViewport.getWidth() * 0.1f;
        MYwidth = camera.virtualViewport.getWidth();
        MYheight = camera.virtualViewport.getHeight();
        toggleSize = MYwidth * .175f;
        game = gam;
        stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        white = new BitmapFont(Gdx.files.internal("white.fnt"));
        bigger = new BitmapFont(Gdx.files.internal("bigger.fnt"));
        selectSound = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
        mmdrone = Gdx.audio.newMusic(Gdx.files.internal("PIDmm.wav"));
        //falling red blocks
        blocks = new Array<Rectangle>();
        blockImage = new Texture(Gdx.files.internal("red.png"));
        mOn = new Texture(Gdx.files.internal("music_icon.png"));
        mOff = new Texture(Gdx.files.internal("music_icon_X.png"));
        sOn = new Texture(Gdx.files.internal("sound_icon.png"));
        sOff = new Texture(Gdx.files.internal("sound_icon_X.png"));
        if (pidPrefs.getSoundPref()) sCur = sOn;
        else sCur = sOff;
        if (pidPrefs.getMusicPref()) mCur = mOn;
        else mCur = mOff;
        spawnBlock();
        //end that stuff
        mmdrone.setLooping(true);
        mmdrone.play();
        if (pidPrefs.getMusicPref()) mmdrone.setVolume(.25f);
        else mmdrone.setVolume(0.0f);
        mToggle = new Image(mCur);
        mToggle.setX(MYwidth - MYwidth * .98f);
        mToggle.setY(MYheight - MYheight * .98f);
        mToggle.setWidth(toggleSize);
        mToggle.setHeight(toggleSize);
        mToggle.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                pidPrefs.toggleMusic();
                if (pidPrefs.getMusicPref()) mCur = mOn;
                else mCur = mOff;
                mToggle.setDrawable(new SpriteDrawable(new Sprite(mCur)));
                if (pidPrefs.getMusicPref()) mmdrone.setVolume(.25f);
                else mmdrone.setVolume(0.0f);
                return true;
            }
        });
        sToggle = new Image(sCur);
        sToggle.setX(MYwidth - MYwidth * .85f);
        sToggle.setY(MYheight - MYheight * .98f);
        sToggle.setWidth(toggleSize);
        sToggle.setHeight(toggleSize);
        sToggle.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                pidPrefs.toggleSound();
                if (pidPrefs.getSoundPref()) sCur = sOn;
                else sCur = sOff;
                sToggle.setDrawable(new SpriteDrawable(new Sprite(sCur)));
                return true;
            }
        });
        atlas = new TextureAtlas("button.pack");
        skin = new Skin(atlas);
        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonUp");
        textButtonStyle.down = skin.getDrawable("buttonDown");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = white;
        textButtonStyle.fontColor = Color.BLACK;
        LabelStyle headingStyle = new LabelStyle(bigger, Color.WHITE);
        heading = new Label("PLUMMET\nINTO\nDARKNESS", headingStyle);
        buttonQuit = new TextButton("QUIT", textButtonStyle);
        buttonQuit.pad(50);
        buttonQuit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                Gdx.app.exit();
            }
        });
        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(50);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                game.setScreen(new ModeChooser(game, camera));
                dispose();
            }
        });
        table.add(heading).left();
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(15);
        table.row();
        table.add(buttonQuit);
        stage.addActor(table);
        stage.addActor(sToggle);
        stage.addActor(mToggle);
    }

    private void spawnBlock() {
        Rectangle block = new Rectangle();
        block.x = MathUtils.random(0, MYwidth - assetSize);
        block.y = MYheight + 100;
        block.width = assetSize;
        block.height = assetSize;
        blocks.add(block);
        lastBlockTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        stage.act(delta);
        Iterator<Rectangle> iter = blocks.iterator();
        while (iter.hasNext()) {
            Rectangle block = iter.next();
            block.y -= 450 * Gdx.graphics.getDeltaTime();
            if (block.y + assetSize < 0) {
                iter.remove();
            }
        }
        if (TimeUtils.millis() - lastBlockTime > 1500) {
            spawnBlock();
        }
        stage.draw();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        white.draw(game.batch, "Rank: " + pidPrefs.getRank(), MYwidth - (MYwidth * .99f), MYheight);
        white.draw(game.batch, "Total XP: " + pidPrefs.getXP(), MYwidth - (MYwidth * .99f), MYheight - (MYheight * (1 - 0.9789063f)));
        white.draw(game.batch, "XP until next rank-up: " + Leveler.getNeeded(), MYwidth - (MYwidth * .99f), MYheight - (MYheight * (1 - 0.9578125f)));
        for (Rectangle block : blocks) {
            game.batch.draw(blockImage, block.x, block.y, textureSize, textureSize);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        mmdrone.play();
        if (pidPrefs.getMusicPref()) mmdrone.setVolume(.25f);
        else mmdrone.setVolume(0.0f);
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
