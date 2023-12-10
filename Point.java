
import java.awt.Graphics2D;

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
	
	// Rotate functions are outdated; head on over to method for a Vector; rotate only basis; not rotate points but recalculate it from basis
	
//	public void rotateX(double rotateAngle){ 
//	
//		double atan2 = Math.atan2(z, y);
//		double r = Math.sqrt(y*y + z*z);
//		
//		atan2 += rotateAngle;
//		
//		y = Math.cos(atan2) * r;
//		z = Math.sin(atan2) * r;
//
//	}
//	
//	public void rotateY(double rotateAngle){
//	
//		double atan2 = Math.atan2(x, z);
//		double r = Math.sqrt(z*z + x*x);
//		
//		atan2 += rotateAngle;
//		
//		z = Math.cos(atan2) * r;
//		x = Math.sin(atan2) * r;
//
//	}
//	
//	public void rotateZ(double rotateAngle){
//	
//		double atan2 = Math.atan2(y, x);
//		double r = Math.sqrt(x*x + y*y);
//		
//		atan2 += rotateAngle;
//		
//		x = Math.cos(atan2) * r;
//		y = Math.sin(atan2) * r;
//
//	}
	
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
	
}	
