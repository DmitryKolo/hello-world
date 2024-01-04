
public class FractionalAddress {

	// Fields
		
	public double i;
	public double j;
	public double k;
	
	// Constructor
	
	public FractionalAddress(){
		
		this.i = 0; 
		this.j = 0; 
		this.k = 0;
		
	}	
	
	public FractionalAddress(double i, double j, double k){
		
		this.i = i; 
		this.j = j; 
		this.k = k;
		
	}	
	
	// Functions
	
	public double getCoordinate(int coordinateIndex){
		
		switch(coordinateIndex){
		
			case 0: 
				return this.i; 
			case 1: 
				return this.j; 
			case 2: 
				return this.k;
			default:
				throw new RuntimeException("Illegal coordinate index.");
		
		}	
	}
	
	public void setCoordinate(int coordinateIndex, double coordinateValue){
	
	switch(coordinateIndex){
	
		case 0: 
			this.i = coordinateValue; 
			break;
		case 1: 
			this.j = coordinateValue;
			break;
		case 2: 
			this.k = coordinateValue;
			break;
		default:
			throw new RuntimeException("Illegal coordinate index.");
	}	
}

	
	public void print(){
		
		System.out.println("i = " + i + ", j = " + j + ", k = " + k);
			
	}
	
	
	public Point calculatedPoint(Matrix transitionMatrix){
		
		int M = transitionMatrix.getM(); 
		
		Matrix columnX = new Matrix(M,
				//Cube.DIMENSION + 1, 
				1);
	
		for(int i = 0; i < Cube.DIMENSION; i++)
			columnX.data[i][0] = this.getCoordinate(i);
		
		if(M > Cube.DIMENSION) columnX.data[M - 1][0] = 1;

		Matrix columnY = transitionMatrix.times(columnX);
		
		return new Point(columnY.data[0][0], columnY.data[1][0], columnY.data[2][0]);

	}
	
}