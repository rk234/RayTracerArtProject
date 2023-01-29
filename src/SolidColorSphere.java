
public class SolidColorSphere extends Sphere {
	private Vector3 color;
	private float shininess;
	
	public SolidColorSphere(Vector3 position, float radius, Vector3 color, float shininess) {
		super(position, radius);
		this.color = color;
		this.shininess = shininess;
	}
	
	
	@Override
	public Vector3 diffuseAt(Vector3 point) {
		return color;
	}

	@Override
	public float shininess() {
		return shininess;
	}

	@Override
	public void update(float dt) {}
}
