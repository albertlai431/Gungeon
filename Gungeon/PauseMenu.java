import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseMenu here.
 * 
 * @author Albert Lai 
 * @version January 2020
 */
public class PauseMenu extends Menu
{
    private static GreenfootImage pauseMenuImg = new GreenfootImage(200,120);
    private Label menuTitle = new Label("Pause", 27, true);
    private Button play = new Button("Play", 18);
    private Button exit = new Button("Exit", 18);

    /**
     * Constructor for objects of class PauseMenu.
     */
    public PauseMenu(){
        pauseMenuImg.setColor(Color.LIGHT_GRAY);
        pauseMenuImg.fill();
        setImage(pauseMenuImg);
    }    

    public void addedToWorld(World w){
        GameWorld world = (GameWorld) w;
        world.addObject(menuTitle,world.width/2,world.height/2 - 30);
        world.addObject(play,world.width/2,world.height/2 + 5);
        world.addObject(exit,world.width/2,world.height/2 + 30);
    }

    protected void checkButtonClicks(){
        GameWorld world = (GameWorld) getWorld();
        if(Greenfoot.mouseClicked(play)){
            world.play();
        }
        else if(Greenfoot.mouseClicked(exit)){
            world.closeWorld();
            Greenfoot.setWorld(new TitleScreen());
        }    
    }    

    public void closeMenu(){
        GameWorld world = (GameWorld) getWorld();
        world.removeObject(menuTitle);
        world.removeObject(play);
        world.removeObject(exit);
        if(world!=null) world.removeObject(this);
    }    
}
