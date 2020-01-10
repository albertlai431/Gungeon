import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
* Write a description of class Player here.
*
 * @author (your name)
 * @version (a version number or a date)
*/
public class Player extends Actor
{
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
       if(Greenfoot.isKeyDown("a"))//runs if "a" is pressed and the player is past the starting location
            {
                imageLeft();
            }
            else if(Greenfoot.isKeyDown("d"))//runs if "d" is pressed
            {
                imageRight();
            }
            else if(Greenfoot.isKeyDown("w"))//runs if "d" is pressed
            {
                imageUp();
            }
            else if(Greenfoot.isKeyDown("s"))//runs if "d" is pressed
            {
                imageDown();
            }
    }   
    
    public void imageLeft()
    {
        GreenfootImage img = getImage();
       
    }
}
