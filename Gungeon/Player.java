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
 *a
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player extends Actor implements AnimationInterface
{
    GreenfootImage[] rightMvt = new GreenfootImage[5];
    GreenfootImage[] leftMvt = new GreenfootImage[5];
    GreenfootImage[] upMvt = new GreenfootImage[8];
    GreenfootImage[] downMvt = new GreenfootImage[8];

    private int curLevel;
    private int maxLevel;
    private int money;
    private File txtFile;
    private HashMap <String,Integer> items = new HashMap <String,Integer>();
    private Weapon gun;

    private int scaleNumber = 100;
    private int animationCount = 0;
    private int frameRate = 7;
    private int imageNumber = 0;
    private int hearts = 0;
    private int weapon = 0; // 0 = pistol, 1 = shotgun, 2 = rifle
    private int totalAmmoShotgun = 0;
    private int totalAmmoRifle = 0;
    private int currentHealth = 6;
    private int XCoord;
    private int YCoord;
    private boolean hasShotgun = false;
    private boolean hasRifle = false;
    private Stack<Integer> health = new Stack<Integer>();
    private ArrayList<String> listOfGuns = new ArrayList<String>();
    private ResourceBarManager healthBar = new ResourceBarManager(3, 20, 20, 20, new GreenfootImage("halfHeart.png"), new GreenfootImage("fullHeart.png"), (GameWorld) getWorld());
    public Player()
    {
        for(int i = 0; i < 6; i++)
        {
            health.push(1);
        }
        //Sets images to given GreenfootImage arrays
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

        setImage(rightMvt[0]);
    }

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        animationCount++;  
        move();
        changeGun();
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
                dx = -2; dy = 2;
            }
            else if(Greenfoot.isKeyDown("w")){
                dx = -2; dy = -2;
            }
            else dx = -2;
        }
        else if(Greenfoot.isKeyDown("d"))//runs if "d" is pressed
        {
            //animateMovementRight();
            if(Greenfoot.isKeyDown("s")){
                dx = 2; dy = 2;
            }    
            else if(Greenfoot.isKeyDown("w")){
                dx = 2; dy = -2;
            }    
            else dx = 2;
        }
        else if(Greenfoot.isKeyDown("w"))//runs if "d" is pressed
        {
            //animateMovementUp();
            if(Greenfoot.isKeyDown("a")) {
                dx = -2; dy = -2;
            }
            else if(Greenfoot.isKeyDown("d")) {
                dx = 2; dy = -2;
            }
            else dy = -2;
        }
        else if(Greenfoot.isKeyDown("s"))//runs if "d" is pressed
        {
            //animateMovementDown();
            if(Greenfoot.isKeyDown("a")) {
                dx = -2; dy = 2;
            }
            else if(Greenfoot.isKeyDown("d")) {
                dx = 2; dy = 2;
            }
            else dy = 2;
        }
        
        setLocation(getX()+dx,getY()+dy);
        Door door = (Door) getOneObjectAtOffset(0, 0, Door.class);
        if((door!=null && !door.getComplete()) || getOneObjectAtOffset(0, 0, Walls.class)!=null) setLocation(getX()-dx,getY()-dy);
    } 

    public int currentAmmoShotgun()
    {
        return totalAmmoShotgun;
    }

    public int currentAmmoRifle()
    {
        return totalAmmoRifle;
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
        if(Greenfoot.isKeyDown("e") && (listOfGuns.size() > 1))
        {
            String x = listOfGuns.get(0);
            listOfGuns.remove(0);
            listOfGuns.add(x);
        }
    }

    public void newGun(int n)
    {
        if((n == 1) && (hasShotgun = false)){
            listOfGuns.add("shotgun");
            hasShotgun = true;
        }
        else if ((n == 2) && (hasRifle = false)){
            listOfGuns.add("rifle");
            hasRifle = true;
        }
    }

    public void loseOneHeart()
    {
        health.pop();
        healthBar.reduceHealth(1,(GameWorld) getWorld());
    }

    public void addOneHeart()
    {
        health.push(1);
        healthBar.refillHealth(1, (GameWorld) getWorld());
    }

    public void animateMovementUp()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (upMvt.length);
            setImage(upMvt[imageNumber]);
        }
    }

    public void animateMovementDown()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (downMvt.length);
            setImage(downMvt[imageNumber]);
        }
    }

    public void animateMovementRight()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (rightMvt.length);
            setImage(rightMvt[imageNumber]);
        }
    }

    public void animateMovementLeft()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (leftMvt.length);
            setImage(leftMvt[imageNumber]);
        }
    }

    /**
     * saveData - save data from player into text file
     */
    public void saveData() {
        // get data from Player

        // save data into a text file (haven't tested yet!)
        try {
            FileWriter fw = new FileWriter(txtFile);
            fw.write(curLevel + "\n");
            fw.write(maxLevel + "\n");
            fw.write(money + "\n");
            fw.write(hearts + "\n");
            fw.write(listOfGuns.size() + "\n");
            for(String gun: listOfGuns) fw.write(gun + "\n");
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

        for (int i = 0; i < 4; i++) {
            int val = Integer.parseInt(s.nextLine());
            switch (i) {
                case 0:
                curLevel = val;
                case 1:
                maxLevel = val;
                case 2:
                money = val;
                case 3:
                hearts = val;
                case 4:
                for(int j=0;j<val;j++){
                    listOfGuns.add(s.nextLine());
                }    
            }
        }

        for (int i = 0; i < 4; i++) {
            items.put(s.nextLine(),Integer.parseInt(s.nextLine()));
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
        return items.get(name);
    }    

    public void changeItemNumber(String name, int amount){
        items.put(name,items.get(name)+amount);
    }    
}
