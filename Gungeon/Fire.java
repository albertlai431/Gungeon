import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Fire is an obstacle that inflicts damage on the player/enemies that touch it
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Fire extends Obstacles
{
    private GreenfootSound fireSound = new GreenfootSound("lavaSizzle.mp3");
    
    /**
     * Constructor for Fire
     * 
     * @param firstInd              the first index of the array 
     * @param secondInd             the second index of the array
     */
    public Fire(int firstInd, int secondInd){
        super(200,firstInd,secondInd);
        fireSound.setVolume(55);
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
            if(damage()){
                if(fireSound.isPlaying()) fireSound.stop();
                fireSound.play();
            }    
        }  
    }    
}
