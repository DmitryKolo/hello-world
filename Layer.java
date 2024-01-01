import java.awt.Color;
import java.awt.Graphics2D;


public class Layer {
	
	// Fields
	
	public Axis axis;
	public int coordinate;
	public double rotationAngle;
	public Matrix relativeTransitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	public Matrix transitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	
	public Address3D addressNearestCorner = Address3D.NULL;
	public double nearestCornerDeltaZ; // not use?
	
	public Point nearestCorner;
	public Point[] nearestRibsEnd = new Point[Cube.DIMENSION];
	public Vector[] ribVector = new Vector[Cube.DIMENSION];

	
	// Constructor
	
	public Layer(Axis axis, int coordinate){
		
		this.axis = axis;
		this.coordinate = coordinate;
		this.rotationAngle = 0;
		
		//relativeTransitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	}
	
	// Function
	
	public void update(){
		
		caltulateTransitionMatrix();
		
	}
	
	
	public void caltulateRelativeTransitionMatrix(){
		
		double cosA = Math.cos(rotationAngle);
		double sinA = Math.sin(rotationAngle);
		
		switch(axis.index){
		case 0:
			relativeTransitionMatrix.data[0][0] =  1;
			relativeTransitionMatrix.data[1][1] =  cosA;
			relativeTransitionMatrix.data[2][1] =  sinA;
			relativeTransitionMatrix.data[1][2] = -sinA;
			relativeTransitionMatrix.data[2][2] =  cosA;
			break;
		case 1:
			relativeTransitionMatrix.data[0][0] =  cosA;
			relativeTransitionMatrix.data[2][0] = -sinA;
			relativeTransitionMatrix.data[1][1] =  1;
			relativeTransitionMatrix.data[0][2] =  sinA;
			relativeTransitionMatrix.data[2][2] =  cosA;
			break;
		case 2:
			relativeTransitionMatrix.data[0][0] =  cosA;
			relativeTransitionMatrix.data[1][0] =  sinA;
			relativeTransitionMatrix.data[0][1] = -sinA;
			relativeTransitionMatrix.data[1][1] =  cosA;
			relativeTransitionMatrix.data[2][2] =  1;
			break;
		default:
			break;
		}
		
		relativeTransitionMatrix.data[axis.index][Cube.DIMENSION] = coordinate;
	
	}	
	
	
	public void caltulateTransitionMatrix(){
		
		caltulateRelativeTransitionMatrix();
		transitionMatrix = axis.cube.transitionMatrixC.squareTimes(relativeTransitionMatrix);
		
		//Address3D addressNearestCorner = Address3D.NULL;
		nearestCornerDeltaZ = 0;
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			double dZ = transitionMatrix.data[i][Cube.DIMENSION - 1];
			addressNearestCorner.setCoordinate(i, (dZ >= 0) ? 1 : 0);
			nearestCornerDeltaZ += Math.abs(dZ) * (Cube.SIZE / 2.0);
		}
		
		nearestCorner = addressNearestCorner.calculatedPoint(transitionMatrix);
		
		Address3D[] addressNearestRibsEnd = new Address3D[Cube.DIMENSION];
		//Point[] nearestRibsEnd = new Point[Cube.DIMENSION];
		//Vector[] ribVector = new Vector[Cube.DIMENSION];
		
		for(int v = 0; v < Cube.DIMENSION; v++){
			
			for(int w = 0; w < Cube.DIMENSION; w++){
				addressNearestRibsEnd[v] = Address3D.NULL;
				if(v == w) 
					addressNearestRibsEnd[v].setCoordinate(w, - addressNearestCorner.getCoordinate(w));
				else 
					addressNearestRibsEnd[v].setCoordinate(w, addressNearestCorner.getCoordinate(w));
			}
		
			nearestRibsEnd[v] = addressNearestRibsEnd[v].calculatedPoint(transitionMatrix); 
			ribVector[v] = new Vector(nearestCorner, nearestRibsEnd[v]);
		}
	}
	
	
	public void draw(Graphics2D g){
		
		for(int i = 0; i < Cube.DIMENSION; i++)
			ribVector[i].draw(g, Color.BLACK);
				
	}

		
}
