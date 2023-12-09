
import java.awt.*;

public class Cube{

	// Fields
	
	public CubeBasis basis;
	
	public Vector vectorA, vectorB, vectorC; // перейти на Axis, basis;
//	public PairOfEdges edgesPairA, edgesPairB, edgesPairC; // перейти на Axis;
	Axis[] axis = new Axis[3];
		
	public int size; 
			
	public Point centre, cornerA0B0C0, cornerA0B0C1, cornerA0B1C0, cornerA0B1C1, cornerA1B0C0, cornerA1B0C1, cornerA1B1C0, cornerA1B1C1; // перейти на Angle
	Point[][][] angle = new Point[2][2][2];
	
	private Address3D upperAngle; 

	// Constructor
	
	public Cube(int size, Point centre, Vector vectorA, Vector vectorB, Vector vectorC){
			
		this.size = size;
		this.centre = centre;
		this.basis = new CubeBasis(vectorA, vectorB, vectorC);
		
		this.vectorA = vectorA;
		this.vectorB = vectorB;
		this.vectorC = vectorC;
				
		// three-dimensional array of vertices (angle):
		for (int i = 0; i < angle.length; i++){
			
			this.axis[i] = new Axis(i, this.basis, size, centre);

			double halfsize = size / 2;
			
			double coefI = (i==0 ? -halfsize : halfsize);
			Point pointI = new Point(centre, new Vector(coefI, vectorA));				
						
		    for (int j = 0; j < angle[i].length; j++){
		    	
				double coefJ = (j==0 ? -halfsize : halfsize);
				Point pointJ = new Point(pointI, new Vector(coefJ, vectorB));				
				
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
					double coefK = (k==0 ? -halfsize : halfsize);
			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, vectorC));
			    	Point CurrentCorner = this.angle[i][j][k];
					System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
			    	
			    }
		    }
	    }
		
		this.upperAngle = this.upperAngleAddress();
		
		// to improve: first, create a three-dimensional array of vertices (angle), then specify them in the edges of the axes
//		edgesPairA = new PairOfEdges(size, centre, vectorA, vectorB, vectorC);
//		edgesPairB = new PairOfEdges(size, centre, vectorB, vectorC, vectorA);
//		edgesPairC = new PairOfEdges(size, centre, vectorC, vectorA, vectorB);
		
//		this.axis[0] = new Axis(vectorA, edgesPairA.edge0, edgesPairA.edge1); 
//		this.axis[1] = new Axis(vectorB, edgesPairB.edge0, edgesPairB.edge1);
//		this.axis[2] = new Axis(vectorC, edgesPairC.edge0, edgesPairC.edge1);
		
		//
		//this.axis[0] = new Axis(this.basis.vector[0], edgesPairA.edge0, edgesPairA.edge1); 
		

	}
			
	// Functions
	
	public void update(){
				
	}
			
	public void draw(Graphics2D g){
				
	}
	
	// Functions
	
	public Point angleAtAddress(Address3D address){
		
		return angle[address.i][address.j][address.k];
		
	}
		
	public Address3D upperAngleAddress(){
		
		Address3D upperAngleAddress = new Address3D(0, 0, 0);
		Point upperAngle = angleAtAddress(upperAngleAddress);
				
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
			    	Address3D currentAddress = new Address3D(i, j, k);
			    	Point currentAngle = angleAtAddress(currentAddress);
			    	if (upperAngle.z < currentAngle.z){
			    		upperAngleAddress = currentAddress;
			    	}
			    	
			    }
		    }
	    }
		
		return upperAngleAddress;
		
	}
	
}




