
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.*;


public class GamePanel extends JPanel implements Runnable{

	// Fields
	
	public static int WIDTH = 600;
	public static int HEIGHT = 600;
	
	private Thread thread;
	
	private BufferedImage image;
	private Graphics2D g;
	
	public static double pause = 10;
	
	public static GameBack background;
	public static Player player;

	public Cube cube;
	
	ArrayList<Brick> brickCollection = new ArrayList<Brick>();
	
	// Constructor
	
	public GamePanel(){
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		
		addKeyListener(new Listeners());
		
	}
	
	// Functions
	
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	
	
	public void run() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		background = new GameBack();
		
		player = new Player(0, 50, 17);
		
		Vector[] vectors = Vector.orthogonalVectors(70,
				10, 50, -19,
				40, -19,
				-30);
		
		cube = new Cube(Cube.SIZE, 
				Point.NULL, 
				vectors);
		
		while(true){
			
			gameUpdate();
			gameRender();
			
			try {
				thread.sleep((int)pause);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
		}
	}
	
	
	public void gameUpdate(){
		
		player.update();
		background.update(cube.tile[0][1][2][1]);
		
		background.draw(g);
		cube.update(g, brickCollection);
	}
	
	
	public void gameRender(){
		
//		background.draw(g);
		player.draw(g);

		cube.draw(g);

		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	
	public static void drawLine(Graphics2D g, Point beginingPoint, Point endingPoint, Point originPoint, Color color){
		
//		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
//	   	g.setStroke(dashed); //2d
		
    	double xB = beginingPoint.x + originPoint.x;
    	double yB = beginingPoint.y + originPoint.y;
    	double xE = endingPoint.x + originPoint.x;
    	double yE = endingPoint.y + originPoint.y;
    	
    	g.setColor(color);
    	g.drawLine((int)xB, (int)yB, (int)xE, (int)yE);
	}	
	
	
	public static void drawPoint(Graphics2D g, Point point, Point origin, Color color, int radius){
		
		g.setColor(color);
		
		double x0 = point.x + origin.x - radius / 2.0;
    	double y0 = point.y + origin.y - radius / 2.0;
     	
		g.fillOval((int)x0, (int)y0, (int)radius, radius);
	}

	
	public static void drawPointInPanel(Graphics2D g, Point point, Color color, int radius){
		
		g.setColor(color);
		
		double x0 = GamePanel.WIDTH / 2.0 + point.x - radius / 2.0;
    	double y0 = GamePanel.HEIGHT / 2.0 + point.y - radius / 2.0;
     	
		g.fillOval((int)x0, (int)y0, radius, radius);
	}
	
	
	public static void drawVectorInPanel(Graphics2D g, Point beginingPoint, Vector vector, Color color){
		
		double xB = beginingPoint.x + GamePanel.WIDTH / 2.0;
    	double yB = beginingPoint.y + GamePanel.HEIGHT / 2.0;
    	
    	Point endingPoint = new Point(beginingPoint, vector);
    	
    	double xE = endingPoint.x + GamePanel.WIDTH / 2.0;
    	double yE = endingPoint.y + GamePanel.HEIGHT / 2.0;
    	
    	g.setColor(color);
    	g.drawLine((int)xB, (int)yB, (int)xE, (int)yE);
	}		
	
	
	public static void drawLineInPanel(Graphics2D g, Point beginingPoint, Point endingPoint, Color color){
		
		double xB = beginingPoint.x + GamePanel.WIDTH / 2.0;
    	double yB = beginingPoint.y + GamePanel.HEIGHT / 2.0;
    	double xE = endingPoint.x + GamePanel.WIDTH / 2.0;
    	double yE = endingPoint.y + GamePanel.HEIGHT / 2.0;
    	
    	g.setColor(color);
    	g.drawLine((int)xB, (int)yB, (int)xE, (int)yE);
	}		
	
}
