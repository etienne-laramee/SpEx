package com.Spex.gamestates;

import java.util.ArrayList;

import HUD.HeadsUpDisplay;

import com.Spex.entities.LandingPad;
import com.Spex.entities.Planet;
import com.Spex.entities.Player;
import com.Spex.entities.Star;
import com.Spex.game.Spex;
import com.Spex.managers.GameKeys;
import com.Spex.managers.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PlayState extends GameState
{
    public static final int    GRAVITY     = 60;
    public static final float  RAD         = 57.2957795f;
    public static final float  RIGHT_ANGLE = (MathUtils.atan2(6, 0));

    private ShapeRenderer      sr;
    private SpriteBatch        batch;
    private BitmapFont         font;

    private Player             player;
    private ArrayList<Star>    stars;
    private ArrayList<Planet>  planets;
    private OrthographicCamera playerCam;

    // Camera attributes
    private double             prevAngleDeg;
    private double             diffAngleDeg;
    private double             currentAngleDeg;

    private boolean            isAvionics;

    private int                numStars;

    private float              deltaX;
    private float              deltaY;

    private float              landVelocity;

    private LandingPad         pad;
    private int                span;
    private int                fuelAmount;
    private int                numEmptyPads;
    private boolean            padsAreEmpty;
    private int                numLandingPad;

    // HUD
    private HeadsUpDisplay     hud;

    // Constructor
    public PlayState(GameStateManager gsm, int level)
    {
        super(gsm);
        sr = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);

        // Init lists
        player = new Player(Spex.WIDTH / 2, Spex.HEIGHT / 2, gsm);

        stars = new ArrayList<Star>();

        planets = new ArrayList<Planet>();

        // Set camera
        playerCam = new OrthographicCamera(Spex.WIDTH, Spex.HEIGHT);
        playerCam.translate(new Vector2(Spex.WIDTH / 2, Spex.HEIGHT / 2));
        playerCam.update();

        // Create a planet
        int moonSize = 2000;

        // Number of stars
        numStars = 1000;
        createStars();

        // Set level attributes
        numLandingPad = level;
        numEmptyPads = 0;
        span = (10 - level);
        if (span < 4)
        {
            span = 4;
        }
        fuelAmount = (1000 / level);

        Planet moon = new Planet(Spex.WIDTH / 2f, moonSize * -0.4f, moonSize, numLandingPad, span, fuelAmount);
        moon.setRotationSpeed(0.007f);

        planets.add(moon);

        // Set camera rotation variables
        deltaX = 0;
        deltaY = 0;
        prevAngleDeg = 270;
        diffAngleDeg = 0;
        currentAngleDeg = 0;

        isAvionics = true;

        pad = null;
        padsAreEmpty = false;

        // Maximum land velocity
        landVelocity = 200 - (Spex.getDiffIndex() * 50);

        // Hud
        hud = new HeadsUpDisplay(numLandingPad, player.getFuel(), level, landVelocity);
    }

    public void init()
    {
    }

    private void createStars()
    {
        for (int i = 0; i < numStars; i++)
        {
            stars.add(new Star((float) Math.random() * Spex.WIDTH * 2, (float) Math.random() * Spex.WIDTH * 2));
        }
    }

    public void update(float dt)
    {
        // Set player's closest planet
        Planet closestPlanet = planets.get(0);
        for (int i = 0; i < planets.size(); i++)
        {
            double distanceClosest = Math.sqrt(Math.pow((closestPlanet.getX() - player.getX()), 2) + Math.pow((closestPlanet.getY() - player.getY()), 2));
            double distanceN = Math.sqrt(Math.pow((planets.get(i).getX() - player.getX()), 2) + Math.pow((planets.get(i).getY() - player.getY()), 2));
            if (distanceN < distanceClosest)
            {
                closestPlanet = planets.get(i);
            }
        }

        player.setPlanet(closestPlanet);

        handleInput();

        // Check position of player with each planet and add gravity
        if (!player.isHit())
        {
            for (int i = 0; i < planets.size(); i++)
            {
                deltaX = planets.get(i).getX() - player.getX();
                deltaY = planets.get(i).getY() - player.getY();

                player.calculateGravity(dt, MathUtils.atan2(deltaX, deltaY), (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY), planets.get(i).getMass());
            }
        }

        // Update player
        player.update(dt);

        // Update HUD
        hud.update(numLandingPad - numEmptyPads, player.getFuel(), player.getVelocity());

        // Clip player to planet
        if (player.isLanded())
        {
            player.translate(planets.get(0).getRotation() * dt * ((player.getY() - planets.get(0).getY()) / (player.getX() - planets.get(0).getX())), 0);
        }

        // Camera Rotate
        if (isAvionics)
        {
            // Calculate the angle in degrees
            currentAngleDeg = Math.atan2(deltaY, deltaX) * 180 / Math.PI;

            // get the difference from the previous angle
            diffAngleDeg = prevAngleDeg - currentAngleDeg;

            // apply the rotation
            playerCam.rotate((float) diffAngleDeg);
            player.addPlanetaryRotationCorrection((float) diffAngleDeg);

            // set the previous angle
            prevAngleDeg = currentAngleDeg;
        }

        // Camera: Follow the player
        playerCam.translate(new Vector2(player.getTranslationX(), player.getTranslationY()));

        playerCam.update();

        if (player.isDead())
        {
            player.reset();
            return;
        }

        // Update Stars
        for (int i = 0; i < stars.size(); i++)
        {
            float xMin = player.getX() - Spex.WIDTH / 2;
            float xMax = player.getX() + Spex.WIDTH / 2;
            float yMin = player.getY() - Spex.WIDTH / 2;
            float yMax = player.getY() + Spex.WIDTH / 2;

            stars.get(i).update(xMin, yMin, xMax, yMax, player.getVX(), player.getVY(), dt);
        }

        // Update Planet
        for (int i = 0; i < planets.size(); i++)
        {
            planets.get(i).update(dt);
        }

        // Check collisions
        checkCollisions(dt);

        // Check fuel levels and next level if all empty
        ArrayList<LandingPad> landingPadList = planets.get(0).getLandingPadList();
        if (landingPadList.size() > 0)
        {
            padsAreEmpty = true;
            numEmptyPads = 0;
            for (int i = 0; i < landingPadList.size(); i++)
            {
                if (landingPadList.get(i).getFuelLevel() > 0)
                {
                    padsAreEmpty = false;
                } else
                {
                    numEmptyPads++;
                }
            }

            if (padsAreEmpty)
            {
                gsm.setState(GameStateManager.NEXTLEVEL_MENU);
            }
        }
    }

    private void checkCollisions(float dt)
    {
        // Player planet collision
        if (!player.isHit())
        {
            for (int i = 0; i < planets.size(); i++)
            {
                Planet a = planets.get(i);

                if (a.intersects(player) && (player.getVelocity() >= landVelocity))
                {
                    player.hit();
                    break;
                } else if (a.intersects(player) && (player.getVelocity() < landVelocity))
                {
                    // Calculate ship movement with planet distance and
                    // rotation.
                    player.landed();

                    player.setX((float) (a.getX() + ((player.getX() - a.getX()) * Math.cos(a.getRotation() * dt) - (player.getY() - a.getY()) * Math.sin(a.getRotation() * dt))));
                    player.setY((float) (a.getY() + ((player.getX() - a.getX()) * Math.sin(a.getRotation() * dt) + (player.getY() - a.getY()) * Math.cos(a.getRotation() * dt))));

                    boolean isOnAPad = false;
                    // Find which pad player is landed on, if any.
                    for (int n = 0; n < a.getLandingPadList().size(); n++)
                    {
                        pad = a.getLandingPadList().get(n);

                        if (pad.intersects(player))
                        {
                            player.setPad(pad);
                            isOnAPad = true;
                            
                            break;
                        }
                    }

                    if (!isOnAPad)
                    {
                        // player.hit();
                        // player.launched();
                    }
                    else
                    {
                        if (player.getPad().getFuelLevel() > 0)
                        {
                            Spex.playRefuel();
                        } else {
                            Spex.stopRefuel();
                        }                        
                    }
                } else
                {
                    Spex.stopRefuel();
                    player.launched();
                    player.removePad();
                }
            }
        }
    }

    public void draw()
    {
        sr.setProjectionMatrix(playerCam.combined);

        // Draw Stars
        for (int i = 0; i < stars.size(); i++)
        {
            stars.get(i).draw(sr);
        }

        // Draw player
        player.draw(sr);

        // Draw moon
        for (int i = 0; i < planets.size(); i++)
        {
            planets.get(i).draw(sr);
        }

        // Draw Heads up display
        hud.draw();
    }

    public void handleInput()
    {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        player.setDown(GameKeys.isDown(GameKeys.DOWN));

        // Pause menu
        if (GameKeys.isPressed(GameKeys.ESCAPE))
        {
            Spex.stopThrust();
            Spex.stopRefuel();
            gsm.setState(GameStateManager.PAUSE);
        }
    }

    public void dispose()
    {
//        if (sr != null)
//            sr.dispose();
//        if (batch != null)
//            batch.dispose();
//        if (font != null)
//            font.dispose();
    }
}
