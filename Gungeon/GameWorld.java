import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.NoSuchElementException;

/**
 * GameWorld is the world where the game takes place. It holds the player, actors of the 
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
    private int curLevel;
    private boolean isPaused = false;
    private boolean lvlComplete = false;

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
        
        //check if player is complete this level
        if(true) lvlComplete = true;

        //get level somehow
        curLevel = 1;
        File worldFile = new File(folderDir + File.separator + saveDir.charAt(saveDir.length()-1) + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);
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
        
        //checks if level is complete
        if(getObjects(Enemy.class).size()==0) lvlComplete = true;
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

    private void parseTextFile(File worldFile){
        Scanner s = null;
        try{
            s = new Scanner(worldFile);
        }    
        catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }    

        while(true){
            try{
                String actor = s.nextLine();
                int secondInd = Integer.parseInt(s.nextLine());
                int firstInd = Integer.parseInt(s.nextLine());
                int xcoord = convertX(secondInd);
                int ycoord = convertY(firstInd);
                boolean isEnemy = false;
                if(arr[firstInd][secondInd]!=null){
                    Actor a = null;
                    if(actor.indexOf("Arrows")==0){
                        a = new Arrows();
                    }    
                    else if(actor.indexOf("Fire")==0){
                        a = new Fire();
                    }    
                    else if(actor.indexOf("Spikes")==0){
                        a = new Spikes();
                    }  
                    else if(actor.indexOf("Walls")==0){
                        a = new Walls();
                    } 
                    else if(actor.indexOf("Player")==0){
                        addObject(player, xcoord, ycoord);
                        arr[firstInd][secondInd] = player;
                    }  
                    else if(actor.indexOf("RifleEnemy")==0){
                        a = new RifleEnemy();
                        isEnemy = true;
                    }
                    else if(actor.indexOf("ShotgunEnemy")==0){
                        a = new ShotgunEnemy();
                        isEnemy = true;
                    } 
                    else if(actor.indexOf("RocketEnemy")==0){
                        a = new RocketEnemy();
                        isEnemy = true;
                    }
                    else{
                        System.out.println("Invalid actor");
                    }   

                    if(a!=null && !(lvlComplete && isEnemy)){
                        addObject(a, xcoord, ycoord);
                        arr[firstInd][secondInd] = a;
                    }    
                }
            }    
            catch(NoSuchElementException e){
                break;
            }    
        }    
    }

    public void pause(){
        isPaused = true;
    }

    public void play(){
        if(menu!=null && menu.getWorld()!=null) menu.closeMenu();
        state = State.PLAYING;
        isPaused = false;
    }

    /**
     * convertX - takes the index value and converts to greenfoot X value
     */
    public int convertX(int secondIndex){
        return 0;
    }    

    /**
     * convertY - takes the index value and converts to greenfoot Y value
     */
    public int convertY(int firstIndex){
        return 0;
    }    
}
