package RubiksCube;

import java.awt.*;

public class Player {
	
	// Fields
	private double x;
	private double y;
	private int r;
	
	private int speed;
	
	private Color color1;	
	private Color color2;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	// Constructor
	public Player(){
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
		
		r = 7;
		
		speed = 5;
		
		color1 = Color.WHITE;
		
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	// Functions
	public void update(){
		if(up && y > r){
			y -= speed;
		}
		if(down && y < GamePanel.HEIGHT - r){
			y += speed;
		}
		if(left && x > r){
			x -= speed;
		}
		if(right && x < GamePanel.WIDTH - r){
			x += speed;
		}
	}
	
	public void draw(Graphics2D g){
		
		double x0 = x - r;
		double y0 = y - r;
		int strokeThickness = 3;
		int r1 = r - strokeThickness;
		double x1 = x - r1;
		double y1 = y - r1;
		
		g.setColor(color1.darker());
		//g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.fillOval((int)x0, (int)y0, 2 * r, 2 * r);
		//g.setStroke(new BasicStroke(1));
		g.setColor(color1);
		//g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.fillOval((int)x1, (int)y1, 2 * r1, 2 * r1);
		//g.setStroke(new BasicStroke(strokeThickness));
	}



}
