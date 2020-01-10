import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
 
/**
* Bullet- a subclass of Ammunition where bullets are made. Bullets follow a
* straight line path from the angle they are shot at and will do a constant
* damage to the HP of the Building/Vehicle that it hits.
*
 * @author Star Xie, Albert Lai
* @version November 2019
*/
public class bulletBullet extends Ammunition
{
    //declare greenfoot sounds
    protected GreenfootSound hit = new GreenfootSound("BulletHit.wav");
    protected GreenfootSound shoot = new GreenfootSound("BulletShot.wav");
 
    /**
     * Constructor - creates a Bullet, sets the team, targets an Actor, and specifies damage dealt
     *
     * @param red               specifies the team (true for red, false for blue)
     * @param actor             the specific object that is being targetted
     * @param damage            specifies the damage taken for each hit
     */
    public bulletBullet(Actor actor, int damage)
    {
        super(actor,damage);
        setImage(PistolBullet);
        hit.setVolume(50);
        shoot.setVolume(75);
    }
   
    /**
     * addedToWorld - Turns towards a specified vehicle or building when
     * the Bullet object is added to the world
     */
    public void addedToWorld (World w){
        //Play sound
        shoot.play();
        turnTowards(target.getX(), target.getY());
    }
 
    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //Checks and deals damage to intersecting classes
        checkAndHit();
        //Makes sure that the World is not returning a null value
        if(getWorld()!=null){
            //Removes object at world's edge
            if(isAtEdge()) getWorld().removeObject(this);
        }   
    }   
 
    /**
     * checkAndHit - checks if the Bullet has hit a Vehicle or Building
     * and deals damage to their HP if collision is detected.
     */
    public void checkAndHit()
    {
        //Gets a building that is intersecting a bullet
        Enemy enemy = (Enemy)getOneObjectAtOffset(0,0,Enemy.class);
        //Gets a vehicle that is intersecting a bullet
        Walls walls = (Walls)getOneObjectAtOffset(0,0,Walls.class);
       
        //Checks to see if the bullet is on the opposite team as the objects
        if(walls != null){
            //Deal damage and play sound
            hit.play();
            //Removes the Bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        }
        if(enemy != null){
            //Deal damage and play sound
            hit.play();
            //Decreases the damage of the vehicle when hit
            enemy.getDamaged(damage);
            //Removes the bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        }   
    }
   
    public void reloadAmmo()
    {
        ammo = 25;
    }
   
    public int checkAmmo()
    {
        return ammo;
    }
}
