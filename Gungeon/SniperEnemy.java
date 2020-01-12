import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class RifleEnemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SniperEnemy extends Enemy
{
    // Need to use array list of images later
    private static GreenfootImage SniperEnemyImage;
    private static int imageX;
    private static int imageY;
    public SniperEnemy(int health, int bulletWidth)
    {
        //SniperEnemyImage.scale(imageX, imageY);
        //setImage(SniperEnemyImage);        
        //healthPoints = health;
        //this.bulletWidth = bulletWidth;        
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
        //getWorld().addObject(new SniperBullet(player, 1), getX(), getY());   
        
    }
    
}
