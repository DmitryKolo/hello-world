
import java.awt.*;

public class VolumetricAngle {

	// Fields
	
	private double x;
	private double y;
	private double xA;
	private double yA;
	private double zA;
	private double xB;
	private double yB;
	private double zB;
	private double xC;
	private double yC;
	private double zC;
			
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
			
	private Edge edge1, edge2, edge3;
			
	// Constructor
	
	public VolumetricAngle(double xA, double yA, double xB, double yB, double xC, double yC){
			
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
			
		this.xA = xA;
		this.yA = yA;
		zA = 0;
		this.xB = xB;
		this.yB = yB;
		zC = 0;
		this.xC = xC;
		this.yC = yC;
		zC = 0;
				
		up = false;
		down = false;
		left = false;
		right = false;
				
		edge1 = new Edge(xA, yA, xB, yB);
		edge2 = new Edge(xB, yB, xC, yC);
		edge3 = new Edge(xC, yC, xA, yA);
	
	}
			
	// Functions
	
	public double getX(){
		return x;
	}
			
	public double getY(){
		return y;
	}
			
	public void update(){
				
		x += 0;
		y += 0;
				
	}
			
	public void draw(Graphics2D g){
				
		//double yB = y + dyA + dyC;
			
		edge1.draw(g);
		edge2.draw(g);
		edge3.draw(g);
	}
}

