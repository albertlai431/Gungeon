import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Resource class is managed by the resource bar manager(most methods in this class are only called by resource bar manager)
 * Resource class helps to create a bar (this class are the images that show up as the bar)
 * 
 * @author Henry Ma
 * @version January 16, 2020
 */
public class Resource extends Actor
{
    //Initialize objects and variables
    private GreenfootImage firstImage;
    private GreenfootImage secondImage;
    //Tells us if the heart is at half or full
    private int currentStatus;
    //Some methods only work if this is defined as a heart resource
    private boolean isHealthBar;
    /**
     * Constructor 1: Used for any kind of resource(Usually used for ammo bar)
     * 
     * @param first     Image of the resource
     */    
    public Resource(GreenfootImage first)
    {
        //Set initial values
        firstImage = new GreenfootImage(first);
        firstImage.scale(16, 16);
        setImage(firstImage);
        isHealthBar = false;
    }
    /**
     * Constructor 2: Used for the health bar as hearts have to images
     * 
     * @param first     The first image of the heart(full heart)
     * @param second    The second image of the heart(half heart)
     */
    public Resource(GreenfootImage first, GreenfootImage second)
    {
        //Set initial values
        firstImage = new GreenfootImage(first);
        secondImage = new GreenfootImage(second);
        firstImage.scale(16, 16);
        secondImage.scale(16, 16);
        setImage(firstImage);
        currentStatus = 2;
        isHealthBar = true;
    }  
    /**
     * Can either heal or remove a heart(half) depending on its current status
     */
    public boolean switchImage()
    {
        if(isHealthBar)
        {
            if(currentStatus == 2)
            {
                setImage(secondImage);
                currentStatus = 1;
            }
            else
            {
                setImage(firstImage);
                currentStatus = 2;
            }
            return true;
        }
        return false;
    }
    /**
     * Get the status of the heart
     * 
     * @return      Returns 1 if at half heart or 2 if at full(returns -1 if not a heart resource)
     */
    public int getStatus()
    {
        if(isHealthBar)
        {
            return currentStatus;
        }
        return -1;
    }   
}
