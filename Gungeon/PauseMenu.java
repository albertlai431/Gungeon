import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseMenu here.
 * 
 * @author Albert Lai 
 * @version January 2020
 */
public class PauseMenu extends Actor
{
    private Button play = new Button();
    private Button save = new Button();
    private Button exit = new Button();
    
    /**
     * Constructor for objects of class PauseMenu.
     */
    public PauseMenu(){
        GameWorld world = (GameWorld) getWorld();
        world.addObject(play,world.width,100);
        world.addObject(save,world.width,200);
        world.addObject(exit,world.width,300);
    }    
    
    /**
     * Act - do whatever the PauseMenu wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkButtonClicks();
    }    
    
    private void checkButtonClicks(){
        if(Greenfoot.mouseClicked(play)){
            
        }
        else if(Greenfoot.mouseClicked(save)){
            
        }
        else if(Greenfoot.mouseClicked(exit)){
            
        }    
    }    
}
