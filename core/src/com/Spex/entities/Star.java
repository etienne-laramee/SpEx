package com.Spex.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Star extends SpaceObject{
    private double weight;
	public Star(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.weight = Math.random();
	}
	
	public void update(float x_min, float y_min, float x_max, float y_max, float velocityX, float velocityY, float dt)
	{
	    this.x -= velocityX * dt * ((weight * -0.5f) - 0.5f);
	    this.y -= velocityY * dt * ((weight * -0.5f) - 0.5f);

	    wrap(x_min, y_min, x_max, y_max);
	}
	
	public void draw(ShapeRenderer sr)
	{
	    sr.setColor(1 - (float)weight, 1 - (float)weight, 1 - (float)weight, 0.75f);
		sr.begin(ShapeType.Line);
		sr.circle(this.x, this.y, (float) (1.5 * weight));
		sr.end();	
	}
}
