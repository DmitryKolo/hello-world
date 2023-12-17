
import java.awt.*;
import java.util.*;

public class Block {

	// Fields
	
	//public Matrix transitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	public Matrix transitionMatrix;
	
	public Cube cube; //1
	public Axis[] axis = new Axis[Cube.DIMENSION];
	public Point[][][] angle = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	
	public Point centre = Point.NULL;
	private Address3D upperAngle; 

	public final int rotatableAxisIndex;//2
	public double rotationAngle;//3
	
	//int BeginIndex; //4
	//int EndIndex; //5
	
	private double speed = 0.04;

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
		this.rotationAngle = 0;
		
		//ArrayList<ArrayList<ArrayList<int[]>>> blockCoords = BlockCoords(endingIndex - beginingIndex + 1);
		
		double[] shift = {beginingIndex, beginingIndex, beginingIndex};
		Matrix identityMatrix = Matrix.identity(Cube.DIMENSION);
		if (rotatableAxisIndex != Cube.DIMENSION - 1)
			identityMatrix = identityMatrix.swapIdentity(rotatableAxisIndex, 1 - rotatableAxisIndex);
		transitionMatrix = identityMatrix.attachArray(shift);
		
		Matrix anglesMatrix = new Matrix(8, 3);
		
		anglesMatrix.data[1][0] = 1;
		anglesMatrix.data[2][1] = 2;
		anglesMatrix.data[3][0] = 1;
		anglesMatrix.data[3][1] = 2;
		
		anglesMatrix.data[4][2] = 3;
		anglesMatrix.data[5][2] = 3;
		anglesMatrix.data[6][2] = 3;
		anglesMatrix.data[7][2] = 3;

		anglesMatrix.data[4][0] = 1;
		anglesMatrix.data[5][1] = 2;
		anglesMatrix.data[6][0] = 1;
		anglesMatrix.data[7][1] = 2;
		
		for(int i = 0; i < 8; i++){
			Matrix columnX = anglesMatrix.nPlus1Column(i);
			Matrix columnY = transitionMatrix.times(columnX);
			int j = i;
			j++;
		}

		CubeBasis basis = new CubeBasis(cube);

		for (int i = 0; i < cube.DIMENSION; i++){
			
			int currentBeginingIndex = (i == rotatableAxisIndex ? beginingIndex : 0);
			int currentEndingIndex = (i == rotatableAxisIndex ? endingIndex : cube.size - 1);
			
			this.axis[i] = new Axis(i, basis, cube.size, cube.centre, currentBeginingIndex, currentEndingIndex); // сообщать оси центр куба не нужно?
		}
			
		this.CalculateCenter();
		this.CalculateAngles();
		
	}
	
	
	// Functions
	
//	public ArrayList<ArrayList<ArrayList<int[]>>> BlockCoords(int height){
//		
//		ArrayList<ArrayList<ArrayList<int[]>>> blockCoords;
//		ArrayList<ArrayList<int[]>> edgeCoords;
//		ArrayList<int[]> rowCoords;
//
//		blockCoords = new ArrayList<ArrayList<ArrayList<int[]>>>();
//		for(int k = 0; k < height; k++){
//			edgeCoords = new ArrayList<ArrayList<int[]>>();
//			for(int i = 0; i < Cube.DIMENSION; i++){
//				rowCoords = new ArrayList<int[]>();
//				for(int j = 0; j < Cube.DIMENSION; j++){
//					int[] address = {i, j, k};
//					rowCoords.add(address);
//				}
//				edgeCoords.add(rowCoords);
//			}
//			blockCoords.add(edgeCoords);
//		}
//		
//		int[] ff = blockCoords.get(0).get(2).get(1);
//		int y = ff[1];
//		
//		int[][][][] asd = new int[Cube.DIMENSION][Cube.DIMENSION][Cube.DIMENSION][Cube.DIMENSION];
//		asd[1][2][2][1] = 78;
//		return blockCoords;
//	}
		
	public void RecalculateBasis(){
			
		CubeBasis basis = new CubeBasis(cube);

		for (int i = 0; i < cube.DIMENSION; i++)
			axis[i].vector = new Vector(1, basis.vector[i]);

		Axis rotatableAxis = axis[rotatableAxisIndex];
		rotateBasis(rotatableAxis.vector, rotationAngle);

	}
	
	
	public void CalculateCenter(){
		
		centre = Point.NULL;
		
		if(rotatableAxisIndex<Cube.DIMENSION){
		
			CubeBasis basis = new CubeBasis(cube);
			Axis rotatableAxis = axis[rotatableAxisIndex];

			double blockMiddle = (rotatableAxis.beginingIndex + rotatableAxis.endingIndex) / 2.0;
			double cubeMiddle = (cube.size - 1) / 2.0;
				
			if(blockMiddle != cubeMiddle){
				double shiftMiddle = blockMiddle - cubeMiddle;
				centre = new Point(centre, new Vector(shiftMiddle, rotatableAxis.vector));
			}
		}
	}
	
	
	public void CalculateAngles(){
		
		double[] halfSize = new double[Cube.DIMENSION];

		for (int i = 0; i < cube.DIMENSION; i++)
			halfSize[i] = (axis[i].endingIndex - axis[i].beginingIndex + 1) / 2.0;
		

		for (int i = 0; i < angle.length; i++){
			
			double coefI = (i==0 ? -halfSize[0] : halfSize[0]);
			Point pointI = new Point(this.centre, new Vector(coefI, axis[0].vector));				
						
		    for (int j = 0; j < angle[i].length; j++){
		    	
				double coefJ = (j==0 ? -halfSize[1] : halfSize[1]);
				Point pointJ = new Point(pointI, new Vector(coefJ, axis[1].vector));				
				
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
					double coefK = (k==0 ? -halfSize[2] : halfSize[2]);
			    	this.angle[i][j][k] = new Point(pointJ, new Vector(coefK, axis[2].vector));
			    }
		    }
	    }
		
		this.upperAngle = this.upperAngleAddress();
	}
	
	
	public void rotateXYZ(Vector rotateAxis, double rotateAngle){
		
		Vector normalAxis = new Vector(rotateAxis);
		
//		for (int i = 0; i < angle.length; i++)
//		    for (int j = 0; j < angle[i].length; j++)
//			    for (int k = 0; k < angle[i][j].length; k++){
//					angle[i][j][k].rotateX(rotateAngle * normalAxis.dx); 
//					angle[i][j][k].rotateY(rotateAngle * normalAxis.dy); 
//					angle[i][j][k].rotateZ(rotateAngle * normalAxis.dz); 
//			    }

		centre.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz);
		
