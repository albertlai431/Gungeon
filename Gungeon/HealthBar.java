import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo) 

/**
 * HealthBar creates a customizable bar.
 * Can be used as a HealthBar
 * (for keeping track of health of an object).
 * Can be used as a energy/timer bar
 * (for decreasing of a resource at a set rate).
 * 
 * Takes in Integers and Greenfoot Objects(ex.color)
 * 
 * @author Henry Ma 
 * @version 1 
 */
public class HealthBar extends Actor 
{
    // Values for bars
    private int maxValue;
    private int currentValue;
    // Size of bar
    private int sizeY;
    private int sizeX;
    
    // String that is displayed(eg. 10/50)
    private String str;

    // Image of bar
    private GreenfootImage displayImage;
    // Colors for front and back of bar
    private Color front = new Color(0, 0, 100);
    private Color back = new Color(100, 0, 0);
    // Font of str
    private Font font = new Font("Courrier", 10);
    /**
     * Creates a health bar 
     * 
     * @param x X size/width of health bar
     * @param y Y size/height of health bar
     * @param max Max value of health bar(max HP)
     * @param m Color of middle bar(reflects current HP)
     */ 
    public HealthBar(int x, int y, int max, Color m)
    {
        // Sets bar values
        sizeX = x;
        sizeY = y;
        maxValue = max;
        currentValue = max;

        // Creates image and sets properties of the image
        displayImage = new GreenfootImage(sizeX, sizeY);
        displayImage.setFont(font);

        // Draws health bar and sets the image of this instance to the created image
        //drawBar(maxValue, maxValue, m);
        this.setImage(displayImage);
    }

    /**
     * Draws bar/image for HealthBar
     * 
     * @param current Current value of resource
     * @param maxValue Max value of resource
     * @param m Color of middle bar(reflects current resource)
     */ 
    private void drawBar(int current, int maxValue, Color m) 
    {
        // Sets string that will be displayed
        str = current + "/" + maxValue;

        // Sets properties and colors in displayImage for each level(back, middle, and front)
        displayImage.setColor(back);
        displayImage.fill();
        displayImage.setColor(m);

        // Fills a rect corresponding to current resource
        displayImage.fillRect(0, 0, (int)(((double)current/(double)maxValue) * sizeX), sizeY);

        // Sets color for str
        displayImage.setColor(front);

        // Draws str value onto the bar and positions it in the middle
        //displayImage.drawString(str, (sizeX/2) - ((str.length() * 11)/2), sizeY/2);
    }

    /**
     * Updates value of current resource
     * 
     * @param newValue New value of resource(update to this value)
     * @param m Color of middle bar(reflects resource)
     */ 
    public void update(int newValue, Color m)
    {
        currentValue = newValue;
        //If hp is at max do not draw bar
        if(maxValue != currentValue)
        {
            drawBar(currentValue, maxValue, m);
        } 
    }


    /**
     * Sets bar to location given by x and y
     * 
     * @param x X coordinate to be placed
     * @param y Y coordinate to be placed
     */ 
    public void follow(int x, int y)
    {
        setLocation(x, y);
    }
}