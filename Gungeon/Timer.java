import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Timer is a Greenfoot Actor that creates an in-game countdown
 * object. The countdown is displayed through a circle and numbers counting
 * down inside the circle. When it reaches zero, it removes itself from
 * the world
 * 
 * @author Albert Lai, Star Xie
 * @version November 2019
 */
public class Timer extends Actor
{
    //Declare Instance Variables  
    private boolean timerIsRunning;
    private long milliSeconds;
    private long maxMilliSeconds;  
    private int transparency;
    private int radiusSize;
    private int textSize;
    private static int frameTime = 1000/24;

    //Declare Color objects
    private Color circleColor;
    private Color outlineColor;
    private Color textColor;

    //Declare Greenfoot Image
    private GreenfootImage circle;
    private GreenfootImage text = new GreenfootImage(1,1);

    //Declare new SimpleTimer
    private SimpleTimer timer = new SimpleTimer();

    /**
     * Main Constructor initializes initial values and is only called 
     * by the other constructors, not intended to be called directly.
     */
    public Timer()
    {
        //Setting default values for instance variables
        timerIsRunning = true;
        transparency = 255;
        radiusSize = 15;
        textSize = 20;

        circleColor = Color.RED;
        outlineColor = Color.BLUE; 
        textColor = Color.WHITE;
        updateImage();
    }

    /**
     * Constructor takes one int, for objects with a max time to count down from.
     * Both the current time and max time will be set to the same value.
     * 
     * @param maxMilliSeconds      Maximum time to start countdown at
     */
    public Timer(long maxMilliSeconds)
    {
        this();
        //Checks if the maximum time is a valid input
        if(maxMilliSeconds>0)
        {
            //Sets values for maximum and current milliSeconds
            this.maxMilliSeconds = maxMilliSeconds;
            this.milliSeconds = maxMilliSeconds;
        }
        timer.mark();
        updateImage();
    }

    /**
     * Constructor takes two ints for objects with a max time to countdown 
     * from and a current time value, which will both be set accordingly. It 
     * also takes a boolean, representing the team and uses it to set the color.
     * 
     * @param maxMilliSeconds  Max amount of milliSeconds the countdown can hold
     * @param milliSeconds     Time the countdown will start at
     * @boolean red            specifies the team (true for Red and false for Blue)
     */
    public Timer(long maxMilliSeconds, long milliSeconds)
    {
        this(maxMilliSeconds);
        //Checks to see if the time entered is valid
        if(milliSeconds>0)
        {
            this.milliSeconds=milliSeconds;
        }   
        //if(!red){
            circleColor = Color.BLACK;
            outlineColor = Color.GRAY;
        //}    
        timer.mark();
        updateImage();
    }

    /**
     * Executes whatever the Timer wants to do. The method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        //removes timer if 0 milliSeconds is reached
        if(milliSeconds <= 0)
        {
            getWorld().removeObject(this);
        }

        if(timerIsRunning){
            //Counting down the timer
            if(timer.millisElapsed()>=frameTime){
                milliSeconds-=timer.millisElapsed();
                //Updates the image regularly
                updateImage();
                timer.mark();            
                if(milliSeconds<=0){
                    timerIsRunning = false;
                }    
            }  
        }
    }

    /**
     * Returns the amount of time left on the timer
     * 
     * @return int      Amount of time left (milliSeconds)
     */
    public long getTime()
    {
        return milliSeconds;
    }

    /**
     * Creates various amounts of sectors to be filled, updates the image and position
     * of the timer each time by decreasing or increasing sectors of a circle
     */
    private void updateImage()
    {
        //Creates a square as the base for the timer and fill it to a certain colour
        circle = new GreenfootImage(2*radiusSize, 2*radiusSize);
        circle.setColor(circleColor);
        circle.setTransparency(transparency);
        //Checks to see if the timer is in the world. If it is not, create a circle at point 0,0 with radius 149
        if (getWorld() == null) circle.fillOval(0, 0, 2*radiusSize-1, 2*radiusSize-1); //Note: to create a circle it must be one less than the size of the base 

        //If the timer is in the world, execute the following code to increment the rotation
        else
        {
            //Initializes the starting X and Y values, starting at the top of the circle
            double lastX = radiusSize, lastY = 0;
            //draws the triangles to represent the circle
            for(double rotation=0; rotation<milliSeconds*360/maxMilliSeconds; rotation+=5){
                //get angle in radians
                double angle = 2*Math.PI-Math.toRadians(rotation);
                double newX = radiusSize+radiusSize*(Math.sin(angle)),newY = radiusSize-radiusSize*(Math.cos(angle));
                //draw triangle
                circle.fillPolygon(new int[] { radiusSize, (int)lastX, (int)newX}, new int[] { radiusSize, (int)lastY, (int)newY}, 3);
                lastX = newX; lastY = newY;
            }    
        }
        //Draws an image to display the numbers 
        text = new GreenfootImage(String.valueOf(""/*milliSeconds/1000*/), this.textSize,this.textColor, null);
        //Draws the image in the middle of the timer and sets the colour
        circle.drawImage(text, radiusSize-text.getWidth()/2, radiusSize-text.getHeight()/2);
        circle.setColor(outlineColor);

        //Draws the outline of the circle
        for (int i=0; i<3; i++) circle.drawOval(i, i, (radiusSize*2-1)-2*i, (radiusSize*2-1)-2*i);
        
        //Sets the image of the class as the newly created circle
        setImage(circle);
    }       
}