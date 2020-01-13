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
    public Pistol(Player player, int damage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magSize)
    {
        super(player, damage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magSize);
        setImage(image);
    }
    protected Ammunition createBullet()
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        return new PistolBullet(mouseX, mouseY, bulletDamage,bulletSpeed);
    }
}
