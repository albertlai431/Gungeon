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
    protected int actMod = 60;
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
        //intersecting vs offset??
        Player player = (Player) getOneIntersectingObject​(Player.class);
        if(player!=null){
            player.loseOneHeart();
            //if(changeLoc) setNewLocation(player);
            hit=true;
        }    

        ArrayList <Enemy> enemiesArrayList = (ArrayList) getIntersectingObjects​(Enemy.class);
        for(Enemy enemy: enemiesArrayList){
            enemy.getDamaged(damage);
            //if(changeLoc) setNewLocation(enemy);
            hit=true;
        }
        return hit;
    }

    /*
    protected void setNewLocation(Actor a){
        if(a!=null){
            GameWorld world = (GameWorld) getWorld();
            int newFirstInd, newSecondInd;
            while(true){
                newFirstInd = firstInd + (Greenfoot.getRandomNumber(3)-1);
                newSecondInd = secondInd + (Greenfoot.getRandomNumber(3)-1);
                if(!world.isWall(newFirstInd, newSecondInd)) break;
            }
            
            a.setLocation(world.convert(newFirstInd),world.convert(newSecondInd));
        }    
    }   
    */
}
