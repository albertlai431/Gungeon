import greenfoot.*; 
import java.util.ArrayList;
/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemy extends Actor implements AnimationInterface
{
    protected static int tileSizeX = 32;
    protected static int tileSizeY = 32;
    protected int currentX;
    protected int currentY;
    protected int playerX;
    protected int playerY;
    protected int bulletWidth;
    protected int healthPoints;
    protected int bulletSpeed;
    protected int quadrant;
    protected int counter=0;
    protected int damage = 1;
    protected int fireRate;
    protected long animationCount = 0;
    protected int movementCounter = 30;
    protected GreenfootImage animationImage;
    protected ArrayList<Player> foundPlayers;
    protected Actor player;
    protected boolean animated = false;
    protected int scoreBoost;
    protected int moneyBoost;
    
    public void getDamaged(int damage)
    {
        healthPoints -= damage;
        if(healthPoints <= 0)
        {           
            die();
        }
        if(getWorld()!=null && this.isTouching(Player.class)) 
        {
            getWorld().getObjects(Player.class).get(0).loseOneHeart();
            die(); 
        }
    }

    protected void moveTowardsPlayer()
    {
        int[] closestTileCoordinates = findClosestAdjacentTileTowardsPlayer();
        if(checkLineOfSight() == false || animated==false)
        {
            animated=true;
            if(movementCounter == 30)
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

    protected abstract void attack();

    private double getPointDistance(int enemyX, int enemyY, int playerX, int playerY)
    {
        double distance = Math.sqrt(Math.pow(playerX - enemyX, 2) + Math.pow(playerY - enemyY ,2));     
        return distance;
    }

    private boolean checkForWall(int x, int y)
    {
        ArrayList <Walls> obstacleList = (ArrayList) (getWorld().getObjectsAt(x, y, Walls.class));
        if(obstacleList.isEmpty())
        {
            return false;
        }
        return true;
    }

    public int[] findClosestAdjacentTileTowardsPlayer()
    {        
        foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
        player = foundPlayers.get(0); 
        return Pathfinding.nextCoord(getX(),getY(), (GameWorld) getWorld(), (Player) player);
    }

    private boolean checkLineOfSight()
    {
        double dx=0, dy=0;
        double interval = 8;
        if(player.getX()!=getX()){
            double slope = (double)(player.getY()-getY())/(double)(player.getX()-getX());
            dx= Math.sqrt(interval*interval/(1+slope*slope));
            dy= slope*dx;
        }    
        else dy=interval;
        

        double length = Math.sqrt((player.getX()-getX())*(player.getX()-getX())+(player.getY()-getY())*(player.getY()-getY()));
        if(player.getX()<getX()){
            dx*=-1;
            dy*=-1;
        }

        double currX=0;
        double currY=0;
        double currdis=0;

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
    /*
    private int getBulletRotation()
    {
        int Xdisplacement = Math.abs(currentX-playerX);
        int Ydisplacement = Math.abs(currentY-playerY);
        int theta = 0;
        if(Ydisplacement != 0)
        {
            theta = (int) Math.atan(Xdisplacement/Ydisplacement);
        }
        else theta = 0;

        if(currentY-playerY>=0)
        {
            if(currentX-playerX>=0) theta = 180 + theta;
            else theta = 180 - theta;
        }
        else
        {
            if(currentX-playerX>=0)theta = 360-theta;
            else theta = theta;
        }

        return theta;
    }
    */

    protected void die()
    {
        GameWorld world = (GameWorld) getWorld();
        world.updateScore(scoreBoost,moneyBoost);
        world.removeObject(this);
    }

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

