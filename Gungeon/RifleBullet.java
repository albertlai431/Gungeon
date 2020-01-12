import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
 
/**
* Bullet- a subclass of Ammunition where bullets are made. Bullets follow a
* straight line path from the angle they are shot at and will do a constant
* damage to the HP of the Building/Vehicle that it hits.
*
* @author Star Xie, Albert Lai, Clarence
* @version November 2019
*/
public class RifleBullet extends Ammunition
{
    //declare greenfoot sounds
    private GreenfootImage bullet = new GreenfootImage("rifleBullet.png");
 
    /**
     * Constructor - creates a Bullet, sets the team, targets an Actor, and specifies damage dealt
     *
     * @param red               specifies the team (true for red, false for blue)
     * @param actor             the specific object that is being targetted
     * @param damage            specifies the damage taken for each hit
     */
    public RifleBullet()
    {
        super(x, y,damage);
        setImage(bullet);
        hit.setVolume(50);
        shoot.setVolume(75);
    }

    private void reloadAmmo()
    {
        ammo = 50;
    }
   
    private int checkAmmo()
    {
        return ammo;
    }
}
