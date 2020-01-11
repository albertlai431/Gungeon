import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pistol here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pistol extends Weapon
{
    private String image = "Pistol.png";
    public Pistol(int damage, int bulletSpeed, int fireRate, int reloadTime, int magazines, int magSize)
    {
        super(damage, bulletSpeed, fireRate, reloadTime, magazines, magSize);
        setImage(image);
    }
    protected Ammunition createBullet()
    {
        return new PistolBullet()/*bulletBullet(damage,bulletSpeed)*/;
    }
}
