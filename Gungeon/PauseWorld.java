import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PauseWorld here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class PauseWorld extends World
{
    private GameWorld gameWorld;
    public static int height = 640;
    public static int width = 960;
    private Button returnToTitleScreen = new Button("Return to Title Screen", 22);
    private Label scoreLabel;
    
    /**
     * Constructor for objects of class PauseWorld.
     * 
     */
    public PauseWorld(String menuType, GameWorld gameWorld)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        setBackground("pauseWorldImg.png");
        if(menuType.equals("pause")) addObject(new PauseMenu(),480,320);
        this.gameWorld = gameWorld;
    }
    
    public PauseWorld(GameWorld gameWorld, Player player, ItemInfo itemInfo){
        this("store", gameWorld);
        addObject(new StoreMenu(player,itemInfo),480,320);
    }   
    
    public PauseWorld(int score, boolean isWin){
        super(960, 640, 1); 
        
        if(isWin){
            setBackground("Victory.png");
            scoreLabel = new Label("Score: " + Integer.toString(score), 35, 230, 230, 230, true);
            addObject(scoreLabel,120,575);
            addObject(returnToTitleScreen, 830, 575);
        }    
        else{
            setBackground("gameOver.png");
            scoreLabel = new Label("Score: " + Integer.toString(score), 35, 230, 230, 230, true);
            addObject(scoreLabel,480,550);
            addObject(returnToTitleScreen, 480, 585);
        }    
    }    
    
    public void act(){
        if(returnToTitleScreen.getWorld()!=null && Greenfoot.mouseClicked(returnToTitleScreen)) Greenfoot.setWorld(new TitleScreen());
    }    
    
    public void closeWorld(){
        Greenfoot.setWorld(gameWorld);
    }    
}
