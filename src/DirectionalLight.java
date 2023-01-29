
public class DirectionalLight extends Light {
	private Vector3 direction;
	private Vector3 intensity;
	
	public DirectionalLight(Vector3 direction, Vector3 intensity) {
		this.direction = direction;
		this.intensity = intensity;
	}
	
	@Override
	public Vector3 lVector(Vector3 p) {
		return direction.scale(-1).normalize();
	}
	
	@Override
	public Vector3 intensity() {
		return intensity;
	}
}
