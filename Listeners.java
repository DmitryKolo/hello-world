import java.awt.event.*;

public class Listeners implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT){
			VolumetricAngle.shift = true;
		}
		if(key == KeyEvent.VK_RIGHT){
			VolumetricAngle.right = true;
		}
		if(key == KeyEvent.VK_LEFT){
			VolumetricAngle.left = true;
		}
		if(key == KeyEvent.VK_DOWN){
			VolumetricAngle.down = true;
		}
		if(key == KeyEvent.VK_UP){
			VolumetricAngle.up = true;
		}
		if(key == KeyEvent.VK_W){
			Player.up = true;
		}
		if(key == KeyEvent.VK_X){
			Player.down = true;
		}
		if(key == KeyEvent.VK_A){
			Player.left = true;
		}
		if(key == KeyEvent.VK_D){
			Player.right = true;
		}
		if(key == KeyEvent.VK_SPACE){
			Player.isFiring = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT){
			VolumetricAngle.shift = false;
		}
		if(key == KeyEvent.VK_RIGHT){
			VolumetricAngle.right = false;
		}
		if(key == KeyEvent.VK_LEFT){
			VolumetricAngle.left = false;
		}
		if(key == KeyEvent.VK_DOWN){
			VolumetricAngle.down = false;
		}
		if(key == KeyEvent.VK_UP){
			VolumetricAngle.up = false;
		}
		if(key == KeyEvent.VK_W){
			Player.up = false;
		}
		if(key == KeyEvent.VK_X){
			Player.down = false;
		}
		if(key == KeyEvent.VK_A){
			Player.left = false;
		}
		if(key == KeyEvent.VK_D){
			Player.right = false;
		}
		if(key == KeyEvent.VK_SPACE){
			Player.isFiring = false;
		}
	
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
