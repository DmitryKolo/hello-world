package RubiksCube;

import java.awt.event.*;

public class Listeners implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP){
			Player.up = true;
		}
		if(key == KeyEvent.VK_DOWN){
			Player.down = true;
		}
		if(key == KeyEvent.VK_LEFT){
			Player.left = true;
		}
		if(key == KeyEvent.VK_RIGHT){
			Player.right = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP){
			Player.up = false;
		}
		if(key == KeyEvent.VK_DOWN){
			Player.down = false;
		}
		if(key == KeyEvent.VK_LEFT){
			Player.left = false;
		}
		if(key == KeyEvent.VK_RIGHT){
			Player.right = false;
		}
	
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
