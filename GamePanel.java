import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import java.util.*;

public class GamePanel extends JPanel implements Runnable{

	// Fields
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	
	private Thread thread;
	
	private BufferedImage image;
	private Graphics2D g;
	
	public static GameBack background;
	public static Player player;
	//public static ArrayList<Bullet> bullets;
	
	public static VolumetricAngle vAngle;
	
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
		player = new Player();
		//bullets = new ArrayList<Bullet>();
		
		double xA = 2;
		double yA = 50;
		
		double xB = 50;
		double yB = -22;
		
		double xC = -30;
		double yC = -26;
		
		vAngle = new VolumetricAngle(xA, yA, xB, yB, xC, yC);
				
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
	}
	
	public void gameRender(){
		
		// Background draw
		background.draw(g);
		
		// Player draw
		player.draw(g);
		
		// Volumetric Angle draw
		vAngle.draw(g);
		
	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

}
