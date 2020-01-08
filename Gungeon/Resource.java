import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Resource here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Resource extends Actor
{
    private GreenfootImage firstImage;
    private GreenfootImage secondImage;
    private int currentStatus;
    private boolean isHealthBar;
    public Resource(GreenfootImage first)
    {
        firstImage = new GreenfootImage(first);
        firstImage.scale(16, 16);
        setImage(firstImage);
        isHealthBar = false;
    }

    public Resource(GreenfootImage first, GreenfootImage second)
    {
        firstImage = new GreenfootImage(first);
        secondImage = new GreenfootImage(second);
        firstImage.scale(16, 16);
        secondImage.scale(16, 16);
        setImage(firstImage);
        currentStatus = 2;
        isHealthBar = true;
    }  

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

    public int getStatus()
    {
        if(isHealthBar)
        {
            return currentStatus;
        }
        return -1;
    }   
}
