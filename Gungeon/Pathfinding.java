import javafx.util.Pair;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;
import greenfoot.*;
/**
 * Pathfinding is used by the Enemies class to find an optimal path to the player. It utilizes BFS (breadth first search) and backtracking. 
 * 
 * @author Albert Lai
 * @version January 2020
 */
public class Pathfinding  
{
    private static final int dir[][] = {{1,0},{-1,0},{0,1},{0,-1}};
    private static int x = 0;

    /**
     * nextCoord - finds the next tile in the 2D array the enemy should move to
     * 
     * @param startX                        X coordinate of enemy
     * @param secondY                       Y coordinate of enemy
     * @param world                         world of the the enemy
     * @param player                        player in the world
     * @return int[]                        new coordinates to move to
     */
    public static int[] nextCoord(int startX, int startY, GameWorld world, Player player){
        int [][] dis = new int [30][20];
        for(int[] row: dis) Arrays.fill(row,-1);
        Queue <Pair <Integer, Integer>> queue = new LinkedList <Pair <Integer, Integer>>();
        int startFirstInd = startX/GameWorld.tileSize;
        int startSecondInd = startY/GameWorld.tileSize;
        int endFirstInd = player.getX()/GameWorld.tileSize;
        int endSecondInd = player.getY()/GameWorld.tileSize;

        queue.add(new Pair<>(startFirstInd, startSecondInd));
        dis[startFirstInd][startSecondInd] = 0;

        //BFS
        while(!queue.isEmpty()){
            int curFirstInd = queue.peek().getKey();
            int curSecondInd = queue.peek().getValue();
            queue.remove();
            if(curFirstInd == endFirstInd && curSecondInd == endSecondInd) break;
            for(int i=0;i<4;i++){
                int newFirstInd = curFirstInd + dir[i][0], newSecondInd = curSecondInd + dir[i][1];
                if(!world.isWall(newFirstInd,newSecondInd) && newSecondInd!=0 && newSecondInd!=19 && dis[newFirstInd][newSecondInd]==-1){
                    dis[newFirstInd][newSecondInd] = dis[curFirstInd][curSecondInd]+1;
                    queue.add(new Pair<>(newFirstInd,newSecondInd));
                }   
            }    
        }  

        //Backtracking
        int curFirstInd = endFirstInd, curSecondInd = endSecondInd;
        for(int i = dis[endFirstInd][endSecondInd]-1; i>=1; i--){
            for(int j=0;j<4;j++){
                int newFirstInd = curFirstInd + dir[j][0];
                int newSecondInd = curSecondInd + dir[j][1];
                if(dis[newFirstInd][newSecondInd]==i){
                    curFirstInd = newFirstInd; 
                    curSecondInd = newSecondInd; 
                    break;
                }    
            }  
        }    

        return new int[]{GameWorld.convert(curFirstInd), GameWorld.convert(curSecondInd)};
    }    
}
