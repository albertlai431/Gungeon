import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Weapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Weapon extends Actor
{
    protected int bulletSpeed;
    protected int fireRate;
    protected int reloadTime;
    protected int damage;
    private long currTime;
    private int magazines;
    private int ammoInMag;
    private int magSize;
    private boolean reloading;
    
    public Weapon(int damage, int bulletSpeed, int fireRate, int reloadTime, int magazines, int magSize){
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.currTime = 0;
        this.magazines = magazines; 
        this.ammoInMag = magSize;
        this.magSize = magSize;
    }
    public Weapon(int damage, int bulletSpeed, int reloadTime, int magazines, int magSize){
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.fireRate = 0;
        this.reloadTime = reloadTime;
        this.currTime = 0;
        this.magazines = magazines; 
        this.ammoInMag = magSize;
        this.magSize = magSize;
    }
    abstract protected Ammunition createBullet();
    /**
     * Act - do whatever the Weapon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.getMouseInfo() != null)
        {
            turnTowards(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
        }
        if(checkToShoot()&&!isReloading())
        {
            shoot();
        }
    }    
    public boolean isReloading()
    {
        if(this.currTime>0)
        {
            
            long now = System.currentTimeMillis();
    
            if(now>=this.currTime)
            {
                this.currTime = 0;
                if(this.magazines>0)
                {
                    this.magazines--;
                    this.ammoInMag = magSize;
                    
                }
                return false;
            }
            else{
                return true;
            }
        }
        
        return false;
    }
    public void updateAmmo(int ammo)
    {
        GameWorld world = (GameWorld)getWorld();
        //update ammo method
    }
    public void startReload()
    {
        long now = System.currentTimeMillis();
        //reloading = true;
        this.currTime = now + reloadTime;
        
    }
    protected boolean checkToShoot()
    {
        
        if(Greenfoot.mouseClicked(getWorld()))
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            
            if(mi!=null&&mi.getButton()==1)
            {
                return true;
            }
        }
        return false;
    }
    
    public void shoot()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int targetX =  mouse.getX();
        int targetY =  mouse.getY();
        if(mouse.getButton() == 1&&fireRate==0&&magazines>0)
        {
            
            Ammunition bullet = createBullet();
            //bullet.setTargetLocation(targetX, targetY);
            getWorld().addObject(bullet, this.getX(), this.getY());
            this.ammoInMag--;
            if(ammoInMag==0)
            {
                startReload();
            }
            
            //System.out.println("Bullet");
        }
    }
}
