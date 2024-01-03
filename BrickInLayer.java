
import java.awt.*;
import java.util.ArrayList;

public class BrickInLayer {
	
	// Fields
	
	public Layer layer;
	public int v, w;
	public Brick brick;
	
	public Point centre;
	
	// Constructor
	
	public BrickInLayer(Brick brick, Layer layer, int v, int w){
		
		this.layer = layer;
		this.v = v;
		this.w = w;
		this.brick = brick;
	}
	
	// Functions
	
	public int getCoordinate(int coordinateIndex){
		
		switch(coordinateIndex){
		
			case 0: 
				return this.v; 
			case 1: 
				return this.w;
			default:
				throw new RuntimeException("Illegal coordinate index.");
		
		}	
	}
	

	public void update(){
		
		int indexV = layer.axis.indexV;
		int indexW = layer.axis.indexW;
		
		centre = new Point(
						new Point(layer.centre, new Vector(v - 1, layer.ribVector[indexV])), 
						new Vector(w - 1, layer.ribVector[indexW]));
		
		//if(layer.coordinate == 1 && v == -1 && w == -1) 
			//System.out.println("              brick[" + brick.i + "][" + brick.j + "][" + brick.k + "].centre.x = " + this.centre.x + " y = " + this.centre.y);
	}
	
	
	public void draw(Graphics2D g, Color color){
		
		centre.draw(g);
//		nearestAngle.draw(g, cube.centre, Color.RED, 5);

	}
	
}
	
