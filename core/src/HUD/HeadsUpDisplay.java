package HUD;

import java.util.ArrayList;

import com.Spex.game.Spex;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class HeadsUpDisplay
{
    private int                 numPads;
    private int                 numPadsLeft;
    private float               fuelLevel;
    private int                 level;
    private float               currentVelocity;
    private float               velocityMax;

    private SpriteBatch         batch;
    private ShapeRenderer       sr;
    private BitmapFont          font;
    private String              levelName;
    private float               levelLength;

    private Color               col;

    private float               centerX;

    private PadDisplay          pads;
    private FuelGauge           fuel;
    private VelocityGauge       velocity;

    private Rectangle           rectFuel;
    private float               HUDheightTop;
    private float               HUDheightBottom;

    public HeadsUpDisplay(int numPads, float fuelLevel, int level, float velocityMax)
    {
        this.centerX = (Gdx.graphics.getWidth() / 2);
        this.numPads = numPads;
        this.numPadsLeft = 0;
        this.fuelLevel = fuelLevel;
        this.level = level;
        this.velocityMax = velocityMax;

        this.HUDheightTop = ((Gdx.graphics.getHeight() / 12) * 9);
        this.HUDheightBottom = ((Gdx.graphics.getHeight() / 12) * 8);

        init();
    }

    private void init()
    {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        
        // Font
        font = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);

        this.pads = new PadDisplay((Gdx.graphics.getWidth() / 24) * 22 - 20, (Gdx.graphics.getHeight() / 24) * 18, (Gdx.graphics.getWidth() / 24) * 8,
                (Gdx.graphics.getHeight() / 24), numPads);
        
        this.fuel = new FuelGauge((Gdx.graphics.getWidth() / 24) * 2 - 20, (Gdx.graphics.getHeight() / 24) * 18, (Gdx.graphics.getWidth() / 24) * 8,
                (Gdx.graphics.getHeight() / 24), 2000, fuelLevel);
        this.velocity = new VelocityGauge((Gdx.graphics.getWidth() / 24) * 2 - 20, (Gdx.graphics.getHeight() / 24) * 16, (Gdx.graphics.getWidth() / 24) * 8,
                (Gdx.graphics.getHeight() / 24), velocityMax);

    }

    public void update(int numPadsLeft, float fuelLevel, float currentVelocity)
    {
        this.pads.setPadsLeft(numPadsLeft);
        
        this.velocity.update(currentVelocity);

        this.fuel.setFuel(fuelLevel);
        this.fuel.update(1);
    }

    public void draw()
    {
        drawLevel();
        
        this.fuel.draw(sr, batch);
        
        this.velocity.draw(sr, batch);
        
        this.pads.draw(sr, batch);
    }

    private void drawLevel()
    {
        batch.begin();
        font.setScale(0.5f);
        levelName = ("Level: " + this.level);
        levelLength = font.getBounds(levelName).width;
        font.draw(batch, levelName, ((Gdx.graphics.getWidth() / 2) - (levelLength / 2)), (Gdx.graphics.getHeight() / 12) * 11);
        batch.end();
    }
}
