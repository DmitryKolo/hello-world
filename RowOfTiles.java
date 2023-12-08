
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
		
		private Tile tile1, tile2, tile3;
			
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
			
			tile1 = new Tile(x - dxA, y - dyA, dxA, dyA, dxC, dyC, Color.RED);
			tile2 = new Tile(x,       y,       dxA, dyA, dxC, dyC, Color.CYAN);
			tile3 = new Tile(x + dxA, y + dyA, dxA, dyA, dxC, dyC, Color.DARK_GRAY);
		}
			
		// Functions
		public double getX(){
			return x;
		}
			
		public double getY(){
			return y;
		}
			
		public void update(double x, double y, double dxA, double dyA, double dxC, double dyC){
				
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
			
			tile1.update(x - dxA, y - dyA, dxA, dyA, dxC, dyC, Color.RED);
			tile2.update(x,       y,       dxA, dyA, dxC, dyC, Color.CYAN);
			tile3.update(x + dxA, y + dyA, dxA, dyA, dxC, dyC, Color.DARK_GRAY);
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
			
			tile1.draw(g);
			tile2.draw(g);
			tile3.draw(g);
		}
	}

