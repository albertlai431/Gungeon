import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.NoSuchElementException;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.io.FileWriter;

/**
 * Write a description of class PlayerData here.
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class PlayerData 
{
    private LinkedHashMap <String, Integer> map = new LinkedHashMap <String, Integer>();
    private File txtFile;

    /**
     * Constructor for objects of class PlayerData
     * 
     * @param txtFile           text file to load from 
     */
    public PlayerData(File txtFile)
    {
        this.txtFile = txtFile;
        parseData();
    }
    
    /**
     * saveData - save data from player into text file
     * 
     * @param player            player to save from
     */
    public void saveData(Player player){
        //get data from Player
        
        
        //save data into a text file (haven't tested yet!)
        try{
            FileWriter fw = new FileWriter(txtFile);
            for (String key : map.keySet()) {
                int val = map.get(key);
                fw.write(key+"\n");
                fw.write(val+"\n");
                fw.flush();
            }
            fw.close();
        }
        catch(java.io.IOException e){
            System.out.println("No such file");
        } 
    }

    /**
     * createPlayer - create a player from 
     * 
     * @return Player           player to create
     */
    public Player createPlayer(){
        
        return new Player();
    }    
    
    /**
     * parseData - load data from Player.txt 
     */
    public void parseData(){
        Scanner s = null;
        try{
            s = new Scanner(txtFile);
        }    
        catch(FileNotFoundException e){
            System.out.println("File Not Found");
        } 

        while(true){
            try{
                String key = s.nextLine();
                int val = Integer.parseInt(s.nextLine());
                map.put(key,val);
            } 
            catch(NoSuchElementException e){
                break;
            }    
        }    
    }  
}
