import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StoreItem here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class StoreItem extends Actor
{
    private String item;
    private int value;
    private GreenfootImage [] img;
    
    /**
     * Act - do whatever the StoreItem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public StoreItem(String item){
        this.item = item;
    }    
    
    /**
     * Act - do whatever the StoreItem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this)){
            
        }    
    }    
}
