import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Player is the main character in the game. It is saved and loaded from a text file and holds the gun. 
 * It is controlled by the user with key presses, allowing it to move around and shoot. It has
 * stats like level, health, speed, and money. It implements the animation interface to move
 * smoothly in the 4 different directions. 
 * 
 * @author Albert Lai, Star Xie, and Clarence Chau
 * @version January 2020
 */
public class Player extends Actor implements AnimationInterface
{
    //Declare Greenfoot Images
    private static GreenfootImage[] rightMvt = new GreenfootImage[5];
    private static GreenfootImage[] leftMvt = new GreenfootImage[5];
    private static GreenfootImage[] upMvt = new GreenfootImage[8];
    private static GreenfootImage[] downMvt = new GreenfootImage[8];
    private static GreenfootImage pistolImg = new GreenfootImage("Pistol.png");
    private static GreenfootImage rifleImg = new GreenfootImage("Rifle.png");
    private static GreenfootImage shotgunImg = new GreenfootImage("Shotgun.png");
    private static GreenfootImage pistolImgMir = new GreenfootImage("Pistol.png");
    private static GreenfootImage rifleImgMir = new GreenfootImage("Rifle.png");
    private static GreenfootImage shotgunImgMir = new GreenfootImage("Shotgun.png");
    private static GreenfootImage pistolBarImg = new GreenfootImage("pistolShell.png");
    private static GreenfootImage rifleBarImg = new GreenfootImage("rifleShell.png");
    private static GreenfootImage shotgunBarImg = new GreenfootImage("shotgunShell.png");
    private static GreenfootImage halfHeart = new GreenfootImage("halfHeart.png");
    private static GreenfootImage fullHeart = new GreenfootImage("fullHeart.png");
    private static GreenfootSound loseHeart = new GreenfootSound("loseHeart.mp3");

    //ResourceBarManagers
    private ResourceBarManager healthBar;
    private ResourceBarManager reloadBar;

    //Static vars
    private static boolean createdImages = false;
    private static final int scaleNumber = 100;

    //Data Structures and objects
    private File txtFile;
    private HashMap <String,Integer> items = new HashMap <String,Integer>();
    private Weapon gun = null;
    private ItemInfo itemInfo;
    private ArrayList<String> listOfGuns = new ArrayList<String>(); //Pistol, Shotgun, Rifle
    private HashMap <String, Integer> ammoInMag = new HashMap <String,Integer>();
    private String curgun; //only for constructor purposes

    //Instance variables
    private long animationCount = 0;
    private int frameRate = 7;
    private int imageNumber = 0;
    private int curLevel;
    private int maxLevel;
    private int money;
    private int hearts;
    private int speed;
    private int kills;
    private int score;
    private int weapon = 0; // 0 = pistol, 1 = shotgun, 2 = rifle
    private int XCoord;
    private int YCoord;

    /**
     * Constructor for Player Class
     * 
     * @param txtFile           txtFile storing the player
     * @param itemInfo          itemInfo of the world
     */
    public Player(File txtFile, ItemInfo itemInfo)
    {
        setImage(rightMvt[0]);
        this.txtFile = txtFile;
        this.itemInfo = itemInfo;
        parseData();
    }

    /**
     * addedToWorld - adds guns and resource bars to the world
     * 
     * @param w             current world of the player
     */
    public void addedToWorld(World w){
        changeGun(); 
        while(!getCurrentGun().equals(curgun)) changeGun();
        healthBar =  new ResourceBarManager(3, 20, 900, 620, fullHeart, halfHeart, (GameWorld) getWorld());
        healthBar.reduceHealth(6-hearts, (GameWorld) getWorld());
    }  

