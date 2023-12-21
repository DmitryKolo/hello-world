
import java.awt.*;
import java.util.*;

public class Block {

	// Fields
	
	//public Matrix transitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION + 1);
	public Matrix transitionMatrix = new Matrix(Cube.DIMENSION, Cube.DIMENSION);
	
	public Cube cube; 
	
	public final int rotatableAxisIndex;
	public int beginingIndex, endingIndex;
	public double rotationAngle;
	
	public Axis[] axis = new Axis[Cube.DIMENSION];
	public Point[][][] anglesInBasis = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	public Point[][][] angle = new Point[Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY][Axis.ENDS_QUANTITY];
	
	public Point centre = Point.NULL;
	public Address3D nearestAngleAddress; 
	public Point nearestAngle;

	private double speed;

	public static boolean shift;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;

	public static boolean rotateI;
	public static boolean rotateJ;
	public static boolean rotateK;

	// Constructor
	
	public Block(Cube cube, int rotatableAxisIndex, int beginingIndex, int endingIndex, double speed){
			
		this.cube = cube;
		this.rotatableAxisIndex = rotatableAxisIndex;
		this.beginingIndex = beginingIndex;
		this.endingIndex = endingIndex;

		this.rotationAngle = 0;
		this.speed = speed;
		
//		Matrix identityMatrix = Matrix.identity(Cube.DIMENSION);
//		if (rotatableAxisIndex != Cube.DIMENSION - 1)
//			identityMatrix = identityMatrix.swapIdentity(rotatableAxisIndex, Cube.DIMENSION - 1);
		//transitionMatrix = identityMatrix.attachArray(shift);
		transitionMatrix = Matrix.identity(Cube.DIMENSION);
		if (rotatableAxisIndex != Cube.DIMENSION - 1)
			transitionMatrix = transitionMatrix.swapIdentity(rotatableAxisIndex, Cube.DIMENSION - 1);

//		for(int i = 0; i < 2; i++){
//			for(int j = 0; j < 2; j++){
//				for(int k = 0; k < 2; k++){
//					Matrix columnX = new Matrix(Cube.DIMENSION, 1);
//					columnX.data[0][0] = (- 0.5 + i) * Cube.DIMENSION;
//					columnX.data[1][0] = (- 0.5 + j) * Cube.DIMENSION;
//					columnX.data[2][0] = - 0.5 * Cube.DIMENSION + beginingIndex + k * (endingIndex - beginingIndex + 1);
//					//columnX.data[3][0] = 1;
//					Matrix columnY = transitionMatrix.times(columnX);
//					anglesInBasis[i][j][k] = new Point(columnY.data[0][0], columnY.data[1][0], columnY.data[2][0]);
//				}
//			}
//		}
		
		rotate(rotationAngle);

		CubeBasis basis = new CubeBasis(cube);

		for (int i = 0; i < cube.DIMENSION; i++){
			
			int currentBeginingIndex = (i == rotatableAxisIndex ? beginingIndex : 0);
			int currentEndingIndex = (i == rotatableAxisIndex ? endingIndex : cube.size - 1);
			
			this.axis[i] = new Axis(i, basis, cube.size, cube.centre, currentBeginingIndex, currentEndingIndex); // �������� ��� ����� ���� �� �����?
		}
			
		this.CalculateCenter();
		this.CalculateAngles();
		
	}
	
	
	// Functions
	
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
		
