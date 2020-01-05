import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;

/**
 * GameWorld is the world where the game takes place. It holds the player, elements of the 
 * game, 2D array of actors, and game data. 
 * 
 * TODO:
 * 1. Make data work (GameWorld, PlayerData, WorldData, file directory)
 * 2. Integrate obstacles with the world
 * 3. Make the actual text files 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class GameWorld extends World
{
    private Actor [][] arr;
    private Player player;
    private PlayerData playerData;
    private WorldData worldData;
    private int curLevel;
    private String saveDir;
    private String folderDir;
    private Menu menu;
    private enum State{
        PLAYING,
        PAUSE,
        STORE
    }    
    private State state;

    public int height;
    public int width;
    private boolean isPaused = false;

    /**
     * Dummy constructor to make sure that things work
     * 
     */
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1,false); 
        height = getHeight();
        width = getWidth();
        state = State.PLAYING;
    }

    /**
     * Contructor to initialize the world from "create a new game" or "load saved game"
     * 
     * @param saveDir   a string representing the name of the save slot ("save1", "save2", "save3")
     * 
     */
    public GameWorld(String saveDir){
        this();
        this.saveDir = saveDir;
        this.folderDir = "data" + File.separator + saveDir;
        File playerFile = new File(folderDir + File.separator + saveDir.charAt(saveDir.length()-1) + "Player.txt");
        if(playerFile.isFile()) playerData = new PlayerData(playerFile);
        else System.out.println("File Not Found");
        
        //get level somehow
        curLevel = 1;
        File worldFile = new File(folderDir + File.separator + saveDir.charAt(saveDir.length()-1) + "lvl" + Integer.toString(curLevel) + ".txt");
        if(worldFile.isFile()) worldData = new WorldData(worldFile);
        else System.out.println("File Not Found");
    }    
    
    /**
     * Constructor to switch between rooms
     * 
     * 
     */
    public GameWorld(String saveDir,String folderDir, int curLevel,Player player, PlayerData playerData){
        this();
    }    

    /**
     * gameOver - checks if player is dead and ends the game
     */
    public void gameOver(){

    }

    /**
     * Act - do whatever the GameWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        keyboardInput();
    }

    /**
     * keyboardInput - checks for keyboard input
     */
    private void keyboardInput(){
        String key = Greenfoot.getKey();
        if("escape".equals(key)){
            if(isPaused){
                play();
            }
            else{
                pause();
                menu = new PauseMenu();
                addObject(menu,width/2,height/2);
                state = State.PAUSE;
            }
        }    
        else if("s".equals(key) && state!=State.PAUSE){
            if(isPaused){
                play();
            }
            else{
                pause();
                menu = new StoreMenu();
                addObject(menu,width/2,height/2);
                state = State.STORE;
            }
        }    
    }    

    private void loadTextFile(){
    }

    public void saveData(){
        playerData.saveData(player);

    }    

    public void pause(){
        isPaused = true;
    }

    public void play(){
        if(menu!=null && menu.getWorld()!=null) menu.closeMenu();
        state = State.PLAYING;
        isPaused = false;
    }
}
