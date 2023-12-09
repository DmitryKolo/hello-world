
public class Vector{

	// Fields
		
	public double dx;
	public double dy;
	public double dz;

	// Constructor
	
	public Vector(double dx, double dy, double dz){
		
		this.dx = dx; 
		this.dy = dy; 
		this.dz = dz;
		
	}	
	
	public Vector(double coef, Vector vector){
		
		this.dx = coef * vector.dx; 
		this.dy = coef * vector.dy; 
		this.dz = coef * vector.dz;
		
	}	
	
}