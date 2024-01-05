
import java.awt.*;
import java.util.ArrayList;

public class Tile{
	
	// Fields
	
	//public Point centre;
	
	private double x;
	private double y;
	private double dxA;
	private double dyA;
	private double dzA;
	private double dxC;
	private double dyC;
	private double dzC;
	
	public Vector[] vector = new Vector[2];
	public Point point;
	public Point diagonalPoint;
	
	private Color color;	
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public static boolean isFiring;
	
	// Constructor
	
	public Tile(double x, double y, double dxA, double dyA, double dxC, double dyC, Color color){
		
		this.x = x; // GamePanel.WIDTH / 2;
		this.y = y; // GamePanel.HEIGHT / 2;
		
		this.dxA = dxA;
		this.dyA = dyA;
		dzA = 0;
		this.dxC = dxC;
		this.dyC = dyC;
		dzC = 0;
		
		this.color = color;
		
		up = false;
		down = false;
		left = false;
		right = false;
		
		isFiring = false;
	}
	
	public Tile(BrickInLayer brickInLayer, int axisIndex){
		
		int firstAxisIndex = (axisIndex < Cube.DIMENSION - 1) ? axisIndex + 1 : axisIndex + 1 - Cube.DIMENSION;
		int secondAxisIndex = (axisIndex < Cube.DIMENSION - 2) ? axisIndex + 2 : axisIndex + 2 - Cube.DIMENSION;
		
		//Point point1 = brickInLayer.nearestTileDiagonalEnd[axisIndex];
		this.point = brickInLayer.nearestCorner;
	
		int v, w;
		switch(axisIndex){
		case 0:
			v = 1;
			w = 2;
			break;
		case 1:
			v = 2;
			w = 0;
			break;
		case 2:
			v = 0;
			w = 1;
			break;
		default:
			v = 0;
			w = 0;
			break;
		}
		vector[0] = brickInLayer.layer.nearestRibVector[v];
		vector[1] = brickInLayer.layer.nearestRibVector[w];
		
		Vector halfDiagonalVector = new Vector(0.5, vector[0].plus(vector[1]));
		//this.centre = new Point(point, halfDiagonalVector);
		
//		Point point1 = brickInLayer.nearestRibEnd[firstAxisIndex];
//		Point point2 = brickInLayer.nearestRibEnd[secondAxisIndex];

		Point point0 = new Point(point, vector[0]);
		Point point1 = new Point(point, vector[1]);
		this.diagonalPoint = new Point(point0, vector[1]);
		
		this.dxA = point0.x - point.x;
		this.dyA = point0.y - point.y;
		dzA = 0;
		this.dxC = point1.x - point.x;
		this.dyC = point1.y - point.y;
		dzC = 0;
		
		this.x = (point1.x + point0.x) / 2.0; // + GamePanel.WIDTH / 2.0; 
		this.y = (point1.y + point0.y) / 2.0; // + GamePanel.HEIGHT / 2.0; 
		
		this.color = brickInLayer.brick.color[axisIndex][(brickInLayer.layer.nearestCornerSignes.getCoordinate(axisIndex) + 1) / 2];;
		
		up = false;
		down = false;
		left = false;
		right = false;
		
		isFiring = false;
	}
	
