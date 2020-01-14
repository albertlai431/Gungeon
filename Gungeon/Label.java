import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Label class that allows you to display a textual value on screen.
 * <p>
 * The Label is an actor, so you will need to create it, and then add it to the world
 * in Greenfoot.
 *
 * @author Alex Li
 * @version 1.2.1
 */
public class Label extends Actor
{
    //instance variables
    private String text,fontName;
    private int fontSize;
    private boolean isTransparent;
    //default colours
    private Color textColour = Color.BLACK;
    private Color backgroundColor;
    private Font font;
    private GreenfootImage img;
    private TextSizeFinder finder = new TextSizeFinder();
    /**
     * Creates a default label with customizable font size and text. The text will have a black outline on a white background
     * 
     * @param text             The text that you want to label to have
     * @param fontSize          The desired fontSize
     * @param isTransparent     True if the background is transparent
     */
    public Label(String text, int fontSize, boolean isTransparent)
    {
        //changes the outline to be white
        this.text = text;
        this.fontSize = fontSize;
        this.isTransparent = isTransparent;
        font = new Font ("calibri", false, false, fontSize);
        fontName = "Calibri";
        textColour = Color.BLACK;
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    public Label(String text, int fontSize, int txtR, int txtB, int txtG, boolean isTransparent)
    {
        //changes the outline to be white
        this.text = text;
        this.fontSize = fontSize;
        this. isTransparent = isTransparent;
        font = new Font ("calibri", false, false, fontSize);
        fontName = "Calibri";
        textColour = new Color(txtR,txtB,txtG);
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    //must have a background
    public Label(String text, int fontSize, int txtR, int txtB, int txtG, int backR, int backB, int backG)
    {
        //changes the outline to be white
        this.text = text;
        this.fontSize = fontSize;
        isTransparent = false;
        font = new Font ("calibri", false, false, fontSize);
        fontName = "Calibri";
        textColour = new Color(txtR,txtB,txtG);
        backgroundColor = new Color(backR, backB, backG);
        //draws the image
        updateImage();
    }
    //custom fonts
    public Label(String text, String fontName, int fontSize, boolean isTransparent)
    {
        //changes the outline to be white
        this.text = text;
        this.fontSize = fontSize;
        this.isTransparent = isTransparent;
        this.fontName = fontName;
        font = new Font (fontName, false, false, fontSize);
        textColour = Color.BLACK;
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    public Label(String text, String fontName, int fontSize, int txtR, int txtB, int txtG, boolean isTransparent)
    {
        //changes the outline to be white
        this.text = text;
        this.fontSize = fontSize;
        this.isTransparent = isTransparent;
        this.fontName = fontName;
        font = new Font (fontName, false, false, fontSize);
        textColour = new Color(txtR,txtB,txtG);
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    public Label(String text, String fontName, int fontSize, int txtR, int txtB, int txtG, int backR, int backB, int backG)
    {
        //changes the outline to be white
        this.text = text;
        this.fontSize = fontSize;
        isTransparent = false;
        this.fontName = fontName;
        font = new Font (fontName, false, false, fontSize);
        textColour = new Color(txtR,txtB,txtG);
        backgroundColor = new Color(backR, backB, backG);
        //draws the image
        updateImage();
    }
    /**
     * Updates the image on screen to show the current text with its specified font size and colour.
     */
    private void updateImage()
    {
        if(fontName.equals("Calibri"))img = new GreenfootImage(finder.getTextWidth(text, fontSize), finder.getTextHeight(text, fontSize));
        else img = new GreenfootImage(finder.getTextWidth(text,fontName, fontSize), finder.getTextHeight(text,fontName, fontSize));
        img.setFont(font);
        if(!isTransparent){
            img.setColor(backgroundColor);
            img.fill();
        }
        img.setColor(textColour);
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        setImage(img);
    }
    public int getLength(){
        return finder.getTextWidth(text, fontName, fontSize);
    }
    public void updateText(String newText){
        text = newText;
    }
}
