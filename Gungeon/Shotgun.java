import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Shotgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shotgun extends Weapon
{
    private String image = "Shotgun.png";
    public Shotgun(int damage, int bulletSpeed, int fireRate, int reloadTime, int magazines, int magSize)
    {
        super(damage, bulletSpeed, fireRate, reloadTime, magazines, magSize);
        setImage(image);
    }
    protected Ammunition createBullet()
    {
        return new ShotgunBullet(/*damage,bulletSpeed*/);
    }
}
