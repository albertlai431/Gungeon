import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write a description of class Player here.
 * 
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player extends Actor implements AnimationInterface
{
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
    private static boolean createdImages = false;
    private int curLevel;
    private int maxLevel;
    private int money;
    private File txtFile;
    private HashMap <String,Integer> items = new HashMap <String,Integer>();
    private Weapon gun = null;
    private ItemInfo itemInfo;
    private String curgun; //only for constructor purposes

    private static final int scaleNumber = 100;
    private long animationCount = 0;
    private int frameRate = 7;
    private int imageNumber = 0;
    private int hearts;
    private int speed;
    private int weapon = 0; // 0 = pistol, 1 = shotgun, 2 = rifle
    private int XCoord;
    private int YCoord;
    private ArrayList<String> listOfGuns = new ArrayList<String>(); //Pistol, Shotgun, Rifle
    private ResourceBarManager healthBar;
    private ResourceBarManager reloadBar;

    public Player(File txtFile, ItemInfo itemInfo)
    {
        setImage(rightMvt[0]);
        this.txtFile = txtFile;
        this.itemInfo = itemInfo;
        parseData();
    }

    public void addedToWorld(World w){
        while(!getCurrentGun().equals(curgun)) changeGun();
        
        healthBar =  new ResourceBarManager(3, 20, 900, 620, fullHeart, halfHeart, (GameWorld) getWorld());
        if(curgun.equals("Pistol")){
            if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(0, -1, 15);
            gun = new Pistol(itemInfo,this,50,2,100,100,100,15);
            reloadBar = new ResourceBarManager(10, 6, 700, 575, pistolBarImg, (GameWorld) getWorld());
            if(getWorld()!=null) getWorld().addObject(gun,getX()+18, getY()+16);
        }    
    }  

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
            pistolImgMir.scale(pistolImg.getWidth()*150/100,pistolImg.getHeight()*150/100);
            rifleImgMir.scale(rifleImg.getWidth()*150/100,rifleImg.getHeight()*150/100);
            shotgunImgMir.scale(shotgunImg.getWidth()*150/100,shotgunImg.getHeight()*150/100);
            pistolImgMir.mirrorVertically();
            rifleImgMir.mirrorVertically();
            shotgunImgMir.mirrorVertically();
        }
    }    

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
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
    }   

    public void move()
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

        Door door = (Door) getOneIntersectingObject(Door.class);
        if((door!=null && !door.getComplete()) || getOneIntersectingObject(Walls.class)!=null) setLocation(getX()-dx,getY()-dy);
        else{
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

    public String getCurrentGun()
    {
        String x = listOfGuns.get(0);
        return x;
    }

    public boolean hasGun(String gunName){
        for(String gun: listOfGuns){
            if(gunName.contains(gun)) return true;
        }    
        return false;
    }    

    public void changeGun()
    {
        if(Greenfoot.isKeyDown("e"))
        {
            String x = listOfGuns.get(0);
            listOfGuns.remove(0);
            listOfGuns.add(x);
            if(gun!=null) getWorld().removeObject(gun);

            if(listOfGuns.get(0)=="Pistol"){
                if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(0, -1, 15);
                gun = new Pistol(itemInfo,this,50,2,100,100,100,15);
                if(getWorld()!=null) getWorld().addObject(gun,getX()+18, getY()+16);
                reloadBar = new ResourceBarManager(10, 10, 800, 600, pistolBarImg, (GameWorld) getWorld());
            }    
            else if(listOfGuns.get(0)=="Shotgun"){
                if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(0, 8+items.get("Shotgun Bullet")*8, 8);
                gun = new Shotgun(itemInfo,this,100,4,50,50,100,8);
                if(getWorld()!=null) getWorld().addObject(gun,getX()+16, getY()+15);
                reloadBar = new ResourceBarManager(10, 10, 800, 600, shotgunBarImg, (GameWorld) getWorld());
            }    
            else{
                if(itemInfo!=null && itemInfo.getWorld()!=null) itemInfo.updateGun(0, 30+items.get("Rifle Bullet")*30, 30);
                gun = new Rifle(itemInfo,this,200,5,50,50,50,30);
                if(getWorld()!=null) getWorld().addObject(gun,getX()+20, getY()+13);
                reloadBar = new ResourceBarManager(10, 10, 800, 600, rifleBarImg, (GameWorld) getWorld());
            }    
        }
    }

    public void newGun(String gun)
    {
        if(gun.contains("Shotgun")){
            listOfGuns.add("Shotgun");
        }
        else{
            listOfGuns.add("Rifle");
        }
    }

    public void loseOneHeart()
    {
        hearts--;
        healthBar.reduceHealth(1,(GameWorld) getWorld());
        if(hearts == 0){
            GameWorld world = (GameWorld) getWorld();
            world.gameOver(false); 
        }   
    }

    public void addOneHeart()
    {
        hearts++;
        healthBar.refillHealth(1, (GameWorld) getWorld());
    }

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

    public void animateMovementDown()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (downMvt.length);
            setImage(downMvt[imageNumber]);
        }
        gun.getImage().setTransparency(255);
    }

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

    public boolean canReload(Weapon w){
        if(w instanceof Shotgun){
            if(items.get("Shotgun Bullet")>0 && w.getAmmo()<8){
                items.put("Shotgun Bullet",items.get("Shotgun Bullet")-1);
                itemInfo.updateAmmo(8+items.get("Shotgun Bullet")*8, 8);
                reloadBar.refillAmmo((GameWorld) getWorld());
                return true;
            }    
            else return false;
        }   
        else if(w instanceof Rifle){
            if(items.get("Rifle Bullet")>0 && w.getAmmo()<30){
                items.put("Rifle Bullet",items.get("Rifle Bullet")-1);
                itemInfo.updateAmmo(30+items.get("Rifle Bullet")*30, 30);
                reloadBar.refillAmmo((GameWorld) getWorld());
                return true;
            }    
            else return false;
        }
        else if(w.getAmmo()<15){
            itemInfo.updateAmmo(-1, 15);
            reloadBar.refillAmmo((GameWorld) getWorld());
            return true;
        }  
        return false;
    }    

    /**
     * saveData - save data from player into text file
     */
    public void saveData() {
        // save data into a text file (haven't tested yet!)
        try {
            FileWriter fw = new FileWriter(txtFile);
            fw.write(curLevel + "\n");
            fw.write(maxLevel + "\n");
            fw.write(money + "\n");
            fw.write(hearts + "\n");
            fw.write(speed + "\n");
            fw.write(listOfGuns.size() + "\n");
            for(String gun: listOfGuns) fw.write(gun + "\n");
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
    private void parseData() {
        Scanner s = null;
        try {
            s = new Scanner(txtFile);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }

        for (int i = 0; i < 6; i++) {
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
                break;
                case 3:
                hearts = val;
                break;
                case 4:
                speed = val;
                break;
                case 5:
                for(int j=0;j<val;j++){
                    listOfGuns.add(s.nextLine());
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
     * getMoney - get the amount of money
     * 
     * @param amount      the amount of money to add/remove
     */
    public void setMoney(int amount){
        money+=amount;
    }

    public int getItemNumber(String name){
        if(items.containsKey(name)) return items.get(name);
        return 0;
    }    

    public void changeItemNumber(String name, int amount){
        items.put(name,items.get(name)+amount);
    }    

    public void speedBoost(){
        speed++;
    }    

    public int getCurLevel(){
        return curLevel;
    }    

    public void changeCurLevel(int amount){
        curLevel=amount;
    }    

    public int getMaxLevel(){
        return maxLevel;
    }  

    public void incrementMaxLevel(){
        maxLevel++;
    }    

    public int getHearts(){
        return hearts;
    }
    
    public void reduceAmmo(){
        reloadBar.reduceAmmo((GameWorld) getWorld());
    }    
}
