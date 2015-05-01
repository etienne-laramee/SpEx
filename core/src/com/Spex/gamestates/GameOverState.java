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

public class GameOverState extends Menu
{
    private SpriteBatch        batch;
    private BitmapFont         font_default;
    private BitmapFont         font_highlight;
    private BitmapFont         font;

    // Control IDs
    private static final int   UP               = 1000;
    private static final int   DOWN             = 2000;
    private static final int   SELECT           = 3000;
    private static final int   ESCAPE           = 4000;

    private ArrayList<Star>    stars;
    private int                numStars         = 2000;
    private ShapeRenderer      sr;
    private OrthographicCamera playerCam;

    private int                gameoverSelector = 0;
    private ArrayList<Integer> gameoverMenu     = new ArrayList<Integer>();

    public GameOverState(GameStateManager gsm)
    {
        super(gsm);

        // Background stars
        sr = new ShapeRenderer();
        stars = new ArrayList<Star>();
        createStars();

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

        handleInput();
    }

    @Override
    public void draw()
    {
        super.draw();

        batch.begin();

        // Resize the font
        font_default.setScale(0.5f, 0.5f);
        font_highlight.setScale(0.5f, 0.5f);

        font.draw(batch, "GAME OVER", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 7);

        font = font_default;
        batch.end();
    }

    @Override
    public void handleInput()
    {
        // Escape
        if (GameKeys.isPressed(GameKeys.ESCAPE))
        {
            inputMenu(GameOverState.ESCAPE);
        }
        // UP
        if (GameKeys.isPressed(GameKeys.UP))
        {
            inputMenu(GameOverState.UP);
        }
        // DOWN
        if (GameKeys.isPressed(GameKeys.DOWN))
        {
            inputMenu(GameOverState.DOWN);
        }
        // Select
        if (GameKeys.isPressed(GameKeys.ENTER))
        {
            inputMenu(GameOverState.SELECT);
        }
    }

    public void inputMenu(int action)
    {
        switch (action)
        {
            case GameOverState.SELECT:
                gsm.setState(GameStateManager.MENU);
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
