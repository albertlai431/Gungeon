import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * StoreMenu is the store that exists in the game. The user can open the Store anytime
 * when playing the game and choose to buy items/powerups or equip themselves with 
 * an item. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class StoreMenu extends Menu
{
    private static GreenfootImage storeMenuImg = new GreenfootImage(600,400);
    private Label menuTitle = new Label("Store", 25, true);
    private Button closeStore = new Button("Close", 20);
    private String[] items = {"Rifle","Shotgun","Half-Heart Refill","RifleBullet","ShotgunBullet","Speed Boost"};
    
    public StoreMenu(){
        storeMenuImg.setColor(Color.LIGHT_GRAY);
        storeMenuImg.fill();
        setImage(storeMenuImg);
    }    
    
    public void addedToWorld(World w){
        GameWorld world = (GameWorld) w;
        world.addObject(menuTitle,world.width/2,world.height/2-180);
        world.addObject(closeStore, 725, 500);
        for(String item: items){
            
        }    
    }    
    
    /**
     * Act - do whatever the StoreMenu wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        
    }

    private boolean purchase(){
        return true;
    }    

    private boolean equip(){
        return true;
    }
    
    protected void checkButtonClicks(){
        GameWorld world = (GameWorld) getWorld();
        if(Greenfoot.mouseClicked(closeStore)){
            world.play();
        }
    }    
    
    public void closeMenu(){
        GameWorld world = (GameWorld) getWorld();
        world.removeObject(menuTitle);
        world.removeObject(closeStore);
        world.removeObject(this);
    }   
}
