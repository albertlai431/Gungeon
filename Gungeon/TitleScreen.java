import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    private Button play = new Button("PLAY",35);
    private Button options = new Button("CONTROLS",35);
    private Button load = new Button("LOAD",35);
    private MouseInfo mouse = Greenfoot.getMouseInfo();
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        addObject(play,35,560);
        addObject(load,40,590);
        addObject(options,77,620);
    }
    public void act(){
        if(Greenfoot.mouseClicked(play)){
            GameWorld gameWorld = new GameWorld(true);
            Greenfoot.setWorld(gameWorld);
        }
        else if(Greenfoot.mouseClicked(load)){
            GameWorld gameWorld = new GameWorld(false);
            Greenfoot.setWorld(gameWorld);
        }
        else if(Greenfoot.mouseClicked(options)){
            Options optionWorld = new Options();
            Greenfoot.setWorld(optionWorld);
        }
    }
}
