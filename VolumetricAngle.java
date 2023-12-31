
import java.awt.*;

public class VolumetricAngle {

	// Fields
	
	private double x;
	private double y;
	private double dy;
	private double xA;
	private double yA;
	private double zA;
	private double xB;
	private double yB;
	private double zB;
	private double xC;
	private double yC;
	private double zC;
			
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean shift;
	
	private double speed;
			
	private Edge edge1, edge2, edge3, edge4, edge5, edge6;
			
	// Constructor
	
	public VolumetricAngle(double xA, double yA, double zA, double xB, double yB, double zB, double xC, double yC, double zC){
			
//		x = xA;		
//		y = yA;
		
		this.xA = xA;
		this.yA = yA;
		this.zA = zA;
		this.xB = xB;
		this.yB = yB;
		this.zC = zB;
		this.xC = xC;
		this.yC = yC;
		this.zC = zC;
				
		up = false;
		down = false;
		left = false;
		right = false;
		shift = false;
		
		speed = 1;
				
		edge1 = new Edge(xA, yA, xB, yB, 1.5*xC, 1.5*yC);
		edge2 = new Edge(xB, yB, xC, yC, 1.5*xA, 1.5*yA);
		edge3 = new Edge(xC, yC, xA, yA, 1.5*xB, 1.5*yB);
		edge4 = new Edge(xA, yA, xB, yB, -1.5*xC, -1.5*yC);
		edge5 = new Edge(xB, yB, xC, yC, -1.5*xA, -1.5*yA);
		edge6 = new Edge(xC, yC, xA, yA, -1.5*xB, -1.5*yB);
	
	}
			
	// Functions
	
	public double getX(){
		return x;
	}
			
	public double getY(){
		return y;
	}
	
	private class RotatingVectors{
		
		// Fields
		
		double uA;
		double uB;
		double uC;
		double vA;
		double vB;
		double vC;
		
		// Constructor
		
		public RotatingVectors(double uA, double vA, double uB, double vB, double uC, double vC){
			this.uA = uA;
			this.uB = uB;
			this.uC = uC;
			this.vA = vA;
			this.vB = vB;
			this.vC = vC;
		}
	
		// Functions
		
		public void rotate(double rotateSpeed){
		
			double atan2A = Math.atan2(vA, uA);
			double atan2B = Math.atan2(vB, uB);
			double atan2C = Math.atan2(vC, uC);
			double r2A = Math.sqrt(uA*uA + vA*vA);
			double r2B = Math.sqrt(uB*uB + vB*vB);
			double r2C = Math.sqrt(uC*uC + vC*vC);
			atan2A += rotateSpeed;
			atan2B += rotateSpeed;
			atan2C += rotateSpeed;
			
			uA = Math.cos(atan2A) * r2A;
			vA = Math.sin(atan2A) * r2A;
			uB = Math.cos(atan2B) * r2B;
			vB = Math.sin(atan2B) * r2B;
			uC = Math.cos(atan2C) * r2C;
			vC = Math.sin(atan2C) * r2C;
	
		}
		
	}
	
	public void rotateX(double z1, double y1, double z2, double y2, double z3, double y3, double rotateSpeed){
		
		RotatingVectors rVect = new RotatingVectors(z1, y1, z2, y2, z3, y3);
		rVect.rotate(rotateSpeed);
		zA = rVect.uA;
		zB = rVect.uB;
		zC = rVect.uC;
		yA = rVect.vA;
		yB = rVect.vB;
		yC = rVect.vC;

	}	
	
	public void rotateY(double z1, double x1, double z2, double x2, double z3, double x3, double rotateSpeed){
		
		RotatingVectors rVect = new RotatingVectors(z1, x1, z2, x2, z3, x3);
		rVect.rotate(rotateSpeed);
		zA = rVect.uA;
		zB = rVect.uB;
		zC = rVect.uC;
		xA = rVect.vA;
		xB = rVect.vB;
		xC = rVect.vC;

	}	
	
	public void rotateZ(double x1, double y1, double x2, double y2, double x3, double y3, double rotateSpeed){
		
		RotatingVectors rVect = new RotatingVectors(x1, y1, x2, y2, x3, y3);
		rVect.rotate(rotateSpeed);
		xA = rVect.uA;
		xB = rVect.uB;
		xC = rVect.uC;
		yA = rVect.vA;
		yB = rVect.vB;
		yC = rVect.vC;

	}	
	
