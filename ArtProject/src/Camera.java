public class Camera {
	private Vector3 position;
	private Vector3 direction;
	private float nearPlane;
	private Vector3 viewportSize;
	
	public Camera() {
		this.position = new Vector3(0, 0, -2);
		this.direction = new Vector3(0,0,1);
		this.viewportSize = new Vector3(2*(16f/9), 2, 0);
		this.nearPlane = 1;
	}
	
	public Camera(Vector3 position, Vector3 direction) {
		this.position = position;
		this.direction = direction;
		this.viewportSize = new Vector3(2*(16f/9), 2, 0);
		this.nearPlane = 1;
	}
	
	public Ray generatePrimaryRay(int screenX, int screenY, int screenWidth, int screenHeight) {
		Vector3 adjacent = new Vector3(0,1,0).cross(direction).normalize(); //Goes to right by default
		Vector3 localUp = adjacent.cross(direction).normalize();
		
		Vector3 bottomLeft = adjacent.scale(-viewportSize.x() / 2).add(localUp.scale(-viewportSize.y()/2));
		
		return new Ray(position, bottomLeft
				.add(adjacent.scale(viewportSize.x()*((float)screenX/screenWidth)))
				.add(localUp.scale(viewportSize.y()*((float)(screenY)/screenHeight)))
				.add(direction.scale(nearPlane)).normalize());
	}
	
	public void update(float dt, int screenWidth, int screenHeight) {}
	
	public void lookAt(Vector3 point) {
		direction = point.subtract(position).normalize();
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Vector3 getDirection() {
		return direction;
	}

	public void setDirection(Vector3 direction) {
		this.direction = direction;
	}

	public float getNearPlane() {
		return nearPlane;
	}

	public void setNearPlane(float nearPlane) {
		this.nearPlane = nearPlane;
	}

	public Vector3 getViewportSize() {
		return viewportSize;
	}
	
	public void setViewportSize(Vector3 viewportSize) {
		this.viewportSize = viewportSize;
	}
	
	public void translate(Vector3 amount) {
		setPosition(position.add(amount));
	}
	
	public String toString() {
		return "Camera(" + position + ", " + direction + ")";
	}
}
