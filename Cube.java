
import java.awt.*;
import java.util.*;	

public class Cube {

	// Fields
	
	public final static int DIMENSION = 3;
	public final static int SIZE = 3;
	public final Color[] COLOR = new Color[Cube.DIMENSION * Axis.ENDS_QUANTITY + 1]; 
	
	public Matrix transitionMatrix = new Matrix(DIMENSION + 1, DIMENSION + 1);
	
	public int size; // not used?
			
	public Point centre;
	Address3D nearestCornerAddress;
	FrontLeftTopInBasis frontTopLeft;
	
	public Color[][][][] tile = new Color[Cube.DIMENSION][Axis.ENDS_QUANTITY][3][3];
	
	public Brick[][][] brick = new Brick[Cube.SIZE][Cube.SIZE][Cube.SIZE];
	Axis[] axis = new Axis[Cube.DIMENSION];  
	public int rotatableAxisIndex;
	
	public ArrayList<Tile> tileCollection = new ArrayList<Tile>();
	
	// private Address3D upper_Angle; 
	
	private double speed = 1;
	
	public static boolean shift;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;

	public static boolean rotateI;
	public static boolean rotateJ;
	public static boolean rotateK;

	public static boolean rotateTopLP;
	public static boolean rotateTopL0;
	public static boolean rotateTopLM;
	
	public static boolean rotateTopRP;
	public static boolean rotateTopR0;
	public static boolean rotateTopRM;

	public static boolean[][] rotateF = new boolean[Cube.SIZE][Axis.ENDS_QUANTITY];

	public static boolean rotateBackCounterWise;

	public static boolean stabilizeMode = false;
	public static boolean Rebuilding = false;

	// Constructor
	
	public Cube(int size, Point centre, Vector[] vectors){
		
		this.size = size;
		this.centre = centre;
		
//		Vector vectorA = vectors[0]; // head on over to Axis, basis
//		Vector vectorB = vectors[1];
//		Vector vectorC = vectors[2];

//		this.basis = new CubeBasis(vectorA, vectorB, vectorC);
//		this.vectorA = vectorA; // head on over to Axis, basis
//		this.vectorB = vectorB;
//		this.vectorC = vectorC;

		initialCubeColorsAndBricks();
		
		for (int i = 0; i < DIMENSION; i++)
			this.axis[i] = new Axis(i, this, vectors[i]);
		
		setRotatableAxis(0);
		
		calculateTransitionMatrix();
	
//		for (int i = 0; i < angle.length; i++){
//			
//			double halfsize = size / 2;
//			
//			double coefI = (i==0 ? -halfsize : halfsize);
//			Point pointI = new Point(Point.NULL, new Vector(coefI, vectors[0]));				
//						
//		    for (int j = 0; j < angle[i].length; j++){
//		    	
//				double coefJ = (j==0 ? -halfsize : halfsize);
//				Point pointJ = new Point(pointI, new Vector(coefJ, vectors[1]));				
//				
//			    for (int k = 0; k < angle[i][j].length; k++){
//			    	
//					double coefK = (k==0 ? -halfsize : halfsize);
//			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, vectors[2]));
//			    	//Point CurrentCorner = this.angle[i][j][k];
//					//System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
//			    }
//		    }
//	    }
		
		for(int i = 0; i < Cube.DIMENSION; i++)
		    for (int n = 0; n < Axis.ENDS_QUANTITY; n++)
			    for (int j = 0; j < size; j++)
				    for (int k = 0; k < size; k++) 
    					tile[i][n][j][k] = colorINJK(i, n, j, k);
		
		stabilizeMode = false;
		
//		blockCollection.add(new Block(this, 2, 0, 0.00270)); 
//		blockCollection.add(new Block(this, 2, 1, 0.0100)); 
//		blockCollection.add(new Block(this, 2, 2, 0.0308)); 

	}
			
	// Functions
	
