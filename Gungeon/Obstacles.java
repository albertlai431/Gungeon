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
    //damages are arbitrary for now
    protected int damage;

    protected Player player;
    protected ArrayList <Enemy> enemiesArrayList;

    public Obstacles(int damage){
        this.damage = damage;
    }    

    protected void damage(){
        player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null){
            //player.loseHeart();
        }    

        enemiesArrayList = (ArrayList) getIntersectingObjects(Enemy.class);
        for(Enemy enemy: enemiesArrayList){
            //enemy.getDamaged();
        }    
    }

    protected void setNewLocation(Actor a, int firstInd, int secondInd){
        if(a!=null){
            GameWorld world = (GameWorld) getWorld();
            int newFirstInd, newSecondInd;
            while(true){
                newFirstInd = firstInd + Greenfoot.getRandomNumber(3) - 1;
                newSecondInd = secondInd + Greenfoot.getRandomNumber(3) - 1;
                if(!world.isWall(newFirstInd, newSecondInd)) break;
            }
            
            a.setLocation(world.convert(firstInd),world.convert(secondInd));
        }    
    }    
}
