import java.io.File;

/**
 * Write a description of class PlayerData here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class PlayerData extends Data 
{
    private File txtFile;
    
    /**
     * Constructor for objects of class PlayerData
     */
    public PlayerData(File txtFile)
    {
        this.txtFile = txtFile;
    }
    
    public PlayerData(Player player, File txtFile){
        this.txtFile = txtFile;
    }    

    public void saveData(Player player){
        
    }
    
    public void parseData(){
        
    }  
}
