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
    
    public void act() 
    {
        moveTowardsPlayer();       
    }    
 
    
    public void attack()
    {
        //getWorld().addObject(new ShotgunBullet(player.getX(), player.getY() + 60, 1, 12), getX(), getY());   
        //getWorld().addObject(new ShotgunBullet(player.getX(), player.getY() + 30, 1, 12), getX(), getY());  
        //getWorld().addObject(new ShotgunBullet(player.getX(), player.getY(), 1, 12), getX(), getY());  
        //getWorld().addObject(new ShotgunBullet(player.getX(), player.getY() - 30, 1, 12), getX(), getY());  
        //getWorld().addObject(new ShotgunBullet(player.getX(), player.getY() - 60, 1, 12), getX(), getY());  
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
    
        public void animate(String direction)
    {
        int totalFrames = 0;
        String imageName = new String(direction);
        if(direction.equals("Left") || direction.equals("Right"))
        {
            totalFrames = 3;
            imageName = new String("Left");
        }
        else
        {
            totalFrames = 8;
        }
        for(int i = 0; i < totalFrames; i++)
        {
            animationImage = new GreenfootImage("shotgunEnemy" + imageName + i + ".png");
            animationImage.scale(16, 28);
            animationImage.mirrorHorizontally();
            setImage(animationImage);
            move(1);
        }
    }
}
