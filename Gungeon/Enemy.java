
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
    protected int damage = 1;
    protected int fireRate;
    protected long animationCount = 0;
    protected int movementCounter = 30;
    protected GreenfootImage animationImage;
    protected ArrayList<Player> foundPlayers;
    protected Actor player;

    protected abstract void attack();

    public void getDamaged(int damage)
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
        if(movementCounter == 30)
        {
            animate(closestTileCoordinates);
            movementCounter = 0;
        }
        else
        {
            movementCounter++;
        }
        while(checkLineOfSight(bulletWidth) == true)
        {
            int counter = fireRate;
            if(fireRate == counter)
            {
                attack();
                counter = 0;
            }
            else
            {
                counter++;
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
        ArrayList <Obstacles> obstacleList = (ArrayList) (getWorld().getObjectsAt(x, y, Obstacles.class));
        if(obstacleList.isEmpty())
        {
            return false;
        }
        return true;
    }

    private int[] findClosestAdjacentTileTowardsPlayer()
    {        
        foundPlayers = new ArrayList<Player>(getWorld().getObjects(Player.class));
        player = foundPlayers.get(0);    
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
                if(x == 1 && y == 1){}         
                else
                {
                    int[] tileCoordinates = new int[]{coordinateXStart + (x * tileSizeX), coordinateYStart + (y * tileSizeY)};                    
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

    private boolean checkLineOfSight(int bulletWidth)
    {
        LineOfSightRect line = new LineOfSightRect(bulletWidth, (int)getPointDistance(getX(), getY(), player.getX(), player.getY()) + 5);
        getWorld().addObject(line, 2, 2);
        return line.playerVisible(getRotation(), getX(), getY(), player.getX(), player.getY());
    }

    protected void die()
    {
        GameWorld world = (GameWorld) getWorld();
        world.updateScore();
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