//		for (int i = 0; i < axis.length; i++){
//			//System.out.println("Block vector ["+i+"] sta = ("+axis[i].vector.dx+", "+axis[i].vector.dy+", "+axis[i].vector.dz+")");
//			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
//			//System.out.println("Block vector ["+i+"] end = ("+axis[i].vector.dx+", "+axis[i].vector.dy+", "+axis[i].vector.dz+")");
//		}	
		rotateBasis(rotateAxis, rotateAngle);
		CalculateAngles();
	}	
	
	
	public void rotateBasis(Vector rotateAxis, double rotateAngle){
		
		Vector normalAxis = new Vector(rotateAxis);
	
		for (int i = 0; i < axis.length; i++)
			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
	}
	
	public void update(){
		
		double rotateAngle = speed;

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
				rotationAngle += rotateAngle;
				RecalculateBasis();
				CalculateCenter();
				CalculateAngles();
				//rotateXYZ(axis[0].vector, rotateAngle);
			}

		if(rotateJ){
			if (rotatableAxisIndex == 1){
				if(shift) rotateAngle = -rotateAngle;
				rotationAngle += rotateAngle;
				RecalculateBasis();
				CalculateCenter();
				CalculateAngles();
				//rotateXYZ(axis[1].vector, rotateAngle);
			}
//			else{
//				double cos1 = axis[1].vector.cos(cube.axis[1].vector);
//				System.out.println("Angle 1 cos = "+cos1);
//				rotateAngle = Math.acos(cos1);
//				System.out.println("rotateAngle = "+rotateAngle);
//				rotateXYZ(axis[0].vector, rotateAngle);
////				ff = -1.000; af = Math.acos(ff); System.out.println("Math.acos("+ff+") = "+af+"; cos = "+Math.cos(af));
//				rotateJ = false;
//			}
		}

		if(rotateK && rotatableAxisIndex == 2){
				if(!shift) rotateAngle = -rotateAngle;
				rotationAngle += rotateAngle;
//				RecalculateBasis();
//				CalculateCenter();
//				CalculateAngles();
				rotateXYZ(axis[2].vector, rotateAngle);
			}
				
		if(up || left || right || down || rotateI || rotateJ || rotateK)
			this.upperAngle = this.upperAngleAddress();
	}
	
	public void draw(Graphics2D g, Color color){
		
		g.setColor(color);
		
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
		    	
		    	Point angle0;
		    	Point angle1;
		    	
		    	if (i == upperAngle.j || j == upperAngle.k) {
		    		g.setColor(Color.RED);
			    	angle0 = angle[0][i][j];
			    	angle1 = angle[1][i][j];
			    	GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
				
		    	if (i == upperAngle.i || j == upperAngle.k) {
		    		g.setColor(Color.ORANGE);
			    	angle0 = angle[i][0][j];
			    	angle1 = angle[i][1][j];
			    	GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
		    	
		    	if (i == upperAngle.i || j == upperAngle.j) {
		    		g.setColor(Color.GREEN);
		    		angle0 = angle[i][j][0];
		    		angle1 = angle[i][j][1];
		    		GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
		    }
	    }
		
		g.setColor(Color.RED);
		
		Point currentUpperAngle = angle[upperAngle.i][upperAngle.j][upperAngle.k];
		
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
	
	public static void drawCollection(Graphics2D g, ArrayList<Block> blockCollect){
		//System.out.println("drawCollection");
		for(Block block : blockCollect){
			block.draw(g, Color.MAGENTA);
		}
	}
	
}