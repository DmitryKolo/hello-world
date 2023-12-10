
import java.awt.*;

public class Block {

	// Fields
	
	public Cube cube;
	public Axis[] axis = new Axis[Cube.DIMENSION];
	public Point[][][] angle = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	
	//public Point centre;
	private Address3D upperAngle; 

	public final int rotatebledAxisNumber;
	private boolean turned;
	
	private double speed = 1;

	public boolean shift;

	public boolean rotateI;
	public boolean rotateJ;
	public boolean rotateK;

	// Constructor
	
	public Block(Cube cube, int rotatebledAxisNumber, int beginingIndex, int endingIndex){
			
		this.cube = cube;
		this.rotatebledAxisNumber = rotatebledAxisNumber;
		
		CubeBasis basis = new CubeBasis(cube);
//		if(rotatebledAxisNumber != Cube.DIMENSION){
//			basis.vector[rotatebledAxisNumber] = Vector.NULL;
//		}
		
		int rotatebledAxisSize = endingIndex -  beginingIndex + 1;
		int[] size = new int[Cube.DIMENSION];
		double[] halfSize = new double[Cube.DIMENSION];

		for (int i = 0; i < cube.DIMENSION; i++){
			size[i] = (i == rotatebledAxisNumber ? rotatebledAxisSize : cube.size);
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
//			    	Point CurrentCorner = this.angle[i][j][k];
//					System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
			    }
		    }
	    }
		
		this.upperAngle = this.upperAngleAddress();
		
//		// to improve: first, create a three-dimensional array of vertices (angle), then specify them in the edges of the axes
////		edgesPairA = new PairOfEdges(size, centre, vectorA, vectorB, vectorC);
////		edgesPairB = new PairOfEdges(size, centre, vectorB, vectorC, vectorA);
////		edgesPairC = new PairOfEdges(size, centre, vectorC, vectorA, vectorB);
//		
////		this.axis[0] = new Axis(vectorA, edgesPairA.edge0, edgesPairA.edge1); 
////		this.axis[1] = new Axis(vectorB, edgesPairB.edge0, edgesPairB.edge1);
////		this.axis[2] = new Axis(vectorC, edgesPairC.edge0, edgesPairC.edge1);
//		
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
//	
//	public void rotateV(Vector rotateAxis, double rotateAngle){
//		
//		Vector normalAxis = new Vector(rotateAxis);
//		
//		for (int i = 0; i < angle.length; i++)
//		    for (int j = 0; j < angle[i].length; j++)
//			    for (int k = 0; k < angle[i][j].length; k++){
//					angle[i][j][k].rotateX(rotateAngle * normalAxis.dx); 
//					angle[i][j][k].rotateY(rotateAngle * normalAxis.dy); 
//					angle[i][j][k].rotateZ(rotateAngle * normalAxis.dz); 
//			    }
//		
//		for (int i = 0; i < axis.length; i++){
//			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
//		}	
//	}
//	
//	public void update(){
//		
//		double rotateAngle = speed/20;
//
//		if(up){
//			if(shift) rotateAngle = -rotateAngle;
//			rotateX(rotateAngle);
//		}
//
//		if(left){
//			if(!shift) rotateAngle = -rotateAngle;
//			rotateY(rotateAngle);
//		}
//
//		if(right){
//			if(shift) rotateAngle = -rotateAngle;
//			rotateZ(rotateAngle);
//		}
//		
//		if(down){
//			if(!shift) rotateAngle = -rotateAngle;
//			rotateX(rotateAngle);
//		}
//		
//		if(rotateI){
//			if(!shift) rotateAngle = -rotateAngle;
//			//Vector rotateVector = new Vector(basis.vector[0].dx, basis.vector[1].dx, basis.vector[2].dx);
//			rotateV(basis.vector[0], rotateAngle);
//			//rotateV(rotateVector, rotateAngle);
//		}
//
//		if(rotateJ){
//			if(shift) rotateAngle = -rotateAngle;
//			//Vector rotateVector = new Vector(basis.vector[0].dy, basis.vector[1].dy, basis.vector[2].dy);
//			rotateV(basis.vector[1], rotateAngle);
//			//rotateV(rotateVector, rotateAngle);
//		}
//		
//		if(rotateK){
//			if(!shift) rotateAngle = -rotateAngle;
//			//Vector rotateVector = new Vector(basis.vector[0].dz, basis.vector[1].dz, basis.vector[2].dz);
//			rotateV(basis.vector[2], rotateAngle);
//			//rotateV(rotateVector, rotateAngle);
//		}
//		
//		if(up || left || right || down || rotateI || rotateJ || rotateK){
//			this.upperAngle = this.upperAngleAddress();
////			for (int i = 0; i < angle.length; i++){
////			    for (int j = 0; j < angle[i].length; j++){
////				    for (int k = 0; k < angle[i][j].length; k++){
////				    	Point CurrentCorner = this.angle[i][j][k];
////						System.out.println("Angle["+i+","+j+","+k+"] = ("+CurrentCorner.x+", "+CurrentCorner.y+", "+CurrentCorner.z+")");
////					}
////				}
////			}
////			System.out.println("upperAngleAddress = ("+upperAngle.i+", "+upperAngle.j+", "+upperAngle.k+")");
//		}
//	}
//			
	
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