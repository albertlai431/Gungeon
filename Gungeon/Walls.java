import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;

/**
 * Write a description of class Walls here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Walls extends Obstacles
{
    private static final String path = "Walls" + File.separator;
    private static final GreenfootImage [] imgs = {new GreenfootImage(path+"wallUp.png"),new GreenfootImage(path+"wallDown.png"),
                                                   new GreenfootImage(path+"wallRight.png"),new GreenfootImage(path+"wallLeft.png")};

    public Walls(){
        super(0);
    }  

    public void addedToWorld(World w){
        GameWorld world = (GameWorld) w;
        if(world.width-getX()<25) setImage(imgs[2]);
        else if(getX()<25) setImage(imgs[3]);
        else if(world.height-getY()<25) setImage(imgs[1]);
    }    
}
