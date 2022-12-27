
public class BouncingSphere extends Sphere {
	private float time = 0;
	private Vector3 vel;
	private Vector3 col = new Vector3((float)Math.random(), (float)Math.random(), (float)Math.random());
	
	public BouncingSphere(Vector3 position, float radius) {
		super(position, radius);
		vel = new Vector3(0);
	}

	public void update(float dt) {
		time+=dt/500;
		setPosition(new Vector3(position().x(), (float) (1+Math.sin(time + position().x() + position().z()))/5, position().z()));
		setPosition(position().add(vel));
	}
	
	public float shininess() {
		return 1f;
	}
	
	public Vector3 diffuseAt(Vector3 point) {
		return col;
	}
}
