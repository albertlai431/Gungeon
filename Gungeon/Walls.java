import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;

/**
 * Walls are obstacles that the player cannot pass through. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Walls extends Obstacles
{
    private static final String path = "Walls" + File.separator;
    private static final GreenfootImage [] imgs = {new GreenfootImage(path+"wallUp.png"),new GreenfootImage(path+"wallDown.png"),
                                                   new GreenfootImage(path+"wallRight.png"),new GreenfootImage(path+"wallLeft.png")};
                                                   
    /**
     * Constructor for Walls           the second index of the array
     */                                               
    public Walls(){
        super(0);
    }  

    /**
     * addedToWorld - sets the image
     * 
     * @param w             the current world 
     */
    public void addedToWorld(World w){
        GameWorld world = (GameWorld) w;
        if(world.width-getX()<25) setImage(imgs[2]);
        else if(getX()<25) setImage(imgs[3]);
        else if(world.height-getY()<25) setImage(imgs[1]);
    }    
}
