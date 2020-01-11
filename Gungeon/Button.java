import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontMetrics;
import java.awt.Graphics2D;
//import java.awt.Font;
/**
 * A Generic Button to display text that is clickable. Multiple buttons can be added the world.
 * 
 * @author Alex Li
 * @version v1.1.0
 */
public class Button extends Actor
{
    // Declare private variables
    private GreenfootImage img, temp;
    //ButtonText is the text the button will display
    private String text;
    //The font of the button
    private Font TextFont;
    //A boolean to control whether the button will belong in a drop down menu or not
    private Color textColour, highlightColour;
    //The font size of the button
    private int fontSize, textWidth = 0, actCount = 0;
    //helper class
    private TextSizeFinder finder = new TextSizeFinder();
    private MouseInfo mouse;
    /**
     * Constructs a TextButton with a given String, a specified size, whether it is a subButton, and its rgb values
     * @param text          String value to display
     * @param fontSize      Size of text, as an integer
     */
    public Button (String text, int fontSize){
        textColour = new Color(98,98,98);
        highlightColour = new Color(158,158,159);
        TextFont = new Font ("calibri",fontSize);
        this.text = text;
        this.fontSize = fontSize;
        img = new GreenfootImage(finder.getTextWidth(text,fontSize), finder.getTextHeight(text,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    //txtR, txtB ... highlightG are the RGB values of the text colour and highlight colour
    public Button (String text, int fontSize, int txtR, int txtB, int txtG, int highlightR, int highlightB, int highlightG){
        textColour = new Color(txtR,txtB,txtG);
        highlightColour = new Color(highlightR,highlightB,highlightG);
        TextFont = new Font ("calibri",fontSize);
        this.text = text;
        this.fontSize = fontSize;
        img = new GreenfootImage(finder.getTextWidth(text,fontSize), finder.getTextHeight(text,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    //custom fonts
    public Button (String text, String fontName, int fontSize){
        textColour = new Color(98,98,98);
        highlightColour = new Color(158,158,159);
        TextFont = new Font (fontName,fontSize);
        this.text = text;
        this.fontSize = fontSize;
        img = new GreenfootImage(finder.getTextWidth(text,fontName,fontSize), finder.getTextHeight(text,fontName,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    public Button (String text,  String fontName, int fontSize, int txtR, int txtB, int txtG, int highlightR, int highlightB, int highlightG){
        textColour = new Color(txtR,txtB,txtG);
        highlightColour = new Color(highlightR,highlightB,highlightG);
        TextFont = new Font(fontName,fontSize);
        this.text = text;
        this.fontSize = fontSize;
        img = new GreenfootImage(finder.getTextWidth(text,fontName,fontSize), finder.getTextHeight(text,fontName,fontSize));
        img.setColor(textColour);
        img.setFont(TextFont);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    public void act(){
        if(actCount % 5 == 0){
            if(Greenfoot.mouseMoved(this)){ 
                //Highlights the button
                this.hoverOver();
            }
            if(Greenfoot.mouseMoved(null) &&!Greenfoot.mouseMoved(this)) this.reset();
        }
    }
    /**
     * Highlights the button if the mouse is hoving over a button
     */
    public void hoverOver(){
        img.clear();
        img.setColor(highlightColour);
        img.setFont(TextFont);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    public void reset()
    {
        img.clear();
        img.setColor(textColour);
        img.setFont(TextFont);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
}
