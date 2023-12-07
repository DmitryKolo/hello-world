package RubiksCube;

import java.awt.*;

public class Player {
	
	// Fields
	private double x;
	private double y;
	private int r;
	
	private Color color1;	
	private Color color2;
	
	// Constructor
	public Player(){
		x = GamePanel.WIDTH / 2;		
		y = GamePanel.HEIGHT / 2;
		
		r = 7;
		
		color1 = Color.WHITE;
	}
	
	// Functions
	public void update(){
		
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
