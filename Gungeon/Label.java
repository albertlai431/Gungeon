import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Label class that allows you to display a textual value on screen with or without a background. You can create a label with a custom font, a custom text
 * size, custom letter colours, and background colours.
 * <p>
 * The Label is an actor, so you will need to create it, and then add it to the world in Greenfoot.
 *
 * @author Alex Li
 * @version 1.5
 */
public class Label extends Actor
{
    //instance variables
    private String text,fontName;
    private int fontSize;
    private boolean isTransparent;
    //set default text colour
    private Color textColour = Color.BLACK;
    private Color backgroundColor;
    private Font font;
    //an image to store what the label will look like
    private GreenfootImage img;
    //a helper class to help find the size of the label based on the font and the font size
    private TextSizeFinder finder = new TextSizeFinder();
    /**
     * Creates a default label with customizable font size and text, and an option to have a transparent background. It will use the default font of calibri.
     * 
     * @param text              The text that you want to label to have
     * @param fontSize          The desired fontSize
     * @param isTransparent     Must be set to true for the background to be transparent, otherwise it will be white
     */
    public Label(String text, int fontSize, boolean isTransparent)
    {
        this.text = text;
        this.fontSize = fontSize;
        this.isTransparent = isTransparent;
        //sets the font
        font = new Font ("calibri", false, false, fontSize);
        fontName = "Calibri";
        //sets the text colour
        textColour = Color.BLACK;
        //sets the background colour
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    /**
     * Creates a default label with customizable font size, text, text colour, and an option to have a transparent background. It will use the
     * default font of calibri.
     * 
     * @param text              The text that you want to label to have
     * @param fontSize          The desired fontSize
     * @param txtR              The intensity of red in the text's colour. The R value in RGB
     * @param txtG              The intensity of green in the text's colour. The G value in RGB
     * @param txtB              The intensity of blue in the text's colour. The B value in RGB
     * @param isTransparent     Must be set to true for the background to be transparent, otherwise it will be white
     */
    public Label(String text, int fontSize, int txtR, int txtG, int txtB, boolean isTransparent)
    {
        this.text = text;
        this.fontSize = fontSize;
        this. isTransparent = isTransparent;
        //sets the fonts
        font = new Font ("calibri", false, false, fontSize);
        fontName = "Calibri";
        //sets the custom text colour
        textColour = new Color(txtR,txtG,txtB);
        //sets the backgroundColor
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    /**
     * Creates a default label with customizable font size, text, text colour, and background colour. It will use the default font of calibri. 
     * 
     * @param text              The text that you want to label to have
     * @param fontSize          The desired fontSize
     * @param txtR              The intensity of red in the text's colour. The R value in RGB
     * @param txtG              The intensity of green in the text's colour. The G value in RGB
     * @param txtB              The intensity of blue in the text's colour. The B value in RGB
     * @param backR             The intensity of red in the background colour. The R value in RGB
     * @param backG             The intensity of green in the background colour. The G value in RGB
     * @param backB             The intensity of blue in the background colour. The B value in RGB
     */
    public Label(String text, int fontSize, int txtR, int txtG, int txtB, int backR, int backG, int backB)
    {
        this.text = text;
        this.fontSize = fontSize;
        isTransparent = false;
        //sets the fonts
        font = new Font ("calibri", false, false, fontSize);
        fontName = "Calibri";
        //sets the custom text colour
        textColour = new Color(txtR,txtG,txtB);
        //sets the custom background colour
        backgroundColor = new Color(backR, backG, backB);
        //draws the image
        updateImage();
    }
    /**
     * Creates a default label with customizable font size, font and text, and an option to have a transparent background. 
     * 
     * @param text              The text that you want to label to have
     * @param fontName          The name of the font you want the label to use. Must be a valid font in greenfoot and java
     * @param fontSize          The desired fontSize
     * @param isTransparent     Must be set to true for the background to be transparent, otherwise it will be white
     */
    public Label(String text, String fontName, int fontSize, boolean isTransparent)
    {
        this.text = text;
        this.fontSize = fontSize;
        this.isTransparent = isTransparent;
        this.fontName = fontName;
        //sets the font
        font = new Font (fontName, false, false, fontSize);
        //sets the text colour
        textColour = Color.BLACK;
        //sets the background colour
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    /**
     * Creates a default label with customizable font, font size, text, text colour, and an option to have a transparent background. 
     * 
     * @param text              The text that you want to label to have
     * @param fontName          The name of the font you want the label to use. Must be a valid font in greenfoot and java
     * @param fontSize          The desired fontSize
     * @param txtR              The intensity of red in the text's colour. The R value in RGB
     * @param txtG              The intensity of green in the text's colour. The G value in RGB
     * @param txtB              The intensity of blue in the text's colour. The B value in RGB
     * @param isTransparent     Must be set to true for the background to be transparent, otherwise it will be white
     */
    public Label(String text, String fontName, int fontSize, int txtR, int txtG, int txtB, boolean isTransparent)
    {
        this.text = text;
        this.fontSize = fontSize;
        this.isTransparent = isTransparent;
        this.fontName = fontName;
        //sets the font
        font = new Font (fontName, false, false, fontSize);
        //sets the text colour
        textColour = new Color(txtR,txtG,txtB);
        //sets the background colour
        if(isTransparent) backgroundColor = new Color(0,0,0,0);
        else backgroundColor = new Color(0,0,0);
        //draws the image
        updateImage();
    }
    /**
     * Creates a default label with customizable font, font size, text, text colour, and background colour. 
     * 
     * @param text              The text that you want to label to have
     * @param fontName          The name of the font you want the label to use. Must be a valid font in greenfoot and java
     * @param fontSize          The desired fontSize
     * @param txtR              The intensity of red in the text's colour. The R value in RGB
     * @param txtG              The intensity of green in the text's colour. The G value in RGB
     * @param txtB              The intensity of blue in the text's colour. The B value in RGB
     * @param backR             The intensity of red in the background colour. The R value in RGB
     * @param backG             The intensity of green in the background colour. The G value in RGB
     * @param backB             The intensity of blue in the background colour. The B value in RGB
     */
    public Label(String text, String fontName, int fontSize, int txtR, int txtG, int txtB, int backR, int backG, int backB)
    {
        this.text = text;
        this.fontSize = fontSize;
        isTransparent = false;
        this.fontName = fontName;
        //sets the font
        font = new Font (fontName, false, false, fontSize);
        //sets the font colour
        textColour = new Color(txtR,txtG,txtB);
        //sets the background colour
        backgroundColor = new Color(backR, backG, backB);
        //draws the image
        updateImage();
    }
    /**
     * Draws the text onto a greenfoot image, which then gets added onto the screen to show the current text with its specified parameters
     */
    private void updateImage()
    {
        //finds the size the background must be to fit the text with the current font and font size 
        if(fontName.equals("Calibri"))img = new GreenfootImage(finder.getTextWidth(text, fontSize), finder.getTextHeight(text, fontSize));
        else img = new GreenfootImage(finder.getTextWidth(text,fontName, fontSize), finder.getTextHeight(text,fontName, fontSize));
        //sets the font to draw the text with
        img.setFont(font);
        if(!isTransparent){
            //fill the background if it is not transparent 
            img.setColor(backgroundColor);
            img.fill();
        }
        //set the colour to draw the text with
        img.setColor(textColour);
        //draws the text
        img.drawString(text,0,img.getHeight() - (img.getHeight()/3));
        //sets the image
        setImage(img);
    }
    /**
     * Returns the length of the label
     * @return int              The length of the label with the current font, font size and text
     */
    public int getLength(){
        return finder.getTextWidth(text, fontName, fontSize);
    }
    /**
     * Updates the text to be displayed 
     * @param newText           The new text to be displayed onto the screen   
     */
    public void updateText(String newText){
        text = newText;
        updateImage();
    }
}
