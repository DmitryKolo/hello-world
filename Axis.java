import java.awt.Graphics2D;
import java.util.ArrayList;


public class Axis {

	// Fields
	
	public Vector vector; // ? or in Cube
	
	public Cube cube;
	public int index;

	public boolean rotatable;
	public Layer[] layer = new Layer[Cube.SIZE];
	public int indexV;
	public int indexW;
	
	public final static int ENDS_QUANTITY = 2;

	//public Edge[] edge = new Edge[Axis.ENDS_QUANTITY]; // endingIndex - beginingIndex + 1]; 
			
	
	// Constructor
	
	public Axis(int number, Cube cube){
		
		this.cube = cube;
		this.index = number;
		
		CubeBasis basis = cube.basis;
		this.vector = new Vector(1, basis.vector[number]);
		
		//this.beginingIndex = beginingIndex;
		//this.endingIndex = endingIndex;
		
		//int size = endingIndex -  beginingIndex + 1;
		
		Vector vector0 = new Vector(1, this.vector);
		Vector vector0cw = new Vector(-1, vector0);
		
		int number1 = number + 1;
		if(number1 == Cube.DIMENSION) number1 = 0;
		Vector vector1 = new Vector(1, basis.vector[number1]);		
		Vector vector1cw = new Vector(-1, vector1);		
		
		int number2 = number1 + 1;
		if(number2 == Cube.DIMENSION) number2 = 0;
		Vector vector2 = new Vector(1, basis.vector[number2]);		
		Vector vector2cw = new Vector(-1, vector2);		
		
//		this.edge[0] = new Edge(cube.size, cube.centre, vector0, vector1, vector2);
//		this.edge[1] = new Edge(cube.size, cube.centre, vector0cw, vector1cw, vector2cw);
		
		switch(index){
		case 0:
			indexV = 1;
			indexW = 2;
			break;
		case 1:
			indexV = 2;
			indexW = 0;
			break;
		case 2:
			indexV = 0;
			indexW = 1;
			break;
		default:
			indexV = 0;
			indexW = 0;
			break;
		}
	}
		
	// Functions
	
	public void update(Graphics2D g){
		
		cube.tileCollection.clear();
		
		for(Layer currentLayer : layer){
			currentLayer.update();
		}
		
		Tile.arrangeCollection(g, cube.tileCollection);
	}
	
	
	public void setRotatable(){
		rotatable = true;
		cube.rotatableAxisIndex = index;
		for(int i = 0; i < Cube.SIZE; i++) {
			double speed = 0;
			switch(i){
			case 0:
				speed = 0.03270;
				break;
			case 1:
				speed = 0.002000; 
				break;
			case 2:
				speed = 0.001080; 
				break;
			default:
				speed = 0.0;
			}
			layer[i] = new Layer(this, i - 1, speed);
		}
	}
	
	
	public void stabilize(){
		rotatable = false;
		// stabilize layers
		
	}
	
	
	public void draw(Graphics2D g){
		
		for(Layer currentLayer : layer){
			currentLayer.draw(g);
		}
		
		Tile.drawCollection(g, cube.tileCollection);
	}

	
}