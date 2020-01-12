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
 * - Player data
 * - Wall and door detection with player and enemies
 * - Integrate player with store
 * - Game Over and Victory animation 
 * - Leaderboard?? 
 * 
 * NICE TO HAVE
 * - Screenshot 
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
    private Door door;
    private ItemInfo itemInfo;
    private static final String folderDir = "data" + File.separator + "save" + File.separator;
    private static final String sourceDir = "data" + File.separator + "source" + File.separator;
    private Button returnToTitleScreen = new Button("Return to Title Screen", 20);
    public static final int height = 640;
    public static final int width = 960;
    private static final int totLevels = 5;
    private static final int totVersions = 5;
    public static final int tileSize = 32;
    private int fromLevel = -1;
    private int curLevel;
    private static final int tileOffset = 16;
    private boolean lvlComplete = false;
    private boolean gameOver = false;

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
        setPaintOrder(Label.class,ItemInfo.class,Player.class);
    }

    /**
     * Contructor to initialize the world from "create a new game" or "load saved game"
     * 
     * @param boolean   true if loading from saved game, false if not
     * 
     */
    public GameWorld(boolean newGame){
        this();
        File playerFile = new File(folderDir + "Player.txt");
        if(!playerFile.isFile()){
            System.out.println("File Not Found");
            return;
        }    

        //clear data and load from original text files
        if(newGame){
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
        curLevel = 1;
        File worldFile = new File(folderDir + File.separator + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);

        itemInfo = new ItemInfo(0,-1,5);
        addObject(itemInfo,824,601);
    }    

    /**
     * Constructor to switch between rooms
     * 
     * @param curLevel          current level of the world
     * @param player            player transferred from the other world
     * @param playerData        playerData transferred from the other world
     */
    public GameWorld(int curLevel,int fromLevel,Player player, PlayerData playerData){
        this();
        this.curLevel = curLevel;
        this.fromLevel = fromLevel;
        this.player = player;
        this.playerData = playerData; 
        File worldFile = new File(folderDir + File.separator + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);

        //get player's current gun
        itemInfo = new ItemInfo(0,-1,5);
        addObject(itemInfo,824,601);
    }    

    /**
     * Act - do whatever the GameWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        if(gameOver){
            if(Greenfoot.mouseClicked(returnToTitleScreen)) Greenfoot.setWorld(new TitleScreen());
        }
        else{
            keyboardInput();
            //checks if level is complete
            if(fromLevel<curLevel && getObjects(Enemy.class).size()==0){
                door.completeLevel();
                //set player level
            }    
        }
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
            Greenfoot.setWorld(new PauseWorld("store",this,player));
        }    
    }

    //game specific events
    /**
     * gameOver - checks if player is dead and ends the game, called by player class
     */
    public void gameOver(boolean win){
        //game over animation
        if(win){} //victory animation
        else{} //game over animation
        
        //save to leaderboard?

        gameOver = true;
        addObject(returnToTitleScreen,width/2,height-100);
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
        Greenfoot.setWorld(new GameWorld(newLevel, curLevel, player, playerData));
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
                        else if(actor.indexOf("Door")==0){
                            int doorLevel = (int)(actor.charAt(actor.length()-1))-48;
                            a = new Door(doorLevel,/*cur player level*/curLevel>=doorLevel);
                            if(fromLevel>curLevel){
                                if(secondInd==19) addObject(player,convert(firstInd),convert(secondInd-2));
                            }    
                            else{
                                if(secondInd==19) door = (Door) a;
                                else addObject(player,convert(firstInd),convert(secondInd+2));
                            }
                        } 
                        //fix
                        else if(actor.indexOf("RifleEnemy")==0){
                            a = new SniperEnemy(200,2);
                            isEnemy = true;
                        }
                        else if(actor.indexOf("ShotgunEnemy")==0){
                            a = new ShotgunEnemy(100,2);
                            isEnemy = true;
                        } 
                        else if(actor.indexOf("RocketEnemy")==0){
                            a = new BulletEnemy(300,2);
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
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("a " + firstInd + " " + secondInd);
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

                    //Door
                    int xval = Greenfoot.getRandomNumber(5)+2;
                    fw.write("Door" + (i-1) + "\n" + xval + "\n" + 0 + "\n");
                    xval = 20 - Greenfoot.getRandomNumber(5);
                    fw.write("Door" + (i+1) + "\n" + xval + "\n" + 19 + "\n");

                    //Walls (horizontal and vertical)
                    for(int x=0;x<30;x++){
                        if(!curarr[x][0]) fw.write("Walls\n" + x + "\n" + 0 + "\n");
                        if(!curarr[x][19] && x!=0 && x!=29) fw.write("Walls\n" + x + "\n" + 19 + "\n");
                        curarr[x][0] = true; curarr[x][19] = true; a+=2;
                    }
                    for(int y=1;y<19;y++){
                        if(!curarr[0][y]) fw.write("Walls\n" + 0 + "\n" + y + "\n");
                        if(!curarr[29][y]) fw.write("Walls\n" + 29 + "\n" + y + "\n");
                        curarr[0][y] = true; curarr[29][y] = true; a+=2;
                    }    

                    //Boss
                    if(i==5){
                        curarr[27][16]=true;
                        fw.write("Boss\n" + 27 + "\n" + 16 + "\n"); a++;
                    }    

                    //Obstacles (not passage)
                    for(String actor: obstaclesNames){
                        //walls
                        if(actor.equals("Walls")){
                            for(int k=0;k<6-i+Greenfoot.getRandomNumber(2);k++){
                                while(true){
                                    boolean works = true;
                                    int x = Greenfoot.getRandomNumber(30);
                                    int y = Greenfoot.getRandomNumber(20);
                                    int xSize = 3 + Greenfoot.getRandomNumber(5);
                                    int ySize = 3 + Greenfoot.getRandomNumber(5);
                                    try{
                                        for(int X=x; X<x+xSize; X++){
                                            for(int Y=y; Y<y+ySize; Y++){
                                                if(curarr[X][Y] || X<=2 || X>=27 || Y<=2 || Y>=17){
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
