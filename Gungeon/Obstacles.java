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
    //Instance variables
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
     * @return boolean          whether or not the obstacle has damaged a player/enemy
     */
    protected boolean damage(){
        boolean hit = false;
        
        Player player = (Player) getOneObjectAtOffset​(0,-16,Player.class);
        if(player!=null){
            hit = player.loseOneHeart();
        }    
        

        ArrayList <Enemy> enemiesArrayList = (ArrayList) getObjectsAtOffset​(0,0,Enemy.class);
        for(Enemy enemy: enemiesArrayList){
            enemy.getDamaged(damage);
            hit=true;
        }
        return hit;
    }
}
