import java.awt.event.KeyEvent;

public class FreeCamera extends Camera {
	private float camMoveSpeed = 0.05f;
	
	private float lastMouseX = -1;
	private float lastMouseY = -1;
	
	private float yaw = 90;
	private float pitch = 0;
	
	public FreeCamera(float camMoveSpeed) {
		super();
		this.camMoveSpeed = camMoveSpeed;
	}
	
	public FreeCamera(Vector3 position, Vector3 direction, float camMoveSpeed) {
		super(position, direction);
		this.camMoveSpeed = camMoveSpeed;
	}
	
	public void update(float dt, int screenWidth, int screenHeight) {
		if(lastMouseX == -1 || lastMouseY == -1) {
			lastMouseX = InputHandler.main.getMouseX();
			lastMouseY = InputHandler.main.getMouseY();
		}
		
		if(InputHandler.main.keyDown(KeyEvent.VK_W))
			translate(getDirection().scale(camMoveSpeed*dt));
		if(InputHandler.main.keyDown(KeyEvent.VK_S))
			translate(getDirection().scale(-camMoveSpeed*dt));
		if(InputHandler.main.keyDown(KeyEvent.VK_A))
			translate(getDirection().cross(new Vector3(0,1,0)).normalize().scale(camMoveSpeed*dt));
		if(InputHandler.main.keyDown(KeyEvent.VK_D))
			translate(getDirection().cross(new Vector3(0,1,0)).normalize().scale(-camMoveSpeed*dt));
		
		if(InputHandler.main.isLeftMouseDown()) {
			float xoffset = -(InputHandler.main.getMouseX() - lastMouseX);
		    float yoffset = lastMouseY - InputHandler.main.getMouseY();
		    
		    float sensitivity = 0.3f;
		    xoffset *= sensitivity;
		    yoffset *= sensitivity;
	
		    yaw   += xoffset;
		    pitch += yoffset;
	
		    if(pitch > 89.0f)
		        pitch = 89.0f;
		    if(pitch < -89.0f)
		        pitch = -89.0f;
		    
		    setDirection(new Vector3(
		    	(float)(Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch))),
		    	(float)Math.sin(Math.toRadians(pitch)),
		    	(float)(Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)))
		    ).normalize());
		}
	    lastMouseX = InputHandler.main.getMouseX();
	    lastMouseY = InputHandler.main.getMouseY();
	}
}
