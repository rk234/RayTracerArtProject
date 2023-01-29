
public abstract class Sphere extends SceneObject {
	private float radius;
	
	public Sphere(Vector3 position, float radius) {
		setPosition(position);
		this.radius = radius;
	}
	
	@Override
	public Vector3 intersect(Ray ray) {
		Vector3 oc = ray.getOrigin().subtract(position());
		
		float a = ray.getDirection().dot(ray.getDirection());
		float b = 2 * oc.dot(ray.getDirection());
		float c = oc.dot(oc) - (radius*radius);
		
		float disc = (float) (b*b - 4*a*c);
		
		if(disc < 0) {
			return null;
		} else {
			float t0 = (float) (-b+Math.sqrt(disc)) / (2*a);
			float t1 = (float) (-b-Math.sqrt(disc)) / (2*a);
			
			float t = Math.min(t0, t1);
			
			if(t > 0)
				return ray.getOrigin().add(ray.getDirection().scale(t));
			else
				return null;
		}
	}

	@Override
	public Vector3 normalAt(Vector3 point) {
		return point.subtract(position()).normalize();
	}
	
	public String toString() {
		return "Sphere(" + position() + ", " + radius + ")";
	}
}
