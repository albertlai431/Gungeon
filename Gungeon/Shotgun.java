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

    protected void createBullet(int xcoord, int ycoord)
    {
        this.mouseX = Greenfoot.getMouseInfo().getX();
        this.mouseY = Greenfoot.getMouseInfo().getY();
        double x2 = xcoord + (mouseX-xcoord)/10;
        double y2 = ycoord + (mouseY-ycoord)/10;
        double dis = Math.sqrt((xcoord-x2)*(xcoord-x2) + (ycoord-y2)*(ycoord-y2))*Math.tan(Math.PI/6); 
        if(Math.abs(ycoord - mouseY)<=9){
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2), (int)Math.round(y2+3),bulletDamage,bulletSpeed),xcoord,ycoord);
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2), (int)Math.round(y2),bulletDamage,bulletSpeed),xcoord,ycoord);
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2),  (int)Math.round(y2-3),bulletDamage,bulletSpeed),xcoord,ycoord);
        }    
        else if(Math.abs(xcoord - mouseX)<=9){
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2+3), (int)Math.round(y2),bulletDamage,bulletSpeed),xcoord,ycoord);
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2), (int)Math.round(y2),bulletDamage,bulletSpeed),xcoord,ycoord);
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2-3),  (int)Math.round(y2),bulletDamage,bulletSpeed),xcoord,ycoord);
        }
        else{
            double slopeperp = -(xcoord-x2)/(ycoord-y2);
            double dx = Math.sqrt(dis*dis/(1+slopeperp*slopeperp));
            double dy = slopeperp*dx;

            getWorld().addObject(new ShotgunBullet((int)Math.round(x2+dx), (int)Math.round(y2+dy),bulletDamage,bulletSpeed),xcoord,ycoord);
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2), (int)Math.round(y2),bulletDamage,bulletSpeed),xcoord,ycoord);
            getWorld().addObject(new ShotgunBullet((int)Math.round(x2-dx),  (int)Math.round(y2-dy),bulletDamage,bulletSpeed),xcoord,ycoord);
        }  
    }
}
