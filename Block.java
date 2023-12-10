
import java.awt.*;

public class Block {

	// Fields
	
	public Cube cube;
	public Axis[] axis = new Axis[Cube.DIMENSION];
	public Point[][][] angle = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	
	//public Point centre;
	private Address3D upperAngle; 

	public final int rotatableAxisIndex;
	private boolean turned;
	
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
	
	public Block(Cube cube, int rotatableAxisIndex, int beginingIndex, int endingIndex){
			
		this.cube = cube;
		this.rotatableAxisIndex = rotatableAxisIndex;
		
		CubeBasis basis = new CubeBasis(cube);
//		if(rotatebledAxisNumber != Cube.DIMENSION){
//			basis.vector[rotatebledAxisNumber] = Vector.NULL;
//		}
		
		int rotatebledAxisSize = endingIndex -  beginingIndex + 1;
		int[] size = new int[Cube.DIMENSION];
		double[] halfSize = new double[Cube.DIMENSION];

		for (int i = 0; i < cube.DIMENSION; i++){
			size[i] = (i == rotatableAxisIndex ? rotatebledAxisSize : cube.size);
			this.axis[i] = new Axis(i, basis, size[i], cube.centre); // сообщать оси центр куба не нужно
			halfSize[i] = (double)size[i] / 2;
		}

		for (int i = 0; i < angle.length; i++){
			
			double coefI = (i==0 ? -halfSize[0] : halfSize[0]);
			Point pointI = new Point(Point.NULL, new Vector(coefI, basis.vector[0]));				
						
		    for (int j = 0; j < angle[i].length; j++){
		    	
				double coefJ = (j==0 ? -halfSize[1] : halfSize[1]);
				Point pointJ = new Point(pointI, new Vector(coefJ, basis.vector[1]));				
				
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
					double coefK = (k==0 ? -halfSize[2] : halfSize[2]);
			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, basis.vector[2]));
			    	Point CurrentCorner = this.angle[i][j][k];
					System.out.println("Block angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
			    }
		    }
	    }
		
		this.upperAngle = this.upperAngleAddress();
		
	}
			
	// Functions
	
//	public void rotateX(double rotateAngle){
//		
//		for (int i = 0; i < angle.length; i++)
//		    for (int j = 0; j < angle[i].length; j++)
//			    for (int k = 0; k < angle[i][j].length; k++)
//					angle[i][j][k].rotateX(rotateAngle); 
//		
//		for (int i = 0; i < axis.length; i++)
//			axis[i].vector.rotateXYZ(rotateAngle, 0, 0); 
//	}	
//			    
//	public void rotateY(double rotateAngle){
//		
//		for (int i = 0; i < angle.length; i++)
//		    for (int j = 0; j < angle[i].length; j++)
//			    for (int k = 0; k < angle[i][j].length; k++)
//					angle[i][j][k].rotateY(rotateAngle); 
//		for (int i = 0; i < axis.length; i++)
//			axis[i].vector.rotateXYZ(0, rotateAngle, 0); 
//	}	
//			    
//	public void rotateZ(double rotateAngle){
//		
//		for (int i = 0; i < angle.length; i++)
//		    for (int j = 0; j < angle[i].length; j++)
//			    for (int k = 0; k < angle[i][j].length; k++)
//					angle[i][j][k].rotateZ(rotateAngle); 
//		for (int i = 0; i < axis.length; i++)
//			axis[i].vector.rotateXYZ(0, 0, rotateAngle); 
//	}	
	
	public void rotateXYZ(Vector rotateAxis, double rotateAngle){
		
		Vector normalAxis = new Vector(rotateAxis);
		
		for (int i = 0; i < angle.length; i++)
		    for (int j = 0; j < angle[i].length; j++)
			    for (int k = 0; k < angle[i][j].length; k++){
					angle[i][j][k].rotateX(rotateAngle * normalAxis.dx); 
					angle[i][j][k].rotateY(rotateAngle * normalAxis.dy); 
					angle[i][j][k].rotateZ(rotateAngle * normalAxis.dz); 
			    }
		
		for (int i = 0; i < axis.length; i++){
			System.out.println("Block vector ["+i+"] sta = ("+axis[i].vector.dx+", "+axis[i].vector.dy+", "+axis[i].vector.dz+")");
			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
			System.out.println("Block vector ["+i+"] end = ("+axis[i].vector.dx+", "+axis[i].vector.dy+", "+axis[i].vector.dz+")");
		}	
	}
	
	public void update(){
		
		double rotateAngle = speed/20;

		if(up){
			if(shift) rotateAngle = -rotateAngle;
			rotateXYZ(new Vector(1, 0, 0), rotateAngle);
	}

		if(left){
			if(!shift) rotateAngle = -rotateAngle;
			rotateXYZ(new Vector(0, 1, 0), rotateAngle);
	}

		if(right){
			if(shift) rotateAngle = -rotateAngle;
			rotateXYZ(new Vector(0, 0, 1), rotateAngle);
		}
		
		if(down){
			if(!shift) rotateAngle = -rotateAngle;
			rotateXYZ(new Vector(1, 0, 0), rotateAngle);
		}
		
		if(rotateI && rotatableAxisIndex == 0){
				if(!shift) rotateAngle = -rotateAngle;
				rotateXYZ(axis[0].vector, rotateAngle);
			}

		if(rotateJ && rotatableAxisIndex == 1){
				if(shift) rotateAngle = -rotateAngle;
				rotateXYZ(axis[1].vector, rotateAngle);
			}

		if(rotateK && rotatableAxisIndex == 2){
				if(!shift) rotateAngle = -rotateAngle;
				rotateXYZ(axis[2].vector, rotateAngle);
			}
				
		if(up || left || right || down || rotateI || rotateJ || rotateK)
			this.upperAngle = this.upperAngleAddress();
	}
	
	public void draw(Graphics2D g){
		
		g.setColor(Color.CYAN);
		
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
		    	
		    	Point angle0;
		    	Point angle1;
		    	
		    	if (i == upperAngle.i || j == upperAngle.j) {
		    		//g.setColor(Color.CYAN);
		    		angle0 = angle[i][j][0];
		    		angle1 = angle[i][j][1];
		    		GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
				
		    	if (i == upperAngle.i || j == upperAngle.k) {
		    		//g.setColor(Color.DARK_GRAY);
			    	angle0 = angle[i][0][j];
			    	angle1 = angle[i][1][j];
			    	GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
				
		    	if (i == upperAngle.j || j == upperAngle.k) {
		    		//g.setColor(Color.GREEN);
			    	angle0 = angle[0][i][j];
			    	angle1 = angle[1][i][j];
			    	GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
		    }
	    }
		
		g.setColor(Color.RED);
		
		Point currentUpperAngle = angle[upperAngle.i][upperAngle.j][upperAngle.k];
		
//		System.out.println("upperAngle.i = "+upperAngle.i);
//		System.out.println("upperAngle.j = "+upperAngle.j);
//		System.out.println("upperAngle.k = "+upperAngle.k);
//		System.out.println("upperAngleAddress = ("+upperAngle.i+", "+upperAngle.j+", "+upperAngle.k+")");
//		System.out.println("currentUpperAngle = " + currentUpperAngle);
		
		double r = 5;
		double x0 = cube.centre.x + currentUpperAngle.x - r/2;
		double y0 = cube.centre.y + currentUpperAngle.y - r/2;
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