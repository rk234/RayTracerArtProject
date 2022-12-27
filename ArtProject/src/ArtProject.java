import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ArtProject {
	private JFrame frame;
	
	public static void main(String[] args) {
		new ArtProject().run();
	}

	public void run() {
		frame = new JFrame();
		frame.setTitle("Art Project");
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		Scene scene = new Scene();
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				scene.addObject(new BouncingSphere(new Vector3(i*1.2f-2,0,j*1.2f), 0.5f));
		scene.addObject(new Plane(new Vector3(0,1,0), new Vector3(0,-0.5f,0)));
		scene.addLight(new DirectionalLight(new Vector3(-1,-1,1f), new Vector3(0.9f)));
		//scene.addLight(new DirectionalLight(new Vector3(0,1,0), new Vector3(0.1f)));
		//scene.addLight(new DirectionalLight(new Vector3(0,1,0), new Vector3(5)));
		//scene.addLight(new PointLight(new Vector3(1,2,-1), new Vector3(4)));
		/*scene.addObject(new Sphere(new Vector3(0,0,1), 1f) {
			@Override
			public void update(float dt) {}
			
			@Override
			public Vector3 diffuseAt(Vector3 point) {
				return normalAt(point).add(new Vector3(1)).scale(0.5f);
			}

			@Override
			public float shininess() {
				// TODO Auto-generated method stub
				return 1;
			}
		});
		/*scene.addObject(new Sphere(new Vector3(0,-99.5f,0), 100f) {
			@Override
			public void update(float dt) {}
			
			@Override
			public Vector3 diffuseAt(Vector3 point) {
				return new Vector3(1);
			}

			@Override
			public float shininess() {
				// TODO Auto-generated method stub
				return 1;
			}
		})*/
		
		FreeCamera cam = new FreeCamera(new Vector3(0,0,-1f), new Vector3(0,0,1), 0.001f);
		
		RenderPanel rp = new RenderPanel(cam, scene, 16*50, 9*50);
		ConfigPanel cp = new ConfigPanel(rp.getRenderer(), rp, scene);
		cp.setPreferredSize(new Dimension(250, 100));
		
		contentPane.add(rp);
		contentPane.add(cp);
		
		frame.add(contentPane);
        
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		Dimension windowSize = frame.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int x = centerPoint.x - windowSize.width / 2;
        int y = centerPoint.y - windowSize.height / 2;    
        frame.setLocation(x,y);
        
		frame.setVisible(true);
	}
}