	public void calculateTransitionMatrix(){
		
		for(int j = 0; j < Cube.DIMENSION; j++){
			for(int i = 0; i < Cube.DIMENSION; i++)
				transitionMatrix.data[j][i] = axis[j].vector.getCoordinate(i);
			transitionMatrix.data[Cube.DIMENSION][j] = centre.getCoordinate(j);
		}
		
		transitionMatrix.data[Cube.DIMENSION][Cube.DIMENSION] = 1;
	}

		
	public void initialCubeColorsAndBricks(){
		
		COLOR[0] = Color.RED;
		COLOR[1] = Color.CYAN; // YELLOW // LIGHT_GRAY
		COLOR[2] = Color.GRAY;
		COLOR[3] = Color.GREEN;
		COLOR[4] = Color.MAGENTA;
		COLOR[5] = Color.ORANGE;
		COLOR[6] = Color.WHITE;
				
		for (int i = 0; i < Cube.SIZE; i++)
			    for (int j = 0; j < Cube.SIZE; j++)
				    for (int k = 0; k < Cube.SIZE; k++)
						brick[i][j][k] = new Brick(this, i - 1, j - 1, k - 1);
				    
		initialCubeBricksColor();
	}

	
	public void initialCubeBricksColor(){

		for(int v = 0; v < Cube.SIZE; v++)
			for(int w = 0; w < Cube.SIZE; w++){

				brick[0][v][w].color[0][0] = COLOR[0];
				brick[0][v][w].color[0][1] = COLOR[6];
				brick[1][v][w].color[0][0] = COLOR[6];
				brick[1][v][w].color[0][1] = COLOR[6];
				brick[2][v][w].color[0][0] = COLOR[6];
				brick[2][v][w].color[0][1] = COLOR[3];
						
				brick[v][0][w].color[1][0] = COLOR[1];
				brick[v][0][w].color[1][1] = COLOR[6];
				brick[v][1][w].color[1][0] = COLOR[6];
				brick[v][1][w].color[1][1] = COLOR[6];
				brick[v][2][w].color[1][0] = COLOR[6];
				brick[v][2][w].color[1][1] = COLOR[4];
						
				brick[v][w][0].color[2][0] = COLOR[2];
				brick[v][w][0].color[2][1] = COLOR[6];
				brick[v][w][1].color[2][0] = COLOR[6];
				brick[v][w][1].color[2][1] = COLOR[6];
				brick[v][w][2].color[2][0] = COLOR[6];
				brick[v][w][2].color[2][1] = COLOR[5];
						
			}
	}
	
	
	public Color colorINJK(int i, int n, int j, int k){
		
    	int colorNumber = i * 2 + n;
    	
    	switch(colorNumber){
	    	case 0:
	    		return Color.CYAN;
	    	case 1:
	    		return Color.GREEN;
	    	case 2:
	    		return Color.MAGENTA;
	    	case 3:
	    		return Color.ORANGE;
	    	case 4:
	    		return Color.PINK;
	    	case 5:
	    		return Color.RED;
	    	case 6:
	    		return Color.YELLOW;
	    	default:
	    		return Color.WHITE;
	   	}
    }

	
	public Color colorINJK_random(int i, int n, int j, int k){
	
		double random = Math.random() * 6;
		int colorNumber = ((int) random) % 6;
		
		switch(colorNumber){
	    	case 0:
	    		return Color.CYAN;
	    	case 1:
	    		return Color.GREEN;
	    	case 2:
	    		return Color.MAGENTA;
	    	case 3:
	    		return Color.ORANGE;
	    	case 4:
	    		return Color.PINK;
	    	case 5:
	    		return Color.RED;
	    	case 6:
	    		return Color.YELLOW;
	    	default:
	    		return Color.WHITE;
	   	}
	}

			
	public void rotateV(Vector rotateAxis, double rotateAngle){
		
		Vector normalAxis = new Vector(rotateAxis);
		
		for(int i = 0; i < Cube.DIMENSION; i++)			
			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
		
		calculateTransitionMatrix();
		
	}
	
	
	public void stabilize(){
		
		stabilizeMode = true;
		axis[rotatableAxisIndex].stabilizeMode = true;
		
		sayVectorsCoords();
		
	}
	
	
	public void restart(){
		
//		blockCollection.clear();
//		
//		blockCollection.add(new Block(this, stratifiedAxis, 0, -0.017)); 
//		blockCollection.add(new Block(this, stratifiedAxis, 1, -0.032)); 
//		blockCollection.add(new Block(this, stratifiedAxis, 2,  0.018)); 
		
	}	
		
	
	public void rotateEdge(int axisIndex, int layerCoord){
		
		Color[][][][][] tmpColor = colorShot();
						
		for(int i = 0; i < Cube.SIZE; i++)
			for(int j = 0; j < Cube.SIZE; j++)
			{
				Address3D address = Address3D.addressForAxis(axisIndex, layerCoord, i, j);
				Address3D rotatedAddress = address.rotatedAddressForAxis(axisIndex, 1);
				Brick currentBrick = brick[address.getCoordinate(0)][address.getCoordinate(1)][address.getCoordinate(2)];
				
				for(int v = 0; v < Cube.SIZE; v++)
					for(int w = 0; w < Axis.ENDS_QUANTITY; w++)
						currentBrick.color[v][w] = 
								tmpColor[rotatedAddress.getCoordinate(0)][rotatedAddress.getCoordinate(1)][rotatedAddress.getCoordinate(2)]
										[v][w];
				
				currentBrick.rotate(axisIndex);
			}
		
					
	Cube.rotateBackCounterWise = false;
		
	}	
	
	
	public Color[][][][][] colorShot(){
		
		Color[][][][][] colorShot = new Color[Cube.SIZE][Cube.SIZE][Cube.SIZE][Cube.SIZE][Axis.ENDS_QUANTITY];
		
		for(int i = 0; i < Cube.SIZE; i++)
			for(int j = 0; j < Cube.SIZE; j++)
				for(int k = 0; k < Cube.SIZE; k++)
					for(int v = 0; v < Cube.SIZE; v++)
						for(int w = 0; w < Axis.ENDS_QUANTITY; w++)
							colorShot[i][j][k][v][w] = brick[i][j][k].color[v][w];
						
		return colorShot;
	}
	
	
	public void update(Graphics2D g, ArrayList<Brick> brickCollection){
		
		double rotateAngle = speed/20;

		if(up){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(1, 0, 0), rotateAngle);
		}

