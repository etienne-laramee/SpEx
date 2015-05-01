package Interfaces;


public interface MenuInterface
{
    // Control IDs
    static final int   UP            = 1000;
    static final int   DOWN          = 2000;
    static final int   SELECT        = 3000;
    static final int   ESCAPE        = 4000;
    
    public void update(float dt);
    
    public void draw();
    
    public void handleInput();
    public void inputMenu(int action);
    
    public void dispose();
}
