
public class Address3D {

	// Fields
		
	public int i;
	public int j;
	public int k;
	
	public final static Address3D NULL = new Address3D(0, 0, 0);

	// Constructor
	
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
		
		Matrix columnX = new Matrix(Cube.DIMENSION + 1, 1);
	
		for(int i = 0; i < Cube.DIMENSION; i++)
			columnX.data[i][0] = this.getCoordinate(i);
		
		columnX.data[Cube.DIMENSION][0] = 1;

		Matrix columnYabs = transitionMatrix.times(columnX);
		
		return new Point(columnYabs.data[0][0], columnYabs.data[1][0], columnYabs.data[2][0]);

	}
	
}