import java.awt.*;

public class Player {
	
	// Fields
	private double x;
	private double y;
	private double z;
	
	private int r;
	
	private double dx;
	private double dy;
	private double dz;
	
	private int speed;
	
	private Color color1;	
	private Color color2;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public static boolean isFiring;
	
	// Constructor
	
	public Player(double x, double y, double z){
		
		//x = GamePanel.WIDTH / 2;		
		//y = GamePanel.HEIGHT / 2;
		//z = 0;
		this.x = x;
		this.y = y;
		this.z = z;
		
		r = 7;
		
		speed = 4;
		
		dx = 0;
		dy = 0;
		dz = 0;
		
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
	
	public double getZ(){
		return z;
	}
	
	public void update(){
		if(up && y > - GamePanel.HEIGHT / 2 + r){
			dy = -speed;
		}
		if(down && y < GamePanel.HEIGHT / 2 - r){
			dy = speed;
		}
		if(left && x > - GamePanel.WIDTH / 2 + r){
			dx = -speed;
		}
		if(right && x < GamePanel.WIDTH / 2 - r){
			dx = speed;
		}
		if(up && left || up && right || down && left || down && right){
			double angle = Math.toRadians(45);
			dx = dx * Math.cos(angle);
			dy = dy * Math.sin(angle);
		}
		
		x += dx;
		y += dy;
		z += dz;
		
		dx = 0;
		dy = 0;
		dz = 0;
		
		//if(isFiring){
		//	GamePanel.bullets.add(new Bullet());
		//}
	}
	
	public void draw(Graphics2D g){
		
		//double x0 = GamePanel.WIDTH / 2 + x - r;
		//double y0 = GamePanel.HEIGHT / 2 + y - r;
		//int strokeThickness = 3;
		//int r1 = r - strokeThickness;
		//double x1 = GamePanel.WIDTH / 2 + x - r1;
		//double y1 = GamePanel.HEIGHT / 2 + y - r1;
		
		//g.setColor(color1.darker());
		//g.fillOval((int)x0, (int)y0, 2 * r, 2 * r);
		//g.setColor(color1);
		//g.fillOval((int)x1, (int)y1, 2 * r1, 2 * r1);
		
	}

}
