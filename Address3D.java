
public class Address3D {

	// Fields
		
	public int i;
	public int j;
	public int k;
	
	// Constructor
	
	public Address3D(){
		
		this.i = 0; 
		this.j = 0; 
		this.k = 0;
		
	}	
	
	public Address3D(int i, int j, int k){
		
		this.i = i; 
		this.j = j; 
		this.k = k;
		
	}	
	
	// Functions
	
	public int getCoordinate(int coordinateIndex){
		
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
	
	public void setCoordinate(int coordinateIndex, int coordinateValue){
	
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
		
		Matrix rowX = new Matrix(this);

//		int M = transitionMatrix.getM(); 
//		Matrix columnX = new Matrix(M,
//				//Cube.DIMENSION + 1, 
//				1);
//		for(int i = 0; i < Cube.DIMENSION; i++)
//			columnX.data[i][0] = this.getCoordinate(i);
//		if(M > Cube.DIMENSION) columnX.data[M - 1][0] = 1;
//		Matrix columnY = transitionMatrix.times(columnX);
//		return new Point(columnY.data[0][0], columnY.data[1][0], columnY.data[2][0]);
		
//		Matrix columnX = rowX.transpose();
//		Matrix columnY = transitionMatrix.times(columnX);
		Matrix rowX1 = rowX.transitionRow(transitionMatrix);
//		Matrix columnY = rowX1.transpose();
//		return new Point(columnY.data[0][0], columnY.data[1][0], columnY.data[2][0]);
		
		return rowX1.createPoint();
	}
	
}