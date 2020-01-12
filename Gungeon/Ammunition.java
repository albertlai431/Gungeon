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
    protected Actor target;
    protected int ammo = 0;
    /**
     * Constructor - calls the superclass and initializes values
     *
     * @param actor             the specific object that is being targeted
     * @param damage            specifies the damage taken for each hit
     */
    public Ammunition (Actor actor, int damage)
    {
        //Instantiates variables
        target = actor;
        this.damage=damage;
    }   
 
    /**
     * checkAndHit - checks if the Ammunition has hit a Vehicle or Building. To be implemented in subclasses
     */
    protected abstract void checkAndHit();
   
    protected abstract void reloadAmmo();
   
 
}
