
import java.awt.*;
import java.util.ArrayList;

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
	
	
	public void calculateCentreAndNearestCornerZ(){

		int rotatableAxisIndex = cube.rotatableAxisIndex;
		Axis rotatableAxis = cube.axis[rotatableAxisIndex];
		Layer rotatableLayer = rotatableAxis.layer[getCoordinate(rotatableAxisIndex)];
		Matrix relativeTransitionMatrix = rotatableLayer.relativeTransitionMatrix; // initial calculation of rotatableLayer.relativeTransitionMatrix? // not use?
		Matrix transitionMatrix = rotatableLayer.transitionMatrix; // initial calculation of rotatableLayer.transitionMatrix?

//		Matrix columnX = new Matrix(Cube.DIMENSION + 1, 1);
//		columnX.data[0][0] = i;
//		columnX.data[1][0] = j;
//		columnX.data[2][0] = k;
//		columnX.data[3][0] = 1;

		Address3D thisAddress = new Address3D(i, j, k);
		
//		Matrix columnYrel = relativeTransitionMatrix.times(columnX); // not use?
//		relativeCentre = new Point(columnYrel.data[0][0], columnYrel.data[1][0], columnYrel.data[2][0]); // not use?
		relativeCentre = thisAddress.calculatedPoint(relativeTransitionMatrix); // not use?
		
//		Matrix columnYabs = transitionMatrix.times(columnX);
//		absoluteCentre = new Point(columnYabs.data[0][0], columnYabs.data[1][0], columnYabs.data[2][0]);
		absoluteCentre = thisAddress.calculatedPoint(transitionMatrix); 
		
		nearestCornerZ = absoluteCentre.z + rotatableLayer.nearestCornerDeltaZ; // not used?
	}
	
	
	public static void drawCollection(Graphics2D g, ArrayList<Brick> brickCollection){
		
		//arrangeCollection(blockCollection, g);
		
		Color color = Color.MAGENTA;
				
		for(Brick brick : brickCollection){
			//brick.draw(g, color);
		}
	}
	

	public void draw(Graphics2D g, Color color, Layer layer, int v, int w){
		
//		for(Edge edge : edgeCollection){
//			
//      		edge.draw(g);
//	   		      		
//		}
//	
//		nearestAngle.draw(g, cube.centre, Color.RED, 5);
		

	}
	
	public static void arrangeCollection(ArrayList<Brick> brickCollection, Cube cube){
		
		brickCollection.clear();
		
		// to improve
		
		Address3D nearestBrickAddress = new Address3D(0, 0, 0);
		double nearestBrickNearestCornerZ = cube.getBrickAtAddress(nearestBrickAddress).nearestCornerZ; 
		
		for(int i = 0; i < Cube.SIZE; i++)
			for(int j = 0; j < Cube.SIZE; j++)
				for(int k = 0; k < Cube.SIZE; k++){
					Address3D currentBrickAddress = new Address3D(i, j, k);
					Brick currentBrick = cube.getBrickAtAddress(currentBrickAddress);
					double currentBrickNearestCornerZ = currentBrick.nearestCornerZ; 
					if(currentBrickNearestCornerZ > nearestBrickNearestCornerZ){
						nearestBrickAddress = currentBrickAddress;
						nearestBrickNearestCornerZ = currentBrickNearestCornerZ; 
					};
				}
		
		for(int i = 0; i < Cube.SIZE; i++)
			for(int j = 0; j < Cube.SIZE; j++)
				for(int k = 0; k < Cube.SIZE; k++){
					Address3D currentBrickAddress = new Address3D(i, j, k);
					Brick currentBrick = cube.getBrickAtAddress(currentBrickAddress);
					double currentBrickNearestCornerZ = currentBrick.nearestCornerZ; 
					if(currentBrickNearestCornerZ == nearestBrickNearestCornerZ){
						brickCollection.add(currentBrick); 
					};
				}
		
			
		for(Brick brick : brickCollection){
			//brick.draw(g, color);
		}
	}
}
