
public class Vector {

	// Fields
		
	public double dx;
	public double dy;
	public double dz;
	
	public final static Vector NULL = new Vector(0, 0, 0);

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
	
	public Vector(Vector vector){
		
		double coef = Math.sqrt(vector.dx * vector.dx + vector.dy * vector.dy + vector.dz * vector.dz);
		
		this.dx = vector.dx / coef; 
		this.dy = vector.dy / coef; 
		this.dz = vector.dz / coef;
		
	}	
	
	// Functions
	
	public void rotateXYZ(double rotateAngleX, double rotateAngleY, double rotateAngleZ){
	
		if(rotateAngleX != 0){
			
			double atan2X = Math.atan2(dz, dy) + rotateAngleX;
			double rX = Math.sqrt(dy*dy + dz*dz);
			
			dy = Math.cos(atan2X) * rX;
			dz = Math.sin(atan2X) * rX;
		}

		if(rotateAngleY != 0){
			
			double atan2Y = Math.atan2(dx, dz) + rotateAngleY;
			double rY = Math.sqrt(dz*dz + dx*dx);
			
			dz = Math.cos(atan2Y) * rY;
			dx = Math.sin(atan2Y) * rY;
		}

		if(rotateAngleZ != 0){
			
			double atan2Z = Math.atan2(dy, dx) + rotateAngleZ;
			double rZ = Math.sqrt(dx*dx + dy*dy);
			
			dx = Math.cos(atan2Z) * rZ;
			dy = Math.sin(atan2Z) * rZ;
		}
	
	}	
	
	public double cos(Vector vector){
		
		double r0 = Math.sqrt(this.dx * this.dx + this.dy * this.dy + this.dz * this.dz);
		double r1 = Math.sqrt(vector.dx * vector.dx + vector.dy * vector.dy + vector.dz * vector.dz);
		double multi = this.dx * vector.dx + this.dy * vector.dy + this.dz * vector.dz;
		return multi / r0 / r1;
		
	}
}