
public class PairOfEdges {

	// Fields
	
	public int size; 
	public Point centre;
	
	public Vector vector0, vector1, vector2;
	public Vector vector0cw, vector1cw, vector2cw;
	//public Edge edge0, edge1;
	
	// Constructor
	
	public PairOfEdges(int size, Point centre, Vector vector0, Vector vector1, Vector vector2){
			
		this.size = size;
		this.centre = centre;
		
		this.vector0 = vector0;
		this.vector1 = vector1;
		this.vector2 = vector2;
		
		Vector vector0cw = new Vector(-vector0.dx, -vector0.dy, -vector0.dz);
		Vector vector1cw = new Vector(-vector1.dx, -vector1.dy, -vector1.dz);
		Vector vector2cw = new Vector(-vector2.dx, -vector2.dy, -vector2.dz);
		
//		edge0 = new Edge(size, centre, vector0, vector1, vector2);
//		edge1 = new Edge(size, centre, vector0cw, vector1cw, vector2cw);
	
	}
			
}
