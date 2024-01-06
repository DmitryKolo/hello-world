
public class FrontLeftTopInBasis {
	
	// Fields
	public int front, left, top;
	
	// Constructor
	
	public FrontLeftTopInBasis(int f, int l, int t){
		
		front = f;
		left = l;
		top = t;
	}
	
	public FrontLeftTopInBasis(Cube basis){
		
		Vector[] vectorZ = new Vector[Cube.DIMENSION];
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			Vector basisVector = basis.axis[i].vector;
			if(basisVector.dz >= 0) vectorZ[i] = basisVector;
			else vectorZ[i] = new Vector (-1.0, basisVector);
		}
		
		double x01 = vectorZ[0].dx - vectorZ[1].dx;
		double x12 = vectorZ[1].dx - vectorZ[2].dx;
		double x20 = vectorZ[2].dx - vectorZ[0].dx;
		double sqx01 = x01 * x01;
		double sqx12 = x12 * x12;
		double sqx20 = x20 * x20;
		
		if(sqx01 > sqx12 && sqx01 > sqx20) {
			if(vectorZ[0].dx > vectorZ[1].dx){
				front = 0; 
				left = 1; 
			}
			else {
				front = 1; 
				left = 0; 
			}
		}
		else {
			if(sqx12 > sqx20) {
				if(vectorZ[1].dx > vectorZ[2].dx){
					front = 1; 
					left = 2; 
				}
				else {
					front = 2; 
					left = 1; 
				}
			}
			else {
				if(vectorZ[2].dx > vectorZ[0].dx){
					front = 2; 
					left = 0; 
				}
				else {
					front = 0; 
					left = 2; 
				}
			}
		}
		
		top = Cube.DIMENSION - front - left;
		
	}

	// Functions
	
}
