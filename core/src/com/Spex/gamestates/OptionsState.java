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

public class OptionsState extends Menu
{
    private SpriteBatch        batch;
    private BitmapFont         font_default;
    private BitmapFont         font_highlight;
    private BitmapFont         font;

    private static final int   OPTIONS_DIFF    = 0;
    private static final int   OPTIONS_MUSIC   = 1;
    private static final int   OPTIONS_MAIN    = 2;

    private int                optionsSelector = 0;
    private ArrayList<Integer> optionsMenu     = new ArrayList<Integer>();

    public OptionsState(GameStateManager gsm)
    {
        super(gsm);

        batch = new SpriteBatch();

        // Font setup
        font_default = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
        font_highlight = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_highlight.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_highlight.png"), false);

        font_default.setColor(Color.WHITE);
        font_highlight.setColor(Color.WHITE);

        font = font_default;

        optionsMenu.add(OPTIONS_DIFF);
        optionsMenu.add(OPTIONS_MUSIC);
        optionsMenu.add(OPTIONS_MAIN);
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

        font.draw(batch, "SpEx - Options", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 7);

        font = (optionsSelector == OptionsState.OPTIONS_DIFF ? font_highlight : font_default);
        font.draw(batch, "Difficulty: " + Spex.getDifficulty(), Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 5);

        font = (optionsSelector == OptionsState.OPTIONS_MUSIC ? font_highlight : font_default);
        font.draw(batch, "Music: " + Spex.getMusic(), Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 4);

        font = (optionsSelector == OptionsState.OPTIONS_MAIN ? font_highlight : font_default);
        font.draw(batch, "Main Menu", Spex.WIDTH / 12, (Spex.HEIGHT / 8) * 3);

        font = font_default;
        batch.end();
    }

    public void inputMenu(int action)
    {
        switch (action)
        {
            case OptionsState.UP:
                if (optionsSelector > 0)
                {
                    optionsSelector--;
                }
                break;
            case OptionsState.DOWN:
                if (optionsSelector < optionsMenu.size() - 1)
                {
                    optionsSelector++;
                }
                break;
            case OptionsState.LEFT:
                if (optionsSelector == OPTIONS_DIFF)
                {
                    Spex.decreaseDiff();
                }
                else if(optionsSelector == OPTIONS_MUSIC)
                {
                    Spex.toggleMusic();
                }
                break;
            case OptionsState.RIGHT:
                if (optionsSelector == OPTIONS_DIFF)
                {
                    Spex.increaseDiff();
                }
                else if(optionsSelector == OPTIONS_MUSIC)
                {
                    Spex.toggleMusic();
                }
                break;
            case OptionsState.SELECT:
                switch (optionsSelector)
                {
                    case OptionsState.OPTIONS_MAIN:
                        gsm.setState(GameStateManager.MENU);
                        break;
                    case OptionsState.OPTIONS_MUSIC:
                        Spex.toggleMusic();
                        break;
                    default:
                        break;
                }
                break;
            case OptionsState.ESCAPE:
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
