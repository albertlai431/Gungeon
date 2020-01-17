import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Options is the world where the user is told how to play the game. It displays all the controls and game features
 * 
 * @author Alex
 * @version 0.3
 */
public class Options extends World
{
    //instance variables
    private GreenfootImage wasd = new GreenfootImage("wasd.png");
    private GreenfootImage shop = new GreenfootImage("z.png");
    private GreenfootImage mouse = new GreenfootImage("mouse.png");
    private GreenfootImage reload = new GreenfootImage("R.png");
    private GreenfootImage switchGun = new GreenfootImage("E.png");
    private GreenfootImage Pause = new GreenfootImage("Esc.png");
    //text to be added to the world
    private Label title = new Label("CONTROLS",40,true);
    private Label subTitle1 = new Label("MOVEMENT",25,true);
    private Label subTitle2 = new Label("AIMING",25,true);
    private Label subTitle3 = new Label("ADDITIONAL CONTROLS",25,true);
    private Label text1 = new Label("Use the \"WASD\" keys to move.",16,true);
    private Label text2 = new Label("Use the mouse to aim and mouse 1 (the left most button) to shoot.",16,true);
    private Label text3 = new Label("\"Z\" opens the in game shop",16,true);
    private Label text4 = new Label("\"R\" reloads your gun",16,true);
    private Label text5 = new Label("\"E\" to swap guns",16,true);
    private Label text6 = new Label("\"Esc\" pasues the game",16,true);
    private Button back = new Button("BACK",35);
    private Button next = new Button("NEXT",35);
    private Button done = new Button("DONE",35);
    //int to track what page of the tutorial you are on
    private int tutorialCount = 0;
    /**
     * Constructor for objects of class Options. Adds all the text and images to the world for the first tutorial page
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
        addObject(back,50,620);
        addObject(next,911,620);
        getBackground().drawImage(wasd,300,125);
        getBackground().drawImage(mouse,300,300);
        getBackground().drawImage(shop,300,450);
        getBackground().drawImage(reload,300,500);
        getBackground().drawImage(switchGun,300,550);
        getBackground().drawImage(Pause,300,600);
    }
    /**
     * Act - do whatever Options wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     * Checks for mouse input from the user and acts accordingly.
     */
    public void act(){
        //if the user presses the back button...
        if(Greenfoot.mouseClicked(back)){
            //if there are no previous "pages" in the options world, take the user back to the title screen
            if(tutorialCount==0){
                TitleScreen titleWorld = new TitleScreen();
                Greenfoot.setWorld(titleWorld);
            }
            //if there are previous pages in the options world, taken them to the previous page
            else if(tutorialCount == 1){
                List objects = getObjects(null);
                removeObjects(objects);
                getBackground().fill();
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
                addObject(back,50,620);
                addObject(next,911,620);
                getBackground().drawImage(wasd,300,125);
                getBackground().drawImage(mouse,300,300);
                getBackground().drawImage(shop,300,450);
                getBackground().drawImage(reload,300,500);
                getBackground().drawImage(switchGun,300,550);
                getBackground().drawImage(Pause,300,600);
                tutorialCount--;
            }
            else if(tutorialCount == 2){
                tutorialCount--;
                List objects = getObjects(null);
                removeObjects(objects);
                getBackground().fill();
                addObject(next,911,620);
                addObject(back,50,620);
                //instead of adding labels, we saved this page as an image  
                getBackground().drawImage(new GreenfootImage("tutorial"+tutorialCount+".png"),0,0);
                tutorialCount--;
            }
        }
        //if the user clicks the next button
        if(Greenfoot.mouseClicked(next)){
            //increase the page count
            tutorialCount++;
            //move on to the next page of the tutorial 
            if(tutorialCount == 1){
                List objects = getObjects(null);
                removeObjects(objects);
                getBackground().fill();
                addObject(next,911,620);
                addObject(back,50,620);
                getBackground().drawImage(new GreenfootImage("tutorial"+tutorialCount+".png"),0,0);
            }
            else if(tutorialCount == 2){
                List objects = getObjects(null);
                removeObjects(objects);
                getBackground().fill();
                addObject(back,50,620);
                //replaces the next button with the done button
                addObject(done,910,620);
                getBackground().drawImage(new GreenfootImage("tutorial"+tutorialCount+".png"),0,0);
            }
        }
        //if the user is on the last page of the tutorial and they click done, it will take them back to the title screen
        if(Greenfoot.mouseClicked(done)){
            TitleScreen titleWorld = new TitleScreen();
            Greenfoot.setWorld(titleWorld);
        }
    }
}
