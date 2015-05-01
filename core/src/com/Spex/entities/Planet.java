package com.Spex.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Planet extends SpaceObject
{
    private int                   numPoints;
    private float[]               heightList;
    private float                 mass;
    private ArrayList<LandingPad> landingPadList;
    private int                   numLandingPad;
    private int                   span;
    private int                   fuelAmount;
    private float                 heightRatio;

    public Planet(float x, float y, int size, int numLandingPad, int span, int fuelAmount)
    {
        this.heightRatio = 1.04f;
        this.numLandingPad = numLandingPad;
        this.span = span;
        this.fuelAmount = fuelAmount;

        this.span = span;
        this.fuelAmount = fuelAmount;

        landingPadList = new ArrayList<LandingPad>();
        this.numLandingPad = numLandingPad;

        this.x = x;
        this.y = y;

        width = height = size;
        speed = 0;
        mass = 2000;
        rotationSpeed = 0.02f;

        radians = MathUtils.random(2 * 3.1415f);
        dx = 0;
        dy = 0;

        numPoints = 500;

        shapex = new float[numPoints];
        shapey = new float[numPoints];
        heightList = new float[numPoints];

        int radius = width / 2;

        // Create a list of random hill heights
        for (int i = 0; i < numPoints; i++)
        {
            heightList[i] = MathUtils.random(radius / heightRatio, radius);
        }

        // Create landing pads
        for (int i = 0; i < numLandingPad; i++)
        {
            LandingPad pad = null;

            // Generate random index within range.
            int index = 0;
            float height = 0.0f;
            boolean isValid = false;
            while (!isValid)
            {
                isValid = true;
                index = MathUtils.random(numPoints - 1);
                height = heightList[index];

                if (landingPadList.size() > 0)
                {
                    // Check if interfering with other pad
                    for (int j = 0; j < landingPadList.size(); j++)
                    {
                        if ((((index + span + 1) >= (landingPadList.get(j).getIndex() - 1)) && (index - 1 <= ((landingPadList.get(j).getIndex() + landingPadList.get(j).getSpan()) + 1)))
                                || ((index + span - 1) >= numPoints))
                        {
                            isValid = false;
                            break;
                        }
                    }
                }
            }

            pad = new LandingPad(index, span, height, fuelAmount);
            landingPadList.add(pad);

            // Add landing pad heights to height list
            for (int j = 0; j < pad.getSpan(); j++)
            {
                heightList[((pad.getIndex() + j) >= numPoints ? (pad.getIndex() + j) - numPoints : (pad.getIndex() + j))] = pad.getHeight();
            }
        }

        setShape();
    }

    private void setShape()
    {
        float angle = 0;

        for (int i = 0; i < numPoints; i++)
        {
            shapex[i] = x + MathUtils.cos(angle + radians) * heightList[i];
            shapey[i] = y + MathUtils.sin(angle + radians) * heightList[i];
            angle += 2 * 3.1415f / numPoints;

            for (int n = 0; n < landingPadList.size(); n++)
            {
                int index = landingPadList.get(n).getIndex();

                // If it's a landing pad, set the two points
                if (i == index)
                {
                    // Get coordinates for the pad
                    landingPadList.get(n).setPointA(shapex[i], shapey[i]);
                    landingPadList.get(n).setPointB(shapex[(i + landingPadList.get(n).getSpan() - 1) % numPoints], shapey[(i + landingPadList.get(n).getSpan() - 1) % numPoints]);
                }
            }
        }

        // Update pad rectangles
        for (int n = 0; n < landingPadList.size(); n++)
        {
            landingPadList.get(n).setRectangle();
        }
    }

    public void update(float dt)
    {
        // Update planet's rotation angle
        radians += rotationSpeed * dt;

        // Set planet shape
        setShape();
    }

    public void draw(ShapeRenderer sr)
    {
        sr.setColor(0.25f, 0.25f, 0.25f, 1);
        sr.begin(ShapeType.Filled);
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++)
        {

            sr.triangle(shapex[i], shapey[i], shapex[j], shapey[j], this.x, this.y);
        }
        sr.end();

        sr.setColor(0.25f, 0.25f, 0.25f, 1);
        sr.begin(ShapeType.Line);
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++)
        {
            for (int n = 0; n < landingPadList.size(); n++)
            {
                LandingPad pad = landingPadList.get(n);
                int min = pad.getIndex();
                int max = (min + pad.getSpan() - 1);

                // If the drawn points are landing pads, color green, yellow or
                // red depending on the fuel level.
                if (i > min && j < max)
                {
                    if (pad.getFuelLevel() > (pad.getMaxFuel() / 3))
                    {
                        // Green
                        sr.setColor(0, 1, 0, 1);
                    } else if (pad.getFuelLevel() > 0)
                    {
                        // Yellow
                        sr.setColor(1, 1, 0, 1);
                    } else
                    {
                        // Red
                        sr.setColor(1, 0, 0, 1);
                    }
                    Gdx.gl.glLineWidth(2);
                    pad.setRectangle();
                    sr.line(pad.getPointA_X(), pad.getPointA_Y(), pad.getPointB_X(), pad.getPointB_Y());
                    sr.line(pad.getPointB_X(), pad.getPointB_Y(), pad.getPointD_X(), pad.getPointD_Y());
                    sr.line(pad.getPointD_X(), pad.getPointD_Y(), pad.getPointC_X(), pad.getPointC_Y());
                    sr.line(pad.getPointC_X(), pad.getPointC_Y(), pad.getPointA_X(), pad.getPointA_Y());
                    sr.setColor(0.25f, 0.25f, 0.25f, 1);
                }
            }
        }
        sr.end();
    }

    public float getMass()
    {
        return mass;
    }

    public float getRotation()
    {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed)
    {
        this.rotationSpeed = rotationSpeed;
    }

    public ArrayList<LandingPad> getLandingPadList()
    {
        return landingPadList;
    }

}
