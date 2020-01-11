import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Door here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Door extends Obstacles
{
    private int curLevel;
    private boolean isComplete = false;
    //private static final GreenfootImage img;
    
    public Door(int curLevel, boolean isComplete){
        super(0);
        this.curLevel = curLevel;
        this.isComplete = isComplete;
    }   
    
    public void completeLevel(){
        //change image
        isComplete = true;
    }    
    
    private int getLevel(){
        return curLevel;
    }    
}
