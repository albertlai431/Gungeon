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
        PauseWorld world = (PauseWorld) getWorld();
        world.addObject(menuTitle,world.width/2,world.height/2 - 30);
        world.addObject(play,world.width/2,world.height/2 + 5);
        world.addObject(exit,world.width/2,world.height/2 + 30);
    }

    protected void checkButtonClicks(){
        PauseWorld world = (PauseWorld) getWorld();
        String key = Greenfoot.getKey();
        if(Greenfoot.mouseClicked(play) || "escape".equals(key)){
            world.closeWorld();
        }
        else if(Greenfoot.mouseClicked(exit)){
            Greenfoot.setWorld(new TitleScreen());
        }    
    }    

    public void closeMenu(){
        PauseWorld world = (PauseWorld) getWorld();
        world.removeObject(menuTitle);
        world.removeObject(play);
        world.removeObject(exit);
        if(world!=null) world.removeObject(this);
    }    
}
