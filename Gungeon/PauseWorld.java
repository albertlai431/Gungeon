import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * PauseWorld - world that is set when player clicks pause, opens the store, or ends the game. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class PauseWorld extends World
{
    private GameWorld gameWorld;
    public static int height = 640;
    public static int width = 960;
    private String menuType;
    private Button returnToTitleScreen = new Button("Return to Title Screen", 22);
    private Label scoreLabel;
    private static GreenfootSound victorySound = new GreenfootSound("victory.mp3");
    private static GreenfootSound gameOverSound = new GreenfootSound("lostAllHeart.mp3");
    
    /**
     * Constructor for objects of class PauseWorld, called to create a PauseMenu
     * 
     * @param menuType              Type of the menu
     * @param gameWorld             gameWorld of the player
     */
    public PauseWorld(String menuType, GameWorld gameWorld)
    {    
        super(960, 640, 1); 
        this.menuType=menuType;
        setBackground("pauseWorldImg.png");
        if(menuType.equals("pause")) addObject(new PauseMenu(),480,320);
        this.gameWorld = gameWorld;
    }
    
    /**
     * Constructor for objects of class PauseWorld, called to create a Store
     * 
     * @param gameWorld             gameWorld of the player
     * @param menuType              Type of the menu
     * @param player                player in the world
     * @param itemInfo              itemInfo in the world
     */
    public PauseWorld(GameWorld gameWorld, Player player, ItemInfo itemInfo){
        this("store", gameWorld);
        addObject(new StoreMenu(player,itemInfo),480,320);
    }   
    
    /**
     * Constructor for objects of class PauseWorld, called to create a gameOver screen
     * 
     * @param score                 score of the player
     * @param isWin                 true if the player won, false if not
     */
    public PauseWorld(int score, boolean isWin){
        super(960, 640, 1); 
        if(isWin){
            setBackground("Victory.png");
            scoreLabel = new Label("Score: " + Integer.toString(score), 35, 230, 230, 230, true);
            addObject(scoreLabel,120,575);
            addObject(returnToTitleScreen, 830, 575);
            victorySound.setVolume(60);
            victorySound.playLoop();
        }    
        else{
            setBackground("gameOver.png");
            scoreLabel = new Label("Score: " + Integer.toString(score), 35, 230, 230, 230, true);
            addObject(scoreLabel,480,550);
            addObject(returnToTitleScreen, 480, 585);
            gameOverSound.setVolume(70);
            gameOverSound.play();
        }    
    }    
    
    /**
     * act - checks for button clicks
     */
    public void act(){
        //checks for button press
        if(returnToTitleScreen.getWorld()!=null && Greenfoot.mouseClicked(returnToTitleScreen)){
            if(victorySound.isPlaying()) victorySound.stop();
            Greenfoot.setWorld(new TitleScreen());
        }    
    }    
    
    /**
     * closeWorld - sets the game back to gameWorld
     */
    public void closeWorld(){
        if(menuType.equals("pause")) gameWorld.resumeWorld();
        Greenfoot.setWorld(gameWorld);
    }    
}
