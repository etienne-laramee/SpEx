package com.Spex.game;

import com.Spex.managers.GameInputProcessor;
import com.Spex.managers.GameKeys;
import com.Spex.managers.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

@SuppressWarnings("unused")
public class Spex extends ApplicationAdapter
{
    public static int        WIDTH;
    public static int        HEIGHT;

    public static Music      music              = null;
    public static Music      SFXmenu_select     = null;
    public static Music      SFXmenu_up         = null;
    public static Music      SFXmenu_down       = null;
    public static Music      SFXthrust          = null;
    public static Music      SFXrefuel          = null;
    public static Music      SFXrefuel_complete = null;
    public static Music      SFXcrash           = null;

    public static final int  EASY               = 0;
    public static final int  MEDIUM             = 1;
    public static final int  HARD               = 2;
    public static final int  IMPOSSIBLE         = 3;

    public static int        difficulty         = MEDIUM;
    public static boolean    isMusic            = true;

    private GameStateManager gsm;

    @Override
    public void create()
    {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/Reve d'Astronaute - Tapani.mp3"));

        SFXmenu_select = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/MenuEffects_sel.ogg"));
        SFXmenu_up = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/MenuEffects_inc.ogg"));
        SFXmenu_down = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/MenuEffects_dec.ogg"));
        SFXrefuel_complete = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/RefuelComplete.ogg"));
        SFXcrash = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/Crash.ogg"));

        SFXthrust = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/Thrust.ogg"));
        SFXthrust.setVolume(0.15f);
        SFXthrust.setLooping(true);
        SFXrefuel = Gdx.audio.newMusic(Gdx.files.internal("Music/SFX/Refuel.ogg"));
        SFXrefuel.setLooping(true);

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        Gdx.input.setInputProcessor(new GameInputProcessor());

        gsm = new GameStateManager();

        if (music != null)
        {
            music.setLooping(true);
            music.play();
        }
    }

    @Override
    public void render()
    {
        // Clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();

        GameKeys.update();
    }

    @Override
    public void dispose()
    {
//        if (Spex.music != null)
//        {
//            music.dispose();
//        }
        
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    public static String getDifficulty()
    {
        switch (difficulty)
        {
            case 0:
                return "Easy";
            case 1:
                return "Medium";
            case 2:
                return "Hard";
            case 3:
                return "Impossible";
            default:
                return "";
        }
    }

    public static int getDiffIndex()
    {
        return difficulty;

    }

    public static void increaseDiff()
    {
        if (difficulty < 3)
        {
            difficulty++;
        }
    }

    public static void decreaseDiff()
    {
        if (difficulty > 0)
        {
            difficulty--;
        }
    }

    public static boolean getMusic()
    {
        return isMusic;
    }

    public static void toggleMusic()
    {
        isMusic = !isMusic;

        if (!isMusic)
        {
            music.stop();
        } else
        {
            music.play();
        }
    }

    public static void playSelect()
    {
        if (SFXmenu_select != null)
        {
            SFXmenu_select.play();
        }
    }

    public static void playUp()
    {
        if (SFXmenu_up != null)
        {
            SFXmenu_up.play();
        }
    }

    public static void playDown()
    {
        if (SFXmenu_down != null)
        {
            SFXmenu_down.play();
        }
    }

    public static void playRefuelComplete()
    {
        if (SFXrefuel_complete != null)
        {
            SFXrefuel_complete.play();
        }
    }
    
    public static void playCrash()
    {
        if (SFXcrash != null)
        {
            SFXcrash.play();
        }
    }

    public static void playThrust()
    {
        if (SFXthrust != null && !SFXthrust.isPlaying())
        {
            SFXthrust.play();
        }
    }

    public static void stopThrust()
    {
        if (SFXthrust != null && SFXthrust.isPlaying())
        {
            SFXthrust.stop();
        }
    }

    public static void playRefuel()
    {
        if (SFXrefuel != null && !SFXrefuel.isPlaying())
        {
            SFXrefuel.setVolume(0.25f);
            SFXrefuel.play();
        }
    }

    public static void stopRefuel()
    {
        if (SFXrefuel != null && SFXrefuel.isPlaying())
        {
            SFXrefuel.stop();
        }
    }
}
