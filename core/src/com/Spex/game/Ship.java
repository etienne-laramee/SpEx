/**
 * 
 */
package com.Spex.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author Etienne
 *
 */
public class Ship extends GameObject
{
	// Constants
	private final short ANGLE = 0;
	private final short SPEED = 1;
	
	// Attributes
	private float health       = 1.0f;
	private float direction    = 0;
	private float acceleration = 0;
	private double [] velocity = {0, 0};
	private float weight       = 0;
	private Sprite sprite      = null;
	private double force       = 0;
	private double mass        = 10000;
	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * @return the force
	 */
	public double getForce() {
		return force;
	}

	/**
	 * @param force the force to set
	 */
	public void setForce(double force) {
		this.force = force;
	}

	public Ship(Sprite sprite)
	{
		super();
		this.sprite = sprite;
		setPosX(100);
		setPosY(100);
	}
	
	public void render(Batch batch)
	{
		sprite.setRotation(this.getDirection());
		sprite.draw(batch);
	}

	public void Physics()
	{
		// r = (rx,ry) - The location vector of your ships center.
		double rx = getPosX();
		double ry = getPosY();
		
		// v - The velocity vector.
		double [] v = getVelocity();
		
		// f - Force - f is your chosen magnitude for the force. f = 0, when the thrust key is not being pressed.
		double f = getForce();
		
		// m - Mass of the ship
		double m = getMass();
		
		// a - Acceleration - a = f/m
		double a = (f / m);
		
		// alpha - The direction angle of the ship.
		double alpha = getDirection();
		
		// f = (cos alpha * f, sin alpha * f)
		f = (Math.cos(alpha) * f);
		
		// You run your simulation in time steps, preferably of uniform length, e.g. once every 40th of a second. Let deltaT be the length of the time step.
		// You need to numerically integrate r from a. It's easiest to do it by simply adding each derivative, multiplied by deltaT to the old value:
		// v' = v + a*deltaT
		// r' = r + v'*deltaT
		// v' and r' are the new values.		
	}			
			
			
	/* (non-Javadoc)
	 * @see com.Spex.game.GameObject#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.setDirection(this.getDirection() % 360);
	}

	/* (non-Javadoc)
	 * @see com.Spex.game.GameObject#paint()
	 */
	@Override
	public void paint() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the health
	 */
	public float getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(float health) {
		this.health = health;
	}

	/**
	 * @return the direction
	 */
	public float getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(float direction) {
		this.direction = direction;
	}
	
	public void setDirectionRight(float degrees)
	{
		this.direction = ((this.direction + (360 - degrees)) % 360);		
	}
	
	public void setDirectionLeft(float degrees)
	{
		this.direction = ((this.direction + degrees) % 360);		
	}

	/**
	 * @return the acceleration
	 */
	public float getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the velocity
	 */
	public double [] getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(double [] velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * @return the sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @param sprite the sprite to set
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}
