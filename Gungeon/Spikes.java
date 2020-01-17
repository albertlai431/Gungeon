import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Spikes is an obstacle that inflicts damage on the player/enemies that touch it
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Spikes extends Obstacles
{
    /**
     * Constructor for Spikes
     * 
     * @param firstInd              the first index of the array 
     * @param secondInd             the second index of the array
     */
    public Spikes(int firstInd, int secondInd){
        super(100,firstInd,secondInd);
    } 

    /**
     * Act - do whatever the Spikes wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        actCount++;
        if(actCount == actMod){
            actCount = 0;
            damage();
        }  
    } 
}
