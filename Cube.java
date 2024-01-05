
import java.awt.*;
import java.util.*;	

public class Cube {

	// Fields
	
	public final static int DIMENSION = 3;
	public final static int SIZE = 3;
	public final Color[] COLOR = new Color[Cube.DIMENSION * Axis.ENDS_QUANTITY + 1]; 
	
	public Vector[] vector = new Vector[Cube.DIMENSION];
	public Matrix transitionMatrix = new Matrix(DIMENSION, DIMENSION + 1);
	public Matrix transitionMatrixC = new Matrix(DIMENSION, DIMENSION + 1);
	
	public Vector vectorA, vectorB, vectorC; // head on over to Vector[] vector
	public CubeBasis basis;
	
	public int size; // not used?
			
	public Point centre;
	Point[][][] angle = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	
	public Color[][][][] tile = new Color[Cube.DIMENSION][Axis.ENDS_QUANTITY][3][3];
	
	public Brick[][][] brick = new Brick[Cube.SIZE][Cube.SIZE][Cube.SIZE];
	Axis[] axis = new Axis[Cube.DIMENSION];  
	public int rotatableAxisIndex;
	
	//public ArrayList<Block> blockCollection = new ArrayList<Block>();
	public ArrayList<Tile> tileCollection = new ArrayList<Tile>();
	
	private Address3D upperAngle; 
	
	private double speed = 1;
	
	public static boolean shift;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;

	public static boolean rotateI;
	public static boolean rotateJ;
	public static boolean rotateK;

	public static boolean rotateTop;
	
	public static boolean rotateBackCounterWise;

	public boolean stabilizeMode = false;

	// Constructor
	
	public Cube(int size, Point centre, Vector vectorA, Vector vectorB, Vector vectorC){
		
		this.size = size;
		this.centre = centre;
		
		this.basis = new CubeBasis(vectorA, vectorB, vectorC);
		this.vectorA = vectorA; // head on over to Axis, basis
		this.vectorB = vectorB;
		this.vectorC = vectorC;

		initialCubeColorsAndBricks();
		
		for (int i = 0; i < DIMENSION; i++)
			this.axis[i] = new Axis(i, this);
		
		setRotatableAxis(0);
		
		calculateTransitionMatrix();
	
		for (int i = 0; i < angle.length; i++){
			
			double halfsize = size / 2;
			
			double coefI = (i==0 ? -halfsize : halfsize);
			Point pointI = new Point(Point.NULL, new Vector(coefI, basis.vector[0]));				
						
		    for (int j = 0; j < angle[i].length; j++){
		    	
				double coefJ = (j==0 ? -halfsize : halfsize);
				Point pointJ = new Point(pointI, new Vector(coefJ, basis.vector[1]));				
				
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
					double coefK = (k==0 ? -halfsize : halfsize);
			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, basis.vector[2]));
			    	//Point CurrentCorner = this.angle[i][j][k];
					//System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
			    }
		    }
	    }
		
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
	
	public void setRotatableAxis(int indexRotatableAxis){
		
		for(int i = 0; i < Cube.DIMENSION; i++) axis[i].stabilize();
		// for layers creation:
		axis[indexRotatableAxis].setRotatable();
		tileCollection.clear();
	}
	

	public void calculateTransitionMatrix(){
		
		for(int i = 0; i < Cube.DIMENSION; i++)
			for(int j = 0; j < Cube.DIMENSION; j++)
				transitionMatrix.data[i][j] = axis[i].vector.getCoordinate(j);
		
		transitionMatrix.data[0][3] = GamePanel.HEIGHT / 2;
		transitionMatrix.data[1][3] = GamePanel.WIDTH / 2;
			
		calculateTransitionMatrixC();
			
//		transitionMatrix.data[0][0] = vectorA.dx;
//		transitionMatrix.data[0][1] = vectorA.dy;
//		transitionMatrix.data[0][2] = vectorA.dz;
//		transitionMatrix.data[1][0] = vectorB.dx;
//		transitionMatrix.data[1][1] = vectorB.dy;
//		transitionMatrix.data[1][2] = vectorB.dz;
//		transitionMatrix.data[2][0] = vectorC.dx;
//		transitionMatrix.data[2][1] = vectorC.dy;
//		transitionMatrix.data[2][2] = vectorC.dz;
	}

		
	public void calculateTransitionMatrixC(){
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			for(int j = 0; j < Cube.DIMENSION; j++)
				transitionMatrixC.data[i][j] = axis[j].vector.getCoordinate(i);
			transitionMatrixC.data[i][Cube.DIMENSION] = centre.getCoordinate(i);
		}
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
			basis.vector[i].rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
		
		vectorA = basis.vector[0]; // outdated
		vectorB = basis.vector[1]; // outdated
		vectorC = basis.vector[2]; // outdated
		
		calculateTransitionMatrix();
		
	}
	
	
	public void stabilize(){
		
		stabilizeMode = true;
		
	}
	
	
	public void restart(int stratifiedAxis){
		
//		blockCollection.clear();
//		
//		blockCollection.add(new Block(this, stratifiedAxis, 0, -0.017)); 
//		blockCollection.add(new Block(this, stratifiedAxis, 1, -0.032)); 
//		blockCollection.add(new Block(this, stratifiedAxis, 2,  0.018)); 
		
		stabilizeMode = false;
		
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
			rotateV(basis.vector[0], rotateAngle);
			//rotateV(rotateVector, rotateAngle);
		}

		if(rotateJ){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(basis.vector[1], rotateAngle);
		}
		
		if(rotateK){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(basis.vector[2], rotateAngle);
		}
		
		if(rotateTop){
			stabilize();
		}
		
		if(rotateBackCounterWise){
			restart(0);
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
			
			Brick.arrangeCollection(brickCollection, this);
		}
		
		axis[rotatableAxisIndex].update(g);
	}
	

	public void draw(Graphics2D g){
		
		axis[rotatableAxisIndex].draw(g);
			
	}

	
	public Point angleAtAddress(Address3D address){
		
		return angle[address.i][address.j][address.k];
		
	}
		
	
	public Brick getBrickAtAddress(Address3D address){
		
		return brick[address.i][address.j][address.k];	
	}

}