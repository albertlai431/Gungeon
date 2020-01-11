import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Options here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Options extends World
{
    private GreenfootImage wasd = new GreenfootImage("wasd.png");
    private GreenfootImage shop = new GreenfootImage("z.png");
    private GreenfootImage mouse = new GreenfootImage("mouse.png");
    private GreenfootImage reload = new GreenfootImage("R.png");
    private GreenfootImage switchGun = new GreenfootImage("E.png");
    private GreenfootImage Pause = new GreenfootImage("Esc.png");
    private Label title = new Label("CONTROLS",40,true);
    private Label subTitle1 = new Label("MOVEMENT",25,true);
    private Label subTitle2 = new Label("AIMING",25,true);
    private Label subTitle3 = new Label("ADDITIONAL CONTORLS",25,true);
    private Label text1 = new Label("Use the \"WASD\" keys to move.",16,true);
    private Label text2 = new Label("Use the mouse to aim and mouse 1 (the left most button) to shoot.",16,true);
    private Label text3 = new Label("\"Z\" opens the in game shop",16,true);
    private Label text4 = new Label("\"R\" reloads your gun",16,true);
    private Label text5 = new Label("\"E\" to swap guns",16,true);
    private Label text6 = new Label("\"Esc\" pasues the game",16,true);
    private Button back = new Button("BACK",35);
    /**
     * Constructor for objects of class Options.
     * 
     */
    public Options()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        addObject(title,480,50);
        addObject(subTitle1,480,100);
        addObject(subTitle2,480,250);
        addObject(subTitle3,480,420);
        addObject(text1,600,175);
        addObject(text2,717,330);
        addObject(text3,590,462);
        addObject(text4,567,515);
        addObject(text5,556,565);
        addObject(text6,575,620);
        addObject(back,915,620);
        getBackground().drawImage(wasd,300,125);
        getBackground().drawImage(mouse,300,300);
        getBackground().drawImage(shop,300,450);
        getBackground().drawImage(reload,300,500);
        getBackground().drawImage(switchGun,300,550);
        getBackground().drawImage(Pause,300,600);
    }
    public void act(){
        if(Greenfoot.mouseClicked(back)){
            TitleScreen titleWorld = new TitleScreen();
            Greenfoot.setWorld(titleWorld);
        }
    }
}
