package HUD;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class PadDisplay extends HUDElement
{
    private int numPadsTotal;
    private int numPadsLeft;
    
    // List
    public ArrayList<Rectangle> padList;
    
    private BitmapFont font;
    
    private Color col;

    public PadDisplay(float x, float y, float width, float height, int numPadsTotal)
    {
        super(x, y, width, height);
        
        this.numPadsTotal = numPadsTotal;
        
        padList = new ArrayList<Rectangle>();

        // Init the full landing pads for the level
        for (int i = 0; i < numPadsTotal; i++)
        {
            Rectangle rectPad = new Rectangle(this.getX() - (i * 15), this.getY(), 10, 30);
            this.padList.add(rectPad);
        }
        
        this.col = Color.GREEN;
        
        font = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
    }

    @Override
    public void update(float dt)
    {
        
    }
    
    public void setPadsLeft(int numPadsLeft)
    {
        this.numPadsLeft = numPadsLeft;
    }

    @Override
    public void draw(ShapeRenderer sr, Batch batch)
    {
        sr.begin(ShapeType.Line);

        // Landing pads
        for (int i = 0; i < numPadsTotal; i++)
        {
            Rectangle rect = this.padList.get(i);
            if (i < numPadsLeft)
            {
                col = Color.GREEN;
            } else
            {
                col = Color.RED;
            }
            
            sr.rect(rect.x, rect.y, rect.width, rect.height, col, col, col, col);
        }
        sr.end();
    }
}
