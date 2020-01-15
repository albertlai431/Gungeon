
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
    protected int fireRate = 5;
    protected long animationCount = 0;
    protected int movementCounter = 30;
    protected GreenfootImage animationImage;
    protected ArrayList<Player> foundPlayers;
    protected Actor player;
    
    protected Walls wall;
    protected int yeetx, yeety;
    protected double slope;
    protected boolean printed = false;

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
        if(checkLineOfSight() == false)
        {
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
            if(counter%80 == 0)
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
                    if(checkForWall(tileCoordinates[0], tileCoordinates[1])){}                    
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

    public boolean checkLineOfSight()
    {
        /*line = new LineOfSightRect(bulletWidth, (int)getPointDistance(getX(), getY(), player.getX(), player.getY()) + 5);
        getWorld().addObject(line, 2, 2);
        boolean answer = line.playerVisible(this.getBulletRotation(), getX(), getY(), player.getX(), player.getY());
        System.out.println(answer); */
        //return answer;

        double dx=0, dy=0;
        double interval = 8;
        if(player.getX()!=getX()){
            slope = (double)(player.getY()-getY())/(double)(player.getX()-getX());
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
            //System.out.println(currX + " " + currY);
            if(getObjectsAtOffset((int)currX, (int)currY, Walls.class).size()>0)
            {
                yeetx = (int)currX;
                yeety = (int)currY;
                wall = getObjectsAtOffset((int)currX, (int)currY, Walls.class).get(0);
                //System.out.println("false");
                return false;
            }
        }
        //System.out.println("true");
        return true;
    }
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
}    

