
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
	
	
	public FractionalAddress nearestCornerAddress(Address3D cornerSignes){
		
		FractionalAddress address = new FractionalAddress(this.i, this.j, this.k);
		for(int w = 0; w < Cube.DIMENSION; w++){
			double sign = 0.5;
			address.setCoordinate(w, address.getCoordinate(w) + sign * cornerSignes.getCoordinate(w));
		}
		
		return address;
	}
	
	
	public FractionalAddress[] nearestRibEndAddresses(Address3D cornerSignes){
		
		FractionalAddress[] address = new FractionalAddress[Cube.DIMENSION]; 
		
		for(int v = 0; v < Cube.DIMENSION; v++){
			address[v] =  new FractionalAddress(this.i, this.j, this.k);
			for(int w = 0; w < Cube.DIMENSION; w++){
				double sign = (v == w) ? -0.5 : 0.5;
				address[v].setCoordinate(w, address[v].getCoordinate(w) + sign * cornerSignes.getCoordinate(w));
			}
		}
		
		return address;
	}
	
	
	public FractionalAddress[] nearestTileDiagonalEndAddresses(Address3D cornerSignes){
	
		FractionalAddress[] address = new FractionalAddress[Cube.DIMENSION]; 
		
		for(int v = 0; v < Cube.DIMENSION; v++){
			address[v] =  new FractionalAddress(this.i, this.j, this.k);
			for(int w = 0; w < Cube.DIMENSION; w++){
				double sign = (v == w) ? 0.5 : -0.5;
				address[v].setCoordinate(w, address[v].getCoordinate(w) + sign * cornerSignes.getCoordinate(w));
			}
		}
		
		return address;
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
