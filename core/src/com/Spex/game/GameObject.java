package com.Spex.game;

public abstract class GameObject {
	private double posX = 0;
	private double posY = 0;
	
	public abstract void update();
	
	public abstract void paint();

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
}
