package com.Spex.entities;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javafx.embed.swing.SwingFXUtils;

import com.Spex.game.Spex;
import com.Spex.gamestates.PlayState;
import com.Spex.managers.GameStateManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Player extends SpaceObject
{
    private final int        refuelRate = 300;

    private GameStateManager gsm;

    // Closest Planet
    private Planet           planet;

    private float[]          flamex;
    private float[]          flamey;

    private float            camRadians;

    private float            oldX;
    private float            oldY;

    private boolean          left;
    private boolean          right;
    private boolean          up;
    private boolean          down;

    private float            maxSpeed;
    private float            acceleration;
    private float            friction;
    private float            acceleratingTimer;
    private float            outOfFuelTimer;
    private float            outOfFuelTime;

    private float            fuel;
    private float            consumptionRate;

    private boolean          landed;
    private boolean          hit;
    private boolean          dead;
    private boolean          isCrash;

    private float            hitTimer;
    private float            hitTime;
    private Line2D.Float[]   hitLines;
    private Point2D.Float[]  hitLinesVector;

    private LandingPad       pad;

    private boolean          menu;

    public Player(float x, float y, GameStateManager gsm)
    {
        this.gsm = gsm;
        this.setMenu(false);

        this.x = this.oldX = x;
        this.y = this.oldY = y;

        maxSpeed = 300;
        acceleration = 200;
        friction = 10;

        fuel = 1000;
        consumptionRate = (50.0f + (Spex.getDiffIndex() * 5));

        outOfFuelTimer = 0;
        outOfFuelTime = 3;

        shapex = new float[4];
        shapey = new float[4];
        flamex = new float[3];
        flamey = new float[3];

        radians = 3.1415f / 2;
        camRadians = radians;
        rotationSpeed = 3;

        hit = false;
        hitTimer = 0;
        hitTime = 2;

        // Pad the player is landed on.
        pad = null;
    }

    public void setMenu(boolean isMenuMode)
    {
        this.menu = isMenuMode;
    }

    private void setShape()
    {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * 3.1415f / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
        shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
    }

    private void setFlame()
    {
        float timer = (menu ? 0.2f : acceleratingTimer);
        flamex[0] = x + MathUtils.cos(radians - 5 * 3.1415f / 6) * 5;
        flamey[0] = y + MathUtils.sin(radians - 5 * 3.1415f / 6) * 5;

        flamex[1] = x + MathUtils.cos(radians - 3.1415f) * (6 + timer * 50);
        flamey[1] = y + MathUtils.sin(radians - 3.1415f) * (6 + timer * 50);

        flamex[2] = x + MathUtils.cos(radians + 5 * 3.1415f / 6) * 5;
        flamey[2] = y + MathUtils.sin(radians + 5 * 3.1415f / 6) * 5;

    }

    public void setLeft(boolean b)
    {
        left = b;
    }

    public void setRight(boolean b)
    {
        right = b;
    }

    public void setUp(boolean b)
    {
        up = b;
    }

    public void setDown(boolean b)
    {
        down = b;
    }

    public boolean isDead()
    {
        return dead;
    }

    public boolean isHit()
    {
        return hit;
    }

    public boolean isLanded()
    {
        return landed;
    }

    public void reset()
    {
        hit = dead = isCrash = false;
        this.radians = (float) (Math.toRadians((Math.atan2(planet.getY() - this.getY(), planet.getX() - this.getX()) * 180 / Math.PI) + 180));

        this.x = Spex.WIDTH / 2;
        this.y = Spex.HEIGHT / 2;
        this.dx = this.dy = 0;

        setShape();
    }

    // public void shoot()
    // {
    // if (bullets.size() == MAX_BULLETS)
    // return;
    // bullets.add(new Bullet(x, y, radians, this.dx, this.dy));
    // }

    public void hit()
    {
        if (hit)
            return;

        hit = true;
        left = right = up = false;

        hitLines = new Line2D.Float[4];

        for (int i = 0, j = hitLines.length - 1; i < hitLines.length; j = i++)
        {
            hitLines[i] = new Line2D.Float(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        hitLinesVector = new Point2D.Float[4];
        hitLinesVector[0] = new Point2D.Float(MathUtils.cos(radians + 1.5f + dx), MathUtils.sin(radians + 1.5f + dy));
        hitLinesVector[1] = new Point2D.Float(MathUtils.cos(radians - 1.5f + dx), MathUtils.sin(radians - 1.5f + dy));
        hitLinesVector[2] = new Point2D.Float(MathUtils.cos(radians - 2.8f + dx), MathUtils.sin(radians - 2.8f + dy));
        hitLinesVector[3] = new Point2D.Float(MathUtils.cos(radians + 2.8f + dx), MathUtils.sin(radians + 2.8f + dy));
    }

    public void landed()
    {
        if (landed)
        {
            return;
        }

        landed = true;

        this.setVelocity(0, 0);
        left = right = false;

        // Set the ship upright
        this.radians = (float) (Math.toRadians((Math.atan2(planet.getY() - this.y, planet.getX() - this.x) * 180 / Math.PI) + 180));
    }

    public float getFuel()
    {
        return fuel;
    }

    public void setFuel(float fuel)
    {
        this.fuel = fuel;
    }

    public void addFuel(float fuel)
    {
        this.setFuel(this.getFuel() + fuel);
    }

    public void translate(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    public void launched()
    {
        if (landed)
        {
            landed = false;
        }
    }

    public void update(float dt)
    {
        // Check if hit
        if (hit)
        {
            if (!isCrash)
            {
                Spex.playCrash();
                isCrash = true;
            }
            Spex.stopThrust();
            // dx = dy = 0;
            hitTimer += dt;
            if (hitTimer > hitTime)
            {
                dead = true;
                hitTimer = 0;
            }
            for (int i = 0; i < hitLines.length; i++)
            {
                hitLines[i].setLine(hitLines[i].x1 + hitLinesVector[i].x * 10 * dt, hitLines[i].y1 + hitLinesVector[i].y * 10 * dt, hitLines[i].x2 + hitLinesVector[i].x * 10 * dt,
                        hitLines[i].y2 + hitLinesVector[i].y * 10 * dt);

            }

            // Set position
            this.x += dx * dt;
            this.y += dy * dt;

            return;
        } else
        {

            if (fuel <= 0)
            {
                Spex.stopThrust();

                // Game is over, out of fuel
                outOfFuelTimer += dt;

                if (outOfFuelTimer >= outOfFuelTime)
                {
                    gsm.setState(GameStateManager.GAMEOVER);
                }

            } else
            {
                // accelerating
                if (up)
                {
                    dx += MathUtils.cos(radians) * acceleration * dt;
                    dy += MathUtils.sin(radians) * acceleration * dt;
                    acceleratingTimer += dt;
                    if (acceleratingTimer > 0.1f)
                    {
                        acceleratingTimer = 0.1f;
                    }

                    // Burn fuel, mahfa'a
                    fuel -= consumptionRate * dt;
                    Spex.playThrust();
                    if (fuel < 0)
                    {
                        fuel = 0;
                        Spex.stopThrust();
                    }
                }
                // Front thrusters, half strength
                else if (down && !landed)
                {
                    dx += MathUtils.cos(radians) * (-acceleration / 2) * dt;
                    dy += MathUtils.sin(radians) * (-acceleration / 2) * dt;
                    acceleratingTimer += dt;
                    if (acceleratingTimer > 0.1f)
                    {
                        acceleratingTimer = 0.1f;
                    }

                    // Burn fuel, mahfa'a
                    fuel -= (consumptionRate / 2) * dt;
                    Spex.playThrust();
                    if (fuel < 0)
                    {
                        fuel = 0;
                        Spex.stopThrust();
                    }
                }

                else
                {
                    acceleratingTimer = 0;
                    Spex.stopThrust();
                }
            }
            if (!landed)
            {
                // turning
                if (left)
                {
                    radians += rotationSpeed * dt;
                } else if (right)
                {
                    radians -= rotationSpeed * dt;
                }
            }

            // friction
            float vec = (float) Math.sqrt(dx * dx + dy * dy);
            if (vec > 0)
            {
                dx -= (dx / vec) * friction * dt;
                dy -= (dy / vec) * friction * dt;
            } else if (vec > maxSpeed)
            {
                dx = (dx / vec) * maxSpeed;
                dy = (dy / vec) * maxSpeed;
            }

            // Set position
            this.x += dx * dt;
            this.y += dy * dt;

            // Refuel at landing pad
            if (pad != null)
            {
                if (pad.getFuelLevel() > 0)
                {
                    pad.removeFuel(refuelRate * dt);
                    this.addFuel(refuelRate * dt);
                }
            }
        }
    }

    public float getVelocity()
    {
        return ((float) Math.sqrt(dx * dx + dy * dy));
    }

    public void setVelocity(float x, float y)
    {
        dx = x;
        dy = y;
    }

    public void calculateGravity(float dt, float angle, float distance, float mass)
    {
        if (!landed)
        {
            dx += MathUtils.cos(angle - 3.1415f / 2) * PlayState.GRAVITY * mass * dt / distance;
            dy += MathUtils.sin(angle + 3.1415f / 2) * PlayState.GRAVITY * mass * dt / distance;
        }
    }

    public void draw(ShapeRenderer sr)
    {
        setShape();

        // Set flame
        if (up || menu)
        {
            setFlame();
        }

        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeType.Line);

        // Check if hit
        if (hit)
        {
            for (int i = 0; i < hitLines.length; i++)
            {
                sr.line(hitLines[i].x1, hitLines[i].y1, hitLines[i].x2, hitLines[i].y2);
            }
            sr.end();
            return;
        }

        sr.end();
        sr.begin(ShapeType.Filled);

        // Draw Flame
        if ((up && fuel > 0) || menu)
        {
            for (int i = 0, j = flamex.length - 1; i < flamex.length; j = i++)
            {
                sr.setColor(Color.ORANGE);
                sr.triangle(flamex[i], flamey[i], flamex[j], flamey[j], this.x, this.y);
            }
        }

        // Draw Ship
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++)
        {
            sr.setColor(Color.WHITE);
            sr.triangle(shapex[i], shapey[i], shapex[j], shapey[j], this.x, this.y);
        }

        sr.end();
    }

    public float getRad()
    {
        return radians;
    }

    public float getCamRad()
    {
        return camRadians;
    }

    public void setCamRad(float camRad)
    {
        this.camRadians += camRad;
        if (this.camRadians >= 4 * 3.1415f)
        {
            this.camRadians -= (4 * 3.1415f);
        }
    }

    public float getTranslationX()
    {
        float retVal = (this.x - this.oldX);
        this.oldX = this.x;
        return retVal;
    }

    public float getTranslationY()
    {
        float retVal = (this.y - this.oldY);
        this.oldY = this.y;
        return retVal;
    }

    public void addPlanetaryRotationCorrection(float angleDeg)
    {
        radians -= Math.toRadians(angleDeg);
    }

    public void setPad(LandingPad pad)
    {
        this.pad = pad;
    }

    public void removePad()
    {
        this.pad = null;
    }

    public LandingPad getPad()
    {
        return this.pad;
    }

    public void setPlanet(Planet planet)
    {
        this.planet = planet;
    }

    public void addX(float x)
    {
        this.x += x;
    }

    public void addY(float y)
    {
        this.y += y;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getVX()
    {
        return this.dx;
    }

    public float getVY()
    {
        return this.dy;
    }
}
