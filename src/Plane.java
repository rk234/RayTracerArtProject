
public class Plane extends SceneObject {
	private Vector3 normal;
	
	public Plane(Vector3 normal, Vector3 position) {
		this.normal = normal.scale(-1);
		setPosition(position);
	}
	
	@Override
	public Vector3 intersect(Ray ray) {
		float denom = normal.dot(ray.getDirection());
		if (denom > 1e-6) { 
	        Vector3 p0l0 = position().subtract(ray.getOrigin()); 
	        float t = p0l0.dot(normal) / denom;
	        if(t > 0)
	        	return ray.getOrigin().add(ray.getDirection().scale(t)); 
	    }
	 
	    return null; 
	}

	@Override
	public Vector3 normalAt(Vector3 point) {
		return normal.scale(-1);
	}

	@Override
	public Vector3 diffuseAt(Vector3 point) {
		if(point.subtract(position()).magnitude() < 50) {
			if(Math.abs(point.z()) % 2 < 0.5 && Math.abs(point.x() % 2) < 0.5)
				return new Vector3(0.1f);
			else if(Math.abs(point.z()) % 2 < 0.5 || Math.abs(point.x() % 2) < 0.5)
				return new Vector3(1f);
			else
				return new Vector3(0.1f);
		} else {
			return new Vector3(0.3f);
		}
		
	}

	@Override
	public void update(float dt) {
		
	}
	
	public String toString() {
		return "Plane(" + position() + ", " + normal.scale(-1) + ")";
	}

	@Override
	public float shininess() {
		return 0.5f;
	}
}
