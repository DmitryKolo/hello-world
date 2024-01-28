
import java.awt.*;
import java.util.ArrayList;

public class Tile{
	
	// Fields
	
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
	public BrickInLayer brickInLayer;
	
	private Color color;	
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public static boolean isFiring;
	
	// Constructor
	
	public Tile(BrickInLayer brickInLayer, int axisIndex){
		
		int firstAxisIndex = (axisIndex < Cube.DIMENSION - 1) ? axisIndex + 1 : axisIndex + 1 - Cube.DIMENSION;
		int secondAxisIndex = (axisIndex < Cube.DIMENSION - 2) ? axisIndex + 2 : axisIndex + 2 - Cube.DIMENSION;
		
		this.brickInLayer = brickInLayer;
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
		//this.diagonalPoint = new Point(point0, vector[1]);
		
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
		
//		for(int v = 0; v < tileCollection.size() - 1; v++)
//			for(int w = v + 1; w < tileCollection.size(); w++){
//				Tile tileV = tileCollection.get(v);
//				Tile tileW = tileCollection.get(w);
//				if(toSwap(g, 
//						tileV, tileW)){
//					tileCollection.set(v, tileW);
//					tileCollection.set(w, tileV);
//				}
//			}
		
		for(int v = 0; v < tileCollection.size() - 1; v++){
			for(int w = v + 1; w < tileCollection.size(); w++){
			
				Tile tileV = tileCollection.get(v);
				Tile tileW = tileCollection.get(w);
			
				//System.out.println("[" + v + "." + w + "] ? " + tileV + "(" + tileV.point.z + ") and " + tileW + "(" + tileW.point.z + ")" );
			
				if(toSwap(g, tileV, tileW)){
					//System.out.println(" ---   Swap: " + v + " - " + w + ": "+GamePanel.pause);
//					for(int i = w; w >= v; w--)
//						tileCollection.set(i, tileCollection.get(i - 1));
					tileCollection.set(v, tileW);
					tileCollection.set(w, tileV);
					if(v>0) { v--; break; }
				}
				
				
			}
		}
	}
	
	public void fillPointAndVectorsCollection(Point[] points, Vector[] vectors){
		
		double correctionCoef = 0.99;
		double gapCoef = (1 - correctionCoef) / 2.0;
		
		Point pointV = this.point.plus(gapCoef, this.vector[0]).plus(gapCoef, this.vector[1]);
		
		Vector vector0 = new Vector(correctionCoef, this.vector[0]);
		Vector vector1 = new Vector(correctionCoef, this.vector[1]);
		
		Point pointV0 = new Point(pointV, vector0);
		Point pointV1 = new Point(pointV, vector1);
		
		points = new Point[] { pointV, pointV, pointV0, pointV1 };
		vectors = new Vector[] { vector0, vector1, vector1, vector0 };
		
	}
	
	
	public static boolean toSwap(Graphics2D g, 
			Tile tileV, Tile tileW){
		
		double correctionCoef = 0.99;
		double gapCoef = (1 - correctionCoef) / 2.0;
		
		Point pointV = tileV.point.plus(gapCoef, tileV.vector[0]).plus(gapCoef, tileV.vector[1]);
		
		Vector vectorV0 = new Vector(correctionCoef, tileV.vector[0]);
		Vector vectorV1 = new Vector(correctionCoef, tileV.vector[1]);
		
		Point pointV0 = new Point(pointV, vectorV0);
		Point pointV1 = new Point(pointV, vectorV1);
		
		Point[] pointsV = new Point[] { pointV, pointV, pointV0, pointV1 };
		Vector[] vectorsV = new Vector[] { vectorV0, vectorV1, vectorV1, vectorV0 };
		
		Point pointW = tileW.point.plus(gapCoef, tileW.vector[0]).plus(gapCoef, tileW.vector[1]);
		
		Vector vectorW0 = new Vector(correctionCoef, tileW.vector[0]);
		Vector vectorW1 = new Vector(correctionCoef, tileW.vector[1]);
		
		Point pointW0 = new Point(pointW, vectorW0);
		Point pointW1 = new Point(pointW, vectorW1);
		
		Point[] pointsW = new Point[] { pointW, pointW, pointW0, pointW1 };
		Vector[] vectorsW = new Vector[] { vectorW0, vectorW1, vectorW1, vectorW0 };
		
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++){
				
				if(GamePanel.pause > 59)
					GamePanel.pause -= 0.0011;
				
				int posV = positionOfSegmentRelativeToTileSegment(g, pointsV[i], vectorsV[i], tileW, pointsW[j], vectorsW[j]);
				if(posV == 1) return true; 
				if(posV == -1) return false; 
			}
		
		return false;

	}
	
	
	public static int positionOfSegmentRelativeToTileSegment(Graphics2D g, Point pointV, Vector vectorV, Tile tile, Point pointW, Vector vectorW){
		
		Point pointV0 = new Point(pointV, vectorV);
		Point pointW0 = new Point(pointW, vectorW);
		
		int pos = Point.zCompareInTwoSegments(pointV, pointV0, pointW, pointW0);
		
		//	GamePanel.pause += 0.1;
		//	if(GamePanel.pause>60) GamePanel.pause = 600;

		return pos;
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

	
	public void drawInPanel(Graphics2D g){
		
		drawPolygon(g, 100, Color.WHITE);
		drawPolygon(g, 90, color);

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
		
		g.fillPolygon(argX, argY, num);
			
//		g.setColor(Color.BLACK);
//		
//	   	g.drawLine((int)xB, (int)yB, (int)xC, (int)yC);
//	   	g.drawLine((int)xC, (int)yC, (int)x0, (int)y0);
//	   	g.drawLine((int)x0, (int)y0, (int)xA, (int)yA);
//	   	g.drawLine((int)xA, (int)yA, (int)xB, (int)yB);
		
	}
	
	
	public static void drawCollection(Graphics2D g,	ArrayList<Tile> tileCollection){
		
		for(Tile currentTile : tileCollection)
			currentTile.drawInPanel(g);
	}

}

