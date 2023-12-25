
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
	
	public Color[][][][] tile = new Color[Cube.DIMENSION][Axis.ENDS_QUANTITY][3][3];
	
	public Point centre = Point.NULL;
	public Address3D nearestAngleAddress; 
	public Point nearestAngle;
	
	ArrayList<Edge> edgeCollection = new ArrayList<Edge>();

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
		
		transitionMatrix = Matrix.identity(Cube.DIMENSION);
		if (rotatableAxisIndex != Cube.DIMENSION - 1)
			transitionMatrix = transitionMatrix.swapIdentity(1 - rotatableAxisIndex, Cube.DIMENSION - 1);

		rotate(rotationAngle);

		CubeBasis basis = new CubeBasis(cube);

		for (int i = 0; i < cube.DIMENSION; i++){
			
			int currentBeginingIndex = (i == rotatableAxisIndex ? beginingIndex : 0);
			int currentEndingIndex = (i == rotatableAxisIndex ? endingIndex : cube.size - 1);
			
			this.axis[i] = new Axis(i, basis, cube.size, cube.centre, currentBeginingIndex, currentEndingIndex); // сообщать оси центр куба не нужно?
		}
			
		this.CalculateCenter();
		this.CalculateAngles();
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			
			int axisIndex = axisIndexAfterRotation(i);
			
		    for (int n = 0; n < Axis.ENDS_QUANTITY; n++){
		    	
		    	int positionAtAxis = positionAtAxisAfterRotation(i, n);
		    	boolean isNotEmptyEdge = true; 
		    	
			    for (int j = 0; j < cube.size; j++)
				    for (int k = 0; k < cube.size; k++){
				    	
				    	if(isNotEmptyEdge) tile[i][n][j][k] = cube.tile[axisIndex][positionAtAxis][j][k];
				    	else tile[axisIndex][positionAtAxis][j][k] = Color.WHITE;
				    }
			}
		}
	}
		
	// Functions

	public int axisIndexAfterRotation(int i){
		
		if (rotatableAxisIndex == Cube.DIMENSION - 1 || i == rotatableAxisIndex) return i;
		else return Cube.DIMENSION - rotatableAxisIndex - i;
		
	}
    

	public int positionAtAxisAfterRotation(int i, int n){
		
		if (rotatableAxisIndex == Cube.DIMENSION - 1 || i != 1 - rotatableAxisIndex) return n;
		else return 1 - n;
		
	}
    
	
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
	
		for(int i = 0; i < Axis.ENDS_QUANTITY; i++){
			for(int j = 0; j < Axis.ENDS_QUANTITY; j++){
				for(int k = 0; k < Axis.ENDS_QUANTITY; k++){
					
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
	
	
	public static void updateCollection(ArrayList<Block> blockCollect){ /// , ArrayList<Edge> edgeCollection){

		for(Block block : blockCollect) block.update();

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
				
//		if(up || left || right || down || rotateI || rotateJ || rotateK)
//			this.upperAngle = this.upperAngleAddress();
		
		rotationAngle = rotationAngle + speed;
		
		while(rotationAngle > Math.PI) rotationAngle -= 2 * Math.PI;
		while(rotationAngle < -Math.PI) rotationAngle += 2 * Math.PI;
		
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
		
		edgeCollection.clear();
		
		for(int i = 0; i < Cube.DIMENSION; i++)
			edgeCollection.add(new Edge(this, i, nearestAngleAddress.getCoordinate(i)));

	}
	
	
	public Point angleAtAddress(Address3D address){
		return angle[address.i][address.j][address.k];
	}
		
	
	public Address3D nearestAngleAddress(){
		
		Address3D selectedAngleAddress = new Address3D(0, 0, 0);
		Point selectedAngle = angleAtAddress(selectedAngleAddress);
		//System.out.println("   Angle["+0+","+0+","+0+"]: z = "+upperAngle.z);
				
		for (int i = 0; i < angle.length; i++){
		    for (int j = 0; j < angle[i].length; j++){
			    for (int k = 0; k < angle[i][j].length; k++){
			    	
			    	Address3D currentAddress = new Address3D(i, j, k);
			    	Point currentAngle = angleAtAddress(currentAddress);
			    	if (selectedAngle.z < currentAngle.z){
			    		selectedAngleAddress = currentAddress;
			    		selectedAngle = angleAtAddress(selectedAngleAddress);
					}
				}
			}
	    }
		
		return selectedAngleAddress;
		
	}
	

	public void draw(Graphics2D g, Color color){
		
		for(Edge edge : edgeCollection){
			
			Point angle00 = angleAtAddress(edge.anglesAddresses[0][0]);
			Point angle01 = angleAtAddress(edge.anglesAddresses[0][1]);
			Point angle10 = angleAtAddress(edge.anglesAddresses[1][0]);
			Point angle11 = angleAtAddress(edge.anglesAddresses[1][1]);
			
   	    	GamePanel.drawLine(g, angle00, angle01, cube.centre, color);
	    	GamePanel.drawLine(g, angle01, angle11, cube.centre, color);
	    	GamePanel.drawLine(g, angle11, angle10, cube.centre, color);
	    	GamePanel.drawLine(g, angle10, angle00, cube.centre, color);
	    	
    		int argX[] = {(int)angle00.x, (int)angle01.x, (int)angle11.x, (int)angle10.x};
      		int argY[] = {(int)angle00.y, (int)angle01.y, (int)angle11.y, (int)angle10.y};
      		
//	   		g.setColor(Color.WHITE);
//	   		g.fillPolygon(argX, argY, 4);
//	   		
//	   		System.out.println("[0][0] = "); edge.anglesAddresses[0][0].print();
//	   		System.out.println("[0][1] = "); edge.anglesAddresses[0][1].print();
//	   		System.out.println("[1][0] = "); edge.anglesAddresses[1][0].print();
//	   		System.out.println("[1][1] = "); edge.anglesAddresses[1][1].print();
      		
      		edge.draw(g);
	   		      		
		}
	
		nearestAngle.draw(g, cube.centre, Color.RED, 5);

	}
	
	
	public static void drawCollection(Graphics2D g, ArrayList<Block> blockCollection){
		
		Block.arrangeCollection(blockCollection, g);
		
		Color color = Color.MAGENTA;
				
		for(Block block : blockCollection){
			block.draw(g, color);
			if(color == Color.MAGENTA) color = Color.GRAY;
			else if(color == Color.GRAY) color = Color.LIGHT_GRAY;
			
		}
		
	}


	public static void arrangeCollection(ArrayList<Block> collection, Graphics2D g){
		
		for(int n = 0; n < collection.size() - 1; n++){
			for(int m = n + 1; m < collection.size(); m++){

				Block blockN = collection.get(n);
				Block blockM = collection.get(m);
				
				int n_hasLargerOrSmallerZ_m = blockN.hasLargerOrSmallerZ(blockM, g, null); // Color.GREEN);
				
				if(n_hasLargerOrSmallerZ_m == 1){
					collection.set(n, blockM);
					collection.set(m, blockN);
				}
				if(n_hasLargerOrSmallerZ_m != 0) continue;
				
				int m_hasLargerOrSmallerZ_n = blockM.hasLargerOrSmallerZ(blockN, g, Color.GRAY);
				
				if(m_hasLargerOrSmallerZ_n == -1){
					collection.set(n, blockM);
					collection.set(m, blockN);
				}
			}
		}
	}
	
	
	public int hasLargerOrSmallerZ (Block bloсkA, Graphics2D g, Color color){
		
		Block blockN = this;
		Block blockM = bloсkA;
		
		Address3D nearestAngleAddressN = blockN.nearestAngleAddress;
				
		Point[] visibleAngleN = new Point[7];
		visibleAngleN[0] = blockN.angle[    nearestAngleAddressN.i][    nearestAngleAddressN.j][    nearestAngleAddressN.k];
		visibleAngleN[1] = blockN.angle[    nearestAngleAddressN.i][    nearestAngleAddressN.j][1 - nearestAngleAddressN.k];
		visibleAngleN[2] = blockN.angle[    nearestAngleAddressN.i][1 - nearestAngleAddressN.j][    nearestAngleAddressN.k];
		visibleAngleN[3] = blockN.angle[1 - nearestAngleAddressN.i][    nearestAngleAddressN.j][    nearestAngleAddressN.k];
		visibleAngleN[4] = blockN.angle[    nearestAngleAddressN.i][1 - nearestAngleAddressN.j][1 - nearestAngleAddressN.k];
		visibleAngleN[5] = blockN.angle[1 - nearestAngleAddressN.i][    nearestAngleAddressN.j][1 - nearestAngleAddressN.k];
		visibleAngleN[6] = blockN.angle[1 - nearestAngleAddressN.i][1 - nearestAngleAddressN.j][    nearestAngleAddressN.k];
				
		int testI = 10;
		int testII = 20;
		
		int ii = 0;
		
		for (Point angleN : visibleAngleN){
			
			ii++;
//			if(ii==3) {
//				angleN.draw(g, cube.centre, Color.GREEN, 9);
//				System.out.println("3   angleN: x = " + angleN.x + ", y = " + angleN.y + ", z = " + angleN.z);
//			}
			if(ii == testII) {
				//angleN.draw(g, cube.centre, Color.MAGENTA, 9);
				System.out.println("   angleN: x = " + angleN.x + ", y = " + angleN.y + ", z = " + angleN.z);
			}
			
			//angleN.draw(g, cube.centre, Color.GREEN, 9);
			
			int i = 0;
			
			for (Edge edgeM : blockM.edgeCollection){
				
				//if(color != null && i == testI) edgeM.drawAngles(g, color);
				
				Proection proectionNM = angleN.ProectionOnEdge(edgeM);
				
//				if(ii==3) {
//					proectionNM.point.draw(g, cube.centre, Color.GRAY, 12);
//					System.out.println("      edge index = " + i + ". proectionNM.inside = " + proectionNM.inside + ". proectionNM: х = " + proectionNM.point.x + ", y = " + proectionNM.point.y + " z = " + proectionNM.point.z);
//				}
				if(ii == testII) {
					if(i == testI){
						proectionNM.pointI.draw(g, cube.centre, Color.DARK_GRAY, 10);
						proectionNM.pointJ.draw(g, cube.centre, Color.DARK_GRAY, 10);
						proectionNM.point.draw(g, 
								cube.centre, 
								proectionNM.point.z < angleN.z ? Color.DARK_GRAY : Color.GRAY, 
								proectionNM.inside ? 15 : 8);
						
						Point ang00 = edgeM.block.angleAtAddress(edgeM.anglesAddresses[0][0]);
						Point ang01 = edgeM.block.angleAtAddress(edgeM.anglesAddresses[0][1]);
						Point ang10 = edgeM.block.angleAtAddress(edgeM.anglesAddresses[1][0]);
						Point ang11 = edgeM.block.angleAtAddress(edgeM.anglesAddresses[1][1]);
						//angleN.draw(g, cube.centre, Color.GREEN, 2);
						ang00.draw(g, cube.centre, Color.RED, 10);
						ang01.draw(g, cube.centre, Color.RED, 5);
						ang10.draw(g, cube.centre, Color.RED, 5);
						ang11.draw(g, cube.centre, Color.RED, 5);
//						GamePanel.drawLine(g, ang00, proectionNM.point, edgeM.block.cube.centre);
//						GamePanel.drawLine(g, ang01, proectionNM.point, edgeM.block.cube.centre);
//						GamePanel.drawLine(g, ang10, proectionNM.point, edgeM.block.cube.centre);
						GamePanel.drawLine(g, proectionNM.pointI, angleN, edgeM.block.cube.centre, Color.GREEN);
						GamePanel.drawLine(g, proectionNM.pointJ, angleN, edgeM.block.cube.centre, Color.GREEN);
						GamePanel.drawLine(g, proectionNM.pointI, ang00, edgeM.block.cube.centre, Color.GREEN);
						GamePanel.drawLine(g, proectionNM.pointJ, ang00, edgeM.block.cube.centre, Color.GREEN);
						GamePanel.drawLine(g, angleN, ang00, edgeM.block.cube.centre, Color.GREEN);
						System.out.println("      edge index = " + i + ". ang00: х = " + ang00.x + ", y = " + ang00.y + " z = " + ang00.z);
						System.out.println("      edge index = " + i + ". proectionNM.vI: dх = " + proectionNM.vI.dx + ", dy = " + proectionNM.vI.dy + " dz = " + proectionNM.vI.dz);
						System.out.println("      edge index = " + i + ". proectionNM.vJ: dх = " + proectionNM.vJ.dx + ", dy = " + proectionNM.vJ.dy + " dz = " + proectionNM.vJ.dz);
						System.out.println("      edge index = " + i + ". proectionNM.inside = " + proectionNM.inside + ". proectionNM: х = " + proectionNM.point.x + ", y = " + proectionNM.point.y + " z = " + proectionNM.point.z);
						System.out.println("      edge index = " + i + ". coefI = " + proectionNM.coefI + ", coefJ = " + proectionNM.coefJ);
					}
				}
				
				i++;
				
				if(proectionNM.inside){
					if(proectionNM.point.z < angleN.z) return 1;
					else if (proectionNM.point.z > angleN.z) return -1;
				}
			}
		}
		return 0;
	}
	
	
	public Point returnAndDrawAngle(int i, int j, int k, Graphics2D g){
		
		Point angle0 = angle[i][j][k];
		//angle0.draw(g, cube.centre);
		
		return angle0;
	}
		
}