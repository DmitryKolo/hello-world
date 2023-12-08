package RubiksCube;

import java.awt.*;

public class Player {
	
	// Fields
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	
	private int speed;
	
	private Color color1;	
	private Color color2;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public static boolean isFiring;
	
	// Constructor
	public Player(){
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
		
		r = 7;
		
		speed = 5;
		
		dx = 0;
		dy = 0;
		
		color1 = Color.WHITE;
		
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
		if(up && y > r){
			dy = -speed;
		}
		if(down && y < GamePanel.HEIGHT - r){
			dy = speed;
		}
		if(left && x > r){
			dx = -speed;
		}
		if(right && x < GamePanel.WIDTH - r){
			dx = speed;
		}
		if(up && left || up && right || down && left || down && right){
			double angle = Math.toRadians(45);
			dx = dx * Math.cos(angle);
			dy = dy * Math.sin(angle);
		}
		
		x += dx;
		y += dy;
		
		dx = 0;
		dy = 0;
		
		if(isFiring){
			GamePanel.bullets.add(new Bullet());
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
