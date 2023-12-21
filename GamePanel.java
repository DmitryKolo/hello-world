
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import java.util.*;	

public class GamePanel extends JPanel implements Runnable{

	// Fields
	
	public static int WIDTH = 500;
	public static int HEIGHT = 500;
	
	private Thread thread;
	
	private BufferedImage image;
	private Graphics2D g;
	
	public static GameBack background;
	public static Player player;
	//public static ArrayList<Bullet> bullets;
	
	public static VolumetricAngle vAngle;
	public Cube cube;
	public Block block, block0, block1;
	
	//public Block[] blockCollection;
	
	ArrayList<Block> blockCollection = new ArrayList<Block>();
	ArrayList<Edge> edgeCollection = new ArrayList<Edge>();
	
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
//		System.out.println("xA = " + xA);
//		System.out.println("yA = " + yA);
//		System.out.println("zA = " + zA);
		
		double rA2 = xA * xA + yA * yA + zA * zA;
//		System.out.println("rA(2) = " + rA2);
		
		double rA = Math.sqrt(rA2);
//		System.out.println("rA = " + rA);
		
		double co0A = r0 / rA;
		xA = xA * co0A;
		yA = yA * co0A;
		zA = zA * co0A;
//		System.out.println("xA = " + xA);
//		System.out.println("yA = " + yA);
//		System.out.println("zA = " + zA);
		
		rA2 = xA * xA + yA * yA + zA * zA;
		rA = Math.sqrt(rA2);
//		System.out.println("rA = " + rA);
		
		double xB = 40;
		double yB = -19;
//		System.out.println("xB = " + xB);
//		System.out.println("yB = " + yB);
		
		double zB2 = rA2 - xB * xB - yB * yB;
//		System.out.println("zB(2) = " + zB2);
		
		double zB = Math.sqrt(zB2);
//		System.out.println("zB = " + zB);
		
		double scAB = xA * xB + yA * yB + zA * zB;
//		System.out.println("скалярное произведение AB = " + scAB);
	
		zB = - (xA * xB + yA * yB) / zA;
//		System.out.println("zB = " + zB);
	
		scAB = xA * xB + yA * yB + zA * zB;
//		System.out.println("скалярное произведение AB = " + scAB);
		
		double rB2 = xB * xB + yB * yB + zB * zB;
//		System.out.println("rB(2) = " + rB2);
		
		double rB = Math.sqrt(rB2);
//		System.out.println("rB = " + rB);

		double coAB = rA / rB;
		xB = xB * coAB;
		yB = yB * coAB;
		zB = zB * coAB;
//		System.out.println("xB = " + xB);
//		System.out.println("yB = " + yB);
//		System.out.println("zB = " + zB);
		
		rB2 = xB * xB + yB * yB + zB * zB;
//		System.out.println("rB(2) = " + rB2);
		
		double xC = -30;
//		System.out.println("xC = " + xC);
		
		double yC = -26;
//		System.out.println("yC = " + yC);
		
		yC = xC * (xA*zB - xB*zA) / (zA*yB - yA*zB);
//		System.out.println("yC = " + yC);
		
		double zC = xC * (xA*yB - xB*yA) / (yA*zB - zA*yB);
//		System.out.println("zC = " + zC);
		
		double rC2 = xC * xC + yC * yC + zC * zC;
//		System.out.println("rС(2) = " + rC2);
		
		double rC = Math.sqrt(rC2);
//		System.out.println("rС = " + rC);
		
		double coAC = rA / rC;
		xC = xC * coAC;
		yC = yC * coAC;
		zC = zC * coAC;
		
//		System.out.println("xC = " + xC);
//		System.out.println("yC = " + yC);
//		System.out.println("zC = " + zC);

		rC2 = xC * xC + yC * yC + zC * zC;
//		System.out.println("rС(2) = " + rC2);
		
		vAngle = new VolumetricAngle(xA, yA, zA, xB, yB, zB, xC, yC, zC);
		
		//cube = new Cube(3, new Point(WIDTH/2, HEIGHT/2, 0), new Vector(xA, yA, zA), new Vector(xB, yB, zB), new Vector(xC, yC, zC));
		cube = new Cube(3, new Point(0, 0, 0), new Vector(50, 1, -2), new Vector(0, 50, -5), new Vector(1.5, 5, 50));
		
		blockCollection.add(new Block(cube, 1, 0, 0, 0.0035)); 
		blockCollection.add(new Block(cube, 1, 1, 1, 0.0011)); 
		blockCollection.add(new Block(cube, 1, 2, 2, 0.0008)); 
							
		while(true){
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			try {
				thread.sleep(33);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void gameUpdate(){
		
		// Background update
		background.update();
		
		// Player update
		player.update();
		
		// Volumetric Angle update
		vAngle.update();
		
		cube.update();
		
		Block.updateCollection(blockCollection, edgeCollection);
		
	}
	
	public void gameRender(){
		
		// Background draw
		background.draw(g);
		
		// Volumetric Angle draw
		vAngle.draw(g);
		
		// Player draw
		player.draw(g);
		
		//cube.draw(g);
		
//		block.draw(g, Color.BLACK);
//		block0.draw(g, Color.GREEN);
//		block1.draw(g, Color.RED);
		
		Block.drawCollection(g, blockCollection);

	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public static void drawLine(Graphics2D g, Point beginingPoint, Point endingPoint, Point originPoint){
		
//		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
//	   	g.setStroke(dashed); //2d
		
    	double xB = beginingPoint.x + originPoint.x;
    	double yB = beginingPoint.y + originPoint.y;
    	double xE = endingPoint.x + originPoint.x;
    	double yE = endingPoint.y + originPoint.y;
    	
		g.drawLine((int)xB, (int)yB, (int)xE, (int)yE);
	}

}
