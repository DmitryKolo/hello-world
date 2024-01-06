
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
		
		Matrix rowX = new Matrix(this);

		Matrix rowX1 = rowX.transitionRow(transitionMatrix);
				
		return rowX1.createPoint();
	}
	
}