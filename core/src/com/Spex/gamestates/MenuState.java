package com.Spex.gamestates;

import java.util.ArrayList;

import Menus.Menu;

import com.Spex.entities.Player;
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

public class MenuState extends Menu
{
    private SpriteBatch        batch;
    private BitmapFont         font_default;
    private BitmapFont         font_highlight;
    private BitmapFont         font;

    // Menu index
    private static final int   MAIN_PLAY    = 0;
    private static final int   MAIN_OPTIONS = 1;
    private static final int   MAIN_ABOUT = 2;
    private static final int   MAIN_EXIT    = 3;

    private ArrayList<Star>    stars;
    private int                numStars     = 2000;
    private ShapeRenderer      sr;
    private OrthographicCamera playerCam;
    private Player             spaceship;

    // Menu list
    private ArrayList<Integer> mainMenu     = new ArrayList<Integer>();

    // Menu selector
    private int                mainSelector = 0;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);

        // Background stars
        sr = new ShapeRenderer();
        stars = new ArrayList<Star>();
        createStars();

        // Set spaceship
        spaceship = new Player(0, 0, gsm);
        spaceship.setMenu(true);

        // Set camera
        playerCam = new OrthographicCamera(Spex.WIDTH, Spex.HEIGHT);
        playerCam.update();

        batch = new SpriteBatch();

        // Font setup
        font_default = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
        font_highlight = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_highlight.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_highlight.png"), false);

        font_default.setColor(Color.WHITE);
        font_highlight.setColor(Color.WHITE);

        font = font_default;

        // Build menus
        mainMenu.add(MAIN_PLAY);
        mainMenu.add(MAIN_OPTIONS);
        mainMenu.add(MAIN_ABOUT);
        mainMenu.add(MAIN_EXIT);
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
        // Update Stars
        for (int i = 0; i < stars.size(); i++)
        {
            float xMin = -Spex.HEIGHT;
            float xMax = Spex.HEIGHT;
            float yMin = -Spex.WIDTH;
            float yMax = Spex.WIDTH;

            stars.get(i).update(xMin, yMin, xMax, yMax, 0, -150, dt);
        }

        spaceship.update(dt);

        handleInput();
    }

    @Override
    public void draw()
    {
        // Draw Stars
        playerCam = new OrthographicCamera(Spex.WIDTH, Spex.HEIGHT);
        sr.setProjectionMatrix(playerCam.combined);
        for (int i = 0; i < stars.size(); i++)
        {
            stars.get(i).draw(sr);
        }

        spaceship.draw(sr);

        sr.end();

        batch.begin();

        // Resize the font
        font_default.setScale(0.5f, 0.5f);
        font_highlight.setScale(0.5f, 0.5f);

        // Draw menu items
        font.draw(batch, "SpEx - Main Menu", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 7);

        font = (mainSelector == MenuState.MAIN_PLAY ? font_highlight : font_default);
        font.draw(batch, "Play", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 5);

        font = (mainSelector == MenuState.MAIN_OPTIONS ? font_highlight : font_default);
        font.draw(batch, "Options", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 4);
        
        font = (mainSelector == MenuState.MAIN_ABOUT ? font_highlight : font_default);
        font.draw(batch, "About", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 3);

        font = (mainSelector == MenuState.MAIN_EXIT ? font_highlight : font_default);
        font.draw(batch, "Exit", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 2);

        font = font_default;
        batch.end();
    }

    public void inputMenu(int action)
    {
        switch (action)
        {
            case Menu.UP:
                if (mainSelector > 0)
                {
                    mainSelector--;
                }
                break;
            case Menu.DOWN:
                if (mainSelector < mainMenu.size() - 1)
                {
                    mainSelector++;
                }
                break;
            case Menu.SELECT:
                switch (mainSelector)
                {
                    case MenuState.MAIN_PLAY:
                        gsm.setState(GameStateManager.NEWGAME);
                        break;
                    case MenuState.MAIN_OPTIONS:
                        gsm.setState(GameStateManager.OPTIONS);
                        break;
                    case MenuState.MAIN_ABOUT:
                        gsm.setState(GameStateManager.ABOUT);
                        break;
                    case MenuState.MAIN_EXIT:
                        gsm.setState(GameStateManager.EXIT);
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void dispose()
    {
        sr.dispose();
        batch.dispose();
        font.dispose();
        font_default.dispose();
        font_highlight.dispose();
    }

}
