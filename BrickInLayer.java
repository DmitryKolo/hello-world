
import java.awt.*;
import java.util.ArrayList;

public class BrickInLayer {
	
	// Fields
	
	public Layer layer;
	public int v, w;
	public Brick brick;
	
	//public Point centre; // use brick.centre
	
	public FractionalAddress nearestCornerAddress;
	public FractionalAddress[] nearestRibEndAddresses = new FractionalAddress[Cube.DIMENSION];
	public FractionalAddress[] nearestTileDiagonalEndAddresses = new FractionalAddress[Cube.DIMENSION];
	
	//public Vector[] nearestRibVector = new Vector[Cube.DIMENSION];
	
	public Point nearestCorner;
	public Point[] nearestRibEnd = new Point[Cube.DIMENSION];
	public Point[] nearestTileDiagonalEnd = new Point[Cube.DIMENSION];	
	
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
		
		//centre = new Point(
		brick.absoluteCentre = new Point(
						new Point(layer.centre, new Vector(v, layer.ribVector[indexV])), 
						new Vector(w, layer.ribVector[indexW]));
		
		caltulateNearestTiles();
	}
	

	public void caltulateNearestTiles(){
		
		nearestCornerAddress = brick.nearestCornerAddress(layer.nearestCornerSignes);
		nearestRibEndAddresses = brick.nearestRibEndAddresses(layer.nearestCornerSignes);
		nearestTileDiagonalEndAddresses = brick.nearestTileDiagonalEndAddresses(layer.nearestCornerSignes);
		
		//nearestCorner = new Point(brick.absoluteCentre, layer.toNearestCornerVector);
		nearestCorner = nearestCornerAddress.calculatedPoint(layer.transitionMatrix);
		
		for(int v = 0; v < Cube.DIMENSION; v++){	
			
			//nearestRibEnd[v] = new Point(nearestCorner, layer.nearestRibVector[v]); 
			nearestRibEnd[v] = nearestRibEndAddresses[v].calculatedPoint(layer.transitionMatrix);
		
			//int w = (v == Cube.DIMENSION - 1) ? 0 : v + 1;
			//nearestTileDiagonalEnd[v] = new Point(nearestRibEnd[v], layer.nearestRibVector[w]);
			nearestTileDiagonalEnd[v] = nearestTileDiagonalEndAddresses[v].calculatedPoint(layer.transitionMatrix);
		}
		
//		for(int v = 0; v < Cube.DIMENSION; v++){
//			
//			int w = (v == Cube.DIMENSION - 1) ? 0 : v + 1;
//			
//			nearestTileDiagonalEnd[v] = new Point(nearestRibEnd[v], nearestRibVector[w]);
//		}
		
//		Address3D nullCornerAddress = new Address3D(0, 0, 0);
//		
//		this.nullCorner = nullCornerAddress.calculatedPoint(transitionMatrix);
//		
//		Address3D[] ribEndAddress = new Address3D[Cube.DIMENSION];
//		Point[] ribEnd = new Point[Cube.DIMENSION];
//		
//		for(int v = 0; v < Cube.DIMENSION; v++){
//			
//			ribEndAddress[v] = new Address3D();
//			
//			for(int w = 0; w < Cube.DIMENSION; w++)
//				if(v == w) 
//					ribEndAddress[v].setCoordinate(w, 1 - ribEndAddress[v].getCoordinate(w));
//				else 
//					ribEndAddress[v].setCoordinate(w, ribEndAddress[v].getCoordinate(w));
//			
//			ribEnd[v] = ribEndAddress[v].calculatedPoint(transitionMatrix); 
//			ribVector[v] = new Vector(nullCorner, ribEnd[v]);
//		}
	}
	
	
	public void draw(Graphics2D g, Color color){
		
		//centre.draw(g);
		//brick.absoluteCentre.draw(g, Color.DARK_GRAY);
		
		Color color1 = Color.ORANGE;
		Color color2 = Color.PINK;
		
	//	if(this.v == -1 && this.w == -1){
			
			nearestCorner.draw(g, Color.BLACK);
			
			nearestRibEnd[0].draw(g, color1); 
			nearestTileDiagonalEnd[0].draw(g, color1);
			nearestRibEnd[1].draw(g, color1); 
			nearestTileDiagonalEnd[1].draw(g, color1);
			nearestRibEnd[2].draw(g, color1); 
			nearestTileDiagonalEnd[2].draw(g, color1);
			
			GamePanel.drawLineInPanel(g, nearestCorner, nearestRibEnd[0], color2);
			GamePanel.drawLineInPanel(g, nearestCorner, nearestRibEnd[1], color2);
			GamePanel.drawLineInPanel(g, nearestCorner, nearestRibEnd[2], color2);
			GamePanel.drawLineInPanel(g, nearestRibEnd[0], nearestTileDiagonalEnd[2], color2);
			GamePanel.drawLineInPanel(g, nearestTileDiagonalEnd[2], nearestRibEnd[1], color2);
			GamePanel.drawLineInPanel(g, nearestRibEnd[1], nearestTileDiagonalEnd[0], color2);
			GamePanel.drawLineInPanel(g, nearestTileDiagonalEnd[0], nearestRibEnd[2], color2);
			GamePanel.drawLineInPanel(g, nearestRibEnd[2], nearestTileDiagonalEnd[1], color2);
			GamePanel.drawLineInPanel(g, nearestTileDiagonalEnd[1], nearestRibEnd[0], color2);
			
	//	}

	}
	
}
	
