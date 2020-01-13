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
    public Pistol(int damage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magazines, int magSize)
    {
        super(damage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magazines, magSize);
        setImage(image);
    }
    protected Ammunition createBullet()
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        return new PistolBullet(mouseX, mouseY, bulletDamage,bulletSpeed);
    }
}
