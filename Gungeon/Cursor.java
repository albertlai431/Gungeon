import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Cursor- a class meant to act as an image for the mouse to have the appearance as a targetting device.
 * 
 * @author Star Xie
 * @version January 2020
 */
public class Cursor extends Actor
{
    //Declare variables
    private int XCoord;
    private int YCoord;
    /**
     * Constructor - creates a Cursor with a scaled image
     */
    public Cursor()
    {
        GreenfootImage image = new GreenfootImage("cursor.png");
        //Scales and sets image
        image.scale(image.getWidth()*150/100, image.getHeight()*150/100);
        setImage(image);
    }
    
    /**
     * Act - do whatever the Crusor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //Checks if the mouse is currently in the world, sets location
        if(Greenfoot.getMouseInfo() != null)
        {
            XCoord = Greenfoot.getMouseInfo().getX();
            YCoord = Greenfoot.getMouseInfo().getY();
        }
        setLocation(XCoord, YCoord);
    }    
}
