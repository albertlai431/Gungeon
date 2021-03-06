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
    //Buttons, labels, images, and sounds
    private static GreenfootImage storeMenuImg = new GreenfootImage(600,425);
    private GreenfootImage moneyIcon = new GreenfootImage("Money.png");
    private Label menuTitle = new Label("Store", 27, true);
    private Label gunsLabel = new Label("Guns", 20, true);
    private Label ammoLabel = new Label("Ammo", 20, true);
    private Label powerupsLabel = new Label("Power Ups", 20, true);
    private Label quantityLabel = new Label(" ",20,true);
    private Label costLabel = new Label(" ",20,true);
    private Label moneyLabel = new Label(" ",20,true);
    private Button closeStore = new Button("Close", 18, 20, 20, 20,  100, 100, 100);
    private Button equipButton = new Button("Equip",18, 20, 20, 20,  100, 100, 100);
    private Button purchaseButton = new Button("Purchase", 18, 20, 20, 20,  100, 100, 100);
    private static GreenfootSound purchaseSound = new GreenfootSound("purchase.mp3");

    //Objects
    private Player player;
    private ItemInfo itemInfo;
    private StoreItem lastItem = null;
    private String lastItemName = null;
    private int lastItemCost;

    //Data structures
    private static final String[] itemNames = {"Pistol Gun","Rifle Gun","Shotgun Gun","Rifle Bullet","Shotgun Bullet","Half-Heart Refill","Speed Boost"};
    private static final int[] xCoords = {250,350,450,250,350,250,350};
    private static final int[] yCoords = {225,225,225,350,350,475,475};
    private static final int[] costs = {0,500,750,100,100,500,750}; //to be completed
    private static final int numItems = itemNames.length;
    private static GreenfootImage [] itemImages = new GreenfootImage [numItems];
    private static boolean createdImages = false;

    /**
     * Constructor for Store Menu
     * 
     * @param player            reference to player object in the GameWorld
     * @param itemInfo          reference to ItemInfo object in the GameWorld
     */
    public StoreMenu(Player player, ItemInfo itemInfo){
        storeMenuImg.setColor(Color.LIGHT_GRAY);
        storeMenuImg.fill();
        storeMenuImg.drawImage(moneyIcon,500, 30);
        setImage(storeMenuImg);
        this.player = player;
        this.itemInfo = itemInfo;
        purchaseSound.setVolume(75);
    }    

    /**
     * createImages - static method to create all the images for the items
     */
    public static void createImages(){
        if(!createdImages){
            createdImages = true;
            for(int i=0;i<numItems;i++){
                itemImages[i] = new GreenfootImage("Store"+File.separator+itemNames[i]+".png");
                itemImages[i].scale(40,(int)((double)itemImages[i].getHeight()*40/itemImages[i].getWidth()));
            }    
        }    
    }

    /**
     * addedToWorld - add buttons, labels, and StoreItems to the world
     * 
     * @param w             current world of the StoreMenu
     */
    public void addedToWorld(World w){
        PauseWorld world = (PauseWorld) getWorld();
        world.addObject(menuTitle,world.width/2,world.height/2-180);
        world.addObject(closeStore, 725, 500);
        world.addObject(gunsLabel, 225, 170);
        world.addObject(ammoLabel, 225, 295);
        world.addObject(powerupsLabel, 240, 420);
        world.addObject(equipButton, 700, 200);
        world.addObject(purchaseButton, 700, 225);
        world.addObject(quantityLabel,650,300);
        world.addObject(costLabel,650,325);
        moneyLabel = new Label(Integer.toString(player.getMoney()),18, true);
        world.addObject(moneyLabel, 720, 148);
        purchaseButton.getImage().setTransparency(0);
        equipButton.getImage().setTransparency(0);
        for(int i=0;i<numItems;i++){
            world.addObject(new StoreItem(itemImages[i], itemNames[i],this,costs[i]),xCoords[i],yCoords[i]);
        }    
    }    

    /**
     * getLastPressed - get the properties of the last item pressed
     * 
     * @param item              reference to StoreItem object
     * @param itemName          item name of store object
     * @param itemCost          item cost of store object
     */
    public void getLastPressed(StoreItem item,String itemName, int itemCost){
        if(lastItem!=null){
            lastItem.changeImage();
        }    

        lastItem = item;
        lastItemName = itemName;
        lastItemCost = itemCost;

        //quantity and cost labels
        setQuantityLabel();
        if(lastItemName.equals("Rifle Bullet")) costLabel.updateText("Cost (100 pack): $" + Integer.toString(itemCost));
        else if (lastItemName.equals("Shotgun Bullet")) costLabel.updateText("Cost (50 pack): $" + Integer.toString(itemCost));
        else costLabel.updateText("Cost: $" + Integer.toString(itemCost));

        //make buttons transparent accordingly
        setPurchaseTransparency();
        setEquipTransparency();
    }    

    /**
     * purchase - purchases lastItem
     */
    private void purchase(){
        //add to player inventory
        if(lastItemName.contains("Gun")){
            player.newGun(lastItemName);
        }    
        else if(lastItemName.equals("Rifle Bullet")){
            player.changeItemNumber(lastItemName,100);
        }   
        else if(lastItemName.equals("Shotgun Bullet")){
            player.changeItemNumber(lastItemName,50);
        } 
        else{
            player.changeItemNumber(lastItemName,1);
        }    
        if(purchaseSound.isPlaying()) purchaseSound.stop();
        purchaseSound.play();
        player.setMoney(-lastItemCost);
        itemInfo.updateMoney(-lastItemCost);
        setPurchaseTransparency();
        setEquipTransparency();
        setQuantityLabel();
        moneyLabel.updateText(Integer.toString(player.getMoney()));
    }    

    /**
     * equip - equips lastItem
     */
    private void equip(){
        if(lastItemName.contains("Gun")){
            while(!lastItemName.contains(player.getCurrentGun())) player.changeGun();
        }    
        else if(lastItemName.equals("Speed Boost")){
            player.speedBoost();
            player.changeItemNumber("Speed Boost", -1);
        }    
        else if(lastItemName.equals("Half-Heart Refill")){
            player.addOneHeart();
            player.changeItemNumber("Half-Heart Refill", -1);
        } 

        setEquipTransparency();
        setQuantityLabel();
    }

    /**
     * setPurchaseTransparency - sets transparency of purchase button
     */
    private void setPurchaseTransparency(){
        if(player.getMoney()<lastItemCost || (lastItemName.contains("Gun") && player.hasGun(lastItemName))){
            purchaseButton.getImage().setTransparency(0);
        }    
        else purchaseButton.getImage().setTransparency(255);
    }    

    /**
     * setEquipTransparency - sets transparency of equip button
     */
    private void setEquipTransparency(){
        if(lastItemName.equals("Half-Heart Refill")){
            if((player.getHearts()==6 || player.getHearts()==0) || player.getItemNumber(lastItemName)==0) equipButton.getImage().setTransparency(0);
            else equipButton.getImage().setTransparency(255);
        }    
        else if(lastItemName.contains("Gun")){
            if(lastItemName.contains(player.getCurrentGun()) || !player.hasGun(lastItemName)) equipButton.getImage().setTransparency(0);
            else equipButton.getImage().setTransparency(255);
        }    
        else if(lastItemName.contains("Bullet")) equipButton.getImage().setTransparency(0);
        else{
            if(player.getItemNumber(lastItemName)==0) equipButton.getImage().setTransparency(0);
            else equipButton.getImage().setTransparency(255);
        }    

    }  

    /**
     * setQuantityLabel - sets the text of quantityLabel
     */
    private void setQuantityLabel(){
        if(lastItemName.contains("Gun")){
            quantityLabel.updateText("Quantity: " + Integer.toString(player.hasGun(lastItemName) ? 1:0));
        }    
        else{
            quantityLabel.updateText("Quantity: " + Integer.toString(player.getItemNumber(lastItemName)));
        }
    }    

    /**
     * checkButtonClicks - checks for button clicks
     */
    protected void checkButtonClicks(){
        PauseWorld world = (PauseWorld) getWorld();
        String key = Greenfoot.getKey();
        if(Greenfoot.mouseClicked(closeStore) || "escape".equals(key) || "z".equals(key)){
            world.closeWorld();
        }
        else if(Greenfoot.mouseClicked(equipButton) && equipButton.getImage().getTransparency()!=0){
            equip();
        }
        else if(Greenfoot.mouseClicked(purchaseButton) && purchaseButton.getImage().getTransparency()!=0){
            purchase();
        }
    }     
}
