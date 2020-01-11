import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Crusor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cursor extends Actor
{
    private int XCoord;
    private int YCoord;
    public CursorMouse()
    {
        GreenfootImage image = new GreenfootImage("crusor.png");
        setImage(image);
        image.scale(image.getWidth()*150/100, image.getHeight()*150/100);
        
        
    }
    /**
     * Act - do whatever the Crusor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.getMouseInfo() != null)
        {
            XCoord = Greenfoot.getMouseInfo().getX();
            YCoord = Greenfoot.getMouseInfo().getY();
        }
        setLocation(XCoord, YCoord);
    }    
}
