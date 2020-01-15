import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class BlobBoss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlobBoss extends Actor
{
    private int healthPoints;
    private static GreenfootImage[] animation = new GreenfootImage[4];
    private static boolean createdImages = false;
    private int frameRate = 8;
    private int imageNumber = 0;
    private int fireRate = 20;
    private int bigAttackRate = 60;
    private long animationCount = 0;
    private ArrayList<Player> foundPlayers;
    private Actor player;
    public BlobBoss()
    {
        healthPoints = 3000;
        setImage(animation[0]);
    }
    protected void addedToWorld(World world) 
    {
        createImages();
    }
    
    public void act() 
    {
        animationCount ++;
        animate();
        
        if(fireRate == 30)
        {
            if(bigAttackRate == 60)
            {
                bigAttackOne();
                bigAttackRate = 0;
            }
            else
            {
                bigAttackRate++;
                foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
                player = foundPlayers.get(0);   
                int attackNumber = Greenfoot.getRandomNumber(2);
                pickAttack(attackNumber);
            }            
        }
        else
        {
            fireRate++;
        }
        
    }    
    
    private void pickAttack(int attackNumber)
    {
        if(attackNumber == 0)
        {
            smallAttackOne();
        }
        if(attackNumber == 1)
        {
            smallAttackTwo();
        }
    }
    
    private void smallAttackOne()
    {
        for(int i = 0; i < 7; i++)
        {
            //getWorld().addObject(new ShotgunBullet(player.getX(), player.getY() - 90 + (30 * i), 1, 6), getX(), getY());
        }
    }
    
    private void smallAttackTwo()
    {
        for(int i = 0; i < 3; i++)
        {
            //getWorld().addObject(new SniperBullet(player.getX(), playergetY() - 30 + (30 * i), 1, 10), getX(), getY());
        }
    }
    
    private void bigAttackOne()
    {
        for(int i = 0; i < 100; i++)
        {
            int attack = Greenfoot.getRandomNumber(100);
            if(attack < 25)
            {
                //getWorld().addObject(new PistolBullet(player.getX(), playergetY(), 1, 10), getX(), getY());
            }
            else
            {
                int attackX = Greenfoot.getRandomNumber(480);
                int attackY = Greenfoot.getRandomNumber(320);
                //getWorld().addObject(new PistolBullet(attackX, attackY, 1, 10), getX(), getY());
            }
        }
    }
    
    private void animate()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)%(animation.length);
            setImage(animation[imageNumber]);
        } 
    }

    private void createImages()
    {
        if(!createdImages)
        {
            createdImages = true;
            for(int i=0; i<animation.length; i++)
            {
                animation[i] = new GreenfootImage("blobBoss"+i+".png");
            }
        }
    }
    
    public void getDamaged(int damage)
    {
        healthPoints -= damage;
        if(healthPoints <= 0)
        {           
            die();
        }
    }

    private void die()
    {
        getWorld().removeObject(this);
    }
    
    
}
