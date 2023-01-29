import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ArtProject {
	public static void main(String[] args) {
		new ArtProject().run();
	}

	public void run() {
		JFrame frame = new JFrame();
		frame.setTitle("Art Project");
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		Scene scene = initScene();
		
		FreeCamera cam = new FreeCamera(new Vector3(0,0,-2f),
				new Vector3(0,0,1), 0.001f);
		
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
        GraphicsEnvironment ge = 
        		GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int x = centerPoint.x - windowSize.width / 2;
        int y = centerPoint.y - windowSize.height / 2;    
        frame.setLocation(x,y);
        
		frame.setVisible(true);
	}
	
	public Scene initScene() {
		Scene scene = new Scene();
		float spacing = 1.3f;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				float r = 0.5f;
				scene.addObject(new BouncingSphere(
						new Vector3(i*spacing-2,-0.5f+r,j*spacing), r));
			}
		}
		scene.addObject(new Plane(new Vector3(0,1,0),
				new Vector3(0,-0.5f,0)));
		/*scene.addObject(new Plane(new Vector3(0,-1,0),
				new Vector3(0,1f,0)));
		scene.addObject(new Plane(new Vector3(1,0,0),
				new Vector3(-1f,0,0)));
		scene.addObject(new Plane(new Vector3(-1,0,0),
				new Vector3(1f,0,0)));*/
		
		
		scene.addLight(new DirectionalLight(new Vector3(1,-1,0.5f),
				new Vector3(1)));
		
		return scene;
	}
}
