import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Only one iteminfo should exist in a world at any given moment
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ItemInfo extends Actor
{
    private GreenfootImage myImg;
    private Color backgroundColour = new Color (192,192,192), borderColor = Color.BLACK;
    private GreenfootImage[] guns = new GreenfootImage[3];
    private GreenfootImage moneyIcon = new GreenfootImage("Money.png");
    private GreenfootImage killsIcon = new GreenfootImage("KillsIcon.png");
    private int currentGun, totalAmmo, currentAmmo, money, kills = 0,score = 0;
    private Label ammoLabel, moneyLabel, killsLabel,scoreLabel;
    /**
     * @param currentGun            0 = pistol, 1 = rifle, 2 = shotgun
     * @param totalAmmo             The total ammo of a specific type the player has. If the current gun is the pistol, totalAmmo = -1 
     * @param currentAmmo           The amount of bullets in gun's magazine 
     * @param money                 The player's starting money
     */
    public ItemInfo(int currentGun, int totalAmmo, int currentAmmo,int money){
        GameWorld world = (GameWorld) getWorld();
        myImg= new GreenfootImage(274,79);
        drawBoard();
        guns[0] = new GreenfootImage("Pistol.png");
        guns[1] = new GreenfootImage("Rifle.png");
        guns[2] = new GreenfootImage("Shotgun.png");
        this.currentGun = currentGun;
        this.totalAmmo = totalAmmo;
        this.currentAmmo = currentAmmo;
        this.money = money;
        moneyLabel = new Label(Integer.toString(money),18,true);
        killsLabel = new Label("0",18,true);
        scoreLabel = new Label("0",18,true);
        drawGun();
    }
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
    private void drawGun(){
        myImg.clear();
        drawBoard();
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
    private void updateAmmoLabel(){
        GameWorld world = (GameWorld) getWorld(); 
        if(getWorld()!=null){
            if(currentGun == 0) ammoLabel.updateText(Integer.toString(currentAmmo)+"/infinite");           
            else ammoLabel.updateText(Integer.toString(currentAmmo)+"/"+Integer.toString(totalAmmo));
            if(currentGun == 0) world.addObject(ammoLabel,758,628);
            else if(currentGun == 1) world.addObject(ammoLabel,790-(ammoLabel.getLength()/2),628);
            else if(currentGun == 2) world.addObject(ammoLabel,790-(ammoLabel.getLength()/2),628);            
        }
    }
    private void updateMoneyLabel(){
        moneyLabel.updateText(Integer.toString(money));
        if(getWorld()!=null) getWorld().addObject(moneyLabel, 837 + (moneyLabel.getLength()/2), 577);
    }
    private void updateKillsLabel(){
        killsLabel.updateText(Integer.toString(kills));
        if(getWorld()!=null) getWorld().addObject(killsLabel, 837 + (killsLabel.getLength()/2), 599);
    }
    private void updateScoreLabel(){
        scoreLabel.updateText(Integer.toString(score));
        if(getWorld()!=null) getWorld().addObject(scoreLabel, 813 + (scoreLabel.getLength()/2), 622);
    }
    private void drawBoard(){
        myImg.setColor(borderColor);
        myImg.fill();
        myImg.setColor(backgroundColour);
        myImg.fillRect(2,2,270,75);
        myImg.setColor(borderColor);
        myImg.drawRect(0,0,110,79);
        setImage(myImg);
        myImg.drawImage(moneyIcon,120,5);
        myImg.drawImage(killsIcon,120,25);
    }
    /**
     * Call this method when the user switches guns
     * @param currentGun            0 = pistol, 1 = rifle, 2 = shotgun
     * @param totalAmmo             The total ammo of a specific type the player has. If the current gun is the pistol, totalAmmo = -1 
     * @param currentAmmo           The amount of bullets in gun's magazine  
     */
    public void updateGun(int currentGun, int totalAmmo, int currentAmmo){
        this.currentGun = currentGun;
        this.totalAmmo = totalAmmo;
        this.currentAmmo = currentAmmo;
        getWorld().removeObject(ammoLabel);
        updateAmmoLabel();
        drawGun();
    }
    /**
     * Call this method when the user kills an enemy, increases total kills by one
     */
    public void incrementKills(){
        kills++;
        getWorld().removeObject(killsLabel);
        updateKillsLabel();
    }
    /**
     * Call this method when the user gains or spends money
     * @param increment         the amount of money the user gains or spends
     */
    public void updateMoney(int increment){
        money+=increment;
        getWorld().removeObject(moneyLabel);
        updateMoneyLabel();
    }
    /**
     * Call this method when the user gains score
     * @param increment         the amount of score the user gains 
     */
    public void updateScore(int increment){
        score+=increment;
        getWorld().removeObject(scoreLabel);
        updateScoreLabel();
    }
    /**
     * Call this method when the user reloads their gun
     * @param totalAmmo             The total ammo of a specific type the player has. If the current gun is the pistol, totalAmmo = -1 
     * @param currentAmmo           The amount of bullets in gun's magazine  
     */
    public void updateAmmo(int totalAmmo, int currentAmmo){
        this.totalAmmo = totalAmmo;
        this.currentAmmo = currentAmmo;
        getWorld().removeObject(ammoLabel);
        updateAmmoLabel();
    }
    /**
     * Call this method when the user shoots their gun
     */
    public void updateAmmo(){
        currentAmmo--;
        updateAmmoLabel();
    }
    /**
     * Returns the player's score
     * @return int                  The score   
     */
    public int getScore(){
        return score;
    }
}
