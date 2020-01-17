import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * PauseMenu is a menu class that allows the user to pause the game, giving them the option to either resume or exit and save. 
 * 
 * @author Albert Lai 
 * @version January 2020
 */
public class PauseMenu extends Menu
{
    private static GreenfootImage pauseMenuImg = new GreenfootImage(200,120);
    private Label menuTitle = new Label("Pause", 27, true);
    private Button play = new Button("Play", 18, 20, 20, 20,  100, 100, 100);
    private Button exit = new Button("Exit and Save", 18, 20, 20, 20,  100, 100, 100);

    /**
     * Constructor for objects of class PauseMenu.
     */
    public PauseMenu(){
        pauseMenuImg.setColor(Color.LIGHT_GRAY);
        pauseMenuImg.fill();
        setImage(pauseMenuImg);
    }    

    /**
     * addedtoWorld - adds labels and buttons to the world
     * 
     * @param w             current world of the menu
     */
    public void addedToWorld(World w){
        PauseWorld world = (PauseWorld) getWorld();
        world.addObject(menuTitle,world.width/2,world.height/2 - 30);
        world.addObject(play,world.width/2,world.height/2 + 5);
        world.addObject(exit,world.width/2,world.height/2 + 30);
    }

    /**
     * checkButtonClicks - checks for button clicks
     */
    protected void checkButtonClicks(){
        PauseWorld world = (PauseWorld) getWorld();
        String key = Greenfoot.getKey();
        if(Greenfoot.mouseClicked(play) || "escape".equals(key)){
            //resume the game
            world.closeWorld();
        }
        else if(Greenfoot.mouseClicked(exit)){
            //return to title screen
            Greenfoot.setWorld(new TitleScreen());
        }    
    }    
}
