import java.awt.Graphics2D;
import java.util.ArrayList;


public class Axis {

	// Fields
	
	public Vector vector; 
	
	public Cube cube;
	public int index;

	public boolean rotatable;
	public Layer[] layer = new Layer[Cube.SIZE];
	public int indexV;
	public int indexW;
	
	public final static int ENDS_QUANTITY = 2;
	
	// Constructor
	
	public Axis(int number, Cube cube, Vector vector){
		
		this.cube = cube;
		this.index = number;
		
		this.vector = vector;
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
				speed = 0.06270;
				break;
			case 1:
				speed = 0.03000; 
				break;
			case 2:
				speed = 0.02080; 
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