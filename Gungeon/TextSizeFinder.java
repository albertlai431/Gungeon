import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Font;
/**
 * Write a description of class ImgSizeFinder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextSizeFinder extends Actor
{
    private Font TextFont;
    private Graphics2D g;
    private FontMetrics fm;
    private GreenfootImage temp;
    public TextSizeFinder(){
        temp = new GreenfootImage(1,1);
        g = temp.getAwtImage().createGraphics();
    }
    public int getTextWidth(String text, int fontSize){
        TextFont = new Font ("calibri", Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int width = fm.charsWidth(text.toCharArray(), 0, text.length());
        return width; 
    }
    public int getTextHeight(String text, int fontSize){
        TextFont = new Font ("calibri", Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int height = fm.getHeight();
        return height; 
    }
    public int getTextWidth(String text, String fontName, int fontSize){
        TextFont = new Font (fontName, Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int width = fm.charsWidth(text.toCharArray(), 0, text.length());
        return width; 
    }
    public int getTextHeight(String text, String fontName, int fontSize){
        TextFont = new Font (fontName, Font.PLAIN, fontSize);
        fm = g.getFontMetrics(TextFont);
        int height = fm.getHeight();
        return height; 
    }
}