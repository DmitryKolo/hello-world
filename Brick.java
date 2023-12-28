
import java.awt.*;

public class Brick {
	
	// Fields
	
	public Cube cube;
	public int i, j, k;
	public Color[][] color = new Color[Cube.DIMENSION][Axis.ENDS_QUANTITY];
	
	public Point relativeCentre;
	public Point absoluteCentre;
	
	public double nearestCornerZ;
	
	// Constructor
	
	public Brick(Cube cube, int i, int j, int k){
		
		this.cube = cube;
		this.i = i - 1;
		this.j = j - 1;
		this.k = k - 1;
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
	
	
	public void calculateRelativeCentre(){

		//cube.basis;
		
		int rotatableAxisIndex = cube.rotatableAxisIndex;
		Axis rotatableAxis = cube.axis[rotatableAxisIndex];
		Layer rotatableLayer = rotatableAxis.layer[getCoordinate(rotatableAxisIndex)];
		Matrix relativeTransitionMatrix = rotatableLayer.relativeTransitionMatrix; // initial calculation of rotatableLayer.relativeTransitionMatrix? // not use?
		Matrix transitionMatrix = rotatableLayer.transitionMatrix; // initial calculation of rotatableLayer.transitionMatrix?

		Matrix columnX = new Matrix(Cube.DIMENSION + 1, 1);
		columnX.data[0][0] = i;
		columnX.data[1][0] = j;
		columnX.data[2][0] = k;
		columnX.data[3][0] = 1;

		Matrix columnYrel = relativeTransitionMatrix.times(columnX); // not use?
		relativeCentre = new Point(columnYrel.data[0][0], columnYrel.data[1][0], columnYrel.data[2][0]); // not use?
		
		Matrix columnYabs = transitionMatrix.times(columnX);
		absoluteCentre = new Point(columnYabs.data[0][0], columnYabs.data[1][0], columnYabs.data[2][0]);
		
		nearestCornerZ = absoluteCentre.z;
	}
	
}