    /**
     * createImages - create images used by player class if not already done so
     */
    public static void createImages(){
        if(!createdImages){
            createdImages = true;
            for(int i=0; i<rightMvt.length; i++)
            {
                rightMvt[i] = new GreenfootImage("pilotRight"+i+".png");
                leftMvt[i] = new GreenfootImage("pilotRight"+i+".png");
            }
            for(int i=0; i<leftMvt.length; i++)
            {
                leftMvt[i].mirrorHorizontally();
            }
            for(int i=0; i<upMvt.length; i++)
            {
                upMvt[i] = new GreenfootImage("pilotUp"+i+".png");
                downMvt[i] = new GreenfootImage("pilotDown"+i+".png");
            }

            for(int i=0; i<rightMvt.length;i++)
            {
                rightMvt[i].scale(rightMvt[i].getWidth()*scaleNumber/100,rightMvt[i].getHeight()*scaleNumber/100);
                leftMvt[i].scale(leftMvt[i].getWidth()*scaleNumber/100,leftMvt[i].getHeight()*scaleNumber/100);
            }
            for(int i=0; i<upMvt.length;i++)
            {
                upMvt[i].scale(upMvt[i].getWidth()*scaleNumber/100,upMvt[i].getHeight()*scaleNumber/100);
                downMvt[i].scale(downMvt[i].getWidth()*scaleNumber/100,downMvt[i].getHeight()*scaleNumber/100);
            }

            pistolImg.scale(pistolImg.getWidth()*150/100,pistolImg.getHeight()*150/100);
            rifleImg.scale(rifleImg.getWidth()*150/100,rifleImg.getHeight()*150/100);
            shotgunImg.scale(shotgunImg.getWidth()*150/100,shotgunImg.getHeight()*150/100);
            pistolImgMir.scale(pistolImgMir.getWidth()*150/100,pistolImgMir.getHeight()*150/100);
            rifleImgMir.scale(rifleImgMir.getWidth()*150/100,rifleImgMir.getHeight()*150/100);
            shotgunImgMir.scale(shotgunImgMir.getWidth()*150/100,shotgunImgMir.getHeight()*150/100);
            pistolImgMir.mirrorVertically();
            rifleImgMir.mirrorVertically();
            shotgunImgMir.mirrorVertically();
            
            //sounds
            loseHeart.setVolume(70);
        }
    }    

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //player moving and animations
        animationCount++;  
        move();
        if(Greenfoot.getMouseInfo() != null)
        {
            XCoord = Greenfoot.getMouseInfo().getX();
            YCoord = Greenfoot.getMouseInfo().getY();
        }
        int x = this.getX();
        int y = this.getY();
        if(x-XCoord<0)
        {
            if(y-YCoord>x-XCoord && y-YCoord<-1*(x-XCoord)) animateMovementRight();
        }
        if(x-XCoord>0)
        {
            if(y-YCoord<x-XCoord && -1*(y-YCoord)<x-XCoord) animateMovementLeft();
        }
        if(y-YCoord<0)
        {
            if(x-XCoord>y-YCoord && x-XCoord<-1*(y-YCoord)) animateMovementDown();
        }
        if(y-YCoord>0)
        {
            if(x-XCoord<y-YCoord && -1*(x-XCoord)<y-YCoord) animateMovementUp();
        }

