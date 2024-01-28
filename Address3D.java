
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

	public static Address3D addressForAxis(int axisIndex, int layerCoord, int i, int j){
		
		switch(axisIndex){
		case 0:
			return new Address3D(layerCoord, i, j);
		case 1:
			return new Address3D(i, layerCoord, j);
		case 2:
			return new Address3D(i, j, layerCoord);
		default:
			return new Address3D();
		}
	}
	
	
	public Address3D rotatedAddressForAxis(int axisIndex, int signQuantity){
		
		switch(axisIndex){
		case 0:
			return new Address3D(this.i, this.k, 2 - this.j);
		case 1:
			return new Address3D(2 - this.k, this.j, this.i);
		case 2:
			return new Address3D(this.j, 2 - this.i, this.k);
		default:
			return new Address3D();
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