		if(left){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(0, 1, 0), rotateAngle);
		}

		if(right){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(0, 0, 1), rotateAngle);
		}
		
		if(down){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(1, 0, 0), rotateAngle);
		}
		
		if(rotateI){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(axis[0].vector, rotateAngle);
			//rotateV(rotateVector, rotateAngle);
		}

		if(rotateJ){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(axis[1].vector, rotateAngle);
		}
		
		if(rotateK){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(axis[2].vector, rotateAngle);
		}
		
//		if(rotateTop){
//			//rotateBricks0(0);
//		}
		
		if(rotateBackCounterWise){
			
			frontTopLeft = new FrontLeftTopInBasis(this);
			
//			System.out.println("front: " + frontTopLeft.front);
//			System.out.println("left:  " + frontTopLeft.left);
//			System.out.println("top:   " + frontTopLeft.top);
			
			if(frontTopLeft.top >= Cube.DIMENSION) 
				rotateEdge(frontTopLeft.top - Cube.DIMENSION, 2);
			else
				rotateEdge(frontTopLeft.top, 0);
			
		}
		
		if(rotateTopLP){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.top, -1, -1);
			rotateTopLP = false;
		}
		if(rotateTopL0){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.top, 0, -1);
			rotateTopL0 = false;
		}
		if(rotateTopLM){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.top, 1, -1);
			rotateTopLM = false;
		}
		if(rotateTopRP){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.top, -1, 1);
			rotateTopRP = false;
		}
		if(rotateTopR0){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.top, 0, 1);
			rotateTopR0 = false;
		}
		if(rotateTopRM){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.top, 1, 1);
			rotateTopRM = false;
		}
		
		if(stabilizeMode){
			stabilize();
		}
		
		if(rotateF[0][0]){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.front, -1, 1);
			rotateF[0][0] = false;
		}
		if(rotateF[1][0]){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.front, 0, 1);
			rotateF[1][0] = false;
		}
		if(rotateF[2][0]){
			frontTopLeft = new FrontLeftTopInBasis(this);
			moveLayers(frontTopLeft.front, 1, 1);
			rotateF[2][0] = false;
		}
	
		
		if(up || left || right || down || rotateI || rotateJ || rotateK){
			
//			this.upperAngle = this.upperAngleAddress();
//			for (int i = 0; i < angle.length; i++){
//			    for (int j = 0; j < angle[i].length; j++){
//				    for (int k = 0; k < angle[i][j].length; k++){
//				    	Point CurrentCorner = this.angle[i][j][k];
//						System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
//					}
//				}
//			}
			
//			Block.updateCollection(blockCollection);
			
			//axis[rotatableAxisIndex].update();
			
			//Brick.arrangeCollection(brickCollection, this);
		}
		
		axis[rotatableAxisIndex].update(g);
	}
	

	public void moveLayers(int axisIndex, int layersPosition, int rotateQuantity){
		
		if(axisIndex >= Cube.DIMENSION){
			axisIndex -= Cube.DIMENSION;
			layersPosition = -layersPosition;
			rotateQuantity = -rotateQuantity;
		}
	
//		if(axisIndex == 1){
//			layersPosition = -layersPosition;
//		}
//		if(axisIndex == 2){
//			rotateQuantity = -rotateQuantity;
//		}
		
		int layersCoord = layersPosition + 1;

		Address3D quantitiesToRotate = new Address3D();
		quantitiesToRotate.setCoordinate(layersCoord, rotateQuantity);
		
		setRotatableAxis(axisIndex, quantitiesToRotate);
		
	}
	
	
	public void setRotatableAxis(int indexRotatableAxis){
		
		for(int i = 0; i < Cube.DIMENSION; i++) axis[i].stabilize();
		// for layers creation:
		axis[indexRotatableAxis].setRotatable();
		tileCollection.clear();
	}
	
	
	public void setRotatableAxis(int indexRotatableAxis, Address3D quantitiesToRotate){
		
		// for(int i = 0; i < Cube.DIMENSION; i++) axis[i].stabilize();
		
		for(int i = 0; i < Cube.DIMENSION; i++) 
			if(i != indexRotatableAxis && axis[i].getRotatable()) return;
				
		axis[indexRotatableAxis].setRotatable(quantitiesToRotate);
		tileCollection.clear();
	}
	
	
	public void draw(Graphics2D g){
		
		axis[rotatableAxisIndex].draw(g);
		
		Point nearestCorner = nearestCorner();
		
		GamePanel.drawPointInPanel(g, nearestCorner, Color.RED, 25);
		
		double vectorCoef = Cube.SIZE * Cube.SIZE;
		FrontLeftTopInBasis frontTopLeft = new FrontLeftTopInBasis(this);

		
		for(int i = 0; i < Cube.DIMENSION; i++){
			
			int axisIndex;
			Color color;
				
			switch(i){
			case 0:
				axisIndex = frontTopLeft.front;
				color = Color.BLACK;
				break;
			case 1:
				axisIndex = frontTopLeft.left;
				color = Color.GREEN;
				break;
			case 2:
				axisIndex = frontTopLeft.top;
				color = Color.MAGENTA;
				break;
			default:
				axisIndex = 2 * Cube.DIMENSION;
				color = Color.ORANGE;
				break;
		}
			
		if(axisIndex >= Cube.DIMENSION) axisIndex -= Cube.DIMENSION;
		
		Vector vector = new Vector(vectorCoef, axis[axisIndex].vector);
		
		GamePanel.drawVectorInPanel(g, nearestCorner, vector, color);
			
		}

			
	}


	public Brick getBrickAtAddress(Address3D address){
		
		return brick[address.i][address.j][address.k];	
	}
	
	
	public String vectorCoords(int axisIndex){

		return axis[axisIndex].vectorCoords();
		
	}

	
	public void sayVectorsCoords(){

		String str = "";
		
		double maxAbsY = 0;
		int axisIndexMaxAbsY = 0;
		
		for(int i = 0; i < Cube.DIMENSION; i++)
			if(axis[i].vector.dy > maxAbsY){
				maxAbsY = axis[i].vector.dy;
				axisIndexMaxAbsY = i;
			}

		for(int i = 0; i < Cube.DIMENSION; i++){
			str = ( str == "" ? "" : str + ", " )
					+ ( i != axisIndexMaxAbsY ? "" : ( axis[i].vector.dy > 0 ? "-" : "" ) + "T" )
					+ vectorCoords(i);
		}

		System.out.println(str);
		
	}

	
	public Point nearestCorner(){
		
		Point nearestCorner = new Point(this.centre, Vector.NULL);
		
		//FrontLeftTopInBasis frontTopLeft = new FrontLeftTopInBasis(this);
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			
			Vector vector = axis[i].vector;
			
			int vectorSignum = (int) Math.signum(vector.dz);
			
			this.nearestCornerAddress.setCoordinate(i, vectorSignum);
			
			double vectorCoefToCorner = vectorSignum * Cube.SIZE / 2.0;
			nearestCorner = new Point(nearestCorner, new Vector(vectorCoefToCorner, vector));
		}
			
		return nearestCorner;
		
	}
}