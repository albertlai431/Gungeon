import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Beholster Boss of the game, has multiple different attacks, stronger attacks happen
 * less, stronger attacks become harder to dodge (second boss)
 * 
 * @author Henry Ma
 * @version January 17, 2020
 */
public class BeholsterBoss extends Boss
{
    /**
     * Constructor for beholster boss, set and initialize values for boss
     */
    public BeholsterBoss()
    {
        healthPoints = 10000;
    }

    /**
     * Greenfoot method, called when object is added to world
     */
    protected void addedToWorld(World world) 
    {
        createImages();
        foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
        player = foundPlayers.get(0); 
        setImage(animation[0]);
    }    

    /**
     * Act - Beholster stays still while charging up for attacks/attacking
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
                pickAttack(attackNumber, false);
                fireRate = 0;
            }            
        }
        else
        {
            fireRate++;
        }
    }

    /**
     * Shoots 100 bullets all targeted towards the player
     */
    private void bigAttackOne()
    {
        for(int i = 0; i < 100; i++)
        {
            getWorld().addObject(new PistolBullet(player.getX(), player.getY(), 1, 10, true), getX(), getY());
        }
    }

    /**
     * Shoots 150 bullets all targeted at random locations
     */
    private void bigAttackTwo()
    {
        for(int i = 0; i < 100; i++)
        {
            int x = Greenfoot.getRandomNumber(200);
            int y = Greenfoot.getRandomNumber(200);
            getWorld().addObject(new RifleBullet(x, y, 1, 12, true), getX(), getY());
        }
    }

    /**
     * Calls on an attack method determined by the parameters given
     * 
     * @param attackNumber      The attack to be called
     * @param bigAttack         True if big attack is being picked, false otherwise
     */
    private void pickAttack(int attackNumber, boolean bigAttack)
    {
        if(bigAttack == true)
        {        
            if(attackNumber == 0)
            {
                bigAttackOne();
            }
            else
            {
                bigAttackTwo();
            }
        }
        else if(attackNumber == 0)
        {
            smallAttackOne();
        }
        else
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
    public void createImages()
    {
        if(!createdImages)
        {
            createdImages = true;
            for(int i=0; i<animation.length; i++)
            {
                animation[i] = new GreenfootImage("newBoss"+i+".png");
            }
        }
    }
}
