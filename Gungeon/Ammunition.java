import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
* Abstract class of all dynamic and moving objects used to deal damage.
*
 * @author Star Xie
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
    protected GreenfootSound hit = new GreenfootSound("BulletHit.wav");
    protected GreenfootSound shoot = new GreenfootSound("BulletShot.wav");
    /**
     * Constructor - calls the superclass and initializes values
     *
     * @param damage            specifies the damage taken for each hit
     */
    public Ammunition (int xCoord, int yCoord, int damage, int speed)
    {
        x = xCoord;
        y = yCoord;
        this.damage=damage;
        this.speed=speed;
    }   
 
    /**
     * checkAndHit - checks if the Ammunition has hit a Vehicle or Building. To be implemented in subclasses
     */
    protected abstract int checkAmmo();
   
    protected abstract void reloadAmmo();
    
    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //Checks and deals damage to intersecting classes
        checkAndHit();
        move(speed);
        //Makes sure that the World is not returning a null value
        if(getWorld()!=null){
            //Removes object at world's edge
            if(isAtEdge()) getWorld().removeObject(this);
        }   
    } 
    
    protected void addedToWorld (World w){
        //Play sound
        shoot.play();
        turnTowards(x, y);
    }
    
    /**
     * checkAndHit - checks if the Bullet has hit a Vehicle or Building
     * and deals damage to their HP if collision is detected.
     */
    protected void checkAndHit()
    {
        //Gets a building that is intersecting a bullet
        Enemy enemy = (Enemy)getOneObjectAtOffset(0,0,Enemy.class);
        //Gets a vehicle that is intersecting a bullet
        Walls walls = (Walls)getOneObjectAtOffset(0,0,Walls.class);
       
        //Checks to see if the bullet is on the opposite team as the objects
        if(walls != null){
            //Deal damage and play sound
            hit.play();
            //Removes the Bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        }
        if(enemy != null){
            //Deal damage and play sound
            hit.play();
            //Decreases the damage of the vehicle when hit
            enemy.getHit(damage);
            //Removes the bullet object from the world
            if(getWorld()!=null) getWorld().removeObject(this);
        }   
    }
   
 
}
