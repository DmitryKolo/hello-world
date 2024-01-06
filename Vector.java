import java.awt.Color;
import java.awt.Graphics2D;


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
	
	public Vector(Point point0, Point point1){
		
		this.dx = point1.x - point0.x; 
		this.dy = point1.y - point0.y; 
		this.dz = point1.z - point0.z;
		
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
	
	public double getCoordinate(int coordinateIndex){
		
		switch(coordinateIndex){
		
			case 0: 
				return this.dx; 
			case 1: 
				return this.dy; 
			case 2: 
				return this.dz;
			default:
				throw new RuntimeException("Illegal coordinate index.");
		}	
	}
	
	
	public Vector plus(Vector vector){
		
		return new Vector(this.dx + vector.dx, this.dy + vector.dy, this.dz + vector.dz);
		
	}	

	
	public Vector minus(Vector vector){
		
		return new Vector(this.dx - vector.dx, this.dy - vector.dy, this.dz - vector.dz);
		
	}	

	
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
		
		double r0 = this.length();
		double r1 = vector.length();
		double scalarProduct = scalarProductBy(vector);
		return scalarProduct / r0 / r1;
		
	}


	public double square(){
		
		return (this.dx * this.dx + this.dy * this.dy + this.dz * this.dz);
		
	}


	public double length(){
		
		return Math.sqrt(this.square());
		
	}


	public double scalarProductBy(Vector vector){
		
		return this.dx * vector.dx + this.dy * vector.dy + this.dz * vector.dz;
		
	}

	
	public Vector projectionOnto(Vector vector){
		
		double scalarProduct = scalarProductBy(vector);
		double r2 = vector.square();
		return new Vector(scalarProduct / r2, vector);
	
	}

	
	public Vector projectionOntoXY(){
		
		return new Vector(this.dx, this.dy, 0);
	
	}


	public void draw(Graphics2D g, Color color){
		
//		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
//	   	g.setStroke(dashed); //2d
		
    	double xB = GamePanel.WIDTH / 2.0;
    	double yB = GamePanel.HEIGHT / 2.0;
    	double xE = xB + this.dx;
    	double yE = yB + this.dy;
    	
    	g.setColor(color);
    	g.drawLine((int)xB, (int)yB, (int)xE, (int)yE);
	}	

	
	public static Vector[] orthogonalVectors(double r0,
			double xA, double yA, double zA,
			double xB, double yB,
			double xC){
		
		Vector[] vectors = new Vector[Cube.DIMENSION];
		
		double rA2 = xA * xA + yA * yA + zA * zA;
		double rA = Math.sqrt(rA2);
		
		double coefA = r0 / rA;
		xA = xA * coefA;
		yA = yA * coefA;
		zA = zA * coefA;
		
		double zB = - (xA * xB + yA * yB) / zA;
		
		double rB2 = xB * xB + yB * yB + zB * zB;
		double rB = Math.sqrt(rB2);

		double coefB = r0 / rB;
		xB = xB * coefB;
		yB = yB * coefB;
		zB = zB * coefB;
		
		double yC = xC * (xA*zB - xB*zA) / (zA*yB - yA*zB);
		double zC = xC * (xA*yB - xB*yA) / (yA*zB - zA*yB);
		
		double rC2 = xC * xC + yC * yC + zC * zC;
		double rC = Math.sqrt(rC2);
		
		double coefC = r0 / rC;
		xC = xC * coefC;
		yC = yC * coefC;
		zC = zC * coefC;
		
		vectors[0] = new Vector(xA, yA, zA);
		vectors[1] = new Vector(xB, yB, zB);
		vectors[2] = new Vector(xC, yC, zC);
		
		return vectors;
		
	}	

}