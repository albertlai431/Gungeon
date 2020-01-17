import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Stack;
/**
 * Manages and displays the resource that is created through the parameters, creates a bar that can be managed
 * through methods in this class
 * Manages the resource class through a stack, call methods in this class to manage the resources it holds/manages
 * 
 * @author Henry Ma
 * @version January 16, 2020
 */
public class ResourceBarManager extends Actor
{
    //Initialize variables and objects
    //Health bar acts differently from normal resource bars
    private boolean isHealthBar;
    //The spacing between resources(bar spacing)
    private int interval;
    //Current coordinates of the first resource in the stack
    private int currentX;
    private int currentY;
    //Max amount of resource
    private int maxResource;
    //Current value of the resource
    private int currentResource;
    //Holds resources as a stack
    private Stack <Resource> resourceStack;
    //Used by both health bar and resource bars
    private GreenfootImage firstImage;
    //Second image for half heart
    private GreenfootImage secondImage;
    /**
     * Constructor 1: Used for any kind of resource(creates a bar of images specified in the parameters)
     * 
     * @param max           Max amount of this resource possible
     * @param cur           Current value for the resource(starting value)
     * @param interval      Spacing of the images/resources
     * @param x             X coordinate of the bar
     * @param y             Y coordinate of the bar
     * @param resourceImage Image of the resource
     * @param world         World the bar is to be added in (do not add the resource bar manager to the world)
     */
    public ResourceBarManager(int max, int cur, int interval, int x, int y, GreenfootImage resourceImage, World world)
    {
        resourceStack = new Stack <Resource>();
        this.firstImage = new GreenfootImage(resourceImage);
        maxResource = max;
        currentResource = cur;
        this.interval = interval;
        currentX = x;
        currentY = y;
        for(int i = 0; i < cur; i++)
        {
            resourceStack.push(new Resource(resourceImage));
        }
        isHealthBar = false;
        addBarToWorld(world);
    }
    /**
     * Constructor 1: Used for health bar(creates a bar of images specified in the parameters)
     * 
     * @param max           Max amount of this resource possible
     * @param interval      Spacing of the images/resources
     * @param x             X coordinate of the bar
     * @param y             Y coordinate of the bar
     * @param firstImage    First image of the resource
     * @param secondImage   Second image of the resource
     * @param world         World the bar is to be added in (do not add the resource bar manager to the world)
     */
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
        addBarToWorld(world);
    }
    /**
     * Adds the resources in the stack to the world, creating a bar
     */
    public void addBarToWorld(World world)
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
    /**
     * Take damage depending amount specified in the parameters(only works if this is a health bar manager)
     */
    public boolean reduceHealth(int hits, World world)
    {
        if(isHealthBar)
        {
            for(int i = 0; i < hits; i ++)
            {
                //Since our hearts have two images one for full heart(two hits left)
                //And a half heart(one hit left)
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
    /**
     * Remove a resource/ammo from the stack, in the world remove one as well
     */
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
    /**
     * Heal/increase the health of the health bar
     */
    public boolean refillHealth(int Amount, World world)
    {
        if(isHealthBar)
        {
            for(int i = 0; i < Amount; i++)
            {
                //To heal half heart just switch image
                if(resourceStack.peek().getStatus() == 1)
                {
                    resourceStack.peek().switchImage();
                }
                //To heal from a full heart add a new heart that is half heart
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
    /**
     * Refills the stack and re-adds the resources back to the world
     */
    public boolean refillAmmo(World world)
    {
        if(isHealthBar == false)
        {
            for(int i = 0; i < maxResource - currentResource; i++)
            {
                if(resourceStack.size()>0) currentY = resourceStack.peek().getY();
                else currentY = 575-interval;
                resourceStack.push(new Resource(firstImage));
                world.addObject(resourceStack.peek(), currentX, currentY + interval);
            }
            currentResource = maxResource;
            return true;
        }
        return false;
    }
    /**
     * Removes an resource from the stack and the world
     */
    public void remove(World world){
        while(!resourceStack.empty()){
            world.removeObject(resourceStack.pop());
        }    
    }    
}
