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
 * game, 2D array of actors, and itemInfo. It is loaded from a text file and has methods 
 * to create, modify, and delete them. It checks for keyboard presses to open menus 
 * and contains methods to modify its objects. 
 * 
 * <p>
 * Gungeon is a remake of the classic Gungeon. The player travels through chambers/rooms, 
 * shooting enemies and avoiding traps to beat the game. It contains a store to allow the
 * user to buy items like more guns, health refills, and ammo. It can be saved to a text
 * file so the user can continue playing from a checkpoint. The tutorial shows how the 
 * game works and its controls. Enter the Gungeon!
 * </p>
 * 
 * <p>
 * Image credits to Star Xie and Enter the Gungeon.
 * Music credits to ZapSplat, soundcloud.com, Super Mario, and Enter the Gungeon.
 * </p>
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class GameWorld extends World
{
    //Objects and data structures
    private Actor [][] arr; //firstInd = xcoord, secondInd = ycoord
    private Player player;
    private Door door;
    private ItemInfo itemInfo;

    //Files
    private static final String folderDir = "data" + File.separator + "save" + File.separator;
    private static final String sourceDir = "data" + File.separator + "source" + File.separator;
    private File playerFile = new File(folderDir + "Player.txt");

    //Static variables
    public static final int height = 640;
    public static final int width = 960;
    private static final int totLevels = 5;
    private static final int totVersions = 5;
    public static final int tileSize = 32;
    private static final int tileOffset = 16;
    private static GreenfootSound gamePlay = new GreenfootSound("gameplay.mp3");
    private static GreenfootSound openRoom = new GreenfootSound("doorClosingNew.mp3");

    private int fromLevel = -1;
    private int curLevel;
    private boolean lvlComplete = false;
    private boolean startSound = false;

    /**
     * Base Constructor - not to be called directly
     */
    public GameWorld()
    {   
        super(width, height, 1,false); 
        arr = new Actor [width/tileSize][height/tileSize];
        addObject(new Cursor(),100,100);
        //Create Images
        StoreMenu.createImages();
        Player.createImages();
        setPaintOrder(Cursor.class,Timer.class,Resource.class,ResourceBarManager.class,Label.class,ItemInfo.class,Weapon.class,Ammunition.class,Player.class);
        //Music
        String key = Greenfoot.getKey();
        openRoom.setVolume(85);
        openRoom.play();
        gamePlay.setVolume(60);
    }

    /**
     * Contructor to initialize the world from "create a new game" or "load saved game"
     * 
     * @param boolean   true if creating new game, false loading from old game
     */
    public GameWorld(boolean newGame){
        this();
        //clear data and load from original text files
        if(newGame) transferTextFiles();

        //Create ItemInfo and Player
        itemInfo = new ItemInfo(0,-1,15,0);
        addObject(itemInfo,824,601);
        player = new Player(playerFile, itemInfo);
        curLevel = player.getCurLevel();
        if(curLevel == 1){
            addObject(player,convert(2),convert(2));
            arr[2][2] = player;
        }    
        if(player.getMaxLevel()>=curLevel) lvlComplete = true;

        //Create World
        File worldFile = new File(folderDir + File.separator + "lvl" + Integer.toString(curLevel) + ".txt");
        parseTextFile(worldFile);
    }    

    /**
     * Constructor to switch between rooms
     * 
     * @param curLevel          current level of the world
     * @param fromLevel         level of the previous room
     * @param player            player transferred from the other world
     * @param itemInfo          ItemInfo transferred from the other world
     */
    public GameWorld(int curLevel,int fromLevel,Player player, ItemInfo itemInfo){
        this();
        this.curLevel = curLevel;
        this.fromLevel = fromLevel;
        this.player = player;
        this.player.parseData();
        this.itemInfo = itemInfo;
        addObject(itemInfo,824,601);
        if(player.getMaxLevel()>=curLevel) lvlComplete = true;
        //create world
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
        if(fromLevel<curLevel && getObjects(Enemy.class).size()==0 && getObjects(BlobBoss.class).size()==0 && !lvlComplete){
            door.completeLevel();
            player.incrementMaxLevel();
            itemInfo.updateScore(curLevel*100);
            lvlComplete = true;
        }    

        //checks if openRoom sound is done playing
        if(!openRoom.isPlaying() && !startSound){
            startSound=true;
            gamePlay.playLoop();
        }    
    }

    /**
     * keyboardInput - checks for keyboard input
     */
    private void keyboardInput(){
        String key = Greenfoot.getKey();
        if("escape".equals(key)){
            //pause menu
            closeWorld(curLevel);
            gamePlay.pause();
            Greenfoot.setWorld(new PauseWorld("pause",this));
        }    
        else if("z".equals(key)){
            //store menu
            Greenfoot.setWorld(new PauseWorld(this,player,itemInfo));
        }    
    }

    /**
     * updateScore - called when enemies die to update the score, money, and kills
     * 
     * @param scoreBoost            amount to increase the score by
     * @param moneyBoost            amount to increase the money by
     */
    public void updateScore(int scoreBoost, int moneyBoost){
        //itemInfo 
        itemInfo.incrementKills();
        itemInfo.updateScore(scoreBoost);
        itemInfo.updateMoney(moneyBoost);
        //player
        player.setMoney(moneyBoost);
        player.setKills();
        player.setScore(scoreBoost);
    }    

    /**
     * gameOver - checks if player is dead and ends the game, called by player class
     * 
     * @param win               true if player has won, false if not
     */
    public void gameOver(boolean win){
        gamePlay.stop();
        deleteTextFiles();
        Greenfoot.setWorld(new PauseWorld(itemInfo.getScore(),win));
    }

    /**
     * closeWorld - closes the current world
     * 
     * @param newLevel          level of the world the player is in
     */
    public void closeWorld(int newLevel){
        gamePlay.pause();
        player.changeCurLevel(newLevel);
        player.saveData();
    }    

    /**
     * resumeWorld - resumes music playing
     */
    public void resumeWorld(){
        gamePlay.playLoop();
    }    

    /**
     * switchWorld - switch to a different room
     * 
     * @param newLevel              new level to switch to
     */
    public void switchWorld(int newLevel){
        gamePlay.pause();
        closeWorld(newLevel);
        Greenfoot.setWorld(new GameWorld(newLevel, curLevel, player, itemInfo));
    }

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
        //Scanner
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
                        //determine actor
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
                            a = new Walls(firstInd, secondInd);
                        } 
                        else if(actor.indexOf("Door")==0){
                            int doorLevel = (int)(actor.charAt(actor.length()-1))-48;
                            a = new Door(doorLevel,player.getMaxLevel()>=doorLevel-1);
                            if(fromLevel>curLevel){
                                if(secondInd==19){
                                    addObject(player,convert(firstInd),convert(secondInd-2));
                                    arr[firstInd][secondInd-2] = player;
                                }    
                            }    
                            else{
                                if(secondInd==19) door = (Door) a;
                                else{
                                    addObject(player,convert(firstInd),convert(secondInd+2));
                                    arr[firstInd][secondInd+2] = player;
                                }    
                            }
                        } 
                        else if(actor.indexOf("BulletEnemy")==0){
                            a = new BulletEnemy();
                            isEnemy = true;
                        }
                        else if(actor.indexOf("ShotgunEnemy")==0){
                            a = new ShotgunEnemy();
                            isEnemy = true;
                        } 
                        else if(actor.indexOf("SniperEnemy")==0){
                            a = new SniperEnemy();
                            isEnemy = true;
                        }
                        else if(actor.indexOf("Boss")==0){
                            a = new BlobBoss();
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

    /**
     * transferTextFiles - make new worlds from text files
     */
    private void transferTextFiles(){
        copyFile(sourceDir + "Player.txt", playerFile);
        for(int i=1;i<=totLevels;i++){
            int version = Greenfoot.getRandomNumber(totVersions);
            copyFile(sourceDir + "lvl" + Integer.toString(i) + Integer.toString(version) + ".txt", new File(folderDir + "lvl" + Integer.toString(i) + ".txt"));
        }
    }  

    /**
     * deleteTextFiles - Deletes text files
     */
    private void deleteTextFiles(){
        playerFile.delete();
        for(int i=1;i<=totLevels;i++){
            File worldFile = new File(folderDir + "lvl" + Integer.toString(i) + ".txt");
            worldFile.delete();
        }
    } 

    /**
     * convert - takes the index value and converts to greenfoot coordinate value
     * 
     * @param index                  index of 2D array to be converted
     * @return int                   greenfoot coordinate value
     */
    public static int convert(int index){
        return tileOffset + index*tileSize;
    }    

    /**
     * isWall - takes position in 2D array and returns whether or not a wall exists
     * 
     * @param firstInd              first index of 2D array
     * @param secondInd             second index of 2D array
     * @return boolean              whether or not there's a wall
     */
    public boolean isWall(int firstInd, int secondInd){
        try{
            return arr[firstInd][secondInd] instanceof Walls;
        }    
        catch (ArrayIndexOutOfBoundsException e){
            return true;
        }    
    }   
    
    /**
     * isObstacle - takes position in 2D array and returns whether or not a obstacle exists
     * 
     * @param firstInd              first index of 2D array
     * @param secondInd             second index of 2D array
     * @return boolean              whether or not there's a wall
     */
    public boolean isObstacle(int firstInd, int secondInd){
        try{
            return arr[firstInd][secondInd] instanceof Obstacles;
        }    
        catch (ArrayIndexOutOfBoundsException e){
            return true;
        }    
    }

    /**
     * createTextFiles - create new text files for the game (not to be called during gameplay)
     */
    public void createTextFiles(){
        FileWriter fw = null;
        //amount ofobstacles in levels
        int[] obstacles = {1,2,3,4,5};
        //BulletEnemy, SniperEnemy, ShotgunEnemy
        int[][] enemies = {{2,4,3,3},{1,2,4,3},{0,1,2,5}};
        String[] obstaclesNames = {"Walls", "Arrows", "Fire", "Spikes"};

        for(int i=1;i<=totLevels;i++){
            for(int j=0;j<totVersions;j++){
                boolean curarr[][] = new boolean[30][20];
                File worldFile = new File(sourceDir + File.separator + "lvl" + Integer.toString(i) + Integer.toString(j) +".txt");
                try{
                    int a = 0;
                    fw = new FileWriter(worldFile);

                    //Doors
                    int xval;
                    if(i!=1){
                        xval = Greenfoot.getRandomNumber(5)+2;
                        fw.write("Door" + (i-1) + "\n" + xval + "\n" + 0 + "\n");
                        for(int k=xval-1;k<=xval+1;k++){
                            for(int l=1;l<=3;l++) curarr[k][l]=true;
                        }    
                    }
                    xval = 20 - Greenfoot.getRandomNumber(5);
                    fw.write("Door" + (i+1) + "\n" + xval + "\n" + 19 + "\n");
                    for(int k=xval-1;k<=xval+1;k++){
                        for(int l=16;l<=18;l++) curarr[k][l]=true;
                    } 

                    //Walls (horizontal and vertical border)
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
                        fw.write("Boss\n" + 15 + "\n" + 10 + "\n"); a++;
                    }    
                    else{
                        //Obstacles 
                        for(String actor: obstaclesNames){
                            //Walls
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
                                                    if(curarr[X][Y] || X<=2 || X>=27 || Y<=2 || Y>=17 || (X==3 && Y==3)){
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
                            //Other obstacles
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

                        //Enemies (BulletEnemy, SniperEnemy, ShotgunEnemy, Boss)
                        for(int k=0;k<3;k++){
                            for(int l=0;l<enemies[k][i-1];l++){
                                while(true){
                                    int x = 10 + Greenfoot.getRandomNumber(20);
                                    int y = 7 + Greenfoot.getRandomNumber(13);
                                    if(!curarr[x][y]){
                                        curarr[x][y] = true;
                                        a++;
                                        if(k==0) fw.write("BulletEnemy\n" + x + "\n" + y + "\n");
                                        else if(k==1) fw.write("ShotgunEnemy\n" + x + "\n" + y + "\n");
                                        else fw.write("SniperEnemy\n" + x + "\n" + y + "\n");
                                        break;
                                    }   
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
