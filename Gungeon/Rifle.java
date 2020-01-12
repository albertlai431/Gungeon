import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Rifle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rifle extends Weapon
{
    private String image = "Rifle.png";
    public Rifle(int damage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magazines, int magSize)
    {
        super(damage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magazines, magSize);
        setImage(image);
    }
    protected Ammunition createBullet()
    {
        return new RifleBullet(bulletDamage, bulletSpeed);
    }
}
