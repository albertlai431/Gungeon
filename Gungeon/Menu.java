import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Menu is the abstract superclass of PauseMenu and StoreMenu. It contains methods to handle button clicks. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public abstract class Menu extends Actor
{
    /**
     * Act - do whatever the Menu wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkButtonClicks();
    }   
    
    /**
     * checkButtonClicks - checks for button clicks
     */
    protected abstract void checkButtonClicks();
}
