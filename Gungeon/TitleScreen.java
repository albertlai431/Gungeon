import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    private Button play = new Button("NEW GAME",35);
    private Button options = new Button("CONTROLS",35);
    private Button load = new Button("LOAD",35);
    private MouseInfo mouse = Greenfoot.getMouseInfo();
    private File playerFile;
    private GreenfootSound titleScreenSound = new GreenfootSound("titlePage.mp3");
    private boolean startedPlaying = false;
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        playerFile = new File("data" + File.separator + "save" + File.separator + "Player.txt");
        if(playerFile.isFile()){
            addObject(play,84,560);
            addObject(load,40,590);
            addObject(options,77,620);
        }
        else{
            addObject(play,84,590);
            addObject(options,77,620);
        }
        titleScreenSound.setVolume(70);
    }
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
