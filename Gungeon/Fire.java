import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fire here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Fire extends Obstacles
{
    private int firstInd;
    private int secondInd;
    private int newX;
    private int newY;
    
    public Fire(int firstInd, int secondInd){
        super(200);
        this.firstInd = firstInd;
        this.secondInd = secondInd;
    }   
    
    /**
     * Act - do whatever the Fire wants to do. This method is called whenever
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
