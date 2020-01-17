import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
/**
 * Title screen is a world where it allows the player to start a new game, load a previous save, or open the controls page.
 * 
 * @author Alex Li
 * @version 1.1
 */
public class TitleScreen extends World
{
    //instance variables
    private Button play = new Button("NEW GAME",35);
    private Button options = new Button("CONTROLS",35);
    private Button load = new Button("LOAD",35);
    private MouseInfo mouse = Greenfoot.getMouseInfo();
    private File playerFile;
    private GreenfootSound titleScreenSound = new GreenfootSound("titlePage.mp3");
    private boolean startedPlaying = false;
    /**
     * Creates a new title screen where it allows the user to start a new game, load a save, and access the controls 
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        //used to check if there is a preexisting save
        playerFile = new File("data" + File.separator + "save" + File.separator + "Player.txt");
        //if there is add all the buttons to the screen
        if(playerFile.isFile()){
            addObject(play,84,560);
            addObject(load,40,590);
            addObject(options,77,620);
        }
        //if there is not, do not give the user an option to load
        else{
            addObject(play,84,590);
            addObject(options,77,620);
        }
        titleScreenSound.setVolume(70);
    }
    /**
     * Act - do whatever the TitleScreen wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     * Checks for mouse input and acts accordingly.
     */
    public void act(){
        if(Greenfoot.mouseClicked(play)){
            titleScreenSound.stop();
            GameWorld gameWorld = new GameWorld(true);
            Greenfoot.setWorld(gameWorld);
        }
        else if(Greenfoot.mouseClicked(load)){
            titleScreenSound.stop();
            GameWorld gameWorld = new GameWorld(false);
            Greenfoot.setWorld(gameWorld);
        }
        else if(Greenfoot.mouseClicked(options)){
            Options optionWorld = new Options();
            Greenfoot.setWorld(optionWorld);
        }
        if(!startedPlaying){
            titleScreenSound.playLoop();
            startedPlaying = true;
        }    
    }
}
