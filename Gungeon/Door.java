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
    private static final GreenfootImage [] img = {new GreenfootImage("lockedDoor.png"),new GreenfootImage("passage.jpg")};
    
    public Door(int curLevel, boolean isComplete){
        super(0);
        this.curLevel = curLevel;
        this.isComplete = isComplete;
        if(isComplete) setImage(img[1]);
        else setImage(img[0]);
    }   
    
    public void act(){
        if((getOneObjectAtOffset(0, -16, Player.class)!=null || getOneObjectAtOffset(0, 16, Player.class)!=null) /*&& isComplete*/){
            GameWorld world = (GameWorld) getWorld();
            if(curLevel == 0){
                world.closeWorld();
                Greenfoot.setWorld(new TitleScreen());
            }    
            else if(curLevel == 6) world.gameOver(true);
            else world.switchWorld(curLevel);
        }    
    }    
    
    public void completeLevel(){
        setImage(img[1]);
        isComplete = true;
    }    
    
    private int getLevel(){
        return curLevel;
    }    
}
