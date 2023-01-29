import java.awt.image.BufferedImage;

public class Renderer {
	private Scene scene;
	private Camera cam;
	
	private int numBounce = 0;
	private final int NUM_THREADS = 4;
	private Thread[] renderThreads = new Thread[NUM_THREADS];
	
	private final float EPSILON = 0.001f;
	
	public Renderer(Scene scene, Camera cam) {
		this.scene = scene;
		this.cam = cam;
	}
	
	public void renderTo(BufferedImage frameBuffer) {
		for(int t = 0; t < NUM_THREADS; t++) {
			int startPixelIdx = (frameBuffer.getWidth()/NUM_THREADS)*t;
			int endPixelIdx;
			
			if(t == NUM_THREADS-1)
				endPixelIdx = frameBuffer.getWidth();
			else
				endPixelIdx = startPixelIdx + (frameBuffer.getWidth()/NUM_THREADS);
			
			renderThreads[t] = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = startPixelIdx; i < endPixelIdx; i++) {
						for(int j = 0; j < frameBuffer.getHeight(); j++) {
							Ray ray = cam.generatePrimaryRay(i, j, 
									frameBuffer.getWidth(), frameBuffer.getHeight());
							Vector3 color = cast(ray, numBounce);
							frameBuffer.setRGB(i, j, ColorUtils.rgbToHex(color));
						}
					}
				}
			});
		}
		
		for(Thread t : renderThreads) {
			t.start();
		}
		
		try {
			for(Thread t : renderThreads) {
				t.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
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
				Hit shadowHit = intersect(new Ray(point.add(normal.scale(EPSILON)), l));
				if(light instanceof DirectionalLight) {
					if(shadowHit == null)
						color = color.add(hit.getObj().diffuseAt(point)
								.multiply(light.intensity()
										.scale(Math.max(0, normal.dot(l)))));
				} else if(light instanceof PointLight) {
					PointLight p = (PointLight) light;
					if(shadowHit.getPoint().subtract(p.getPosition()).magnitude() < point.subtract(p.getPosition()).magnitude())
						color = color.add(hit.getObj().diffuseAt(point)
								.multiply(light.intensity()
										.scale(Math.max(0, normal.dot(l)))));
				}

				if(numBounce > 0 && hit.getObj().shininess() > 0)
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
	
	public Camera getCamera() {
		return cam;
	}
}
