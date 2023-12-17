
import java.awt.*;

public class Edge {

	// Fields
	
	public int size; 
	public Point centre, corner12, corner12cw, corner1cw2, corner1cw2cw;
	
	private double x;
	private double y;
	private double dxA;
	private double dyA;
	private double dzA;
	private double dxC;
	private double dyC;
	private double dzC;
	private double dx0;
	private double dy0;
	private double dz0;
	
	public Vector vector0, vector1, vector2;
			
	private Color color;	

	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
			
	public static boolean isFiring;
	
	//Block block;
	//int AxisIndex;//2
		
	private RowOfTiles row1, row2, row3;
	public RowOfTiles[] row = new RowOfTiles[5];
			
	// Constructors
	
	public Edge(double dxA, double dyA, double dxC, double dyC, double dx0, double dy0){
		
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
			
		this.dxA = dxA;
		this.dyA = dyA;
		dzA = 0;
		this.dxC = dxC;
		this.dyC = dyC;
		dzC = 0;
		this.dx0 = dx0;
		this.dy0 = dy0;
		dz0 = 0;
				
		color = Color.YELLOW;
				
		up = false;
		down = false;
		left = false;
		right = false;
				
		isFiring = false;
			
		row1 = new RowOfTiles(x + dx0 - dxC, y + dy0 - dyC, dxA, dyA, dxC, dyC, Color.RED);
		row2 = new RowOfTiles(x + dx0,       y + dy0,       dxA, dyA, dxC, dyC, Color.CYAN);
		row3 = new RowOfTiles(x + dx0 + dxC, y + dy0 + dyC, dxA, dyA, dxC, dyC, Color.DARK_GRAY);
	
	}
			
	public Edge(int size, Point cubeCentre, Vector vector0, Vector vector1, Vector vector2){
		
		this.size = size;
		this.vector0 = vector0;
		this.vector1 = vector1;
		this.vector2 = vector2;
		
		double d0x = vector0.dx * size / 2;
		double d0y = vector0.dy * size / 2;
		double d0z = vector0.dz * size / 2;
		
		this.centre = new Point(cubeCentre.x + d0x, cubeCentre.y + d0y, cubeCentre.z + d0z);
		
		double d1x = vector1.dx * size / 2;
		double d1y = vector1.dy * size / 2;
		double d1z = vector1.dz * size / 2;
		
		double d2x = vector2.dx * size / 2;
		double d2y = vector2.dy * size / 2;
		double d2z = vector2.dz * size / 2;
		
		this.corner12 = new Point(centre.x + d1x + d2x, centre.y + d1y + d2y, centre.z + d1z + d2z);
		this.corner12cw = new Point(centre.x + d1x - d2x, centre.y + d1y - d2y, centre.z + d1z - d2z);
		this.corner1cw2 = new Point(centre.x - d1x + d2x, centre.y - d1y + d2y, centre.z - d1z + d2z);
		this.corner1cw2cw = new Point(centre.x - d1x - d2x, centre.y - d1y - d2y, centre.z - d1z - d2z);
		
		System.out.println("("+corner12.x+","+corner12.y+","+corner12.z+"); ("+corner12cw.x+","+corner12cw.y+","+corner12cw.z+");");
		System.out.println("    ("+corner1cw2.x+","+corner1cw2.y+","+corner1cw2.z+"); ("+corner1cw2cw.x+","+corner1cw2cw.y+","+corner1cw2cw.z+").");
		
		for(int i=0; i < size; i++){
//			System.out.println("("+i+")");
			row[i] = new RowOfTiles(centre.x - vector2.dx * (i-(size-1)/2), centre.y - vector2.dy * (i-(size-1)/2), vector1.dx, vector1.dy, vector2.dx, vector2.dy, Color.RED);
		}

	}
			
	// Functions
	
	public double getX(){
		return x;
	}
			
	public double getY(){
		return y;
	}
			
	public void update(double dxA, double dyA, double dxC, double dyC, double dx0, double dy0){
				
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
			
		this.dxA = dxA;
		this.dyA = dyA;
		dzA = 0;
		this.dxC = dxC;
		this.dyC = dyC;
		dzC = 0;
		this.dx0 = dx0;
		this.dy0 = dy0;
		dz0 = 0;
				
		color = Color.YELLOW;
				
		up = false;
		down = false;
		left = false;
		right = false;
				
		isFiring = false;
			
		row1.update(x + dx0 - dxC, y + dy0 - dyC, dxA, dyA, dxC, dyC);
		row2.update(x + dx0,       y + dy0,       dxA, dyA, dxC, dyC);
		row3.update(x + dx0 + dxC, y + dy0 + dyC, dxA, dyA, dxC, dyC);
	}
			
	public void draw(Graphics2D g){
				
//		double xA = x + dxA;
//		double yA = y + dyA;
//		double xB = x + dxA + dxC;
//		double yB = y + dyA + dyC;
//		double xC = x + dxC;
//		double yC = y + dyC;
			
		row1.draw(g);
		row2.draw(g);
		row3.draw(g);
	}
}

