import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;

/**
 * GameWorld is the world where the game takes place. It holds the player, elements of the * game, 2D array of actors, and game data. 
 * 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class GameWorld extends World
{
    private Actor [][] arr;
    private Player player;
    private int curLevel;
    private File txtFile;
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
    private void gameOver(){
        
    }

    /**
     * Act - do whatever the GameWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        keyboardInput();
        gameOver();
    }
    
    private void keyboardInput(){
        if(Greenfoot.getKey()=="escape"){
            if(isPaused){
                if(state==State.STORE) removeObjects(getObjects(StoreMenu.class));
                else if(state==State.PAUSE) removeObjects(getObjects(PauseMenu.class));
                play();
                state = State.PLAYING;
            }
            else{
                pause();
                addObject(new PauseMenu(),width/2,height/2);
                state = State.PAUSE;
            }
        }    
        else if(Greenfoot.getKey()=="s" && state==State.PLAYING){
            pause();
            addObject(new StoreMenu(),width/2,height/2);
            state = State.STORE;
        }    
    }    

    private void loadTextFile(){
        
    }

    public void pause(){
        isPaused = true;
    }

    public void play(){
        isPaused = false;
    }
}