	// Functions
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void update(double x, double y, double dxA, double dyA, double dxC, double dyC, Color color){
		
		this.x = x; // GamePanel.WIDTH / 2;
		this.y = y; // GamePanel.HEIGHT / 2;
		
		this.dxA = dxA;
		this.dyA = dyA;
		dzA = 0;
		this.dxC = dxC;
		this.dyC = dyC;
		dzC = 0;
		
		this.color = color;
		
		up = false;
		down = false;
		left = false;
		right = false;
		
		isFiring = false;
	
	}
	
	
	public static void arrangeCollection(Graphics2D g, 
			ArrayList<Tile> tileCollection){
		
		for(int v = 0; v < tileCollection.size() - 1; v++){
			for(int w = v + 1; w < tileCollection.size(); w++){
				Tile tileV = tileCollection.get(v);
				Tile tileW = tileCollection.get(w);
				if(toSwap(g, 
						tileV, tileW)){
//					Tile tileZ = tileCollection.get(v+1);
					tileCollection.set(v, tileW);
//					tileCollection.set(v+1, tileV);
//					tileCollection.set(w, tileZ);
					tileCollection.set(w, tileV);
				}
			}
		}
	}
	
	
	public static boolean toSwap(Graphics2D g, 
			Tile tileV, Tile tileW){
		
		Point pointV = tileV.point;
		Point pointV0 = new Point(pointV, tileV.vector[0]);
		Point pointV1 = new Point(pointV, tileV.vector[1]);
		//Point pointV01 = new Point(pointV0, tileV.vector[0]);
		
		Point[] pointsV = new Point[] { pointV, pointV, pointV0, pointV1 };
		Vector[] vectorsV = new Vector[] { tileV.vector[0], tileV.vector[1], tileV.vector[1], tileV.vector[0] };
		
		Point pointW = tileW.point;
		Point pointW0 = new Point(pointW, tileW.vector[0]);
		Point pointW1 = new Point(pointW, tileW.vector[1]);
		//Point pointW01 = new Point(pointW0, tileW.vector[1]);
		
		Point[] pointsW = new Point[] { pointW, pointW, pointW0, pointW1 };
		Vector[] vectorsW = new Vector[] { tileW.vector[0], tileW.vector[1], tileW.vector[1], tileW.vector[0] };
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++){
				int posV = positionOfSegmentRelativeToTileSegment(g, pointsV[i], vectorsV[i], tileW, pointsW[j], vectorsW[j]);
				if(posV == -1) return true; 
				if(posV == 1) return false; 
			}
		
		//System.out.println("pointV = " + pointV.x + ", "+ pointV.y + ", "+ pointV.z + ";  proection = "+ proectionVonW.point.x + ", "+ proectionVonW.point.y+ ", "+ proectionVonW.point.z);
//		System.out.println("point = " + point0.x + ", "+ point0.y + ", "+ point0.z + ";  proection = "+ proectionV0onW.point.x + ", "+ proectionV0onW.point.y+ ", "+ proectionV0onW.point.z);
//		System.out.println("point = " + point1.x + ", "+ point1.y + ", "+ point1.z + ";  proection = "+ proectionV1onW.point.x + ", "+ proectionV1onW.point.y+ ", "+ proectionV1onW.point.z);
//		System.out.println("point = " + point01.x + ", "+ point01.y + ", "+ point01.z + ";  proection = "+ proectionV01onW.point.x + ", "+ proectionV01onW.point.y+ ", "+ proectionV01onW.point.z);


//		Proection proectionV0onW = pointV0.proectionOnTile(tileW);
//		System.out.println("pointV0 = " + pointV0.x + ", "+ pointV0.y + ", "+ pointV0.z + ";  proection = "+ proectionV0onW.point.x + ", "+ proectionV0onW.point.y+ ", "+ proectionV0onW.point.z);
//		if(proectionV0onW.inside) return proectionV0onW.below;
//
//		Proection proectionV1onW = pointV1.proectionOnTile(tileW);
//		System.out.println("pointV1 = " + pointV1.x + ", "+ pointV1.y + ", "+ pointV1.z + ";  proection = "+ proectionV1onW.point.x + ", "+ proectionV1onW.point.y+ ", "+ proectionV1onW.point.z);
//		if(proectionV1onW.inside) return proectionV1onW.below; 
//		
//		Proection proectionV01onW = pointV01.proectionOnTile(tileW);
//		System.out.println("pointV01 = " + pointV01.x + ", "+ pointV01.y + ", "+ pointV01.z + ";  proection = "+ proectionV01onW.point.x + ", "+ proectionV01onW.point.y+ ", "+ proectionV01onW.point.z);
//		if(proectionV01onW.inside) return proectionV01onW.below;
//				
//		Proection proectionWonV = pointW.proectionOnTile(tileV);
//		System.out.println("pointW = " + pointW.x + ", "+ pointW.y + ", "+ pointW.z + ";  proection = "+ proectionWonV.point.x + ", "+ proectionWonV.point.y+ ", "+ proectionWonV.point.z);
//		if(proectionWonV.inside) return proectionWonV.above; 
//
//		Proection proectionW0onV = pointW0.proectionOnTile(tileV);
//		System.out.println("pointW0 = " + pointW0.x + ", "+ pointW0.y + ", "+ pointW0.z + ";  proection = "+ proectionW0onV.point.x + ", "+ proectionW0onV.point.y+ ", "+ proectionW0onV.point.z);
//		if(proectionW0onV.inside) return proectionW0onV.above;
//
//		Proection proectionW1onV = pointW1.proectionOnTile(tileV);
//		System.out.println("pointW1 = " + pointW1.x + ", "+ pointW1.y + ", "+ pointW1.z + ";  proection = "+ proectionW1onV.point.x + ", "+ proectionW1onV.point.y+ ", "+ proectionW1onV.point.z);
//		if(proectionW1onV.inside) return proectionW1onV.above; 
//		
//		Proection proectionW01onV = pointW01.proectionOnTile(tileV);
//		System.out.println("pointW01 = " + pointW01.x + ", "+ pointW01.y + ", "+ pointW01.z + ";  proection = "+ proectionW01onV.point.x + ", "+ proectionW01onV.point.y+ ", "+ proectionW01onV.point.z);
//		if(proectionW01onV.inside) return proectionW01onV.above; 
			
