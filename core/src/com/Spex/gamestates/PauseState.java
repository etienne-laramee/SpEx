package com.Spex.gamestates;

import java.util.ArrayList;

import Menus.Menu;

import com.Spex.game.Spex;
import com.Spex.managers.GameKeys;
import com.Spex.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseState extends Menu
{
    private SpriteBatch        batch;
    private BitmapFont         font_default;
    private BitmapFont         font_highlight;
    private BitmapFont         font;

    // Menu indexes
    private static final int   PAUSE_RESUME  = 0;
    private static final int   PAUSE_RESTART = 1;
    private static final int   PAUSE_MAIN    = 2;
    private static final int   PAUSE_EXIT    = 3;

    private ArrayList<Integer> pauseMenu     = new ArrayList<Integer>();

    private int                pauseSelector = 0;

    public PauseState(GameStateManager gsm)
    {
        super(gsm);

        batch = new SpriteBatch();

        // Font setup
        font_default = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
        font_highlight = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_highlight.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_highlight.png"), false);

        font_default.setColor(Color.WHITE);
        font_highlight.setColor(Color.WHITE);

        font = font_default;

        pauseMenu.add(PAUSE_RESUME);
        pauseMenu.add(PAUSE_RESTART);
        pauseMenu.add(PAUSE_MAIN);
        pauseMenu.add(PAUSE_EXIT);
    }


    @Override
    public void draw()
    {
        super.draw();
        
        batch.begin();

        // Resize the font
        font_default.setScale(0.5f, 0.5f);
        font_highlight.setScale(0.5f, 0.5f);

        font.draw(batch, "SpEx - Pause", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 7);

        font = (pauseSelector == PauseState.PAUSE_RESUME ? font_highlight : font_default);
        font.draw(batch, "Resume", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 5);

        font = (pauseSelector == PauseState.PAUSE_RESTART ? font_highlight : font_default);
        font.draw(batch, "Restart", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 4);

        font = (pauseSelector == PauseState.PAUSE_MAIN ? font_highlight : font_default);
        font.draw(batch, "Main Menu", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 3);

        font = (pauseSelector == PauseState.PAUSE_EXIT ? font_highlight : font_default);
        font.draw(batch, "Exit", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 2);

        font = font_default;
        batch.end();
    }

    public void inputMenu(int action)
    {
        switch (action)
        {
            case Menu.UP:
                if (pauseSelector > 0)
                {
                    pauseSelector--;
                }
                break;
            case Menu.DOWN:
                if (pauseSelector < pauseMenu.size() - 1)
                {
                    pauseSelector++;
                }
                break;
            case Menu.SELECT:
                switch (pauseSelector)
                {
                    case PauseState.PAUSE_RESUME:
                        gsm.setState(GameStateManager.PLAY);
                        break;
                    case PauseState.PAUSE_RESTART:
                        gsm.setState(GameStateManager.NEWGAME);
                        break;
                    case PauseState.PAUSE_MAIN:
                        gsm.setState(GameStateManager.MENU);
                        break;
                    case PauseState.PAUSE_EXIT:
                        gsm.setState(GameStateManager.EXIT);
                        break;
                    default:
                        break;
                }
                break;
            case Menu.ESCAPE:
                gsm.setState(GameStateManager.PLAY);
                break;
            default:
                break;

        }
    }

    @Override
    public void dispose()
    {
//        super.dispose();
        batch.dispose();
        font.dispose();
        font_default.dispose();
        font_highlight.dispose();
    }


    @Override
    public void init()
    {
    }

}
