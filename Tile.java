
import java.awt.*;

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
		
		double xA = x + dxA;
		double yA = y + dyA;
		double xB = x + dxA + dxC;
		double yB = y + dyA + dyC;
		double xC = x + dxC;
		double yC = y + dyC;
		
		g.setColor(color);

		int argX[] = {(int)x, (int)xA, (int)xB, (int)xC};
		int argY[] = {(int)y, (int)yA, (int)yB, (int)yC};
		int num = 4;
		g.fillPolygon(argX, argY, num);
		
	}
}

