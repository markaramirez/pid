package com.leerez.pid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Iterator;

public class ModeChooser implements Screen {

    final TIDS game;
    public Stage stage;
    public TextureAtlas atlas;
    public Skin skin;
    public BitmapFont white, bigger;
    public Label heading, GSL, GS2L, GS3L, GS4L, ur1, ur2, ur3, ur4;
    public Table table, screen;
    Texture blockImage;
    ScrollPane scroll;
    Array<Rectangle> blocks;
    Sound selectSound;
    long lastBlockTime;
    TextButton buttonGS, buttonGS2, buttonBack, buttonL, buttonE, buttonE1, buttonE2, buttonE3;
    OrthographicCameraWithVirtualViewport camera;
    float MYwidth, MYheight, assetSize, textureSize;

    public ModeChooser(TIDS gam, OrthographicCameraWithVirtualViewport cam) {
        game = gam;
        camera = cam;
        MYwidth = camera.virtualViewport.getWidth();
        MYheight = camera.virtualViewport.getHeight();
        assetSize = MYwidth * .08f;
        textureSize = MYwidth * .1f;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        white = new BitmapFont(Gdx.files.internal("white.fnt"));
        bigger = new BitmapFont(Gdx.files.internal("bigger.fnt"));
        atlas = new TextureAtlas("button.pack");
        selectSound = Gdx.audio.newSound(Gdx.files.internal("select.wav"));
        blocks = new Array<Rectangle>();
        blockImage = new Texture(Gdx.files.internal("red.png"));
        spawnBlock();
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
        LabelStyle heading2Style = new LabelStyle(white, Color.WHITE);
        heading = new Label("Choose a journey", headingStyle);
        GSL = new Label("A Constant Pain.", heading2Style);//all horizontal blocks
        GS2L = new Label("A Steady Descent", heading2Style);//classic
        GS3L = new Label("A Speedy Departure.", heading2Style);//lightning
        GS4L = new Label("An itch to reach boundaries.", heading2Style);//touch left and right walls to score points
        ur1 = new Label("Unlocked at Rank 1", heading2Style);//classic
        ur2 = new Label("Unlocked at Rank 1", heading2Style);//lightning
        ur3 = new Label("Unlocked at Rank 3", heading2Style);//buttons
        ur4 = new Label("Unlocked at Rank 5.", heading2Style);//no killers
        heading.setBounds(0, Gdx.graphics.getHeight() - 100, 300, 100);
        buttonGS = new TextButton("Into the Void", textButtonStyle);
        buttonGS.pad(50);
        buttonGS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                if (pidPrefs.getRank() >= 3) {
                    game.setScreen(new HorizontalMode(game, camera));
                }
                dispose();
            }
        });
        buttonGS2 = new TextButton("Into the Darkness", textButtonStyle);
        buttonGS2.pad(50);
        buttonGS2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                game.setScreen(new NormalMode(game, camera));
                dispose();
            }
        });
        buttonL = new TextButton("Into the Abyss", textButtonStyle);
        buttonL.pad(50);
        buttonL.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                game.setScreen(new LightningMode(game, camera));
                dispose();
            }
        });
        buttonE = new TextButton("Into the Emptiness", textButtonStyle);
        buttonE.pad(50);
        buttonE.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                if (pidPrefs.getRank() >= 5) {
                    game.setScreen(new LeftRightMode(game, camera));
                }
                dispose();
            }
        });
        buttonE1 = new TextButton("Into the Struggle", textButtonStyle);
        buttonE1.pad(50);
        buttonE1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                if (pidPrefs.getRank() >= 5) {
                    game.setScreen(new HopeMode(game, camera));
                }
                dispose();
            }
        });
        buttonE2 = new TextButton("Into the Insanity", textButtonStyle);
        buttonE2.pad(50);
        buttonE2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                if (pidPrefs.getRank() >= 5) {
                    game.setScreen(new SeizureMode(game, camera));
                }
                dispose();
            }
        });
        buttonE3 = new TextButton("Into the Emptiness", textButtonStyle);
        buttonE3.pad(50);
        buttonE3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                if (pidPrefs.getRank() >= 5) {
                    game.setScreen(new LeftRightMode(game, camera));
                }
                dispose();
            }
        });
        buttonBack = new TextButton("Back", textButtonStyle);
        buttonBack.setBounds(0, 0, 125, 100);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (pidPrefs.getSoundPref()) {
                    selectSound.play();
                }
                game.setScreen(new MainMenu(game, camera));
                dispose();
            }
        });
        table.add(buttonGS2);
        table.row();
        table.add(GS2L);
        table.row();
        table.add(ur1);
        table.getCell(ur1).spaceBottom(170);
        table.row();
        table.add(buttonL);
        table.row();
        table.add(GS3L);
        table.row();
        table.add(ur2);
        table.getCell(ur2).spaceBottom(170);
        table.row();
        table.add(buttonGS);
        table.row();
        table.add(GSL);
        table.row();
        table.add(ur3);
        table.getCell(ur3).spaceBottom(170);
        table.row();
        table.add(buttonE);
        table.row();
        table.add(GS4L);
        table.row();
        table.add(ur4);
        table.getCell(ur4).spaceBottom(170);
        table.row();
        table.add(buttonE1);
        table.row();
        table.add(GS4L);
        table.row();
        table.add(ur4);
        table.getCell(ur4).spaceBottom(170);
        table.row();
        table.add(buttonE2);
        table.row();
        table.add(GS4L);
        table.row();
        table.add(ur4);
        table.getCell(ur4).spaceBottom(170);
        table.row();
        table.add(buttonE3);
        table.row();
        table.add(GS4L);
        table.row();
        table.add(ur4);
        table.getCell(buttonE3).spaceBottom(170);
        scroll = new ScrollPane(table);
        screen = new Table(skin);
        screen.setFillParent(true);
        screen.setPosition(0, -150);
        screen.add(scroll).fill().expand();
        stage.addActor(heading);
        stage.addActor(screen);
        stage.addActor(buttonBack);
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
        white.draw(game.batch, "Rank: " + pidPrefs.getRank(), MYwidth, MYheight);
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
        blockImage.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        white.dispose();
        bigger.dispose();
    }

}
