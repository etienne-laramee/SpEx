package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class VelocityGauge extends HUDElement
{
    private float      velocity;
    private float      velocityMax;

    private float      velSafe;

    private BitmapFont font;

    private Rectangle  velocityBarSafe;

    private Color      col;

    public VelocityGauge(float x, float y, float width, float height, float velocityMax)
    {
        super(x, y, width, height);

        this.velocityMax = velocityMax;

        init();
    }

    public void init()
    {
        this.setOutlineMargin(5);

        this.velocity = 0;

        // Init Velocty indicators
        this.velSafe = 0;

        this.velocityBarSafe = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        this.col = Color.WHITE;

        font = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
    }

    public void setVelocityThreshold(float velocityThreshold)
    {
        this.velocityMax = velocityThreshold;
    }

    public void setVelocity(float velocity)
    {
        this.velocity = velocity;
    }

    @Override
    public void update(float velocity)
    {
        this.velocity = velocity;
    }

    @Override
    public void draw(ShapeRenderer sr, Batch batch)
    {
        // Fuel level
        sr.begin(ShapeType.Filled);
        this.col = Color.WHITE;

        if (velocity > 0)
        {
            // col = Color.GREEN;
            velSafe = (this.getWidth() * (velocity / velocityMax));
            
            // Cap max width
            if (velSafe > (this.getWidth()))
            {
                velSafe = (this.getWidth());
            }
            sr.rect(velocityBarSafe.x + (this.getOutlineMargin() / 2), velocityBarSafe.y + (this.getOutlineMargin() / 2), velSafe - (this.getOutlineMargin() * 2),
                    velocityBarSafe.height - (this.getOutlineMargin() * 2), col, col, col, col);
        }

        sr.end();

        // Outline
        sr.begin(ShapeType.Line);
        sr.rect(this.getOutline().x, this.getOutline().y, this.getOutline().width, this.getOutline().height, col, col, col, col);
        sr.end();

        // Draw Text
        String caption = "Velocity";

        batch.begin();
        font.setScale(0.25f);
        font.draw(batch, caption, this.getX(), this.getY() + this.getHeight() + (font.getBounds(caption).height));
        batch.end();
    }

}
