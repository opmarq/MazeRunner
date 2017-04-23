import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author OpmarQ
 *
 * @email opmarq@gmail.com
 */

public class Maze {


	private Dimension depart = null;
	private Dimension destination = null;
	
	public int height;
	private int width;
	
	private LinkedList<Dimension> obstacles;
	
	private int[][] maze;
	
	{
		obstacles = new LinkedList<Dimension>();
		LoadMaze();
	}
	
	public Maze(int height,int width) {
		
		this.height = height;
		this.width = width;

	}
	
	public Maze(int height,int width,Dimension depart,Dimension destination)
	{
		this.height = height;
		
		this.width = width;
		
		this.depart = depart;
		
		this.destination = destination;	
		
		genMaze(height, width);
		
	}

	
	public void genMaze(int height,int width)
	{
		Random rand = new Random();
		
		Integer [][] m = new Integer[height][width];
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++)
			{
				m[i][j] = rand.nextInt(16);
			}
		}
		
		
		
	}
	
	public static String toBinary(int nbr)
	{
		return String.format("%04d", Integer.parseInt(Integer.toString(nbr, 2)));
	}
	
	
	public Dimension getDepart() {
		return depart;
	}

	public void setDepart(Dimension depart) {
		this.depart = getMazeIndex(depart);
	}

	public Dimension getDestination() {
		return destination;
	}

	public void setDestination(Dimension destination) {
		this.destination = getMazeIndex(destination);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	public int[][] getMaze() {
		return maze;
	}

	public void setMaze(int[][] maze) {
		this.maze = maze;
	}

	public LinkedList<Dimension> getObstacles() {
		return obstacles;
	}

	public void setObstacles(LinkedList<Dimension> obstacles) {
		this.obstacles = obstacles;
	}

	
	public void addObstacle(Dimension obs)
	{
		obstacles.add(getMazeIndex(obs));
	}
	
	public static Dimension getMazeIndex(Dimension dim)
	{
		return new Dimension((int) dim.width / MazeArea.BLOCK_SIZE, (int) dim.height / MazeArea.BLOCK_SIZE);
	}
	
	public int getValueaAt(Dimension pos)
	{
		return this.maze[pos.width][pos.height];
	}
	
	public void setValueAt(Dimension pos,int value)
	{
		pos = getMazeIndex(pos);
		
		this.maze[pos.width][pos.height] = value;
	}
	
	public Boolean mazeValidate()
	{
		
		Boolean status = true;
		
		for(int i=0;i<height;i++)
		{
			for(int j=0;j<width;j++){
				
				String binary = Maze.toBinary(maze[j][i]);
				
				String[] splitBinary = binary.split("");
				
				if( i == 0)
				{
					
					if(splitBinary[SearchEngine.LEFT].equals("0"))
						status = false;
				}
				
				if( j == 0)
				{
					if(splitBinary[SearchEngine.TOP].equals("0"))
						status = false;
				}
				
				if( j == width - 1)
				{
					if(splitBinary[SearchEngine.BOTTOM].equals("0"))
						status = false;
				}
				
				if( i == height - 1)
				{
					if(splitBinary[SearchEngine.RIGHT].equals("0"))
						status = false;
				}
				
			}
		}
		
		return status;
	}
	
	
	public void SaveMaze()
	{
	      try {
	          FileOutputStream fileOut =
	          new FileOutputStream("data/muze.ser");
	          
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          
	          out.writeObject(maze);
	          
	          out.close();
	          
	          fileOut.close();
	          
	          System.out.printf("Serialized data is saved in /data/muze.ser");
	       }catch(IOException i) {
	          i.printStackTrace();
	       }
	}
	
	public void LoadMaze()
	{
		 try {
	         FileInputStream fileIn = new FileInputStream("data/muze.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         this.maze = (int[][]) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	}


}
