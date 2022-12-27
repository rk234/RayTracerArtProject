
public class Hit {
	private SceneObject obj;
	private Vector3 point;
	
	public Hit(SceneObject obj, Vector3 point) {
		this.obj = obj;
		this.point = point;
	}

	public SceneObject getObj() {
		return obj;
	}

	public Vector3 getPoint() {
		return point;
	}
}
