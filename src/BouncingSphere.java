
public class BouncingSphere extends SolidColorSphere {
	private float time = 0;
	
	public BouncingSphere(Vector3 position, float radius) {
		super(position, radius, new Vector3((float)(Math.random()*0.8) + 0.2f,
				(float)(Math.random()*0.8) + 0.2f,
				(float)(Math.random()*0.8) + 0.2f), 0.8f);
	}

	public void update(float dt) {
		time+=dt/500;
		setPosition(new Vector3(position().x(), 
				(float) (1+Math.sin(time + position().x() + position().z()))/5,
				position().z()));
	}
}
