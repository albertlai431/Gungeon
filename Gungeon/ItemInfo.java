import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ItemInfo tracks and displays the current gun, the amount of bullets in the gun's magazine, the total ammo (excluding what is already in the magazine, 
 * and the amount of kills, the amount of money and the current score the player has. Although you can create multiple instances of ItemInfo, 
 * only one should exist in a world at any given time.
 * </p>
 * It consists of a 247 by 79 gray rectangle with a line that divides the gun information and the player stats 
 * 
 * @author Alex Li
 * @version 0.9
 */
public class ItemInfo extends Actor
{
    //A GreenfootImage that will be used to display the info
    private GreenfootImage myImg;
    //Other instance variables
    private Color backgroundColour = new Color (192,192,192), borderColor = Color.BLACK;
    //An array to hold all the images of each gun
    private GreenfootImage[] guns = new GreenfootImage[3];
    //Icons for kills and money
    private GreenfootImage moneyIcon = new GreenfootImage("Money.png");
    private GreenfootImage killsIcon = new GreenfootImage("KillsIcon.png");
    private int currentGun, totalAmmo, currentAmmo, money, kills = 0,score = 0;
    private Label ammoLabel, moneyLabel, killsLabel,scoreLabel;
    /**
     * Creates an ItemInfo board and adds all the starting information of the player 
     * @param currentGun            The current gun the player is holding. 0 = pistol, 1 = rifle, 2 = shotgun
     * @param totalAmmo             The total ammo of a specific type the player has. If the current gun is the pistol, totalAmmo = -1 
     * @param currentAmmo           The amount of bullets in gun's magazine 
     * @param money                 The player's starting money
     */
    public ItemInfo(int currentGun, int totalAmmo, int currentAmmo,int money){
        GameWorld world = (GameWorld) getWorld();
        myImg= new GreenfootImage(274,79);
        //draws the board
        drawBoard();
        //fills in the array with the images of each gun
        guns[0] = new GreenfootImage("Pistol.png");
        guns[1] = new GreenfootImage("Rifle.png");
        guns[2] = new GreenfootImage("Shotgun.png");
        this.currentGun = currentGun;
        this.totalAmmo = totalAmmo;
        this.currentAmmo = currentAmmo;
        this.money = money;
        //creates the labels that track score, money and kills
        moneyLabel = new Label(Integer.toString(money),18,true);
        killsLabel = new Label("0",18,true);
        scoreLabel = new Label("0",18,true);
        //draws the current gun onto the board
        drawGun();
    }
    /**
     * Adds all the necessary labels to the world
     * @param world         The Greenfoot World where ItemInfo is added into. 
     */
    public void addedToWorld(World world){
        if(currentGun == 0) {
            ammoLabel = new Label(Integer.toString(currentAmmo)+"/infinite",16,true);
            world.addObject(ammoLabel,758,628);
        }
        else{
            ammoLabel = new Label(Integer.toString(currentAmmo)+"/"+Integer.toString(totalAmmo),16,true);
            if(currentGun == 1) world.addObject(ammoLabel,790-(ammoLabel.getLength()/2),628);
            else if(currentGun == 2) world.addObject(ammoLabel,790-(ammoLabel.getLength()/2),628);
        }
        world.addObject(moneyLabel, 837 + (moneyLabel.getLength()/2), 577);
        world.addObject(killsLabel, 837 + (killsLabel.getLength()/2), 599);
        world.addObject(scoreLabel, 813 + (killsLabel.getLength()/2), 622);
    } 
    /**
     * Draws the current gun onto ItemInfo
     */
    private void drawGun(){
        //clears the image to prevent the stacking of images
        myImg.clear();
        //redraws the board
        drawBoard();
        //draws each gun at its correct position 
        if(currentGun == 0){
            guns[0].scale(54,33);
            myImg.drawImage(guns[currentGun],10,5);
        }
        else if(currentGun == 1){
            guns[1].scale(81,27);
            myImg.drawImage(guns[currentGun],12,11);
        }
        else if(currentGun == 2){
            guns[2].scale(96,27);
            myImg.drawImage(guns[currentGun],8,11);
        }
    }   
    /**
     * Updates the label that displays how much ammo the player has in the magazine and in total
     */
    private void updateAmmoLabel(){
        GameWorld world = (GameWorld) getWorld(); 
        //if ItemInfo is not in the world, do not change the labels. Prevents error
        if(getWorld()!=null){
            if(currentGun == 0) ammoLabel.updateText(Integer.toString(currentAmmo)+"/infinite");           
            else ammoLabel.updateText(Integer.toString(currentAmmo)+"/"+Integer.toString(totalAmmo));
            if(currentGun == 0) world.addObject(ammoLabel,758,628);
            else if(currentGun == 1) world.addObject(ammoLabel,790-(ammoLabel.getLength()/2),628);
            else if(currentGun == 2) world.addObject(ammoLabel,790-(ammoLabel.getLength()/2),628);            
        }
    }
    /**
     * Updates the label that displays the total money the player has
     */
    private void updateMoneyLabel(){
        moneyLabel.updateText(Integer.toString(money));
        if(getWorld()!=null) getWorld().addObject(moneyLabel, 837 + (moneyLabel.getLength()/2), 577);
    }
    /**
     * Updates the label that displays the total kills the player has
     */
    private void updateKillsLabel(){
        killsLabel.updateText(Integer.toString(kills));
        if(getWorld()!=null) getWorld().addObject(killsLabel, 837 + (killsLabel.getLength()/2), 599);
    }
    /**
     * Updates the label that displays the total score the player has
     */
    private void updateScoreLabel(){
        scoreLabel.updateText(Integer.toString(score));
        if(getWorld()!=null) getWorld().addObject(scoreLabel, 813 + (scoreLabel.getLength()/2), 622);
    }
    /**
     * Draws the board with correct dimensions and colours
     */
    private void drawBoard(){
        //draws the border
        myImg.setColor(borderColor);
        myImg.fill();
        //draws the background
        myImg.setColor(backgroundColour);
        myImg.fillRect(2,2,270,75);
        myImg.setColor(borderColor);
        //draws the line the divides the gun informations and the player information
        myImg.drawRect(0,0,110,79);
        setImage(myImg);
        //adds the kills and money icon
        myImg.drawImage(moneyIcon,120,5);
        myImg.drawImage(killsIcon,120,25);
    }
    /**
     * Updates the current gun the user is holding 
     * @param currentGun            The current gun the user is using. 0 = pistol, 1 = rifle, 2 = shotgun
     * @param totalAmmo             The total ammo of a specific gun the player has, excluding the magazine. If the current gun is the pistol, totalAmmo = -1 
     * @param currentAmmo           The amount of bullets in gun's magazine  
     */
    public void updateGun(int currentGun, int totalAmmo, int currentAmmo){
        this.currentGun = currentGun;
        this.totalAmmo = totalAmmo;
        this.currentAmmo = currentAmmo;
        //removes the ammoLabel, to be redrawn in a new position depending on how large the label is
        getWorld().removeObject(ammoLabel);
        //updates and redraws the ammoLabel
        updateAmmoLabel();
        //draws the new gun
        drawGun();
    }
    /**
     * Increases total kills by one
     */
    public void incrementKills(){
        kills++;
        //removes the killsLabel, to be redrawn in a new position depending on how large the label is
        getWorld().removeObject(killsLabel);
        //updates and redraws the killsLabel
        updateKillsLabel();
    }
    /**
     * Updates the amount of money the user has
     * @param increment         the amount of money the user gains or spends
     */
    public void updateMoney(int increment){
        money+=increment;
        //removes the moneyLabel, to be redrawn in a new position depending on how large the label is
        getWorld().removeObject(moneyLabel);
        //updates and redraws the moneyLabel
        updateMoneyLabel();
    }
    /**
     * Updates the user's score
     * @param increment         the amount of score the user gains 
     */
    public void updateScore(int increment){
        score+=increment;
        //removes the scoreLabel, to be redrawn in a new position depending on how large the label is
        getWorld().removeObject(scoreLabel);
        //updates and redraws the scoreLabel
        updateScoreLabel();
    }
    /**
     * Updates the user's total ammo and ammo in the magazine whenever the user reloads their gun
     * @param totalAmmo             The total ammo of a specific gun the player has excluding the ammo in the gun's magazine. If the current gun is the pistol, totalAmmo = -1 
     * @param currentAmmo           The amount of bullets in gun's magazine  
     */
    public void updateAmmo(int totalAmmo, int currentAmmo){
        this.totalAmmo = totalAmmo;
        this.currentAmmo = currentAmmo;
        //removes the ammoLabel, to be redrawn in a new position depending on how large the label is
        getWorld().removeObject(ammoLabel);
        //updates and redraws the ammoLabel
        updateAmmoLabel();
    }
    /**
     * Updates the user's ammo in the magazine whenever the user shoots
     */
    public void updateAmmo(){
        currentAmmo--;
        //updates the ammoLabel
        updateAmmoLabel();
    }
    /**
     * Returns the player's score
     * @return int                  The score the player has
     */
    public int getScore(){
        return score;
    }
}
