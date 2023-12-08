package RubiksCube;

import java.awt.*;

public class SquareOfEdges {

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
		
	private LineOfEdges line1, line2, line3;
			
	// Constructor
	public SquareOfEdges(double dxA, double dyA, double dxC, double dyC){
			
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
			
		this.dxA = dxA;
		this.dyA = dyA;
		dzA = 0;
		this.dxC = dxC;
		this.dyC = dyC;
		dzC = 0;
				
		color = Color.YELLOW;
				
		up = false;
		down = false;
		left = false;
		right = false;
				
		isFiring = false;
			
		line1 = new LineOfEdges(x + 0.1 * dxC, y + 0.1 * dyC, dxA, dyA, dxC, dyC, Color.RED);
		line2 = new LineOfEdges(x + (0.1 + 1.2) * dxC, y + (0.1 + 1.2) * dyC, dxA, dyA, dxC, dyC, Color.CYAN);
		line3 = new LineOfEdges(x + (0.1 + 2 * 1.2) * dxC, y + (0.1 + 2 * 1.2) * dyC, dxA, dyA, dxC, dyC, Color.DARK_GRAY);
	
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
				
		if(isFiring){
			GamePanel.bullets.add(new Bullet());
		}
	}
			
	public void draw(Graphics2D g){
				
		double xA = x + dxA;
		double yA = y + dyA;
		double xB = x + dxA + dxC;
		double yB = y + dyA + dyC;
		double xC = x + dxC;
		double yC = y + dyC;
			
		line1.draw(g);
		line2.draw(g);
		line3.draw(g);
	}
}

