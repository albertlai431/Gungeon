import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract class of all dynamic and moving objects used to deal damage.
 *
 * @author Star Xie, Clarence Chau
 * @version November 2019
 */
public abstract class Ammunition extends Actor
{
    //Declare all instance variables
    protected int damage;
    protected int x;
    protected int y;
    protected int speed;
    protected int ammo = 0;
    protected boolean isEnemy;
    //protected GreenfootSound hit = new GreenfootSound("BulletHit.wav");
    //protected GreenfootSound shoot = new GreenfootSound("BulletShot.wav");
    /**
     * Constructor - calls the superclass and initializes values
     *
     * @param xCoord            the targetted X coordinate
     * @param yCoord            the targetted Y coordinate
     * @param damage            specifies the damage taken for each hit
     * @param speed             speed the bullet travels at
     * @param isEnemy           true if the bullet is firing from an enemy, otherwise false
     */
    public Ammunition (int xCoord, int yCoord, int damage, int speed, boolean isEnemy)
    {
        x = xCoord;
        y = yCoord;
        this.damage=damage;
        this.speed=speed;
        this.isEnemy = isEnemy;
    }   

    /**
     * checkAmmo - Returns the specific ammo number. To be implemented in subclasses
     */
    protected abstract int checkAmmo();

    /**
     * reloadAmmo - Sets the ammunition to it's full amount. To be implemented in subclasses
     */
    protected abstract void reloadAmmo();

    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //Checks and deals damage to intersecting classes
        checkAndHit();
        //Moves at specified speed
        move(speed);
        //Makes sure that the World is not returning a null value
        if(getWorld()!=null){
            //Removes object at world's edge
            if(isAtEdge()) getWorld().removeObject(this);
        }   
    } 

    /**
     * addedToWorld - Makes sure if the bullet object is added to the world before altering it's rotation
     */
    protected void addedToWorld (World w){
        //Play sound
        //shoot.play();
        turnTowards(x, y);
    }

    /**
     * checkAndHit - checks if the Bullet has hit a player, enemy, boss, or walls
     * and deals damage to their HP if collision is detected.
     */
    protected void checkAndHit()
    {
        //Gets intersecting classes with Ammunition object
        Enemy enemy = (Enemy)getOneObjectAtOffset(0,0,Enemy.class);
        BlobBoss boss = (BlobBoss)getOneObjectAtOffset(0,0,BlobBoss.class);
        Walls walls = (Walls)getOneObjectAtOffset(0,0,Walls.class);
        Player player = (Player)getOneObjectAtOffset(0,0,Player.class);

        //Checks if the bullet hits a wall
        if(walls != null){
            //Deal damage and play sound
            //hit.play();
            //Removes the Ammunition object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        }
        if(enemy != null && isEnemy == false){
            //Deal damage and play sound
            //hit.play();
            //Decreases the damage of the enemy when hit
            enemy.getDamaged(damage);
            //Removes the bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        } 
        if(boss != null && isEnemy == false){
            //Deal damage and play sound
            //hit.play();
            //Decreases the damage of the boss when hit
            boss.getDamaged(damage);
            //Removes the bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        } 
        if(player!= null && isEnemy == true)
        {
            //Deal damage and play sound
            player.loseOneHeart();
            //Removes the bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        }
    }

}
