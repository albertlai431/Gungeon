import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseMenu here.
 * 
 * @author Albert Lai 
 * @version January 2020
 */
public class PauseMenu extends Menu
{
    private Button play = new Button("Play", 15);
    private Button exit = new Button("Exit", 15);
    
    /**
     * Constructor for objects of class PauseMenu.
     */
    public PauseMenu(){
        
    }    
    
    public void addedToWorld(World w){
        GameWorld world = (GameWorld) w;
        world.addObject(play,world.width/2,100);
        world.addObject(exit,world.width/2,300);
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
        GameWorld world = (GameWorld) getWorld();
        if(Greenfoot.mouseClicked(play)){
            world.play();
        }
        else if(Greenfoot.mouseClicked(exit)){
            
        }    
    }    
    
    public void closeMenu(){
        GameWorld world = (GameWorld) getWorld();
        world.removeObject(play);
        world.removeObject(exit);
        if(world!=null) world.removeObject(this);
    }    
}
