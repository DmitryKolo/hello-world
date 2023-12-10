import java.awt.event.*;

public class Listeners implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT){
			VolumetricAngle.shift = true;
			Cube.shift = true;
			Block.shift = true;
		}
		if(key == KeyEvent.VK_RIGHT){
			VolumetricAngle.right = true;
			Cube.right = true;
			Block.right = true;
		}
		if(key == KeyEvent.VK_LEFT){
			VolumetricAngle.left = true;
			Cube.left = true;
			Block.left = true;
		}
		if(key == KeyEvent.VK_DOWN){
			VolumetricAngle.down = true;
			Cube.down = true;
			Block.down = true;
		}
		if(key == KeyEvent.VK_UP){
			VolumetricAngle.up = true;
			Cube.up = true;
			Block.up = true;
		}
		if(key == KeyEvent.VK_I){
			//Cube.rotateI = true;
			Block.rotateI = true;
		}
		if(key == KeyEvent.VK_J){
			//Cube.rotateJ = true;
			Block.rotateJ = true;
		}
		if(key == KeyEvent.VK_K){
			//Cube.rotateK = true;
			Block.rotateK = true;
		}
//		if(key == KeyEvent.VK_W){
//			Player.up = true;
//		}
//		if(key == KeyEvent.VK_X){
//			Player.down = true;
//		}
//		if(key == KeyEvent.VK_A){
//			Player.left = true;
//		}
//		if(key == KeyEvent.VK_D){
//			Player.right = true;
//		}
//		if(key == KeyEvent.VK_SPACE){
//			Player.isFiring = true;
//		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT){
			VolumetricAngle.shift = false;
			Cube.shift = false;
			Block.shift = false;
		}
		if(key == KeyEvent.VK_RIGHT){
			VolumetricAngle.right = false;
			Cube.right = false;
			Block.right = false;
		}
		if(key == KeyEvent.VK_LEFT){
			VolumetricAngle.down = false;
			Cube.left = false;
			Block.left = false;
		}
		if(key == KeyEvent.VK_DOWN){
			VolumetricAngle.down = false;
			Cube.down = false;
			Block.down = false;
		}
		if(key == KeyEvent.VK_UP){
			VolumetricAngle.up = false;
			Cube.up = false;
			Block.up = false;
		}
		if(key == KeyEvent.VK_I){
			//Cube.rotateI = false;
			Block.rotateI = false;
		}
		if(key == KeyEvent.VK_J){
			//Cube.rotateJ = false;
			Block.rotateJ = false;
		}
		if(key == KeyEvent.VK_K){
			//Cube.rotateK = false;
			Block.rotateK = false;
		}
//		if(key == KeyEvent.VK_W){
//			Player.up = false;
//		}
//		if(key == KeyEvent.VK_X){
//			Player.down = false;
//		}
//		if(key == KeyEvent.VK_A){
//			Player.left = false;
//		}
//		if(key == KeyEvent.VK_D){
//			Player.right = false;
//		}
//		if(key == KeyEvent.VK_SPACE){
//			Player.isFiring = false;
//		}
	
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
