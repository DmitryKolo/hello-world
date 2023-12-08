
import java.awt.*;

public class Cube{

	// Fields
	
	public Vector vectorA, vectorB, vectorC;
	
	public int size; 
			
	public PairOfEdges pairOfEdgesA, pairOfEdgesB, pairOfEdgesC;
	public Point centre, cornerA0B0C0, cornerA0B0C1, cornerA0B1C0, cornerA0B1C1, cornerA1B0C0, cornerA1B0C1, cornerA1B1C0, cornerA1B1C1;

	// Constructor
	
	public Cube(int size, Point centre, Vector vectorA, Vector vectorB, Vector vectorC){
			
		this.size = size;
		this.centre = centre;
		
		this.vectorA = vectorA;
		this.vectorB = vectorB;
		this.vectorC = vectorC;
				
		pairOfEdgesA = new PairOfEdges(size, centre, vectorA, vectorB, vectorC);
		pairOfEdgesB = new PairOfEdges(size, centre, vectorB, vectorC, vectorA);
		pairOfEdgesC = new PairOfEdges(size, centre, vectorC, vectorA, vectorB);
	
	}
			
	// Functions
	
	public void update(){
				
	}
			
	public void draw(Graphics2D g){
				
	}
}