        //gun switching
        if(animationCount%8==0 && Greenfoot.isKeyDown("e") && listOfGuns.size()>1) changeGun();
    }   

    /**
     * move - moves the player depending on keyboard input along with wall/door detection
     */
    private void move()
    {
        int dx = 0, dy = 0;
        if(Greenfoot.isKeyDown("a"))//runs if "a" is pressed and the player is past the starting location
        {
            //animateMovementLeft();
            if(Greenfoot.isKeyDown("s")){
                dx = -speed; dy = speed;
            }
            else if(Greenfoot.isKeyDown("w")){
                dx = -speed; dy = -speed;
            }
            else dx = -speed;
        }
        else if(Greenfoot.isKeyDown("d"))//runs if "d" is pressed
        {
            //animateMovementRight();
            if(Greenfoot.isKeyDown("s")){
                dx = speed; dy = speed;
            }
            else if(Greenfoot.isKeyDown("w")){
                dx = speed; dy = -speed;
            }
            else dx = speed;
        }
        else if(Greenfoot.isKeyDown("w"))//runs if "d" is pressed
        {
            //animateMovementUp();
            if(Greenfoot.isKeyDown("a")) {
                dx = -speed; dy = -speed;
            }
            else if(Greenfoot.isKeyDown("d")) {
                dx = speed; dy = -speed;
            }
            else dy = -speed;
        }
        else if(Greenfoot.isKeyDown("s"))//runs if "d" is pressed
        {
            //animateMovementDown();
            if(Greenfoot.isKeyDown("a")) {
                dx = -speed; dy = speed;
            }
            else if(Greenfoot.isKeyDown("d")) {
                dx = speed; dy = speed;
            }
            else dy = speed;
        }

        setLocation(getX()+dx,getY()+dy);

        //checks for invalid move
        if(invalidMove()) setLocation(getX()-dx,getY()-dy);
        else{
            //set gun location
            if(this.getCurrentGun().equals("Pistol"))
            {
                gun.setLocation(getX()+18, getY()+16);
            }
            else if(this.getCurrentGun().equals("Rifle")){
                gun.setLocation(getX()+20, getY()+13);
            }
            else if(this.getCurrentGun().equals("Shotgun")){
                gun.setLocation(getX()+16, getY()+15);
            }
        }
    } 

    /**
     * invalidMove - checks if the last move was invalid
     * 
     * @return boolean              true if the move was invalid and false if not
     */
    private boolean invalidMove(){
        for(int i =-1; i<=1;i++){
            for(int j =-1;j<=1;j++){
                Door door = (Door) getOneObjectAtOffset​(i*20, j*30, Door.class);
                if((door!=null  && !door.getComplete()) || getOneObjectAtOffset​(i*20, j*30, Walls.class)!=null){
                    return true;
                }    
            }   
        }
        return false;
    }    

    /**
     * getCurrentgun - returns the name of the current gun
     * 
     * @return String               the name of the current gun
     */
    public String getCurrentGun()
    {
        return listOfGuns.get(0);
    }

    /**
     * hasGun - returns whether or not the player has the specified gun
     * 
     * @param gunName               Name of the gun
     * @return boolean              true if the player has the gun and false if not
     */
    public boolean hasGun(String gunName){
        for(String gun: listOfGuns){
            if(gunName.contains(gun)) return true;
        }    
        return false;
    }    

    /**
     * changeGun - changes the gun to the next one in the arraylist
     */
    public void changeGun()
    {
        String x = listOfGuns.remove(0);
        if(gun!=null) ammoInMag.put(x,gun.ammoInMag);
        listOfGuns.add(x);
        if(gun!=null) getWorld().removeObject(gun);

        if(reloadBar!=null) reloadBar.remove(getWorld());

        if(listOfGuns.get(0).equals("Pistol")){
            if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(0, -1, ammoInMag.get("Pistol"));
            gun = new Pistol(itemInfo,this,50,4,200,200,600,15, ammoInMag.get("Pistol"),true);
            getWorld().addObject(gun,getX()+18, getY()+16);
            reloadBar = new ResourceBarManager(10, Math.min(10, ammoInMag.get("Pistol")), 6, 700, 575, pistolBarImg, (GameWorld) getWorld());
        }    
        else if(listOfGuns.get(0).equals("Shotgun")){
            if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(2, items.get("Shotgun Bullet"), ammoInMag.get("Shotgun"));
            gun = new Shotgun(itemInfo,this,300,5,700,700,1000,8,ammoInMag.get("Shotgun"),true);
            getWorld().addObject(gun,getX()+16, getY()+15);
            reloadBar = new ResourceBarManager(8, ammoInMag.get("Shotgun"), 7, 700, 575, shotgunBarImg, (GameWorld) getWorld());
        }    
        else{
            if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(1, items.get("Rifle Bullet"), ammoInMag.get("Rifle"));
            gun = new Rifle(itemInfo,this,100,6,150,150,400,30,ammoInMag.get("Rifle"),true);
            getWorld().addObject(gun,getX()+20, getY()+13);
            reloadBar = new ResourceBarManager(10, Math.min(10, ammoInMag.get("Rifle")), 6, 700, 575, rifleBarImg, (GameWorld) getWorld());
        } 
    }

    /**
     * newGun - adds a new gun to the player
     * 
     * @param gun               name of the gun
     */
    public void newGun(String gun)
    {
        if(gun.contains("Shotgun")){
            listOfGuns.add("Shotgun");
            ammoInMag.put("Shotgun",8);
        }
        else{
            listOfGuns.add("Rifle");
            ammoInMag.put("Rifle",30);
        }
    }

    /**
     * loseOneHeart - takes one half-heart away from the player
     */
    public void loseOneHeart()
    {
        loseHeart.play();
        hearts--;
        healthBar.reduceHealth(1,(GameWorld) getWorld());
        if(hearts == 0){
            GameWorld world = (GameWorld) getWorld();
            world.gameOver(false); 
        }   
    }

    /**
     * addOneHeart - adds a half-heart to the player
     */
    public void addOneHeart()
    {
        hearts++;
        healthBar.refillHealth(1, (GameWorld) getWorld());
    }

    /**
     * animateMovementUp - animation when player moves up. 
     */
    public void animateMovementUp()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (upMvt.length);
            setImage(upMvt[imageNumber]);
        }
        if(this.getCurrentGun().equals("Pistol"))
        {
            gun.setImage(pistolImg);
            gun.setLocation(getX()-10,getY()-12);
        }
        else if(this.getCurrentGun().equals("Rifle"))
        {
            gun.setImage(rifleImg);
            gun.setLocation(getX()-20,getY()+4);
        }
        else if(this.getCurrentGun().equals("Shotgun"))
        {
            gun.setImage(shotgunImg);
            gun.setLocation(getX()-10,getY()+6);
        }
        gun.getImage().setTransparency(0);
    }

    /**
     * animateMovementDown - animation when player moves down. 
     */
    public void animateMovementDown()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (downMvt.length);
            setImage(downMvt[imageNumber]);
        }
        gun.getImage().setTransparency(255);
    }

    /**
     * animateMovementRight - animation when player moves right. 
     */
    public void animateMovementRight()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (rightMvt.length);
            setImage(rightMvt[imageNumber]);
        }
        if(this.getCurrentGun().equals("Pistol"))
        {
            gun.setImage(pistolImg);
            gun.getImage().setTransparency(255);
        }
        else if(this.getCurrentGun().equals("Rifle"))
        {
            gun.setImage(rifleImg);
            gun.getImage().setTransparency(255);
        }
        else if(this.getCurrentGun().equals("Shotgun"))
        {
            gun.setImage(shotgunImg);
            gun.getImage().setTransparency(255);
        }
    }

    /**
     * animateMovementLeft - animation when player moves left. 
     */
    public void animateMovementLeft()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (leftMvt.length);
            setImage(leftMvt[imageNumber]);
        }

        if(this.getCurrentGun().equals("Pistol"))
        {
            gun.setImage(pistolImgMir);
            gun.setLocation(getX()+10,getY()+12);
            gun.getImage().setTransparency(255);
        }
        else if(this.getCurrentGun().equals("Rifle"))
        {
            gun.setImage(rifleImgMir);
            gun.setLocation(getX()+9,getY()+12);
            gun.getImage().setTransparency(255);
        }
        else if(this.getCurrentGun().equals("Shotgun"))
        {
            gun.setImage(shotgunImgMir);
            gun.setLocation(getX()+10,getY()+12);
            gun.getImage().setTransparency(255);
        }
    }

    /**
     * canReload - checks if there is enough ammo for reload and if there is, update itemInfo and reloadBar
     * 
     * @param w                 current weapon of the player
     * @return int              number of bullets in mag
     */
    public int canReload(Weapon w){
        //shotgun
        if(w instanceof Shotgun){
            if(items.get("Shotgun Bullet")>0 && w.getAmmo()<8){
                reloadBar.refillAmmo((GameWorld) getWorld());
                if(items.get("Shotgun Bullet")+w.getAmmo()>=8){
                    items.put("Shotgun Bullet",items.get("Shotgun Bullet")-(8-w.getAmmo()));
                    itemInfo.updateAmmo(items.get("Shotgun Bullet"), 8);
                    return 8;
                }    
                else{
                    int ammoInMag = items.get("Shotgun Bullet")+w.getAmmo();
                    itemInfo.updateAmmo(0, ammoInMag);
                    for(int i=0;i<8-ammoInMag;i++) reloadBar.reduceAmmo((GameWorld) getWorld());
                    items.put("Shotgun Bullet",0);
                    return ammoInMag;
                }    
            }    
            else return 0;
        }   
        //rifle
        else if(w instanceof Rifle){
            if(items.get("Rifle Bullet")>0 && w.getAmmo()<30){
                reloadBar.refillAmmo((GameWorld) getWorld());
                if(items.get("Rifle Bullet")+w.getAmmo()>=30){
                    items.put("Rifle Bullet",items.get("Rifle Bullet")-(30-w.getAmmo()));
                    itemInfo.updateAmmo(items.get("Rifle Bullet"), 30);
                    return 30;
                }    
                else{
                    int ammoInMag = items.get("Rifle Bullet")+w.getAmmo();
                    itemInfo.updateAmmo(0, ammoInMag);
                    if(ammoInMag<10){
                        for(int i=0;i<10-ammoInMag;i++) reloadBar.reduceAmmo((GameWorld) getWorld());
                    }
                    items.put("Rifle Bullet",0);
                    return ammoInMag;
                }    
            }    
            else return 0;
        }
        //pistol
        else if(w.getAmmo()<15){
            itemInfo.updateAmmo(-1, 15);
            reloadBar.refillAmmo((GameWorld) getWorld());
            return 15;
        }  
        return 0;
    }    

    /**
     * saveData - save data from player into text file
     */
    public void saveData() {
        ammoInMag.put(getCurrentGun(),gun.ammoInMag);
        try {
            FileWriter fw = new FileWriter(txtFile);
            fw.write(curLevel + "\n");
            fw.write(maxLevel + "\n");
            fw.write(money + "\n");
            fw.write(hearts + "\n");
            fw.write(speed + "\n");
            fw.write(kills + "\n");
            fw.write(score + "\n");
            fw.write(listOfGuns.size() + "\n");
            for(String gun: listOfGuns){
                fw.write(gun + "\n");
                fw.write(ammoInMag.get(gun)+"\n");
            }    
            fw.write(getCurrentGun() + "\n");
            for(String key: items.keySet()){
                fw.write(key+"\n");
                fw.write(items.get(key)+"\n");
            }    
            fw.close();
        } catch (java.io.IOException e) {
            System.out.println("No such file");
        }
    }

    /**
     * parseData - load data from Player.txt 
     */
    public void parseData() {
        //create scanner
        Scanner s = null;
        try {
            s = new Scanner(txtFile);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }

        for (int i = 0; i < 8; i++) {
            int val = Integer.parseInt(s.nextLine());
            switch (i) {
                case 0:
                curLevel = val;
                break;
                case 1:
                maxLevel = val;
                break;
                case 2:
                money = val;
                itemInfo.updateMoney(money);
                break;
                case 3:
                hearts = val;
                break;
                case 4:
                speed = val;
                break;
                case 5:
                kills = val;
                for(int j=0;j<kills;j++) itemInfo.incrementKills();
                break;
                case 6:
                score = val;
                itemInfo.updateScore(score);
                break;
                case 7:
                for(int j=0;j<val;j++){
                    curgun = s.nextLine();
                    listOfGuns.add(curgun);
                    ammoInMag.put(curgun, Integer.parseInt(s.nextLine()));
                }    
            }
        }

        curgun = s.nextLine();

        for (int i = 0; i < 4; i++) {
            String item = s.nextLine();
            int val = Integer.parseInt(s.nextLine());
            items.put(item,val);
        }
    }

    /**
     * getMoney - get the amount of money
     * 
     * @return int      the amount of money
     */
    public int getMoney(){
        return money;
    }

    /**
     * setMoney - set the money
     * 
     * @param amount      the amount of money to add/remove
     */
    public void setMoney(int amount){
        money+=amount;
    }
    
    /**
     * setScore - set the score
     * 
     * @param amount        score to add        
     */
    public void setScore(int amount){
        score+=amount;
    }
    
    /**
     * incrementKills - increment kills by one      
     */
    public void setKills(){
        kills++;
    }

    /**
     * getItemNumber - gets the amount of item that the player has
     * 
     * @param name              Item name
     * @return int              amount of item
     */
    public int getItemNumber(String name){
        if(items.containsKey(name)) return items.get(name);
        return 0;
    }    

    /**
     * changeItemNumber - adds or removes an item
     * 
     * @param name              Item name
     * @param amount            Amount to add or remove
     */
    public void changeItemNumber(String name, int amount){
        if(name.equals("Shotgun Bullet")){
            items.replace("Shotgun Bullet",items.get("Shotgun Bullet")+amount);
            itemInfo.updateAmmo(items.get("Shotgun Bullet"), gun.getAmmo());
        } 
        else if(name.equals("Rifle Bullet")){
            items.replace("Rifle Bullet",items.get("Rifle Bullet")+amount);
            itemInfo.updateAmmo(items.get("Rifle Bullet"), gun.getAmmo());
        }
        else items.replace(name,items.get(name)+amount);
    }    

    /**
     * speedBoost - increase the speed
     */
    public void speedBoost(){
        speed++;
    }    

    /**
     * getCurLevel - returns the current level
     * 
     * @return int          current level of the player
     */
    public int getCurLevel(){
        return curLevel;
    }    

    /**
     * changeCurLevel - changes level of the player
     * 
     * @param amount        new level
     */
    public void changeCurLevel(int amount){
        curLevel=amount;
    }    

    /**
     * getMaxLevel - returns max level of the player
     * 
     * @return int          max level of the player
     */
    public int getMaxLevel(){
        return maxLevel;
    }  

    /**
     * incrementMaxLevel - increases max level
     */
    public void incrementMaxLevel(){
        maxLevel++;
    }    

    /**
     * getHearts - returns number of hearts of the player
     * 
     * @return int          number of hearts of the player
     */
    public int getHearts(){
        return hearts;
    }

    /**
     * reduceAmmo - updates reloadBar
     */
    public void reduceAmmo(){
        reloadBar.reduceAmmo((GameWorld) getWorld());
    }    
}
