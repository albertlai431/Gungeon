import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rifle Class - This class creates a shotgun with set characteristics and a rifle bullet in the position of the weapon.
 * 
 * @author Aristos Theocharoulas 
 * @version Jan 2020
 */
public class Rifle extends Weapon
{
    private GreenfootImage gun = new GreenfootImage("Rifle.png");
    public Rifle(ItemInfo itemInfo, Player player,int damage, int bulletSpeed, long fireRate, long bulletReadyTime, long reloadTime, int magSize, int ammoInMag, boolean reloadTimer)
    {
        super(itemInfo,player,damage, bulletSpeed, fireRate, bulletReadyTime, reloadTime, magSize, ammoInMag, reloadTimer);
        gun.scale(gun.getWidth()*150/100,gun.getHeight()*150/100);
        setImage(gun);
    }
    /**
     * createBullet - Creates a rifle bullet that shoots towards the mouse
     */
    protected void createBullet(int xcoord, int ycoord)
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        getWorld().addObject(new RifleBullet(mouseX, mouseY,bulletDamage,bulletSpeed, false),xcoord,ycoord);
    }
}
