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
    public Shotgun(ItemInfo itemInfo, Player player,int bulletDamage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magSize, int ammoInMag)
    {
        super(itemInfo,player, bulletDamage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magSize, ammoInMag);
        gun.scale(gun.getWidth()*150/100,gun.getHeight()*150/100);
        setImage(gun);
    }
    protected Ammunition createBullet()
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        return new ShotgunBullet(mouseX, mouseY,bulletDamage,bulletSpeed, false);
    }
}
