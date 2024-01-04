import java.awt.Color;
import java.awt.Graphics2D;


public class Layer {
	
	// Fields
	
	public Axis axis;
	public int coordinate;
	public Point centre;
	
	public double rotationAngle;
	public Matrix relativeTransitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	public Matrix transitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	
	public Address3D nearestCornerSignes = new Address3D();
	public double nearestCornerDeltaZ; // not use?
	
	public Point nullCornerCentre;
	//public Point[] ribEnd = new Point[Cube.DIMENSION];
	public Vector[] ribVector = new Vector[Cube.DIMENSION];
	
//	public Point nearestCorner;
//	public Point[] nearestRibEnd = new Point[Cube.DIMENSION];
	public Vector[] nearestRibVector = new Vector[Cube.DIMENSION];
//	public Point[] nearestTileDiagonalEnd = new Point[Cube.DIMENSION];
	
	public Vector toNearestCornerVector;
	
	public double speed;
	
	public BrickInLayer[][] brickInLayer = new BrickInLayer[Cube.SIZE][Cube.SIZE];
	
	// Constructor
	
	public Layer(Axis axis, int coordinate, double speed){
		
		this.axis = axis;
		this.coordinate = coordinate;
		this.rotationAngle = 0;
		
		this.speed = speed;
		
		for(int v = 0; v < Cube.SIZE; v++)
			for(int w = 0; w < Cube.SIZE; w++)
				switch(axis.index){
				case 0:
					brickInLayer[v][w] = new BrickInLayer(axis.cube.brick[coordinate + 1][v][w], this, v - 1, w - 1);
					break;
				case 1:
					brickInLayer[v][w] = new BrickInLayer(axis.cube.brick[w][coordinate + 1][v], this, v - 1, w - 1);
					break;
				case 2:
					brickInLayer[v][w] = new BrickInLayer(axis.cube.brick[v][w][coordinate + 1], this, v - 1, w - 1);
					break;
				default:
					break;
				}
	}
	
	// Function
	
	public void update(){
		
		rotationAngle += speed;
		
		caltulateTransitionMatrixAndRibVectors();
		
		centre = new Point(axis.cube.centre, new Vector(coordinate, ribVector[axis.index]));
		
		updateBricks();
		
//		if(coordinate == 1){
//			System.out.println("rotationAngle[1] = " + this.rotationAngle);
//			System.out.println("              layer[1].centre.x = " + this.centre.x + " y = " + this.centre.y);
//			System.out.println("              brickInLayer[1][0][0].centre.x = " + this.brickInLayer[0][0].centre.x + " y = " + this.brickInLayer[0][0].centre.y);
//		}
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
		
		//relativeTransitionMatrix.data[axis.index][Cube.DIMENSION] = coordinate;
	
	}	
	
	
	public void caltulateTransitionMatrixAndRibVectors(){
		
		caltulateRelativeTransitionMatrix();
		//transitionMatrix = axis.cube.transitionMatrixC.times(relativeTransitionMatrix.transpose());
		transitionMatrix = axis.cube.transitionMatrixC.transpose().times(relativeTransitionMatrix);
		
		nearestCornerDeltaZ = 0;
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			double dZ = transitionMatrix.data[Cube.DIMENSION - 1][i];
			//addressNearestCorner.setCoordinate(i, (dZ >= 0) ? 1 : 0);
			nearestCornerSignes.setCoordinate(i, (dZ >= 0) ? 1 : -1);
			nearestCornerDeltaZ += Math.abs(dZ) * (Cube.SIZE / 2.0);
		}
		
//		nearestCorner = addressNearestCorner.calculatedPoint(transitionMatrix);
		
//		Address3D[] addressNearestRibsEnd = new Address3D[Cube.DIMENSION];
//		
//		for(int v = 0; v < Cube.DIMENSION; v++){
//			
//			addressNearestRibsEnd[v] = new Address3D();
//			
//			for(int w = 0; w < Cube.DIMENSION; w++)
//				if(v == w) 
//					//addressNearestRibsEnd[v].setCoordinate(w, 1 - addressNearestCorner.getCoordinate(w));
//					addressNearestRibsEnd[v].setCoordinate(w, - addressNearestCorner.getCoordinate(w));
//				else 
//					addressNearestRibsEnd[v].setCoordinate(w, addressNearestCorner.getCoordinate(w));
//			
////			nearestRibEnd[v] = addressNearestRibsEnd[v].calculatedPoint(transitionMatrix); 
////			nearestRibVector[v] = new Vector(nearestCorner, nearestRibEnd[v]);
//		}
//		
////		for(int v = 0; v < Cube.DIMENSION; v++){
////			
////			int w = (v == Cube.DIMENSION - 1) ? 0 : v + 1;
////			
////			nearestTileDiagonalEnd[v] = new Point(nearestRibEnd[v], nearestRibVector[w]);
////		}
		
