import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 * GameWorld is the world where the game takes place. It holds the player, actors of the 
 * game, 2D array of actors, and game data. 
 * 
 * TODO:
 * 1. Make data work (GameWorld, PlayerData, WorldData, file directory)
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
    private static final String folderDir = "data" + File.separator + "save" + File.separator;
    private static final String sourceDir = "data" + File.separator + "source" + File.separator;
    private Menu menu;
    private enum State{
        PLAYING,
        PAUSE,
        STORE
    }    
    private State state;

    public int height;
    public int width;
    private static final int totLevels = 5;
    private static final int totVersions = 5;
    private int curLevel;
    private int tileSize;
    private int XOffset;
    private int YOffset;
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
     * @param boolean   true if loading from saved game, false if not
     * 
     */
    public GameWorld(boolean loadFromSavedGame){
        this();
        File playerFile = new File(folderDir + "Player.txt");
        if(!playerFile.isFile()){
            System.out.println("File Not Found");
            return;
        }    

        //clear data and load from original text files
        if(!loadFromSavedGame){
            copyFile(sourceDir + "Player.txt", playerFile);
            for(int i=1;i<=totLevels;i++){
                int version = Greenfoot.getRandomNumber(totVersions);
                copyFile(sourceDir + "lvl" + Integer.toString(i) + Integer.toString(version) + "txt", new File(folderDir + "lvl" + Integer.toString(i) + "txt"));
            }   
        }    

        playerData = new PlayerData(playerFile);

        //check if player is complete this level
        if(true) lvlComplete = true;

        //get level somehow
        curLevel = 1;
        File worldFile = new File(folderDir + File.separator + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);
    }    

    /**
     * Constructor to switch between rooms
     * 
     * 
     */
    public GameWorld(int curLevel,Player player, PlayerData playerData){
        this();
        curLevel = this.curLevel;

    }    

    private void copyFile(String source, File dest){
        try{
            Scanner scanner = new Scanner(new File(source));
            FileWriter fw = new FileWriter(dest);

            while(true){
                try{
                    String s = scanner.nextLine();
                    fw.write(s+"\n");
                    System.out.println(s);
                    fw.flush();
                }
                catch(NoSuchElementException e){
                    break;
                }    
            }    
            fw.close();
            scanner.close();
        }
        catch(java.io.IOException e){
            System.out.println("No such file");
        } 
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
                        a = new Fire(firstInd, secondInd);
                    }    
                    else if(actor.indexOf("Spikes")==0){
                        a = new Spikes(firstInd, secondInd);
                    }  
                    else if(actor.indexOf("Walls")==0){
                        a = new Walls();
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
        return XOffset + secondIndex*tileSize;
    }    

    /**
     * convertY - takes the index value and converts to greenfoot Y value
     */
    public int convertY(int firstIndex){
        return YOffset + firstIndex*tileSize;
    }    

    public boolean isWall(int firstInd, int secondInd){
        try{
            return arr[firstInd][secondInd] instanceof Walls;
        }    
        catch (ArrayIndexOutOfBoundsException e){
            return true;
        }    
    }   

    public void closeWorld(){
        playerData.saveData(player);
    }    
}
