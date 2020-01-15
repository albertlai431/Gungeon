import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrows here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Arrows extends Obstacles
{
    private boolean isMoving = false;
    private int x;
    private int xStart;
    private int xEnd;
    private int y;

    public Arrows(){
        super(100);
        getImage().setTransparency(100);
    }    

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
            setLocation(getX()-3,y);
            if(getX()<=xEnd){
                isMoving = false;
                getImage().setTransparency(100);
                setLocation(x,y);
            }    
        }    
        else{
            actCount++;
            if(actCount == actMod){
                actCount = 0;
                damage();
            }    
        }
    }    

    public void damage(){
        boolean hit = super.damage(false);
        if(hit){
            isMoving = true;
            setLocation(xStart,y);
            getImage().setTransparency(255);
        }
    }    
}
