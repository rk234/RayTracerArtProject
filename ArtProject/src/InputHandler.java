import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {
	public static final InputHandler main = new InputHandler();
	
	private boolean leftMouseDown;
	private boolean rightMouseDown;
	private boolean[] keyDown;
	
	public InputHandler() {
		leftMouseDown = false;
		rightMouseDown = false;
		keyDown = new boolean[512];
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftMouseDown = true;
		} else if(e.getButton() == MouseEvent.BUTTON2) {
			rightMouseDown = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftMouseDown = false;
		} else if(e.getButton() == MouseEvent.BUTTON2) {
			rightMouseDown = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() >= 0 && e.getKeyCode() < keyDown.length)
			keyDown[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() >= 0 && e.getKeyCode() < keyDown.length)
			keyDown[e.getKeyCode()] = false;
	}

	public boolean isLeftMouseDown() {
		return leftMouseDown;
	}

	public boolean isRightMouseDown() {
		return rightMouseDown;
	}

	public int getMouseX() {
		return (int) MouseInfo.getPointerInfo().getLocation().getX();
	}

	public int getMouseY() {
		return (int) MouseInfo.getPointerInfo().getLocation().getY();
	}
	
	public boolean keyDown(int keycode) {
		return keyDown[keycode];
	}
	
	public void reset() {
		keyDown = new boolean[512];
	}
}
