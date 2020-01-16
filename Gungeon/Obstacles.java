import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Obstacles is the abstract superclass of all obstacles in the world. It contains methods to damage enemies. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public abstract class Obstacles extends Actor
{
    protected int actCount = 0;
    protected int actMod = 30;
    protected int damage;
    private int firstInd;
    private int secondInd;

    /**
     * Constructor for obstacles 
     * 
     * @param damage            damage inflicted by obstacle
     */
    public Obstacles(int damage){
        this.damage = damage;
    }    
    
    /**
     * Constructor for obstacles
     * 
     * @param damage            damage inflicted by obstacle
     * @param firstInd          first index of array
     * @param secondInd         second index of array
     */
    public Obstacles(int damage, int firstInd, int secondInd){
        this(damage);
        this.firstInd = firstInd;
        this.secondInd = secondInd;
    }   

    /**
     * damage - damages enemies and players
     * 
     * @param boolean           whether or not to change the location of the player/enemies
     * @return boolean          whether or not the obstacle has damaged a player/enemy
     */
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
