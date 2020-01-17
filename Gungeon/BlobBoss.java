import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Boss of the game, has multiple different attacks, stronger attacks happen
 * less, stronger attacks become harder to dodge
 * 
 * @author Henry Ma
 * @version January 16, 2020
 */
public class BlobBoss extends Actor
{
    //Initialize variables and objects
    //Health of boss
    private int healthPoints;
    //Animation for boss
    private static GreenfootImage[] animation = new GreenfootImage[4];
    //Keeps track if images for animation were created
    private static boolean createdImages = false;
    //Frames/acts before next image switch
    private int frameRate = 8;
    //Keeps track of image currently on
    private int imageNumber = 0;
    //Acts before firing
    private int fireRate = 20;
    //Acts before big attack
    private int bigAttackRate = 60;
    //Counter for number of acts gone by
    private long animationCount = 0;
    //Used to find player
    private ArrayList<Player> foundPlayers;
    private Actor player;
    /**
     * Constructor for blob boss, set and initialize values for boss
     */
    public BlobBoss()
    {
        healthPoints = 5000;
        setImage(animation[0]);
    }
    /**
     * Greenfoot method, called when object is added to world
     */
    protected void addedToWorld(World world) 
    {
        createImages();
        foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
        player = foundPlayers.get(0); 
    }
    /**
     * Act - Blob stays still while charging up for attacks/attacking
     */
    public void act() 
    {
        animationCount ++;
        animate();
        
        if(fireRate == 100)
        {
            if(bigAttackRate == 500)
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
                fireRate = 0;
            }            
        }
        else
        {
            fireRate++;
        }
        
    }    
    /**
     * Calls on an attack method determined by the parameters given
     * 
     * @param attackNumber      The attack to be called
     */
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
    /**
     * Shoots a spray of bullets(like shotgun enemy)
     */
    private void smallAttackOne()
    {
        for(int i = 0; i < 7; i++)
        {
            getWorld().addObject(new ShotgunBullet(player.getX(), player.getY() - 90 + (30 * i), 1, 6, true), getX(), getY());
        }
    }
    /**
     * Shoots a spray of rifle bullets
     */
    private void smallAttackTwo()
    {
        for(int i = 0; i < 3; i++)
        {
            getWorld().addObject(new RifleBullet(player.getX(), player.getY() - 30 + (30 * i), 1, 10, true), getX(), getY());
        }
    }
    /**
     * Shoots 100 bullets with an average of 25 targeted towards the player
     */
    private void bigAttackOne()
    {
        for(int i = 0; i < 100; i++)
        {
            int attack = Greenfoot.getRandomNumber(100);
            if(attack < 25)
            {
                getWorld().addObject(new PistolBullet(player.getX(), player.getY(), 1, 10, true), getX(), getY());
            }
            else
            {
                int attackX = Greenfoot.getRandomNumber(480);
                int attackY = Greenfoot.getRandomNumber(320);
                getWorld().addObject(new PistolBullet(attackX, attackY, 1, 10, true), getX(), getY());
            }
        }
    }
    /**
     * Animate by switching images
     */
    private void animate()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)%(animation.length);
            setImage(animation[imageNumber]);
        } 
    }
    /**
     * Create the images for the animation of this object
     */
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
    /**
     * Inflict damage dependent on the parameters given, also checks for death
     * 
     * @param damage    Amount of damage to deal
     */
    public void getDamaged(int damage)
    {
        healthPoints -= damage;
        if(healthPoints <= 0)
        {           
            die();
        }
    }
    /**
     * Remove this object from the world
     */
    private void die()
    {
        getWorld().removeObject(this);
    }
}
