import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pistol here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pistol extends Weapon
{
    private GreenfootImage gun = new GreenfootImage("Pistol.png");
    public Pistol(ItemInfo itemInfo, Player player, int damage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magSize, int ammoInMag)
    {
        super(itemInfo, player, damage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magSize, ammoInMag);
        gun.scale(gun.getWidth()*150/100,gun.getHeight()*150/100);
        setImage(gun);
    }
    protected void createBullet(int xcoord, int ycoord)
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        getWorld().addObject(new PistolBullet(mouseX, mouseY,bulletDamage,bulletSpeed),xcoord,ycoord);
    }
}
