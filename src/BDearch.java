import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * @author OpmarQ
 *
 * @email opmarq@gmail.com
 */

public class BDearch extends SearchEngine {

	
    ArrayList<Dimension> L= new ArrayList<Dimension>(), T=new ArrayList<Dimension>();
    LinkedList<Dimension> file = new LinkedList<Dimension>();
    private Dimension predecessor[][];  
    protected Dimension [] searchPath = null;
    protected int maxDepth;
    boolean isSearching = true;
    public final static int BFS = 0;
    public final static int DFS = 1;
    
    Dimension currentLoc;

    boolean startSearching = false;
    
	
	public BDearch(Maze maze, int type) {
		super(maze);
		
		System.out.println(maze.getObstacles());
		
        if (searchPath == null) {
            searchPath = new Dimension[1000];
            for (int i=0; i<1000; i++) {
                searchPath[i] = new Dimension();
            }
        }
		
        searchPath[0] = maze.getDepart();
        
        predecessor = new Dimension[maze.getWidth()][maze.getHeight()];  
        for (int i=0; i<maze.getWidth(); i++) 
            for (int j=0; j<maze.getHeight(); j++) 
                predecessor[i][j] = null;
        
        System.out.println("depart "+maze.getDepart());
        
        file.add(maze.getDepart());
        T.add(maze.getDestination());
		
        doBFS(type);
        
        System.out.println("hahowa "+predecessor[maze.getDestination().width][maze.getDestination().height]);
        
        
        maxDepth = 0;
        if (!isSearching) {
            searchPath[maxDepth++] = maze.getDestination();
            for (int i=0; i<100; i++) {
                searchPath[maxDepth] = predecessor[searchPath[maxDepth - 1].width][searchPath[maxDepth - 1].height];
                maxDepth++;
                System.out.println(searchPath[maxDepth - 1] + " and start location = " + maze.getDepart() );
                if (searchPath[maxDepth - 1].getWidth() == maze.getDepart().getWidth() && searchPath[maxDepth - 1].getHeight() == maze.getDepart().getHeight() ){
                	
                	break;  // back to starting node
                }
            }
        }else{
        	JOptionPane.showMessageDialog(null, "Pas De Chemin!", "InfoBox: " + "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        

            	            
        
	}
	
	private void doBFS(int type) {
        
        while (!file.isEmpty()) {
            
        	if(type == 1)
        		currentLoc = file.removeLast();
        	else if( type == 0)
        		currentLoc = file.removeFirst();
            
            
            if(T.contains(currentLoc)){
            	
                 System.out.println("But trouvé : (" + currentLoc.width +", " + currentLoc.height+")");
                 isSearching = false;
                 break;
             }
             else{
               
                Dimension [] connected = getPossibleMoves(currentLoc);
                  
                for (int i=0; i<connected.length; i++) {

                    if ( connected[i]!=null && !file.contains(connected[i]) && !L.contains(connected[i])) {
                        L.add(connected[i]);
                        predecessor[connected[i].width][connected[i].height] = currentLoc;
                        file.addLast(connected[i]);
                    }
                }
            }
        }      
        
    }
	
	
	public Dimension[][] getPredecessor(){
		return this.predecessor;
	}
   
	public Dimension[] getSearchPath(){
		return this.searchPath;
	}
	
	public int getMaxDepth(){
		return maxDepth;
	}
}
