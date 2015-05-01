package HUD;

import Interfaces.HUDInterface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class HUDElement implements HUDInterface
{
    private float x;
    private float y;
    private float width;
    private float height;
    
    private String descriptor = null;
    private boolean isAlignRight = false;
    
    private Rectangle outline;
    private float outlineMargin = 5;
    
    public  HUDElement(float x, float y, float width, float height)
    {
        this.setX(x);
        this.setY(y);
        
        this.setWidth(width);
        this.setHeight(height);
        
    }
    
    public void setOutlineMargin(float margin)
    {
        this.outlineMargin = margin;
        createOutline();
    }
    
    public void createOutline()
    {
        this.outline = new Rectangle(this.getX() - outlineMargin, this.getY() - outlineMargin, this.getWidth() + outlineMargin, this.getHeight() + outlineMargin);
    }

    public abstract void update(float dt);
    
    public abstract void draw(ShapeRenderer sr, Batch batch);

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getWidth()
    {
        return width;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public float getHeight()
    {
        return height;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }

    public String getDescriptor()
    {
        return descriptor;
    }

    public void setDescriptor(String descriptor)
    {
        this.descriptor = descriptor;
    }

    public boolean isAlignRight()
    {
        return isAlignRight;
    }

    public void setAlignRight(boolean isAlignRight)
    {
        this.isAlignRight = isAlignRight;
    }

    public Rectangle getOutline()
    {
        return outline;
    }

    public float getOutlineMargin()
    {
        return outlineMargin;
    }


}
