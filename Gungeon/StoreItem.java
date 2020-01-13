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
    private int cost;
    private Label nameLabel;
    private StoreMenu storeMenu;
    private static final int itemSize = 80;
    private GreenfootImage storeItemImg = new GreenfootImage(itemSize,itemSize);
    private GreenfootImage selected = new GreenfootImage(itemSize,itemSize);

    /**
     * Constructor for StoreItem
     * 
     * @param itemImage         image of the item
     * @param item              name of the item
     * @param StoreMenu         reference to StoreMenu object
     * @param cost              cost of the object
     */
    public StoreItem(GreenfootImage itemImage, String item, StoreMenu storeMenu, int cost){
        this.item = item;
        this.storeMenu = storeMenu;
        this.cost = cost;
        
        //create storeItemImg
        storeItemImg.setColor(Color.WHITE);
        storeItemImg.fill();
        storeItemImg.drawImage(itemImage,(itemSize-itemImage.getWidth())/2,(itemSize-itemImage.getHeight())/2);
        setImage(storeItemImg);

        //create selected image
        selected.setColor(Color.WHITE);
        selected.fill();
        selected.setColor(Color.RED);
        selected.drawRect(0,0,itemSize-2,itemSize-2);
        selected.drawImage(itemImage,(itemSize-itemImage.getWidth())/2,(itemSize-itemImage.getHeight())/2);

        nameLabel = new Label(item,10,true);
    }    

    /**
     * addedToWorld - adds the label to the world
     */
    public void addedToWorld(World w){
        PauseWorld world = (PauseWorld) w;
        world.addObject(nameLabel,this.getX(),this.getY()+itemSize/2+10);
    }

    /**
     * Act - do whatever the StoreItem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this)){
            storeMenu.getLastPressed(this,item,cost);
            changeImage();
        }    
    }    

    /**
     * changeImage - changes the image of StoreItem
     */
    public void changeImage(){
        if(getImage()==storeItemImg) setImage(selected);
        else setImage(storeItemImg);
    }    
}
