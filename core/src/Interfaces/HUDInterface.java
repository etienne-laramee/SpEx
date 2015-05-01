package Interfaces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface HUDInterface
{
    public void update(float dt);
    public void draw(ShapeRenderer sr, Batch batch);
}
