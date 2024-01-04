
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
	
	public Point point;
	public Vector[] vector = new Vector[2];
	
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
		
//		Point point1 = brickInLayer.nearestRibEnd[firstAxisIndex];
//		Point point2 = brickInLayer.nearestRibEnd[secondAxisIndex];

		Point point0 = new Point(point, vector[0]);
		Point point1 = new Point(point, vector[1]);
		
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
	
	public static void arrangeCollection(ArrayList<Tile> tileCollection){
		
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
		
		drawPolygon(g, 100, Color.WHITE);
		drawPolygon(g, 80, color);

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
		
	}
	
}

