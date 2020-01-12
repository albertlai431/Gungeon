import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseWorld here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class PauseWorld extends World
{
    private GameWorld gameWorld;
    public static int height = 640;
    public static int width = 960;
    /**
     * Constructor for objects of class PauseWorld.
     * 
     */
    public PauseWorld(String menuType, GameWorld gameWorld)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        setBackground("fullTiledFloor.png");
        if(menuType.equals("pause")) addObject(new PauseMenu(),480,320);
        this.gameWorld = gameWorld;
    }
    
    public PauseWorld(String menuType, GameWorld gameWorld, Player player){
        this(menuType, gameWorld);
        
    }    
    
    public void closeWorld(){
        Greenfoot.setWorld(gameWorld);
    }    
}
