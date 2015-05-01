package com.Spex.managers;

import com.Spex.game.Spex;
import com.Spex.gamestates.AboutState;
import com.Spex.gamestates.GameOverState;
import com.Spex.gamestates.GameState;
import com.Spex.gamestates.MenuState;
import com.Spex.gamestates.NextLevel;
import com.Spex.gamestates.OptionsState;
import com.Spex.gamestates.PauseState;
import com.Spex.gamestates.PlayState;
import com.badlogic.gdx.Gdx;

public class GameStateManager
{

    // Current game state
    private GameState       gameState;
    private PlayState       currentGame;
    private MenuState       menu;
    private int             level          = 1;

    public static final int MENU           = 0;
    public static final int OPTIONS        = 64;
    public static final int PAUSE          = 1024;
    public static final int GAMEOVER       = 1064;
    public static final int NEWGAME        = 1128;
    public static final int NEXTLEVEL_MENU = 1254;
    public static final int NEXTLEVEL      = 1256;
    public static final int PLAY           = 2048;
    public static final int EXIT           = 4064;
    public static final int ABOUT           = 4128;

    public GameStateManager()
    {
        menu = new MenuState(this);

        setState(MENU);
    }

    public void setState(int state)
    {
        if (gameState != null)
        {
            if (gameState.getClass() == PlayState.class)
            {
                currentGame = (PlayState) gameState;
            }

            gameState.dispose();
        }
        if (state == PAUSE)
        {
//            Spex.stopThrust();
//            Spex.stopRefuel();
            gameState = new PauseState(this);
            if (Spex.music != null)
            {
                Spex.music.setVolume(0.25f);
            }
        }
        if (state == MENU)
        {
            Spex.stopThrust();
            Spex.stopRefuel();
            gameState = new MenuState(this);
            if (Spex.music != null)
            {
                Spex.music.setVolume(1.0f);
            }
        }
        if (state == OPTIONS)
        {
            Spex.stopThrust();
            Spex.stopRefuel();
            gameState = new OptionsState(this);
            if (Spex.music != null)
            {
                Spex.music.setVolume(0.25f);
            }
        }
        if (state == GAMEOVER)
        {
            Spex.stopThrust();
            Spex.stopRefuel();
            gameState = new GameOverState(this);
            if (Spex.music != null)
            {
                Spex.music.setVolume(0.25f);
            }
        }
        if (state == ABOUT)
        {
            Spex.stopThrust();
            Spex.stopRefuel();
            gameState = new AboutState(this);
            if (Spex.music != null)
            {
                Spex.music.setVolume(0.25f);
            }
        }
        if (state == PLAY)
        {
            gameState = currentGame;
            if (Spex.music != null)
            {
                Spex.music.setVolume(1.0f);
            }
        }
        if (state == NEWGAME)
        {
            level = 1;
            gameState = new PlayState(this, level);
            if (Spex.music != null)
            {
                Spex.music.setVolume(1.0f);
            }
        }
        if (state == EXIT)
        {
            Gdx.app.exit();
        }
        if (state == NEXTLEVEL_MENU)
        {
            Spex.stopThrust();
            Spex.stopRefuel();
            level++;
            gameState = new NextLevel(this, level);
            if (Spex.music != null)
            {
                Spex.music.setVolume(0.75f);
            }
        }
        if (state == NEXTLEVEL)
        {
            gameState = new PlayState(this, level);
            if (Spex.music != null)
            {
                Spex.music.setVolume(1.0f);
            }
        }
    }

    public void update(float dt)
    {
        gameState.update(dt);
    }

    public void draw()
    {
        gameState.draw();
    }

}
