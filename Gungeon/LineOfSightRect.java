import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class LineOfSightRect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LineOfSightRect extends Actor
{
    private ArrayList<Resource> obstacles;
    private GreenfootImage rect;
    private Color rectColor = new Color(123, 115, 115);
    public LineOfSightRect(int x, int y)
    {
        rect = new GreenfootImage(x, y);
        rect.setColor(rectColor);
        rect.fill();
        setImage(rect);
    }
    
    public boolean playerVisible(int rotation, int enemyX, int enemyY, int playerX, int playerY)
    {
        setLocation((enemyX + playerX)/2, (enemyY + playerY)/2);
        setRotation(rotation);        
        obstacles = new ArrayList<Resource>(getIntersectingObjects(Resource.class));
        if(obstacles.isEmpty())
        {
            System.out.println("yes");
            return true;
        }
        System.out.println("no");
        return false;
    } 
}
