
import java.awt.*;

public class RowOfTiles {

	// Fields
	
		private double x;
		private double y;
		private double dxA;
		private double dyA;
		private double dzA;
		private double dxC;
		private double dyC;
		private double dzC;
			
		private Color color1;	
		private Color color2;
			
		public static boolean up;
		public static boolean down;
		public static boolean left;
		public static boolean right;
			
		public static boolean isFiring;
		
		private Tile edge1, edge2, edge3;
			
		// Constructor
		public RowOfTiles(double x, double y, double dxA, double dyA, double dxC, double dyC, Color color){
			
			this.x = x; // GamePanel.WIDTH / 2;
			this.y = y; // GamePanel.HEIGHT / 2;
			
			this.dxA = dxA;
			this.dyA = dyA;
			dzA = 0;
			this.dxC = dxC;
			this.dyC = dyC;
			dzC = 0;
				
			color1 = Color.YELLOW;
				
			up = false;
			down = false;
			left = false;
			right = false;
				
			isFiring = false;
			
			edge1 = new Tile(x + 0.1 * dxA, y + 0.1 * dyA, dxA, dyA, dxC, dyC, Color.RED);
			edge2 = new Tile(x + (0.1 + 1.2) * dxA, y + (0.1 + 1.2) * dyA, dxA, dyA, dxC, dyC, Color.CYAN);
			edge3 = new Tile(x + (0.1 + 2 * 1.2) * dxA, y + (0.1 + 2 * 1.2) * dyA, dxA, dyA, dxC, dyC, Color.DARK_GRAY);
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
				
			//if(isFiring){
			//	GamePanel.bullets.add(new Bullet());
			//}
		}
			
		public void draw(Graphics2D g){
				
			double xA = x + dxA;
			double yA = y + dyA;
			double xB = x + dxA + dxC;
			double yB = y + dyA + dyC;
			double xC = x + dxC;
			double yC = y + dyC;
			
			g.setColor(color1);

			int argX[] = {(int)x, (int)xA, (int)xB, (int)xC};
			int argY[] = {(int)y, (int)yA, (int)yB, (int)yC};
			int num = 4;
			//g.fillPolygon(argX, argY, num);
			
			edge1.draw(g);
			edge2.draw(g);
			edge3.draw(g);
		}
	}

