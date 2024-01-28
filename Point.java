
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
	
	
	public void setCoordinate(int coordinateIndex, double value){
		
		switch(coordinateIndex){
			case 0: 
				this.x = value; 
				break;
			case 1: 
				this.y = value; 
				break;
			case 2: 
				this.z = value;
				break;
			default:
				throw new RuntimeException("Illegal coordinate index.");
		}	
	}
	
	
	public Point plus (double coef, Vector vector){
		
		return new Point(this.x + coef * vector.dx, this.y + coef * vector.dy, this.z + coef * vector.dz);
		
	}	
	
	
	public Point minus (double coef, Vector vector){
		
		return new Point(this.x - coef * vector.dx, this.y - coef * vector.dy, this.z - coef * vector.dz);
		
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
	
	
	public Proection proectionOnTile(Tile tile){
		
		Point point0 = tile.point;
		
		Vector vector = new Vector(point0, this);
		Vector vectorA = tile.vector[0];
		Vector vectorB = tile.vector[1];
		
		Point angle00 = point0;
		
		Vector vector1 = vectorA;
		Vector vector2 = vectorB;
		
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
				proectionPoint, 
				yI > 0.0001 && yI < 0.9999 && yJ > 0.0001 && yJ < 0.9999 && Math.abs(proectionPoint.z - this.z) > 0.0001, 
				proectionPoint.z - this.z < -0.0001,
				proectionPoint.z - this.z > 0.0001,
				yI, yJ,
				pointI, pointJ,
				vector1, vector2);
	}
	

	public Point projectionOnTile(Tile tile){
		
		Point point0 = tile.point;
		
		Vector vector = new Vector(point0, this);
		Vector vectorA = tile.vector[0];
		Vector vectorB = tile.vector[1];
		
		Point angle00 = point0;
		
		Vector vector1 = vectorA;
		Vector vector2 = vectorB;
		
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
		
		return new Point(pointI, vectorJ);
		
	}
	
	
	public static int zCompareInTwoSegments(Point pointA0, Point pointA1, Point pointB0, Point pointB1){
		
		final double roundOff = 0.00001;
		double X, Y, ZatA, ZatB;
		
		double XA0 = pointA0.x;
		double XA1 = pointA1.x;
		double XB0 = pointB0.x;
		double XB1 = pointB1.x;
		
		double YA0 = pointA0.y;
		double YA1 = pointA1.y;
		double YB0 = pointB0.y;
		double YB1 = pointB1.y;

		double ZA0 = pointA0.z;
		double ZA1 = pointA1.z;
		double ZB0 = pointB0.z;
		double ZB1 = pointB1.z;	
		
//		System.out.println("(" + String.format("%.2f", XA0)+ " " + String.format("%.2f", YA0) + " " + String.format("%.2f", ZA0) + ") - " +
//				"(" + String.format("%.2f", XA1) + " " + String.format("%.2f", YA1) + " " + String.format("%.2f", ZA1) + ");  " +
//				"(" + String.format("%.2f", XB0)+ " " + String.format("%.2f", YB0) + " " + String.format("%.2f", ZB0) + ") - " +
//				"(" + String.format("%.2f", XB1) + " " + String.format("%.2f", YB1) + " " + String.format("%.2f", ZB1) + ")");
		
		double minXA = Math.min(XA0, XA1);
		double maxXA = Math.max(XA0, XA1);
		double minXB = Math.min(XB0, XB1);
		double maxXB = Math.max(XB0, XB1);
		
		double minX = Math.max(minXA, minXB);
		double maxX = Math.min(maxXA, maxXB);
		if(minX > maxX) return 0;
		
//		double YA0 = pointA0.y;
//		double YA1 = pointA1.y;
//		double YB0 = pointB0.y;
//		double YB1 = pointB1.y;

		double minYA = Math.min(YA0, YA1);
		double maxYA = Math.max(YA0, YA1);
		double minYB = Math.min(YB0, YB1);
		double maxYB = Math.max(YB0, YB1);
		
		double minY = Math.max(minYA, minYB);
		double maxY = Math.min(maxYA, maxYB);
		if(minY > maxY) return 0;

		double XA = XA1 - XA0;
		double XB = XB1 - XB0;
		
		if(XA==0 && XB==0 && XA0 != XB0) 
			return 0; 

		double YA = YA1 - YA0;
		double YB = YB1 - YB0;
		
		if(YA==0 && YB==0 && YA0 != YB0) 
			return 0; 
		
		double XYAB = XB * YA - XA * YB;
		boolean roundOffXYAB = Math.abs(XYAB) < roundOff;
	
		double XYA = XA0 * YA - YA0 * XA;
		double XYB = XB0 * YB - YB0 * XB;
			
		if(XA==0 && XB==0) 
			X = XA0;
		else{
			double XYX = XYA * XB - XYB * XA;
			if(roundOffXYAB)
				if(Math.abs(XYX) < roundOff)
					X = (minX + maxX) / 2.0;
				else
					return 0;
			else
				X = XYX / XYAB;
		}
		
		if(minX > X || X > maxX) return 0;
			
		if(YA==0 && YB==0) 
			Y = YA0;
		else{
			double XYY = XYA * YB - XYB * YA;
			if(roundOffXYAB)
				if(Math.abs(XYY) < roundOff)
					Y = (minY + maxY) / 2.0;
				else
					return 0;
			else
				Y = XYY / XYAB;
		}
	
		if(minY > Y || Y > maxY) return 0;
		
		//double ZA0 = pointA0.z;
		
		if(XA==0 && YA==0) {
			ZatA = ZA0;
			//System.out.println(" ZatA null  ");
		}

		else{
			
			//double ZA1 = pointA1.z;
			double ZA = ZA1 - ZA0;

			if(XA != 0){
				
				double ZXA = ZA0 * XA - XA0 * ZA;
				//System.out.println(" ZatA calc ZXA = " + ZXA);
				ZatA = (ZXA + X * ZA) / XA;
			}
			else{
				//System.out.println(" ZatA calc from Y ");
				double ZYA = ZA0 * YA - YA0 * ZA;
				ZatA = (ZYA + Y * ZA) / YA;
			}
		}
		
		//double ZB0 = pointB0.z;
		
		if(XB==0 && YB==0) {
			ZatB = ZB0;
			//System.out.println(" ZatB null  ");
		}

		else{
			
			//double ZB1 = pointB1.z;
			double ZB = ZB1 - ZB0;

			if(XB != 0){
				
				double ZXB = ZB0 * XB - XB0 * ZB;
				//System.out.println(" ZatA calc ZXB = " + ZXB);
				ZatB = (ZXB + X * ZB) / XB;
			}
			else{
				//System.out.println(" ZatB calc from Y ");
				double ZYB = ZB0 * YB - YB0 * ZB;
				ZatB = (ZYB + Y * ZB) / YB;
			}
		}
		
		
		
		int result = 0;
		if(ZatA - ZatB > roundOff) result = 1;
		if(ZatA - ZatB < -roundOff) result = -1;
		
//		System.out.println("                : (" + String.format("%.2f", X) + ", " + String.format("%.2f", Y) + ", " + 
//				String.format("%.10f", ZatA) + " / " + String.format("%.10f", ZatB) + "):  " + result);
		
		return result;
	}
	
	

}	
