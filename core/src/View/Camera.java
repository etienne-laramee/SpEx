package View;

import com.Spex.game.Spex;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Camera
{
	private float radians;
	private float playerRadians;
	private float rotationTimer;
	private float rotationIntensity;
	private float tX;
	private float tY;
	private float dX;
	private float dY;
	private float oldX;
	private float oldY;
	
	public Camera(float x, float y, float rad)
	{
		this.radians = 0;
		this.playerRadians = rad;
		this.rotationTimer = 0;
		this.rotationIntensity = 0;
		this.oldX = this.dX = x;
		this.oldY = this.dY = y;
	}
	
	public void setAngle(float radians)
	{
		this.radians = radians - playerRadians;
		this.playerRadians = radians;
	}
	
	public void setTranslate(float playerTX, float playerTY)
	{
		tX = playerTX;
		tY = playerTY;
	}
	
	public void setDisplacement(float playerX, float playerY)
	{
		dX = playerX;
		dY = playerY;
	}
	
	public void update(ShapeRenderer sr)
	{
		// Move camera to player pos
		sr.translate(Spex.WIDTH / 2, Spex.HEIGHT / 2, 0);

		// Match player location
		sr.translate(-tX, -tY, 0);
		// Rotate
		sr.rotate(0, 0, 2, (-radians * 57.2957795f));
		
		// Move camera back to center player
		sr.translate(-Spex.WIDTH / 2, -Spex.HEIGHT / 2, 0);
		sr.end();
	}
}
