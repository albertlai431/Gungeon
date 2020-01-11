import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spikes here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Spikes extends Obstacles
{
    private int firstInd;
    private int secondInd;
    
    public Spikes(int firstInd, int secondInd){
        super(100);
        this.firstInd = firstInd;
        this.secondInd = secondInd;
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
    
    protected void damage(){
        super.damage();
        setNewLocation(player, firstInd, secondInd);
        for(Enemy enemy: enemiesArrayList) setNewLocation(enemy, firstInd, secondInd);
    }
    
}
