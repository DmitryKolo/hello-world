
public class CubeBasis {

	// Fields
		
	public Vector[] vector = new Vector[Cube.DIMENSION];

	// Constructor
	
	public CubeBasis(Cube cube){
		
		for(int i = 0; i < Cube.DIMENSION; i++)
			this.vector[i] = cube.axis[i].vector;
		
	}
	public CubeBasis(Vector vectorI, Vector vectorJ, Vector vectorK){
		
		this.vector[0] = vectorI; 
		this.vector[1] = vectorJ; 
		this.vector[2] = vectorK;
		
	}	
		
}