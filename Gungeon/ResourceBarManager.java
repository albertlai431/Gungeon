import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Stack;
/**
 * Write a description of class ResourceBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ResourceBarManager extends Actor
{
    private boolean isHealthBar;
    private int interval;
    private int currentX;
    private int currentY;
    private int maxResource;
    private int currentResource;
    private Stack <Resource> resourceStack;
    private GreenfootImage firstImage;
    private GreenfootImage secondImage;
    public ResourceBarManager(int max, int interval, int x, int y, GreenfootImage resourceImage, World world)
    {
        resourceStack = new Stack <Resource>();
        this.firstImage = new GreenfootImage(resourceImage);
        maxResource = max;
        currentResource = max;
        this.interval = interval;
        currentX = x;
        currentY = y;
        for(int i = 0; i < max; i++)
        {
            resourceStack.push(new Resource(resourceImage));
        }
        isHealthBar = false;
        //addBarToWorld(world);
    }

    public ResourceBarManager(int max, int interval, int x, int y, GreenfootImage firstImage, GreenfootImage secondImage, World world)
    {
        resourceStack = new Stack <Resource>();
        this.firstImage = new GreenfootImage(firstImage);
        this.secondImage = new GreenfootImage(secondImage);
        maxResource = max;
        currentResource = max * 2;
        this.interval = interval;
        currentX = x;
        currentY = y;        
        for(int i = 0; i < max; i++)
        {
            resourceStack.push(new Resource(firstImage, secondImage));
        }
        isHealthBar = true;
        //addBarToWorld(world);
    }

    public void addedToWorld(World world)
    {
        if(isHealthBar)
        {
            for(Resource i: resourceStack)
            {   
                world.addObject(i, currentX, currentY);
                currentX += interval;
            }
        }
        else
        {
            for(Resource i: resourceStack)
            {
                world.addObject(i, currentX, currentY);
                currentY += interval;
            }
        }
    }

    public boolean reduceHealth(int hits, World world)
    {
        if(isHealthBar)
        {
            for(int i = 0; i < hits; i ++)
            {
                if(resourceStack.peek().getStatus() == 2)
                {
                    resourceStack.peek().switchImage();
                }
                else
                {
                    world.removeObject(resourceStack.pop());
                }
            }
            return true;
        }
        return false;
    }

    public boolean reduceAmmo(World world)
    {
        if(isHealthBar == false)
        {
            world.removeObject(resourceStack.pop());
            currentResource--;
            return true;
        }
        return false;
    }

    public boolean refillHealth(int Amount, World world)
    {
        if(isHealthBar)
        {
            for(int i = 0; i < Amount; i++)
            {
                if(resourceStack.peek().getStatus() == 1)
                {
                    resourceStack.peek().switchImage();
                }
                else
                {
                    currentX = resourceStack.peek().getX();
                    resourceStack.push(new Resource(firstImage, secondImage));
                    resourceStack.peek().switchImage();
                    world.addObject(resourceStack.peek(), currentX + interval, currentY);            
                }
            }
            for(Resource i: resourceStack)
            {
                currentResource += i.getStatus();
            }
            return true;
        }
        return false;
    }

    public boolean refillAmmo(World world)
    {
        if(isHealthBar == false)
        {
            for(int i = 0; i < maxResource - currentResource; i++)
            {
                currentY = resourceStack.peek().getY();
                resourceStack.push(new Resource(firstImage));
                world.addObject(resourceStack.peek(), currentX, currentY + interval);
            }
            return true;
        }
        currentResource = maxResource;
        return false;
    }
}
