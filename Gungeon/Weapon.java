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
    private long fireRate;
    private long reloadTime;
    protected int bulletDamage;
    private long startTime;
    protected int ammoInMag;
    private int magSize;
    private boolean reloading;
    private boolean firing;
    private long nextFiredTime = 0;
    private long bulletReadyTime;
    private long lastFiredTime=0;
    protected int mouseX;
    protected int mouseY;
    protected Player player;
    protected ItemInfo itemInfo;
    /**
     * Constructor - Initializes the variables for the weapon
     * 
     * @param   itemInfo         the current ItemInfo object
     * @param   player           the current Player object
     * @param   bulletDamage     the damage that the bullet will do to enemies
     * @param   bulletSpeed      the speed that the bullet will have
     * @param   fireRate         the time between each bullet when user is holding down the left click in milliseconds
     * @param   bulletReadyTime  the time bet each bullet when user is clicking the left click in milliseconds
     * @param   reloadTime       the times it takes for the weapon to reload
     * @param   magSize          rhe number of magazines the user has
     * 
     */
    public Weapon(ItemInfo itemInfo, Player player, int bulletDamage, int bulletSpeed, long fireRate, long bulletReadyTime , long reloadTime, int magSize, int ammoInMag){
        this.player = player;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
        this.fireRate = fireRate;
        this.bulletReadyTime = bulletReadyTime;
        this.reloadTime = reloadTime;
        this.startTime = 0;
        //this.magazines = magazines; 
        this.ammoInMag = ammoInMag;
        this.magSize = magSize;
        this.itemInfo = itemInfo;
    }

    /**
     * createBullet - abstract class that choses the correct bullet for each type of weapon
     */
    abstract protected Ammunition createBullet();

    /**
     * Act - Checks for button presses and mouse clicks
     */
    public void act() 
    {

        MouseInfo mi = Greenfoot.getMouseInfo();
        if (mi != null)
        {
            turnTowards(mi.getX(), mi.getY());
        }
        else if(firing)
        {
            stopFiring();
        }
        //reload the weapon if the user presses r
        if(Greenfoot.isKeyDown("r")&&ammoInMag<magSize)
        {
            //if(ammoInMag<=0)itemInfo.updateAmmo(0, ammoInMag);
            //else{itemInfo.updateAmmo(0, ammoInMag);}
            
            startReload();
        }
        //check for mouse press
        if(Greenfoot.mousePressed(null))
        {
            //if the user presses the left mouse button
            if(mi.getButton() ==1)
            {
                if(!firing){
                    firing = true;
                    nextFiredTime = Math.max(System.currentTimeMillis(), lastFiredTime+bulletReadyTime);
                }
            }
        }
        else if(Greenfoot.mouseClicked(null))
        {
            //if the user releases the click
            if(mi!=null&&mi.getButton()==1)
            {
                //stop firing bullets
                stopFiring();
            }
        }
        if(firing)
        {
            //get the current time
            long currentTime = System.currentTimeMillis();
            //if the current time is more than the next set fired time 
            if(currentTime>=nextFiredTime)
            {
                //if not reloading and has ammo
                if(!isReloading()&&ammoInMag>0)
                {
                    shoot();
                }
                nextFiredTime = lastFiredTime+fireRate;
            }

        }
    }    

    /**
     * stopFiring - Stops shooting bullets from the weapon
     */
    private void stopFiring()
    {
        firing = false;
        nextFiredTime = 0;
    }

    /**
     * isReloading - Checks if the weapon is currently reloading
     * 
     * @return  boolean     true if the weapon is currently reloading, false if it is not
     */
    private boolean isReloading()
    {
        if(reloading)
        {

            long now = System.currentTimeMillis();

            if(now>=this.startTime)
            {
                this.startTime = 0;
                reloading = false;
                int newMag = player.canReload(this);
                if(newMag>0)
                {
                    
                    this.ammoInMag = newMag;

                }
                return false;
            }
            else{
                return true;
            }

        }
        return false;
    }

    /**
     * startReload - Starts the reloading process
     */
    private void startReload()
    {
        if(!isReloading()){
            long now = System.currentTimeMillis();
            reloading = true;
            this.startTime = now + reloadTime;
        }
    }

    /**
     * shoot - Creates the correct bullet by calling the createBullet() method and checks if there are enough bullets left
     */
    private void shoot()
    {
        Ammunition bullet = createBullet();
        lastFiredTime = System.currentTimeMillis();
        getWorld().addObject(bullet, this.getX(), this.getY());
        this.ammoInMag--;
        if(ammoInMag<10) player.reduceAmmo();
        itemInfo.updateAmmo();
    }

    public int getAmmo()
    {
        return ammoInMag;
    }

    public long getFireRate()
    {
        return fireRate;
    }

    public int getMagSize()
    {
        return magSize;
    }

    public long getReloadTime()
    {
        return reloadTime;
    }

    public long getBulletReadyTime()
    {
        return bulletReadyTime;
    }

    public int getBulletDamage()
    {
        return bulletDamage;
    }

    public int getBulletSpeed()
    {
        return bulletSpeed;
    }
}