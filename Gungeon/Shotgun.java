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
    public Shotgun(int bulletDamage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magazines, int magSize)
    {
        super(bulletDamage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magazines, magSize);
        setImage(image);
    }
    protected Ammunition createBullet()
    {
        return new ShotgunBullet(null,bulletDamage/*,bulletSpeed*/);
    }
}
