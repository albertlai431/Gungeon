import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * PistolBullet- a subclass of Ammunition where bullets are made. Bullets follow a
 * straight line path from the angle they are shot at and will do a constant
 * damage to the HP of the player/enemy that it hits.
 *
 * @author Star Xie, Clarence Chau
 * @version January 2020
 */
public class PistolBullet extends Ammunition
{
    //declare greenfoot images and sounds
    private GreenfootImage bullet = new GreenfootImage("pistolBullet.png");
    /**
     * Constructor - creates a PistolBullet, sets the team, targets specified coordinates, and specifies damage dealt
     *
     * @param xCoord            the targetted X coordinate
     * @param yCoord            the targetted Y coordinate
     * @param damage            specifies the damage taken for each hit
     * @param speed             speed the bullet travels at
     * @param isEnemy           true if the bullet is firing from an enemy, otherwise false
     */
    public PistolBullet(int x, int y, int damage, int speed, boolean isEnemy)
    {
        super(x, y, damage, speed, isEnemy);
        setImage(bullet);
        //hit.setVolume(50);
        //shoot.setVolume(75);
    } 

    /**
     * reloadAmmo - Sets the ammunition to it's full amount
     */
    public void reloadAmmo()
    {
        ammo = 25;
    }

    /**
     * checkAmmo - Returns the specific ammo number. To be implemented in subclasses
     */
    public int checkAmmo()
    {
        return ammo;
    }
}
