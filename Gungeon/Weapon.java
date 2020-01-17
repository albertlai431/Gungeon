import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Weapon Class - This class controls all of the weapons and their actions in the game. 
 * It looks for mouse clicks to shoot and button presses to reload. There are 3 types of weapons.
 * The Shotgun, the Rifle and the Pistol. Each weapon can have a different fire rate, 
 * bullet speed and damage, ammunition type, reload time and more. It also has an optional timer for when reloading.
 * 
 * @author Aristos Theocharoulas 
 * @version Jan 2020
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
    private Timer t;
    private SimpleTimer timer;
    protected boolean reloadTimer;
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
     * @param   magSize          the number of magazines the user has
     * @param   ammoInMag        the current ammo in the magazine
     * @param   reloadTimer      true to enable the timer when reloading, false to not
     * 
     */
    public Weapon(ItemInfo itemInfo, Player player, int bulletDamage, int bulletSpeed, long fireRate, long bulletReadyTime , long reloadTime, int magSize, int ammoInMag, boolean reloadTimer){
        this.player = player;
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
        this.fireRate = fireRate;
        this.bulletReadyTime = bulletReadyTime;
        this.reloadTime = reloadTime;
        this.startTime = 0;
        this.reloadTimer = reloadTimer;
        this.ammoInMag = ammoInMag;
        this.magSize = magSize;
        this.itemInfo = itemInfo;
    }

    /**
     * createBullet - abstract class that choses the correct bullet for each type of weapon
     */
    abstract protected void createBullet(int xcoord, int ycoord);

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
        else if(firing||(isReloading()&&Greenfoot.isKeyDown("r")))
        {
            stopFiring();
        }
        //reload the weapon if the user presses r
        if(Greenfoot.isKeyDown("r")&&ammoInMag<magSize)
        {
            
            startReload();
        }
        if(isReloading()&&mi.getButton()==1){
            stopFiring();
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
            if(mi!=null&&mi.getButton()==1)//||(t.getTime()>0&&mi.getButton()==1))
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
                if((!isReloading()&&ammoInMag>0))//||(isReloading()&&t.getTime()<=0))
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
     * startReload - Starts the reloading process and the reload timer
     */
    private void startReload()
    {
        if(!isReloading()){
            long now = System.currentTimeMillis();
            reloading = true;
            if(this.reloadTimer){
                this.t = new Timer(reloadTime);
                getWorld().addObject(t, this.getX(), this.getY());
            }
            this.startTime = now + reloadTime;
        }
    }

    /**
     * shoot - Creates the correct bullet by calling the createBullet() method and checks if there are enough bullets left
     */
    private void shoot()
    {
        createBullet(this.getX(), this.getY());
        lastFiredTime = System.currentTimeMillis();
        this.ammoInMag--;
        if(ammoInMag<10) player.reduceAmmo();
        itemInfo.updateAmmo();
    }
    /**
     * getAmmo - gets the current ammo in the magazine
     * 
     * @return  int     the current ammo in the magazine
     */
    public int getAmmo()
    {
        return ammoInMag;
    }
    /**
     * getAmmo - gets the fire rate of the current weapon
     * 
     * @return  long     the fire rate of the weapon
     */

    public long getFireRate()
    {
        return fireRate;
    }
    /**
     * getAmmo - gets the magazine size of the weapon
     * 
     * @return  int     the magazine size of the weapon
     */

    public int getMagSize()
    {
        return magSize;
    }
    /**
     * getAmmo - gets the reload time of the weapon
     * 
     * @return  long     the reload time of the weapon
     */

    public long getReloadTime()
    {
        return reloadTime;
    }
    /**
     * getAmmo - gets the time that the bullet is ready to shoot
     * 
     * @return  long     the time that the bullet is ready to shoot
     */

    public long getBulletReadyTime()
    {
        return bulletReadyTime;
    }

    /**
     * getAmmo - gets the current ammo in the magazine
     * 
     * @return  int     the current ammo in the magazine
     */
    public int getBulletDamage()
    {
        return bulletDamage;
    }

    /**
     * getBulletSpeed - gets the current ammo in the magazine
     * 
     * @return  int     the current ammo in the magazine
     */
    public int getBulletSpeed()
    {
        return bulletSpeed;
    }
}
