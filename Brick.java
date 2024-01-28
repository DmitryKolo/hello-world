
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
	
	
	public void draw(Graphics2D g, Color color, Layer layer, int v, int w){
	}
	
	public void rotate(int axisIndex){
		
		Color[][] tmpColor = new Color[Cube.SIZE][Axis.ENDS_QUANTITY];
		
		for(int v = 0; v < Cube.SIZE; v++)
			for(int w = 0; w < Axis.ENDS_QUANTITY; w++)
				tmpColor[v][w] = color[v][w];
		
		switch(axisIndex){
			case 0:
				color[2][0] = tmpColor[1][0];
				color[2][1] = tmpColor[1][1];
				color[1][1] = tmpColor[2][0];
				color[1][0] = tmpColor[2][1];
				break;
			case 1:
				color[2][1] = tmpColor[0][0];
				color[2][0] = tmpColor[0][1];
				color[0][0] = tmpColor[2][0];
				color[0][1] = tmpColor[2][1];
				break;
			case 2:
				color[1][0] = tmpColor[0][0];
				color[1][1] = tmpColor[0][1];
				color[0][1] = tmpColor[1][0];
				color[0][0] = tmpColor[1][1];
				break;
			default:
		}
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


}
