package com.Spex.entities;

import com.Spex.game.Spex;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LandingPad
{
    private int         index;
    private int         span;
    private float       height;
    private float       pointA_X;
    private float       pointA_Y;
    private float       pointB_X;
    private float       pointB_Y;
    private float       pointC_X;
    private float       pointC_Y;
    private float       pointD_X;
    private float       pointD_Y;
    private float[]     shapex;
    private float[]     shapey;
    private float       centerX;
    private float       centerY;
    private float       deltaX;
    private float       deltaY;

    private Rectangle   rectangle;
    private double      angle;

    private SpriteBatch batch;
    private BitmapFont  font;

    private double      maxFuel;
    private double      remainingFuel;
    
    private boolean isEmpty = false;

    public LandingPad(int index, int span, float height, double fuel)
    {
        this.index = index;
        this.span = span;
        this.height = height;
        this.remainingFuel = this.maxFuel = fuel;
        
        shapex = new float[4];
        shapey = new float[4];

        this.rectangle = new Rectangle(this.pointA_X, this.pointA_Y, span, 5);

        this.setRectangle();

        batch = new SpriteBatch();

        // Font setup
        font = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
        font.setColor(Color.WHITE);

    }

    public void setRectangle()
    {
        this.setAngle();
        this.pointC_X = (float) (pointA_X + 20 * Math.cos(Math.toRadians(180) - Math.toRadians(angle)));
        this.pointC_Y = (float) (pointA_Y + 20 * Math.sin(Math.toRadians(180) - Math.toRadians(angle)));
        this.pointD_X = (float) (pointB_X + 20 * Math.cos(Math.toRadians(180) - Math.toRadians(angle)));
        this.pointD_Y = (float) (pointB_Y + 20 * Math.sin(Math.toRadians(180) - Math.toRadians(angle)));
        shapex[0] = pointA_X;
        shapex[1] = pointB_X;
        shapex[2] = pointC_X;
        shapex[3] = pointD_X;
        shapey[0] = pointA_Y;
        shapey[1] = pointB_Y;
        shapey[2] = pointC_Y;
        shapey[3] = pointD_Y;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getSpan()
    {
        return span;
    }

    public void setSpan(int span)
    {
        this.span = span;
    }

    public float getHeight()
    {
        return height;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }

    public void setAngle()
    {
        deltaX = (pointA_X - pointB_X);
        deltaY = (pointA_Y - pointB_Y);
        this.angle = (Math.atan2(deltaX, deltaY) * 180 / Math.PI);
    }

    public void setPointA(float pointX, float pointY)
    {
        this.pointA_X = pointX;
        this.pointA_Y = pointY;
    }

    public void setPointB(float pointX, float pointY)
    {
        this.pointB_X = pointX;
        this.pointB_Y = pointY;
    }

    public float getPointA_X()
    {
        return pointA_X;
    }

    public float getPointA_Y()
    {
        return pointA_Y;
    }

    public float getPointB_X()
    {
        return pointB_X;
    }

    public float getPointB_Y()
    {
        return pointB_Y;
    }
    
    public float getPointC_X()
    {
        return pointC_X;
    }
    
    public float getPointC_Y()
    {
        return pointC_Y;
    }
    
    public float getPointD_X()
    {
        return pointD_X;
    }
    
    public float getPointD_Y()
    {
        return pointD_Y;
    }

    public double getFuelLevel()
    {
        return this.remainingFuel;
    }

    public void setFuel(float fuel)
    {
        this.remainingFuel = fuel;
    }

    public void removeFuel(float fuel)
    {        
        if (this.remainingFuel >= (this.remainingFuel - fuel))
        {
            this.remainingFuel -= fuel;
        }
        else
        {
            this.remainingFuel = 0;
        }
        
        if (this.remainingFuel < 0.01f)
        {
            if(!this.isEmpty) Spex.playRefuelComplete();
            this.isEmpty = true;            
        }
    }

    public double getMaxFuel()
    {
        return this.maxFuel;
    }
    
    public boolean intersects(SpaceObject other)
    {
        float[] sx = other.getShapex();
        float[] sy = other.getShapey();
        for(int i = 0; i < sx.length; i++)
        {
            if(contains(sx[i], sy[i]))
            {
                return true;
            }
        }
        return false;
    }

    public boolean contains(float x, float y)
    {
        boolean b = false;
        for(int i = 0, j = shapex.length - 1; i < shapex.length; j = i++)
        {
            if((shapey[i] > y) != (shapey[j] > y) && (x < (shapex[j] - shapex[i]) * (y - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i]))
            {
                b = !b;
            }
        }
        return b;
    }
}
