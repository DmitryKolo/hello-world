
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
	
	public static GameBack background;
	public static Player player;

	public Cube cube;
	
	//public Block block, block0, block1;
	ArrayList<Brick> brickCollection = new ArrayList<Brick>();
	//ArrayList<Edge> edgeCollection = new ArrayList<Edge>();
	
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
		
		double x0 = 0;
		double y0 = 0;
		double z0 = 0;
		
		double r0 = 50;
		
		double xA = 0;
		double yA = 50;
		double zA = 17;
		
		double rA2 = xA * xA + yA * yA + zA * zA;
		double rA = Math.sqrt(rA2);
		
		double co0A = r0 / rA;
		xA = xA * co0A;
		yA = yA * co0A;
		zA = zA * co0A;
		
		rA2 = xA * xA + yA * yA + zA * zA;
		rA = Math.sqrt(rA2);
		
		double xB = 40;
		double yB = -19;
		
		double zB2 = rA2 - xB * xB - yB * yB;
		double zB = Math.sqrt(zB2);
		
		double scAB = xA * xB + yA * yB + zA * zB;
//		System.out.println("скалярное произведение AB = " + scAB);
	
		zB = - (xA * xB + yA * yB) / zA;
//		System.out.println("zB = " + zB);
	
		scAB = xA * xB + yA * yB + zA * zB;
		
		double rB2 = xB * xB + yB * yB + zB * zB;
		double rB = Math.sqrt(rB2);

		double coAB = rA / rB;
		xB = xB * coAB;
		yB = yB * coAB;
		zB = zB * coAB;
		
		rB2 = xB * xB + yB * yB + zB * zB;
		
		double xC = -30;
		
		double yC = -26;
		
		yC = xC * (xA*zB - xB*zA) / (zA*yB - yA*zB);
		
		double zC = xC * (xA*yB - xB*yA) / (yA*zB - zA*yB);
		
		double rC2 = xC * xC + yC * yC + zC * zC;
		
		double rC = Math.sqrt(rC2);
		
		double coAC = rA / rC;
		xC = xC * coAC;
		yC = yC * coAC;
		zC = zC * coAC;
		
		rC2 = xC * xC + yC * yC + zC * zC;
		
		cube = new Cube(Cube.SIZE, 
				//new Point(GamePanel.WIDTH/2, GamePanel.HEIGHT / 2, 0),
				Point.NULL, 
				new Vector(xA, yA, zA), new Vector(xB, yB, zB), new Vector(xC, yC, zC));
		
//		blockCollection.add(new Block(cube, 0, 0, -0.017)); 
//		blockCollection.add(new Block(cube, 0, 1, -0.022)); 
//		blockCollection.add(new Block(cube, 0, 2,  0.008)); 
							
		while(true){
			
			gameUpdate();
			gameRender();
//			gameDraw();
			
			try {
				thread.sleep(90);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public void gameUpdate(){
		
		player.update();
		background.update(cube.tile[0][1][2][1]);
		
		cube.update(brickCollection);
		
		Block.updateCollection(cube.blockCollection); ///, edgeCollection);
	}
	
	
	public void gameRender(){
		
		background.draw(g);
		player.draw(g);
		
		//Block.drawCollection(g, cube.blockCollection);
		
		Brick.drawCollection(g, brickCollection);
		//cube.drawLayersVectors(g);
		//axis[rotatableAxisIndex].draw();
		cube.draw(g);
		
//	}
//	private void gameDraw(){
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
     	
		g.fillOval((int)x0, (int)y0, (int)radius, radius);
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
