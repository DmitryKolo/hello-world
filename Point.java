import java.awt.Graphics2D;


public class Point {

	// Fields
		
	public double x;
	public double y;
	public double z;

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
	
}