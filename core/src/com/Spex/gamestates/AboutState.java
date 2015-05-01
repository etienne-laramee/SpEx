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

public class AboutState extends Menu
{
    private SpriteBatch        batch;
    private BitmapFont         font_default;
    private BitmapFont         font_highlight;
    private BitmapFont         font;

    private static final int   OPTIONS_MAIN    = 0;

    private int                optionsSelector = 0;
    private ArrayList<Integer> optionsMenu     = new ArrayList<Integer>();

    private String             about1, about2, about3 = "";

    public AboutState(GameStateManager gsm)
    {
        super(gsm);

        batch = new SpriteBatch();

        // Font setup
        font_default = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
        font_highlight = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_highlight.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_highlight.png"), false);

        font_default.setColor(Color.WHITE);
        font_highlight.setColor(Color.WHITE);

        font = font_default;

        optionsMenu.add(OPTIONS_MAIN);

        about1 = "SpEx, Space Explorer is a \"Lunar Lander\"";
        about2 = "and \"Asteroid\" inspired videogame.";
        about3 = "Programming, animation, graphics, music: Etienne Laramee";
    }

    @Override
    public void init()
    {
    }

    @Override
    public void draw()
    {
        super.draw();

        batch.begin();

        // Resize the font
        font_default.setScale(0.5f, 0.5f);
        font_highlight.setScale(0.5f, 0.5f);

        font.draw(batch, "SpEx - About", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 7);

        // Set font size a little smaller
        font_default.setScale(0.25f, 0.25f);
        font.draw(batch, about1, Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 5);
        font.draw(batch, about2, Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 4);
        font.draw(batch, about3, Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 3);

        // Reset font size
        font_default.setScale(0.5f, 0.5f);

        font = (optionsSelector == AboutState.OPTIONS_MAIN ? font_highlight : font_default);
        font.draw(batch, "Main Menu", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 2);

        font = font_default;
        batch.end();
    }

    public void inputMenu(int action)
    {
        switch (action)
        {
            case AboutState.SELECT:
                switch (optionsSelector)
                {
                    case AboutState.OPTIONS_MAIN:
                        gsm.setState(GameStateManager.MENU);
                        break;
                    default:
                        break;
                }
                break;
            case AboutState.ESCAPE:
                gsm.setState(GameStateManager.MENU);
                break;
            default:
                break;
        }
    }

    @Override
    public void dispose()
    {
        super.dispose();
        batch.dispose();
        font.dispose();
        font_default.dispose();
        font_highlight.dispose();
    }
}