		return false;

	}
	
	
	public static int positionOfSegmentRelativeToTileSegment(Graphics2D g, Point pointV, Vector vectorV, Tile tile, Point pointW, Vector vectorW){
		
		double absCos = Math.abs(vectorV.cos(vectorW));
		if(absCos > 0.99999) return 0;

		Point pointV0 = new Point(pointV, vectorV);
		Point pointW0 = new Point(pointW, vectorW);
		
		Vector vectorVW = new Vector(pointV, pointW);
		Vector vectorVW0 = new Vector(pointV, pointW0);
		
		Vector vectorVonXY = vectorV.projectionOntoXY();
		Vector vectorVWonXY = vectorVW.projectionOntoXY();
		Vector vectorVW0onXY = vectorVW0.projectionOntoXY();
		
		Vector projectionVectorVW = vectorVW.projectionOnto(vectorV);
		Vector projectionVectorVW0 = vectorVW0.projectionOnto(vectorV);
		
		Vector projectionVectorVWonXY = vectorVWonXY.projectionOnto(vectorVonXY);
		Vector projectionVectorVW0onXY = vectorVW0onXY.projectionOnto(vectorVonXY);
	
//		Vector projectionVectorVWonXY = projectionVectorVW.projectionOntoXY();
//		Vector projectionVectorVW0onXY = projectionVectorVW0.projectionOntoXY();

		Vector perpendicularVectorVWonXY = vectorVWonXY.minus(projectionVectorVWonXY);
		Vector perpendicularVectorVW0onXY = vectorVW0onXY.minus(projectionVectorVW0onXY);
		
		double scalarProduct = perpendicularVectorVWonXY.scalarProductBy(perpendicularVectorVW0onXY);
		
		if(scalarProduct < 0){
			Vector projectionVectorWW0 = projectionVectorVW0.minus(projectionVectorVW);
			//Vector projectionVectorWW0onXY = projectionVectorWW0.projectionOntoXY();
			double lengthWonXY = perpendicularVectorVWonXY.length();
			double lengthW0onXY = perpendicularVectorVW0onXY.length();
			double coef = lengthWonXY / (lengthWonXY + lengthW0onXY);
			Vector projectionVectorWX = new Vector(coef, projectionVectorWW0);
			Vector vectorVX = projectionVectorVW.plus(projectionVectorWX);
			Point pointXonV = new Point(pointV, vectorVX);
			Point pointXonW = pointXonV.projectionOnTile(tile);
			if ( (pointXonW.z - pointXonV.z > 0.0001) || (pointXonW.z - pointXonV.z < -0.0001) ){
				GamePanel.drawPointInPanel(g, pointV, Color.GREEN, 10);
				GamePanel.drawPointInPanel(g, pointV0, Color.GREEN, 10);
				GamePanel.drawPointInPanel(g, pointW, Color.YELLOW, 10);
				GamePanel.drawPointInPanel(g, pointW0, Color.YELLOW, 10);
				GamePanel.drawPointInPanel(g, pointXonW, Color.RED, 10);
			}
			if(pointXonW.z - pointXonV.z > 0.0001) return 1;
			if(pointXonW.z - pointXonV.z < -0.0001) return -1;
		}

		return 0;
	
	}
	
	
	public static boolean toSwap_false(Graphics2D g, 
			Tile tileV, Tile tileW){
		
		Point[] pointNearCornerV = tileV.pointNearCorner_false();
		
		Point pointV = pointNearCornerV[0];
		Proection proectionVonW = pointV.proectionOnTile(tileW);
		System.out.println("pointV = " + pointV.x + ", "+ pointV.y + ", "+ pointV.z + ";  proection = "+ proectionVonW.point.x + ", "+ proectionVonW.point.y+ ", "+ proectionVonW.point.z);
		if(proectionVonW.inside) return proectionVonW.below; 

		Point pointV0 = pointNearCornerV[1];
		Proection proectionV0onW = pointV0.proectionOnTile(tileW);
		System.out.println("pointV0 = " + pointV0.x + ", "+ pointV0.y + ", "+ pointV0.z + ";  proection = "+ proectionV0onW.point.x + ", "+ proectionV0onW.point.y+ ", "+ proectionV0onW.point.z);
		if(proectionV0onW.inside) return proectionV0onW.below;

		Point pointV1 = pointNearCornerV[2];
		Proection proectionV1onW = pointV1.proectionOnTile(tileW);
		System.out.println("pointV1 = " + pointV1.x + ", "+ pointV1.y + ", "+ pointV1.z + ";  proection = "+ proectionV1onW.point.x + ", "+ proectionV1onW.point.y+ ", "+ proectionV1onW.point.z);
		if(proectionV1onW.inside) return proectionV1onW.below; 
		
		Point pointV01 = pointNearCornerV[3];
		Proection proectionV01onW = pointV01.proectionOnTile(tileW);
		System.out.println("pointV01 = " + pointV01.x + ", "+ pointV01.y + ", "+ pointV01.z + ";  proection = "+ proectionV01onW.point.x + ", "+ proectionV01onW.point.y+ ", "+ proectionV01onW.point.z);
		if(proectionV01onW.inside) return proectionV01onW.below;
				
		Point[] pointNearCornerW = tileW.pointNearCorner_false();
		
		Point pointW = pointNearCornerW[0];
		Proection proectionWonV = pointW.proectionOnTile(tileV);
		System.out.println("pointW = " + pointW.x + ", "+ pointW.y + ", "+ pointW.z + ";  proection = "+ proectionWonV.point.x + ", "+ proectionWonV.point.y+ ", "+ proectionWonV.point.z);
		if(proectionWonV.inside) return proectionWonV.above; 

		Point pointW0 = pointNearCornerW[1];
		Proection proectionW0onV = pointW0.proectionOnTile(tileV);
		System.out.println("pointW0 = " + pointW0.x + ", "+ pointW0.y + ", "+ pointW0.z + ";  proection = "+ proectionW0onV.point.x + ", "+ proectionW0onV.point.y+ ", "+ proectionW0onV.point.z);
		if(proectionW0onV.inside) return proectionW0onV.above;

		Point pointW1 = pointNearCornerW[2];
		Proection proectionW1onV = pointW1.proectionOnTile(tileV);
		System.out.println("pointW1 = " + pointW1.x + ", "+ pointW1.y + ", "+ pointW1.z + ";  proection = "+ proectionW1onV.point.x + ", "+ proectionW1onV.point.y+ ", "+ proectionW1onV.point.z);
		if(proectionW1onV.inside) return proectionW1onV.above; 
		
		Point pointW01 = pointNearCornerW[3];
		Proection proectionW01onV = pointW01.proectionOnTile(tileV);
		System.out.println("pointW01 = " + pointW01.x + ", "+ pointW01.y + ", "+ pointW01.z + ";  proection = "+ proectionW01onV.point.x + ", "+ proectionW01onV.point.y+ ", "+ proectionW01onV.point.z);
		if(proectionW01onV.inside) return proectionW01onV.above; 
			
		return false;

	}

	
	public Point[] pointNearCorner_false(){
		
		Point pointC = Point.NULL; // centre;
		double coef = 0.49;
		
		Vector smallHalfVectorP0 = new Vector(coef, vector[0]);
		Vector smallHalfVectorP1 = new Vector(coef, vector[1]);
		Vector smallHalfVectorM0 = new Vector(-coef, vector[0]);
		Vector smallHalfVectorM1 = new Vector(-coef, vector[1]);
		
		Point pointCP0 = new Point(pointC, smallHalfVectorP0);
		Point pointCM0 = new Point(pointC, smallHalfVectorM0);
		
		Point point00 = new Point(pointCM0, smallHalfVectorM1);
		Point point01 = new Point(pointCM0, smallHalfVectorP1);
		Point point10 = new Point(pointCP0, smallHalfVectorM1);
		Point point11 = new Point(pointCP0, smallHalfVectorP1);
		
		return new Point[] {point00, point01, point11, point10}; 
	}

	
	public void draw(Graphics2D g){
		
		double x0 = x - dxA/2.5 - dxC/2.5;
		double y0 = y - dyA/2.5 - dyC/2.5;
		double xA = x + dxA/2.5 - dxC/2.5;
		double yA = y + dyA/2.5 - dyC/2.5;
		double xB = x + dxA/2.5 + dxC/2.5;
		double yB = y + dyA/2.5 + dyC/2.5;
		double xC = x - dxA/2.5 + dxC/2.5;
		double yC = y - dyA/2.5 + dyC/2.5;
		
		g.setColor(color);

		int argX[] = {(int)x0, (int)xA, (int)xB, (int)xC};
		int argY[] = {(int)y0, (int)yA, (int)yB, (int)yC};
		int num = 4;
		g.fillPolygon(argX, argY, num);
		
//		g.drawLine((int)x0, (int)y0, (int)xA, (int)yA);
//		g.drawLine((int)xB, (int)yB, (int)xA, (int)yA);
//		g.drawLine((int)x0, (int)y0, (int)xC, (int)yC);
//		g.drawLine((int)xB, (int)yB, (int)xC, (int)yC);
		
	}
	
	
	public void drawInPanel(Graphics2D g){
		
		//drawPolygon(g, 100, Color.WHITE);
		drawPolygon(g, 99, color);

	}		
	
	
	public void drawInPanel(Graphics2D g, Color color){
		
		drawPolygon(g, 100, color);

	}	
	
	
	public void drawPolygon(Graphics2D g, double percent, Color color){
		
		double xP = x + GamePanel.WIDTH / 2.0; 
		double yP = y + GamePanel.HEIGHT / 2.0; 
		
		double coef = 200.0 / percent;
		
		double x0 = xP - dxA/coef - dxC/coef;
		double y0 = yP - dyA/coef - dyC/coef;
		double xA = xP + dxA/coef - dxC/coef;
		double yA = yP + dyA/coef - dyC/coef;
		double xB = xP + dxA/coef + dxC/coef;
		double yB = yP + dyA/coef + dyC/coef;
		double xC = xP - dxA/coef + dxC/coef;
		double yC = yP - dyA/coef + dyC/coef;
		
		g.setColor(color);

		int argX[] = {(int)x0, (int)xA, (int)xB, (int)xC};
		int argY[] = {(int)y0, (int)yA, (int)yB, (int)yC};
		int num = 4;
		
		//if(color == Color.RED)
			g.fillPolygon(argX, argY, num);
			
		g.setColor(Color.BLACK);
		
	   	g.drawLine((int)xB, (int)yB, (int)xC, (int)yC);
	   	g.drawLine((int)xC, (int)yC, (int)x0, (int)y0);
	   	g.drawLine((int)x0, (int)y0, (int)xA, (int)yA);
	   	g.drawLine((int)xA, (int)yA, (int)xB, (int)yB);
		
	}
	
	
	public static void drawCollection(Graphics2D g,	ArrayList<Tile> tileCollection){
		
		int i = 0;
		for(Tile currentTile : tileCollection){
			i++;
			currentTile.drawInPanel(g);
	//		if(i>1) break;
		}
		
//		for(int v = 0; v < tileCollection.size() - 1; v++){
//			
//			for(int w = v + 1; w < tileCollection.size(); w++){
//				Tile tileV = tileCollection.get(v);
//				Tile tileW = tileCollection.get(w);
//				if(toSwap(g, tileV, tileW)){
//					tileCollection.get(w).drawInPanel(g, Color.WHITE);
////					Tile tileZ = tileCollection.get(v+1);
//					tileCollection.get(v).drawInPanel(g, Color.RED);
////					tileCollection.set(v+1, tileV);
////					tileCollection.set(w, tileZ);
//					//return;
//				}
//			}
//		}
	}

}

