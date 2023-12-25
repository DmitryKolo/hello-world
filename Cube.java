
import java.awt.*;

public class Cube {

	// Fields
	
	public final static int DIMENSION = 3;
	public final static int SIZE = 3;
	
	public Matrix transitionMatrix = new Matrix(DIMENSION, DIMENSION + 1);
	
	public Vector vectorA, vectorB, vectorC; // head on over to Axis, basis
	Axis[] axis = new Axis[Cube.DIMENSION];
		
	public int size; 
			
	public Point centre;
	public Point cornerA0B0C0, cornerA0B0C1, cornerA0B1C0, cornerA0B1C1, cornerA1B0C0, cornerA1B0C1, cornerA1B1C0, cornerA1B1C1; // перейти на Angle
	Point[][][] angle = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	
	public Color[][][][] tile = new Color[Cube.DIMENSION][Axis.ENDS_QUANTITY][3][3];
	
	private Address3D upperAngle; 
	
	private double speed = 1;
	
	public static boolean shift;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;

	public static boolean rotateI;
	public static boolean rotateJ;
	public static boolean rotateK;

	// Constructor
	
	public Cube(int size, Point centre, Vector vectorA, Vector vectorB, Vector vectorC){
			
		this.size = size;
		this.centre = centre;
		
		CubeBasis basis = new CubeBasis(vectorA, vectorB, vectorC);
		
		this.vectorA = vectorA; // head on over to Axis, basis
		this.vectorB = vectorB;
		this.vectorC = vectorC;
		
		for (int i = 0; i < DIMENSION; i++)
			this.axis[i] = new Axis(i, basis, size, centre, 0, size - 1);
		
		CalculateTransitionMatrix();
	
		for (int i = 0; i < angle.length; i++){
			
			double halfsize = size / 2;
			
			double coefI = (i==0 ? -halfsize : halfsize);
			Point pointI = new Point(Point.NULL, new Vector(coefI, basis.vector[0]));				
						
		    for (int j = 0; j < angle[i].length; j++){
		    	
				double coefJ = (j==0 ? -halfsize : halfsize);
				Point pointJ = new Point(pointI, new Vector(coefJ, basis.vector[1]));				
				
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
					double coefK = (k==0 ? -halfsize : halfsize);
			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, basis.vector[2]));
			    	//Point CurrentCorner = this.angle[i][j][k];
					//System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
			    }
		    }
	    }
		
		this.upperAngle = this.upperAngleAddress();
		
		for(int i = 0; i < Cube.DIMENSION; i++)
		    for (int n = 0; n < Axis.ENDS_QUANTITY; n++)
			    for (int j = 0; j < size; j++)
				    for (int k = 0; k < size; k++) 
    					tile[i][n][j][k] = colorINJK(i, n, j, k);
	}
			
	// Functions
	
	public Color colorINJK(int i, int n, int j, int k){
		
    	int colorNumber = i * 2 + n;
    	
    	switch(colorNumber){
	    	case 0:
	    		return Color.CYAN;
	    	case 1:
	    		return Color.GREEN;
	    	case 2:
	    		return Color.MAGENTA;
	    	case 3:
	    		return Color.ORANGE;
	    	case 4:
	    		return Color.PINK;
	    	case 5:
	    		return Color.RED;
	    	case 6:
	    		return Color.YELLOW;
	    	default:
	    		return Color.WHITE;
	   	}
    }

	
	public Color colorINJK_random(int i, int n, int j, int k){
	
	double random = Math.random() * 6;
	int colorNumber = ((int) random) % 6;
	
	switch(colorNumber){
    	case 0:
    		return Color.CYAN;
    	case 1:
    		return Color.GREEN;
    	case 2:
    		return Color.MAGENTA;
    	case 3:
    		return Color.ORANGE;
    	case 4:
    		return Color.PINK;
    	case 5:
    		return Color.RED;
    	case 6:
    		return Color.YELLOW;
    	default:
    		return Color.WHITE;
   	}
}

	
	public void CalculateTransitionMatrix(){
		
		transitionMatrix.data[0][0] = vectorA.dx;
		transitionMatrix.data[0][1] = vectorA.dy;
		transitionMatrix.data[0][2] = vectorA.dz;
		transitionMatrix.data[0][3] = GamePanel.WIDTH / 2;
		
		transitionMatrix.data[1][0] = vectorB.dx;
		transitionMatrix.data[1][1] = vectorB.dy;
		transitionMatrix.data[1][2] = vectorB.dz;
		transitionMatrix.data[1][3] = GamePanel.HEIGHT / 2;
		
		transitionMatrix.data[2][0] = vectorC.dx;
		transitionMatrix.data[2][1] = vectorC.dy;
		transitionMatrix.data[2][2] = vectorC.dz;
		
	}

		
	public void rotateV(Vector rotateAxis, double rotateAngle){
		
		Vector normalAxis = new Vector(rotateAxis);
		
//		for (int i = 0; i < angle.length; i++)
//		    for (int j = 0; j < angle[i].length; j++)
//			    for (int k = 0; k < angle[i][j].length; k++){
////					angle[i][j][k].rotateX(rotateAngle * normalAxis.dx); 
////					angle[i][j][k].rotateY(rotateAngle * normalAxis.dy); 
////					angle[i][j][k].rotateZ(rotateAngle * normalAxis.dz); 
//			    	angle[i][j][k].rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz);
//			    } // отказаться: перейти к пересчету углов после пересчета векторов
//		
//		for (int i = 0; i < axis.length; i++){
//			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
//		}	
		
		vectorA.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
		vectorB.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
		vectorC.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
		
		CalculateTransitionMatrix();
		
	}
	
	public void update(){
		
		double rotateAngle = speed/20;

		if(up){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(1, 0, 0), rotateAngle);
		}

		if(left){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(0, 1, 0), rotateAngle);
		}

		if(right){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(0, 0, 1), rotateAngle);
		}
		
		if(down){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(new Vector(1, 0, 0), rotateAngle);
		}
		
		if(rotateI){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(axis[0].vector, rotateAngle);
			//rotateV(rotateVector, rotateAngle);
		}

		if(rotateJ){
			if(shift) rotateAngle = -rotateAngle;
			rotateV(axis[1].vector, rotateAngle);
		}
		
		if(rotateK){
			if(!shift) rotateAngle = -rotateAngle;
			rotateV(axis[2].vector, rotateAngle);
		}
		
		if(up || left || right || down || rotateI || rotateJ || rotateK){
			this.upperAngle = this.upperAngleAddress();
//			for (int i = 0; i < angle.length; i++){
//			    for (int j = 0; j < angle[i].length; j++){
//				    for (int k = 0; k < angle[i][j].length; k++){
//				    	Point CurrentCorner = this.angle[i][j][k];
//						System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
//					}
//				}
//			}
//			System.out.println("upperAngleAddress = ("+upperAngle.i+", "+upperAngle.j+", "+upperAngle.k+")");
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
		
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
		    	
		    	Point angle0;
		    	Point angle1;
		    	
		    	if (i == upperAngle.i || j == upperAngle.j) {
		    		angle0 = angle[i][j][0];
		    		angle1 = angle[i][j][1];
		    		GamePanel.drawLine(g, angle0, angle1, centre, Color.GREEN);
		    	}
				
		    	if (i == upperAngle.i || j == upperAngle.k) {
			    	angle0 = angle[i][0][j];
			    	angle1 = angle[i][1][j];
			    	GamePanel.drawLine(g, angle0, angle1, centre, Color.GREEN);
		    	}
				
		    	if (i == upperAngle.j || j == upperAngle.k) {
			    	angle0 = angle[0][i][j];
			    	angle1 = angle[1][i][j];
			    	GamePanel.drawLine(g, angle0, angle1, centre, Color.GREEN);
		    	}
		    }
	    }
		
		g.setColor(Color.RED);
		
		Point currentUpperAngle = angle[upperAngle.i][upperAngle.j][upperAngle.k];
		
		double r = 5;
		double x0 = centre.x + currentUpperAngle.x - r/2;
		double y0 = centre.y + currentUpperAngle.y - r/2;
		g.fillOval((int)x0, (int)y0, (int)r, (int)r);
			
	}
				
	public Point angleAtAddress(Address3D address){
		
		return angle[address.i][address.j][address.k];
		
	}
		
	public Address3D upperAngleAddress(){
		
		Address3D upperAngleAddress = new Address3D(0, 0, 0);
		Point upperAngle = angleAtAddress(upperAngleAddress);
		//System.out.println("   Angle["+0+","+0+","+0+"]: z = "+upperAngle.z);
				
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
			    	Address3D currentAddress = new Address3D(i, j, k);
			    	Point currentAngle = angleAtAddress(currentAddress);
			    	if (upperAngle.z < currentAngle.z){
			    		upperAngleAddress = currentAddress;
			    		upperAngle = angleAtAddress(upperAngleAddress);
						//System.out.println("   Angle["+i+","+j+","+k+"]: z = " + upperAngle.z + " < " + currentAngle.z);
					}
				}
			}
	    }
		
		return upperAngleAddress;
		
	}
	
}