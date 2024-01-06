
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Layer {
	
	// Fields
	
	public Axis axis;
	public int coordinate;
	public Point centre;
	
	public double rotationAngle;
	
	public Matrix fullRelativeTransitionMatrix = new Matrix(Cube.DIMENSION + 1, Cube.DIMENSION + 1);
	public Matrix transitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	public Matrix fullTransitionMatrix = new Matrix(Cube.DIMENSION + 1, Cube.DIMENSION + 1);
	
	public Address3D nearestCornerSignes = new Address3D();
	
	public Point nullCornerCentre;
	//public Point[] ribEnd = new Point[Cube.DIMENSION];
	public Vector[] ribVector = new Vector[Cube.DIMENSION];
	
//	public Point nearestCorner;
//	public Point[] nearestRibEnd = new Point[Cube.DIMENSION];
	public Vector[] nearestRibVector = new Vector[Cube.DIMENSION];
//	public Point[] nearestTileDiagonalEnd = new Point[Cube.DIMENSION];
	
	public Vector toNearestCornerVector;
	
	public double speed;
	
	public BrickInLayer[][] brickInLayer = new BrickInLayer[Cube.SIZE][Cube.SIZE]; // turn to brickCollection
	
	public ArrayList<BrickInLayer> brickCollection = new ArrayList<BrickInLayer>();
	public ArrayList<Tile> tileCollection = new ArrayList<Tile>();
	
	// Constructor
	
	public Layer(Axis axis, int coordinate, double speed){
		
		this.axis = axis;
		this.coordinate = coordinate;
		this.rotationAngle = 0;
		
		this.speed = speed;
		
		for(int v = 0; v < Cube.SIZE; v++)
			for(int w = 0; w < Cube.SIZE; w++){
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
				brickCollection.add(brickInLayer[v][w]);
			}
	}
	
	// Function
	
	public void update(){
		
		rotationAngle += speed;
		
		caltulateTransitionMatrixAndRibVectors();
		
		centre = new Point(axis.cube.centre, new Vector(coordinate, ribVector[axis.index]));
		
		updateBricksAndTiles();
	}
	
	
	public void caltulateRelativeTransitionMatrix(){
		
		double cosA = Math.cos(rotationAngle);
		double sinA = Math.sin(rotationAngle);
		
		int axisIndexI = axis.index;
		int axisIndexJ = (axisIndexI + 1 == Cube.DIMENSION) ? 0 : axisIndexI + 1;
		int axisIndexK = (axisIndexJ + 1 == Cube.DIMENSION) ? 0 : axisIndexJ + 1;
		
		fullRelativeTransitionMatrix.data[axisIndexI][axisIndexI] =  1;
		fullRelativeTransitionMatrix.data[axisIndexJ][axisIndexJ] =  cosA;
		fullRelativeTransitionMatrix.data[axisIndexJ][axisIndexK] =  sinA;
		fullRelativeTransitionMatrix.data[axisIndexK][axisIndexJ] = -sinA;
		fullRelativeTransitionMatrix.data[axisIndexK][axisIndexK] =  cosA;
		fullRelativeTransitionMatrix.data[Cube.DIMENSION][Cube.DIMENSION] = 1;
	}	
	
	
	public void caltulateTransitionMatrixAndRibVectors(){
		
		caltulateRelativeTransitionMatrix();

		fullTransitionMatrix = fullRelativeTransitionMatrix.times(axis.cube.transitionMatrix);
		
		//nearestCornerDeltaZ = 0;
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			double dZ = fullTransitionMatrix.data[i][Cube.DIMENSION - 1]; // new matrix
			nearestCornerSignes.setCoordinate(i, (dZ >= 0) ? 1 : -1);
			//nearestCornerDeltaZ += Math.abs(dZ) * (Cube.SIZE / 2.0);
		}
		
		Address3D nullCornerAddress = new Address3D(0, 0, 0);
		
		this.nullCornerCentre = nullCornerAddress.calculatedPoint(fullTransitionMatrix); // ? outdated matrix - new matrix
		
		Address3D[] ribEndAddress = new Address3D[Cube.DIMENSION];
		Point[] ribEnd = new Point[Cube.DIMENSION];
		
		for(int v = 0; v < Cube.DIMENSION; v++){
			
			ribEndAddress[v] = new Address3D();
			ribEndAddress[v].setCoordinate(v, 1);

			ribEnd[v] = ribEndAddress[v].calculatedPoint(fullTransitionMatrix); 
			ribVector[v] = new Vector(nullCornerCentre, ribEnd[v]);
		}
		
//		double cos01 = ribVector[0].cos(ribVector[1]);
//		double cos02 = ribVector[0].cos(ribVector[2]);
//		double cos12 = ribVector[1].cos(ribVector[2]);
//		
//		double cos_01 = axis.cube.axis[0].vector.cos(axis.cube.axis[1].vector);
//		double cos_02 = axis.cube.axis[0].vector.cos(axis.cube.axis[2].vector);
//		double cos_12 = axis.cube.axis[1].vector.cos(axis.cube.axis[2].vector);
//		
//		double cosF1 = axis.cube.axis[0].vector.cos(ribVector[0]); //axis.cube.axis[2].vector.length()
//		double cosF2 = axis.cube.axis[1].vector.cos(ribVector[1]);
//		double cosF3 = axis.cube.axis[2].vector.cos(ribVector[2]);
		
		toNearestCornerVector = new Vector(0, 0, 0);

		for(int v = 0; v < Cube.DIMENSION; v++){
			
			nearestRibVector[v] = new Vector(- nearestCornerSignes.getCoordinate(v), ribVector[v]);
			if(nearestCornerSignes.getCoordinate(v) == 1){
				toNearestCornerVector = toNearestCornerVector.plus(ribVector[v]);
				//nearestRibVector[v] = new Vector(1.0, ribVector[v]);
			}
			//else nearestRibVector[v] = new Vector(-1.0, ribVector[v]);
		}
		
		toNearestCornerVector = new Vector(0.5, toNearestCornerVector);
		
	}
	
	
	public void updateBricksAndTiles(){
		
//		tileCollection.clear();

		for(BrickInLayer currentBrick : brickCollection){
			
			currentBrick.update();
			
			for(int i = 0; i < Cube.DIMENSION; i++)
				
				//if(currentBrick.v==1 && currentBrick.w==1) //  && ( i==1 && currentBrick.layer.coordinate == -1 || i ==0)) // && currentBrick.layer.coordinate == 0))
//				if ( (currentBrick.w == 1 && currentBrick.v == -1) && (currentBrick.layer.coordinate == 1) && i == 1
//				|| (currentBrick.w == 0 && currentBrick.v == -1) && (currentBrick.layer.coordinate == 1) && i == 2 )
				
//				if ( currentBrick.layer.coordinate == 1 
//					&& currentBrick.v == -1 
//					&& ( (i == 2 && currentBrick.w == 0 ) || ( i == 10 && currentBrick.w <= 0) || i == 0 && currentBrick.w == 1)
//				  )
				
//				if ( 
//						( currentBrick.layer.coordinate == 1 
//							&& ( (i == 2 && currentBrick.w == 0 ) || ( i == 1 && currentBrick.w == -1) ) )
//						|| ( currentBrick.layer.coordinate == 0 
//							&& ( (i == 2 && currentBrick.w == 0 ) || ( i == 1 && currentBrick.w == -1) ) )
//					)
				
//				if ( 
//						( currentBrick.layer.coordinate == 1 
//							&& ( ( i == 2 && currentBrick.w == 0 && currentBrick.v == 19 ) || ( i == 1 && currentBrick.w == -1 && currentBrick.v == -1 ) ) )
//						|| ( currentBrick.layer.coordinate == 0 
//							&& i == 2 && currentBrick.w == 0 && currentBrick.v >= 0
//							)
//					)
				
//				if ( 
//							( currentBrick.layer.coordinate == 0 && i == 1 && currentBrick.w == 0 && currentBrick.v <= 0 )
//						||  ( currentBrick.layer.coordinate == 0 && i == 0 && currentBrick.w == 0 && currentBrick.v == 0 )
//					)
				
//				if ( 
//						( currentBrick.layer.coordinate == 0 && i == 1 && currentBrick.w == 0 && currentBrick.v <= 0 )
//					||  ( currentBrick.layer.coordinate == 0 && i == 0 && currentBrick.w == 0 )
//				)
				
				if ( 
				( currentBrick.layer.coordinate == 1 )
		)

					axis.cube.tileCollection.add(new Tile(currentBrick, i));
		}
		
	}
	
	
	public void draw(Graphics2D g){
		
		for(BrickInLayer currentBrick : brickCollection){}
			//currentBrick.draw(g, Color.BLACK);
	}

		
}
