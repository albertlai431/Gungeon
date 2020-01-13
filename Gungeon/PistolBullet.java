import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
 
/**
* Bullet- a subclass of Ammunition where bullets are made. Bullets follow a
* straight line path from the angle they are shot at and will do a constant
* damage to the HP of the Building/Vehicle that it hits.
*
 * @author Star Xie, Albert Lai, Clarence 
* @version January 2020
*/
public class PistolBullet extends Ammunition
{
    private GreenfootImage bullet = new GreenfootImage("pistolBullet.png");
    /**
     * Constructor - creates a Bullet, sets the team, targets an Actor, and specifies damage dealt
     *
     * @param red               specifies the team (true for red, false for blue)
     * @param actor             the specific object that is being targetted
     * @param damage            specifies the damage taken for each hit
     */
    public PistolBullet(int x, int y, int damage, int speed)
    {
        super(x, y, damage, speed);
        setImage(bullet);
        //hit.setVolume(50);
        //shoot.setVolume(75);
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
