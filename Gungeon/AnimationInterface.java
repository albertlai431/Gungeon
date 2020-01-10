/**
 * The animation interface is designed to be implemented by enemies or characters requiring movement in all 4 directions 
 * of freedom. The interface ensures all enemies and characters follow through with this animation as required. 
 *
 * @author Star Xie 
 * @version January 2020
 */
public interface Animation 
{
    public void animateMovementUp();
    
    public void animateMovementDown();
    
    public void animateMovementRight();
    
    public void animateMovementLeft();
}