	public void update(){
				
		if(up && ! up && y > - GamePanel.HEIGHT / 2){
			dy = -speed;
		
			x += dy;
			y += dy;
			dy =0;
			
			xA = x;
		
		double r0 = 50;

		//System.out.println("xA = " + xA);
		//System.out.println("yA = " + yA);
		//System.out.println("zA = " + zA);
		
		double rA2 = xA * xA + yA * yA + zA * zA;
		//System.out.println("rA(2) = " + rA2);
		
		double rA = Math.sqrt(rA2);
		//System.out.println("rA = " + rA);
		
		double co0A = r0 / rA;
		xA = xA * co0A;
		yA = yA * co0A;
		zA = zA * co0A;
		//System.out.println("xA = " + xA);
		//System.out.println("yA = " + yA);
		//System.out.println("zA = " + zA);
		
		rA2 = xA * xA + yA * yA + zA * zA;
		rA = Math.sqrt(rA2);
		//System.out.println("rA = " + rA);
		
		//double xB = 40;
		//double yB = -19;
		//System.out.println("xB = " + xB);
		//System.out.println("yB = " + yB);
		
		double zB2 = rA2 - xB * xB - yB * yB;
		//System.out.println("zB(2) = " + zB2);
		
		zB = Math.sqrt(zB2);
		//System.out.println("zB = " + zB);
		
		double scAB = xA * xB + yA * yB + zA * zB;
		//System.out.println("��������� ������������ AB = " + scAB);
	
		zB = - (xA * xB + yA * yB) / zA;
		//System.out.println("zB = " + zB);
	
		scAB = xA * xB + yA * yB + zA * zB;
		//System.out.println("��������� ������������ AB = " + scAB);
		
		double rB2 = xB * xB + yB * yB + zB * zB;
		//System.out.println("rB(2) = " + rB2);
		
		double rB = Math.sqrt(rB2);
		//System.out.println("rB = " + rB);

		double coAB = rA / rB;
		xB = xB * coAB;
		yB = yB * coAB;
		zB = zB * coAB;
		//System.out.println("xB = " + xB);
		//System.out.println("yB = " + yB);
		//System.out.println("zB = " + zB);
		
		rB2 = xB * xB + yB * yB + zB * zB;
		//System.out.println("rB(2) = " + rB2);
		
		//double xC = -30;
		//System.out.println("xC = " + xC);
		
		//double yC = -26;
		//System.out.println("yC = " + yC);
		//System.out.println("zC = " + zC);
		
		//yC = xC * (xA*zB - xB*zA) / (zA*yB - yA*zB);
		//System.out.println("yC = " + yC);
		
		zC = xC * (xA*yB - xB*yA) / (yA*zB - zA*yB);
		//System.out.println("zC = " + zC);
		
		double rC2 = xC * xC + yC * yC + zC * zC;
		//System.out.println("r�(2) = " + rC2);
		
		double rC = Math.sqrt(rC2);
		//System.out.println("r� = " + rC);
		
		double coAC = rA / rC;
		xC = xC * coAC;
		yC = yC * coAC;
		zC = zC * coAC;
		
		rC2 = xC * xC + yC * yC + zC * zC;
		//System.out.println("r�(2) = " + rC2);
		
			edge1.update(xA, yA, xB, yB, 1.5*xC, 1.5*yC);
			edge2.update(xB, yB, xC, yC, 1.5*xA, 1.5*yA);
			edge3.update(xC, yC, xA, yA, 1.5*xB, 1.5*yB);
		}
	
		double rotateSpeed = speed/20;

		if(up){

			if(shift){
				rotateSpeed = -rotateSpeed;
			}
			rotateX(zA, yA, zB, yB, zC, yC, rotateSpeed);
			
		}

		if(left){

			if(shift){
				rotateSpeed = -rotateSpeed;
			}
			rotateY(zA, xA, zB, xB, zC, xC, rotateSpeed);
		}

		if(right){
			
			if(shift){
				rotateSpeed = -rotateSpeed;
			}
			rotateZ(xA, yA, xB, yB, xC, yC, rotateSpeed);
			
		}
		
		if(down){

			if(!shift){
				rotateSpeed = -rotateSpeed;
			}
			rotateX(zA, yA, zB, yB, zC, yC, rotateSpeed);
		}
		
		if(up || left || right || down){
			edge1.update(xA, yA, xB, yB, 1.5*xC, 1.5*yC);
			edge2.update(xB, yB, xC, yC, 1.5*xA, 1.5*yA);
			edge3.update(xC, yC, xA, yA, 1.5*xB, 1.5*yB);
			edge4.update(xC, yC, xA, yA, -1.5*xB, -1.5*yB);
			edge5.update(xB, yB, xC, yC, -1.5*xA, -1.5*yA);
			edge6.update(xC, yC, xA, yA, -1.5*xB, -1.5*yB);
		}
	}
			
	public void draw(Graphics2D g){
				
//		edge1.draw(g);
//		edge2.draw(g);
//		edge3.draw(g);
		
	}
}

