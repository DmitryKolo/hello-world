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
	
	public boolean stabilizeMode = false; // outdated?
	
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
			double speed = 0.01; 
			layer[i] = new Layer(this, i - 1, speed, 0);
		}
	}
	
	
	public void setRotatable(Address3D quantitiesToRotate){
		
		rotatable = true; // outdated?
		cube.rotatableAxisIndex = index;
		if(getRotatable())
			for(int i = 0; i < Cube.SIZE; i++) 
				layer[i].quantityToTurn += quantitiesToRotate.getCoordinate(i);
			
		else
			for(int i = 0; i < Cube.SIZE; i++) {
				double speed = 0.01; 
				layer[i] = new Layer(this, i - 1, speed, quantitiesToRotate.getCoordinate(i));
			}
	}
	
	
	public boolean getRotatable(){
		
		for(int i = 0; i < Cube.SIZE; i++)
			if(layer[i] != null && layer[i].quantityToTurn != 0) return true;
		return false;
	}
	
	
	public void stabilize(){
		rotatable = false;
		// stabilize layers
		
	}
	
	public String vectorCoords(){

		return vector.coords();
		
	}

	public void draw(Graphics2D g){
		
		for(Layer currentLayer : layer){
			currentLayer.draw(g);
		}
		
		Tile.drawCollection(g, cube.tileCollection);
	}

	
}