import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Arrows is an obstacle that inflicts damage on any player or enemy that touches it. 
 * It includes an animation of the arrow hitting its target. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Arrows extends Obstacles
{
    //instance variables
    private boolean isMoving = false;
    private int x;
    private int xStart;
    private int xEnd;
    private int y;
    private GreenfootSound arrowSound = new GreenfootSound("arrow.mp3");

    /**
     * Constructor for Arrows
     */
    public Arrows(){
        super(100);
        getImage().setTransparency(100);
        arrowSound.setVolume(100);
    }    

    /**
     * addedToWorld - sets coordinates
     * 
     * @param w             Current world of the actor
     */
    public void addedToWorld(World w){
        x = getX();
        xStart = x+50;
        xEnd = x-50;
        y = getY();
    }    

    /**
     * Act - do whatever the Arrows wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(isMoving){
            //animation
            setLocation(getX()-3,y);
            if(getX()<=xEnd){
                isMoving = false;
                getImage().setTransparency(100);
                setLocation(x,y);
            }    
        }    
        else{
            //checks for enemy/player
            actCount++;
            if(actCount == actMod){
                actCount = 0;
                boolean hit = super.damage();
                if(hit){
                    if(arrowSound.isPlaying()) arrowSound.stop();
                    arrowSound.play();
                    isMoving = true;
                    setLocation(xStart,y);
                    getImage().setTransparency(255);
                }
            }    
        }
    }    
}
