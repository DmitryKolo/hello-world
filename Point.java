
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Point {

	// Fields
		
	public double x;
	public double y;
	public double z;
	
	public final static Point NULL = new Point(0, 0, 0);

	// Constructor
	
	public Point(double x, double y, double z){
		
		this.x = x; 
		this.y = y; 
		this.z = z;
		
	}	
	
	public Point(Point point, Vector vector){
		
		this.x = point.x + vector.dx; 
		this.y = point.y + vector.dy; 
		this.z = point.z + vector.dz;
		
	}	
	
	// Functions
	
	public double getCoordinate(int coordinateIndex){
		
		switch(coordinateIndex){
		
			case 0: 
				return this.x; 
			case 1: 
				return this.y; 
			case 2: 
				return this.z;
			default:
				throw new RuntimeException("Illegal coordinate index.");
		
		}	
	}
	
	
	public void draw(Graphics2D g, Point origin, Color color, int radius){
		
		GamePanel.drawPoint(g, this, origin, color, radius);
		
	}

	
	public void draw(Graphics2D g, Point origin){
		
		GamePanel.drawPoint(g, this, origin, Color.BLACK, 6);
		
	}

	
	public void draw(Graphics2D g, Color color){
		
		GamePanel.drawPointInPanel(g, this, color, 6);
		
	}

	
	public void rotateXYZ(double rotateAngleX, double rotateAngleY, double rotateAngleZ){
		
		if(rotateAngleX != 0){
			
			double atan2X = Math.atan2(z, y) + rotateAngleX;
			double rX = Math.sqrt(y*y + z*z);
			
			y = Math.cos(atan2X) * rX;
			z = Math.sin(atan2X) * rX;
		}

		if(rotateAngleY != 0){
			
			double atan2Y = Math.atan2(x, z) + rotateAngleY;
			double rY = Math.sqrt(z*z + x*x);
			
			z = Math.cos(atan2Y) * rY;
			x = Math.sin(atan2Y) * rY;
		}

		if(rotateAngleZ != 0){
			
			double atan2Z = Math.atan2(y, x) + rotateAngleZ;
			double rZ = Math.sqrt(x*x + y*y);
			
			x = Math.cos(atan2Z) * rZ;
			y = Math.sin(atan2Z) * rZ;
		}
	
	}	
	
	
	public Proection ProectionOnEdge(Edge edge){
		
		Block block = edge.block;

		Point angle00 = block.angleAtAddress(edge.anglesAddresses[0][0]);
		
		Vector vector1 = edge.vector1;
		Vector vector2 = edge.vector2;
		
		Matrix A = new Matrix(2, 2);
		A.data[0][0] = vector1.dx;
		A.data[1][0] = vector1.dy;
		A.data[0][1] = vector2.dx;
		A.data[1][1] = vector2.dy;
		
		Vector V = new Vector(angle00, this);
		
		Matrix X0 = new Matrix(2, 1);
		X0.data[0][0] = V.dx;
		X0.data[1][0] = V.dy;
		
		Matrix Y0 = A.solve(X0);
		
		double yI = Y0.data[0][0]; 
		double yJ = Y0.data[1][0];
		
		Vector vectorI = new Vector(yI, vector1);
		Vector vectorJ = new Vector(yJ, vector2);
		
		Point pointI = new Point(angle00, vectorI);
		Point pointJ = new Point(angle00, vectorJ);
		
		Point proectionPoint = new Point(pointI, vectorJ);
		
		return new Proection(
			yI >= 0 && yI <= 1 && yJ >= 0 && yJ <= 1, 
			proectionPoint, 
			yI, yJ,
			pointI, pointJ,
			vector1, vector2);

	}
	
}	
