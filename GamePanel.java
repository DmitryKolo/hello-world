package RubiksCube;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

	// Fields
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	
	private Thread thread;
	
	private BufferedImage image;
	private Graphics2D g;
	
	public static GameBack background;
	public static Player player;
	
	// Constructor
	public GamePanel(){
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
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
	}
	
	public void gameRender(){
		// Background draw
		background.draw(g);
		
		// Player draw
		player.draw(g);
	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

}
