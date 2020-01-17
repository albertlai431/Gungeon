import greenfoot.*; 
import java.util.ArrayList;
/**
 * Abstract class for all enemies in the game that target and attack the player
 * 
 * @author Combined(Henry Ma), Debugging(Star, Albert), Pathfinding(Albert), Animations(Star)
 * @version January 16, 2020
 */
public abstract class Enemy extends Actor implements AnimationInterface
{
    //Initialize variables and objects
    //Coordinates used for methods
    protected int playerX;
    protected int playerY;
    //Health of enemy(this)
    protected int healthPoints;
    //Used for attacks
    protected int counter=0;
    protected int fireRate;
    //Used for animation
    protected long animationCount = 0;
    protected int movementCounter;
    protected int movementMod;
    //Used to find player
    protected ArrayList<Player> foundPlayers;
    protected Actor player;
    //Used for animation
    protected boolean animated = false;
    //Used to call methods in the world
    protected int scoreBoost;
    protected int moneyBoost;    
    /**
     * Call to inflict damage, checks for death as well
     * 
     * @param damage    Amount of damage this object takes
     */
    public void getDamaged(int damage)
    {
        //Reduce health
        healthPoints -= damage;
        //If below or at zero hp die
        if(healthPoints <= 0)
        {           
            die();
        }
        //If touching player die and harm player
        if(getWorld()!=null && this.isTouching(Player.class)) 
        {
            getWorld().getObjects(Player.class).get(0).loseOneHeart();
            die(); 
        }
    }    
    /**
     * Move towards player and checks conditions for attacking and moving
     */
    protected void moveTowardsPlayer()
    {
        //Find closeset tile to player
        int[] closestTileCoordinates = findClosestAdjacentTileTowardsPlayer();
        //Checks for conditions if attacking is possible
        if(checkLineOfSight() == false || animated==false)
        {
            animated=true;
            if(movementCounter == movementMod)
            {
                animate(closestTileCoordinates);
                movementCounter = 0;
            }
            else
            {
                movementCounter++;
            }
        }
        else
        {
            counter++;
            if(counter%fireRate == 0)
            {
                attack();
            }
        }        
    }
    /**
     * All enemies must contain an attack method(method/pattern on how they attack)
     */
    protected abstract void attack();
    /**
     * Get the distance between two objects
     * 
     * @param enemyX    X-coordinate of first object
     * @param enemyY    Y-coordinate of first object
     * @param playerX   X-coordinate of second object
     * @param playerY   Y-coordinate of second object
     * 
     * @return double   Returns the distance between the two coordinates as a double
     */
    private double getPointDistance(int enemyX, int enemyY, int playerX, int playerY)
    {
        double distance = Math.sqrt(Math.pow(playerX - enemyX, 2) + Math.pow(playerY - enemyY ,2));     
        return distance;
    }
    /**
     * Checks for objects(Walls) at the coordinates given in the parameters
     * 
     * @param x     X-coordinate of the spot to check
     * @param y     Y-coordinate of the spot to check
     * 
     * @return boolean  Returns true if object is at coordinates false otherwise
     */
    private boolean checkForWall(int x, int y)
    {
        //Get objects of Walls.class at x and y
        ArrayList <Walls> obstacleList = (ArrayList) (getWorld().getObjectsAt(x, y, Walls.class));
        //If walls exist return false
        if(obstacleList.isEmpty())
        {
            return false;
        }
        return true;
    }
    /**
     * Gets the coordinates of a tile that is closest to player
     * 
     * @return  int[]   Returns the coordinates in an array of the tile closest to player
     */
    public int[] findClosestAdjacentTileTowardsPlayer()
    {        
        foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
        player = foundPlayers.get(0); 
        //Call pathfinding class to get tile coordinates
        return Pathfinding.nextCoord(getX(),getY(), (GameWorld) getWorld(), (Player) player);
    }
    /**
     * Checks whether at the current position of this object if it can hit the player with bullets or not
     * 
     * @return boolean  Returns true if there is line of sight, false otherwise
     */
    private boolean checkLineOfSight()
    {
        //find dx, dy
        double dx=0, dy=0;
        double interval = 8;
        if(player.getX()!=getX()){
            double slope = (double)(player.getY()-getY())/(double)(player.getX()-getX());
            dx= Math.sqrt(interval*interval/(1+slope*slope));
            dy= slope*dx;
        }    
        else dy=interval;
        if(player.getX()<getX()){
            dx*=-1;
            dy*=-1;
        }

        double length = Math.sqrt((player.getX()-getX())*(player.getX()-getX())+(player.getY()-getY())*(player.getY()-getY()));
        double currX=0;
        double currY=0;
        double currdis=0;

        //check points on the line for walls
        while(currdis+interval<length)
        {
            currdis+=interval;
            currX+=dx;
            currY+=dy;
            if(getObjectsAtOffset((int)currX, (int)currY, Walls.class).size()>0)
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Removes object from the world properly
     */
    protected void die()
    {
        GameWorld world = (GameWorld) getWorld();
        world.updateScore(scoreBoost,moneyBoost);
        world.removeObject(this);
    }
    /**
     * Animates the object, checks the coordinates given in the parameters to determine the direction of the animation
     * 
     * @param closestTileCoordinates    The coordinates of the object to animate/face towards
     */
    protected void animate(int[] closestTileCoordinates)
    {
        if(closestTileCoordinates[0] > getX() && closestTileCoordinates[1] == getY())
        {           
            for(int i = 0; i < 16; i++)
            {
                animationCount++;
                animateMovementRight();
                setLocation(getX() + 1, getY());
            }
        }
        else if(closestTileCoordinates[0] < getX() && closestTileCoordinates[1] == getY())
        {           
            for(int i = 0; i < 16; i++)
            {
                animateMovementLeft();
                setLocation(getX() - 1, getY());
            }
        }
        else if(closestTileCoordinates[0] < getX() && closestTileCoordinates[1] < getY())
        {
            for(int i = 0; i < 16; i++)
            {
                animateMovementUp();
                setLocation(getX() - 1, getY() - 1 );
            }
        }
        else if(closestTileCoordinates[0] > getX() && closestTileCoordinates[1] < getY())
        {
            for(int i = 0; i < 16; i++)
            {
                animateMovementUp();
                setLocation(getX() + 1, getY() - 1);
            }
        } 
        else if(closestTileCoordinates[0] == getX() && closestTileCoordinates[1] < getY())
        {
            for(int i = 0; i < 16; i++)
            {
                animateMovementUp();
                setLocation(getX(), getY() - 1);
            }
        }         
        if(closestTileCoordinates[0] == getX() && closestTileCoordinates[1] > getY())
        {
            for(int i = 0; i < 16; i++)
            {
                animateMovementDown();
                setLocation(getX(), getY() + 2);
            }
        } 
        else if(closestTileCoordinates[0] < getX() && closestTileCoordinates[1] > getY())
        {
            for(int i = 0; i < 16; i++)
            {
                animateMovementDown();
                setLocation(getX() - 1, getY() + 1);
            }
        } 
        else if(closestTileCoordinates[0] > getX() && closestTileCoordinates[1] > getY())
        {
            for(int i = 0; i < 16; i++)
            {
                animateMovementDown();
                setLocation(getX() + 1, getY() + 1);
            }
        }         
    }
}    
