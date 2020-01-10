import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.File;

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
    private static GreenfootImage storeMenuImg = new GreenfootImage(600,425);
    private Label menuTitle = new Label("Store", 27, true);
    private Button closeStore = new Button("Close", 18);
    private Label gunsLabel = new Label("Guns", 20, true);
    private Label ammoLabel = new Label("Ammo", 20, true);
    private Label powerupsLabel = new Label("Power Ups", 20, true);
    private Button equipButton = new Button("Equip", 18);
    private Button purchaseButton = new Button("Purchase", 18);

    private StoreItem lastItem = null;
    private String lastItemName = null;
    private int money;
    private int lastItemCost;
    private static final String[] itemNames = {"Pistol Gun","Rifle Gun","Shotgun Gun","Rifle Bullet","Shotgun Bullet","Half-Heart Refill","Speed Boost"};
    private static final int[] xCoords = {250,350,450,250,350,250,350};
    private static final int[] yCoords = {225,225,225,350,350,475,475};
    private static final int[] costs = {0x3f3f3f3f,500,500,100,100,500,750}; //to be completed
    private static final int numItems = itemNames.length;
    private static boolean createdImages = false;
    private static GreenfootImage [] itemImages = new GreenfootImage [numItems];

    public StoreMenu(){
        storeMenuImg.setColor(Color.LIGHT_GRAY);
        storeMenuImg.fill();
        setImage(storeMenuImg);

        //money = 
    }    
    
    public static void createImages(){
        if(!createdImages){
            createdImages = true;
            for(int i=0;i<numItems;i++){
                itemImages[i] = new GreenfootImage("Store"+File.separator+itemNames[i]+".png");
                itemImages[i].scale(40,(int)((double)itemImages[i].getHeight()*40/itemImages[i].getWidth()));
            }    
        }    
    }

    public void addedToWorld(World w){
        PauseWorld world = (PauseWorld) getWorld();
        world.addObject(menuTitle,world.width/2,world.height/2-180);
        world.addObject(closeStore, 725, 500);
        world.addObject(gunsLabel, 225, 170);
        world.addObject(ammoLabel, 225, 295);
        world.addObject(powerupsLabel, 240, 420);
        world.addObject(equipButton, 700, 200);
        world.addObject(purchaseButton, 700, 225);
        for(int i=0;i<numItems;i++){
            world.addObject(new StoreItem(itemImages[i], itemNames[i],this,costs[i]),xCoords[i],yCoords[i]);
        }    
    }    

    public void getLastPressed(StoreItem item,String itemName, int itemCost){
        if(lastItem!=null){
            lastItem.changeImage();
        }    

        lastItem = item;
        lastItemName = itemName;
        lastItemCost = itemCost;

        //make buttons transparent accordingly
        if(money<itemCost){//or if already bought
            purchaseButton.getImage().setTransparency(0);
        }    
        else purchaseButton.getImage().setTransparency(255);

        //if already equipped
    }    

    private void purchase(){
        //add to player inventory
        money-=lastItemCost;
        if(lastItemName.contains("Gun") || money<lastItemCost) purchaseButton.getImage().setTransparency(0);
    }    

    private void equip(){
        //add to current player weapon
        if(lastItemName.contains("Gun")) equipButton.getImage().setTransparency(0);
        else{
            //decrease inventory by one
            //equip item
        }
    }

    protected void checkButtonClicks(){
        PauseWorld world = (PauseWorld) getWorld();
        if(Greenfoot.mouseClicked(closeStore)){
            world.closeWorld();
        }
        else if(Greenfoot.mouseClicked(equipButton) && equipButton.getImage().getTransparency()!=0){
            equip();
        }
        else if(Greenfoot.mouseClicked(purchaseButton) && purchaseButton.getImage().getTransparency()!=0){
            purchase();
        }
    }    

    public void closeMenu(){
        PauseWorld world = (PauseWorld) getWorld();
        world.removeObject(menuTitle);
        world.removeObject(closeStore);
        world.removeObject(gunsLabel);
        world.removeObject(ammoLabel);
        world.removeObject(powerupsLabel);
        world.removeObject(equipButton);
        world.removeObject(purchaseButton);
        ArrayList <StoreItem> storeItems = (ArrayList) world.getObjects(StoreItem.class);
        for(StoreItem item: storeItems){
            item.remove();
        }    
        world.removeObject(this);
    }   
}