		//Address3D upperAngle = this.upperAngleAddress();
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
	
	
	public void rotate(double rotateAngleZ){
	
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				for(int k = 0; k < 2; k++){
					
					double dx = (- 0.5 + i) * Cube.DIMENSION;
					double dy = (- 0.5 + j) * Cube.DIMENSION;
					double dz =  - 0.5 * Cube.DIMENSION + beginingIndex + k * (endingIndex - beginingIndex + 1);
					
					if(rotateAngleZ != 0){
						double atan2 = Math.atan2(dy, dx) + rotateAngleZ;
						double r = Math.sqrt(0.5) * Cube.DIMENSION;
						dx = Math.cos(atan2) * r;
						dy = Math.sin(atan2) * r;
					}
				
					anglesInBasis[i][j][k] = transitionMatrix.timesVector( new Vector(dx, dy, dz) );
				}
			}
		}
	}
	
	
	public void rotateBasis(Vector rotateAxis, double rotateAngle){
		
		Vector normalAxis = new Vector(rotateAxis);
	
		for (int i = 0; i < axis.length; i++)
			axis[i].vector.rotateXYZ(rotateAngle * normalAxis.dx, rotateAngle * normalAxis.dy, rotateAngle * normalAxis.dz); 
	}
	
	
	public void update(ArrayList<Edge> edgeCollection){
		
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
				
//		if(up || left || right || down || rotateI || rotateJ || rotateK)
//			this.upperAngle = this.upperAngleAddress();
		
		rotationAngle = rotationAngle + speed;
		
		while(rotationAngle > Math.PI) rotationAngle -= 2 * Math.PI;
		while(rotationAngle < -Math.PI) rotationAngle += 2 * Math.PI;
		
		//System.out.println("rotationAngle = " + rotationAngle);
		
		rotate(rotationAngle);
		
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				for(int k = 0; k < 2; k++){
					Matrix columnX = new Matrix(Cube.DIMENSION + 1, 1);
					columnX.data[0][0] = anglesInBasis[i][j][k].x;
					columnX.data[1][0] = anglesInBasis[i][j][k].y;
					columnX.data[2][0] = anglesInBasis[i][j][k].z;
					columnX.data[3][0] = 1;
					Matrix columnY = cube.transitionMatrix.times(columnX);
					angle[i][j][k] = new Point(columnY.data[0][0], columnY.data[1][0], columnY.data[2][0]);
				}
			}
		}
		
		nearestAngleAddress = nearestAngleAddress();
		nearestAngle = angleAtAddress(nearestAngleAddress);
		
		edgeCollection.add(new Edge(0, 0, 0, 0, 0, 0));

	}
	
	
	public void draw(Graphics2D g, Color color){
		
		for (int i = 0; i < Cube.DIMENSION; i++){
			Point angle00 = nearestAngle;
			Point angle01;
			Point angle10;
			Point angle11;
			switch(i){
			case 0: 
				angle01 = angleAtAddress( new Address3D(    nearestAngleAddress.i,     nearestAngleAddress.j, 1 - nearestAngleAddress.k) );
				angle10 = angleAtAddress( new Address3D(    nearestAngleAddress.i, 1 - nearestAngleAddress.j,     nearestAngleAddress.k) );
				angle11 = angleAtAddress( new Address3D(    nearestAngleAddress.i, 1 - nearestAngleAddress.j, 1 - nearestAngleAddress.k) );
				break;
			case 1: 
				angle01 = angleAtAddress( new Address3D(    nearestAngleAddress.i,     nearestAngleAddress.j, 1 - nearestAngleAddress.k) );
				angle10 = angleAtAddress( new Address3D(1 - nearestAngleAddress.i,     nearestAngleAddress.j,     nearestAngleAddress.k) );
				angle11 = angleAtAddress( new Address3D(1 - nearestAngleAddress.i,     nearestAngleAddress.j, 1 - nearestAngleAddress.k) );
				break;
			default: 
				angle01 = angleAtAddress( new Address3D(    nearestAngleAddress.i, 1 - nearestAngleAddress.j,     nearestAngleAddress.k) );
				angle10 = angleAtAddress( new Address3D(1 - nearestAngleAddress.i,     nearestAngleAddress.j,     nearestAngleAddress.k) );
				angle11 = angleAtAddress( new Address3D(1 - nearestAngleAddress.i, 1 - nearestAngleAddress.j,     nearestAngleAddress.k) );
				break;
			}
      		g.setColor(Color.WHITE);
		
      		int argX[] = {(int)angle00.x, (int)angle01.x, (int)angle11.x, (int)angle10.x};
      		int argY[] = {(int)angle00.y, (int)angle01.y, (int)angle11.y, (int)angle10.y};
      		g.fillPolygon(argX, argY, 4);
		}

		g.setColor(color);
		
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
		    	
		    	Point angle0;
		    	Point angle1;
		    	
		    	if (i == nearestAngleAddress.j || j == nearestAngleAddress.k) {
		    		g.setColor(Color.RED);
			    	angle0 = angle[0][i][j];
			    	angle1 = angle[1][i][j];
			    	GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
				
		    	if (i == nearestAngleAddress.i || j == nearestAngleAddress.k) {
		    		g.setColor(Color.RED);
			    	angle0 = angle[i][0][j];
			    	angle1 = angle[i][1][j];
			    	GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
		    	
		    	if (i == nearestAngleAddress.i || j == nearestAngleAddress.j) {
		    		g.setColor(Color.RED);
		    		angle0 = angle[i][j][0];
		    		angle1 = angle[i][j][1];
		    		GamePanel.drawLine(g, angle0, angle1, cube.centre);
		    	}
		    }
	    }
		
		g.setColor(Color.RED);

		double r = 5;
		double x0 = cube.centre.x + nearestAngle.x - r/2;
		double y0 = cube.centre.y + nearestAngle.y - r/2;
		g.fillOval((int)x0, (int)y0, (int)r, (int)r);

	}
	
	
	public Point angleAtAddress(Address3D address){
		return angle[address.i][address.j][address.k];
	}
		
	public Address3D nearestAngleAddress(){
		
		Address3D upperAngleAddress = new Address3D(0, 0, 0);
		Point upperAngle = angleAtAddress(upperAngleAddress);
		//System.out.println("   Angle["+0+","+0+","+0+"]: z = "+upperAngle.z);
				
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
			    	Address3D currentAddress = new Address3D(i, j, k);
			    	Point currentAngle = angleAtAddress(currentAddress);
			    	if (upperAngle.z > currentAngle.z){
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
		
		//System.out.println("Collection");
		
//		for(Block block : blockCollect){
//			block.nearestAngle = block.angleAtAddress( block.nearestAngleAddress() );
//			System.out.println("block.nearestAngle.z = " + block.nearestAngle.z);
//			//block.draw(g, Color.MAGENTA);
//		}

		for(int i = 0; i < blockCollect.size() - 1; i++){
			for(int j = i + 1; j < blockCollect.size(); j++){
				if(blockCollect.get(i).nearestAngle.z < blockCollect.get(j).nearestAngle.z){
					Block temp = blockCollect.get(i);
					blockCollect.set(i, blockCollect.get(j));
					blockCollect.set(j, temp);
					//blockCollect.System.out.println("Collection swap: " + i + " " + j);
				}
			}
		}

		//System.out.println("draw Collection");
		
//		for(Block block : blockCollect){
//		//block.nearestAngle = block.angleAtAddress( block.nearestAngleAddress() );
//		System.out.println("block.nearestAngle.z = "+block.nearestAngle.z);
//		block.draw(g, Color.MAGENTA);
		for(Block block : blockCollect){
		//block.nearestAngle = block.angleAtAddress( block.nearestAngleAddress() );
		//System.out.println("block.nearestAngle.z = " + block.nearestAngle.z);
		block.draw(g, Color.MAGENTA);
		}
	}
	
	
	public static void updateCollection(ArrayList<Block> blockCollect, ArrayList<Edge> edgeCollection){
		edgeCollection.clear();
		for(Block block : blockCollect){
			block.update(edgeCollection);
			System.out.println("edgeCollection size = " + edgeCollection.size());
		}
	}
	
}