		Address3D nullCornerAddress = new Address3D(0, 0, 0);
		
		this.nullCornerCentre = nullCornerAddress.calculatedPoint(transitionMatrix);
		
		Address3D[] ribEndAddress = new Address3D[Cube.DIMENSION];
		Point[] ribEnd = new Point[Cube.DIMENSION];
		
		for(int v = 0; v < Cube.DIMENSION; v++){
			
			ribEndAddress[v] = new Address3D();
			ribEndAddress[v].setCoordinate(v, 1);
//			for(int w = 0; w < Cube.DIMENSION; w++)
//				if(v == w) 
//					ribEndAddress[v].setCoordinate(w, 1);
////					ribEndAddress[v].setCoordinate(w, 1 - ribEndAddress[v].getCoordinate(w));
////				else 
////					ribEndAddress[v].setCoordinate(w, ribEndAddress[v].getCoordinate(w));
			
			ribEnd[v] = ribEndAddress[v].calculatedPoint(transitionMatrix); 
			ribVector[v] = new Vector(nullCornerCentre, ribEnd[v]);
		}
		
		toNearestCornerVector = new Vector(0, 0, 0);

		for(int v = 0; v < Cube.DIMENSION; v++){
			
			nearestRibVector[v] = new Vector(nearestCornerSignes.getCoordinate(v), ribVector[v]);
			if(nearestCornerSignes.getCoordinate(v) == 1){
				toNearestCornerVector = toNearestCornerVector.Plus(ribVector[v]);
				//nearestRibVector[v] = new Vector(1.0, ribVector[v]);
			}
			//else nearestRibVector[v] = new Vector(-1.0, ribVector[v]);
		}
		
		toNearestCornerVector = new Vector(0.5, toNearestCornerVector);
		
	}
	
	
	public void updateBricks(){
		
		for(int v = 0; v < Cube.SIZE; v++)
			for(int w = 0; w < Cube.SIZE; w++){
				brickInLayer[v][w].update();
		}
	}
	
	
	public void draw(Graphics2D g){
		
		if(coordinate == 0){
		
//			for(int v = 0; v < Cube.DIMENSION; v++)
//				ribVector[v].draw(g, Color.BLUE);
			
			//System.out.println("              brickInLayer[1][0][0].centre.x = " + this.brickInLayer[0][0].centre.x + " y = " + this.brickInLayer[0][0].centre.y);
			
			brickInLayer[0][0].draw(g, Color.BLACK);
//			brickInLayer[0][1].draw(g, Color.BLACK);
//			brickInLayer[0][2].draw(g, Color.BLACK);
//			brickInLayer[1][0].draw(g, Color.BLACK);
//			brickInLayer[1][1].draw(g, Color.GRAY);
//			brickInLayer[1][2].draw(g, Color.BLACK);
//			brickInLayer[2][0].draw(g, Color.BLACK);
			brickInLayer[2][1].draw(g, Color.BLACK);
//			brickInLayer[2][2].draw(g, Color.BLACK);
			
//			GamePanel.drawLineInPanel(g, this.nearestCorner, this.nearestRibEnd[0], Color.BLACK);
//			GamePanel.drawLineInPanel(g, this.nearestRibEnd[0], this.nearestTileDiagonalEnd[0], Color.BLACK);
//			
//			GamePanel.drawLineInPanel(g, this.nearestCorner, this.nearestRibEnd[1], Color.BLACK);
//			GamePanel.drawLineInPanel(g, this.nearestRibEnd[1], this.nearestTileDiagonalEnd[1], Color.BLACK);
//			
//			GamePanel.drawLineInPanel(g, this.nearestCorner, this.nearestRibEnd[1], Color.BLACK);
//			GamePanel.drawLineInPanel(g, this.nearestRibEnd[2], this.nearestTileDiagonalEnd[2], Color.BLACK);
			
			//toNearestCornerVector.draw(g, Color.BLACK);
		}
	}

		
}
