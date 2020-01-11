import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.NoSuchElementException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * GameWorld is the world where the game takes place. It holds the player, actors of the 
 * game, 2D array of actors, and game data. 
 * 
 * TODO:
 * 1. Make data work (GameWorld, PlayerData, WorldData, file directory)
 * 2. Make the actual text files 
 * 3. Switch world for pause menu (screenshot?)
 * 4. Implement images + fix walls
 * 5. Fix Ammunition and merge Clarence + Star's branches
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class GameWorld extends World
{
    //firstInd = xcoord, secondInd = ycoord
    private Actor [][] arr;
    private Player player;
    private PlayerData playerData;
    private static final String folderDir = "data" + File.separator + "save" + File.separator;
    private static final String sourceDir = "data" + File.separator + "source" + File.separator;

    public static final int height = 640;
    public static final int width = 960;
    private static final int totLevels = 5;
    private static final int totVersions = 5;
    public static final int tileSize = 32;
    private int curLevel;
    private static final int tileOffset = 16;
    private boolean lvlComplete = false;

    /**
     * Dummy constructor to make sure that things work
     * 
     */
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(width, height, 1,false); 
        arr = new Actor [width/tileSize][height/tileSize];
        StoreMenu.createImages();
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
                copyFile(sourceDir + "lvl" + Integer.toString(i) + Integer.toString(version) + ".txt", new File(folderDir + "lvl" + Integer.toString(i) + ".txt"));
            }   
        }    

        playerData = new PlayerData(playerFile);
        player = playerData.createPlayer();

        //check if player is complete this level
        if(true) lvlComplete = false;

        //get level somehow
        curLevel = 3;
        File worldFile = new File(folderDir + File.separator + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);
    }    

    /**
     * Constructor to switch between rooms
     * 
     * @param curLevel          current level of the world
     * @param player            player transferred from the other world
     * @param playerData        playerData transferred from the other world
     */
    public GameWorld(int curLevel,Player player, PlayerData playerData){
        this();
        this.curLevel = curLevel;
        this.player = player;
        this.playerData = playerData; 
        File worldFile = new File(folderDir + File.separator + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);
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
            Greenfoot.setWorld(new PauseWorld("pause",this));
        }    
        else if("z".equals(key)){
            Greenfoot.setWorld(new PauseWorld("store",this));
        }    
    }

    //game specific events
    /**
     * gameOver - checks if player is dead and ends the game, called by player class
     */
    public void gameOver(){
        //game over animation
    }

    /**
     * closeWorld - closes the current world
     */
    public void closeWorld(){
        playerData.saveData(player);
    }    

    /**
     * switchWorld - switch to a different room
     * 
     * @param newLevel              new level to switch to
     */
    public void switchWorld(int newLevel){
        closeWorld();
        Greenfoot.setWorld(new GameWorld(newLevel, player, playerData));
    }

    //methods dealing with text files
    /**
     * copyFile - takes contents of one file and copies it into another file
     * 
     * @param source            source file directory to copy content from
     * @param dest              Text file to copy contents into
     */
    private void copyFile(String source, File dest){
        try{
            Scanner scanner = new Scanner(new File(source));
            FileWriter fw = new FileWriter(dest);

            while(true){
                try{
                    String s = scanner.nextLine();
                    fw.write(s+"\n");
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
     * parseTextFile - takes world text file and parses it
     */
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
                int firstInd = Integer.parseInt(s.nextLine());
                int secondInd = Integer.parseInt(s.nextLine());
                int xcoord = convert(firstInd);
                int ycoord = convert(secondInd);
                boolean isEnemy = false;
                try{
                    if(arr[firstInd][secondInd]==null){
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
                        else if(actor.indexOf("Boss")==0){
                            a = new Boss();
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
                catch(Exception e){
                    System.out.println(firstInd + " " + secondInd);
                }    

            }    
            catch(NoSuchElementException e){
                break;
            }    
        }    
    }

    //helper classes
    /**
     * convert - takes the index value and converts to greenfoot X value
     * 
     * param secondIndex            second index of 2D to be converted
     * @return int                  greenfoot x value
     */
    public int convert(int index){
        return tileOffset + index*tileSize;
    }    
    
    public int convert(int index, boolean isWall){
        if(index == 0) return 28;
        else if(index==29 || index==19) return index*tileSize-28;
        else return convert(index);
    }    

    /**
     * isWall - takes position in 2D array and returns whether or not a wall exists
     * 
     * @param firstInd              first index of 2D array
     * @param secondInd             second index of 2D array
     * @return boolean              whether of not there's a wall
     */
    public boolean isWall(int firstInd, int secondInd){
        try{
            return arr[firstInd][secondInd] instanceof Walls;
        }    
        catch (ArrayIndexOutOfBoundsException e){
            return true;
        }    
    }   

    public void createTextFiles(){
        FileWriter fw = null;
        int[] obstacles = {0,2,5,10,20,30};
        //ShotgunEnemy, RifleEnemy, RocketEnemy, Boss
        int[][] enemies = {{4,6,8,6,5},{2,4,6,4,3},{0,1,2,5,3}};
        String[] obstaclesNames = {"Walls", "Arrows", "Fire", "Spikes"};

        for(int i=1;i<=totLevels;i++){
            for(int j=0;j<totVersions;j++){
                boolean curarr[][] = new boolean[30][20];
                File worldFile = new File(sourceDir + File.separator + "lvl" + Integer.toString(i) + Integer.toString(j) +".txt");
                try{
                    int a = 0;
                    fw = new FileWriter(worldFile);
                    //Walls
                    for(int x=0;x<30;x++){
                        fw.write("Walls\n" + x + "\n" + 0 + "\n");
                        fw.write("Walls\n" + x + "\n" + 19 + "\n");
                        curarr[x][0] = true; curarr[x][19] = true; a+=2;
                    }
                    for(int y=1;y<19;y++){
                        fw.write("Walls\n" + 0 + "\n" + y + "\n");
                        fw.write("Walls\n" + 29 + "\n" + y + "\n");
                        curarr[0][y] = true; curarr[29][y] = true; a+=2;
                    }    
                    
                    //Door

                    //Boss
                    if(i==5){
                        curarr[28][18]=true;
                        fw.write("Boss\n" + 28 + "\n" + 18 + "\n"); a++;
                    }    

                    //Obstacles (not passage)
                    for(String actor: obstaclesNames){
                        //walls
                        if(actor.equals("Walls")){
                            for(int k=0;k<Greenfoot.getRandomNumber(5);k++){
                                while(true){
                                    boolean works = true;
                                    int x = Greenfoot.getRandomNumber(30);
                                    int y = Greenfoot.getRandomNumber(20);
                                    int xSize = Greenfoot.getRandomNumber(5);
                                    int ySize = Greenfoot.getRandomNumber(5);
                                    try{
                                        for(int X=x; X<x+xSize; X++){
                                            for(int Y=y; Y<y+ySize; Y++){
                                                if(curarr[X][Y] || X==1 || X==28 || Y==1 || Y==18){
                                                    works=false;
                                                }    
                                            }   
                                        }    
                                    }
                                    catch(IndexOutOfBoundsException e){
                                        works = false;
                                    }

                                    if(works){
                                        for(int X=x; X<x+xSize; X++){
                                            for(int Y=y; Y<y+ySize; Y++){
                                                fw.write(actor + "\n" + X + "\n" + Y + "\n");
                                                curarr[X][Y] = true;
                                                a++;
                                            }   
                                        }
                                        break;
                                    }    
                                }
                            }    
                            continue;
                        }    
                        for(int k=0;k<Greenfoot.getRandomNumber(obstacles[i]-obstacles[i-1])+obstacles[i-1];k++){
                            while(true){
                                int x = Greenfoot.getRandomNumber(30);
                                int y = Greenfoot.getRandomNumber(20);
                                if(!curarr[x][y]){
                                    fw.write(actor + "\n" + x + "\n" + y + "\n");
                                    curarr[x][y] = true;
                                    a++;
                                    break;
                                }   
                            }
                        }
                    }

                    //Enemies (ShotgunEnemy, RifleEnemy, RocketEnemy, Boss)
                    for(int k=0;k<3;k++){
                        for(int l=0;l<enemies[0][i-1];l++){
                            while(true){
                                int x = Greenfoot.getRandomNumber(30);
                                int y = Greenfoot.getRandomNumber(20);
                                if(!curarr[x][y]){
                                    curarr[x][y] = true;
                                    a++;
                                    if(k==0) fw.write("ShotgunEnemy\n" + x + "\n" + y + "\n");
                                    else if(k==1) fw.write("RifleEnemy\n" + x + "\n" + y + "\n");
                                    else fw.write("RocketEnemy\n" + x + "\n" + y + "\n");
                                    break;
                                }   
                            }
                        }
                    }

                    fw.close();
                }
                catch(IOException e){
                    System.out.println("File Not Found");
                }    
            }
            System.out.println(i);
        }  
        System.out.println("done");
    }    
}
