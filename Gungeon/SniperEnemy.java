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
    private static int imageX;
    private static int imageY;
    public SniperEnemy(int health, int bulletWidth)
    {
        animationImage = new GreenfootImage("bulletEnemyDown0.png");
        //animationImage.scale(imageX, imageY);
        setImage(animationImage);        
        healthPoints = health;     
        this.bulletWidth = bulletWidth;
        fireRate = 60;    
    }

    public void act() 
    {
        moveTowardsPlayer();       
    }    
 
    
    public void attack()
    {
        //getWorld().addObject(new SniperBullet(player.getX(), player.getY(), 1, 30), getX(), getY());          
    }
    
    public void animate(String direction)
    {
        int totalFrames = 0;
        String imageName = new String(direction);
        if(direction.equals("Left") || direction.equals("Right"))
        {
            totalFrames = 4;
            imageName = new String("Left");
        }
        else
        {
            totalFrames = 7;
        }
        for(int i = 0; i < totalFrames; i++)
        {
            animationImage = new GreenfootImage("sniperEnemy" + imageName + i + ".png");
            animationImage.scale(16, 28);
            animationImage.mirrorHorizontally();
            setImage(animationImage);
            move(1);
        }
    }
    
    public void animateMovementUp()
    {
    }
    
    public void animateMovementDown()
    {
    }
    
    public void animateMovementRight()
    {
    }
    
    public void animateMovementLeft()
    {
    }
}
