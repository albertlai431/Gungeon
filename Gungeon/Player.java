import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
* Write a description of class Player here.
*a
 * @author (your name)
 * @version (a version number or a date)
*/
public class Player extends Actor implements AnimationInterface
{
    GreenfootImage[] rightMvt = new GreenfootImage[5];
    GreenfootImage[] leftMvt = new GreenfootImage[5];
    GreenfootImage[] upMvt = new GreenfootImage[8];
    GreenfootImage[] downMvt = new GreenfootImage[8];
    private int scaleNumber = 100;
    private int animationCount = 0;
    private int frameRate = 7;
    private int imageNumber = 0;
    public Player()
    {
        //Sets images to given GreenfootImage arrays
        for(int i=0; i<rightMvt.length; i++)
        {
            rightMvt[i] = new GreenfootImage("pilotRight"+i+".png");
            leftMvt[i] = new GreenfootImage("pilotRight"+i+".png");
        }
        for(int i=0; i<leftMvt.length; i++)
        {
            leftMvt[i].mirrorHorizontally();
        }
        for(int i=0; i<upMvt.length; i++)
        {
            upMvt[i] = new GreenfootImage("pilotUp"+i+".png");
            downMvt[i] = new GreenfootImage("pilotDown"+i+".png");
        }
        
        for(int i=0; i<rightMvt.length;i++)
        {
            rightMvt[i].scale(rightMvt[i].getWidth()*scaleNumber/100,rightMvt[i].getHeight()*scaleNumber/100);
            leftMvt[i].scale(leftMvt[i].getWidth()*scaleNumber/100,leftMvt[i].getHeight()*scaleNumber/100);
        }
        for(int i=0; i<upMvt.length;i++)
        {
            upMvt[i].scale(upMvt[i].getWidth()*scaleNumber/100,upMvt[i].getHeight()*scaleNumber/100);
            downMvt[i].scale(downMvt[i].getWidth()*scaleNumber/100,downMvt[i].getHeight()*scaleNumber/100);
        }
        
        setImage(rightMvt[0]);
    }
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        animationCount++;  
        move();
    }   
    
    public void move()
    {
        if(Greenfoot.isKeyDown("a"))//runs if "a" is pressed and the player is past the starting location
            {
                //animateMovementLeft();
                if(Greenfoot.isKeyDown("s")) setLocation(getX()-2, getY()+2);
                else if(Greenfoot.isKeyDown("w")) setLocation(getX()-2, getY()-2);
                else setLocation(getX()-2, getY());
            }
            else if(Greenfoot.isKeyDown("d"))//runs if "d" is pressed
            {
                //animateMovementRight();
                if(Greenfoot.isKeyDown("s")) setLocation(getX()+2, getY()+2);
                else if(Greenfoot.isKeyDown("w")) setLocation(getX()+2, getY()-2);
                else setLocation(getX()+2, getY());
            }
            else if(Greenfoot.isKeyDown("w"))//runs if "d" is pressed
            {
                //animateMovementUp();
                if(Greenfoot.isKeyDown("a")) setLocation(getX()-2, getY()-2);
                else if(Greenfoot.isKeyDown("d")) setLocation(getX()+2, getY()-2);
                else setLocation(getX(), getY()-2);
            }
            else if(Greenfoot.isKeyDown("s"))//runs if "d" is pressed
            {
                //animateMovementDown();
                if(Greenfoot.isKeyDown("a")) setLocation(getX()-2, getY()+2);
                else if(Greenfoot.isKeyDown("d")) setLocation(getX()+2, getY()+2);
                else setLocation(getX(), getY()+2);
            }
    }
    
    public void animateMovementUp()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (upMvt.length);
            setImage(upMvt[imageNumber]);
        }
    }
    
    public void animateMovementDown()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (downMvt.length);
            setImage(downMvt[imageNumber]);
        }
    }
    
    public void animateMovementRight()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (rightMvt.length);
            setImage(rightMvt[imageNumber]);
        }
    }
    
    public void animateMovementLeft()
    {
        if(animationCount%frameRate == 0)
        {
            imageNumber = (imageNumber + 1)% (leftMvt.length);
            setImage(leftMvt[imageNumber]);
        }
    }
}
