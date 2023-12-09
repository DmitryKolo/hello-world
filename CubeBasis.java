
public class CubeBasis {

	// Fields
		
	public Vector[] vector = new Vector[3];

	// Constructor
	
	public CubeBasis(Vector vectorI, Vector vectorJ, Vector vectorK){
		
		this.vector[0] = vectorI; 
		this.vector[1] = vectorJ; 
		this.vector[2] = vectorK;
		
	}	
	
}