
public class Axis {

	// Fields
	
	public Vector vector;
	public Edge[] edge = new Edge[2]; 
			
	// Constructor
	
	public Axis(Vector vector, Edge edge0, Edge edge1){
			
		this.vector = vector;
		this.edge[0] = edge0;
		this.edge[1] = edge1;
	
	}
	
	public Axis(int number, CubeBasis basis, int size, Point centre){
		
		this.vector = basis.vector[number];
		
		Vector vector0 = this.vector;
		Vector vector0cw = new Vector(-1, vector0);
		
		int number1 = number + 1;
		if(number1 == 3) number1 = 0;
		Vector vector1 = basis.vector[number1];		
		Vector vector1cw = new Vector(-1, vector1);		
		
		int number2 = number1 + 1;
		if(number2 == 3) number2 = 0;
		Vector vector2 = basis.vector[number2];		
		Vector vector2cw = new Vector(-1, vector2);		
		
		this.edge[0] = new Edge(size, centre, vector0, vector1, vector2);
		this.edge[1] = new Edge(size, centre, vector0cw, vector1cw, vector2cw);
	
	}
			
	// Functions
	
	public void update(){
				
	}
	
}