
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
	
	// Functions

	public static int axisIndexCubeToLayer(int rotatableAxisIndex, int i){
		
		if (rotatableAxisIndex == Cube.DIMENSION - 1 || i == rotatableAxisIndex) return i;
		else return Cube.DIMENSION - rotatableAxisIndex - i;		
	}
    

	public static int positionAtAxisCubeToLayer(int rotatableAxisIndex, int i, int n){
		
		if (rotatableAxisIndex == Cube.DIMENSION - 1 || i != 1 - rotatableAxisIndex) return n;
		else return 1 - n;		
	}	
		
}