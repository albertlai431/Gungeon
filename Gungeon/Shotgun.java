import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Shotgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shotgun extends Weapon
{
    private GreenfootImage gun = new GreenfootImage("Shotgun.png");
    public Shotgun(Player player,int bulletDamage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magSize)
    {
        super(player, bulletDamage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magSize);
        gun.scale(gun.getWidth()*150/100,gun.getHeight()*150/100);
        setImage(gun);
    }
    protected Ammunition createBullet()
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        return new ShotgunBullet(mouseX, mouseY,bulletDamage,bulletSpeed);
    }
}
