import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Door is an obstacle that provides passages to other rooms. When all the enemies in the room are 
 * defeated, the door will open. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Door extends Obstacles
{
    private int curLevel;
    private boolean isComplete = false;
    private static final GreenfootImage [] doorImg = {new GreenfootImage("lockedDoor.png"),new GreenfootImage("passage.jpg")};
    
    /**
     * Constructor for Door
     * 
     * @param curLevel          the level the door leads to
     * @boolean isComplete      specifies whether the room is complate
     */
    public Door(int curLevel, boolean isComplete){
        super(0);
        this.curLevel = curLevel;
        this.isComplete = isComplete;
        if(this.isComplete) setImage(doorImg[1]);
        else setImage(doorImg[0]);
    }   
    
    /**
     * Act - do whatever the Door wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        //checks if player has reached the door
        if((getOneObjectAtOffset(0, -25, Player.class)!=null || getOneObjectAtOffset(0, 25, Player.class)!=null) && isComplete){
            GameWorld world = (GameWorld) getWorld();
            if(curLevel == 0){
                world.closeWorld(curLevel);
                Greenfoot.setWorld(new TitleScreen());
            }    
            else if(curLevel == 6) world.gameOver(true);
            else world.switchWorld(curLevel);
        }    
    }    
    
    /**
     * completeLevel - called when the player completes the level
     */
    public void completeLevel(){
        setImage(doorImg[1]);
        isComplete = true;
    }    
    
    /**
     * getLevel - returns the level of the door
     * 
     * @return int              level of the door
     */
    private int getLevel(){
        return curLevel;
    }    
    
    /**
     * getLevel - returns the level of the door
     * 
     * @return boolean          true if the level is complete and false if not
     */
    public boolean getComplete(){
        return isComplete;
    }    
}
