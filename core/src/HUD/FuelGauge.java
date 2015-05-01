package HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class FuelGauge extends HUDElement
{
    private float     maxFuel;
    private float     currentFuel;
    private Rectangle fuelBar;
    
    private BitmapFont font;

    private Color     col;

    public FuelGauge(float x, float y, float width, float height, float maxFuel, float currentFuel)
    {
        super(x, y, width, height);

        this.maxFuel = maxFuel;
        this.currentFuel = currentFuel;

        init();
    }

    public void init()
    {
        this.fuelBar = new Rectangle(this.getX(), this.getY(), (this.getWidth() / this.maxFuel) * this.currentFuel, this.getHeight());

        this.col = Color.WHITE;

        this.setOutlineMargin(5);

        font = new BitmapFont(Gdx.files.internal("Fonts/Press_Start_2p_white.fnt"), Gdx.files.internal("Fonts/Press_Start_2p_white.png"), false);
    }

    public void setFuel(float fuel)
    {
        this.currentFuel = fuel;
    }

    @Override
    public void update(float dt)
    {
        this.fuelBar.setWidth((this.getWidth() - (this.getOutlineMargin() * 2)) * (this.currentFuel / this.maxFuel));
    }

    @Override
    public void draw(ShapeRenderer sr, Batch batch)
    {
        // Fuel level
        sr.begin(ShapeType.Filled);
        sr.rect(this.fuelBar.x + (this.getOutlineMargin() / 2), this.fuelBar.y + (this.getOutlineMargin() / 2), this.fuelBar.width,
                this.fuelBar.height - (this.getOutlineMargin() * 2), col, col, col, col);
        sr.end();

        // Outline
        sr.begin(ShapeType.Line);
        sr.rect(this.getOutline().x, this.getOutline().y, this.getOutline().width, this.getOutline().height, col, col, col, col);
        sr.end();

        // Draw Text
        String caption = "Fuel";

        batch.begin();
        font.setScale(0.25f);
        font.draw(batch, caption, this.getX(), this.getY() + this.getHeight() + (font.getBounds(caption).height));
        batch.end();
    }

}
