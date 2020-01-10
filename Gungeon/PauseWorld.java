import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PauseWorld extends World
{
    private GameWorld world;
    public static int height = 640;
    public static int width = 960;
    /**
     * Constructor for objects of class PauseWorld.
     * 
     */
    public PauseWorld(String menuTypes, GameWorld world)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        if(menuTypes=="pause") addObject(new PauseMenu(),480,320);
        else addObject(new StoreMenu(),480,320);
        this.world = world;
    }
    
    public void closeWorld(){
        Greenfoot.setWorld(world);
    }    
}
