import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * A button without a background that can have a custom font, font size, and text colour. It will highlight a different colour when the user mouses
 * over the button, this colour can be customized too. Multiple buttons can be added to the world. 
 * 
 * @author Alex Li
 * @version v1.2
 */
public class Button extends Actor
{
    //Declare private variables
    private GreenfootImage img, temp;
    //text is the text the button will display
    private String text;
    //The font of the button
    private Font TextFont;
    //The colours the button will have
    private Color textColour, highlightColour;
    //The font size and button width of the button
    private int fontSize, textWidth = 0, actCount = 0;
    //Helper class to find the width of height of the button
    private TextSizeFinder finder = new TextSizeFinder();
    //Information about the mouse
    private MouseInfo mouse;
    /**
     * Constructs a Button with a given String and a specified font size. It will have a default text colour of light gray and a default font of calibri 
     * @param text              String value to display
     * @param fontSize          The font size, as an integer
     */
    public Button (String text, int fontSize){
        //sets the colours
        textColour = new Color(98,98,98);
        highlightColour = new Color(158,158,159);
        //sets the font
        TextFont = new Font ("calibri",fontSize);
        this.text = text;
        this.fontSize = fontSize;
        //creates a greenfoot image with the correct width and height to store the text
        img = new GreenfootImage(finder.getTextWidth(text,fontSize), finder.getTextHeight(text,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        //draws the text
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        //sets the image of the button
        setImage(img);
    }
    /**
     * Constructs a Button with a given String, a specified font size, and custom text colour and highlight colour. It will use the default font, calibri
     * @param text              String value to display
     * @param fontSize          The font size, as an integer
     * @param txtR              The intensity of red in the text's colour. The R value in RGB
     * @param txtG              The intensity of green in the text's colour. The G value in RGB
     * @param txtB              The intensity of blue in the text's colour. The B value in RGB
     * @param highlightR        The intensity of red in the highlight colour of the button. The R value in RGB
     * @param highlightG        The intensity of green in the highlight colour of the button. The G value in RGB
     * @param highlightB        The intensity of blue in the highlight colour of the button. The B value in RGB
     */
    public Button (String text, int fontSize, int txtR, int txtG, int txtB, int highlightR, int highlightG, int highlightB){
        //sets the colours
        textColour = new Color(txtR,txtG,txtB);
        highlightColour = new Color(highlightR,highlightG,highlightB);
        //sets the font
        TextFont = new Font ("calibri",fontSize);
        this.text = text;
        this.fontSize = fontSize;
        //creates a greenfoot image with the correct width and height to store the text
        img = new GreenfootImage(finder.getTextWidth(text,fontSize), finder.getTextHeight(text,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        //draws the text
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        //sets the image of the button
        setImage(img);
    }
    /**
     * Constructs a Button with a given String and a specified font and font size. It will have a default text colour of light gray 
     * @param text              String value to display
     * @param fontName          The name of the font you want the label to use. Must be a valid font in greenfoot and java
     * @param fontSize          The font size, as an integer
     */
    public Button (String text, String fontName, int fontSize){
        //sets the colours
        textColour = new Color(98,98,98);
        highlightColour = new Color(158,158,159);
        //sets the font
        TextFont = new Font (fontName,fontSize);
        this.text = text;
        this.fontSize = fontSize;
        //creates a greenfoot image with the correct width and height to store the text
        img = new GreenfootImage(finder.getTextWidth(text,fontName,fontSize), finder.getTextHeight(text,fontName,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        //draws the text
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        //sets the image of the button
        setImage(img);
    }
    /**
     * Constructs a Button with a given String, a specified font and font size, and custom text colour and highlight colour. 
     * @param text              String value to display
     * @param fontName          The name of the font you want the label to use. Must be a valid font in greenfoot and java
     * @param fontSize          The font size, as an integer
     * @param txtR              The intensity of red in the text's colour. The R value in RGB
     * @param txtG              The intensity of green in the text's colour. The G value in RGB
     * @param txtB              The intensity of blue in the text's colour. The B value in RGB
     * @param highlightR        The intensity of red in the highlight colour of the button. The R value in RGB
     * @param highlightG        The intensity of green in the highlight colour of the button. The G value in RGB
     * @param highlightB        The intensity of blue in the highlight colour of the button. The B value in RGB
     */
    public Button (String text,  String fontName, int fontSize, int txtR, int txtG, int txtB, int highlightR, int highlightG, int highlightB){
        //sets the colours
        textColour = new Color(txtR,txtG,txtB);
        highlightColour = new Color(highlightR,highlightG,highlightB);
        //sets the font
        TextFont = new Font(fontName,fontSize);
        this.text = text;
        this.fontSize = fontSize;
        //creates a greenfoot image with the correct width and height to store the text
        img = new GreenfootImage(finder.getTextWidth(text,fontName,fontSize), finder.getTextHeight(text,fontName,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        //draws the text
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        //sets the image of the button
        setImage(img);
    }
    /**
     * Act - do whatever Button wants to do. This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     * Checks whether or not the mouse is hovering over the button, if it is highlight it, otherwise don't
     */
    public void act(){
        if(actCount % 5 == 0){
            if(Greenfoot.mouseMoved(this)){ 
                //Highlights the button
                this.hoverOver();
            }
            //resets the button to its normal state
            if(Greenfoot.mouseMoved(null) &&!Greenfoot.mouseMoved(this)) this.reset();
        }
    }
    /**
     * Highlights the button if the mouse is hoving over a button
     */
    private void hoverOver(){
        //clears the image to prevent the images from overlapping 
        img.clear();
        //changes the text colour to its highlighted colour
        img.setColor(highlightColour);
        img.setFont(TextFont);
        //draws the text, but with the highlighted colour
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    /**
     * Resets the button if the mouse moves away from it
     */
    private void reset()
    {
        //clears the image to prevent the images from overlapping 
        img.clear();
        //changes the text colour back to normal
        img.setColor(textColour);
        img.setFont(TextFont);
        //redraws the text
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
}
