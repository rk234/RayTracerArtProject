import java.awt.image.BufferedImage;

public class Renderer {
	private Scene scene;
	private Camera cam;
	
	private int numBounce = 0;
	
	public Renderer(Scene scene, Camera cam) {
		this.scene = scene;
		this.cam = cam;
	}
	
	public void renderTo(BufferedImage frameBuffer) {
		for(int i = 0; i < frameBuffer.getWidth(); i++) {
			for(int j = 0; j < frameBuffer.getHeight(); j++) {
				Ray ray = cam.generatePrimaryRay(i, j, 
						frameBuffer.getWidth(), frameBuffer.getHeight());
				Vector3 color = cast(ray, numBounce);
				frameBuffer.setRGB(i, j, ColorUtils.rgbToHex(color));
			}
		}
	}
	
	private Vector3 cast(Ray r, int depth) {
		if(depth < 0)
			return skyColor(r);
		
		Hit hit = intersect(r);
		
		if(hit != null) {
			Vector3 point = hit.getPoint();
			Vector3 normal = hit.getObj().normalAt(point);
			Vector3 color = hit.getObj().diffuseAt(point).scale(0.3f); //ambient
			
			for(Light light : scene.getLights()) {
				Vector3 l = light.lVector(point);
				if(intersect(new Ray(point.add(normal.scale(0.01f)), l)) == null)
					color = color.add(hit.getObj().diffuseAt(point).multiply(light.intensity().scale(Math.max(0, normal.dot(l)))));

				if(numBounce > 0)
					color = color.scale(1-hit.getObj().shininess())
					.add(color.multiply(cast(r.reflect(normal, point), depth-1))
							.scale(hit.getObj().shininess()));
			}
			
			return color;
		} else {
			return skyColor(r);
		}
	}
	
	private Hit intersect(Ray r) {
		Hit closest = null;
		float minDist = Float.MAX_VALUE;
		
		for(SceneObject obj : scene.getObjects()) {
			Vector3 intersect = obj.intersect(r);
			if(intersect != null) {
				float dist = intersect.subtract(r.getOrigin()).magnitude();
				if(dist < minDist) {
					closest = new Hit(obj, intersect);
					minDist = dist;
				}
			}
		}
		return closest;
	}
	
	private Vector3 skyColor(Ray r) {
		float t = 0.5f*(r.getDirection().y() + 1.0f);
	    return new Vector3(1-t, 1-t, 1-t).add(
	    		new Vector3((t * 138f/255), (t*188f/255),(t)));
	}
	
	public void setNumBounce(int n) {
		this.numBounce = n;
	}
	
	public int getNumBounce() {
		return numBounce;
	}
}
