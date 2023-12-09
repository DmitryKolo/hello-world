
import java.awt.*;

public class Cube{

	// Fields
	
	public CubeBasis basis;
	
	public Vector vectorA, vectorB, vectorC; // перейти на Axis, basis;
//	public PairOfEdges edgesPairA, edgesPairB, edgesPairC; // перейти на Axis;
	Axis[] axis = new Axis[3];
		
	public int size; 
			
	public Point centre, cornerA0B0C0, cornerA0B0C1, cornerA0B1C0, cornerA0B1C1, cornerA1B0C0, cornerA1B0C1, cornerA1B1C0, cornerA1B1C1; // перейти на Angle
	Point[][][] angle = new Point[2][2][2];
	
	private Address3D upperAngle; 
	
	private double speed = 1;
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	public static boolean shift;

	// Constructor
	
	public Cube(int size, Point centre, Vector vectorA, Vector vectorB, Vector vectorC){
			
		this.size = size;
		this.centre = centre;
		this.basis = new CubeBasis(vectorA, vectorB, vectorC);
		
		this.vectorA = vectorA;
		this.vectorB = vectorB;
		this.vectorC = vectorC;
		
		for (int i = 0; i < size; i++)
			this.axis[i] = new Axis(i, this.basis, size, centre);
	
		Point coordCentre = new Point(0, 0, 0);
				
		for (int i = 0; i < angle.length; i++){
			
			double halfsize = size / 2;
			
			double coefI = (i==0 ? -halfsize : halfsize);
			Point pointI = new Point(coordCentre, new Vector(coefI, vectorA));				
						
		    for (int j = 0; j < angle[i].length; j++){
		    	
				double coefJ = (j==0 ? -halfsize : halfsize);
				Point pointJ = new Point(pointI, new Vector(coefJ, vectorB));				
				
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
					double coefK = (k==0 ? -halfsize : halfsize);
			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, vectorC));
			    	Point CurrentCorner = this.angle[i][j][k];
					System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
			    	
			    }
		    }
	    }
		
		this.upperAngle = this.upperAngleAddress();
		
		// to improve: first, create a three-dimensional array of vertices (angle), then specify them in the edges of the axes
//		edgesPairA = new PairOfEdges(size, centre, vectorA, vectorB, vectorC);
//		edgesPairB = new PairOfEdges(size, centre, vectorB, vectorC, vectorA);
//		edgesPairC = new PairOfEdges(size, centre, vectorC, vectorA, vectorB);
		
//		this.axis[0] = new Axis(vectorA, edgesPairA.edge0, edgesPairA.edge1); 
//		this.axis[1] = new Axis(vectorB, edgesPairB.edge0, edgesPairB.edge1);
//		this.axis[2] = new Axis(vectorC, edgesPairC.edge0, edgesPairC.edge1);
		
	}
			
	// Functions
	
	public void rotateX(double rotateAngle){
		
		for (int i = 0; i < angle.length; i++)
		    for (int j = 0; j < angle[i].length; j++)
			    for (int k = 0; k < angle[i][j].length; k++)
					angle[i][j][k].rotateX(rotateAngle); 
	}	
			    
	public void rotateY(double rotateAngle){
		
		for (int i = 0; i < angle.length; i++)
		    for (int j = 0; j < angle[i].length; j++)
			    for (int k = 0; k < angle[i][j].length; k++)
					angle[i][j][k].rotateY(rotateAngle); 
	}	
			    
	public void rotateZ(double rotateAngle){
		
		for (int i = 0; i < angle.length; i++)
		    for (int j = 0; j < angle[i].length; j++)
			    for (int k = 0; k < angle[i][j].length; k++)
					angle[i][j][k].rotateZ(rotateAngle); 
	}	
	
	public void update(){
		
		double rotateAngle = speed/20;

		if(up){

			if(shift){
				rotateAngle = -rotateAngle;
			}
			rotateX(rotateAngle);
			
		}

		if(left){

			if(!shift){
				rotateAngle = -rotateAngle;
			}
			rotateY(rotateAngle);
		}

		if(right){
			
			if(shift){
				rotateAngle = -rotateAngle;
			}
			rotateZ(rotateAngle);
			
		}
		
		if(down){

			if(!shift){
				rotateAngle = -rotateAngle;
			}
			rotateX(rotateAngle);
		}
		
		if(up || left || right || down){
			this.upperAngle = this.upperAngleAddress();
			for (int i = 0; i < angle.length; i++){
			    for (int j = 0; j < angle[i].length; j++){
				    for (int k = 0; k < angle[i][j].length; k++){
				    	Point CurrentCorner = this.angle[i][j][k];
						System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");

					}
				}
			}
			System.out.println("upperAngleAddress = ("+upperAngle.i+", "+upperAngle.j+", "+upperAngle.k+")");
			
		}
	}
			
	private void drawLine(Graphics2D g, Point angle0, Point angle1){
		
//		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
//	   	g.setStroke(dashed); //2d
		
    	double x0c = angle0.x + centre.x;
    	double y0c = angle0.y + centre.y;
    	double x1c = angle1.x + centre.x;
    	double y1c = angle1.y + centre.y;
    	
		g.drawLine((int)x0c, (int)y0c, (int)x1c, (int)y1c);
	}
	
	public void draw(Graphics2D g){
		
		g.setColor(Color.green);
		
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
		    	
		    	Point angle0;
		    	Point angle1;
		    	
		    	if (i == upperAngle.i || j == upperAngle.j) {
		    		angle0 = angle[i][j][0];
		    		angle1 = angle[i][j][1];
		    		drawLine(g, angle0, angle1);
		    	}
				
		    	if (i == upperAngle.i || j == upperAngle.k) {
			    	angle0 = angle[i][0][j];
			    	angle1 = angle[i][1][j];
			    	drawLine(g, angle0, angle1);
		    	}
				
		    	if (i == upperAngle.j || j == upperAngle.k) {
			    	angle0 = angle[0][i][j];
			    	angle1 = angle[1][i][j];
			    	drawLine(g, angle0, angle1);
		    	}
	    	
		    }
	    }
		
		g.setColor(Color.RED);
		
		Point cirrentUpperAngle = angle[upperAngle.i][upperAngle.j][upperAngle.k];
		
		double r = 5;
		double x0 = centre.x + cirrentUpperAngle.x - r/2;
		double y0 = centre.x + cirrentUpperAngle.y - r/2;
		g.fillOval((int)x0, (int)y0, (int)r, (int)r);
		//g.setColor(color1);
		//g.fillOval((int)x1, (int)y1, 2 * r1, 2 * r1);
			
	}
				
	public Point angleAtAddress(Address3D address){
		
		return angle[address.i][address.j][address.k];
		
	}
		
	public Address3D upperAngleAddress(){
		
		Address3D upperAngleAddress = new Address3D(0, 0, 0);
		Point upperAngle = angleAtAddress(upperAngleAddress);
		System.out.println("   Angle["+0+","+0+","+0+"]: z = "+upperAngle.z);
				
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
			    	Address3D currentAddress = new Address3D(i, j, k);
			    	Point currentAngle = angleAtAddress(currentAddress);
			    	if (upperAngle.z < currentAngle.z){
			    		upperAngleAddress = currentAddress;
			    		upperAngle = angleAtAddress(upperAngleAddress);
						System.out.println("   Angle["+i+","+j+","+k+"]: z = " + upperAngle.z + " < " + currentAngle.z);
					}
				}
			}
	    }
		
		return upperAngleAddress;
		
	}
	
}