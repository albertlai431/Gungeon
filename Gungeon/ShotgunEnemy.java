import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class RocketEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShotgunEnemy extends Enemy
{
    // Need to use array list of images later
    private static GreenfootImage ShotgunEnemyImage;
    private static int imageX;
    private static int imageY;
    public ShotgunEnemy(int health, int bulletWidth)
    {
        //ShotgunEnemyImage.scale(imageX, imageY);
        //setImage(ShotgunEnemyImage);        
        healthPoints = health;    
        this.bulletWidth = bulletWidth;
    }
    
    public void addedToWorld()
    {
        foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
        player = foundPlayers.get(0);
    }
    
    public void act() 
    {
        moveTowardsPlayer();       
    }    
 
    
    public void attack()
    {
        //getWorld().addObject(new ShotgunBullet(player, 1), getX(), getY());   
        
    }
}
