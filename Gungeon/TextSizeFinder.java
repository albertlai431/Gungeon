import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Font;
/**
 * TextSizeFinder is a helper class that can return the width standard height of a String with a given font and font size. It uses
 * Java's Font, not Greenfoot's. It is not meant to be added to the world
 * 
 * @author Alex Li
 * @version 0.2
 */
public class TextSizeFinder extends Actor
{
    //instance variables
    private Font TextFont;
    private Graphics2D g;
    private FontMetrics fm;
    //a temporary image to use FontMetrics on
    private GreenfootImage temp;
    /**
     * Creates an instance of TextSizeFinder
     */
    public TextSizeFinder(){
        temp = new GreenfootImage(1,1);
        g = temp.getAwtImage().createGraphics();
    }
    /**
     * Returns the width of a String in calibri with a custom font size
     * @param text              The text you want to find the width of
     * @param fontSize          The font size
     * @return int              The width of the String
     */
    public int getTextWidth(String text, int fontSize){
        TextFont = new Font ("calibri", Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int width = fm.charsWidth(text.toCharArray(), 0, text.length());
        return width; 
    }
    /**
     * Returns the standard height of the String in calibri with a custom font size
     * @param text              The text you want to find the width of
     * @param fontSize          The font size
     * @return int              The standard height of the String
     */
    public int getTextHeight(String text, int fontSize){
        TextFont = new Font ("calibri", Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int height = fm.getHeight();
        return height; 
    }
    /**
     * Returns the width of a String in a custom font with a custom font size
     * @param text              The text you want to find the width of
     * @param fontName          The name of the custom font. Must be a valid font in greenfoot and java
     * @param fontSize          The font size
     * @return int              The width of the String
     */
    public int getTextWidth(String text, String fontName, int fontSize){
        TextFont = new Font (fontName, Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int width = fm.charsWidth(text.toCharArray(), 0, text.length());
        return width; 
    }
    /**
     * Returns the standard height of the String in calibri with a custom font size
     * @param text              The text you want to find the width of
     * @param fontName          The name of the custom font. Must be a valid font in greenfoot and java
     * @param fontSize          The font size
     * @return int              The standard height of the String
     */
    public int getTextHeight(String text, String fontName, int fontSize){
        TextFont = new Font (fontName, Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int height = fm.getHeight();
        return height; 
    }
}
