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
    private String name = "";

    /**
     * Constructor for Walls at the borders        
     * 
     * @param firstInd             the first index of the array
     * @param secondInd            the second index of the array
     */                                               
    public Walls(int firstInd, int secondInd){
        super(0, firstInd, secondInd);
    }  

    /**
     * Constructor for Walls in the center of the screen          
     * 
     * @param String                name of the file
     */                                               
    public Walls(String name){
        super(0);
        this.name = name;
        setImage(name+".png");
    }  

    /**
     * addedToWorld - sets the image
     * 
     * @param w             the current world 
     */
    public void addedToWorld(World w){
        if(name.equals("")){
            GameWorld world = (GameWorld) w;
            if(world.width-getX()<25) setImage(imgs[2]);
            else if(getX()<25) setImage(imgs[3]);
            else if(world.height-getY()<25) setImage(imgs[1]);
        }
    }    
}
