import java.awt.event.*;

public class Listeners implements KeyListener{

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT)
			Cube.shift = true;
		if(key == KeyEvent.VK_RIGHT)
			Cube.right = true;
		if(key == KeyEvent.VK_LEFT)
			Cube.left = true;
		if(key == KeyEvent.VK_DOWN)
			Cube.down = true;
		if(key == KeyEvent.VK_UP)
			Cube.up = true;

		if(key == KeyEvent.VK_I)
			Cube.rotateI = true;
		if(key == KeyEvent.VK_J)
			Cube.rotateJ = true;
		if(key == KeyEvent.VK_K)
			Cube.rotateK = true;
		
		if(key == KeyEvent.VK_Q)
			Cube.rotateTopLP = true;
		if(key == KeyEvent.VK_A)
			Cube.rotateTopL0 = true;
		if(key == KeyEvent.VK_Z)
			Cube.rotateTopLM = true;
		
		if(key == KeyEvent.VK_CLOSE_BRACKET)
			Cube.rotateTopRP = true;
		if(key == KeyEvent.VK_QUOTE)
			Cube.rotateTopR0 = true;
		if(key == KeyEvent.VK_SLASH)
			Cube.rotateTopRM = true;
		
		if(key == KeyEvent.VK_W)
			Cube.rotateF[2][0] = true;
		if(key == KeyEvent.VK_E)
			Cube.rotateF[1][0] = true;
		if(key == KeyEvent.VK_R)
			Cube.rotateF[0][0] = true;
		
		
		if(key == KeyEvent.VK_SPACE)
			Cube.stabilizeMode = true;
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_SHIFT){
			//VolumetricAngle.shift = false;
			Cube.shift = false;
			//Block.shift = false;
		}
		if(key == KeyEvent.VK_RIGHT){
			//VolumetricAngle.right = false;
			Cube.right = false;
			//Block.right = false;
		}
		if(key == KeyEvent.VK_LEFT){
			//VolumetricAngle.down = false;
			Cube.left = false;
			//Block.left = false;
		}
		if(key == KeyEvent.VK_DOWN){
			//VolumetricAngle.down = false;
			Cube.down = false;
			//Block.down = false;
		}
		if(key == KeyEvent.VK_UP){
			//VolumetricAngle.up = false;
			Cube.up = false;
			//Block.up = false;
		}
		if(key == KeyEvent.VK_I){
			Cube.rotateI = false;
			//Block.rotateI = false;
		}
		if(key == KeyEvent.VK_J){
			Cube.rotateJ = false;
			//Block.rotateJ = false;
		}
		if(key == KeyEvent.VK_K){
			Cube.rotateK = false;
			//Block.rotateK = false;
		}
		if(key == KeyEvent.VK_Q){
			Cube.rotateTopLP = false;
		}
		if(key == KeyEvent.VK_A){
			Cube.rotateTopL0 = false;
		}
		if(key == KeyEvent.VK_Z){
			Cube.rotateTopLM = false;
		}
		if(key == KeyEvent.VK_CLOSE_BRACKET){
			Cube.rotateTopRP = false;
		}
		if(key == KeyEvent.VK_QUOTE){
			Cube.rotateTopR0 = false;
		}
		if(key == KeyEvent.VK_SLASH){
			Cube.rotateTopRM = false;
		}
		if(key == KeyEvent.VK_W){
			Cube.rotateBackCounterWise = false;
		}
		if(key == KeyEvent.VK_E){
			Cube.Rebuilding = false;
		}
//		if(key == KeyEvent.VK_X){
//			Player.down = false;
//		}
//		if(key == KeyEvent.VK_A){
//			Player.left = false;
//		}
//		if(key == KeyEvent.VK_D){
//			Player.right = false;
//		}
		if(key == KeyEvent.VK_SPACE){
			Cube.stabilizeMode = false;
		}
	
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
