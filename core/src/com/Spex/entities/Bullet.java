package com.Spex.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject
{
	private float lifeTime;
	private float lifeTimer;
	
	private boolean remove;
	
	public Bullet(float x, float y, float radians, float accX, float accY)
	{
		this.x = x;
		this.y = y;
		this.radians = radians;
		
		// speed = pixels/second
		float speed = 600;
		
		dx = MathUtils.cos(radians) * speed + accX;
		dy = MathUtils.sin(radians) * speed + accY;
		
		width = height = 2;
		
		lifeTime = 1;
		lifeTimer = 0;
	}
	
	public boolean shouldRemove()
	{
		return remove;
	}
	
	public void update(float dt)
	{
		x += dx * dt;
		y += dy * dt;
		
		//wrap();
		lifeTimer += dt;
		if(lifeTimer > lifeTime)
		{
			remove = true;
		}
	}
	
	public void draw(ShapeRenderer sr)
	{
		sr.setColor(1, 1, 1, 1);
		sr.begin(ShapeType.Point);
		sr.point(x, y, 0);
		sr.end();
	}
}
