package RubiksCube;

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
	public static ArrayList<Bullet> bullets;
	
	//public static Edge edge;
	public static SquareOfEdges squareOfEdges1, squareOfEdges2, squareOfEdges3;
	
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
		bullets = new ArrayList<Bullet>();
		
		//edge = new Edge(250, 260);
		squareOfEdges1 = new SquareOfEdges(2, 50, 50, -22);
		squareOfEdges2 = new SquareOfEdges(50, -22, -30, -26);
		squareOfEdges3 = new SquareOfEdges(-30, -26, 2, 50);
				
		while(true){
			
			//long timer = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			//System.out.println(bullets.size());
			//long elapsed = (System.nanoTime() - timer) / 100000;
			//System.out.println(elapsed);
			
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
		
		// Bullets update
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).update();
			boolean remove = bullets.get(i).remove();
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		//System.out.println(bullets.size());
		
		// Edge update
		//edge.update();
		
		// square of edges update
		squareOfEdges1.update();
		squareOfEdges2.update();
		squareOfEdges3.update();
	}
	
	public void gameRender(){
		
		// Background draw
		background.draw(g);
		
		// Player draw
		player.draw(g);
		
		// Bullets draw
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g);
		}
		
		// Edge draw
		//edge.draw(g);
		
		// square of edges draw
		squareOfEdges1.draw(g);
		squareOfEdges2.draw(g);
		squareOfEdges3.draw(g);
		
	}
	
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

}
