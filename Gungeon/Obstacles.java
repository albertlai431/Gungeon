import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Obstacles here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public abstract class Obstacles extends Actor
{
    private static GreenfootImage img; 
    protected int actCount = 0;
    protected int actMod = 30;
    protected int damage;
    private int firstInd;
    private int secondInd;

    public Obstacles(int damage){
        this.damage = damage;
    }    
    
    public Obstacles(int damage, int firstInd, int secondInd){
        this(damage);
        this.firstInd = firstInd;
        this.secondInd = secondInd;
    }   

    protected boolean damage(boolean changeLoc){
        boolean hit = false;
        Player player = (Player) getOneObjectAtOffset​(0, 0, Player.class);
        if(player!=null){
            player.loseOneHeart();
            if(changeLoc) setNewLocation(player);
            hit=true;
        }    

        ArrayList <Enemy> enemiesArrayList = (ArrayList) getObjectsAtOffset​(0, 0, Enemy.class);
        for(Enemy enemy: enemiesArrayList){
            //enemy.getDamaged();
            if(changeLoc) setNewLocation(enemy);
            hit=true;
        }
        return hit;
    }

    protected void setNewLocation(Actor a){
        if(a!=null){
            GameWorld world = (GameWorld) getWorld();
            int newFirstInd, newSecondInd;
            while(true){
                newFirstInd = firstInd + (Greenfoot.getRandomNumber(3)-1)*2;
                newSecondInd = secondInd + (Greenfoot.getRandomNumber(3)-1)*2;
                if(!world.isWall(newFirstInd, newSecondInd)) break;
            }
            
            a.setLocation(world.convert(newFirstInd),world.convert(newSecondInd));
        }    
    }    
}
