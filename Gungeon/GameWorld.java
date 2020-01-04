import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;

/**
 * GameWorld is the world where the game takes place. It holds the player, elements of the 
 * game, 2D array of actors, and game data. 
 * 
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
    private File playerTxtFile;
    private File worldTxtFile;
    private Menu menu;
    private enum State{
        PLAYING,
        PAUSE,
        STORE
    }    
    private State state;
    //private int actCount = 0;

    public int height;
    public int width;
    private boolean isPaused = false;

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1,false); 
        height = this.getHeight();
        width = this.getWidth();
        state = State.PLAYING;
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
