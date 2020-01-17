import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * The beginner enemies, bullet enemies are average with normal firerate and a normal shooting pattern
 * 
 * @author Henry Ma
 * @version January 16, 2020
 */
public class BulletEnemy extends Enemy
{
    //Initialize variables and objects
    //Array of greenfoot images to hold the different animations
    private static GreenfootImage[] rightMvt = new GreenfootImage[6];
    private static GreenfootImage[] leftMvt = new GreenfootImage[6];
    private static GreenfootImage[] upMvt = new GreenfootImage[9];
    private static GreenfootImage[] downMvt = new GreenfootImage[9];
    //Variable to help determine if images have been created or not
    private static boolean createdImages = false;
    //Variable to help indicate when to change images for animation
    private int frameRate = 8;
    //Tracts the current image number
    private int imageNumber = 0;
    /**
     * Constructor for bullet enemies that sets the initial values
     */
    public BulletEnemy()
    {       
        scoreBoost = 5;
        moneyBoost = 100;
        healthPoints = 300;     
        fireRate = 50;
        movementCounter = 30;
        movementMod = 30;
    }
    /**
     * Greenfoot method, called when this object is added to the world
     */
    protected void addedToWorld(World world) 
    {
        createImages();
    }
    /**
     * Act - Move towards the player and check for line of sight before attempting to shoot
     */
    public void act() 
    {
        moveTowardsPlayer();  
    }    
    /**
     * Creates bullets of a given type depending on the enemy that calls it
     * attacks differ from enemy to enemy
     */
    public void attack()
    {
        getWorld().addObject(new PistolBullet(player.getX(), player.getY(), 1, 4, true), getX(), getY());   
    }  
    /**
     * Changes the image for animation of up movement
     */
    public void animateMovementUp()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (upMvt.length);
            setImage(upMvt[imageNumber]);
        }        
    }
    /**
     * Changes the image for animation of down movement
     */ 
    public void animateMovementDown()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (downMvt.length);
            setImage(downMvt[imageNumber]);
        }        
    }
    /**
     * Changes the image for animation of right movement
     */ 
    public void animateMovementRight()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (rightMvt.length);
            setImage(rightMvt[imageNumber]);
        }
    }
    /**
     * Changes the image for animation of left movement
     */      
    public void animateMovementLeft()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (leftMvt.length);
            setImage(leftMvt[imageNumber]);
        }
    }
    /**
     * Create images for the class to use during animations
     */    
    public static void createImages()
    {
        if(!createdImages)
        {
            //Do if not done before
            createdImages = true;
            for(int i=0; i<rightMvt.length; i++)
            {
                rightMvt[i] = new GreenfootImage("bulletEnemyLeft"+i+".png");
                leftMvt[i] = new GreenfootImage("bulletEnemyLeft"+i+".png");
                rightMvt[i].scale(rightMvt[i].getWidth()*170/100, rightMvt[i].getHeight()*170/100);
                leftMvt[i].scale(leftMvt[i].getWidth()*170/100, leftMvt[i].getHeight()*170/100);
            }
            for(int i=0; i<leftMvt.length; i++)
            {
                rightMvt[i].mirrorHorizontally();
            }
            for(int i=0; i<upMvt.length; i++)
            {
                upMvt[i] = new GreenfootImage("bulletEnemyUp"+i+".png");
                downMvt[i] = new GreenfootImage("bulletEnemyDown"+i+".png");
                upMvt[i].scale(upMvt[i].getWidth()*170/100, upMvt[i].getHeight()*170/100);
                downMvt[i].scale(downMvt[i].getWidth()*170/100, downMvt[i].getHeight()*170/100);
            }
        }
    }
}
