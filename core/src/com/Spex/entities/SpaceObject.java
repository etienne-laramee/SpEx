package com.Spex.entities;

import com.Spex.game.Spex;

public class SpaceObject {
	protected float x;
	protected float y;

	protected float dx;
	protected float dy;

	protected float radians;
	protected float speed;
	protected float rotationSpeed;

	protected int width;
	protected int height;

	protected float[] shapex;
	protected float[] shapey;
	
	public float getX() { return this.x; }
	public float getY() { return this.y; }
	
	public float[] getShapex() { return shapex; }
	public float[] getShapey() { return shapey; }

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
    
    public boolean segmentIntersects(SpaceObject other, int indexA, int indexB)
    {
        float[] sx = other.getShapex();
        float[] sy = other.getShapey();
        for(int i = 0; i < sx.length; i++)
        {
            if(segmentContains(sx[i], sy[i], indexA, indexB))
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
    
    public boolean segmentContains(float x, float y, int indexA, int indexB)
    {
        boolean b = false;
        for(int i = indexA, j = indexB; i < indexB; j = i++)
        {
            if((shapey[i] > y) != (shapey[j] > y) && (x < (shapex[j] - shapex[i]) * (y - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i]))
            {
                b = !b;
            }
        }
        return b;
    }
	
	protected void wrap() {
		if(x < 0) x = Spex.WIDTH;
		if(x > Spex.WIDTH) x = 0;
		if(y < 0) y = Spex.HEIGHT;
		if(y > Spex.HEIGHT) y = 0;
	}
	
	protected void wrap(float x_min, float y_min, float x_max, float y_max)
	{
		if(x < x_min) x += (x_max - x_min);
		if(x > x_max) x -= (x_max - x_min);
		if(y < y_min) y += (y_max - y_min);
		if(y > y_max) y -= (y_max - y_min);
	}
}
