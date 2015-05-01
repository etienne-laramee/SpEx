package com.Spex.gamestates;

import java.util.ArrayList;

import Menus.Menu;

import com.Spex.entities.Star;
import com.Spex.game.Spex;
import com.Spex.managers.GameKeys;
import com.Spex.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class NextLevel extends Menu
{
    private SpriteBatch        batch;
    private BitmapFont         font_default;
    private BitmapFont         font_highlight;
    private BitmapFont         font;

    private int                level;

    private ArrayList<Star>    stars;
    private int                numStars = 2000;
    private ShapeRenderer      sr;
    private OrthographicCamera playerCam;

    public NextLevel(GameStateManager gsm, int level)
    {
        super(gsm);

        // Background stars
        sr = new ShapeRenderer();
        stars = new ArrayList<Star>();
        createStars();
        
        // Set camera
        playerCam = new OrthographicCamera(Spex.WIDTH, Spex.HEIGHT);
        playerCam.update();

        this.level = level;
        batch = new SpriteBatch();

        // Font setup
        font_default = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
        font_highlight = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_highlight.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_highlight.png"), false);

        font_default.setColor(Color.WHITE);
        font_highlight.setColor(Color.WHITE);

        font = font_default;

    }

    private void createStars()
    {
        for (int i = 0; i < numStars; i++)
        {
            stars.add(new Star((float) Math.random() * Spex.WIDTH * 2, (float) Math.random() * Spex.WIDTH * 2));
        }
    }

    @Override
    public void init()
    {
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);

        super.handleInput();
    }

    @Override
    public void draw()
    {
        super.draw();
        
        batch.begin();

        // Resize the font
        font_default.setScale(0.5f, 0.5f);
        font_highlight.setScale(0.5f, 0.5f);

        font = font_default;
        font.draw(batch, "Level " + level, Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 7);

        font = font_highlight;
        font.draw(batch, "Press ENTER to continue", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 4);

        font = font_default;
        batch.end();
    }

    @Override
    public void handleInput()
    {
        // Select
        if (GameKeys.isPressed(GameKeys.ENTER))
        {
            gsm.setState(GameStateManager.NEXTLEVEL);
        }
    }

    @Override
    public void dispose()
    {
        super.dispose();
        sr.dispose();
        batch.dispose();
        font.dispose();
        font_default.dispose();
        font_highlight.dispose();
    }

}
