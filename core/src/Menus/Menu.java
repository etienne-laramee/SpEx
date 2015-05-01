package Menus;

import java.util.ArrayList;

import Interfaces.MenuInterface;

import com.Spex.entities.Star;
import com.Spex.game.Spex;
import com.Spex.gamestates.GameState;
import com.Spex.gamestates.MenuState;
import com.Spex.gamestates.OptionsState;
import com.Spex.managers.GameKeys;
import com.Spex.managers.GameStateManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Menu extends GameState implements MenuInterface 
{
    private ArrayList<Star>    stars;
    private int                numStars        = 2000;

    // Control IDs
    protected static final int   UP              = 1000;
    protected static final int   DOWN            = 2000;
    protected static final int   LEFT            = 3000;
    protected static final int   RIGHT           = 4000;
    protected static final int   SELECT          = 5000;
    protected static final int   ESCAPE          = 6000;
    
    private ShapeRenderer      sr;
    private OrthographicCamera playerCam;
    
    public Menu(GameStateManager gsm)
    {
        super(gsm);
        // Background stars
        sr = new ShapeRenderer();
        stars = new ArrayList<Star>();
        createStars();

        // Set camera
        playerCam = new OrthographicCamera(Spex.WIDTH, Spex.HEIGHT);
        playerCam.update();
    }

    private void createStars()
    {
        for (int i = 0; i < numStars; i++)
        {
            stars.add(new Star((float) Math.random() * Spex.WIDTH * 2, (float) Math.random() * Spex.WIDTH * 2));
        }
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

            stars.get(i).update(xMin, yMin, xMax, yMax, 0, 50, dt);
        }
        
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

        try
        {
            sr.end();
        }
        catch (Exception e)
        {
        }
    }

    @Override
    public void handleInput()
    {
        // Escape
        if (GameKeys.isPressed(GameKeys.ESCAPE))
        {
            inputMenu(Menu.ESCAPE);
        }
        // UP
        if (GameKeys.isPressed(GameKeys.UP))
        {
            inputMenu(Menu.UP);
            Spex.playSelect();
        }
        // DOWN
        if (GameKeys.isPressed(GameKeys.DOWN))
        {
            inputMenu(Menu.DOWN);
            Spex.playSelect();
        }
        // LEFT
        if (GameKeys.isPressed(GameKeys.LEFT))
        {
            inputMenu(Menu.LEFT);
            Spex.playDown();
        }
        // RIGHT
        if (GameKeys.isPressed(GameKeys.RIGHT))
        {
            inputMenu(Menu.RIGHT);
            Spex.playUp();
        }
        // Select
        if (GameKeys.isPressed(GameKeys.ENTER))
        {
            inputMenu(Menu.SELECT);
            Spex.playSelect();
        }       
    }

    @Override
    public void inputMenu(int action)
    {
        
    }

    @Override
    public void dispose()
    {
        sr.dispose();
    }
}
