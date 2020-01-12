import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Enemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemy extends Actor
{
    protected static int tileSizeX;
    protected static int tileSizeY;
    protected int currentX;
    protected int currentY;
    protected int playerX;
    protected int playerY;
    protected int bulletWidth;
    protected int healthPoints;
    protected int bulletSpeed;
    protected int damage = 1;
    protected ArrayList<Player> foundPlayers;
    protected Actor player;

    protected abstract void attack();
    
    public void getHit(int damage)
    {
        healthPoints -= damage;
        if(healthPoints <= 0)
        {
            die();
        }
    }
    
    protected void moveTowardsPlayer()
    {
        int[] closestTileCoordinates = findClosestAdjacentTileTowardsPlayer();
        turnTowards(closestTileCoordinates[0], closestTileCoordinates[1]);
        while(getX() != closestTileCoordinates[0] && getY() != closestTileCoordinates[1])
        {
            move(1);
            while(checkLineOfSight(bulletWidth, getPointDistance(getX(), getY(), player.getX(), player.getY())) == true)
            {
                attack();
            } 
        }
    }

    private double getPointDistance(int enemyX, int enemyY, int playerX, int playerY)
    {
        double distance = Math.sqrt(Math.pow(playerX - enemyX, 2) + Math.pow(playerY - enemyY ,2));     
        return distance;
    }

    private boolean checkForObstacle(int x, int y)
    {
        ArrayList obstacleList = new ArrayList<Player>(getWorld().getObjectsAt(x, y, Player.class));
        if(obstacleList.isEmpty())
        {
            return false;
        }
        return true;
    }

    private int[] findClosestAdjacentTileTowardsPlayer()
    {
        currentX = getX();
        currentY = getY();        
        playerX = player.getX();
        playerY = player.getY();
        double shortestDistance = 10000;
        int coordinateXStart = currentX - tileSizeX;
        int coordinateYStart = currentY - tileSizeY;
        int closestTileX = 0;
        int closestTileY = 0;
        for(int x = 0; x < 3; x ++)
        {
            for(int y = 0; y < 3; y ++)
            {
                if(x == 2 && y == 2){}         
                else
                {
                    int[] tileCoordinates = new int[]{coordinateXStart + x * tileSizeX, coordinateYStart + y * tileSizeY};
                    double distance = getPointDistance(tileCoordinates[0], tileCoordinates[1], playerX, playerY);
                    if(checkForObstacle(tileCoordinates[0], tileCoordinates[1])){}                    
                    else
                    {
                        if(distance < shortestDistance)
                        {
                            shortestDistance = distance;
                            closestTileX = tileCoordinates[0];
                            closestTileY = tileCoordinates[1];
                        }
                    }
                }
            }
        }
        int[] closestTileCoordinates = new int[]{closestTileX, closestTileY};
        return closestTileCoordinates;
    }

    private boolean checkLineOfSight(int bulletWidth, double bulletLength)
    {
        LineOfSightRect line = new LineOfSightRect(bulletWidth, (int)getPointDistance(getX(), getY(), player.getX(), player.getY()) + 5);
        return line.playerVisible(getRotation(), getX(), getY(), player.getX(), player.getY());
    }
    
    protected void die()
    {
        //play animation code
        getWorld().removeObject(this);
    }
}
