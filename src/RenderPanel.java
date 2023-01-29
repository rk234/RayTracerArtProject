import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class RenderPanel extends JPanel {
	private Renderer renderer;
	private BufferedImage frameBuffer;
	private Scene scene;
	private Camera cam;
	private int prevWidth;
	private int prevHeight;
	private float dt = 1;
	
	private boolean showRenderStats = true;
	private float resolutionScale = 0.7f;
	
	public RenderPanel(Camera cam, Scene scene, int width, int height) {
		this.frameBuffer = createFrameBuffer(width, height);
		this.renderer = new Renderer(scene, cam);
		this.scene = scene;
		this.cam = cam;
		setPreferredSize(new Dimension(width, height));
		
		addKeyListener(InputHandler.main);
		addMouseListener(InputHandler.main);
		
		setFocusable(true);
		requestFocus();
		
		prevWidth = width;
		prevHeight = height;
		
		loop();
	}
	
	public void paintComponent(Graphics g) {
		if(getWidth() != prevWidth || getHeight() != prevHeight)
			frameBuffer = createFrameBuffer(getWidth(), getHeight());
		
		renderer.renderTo(frameBuffer);
		g.drawImage(frameBuffer, 0, 0, getWidth(), getHeight(), null);
		
		if(showRenderStats)
			drawRenderStats(g);
		
		prevHeight = getHeight();
		prevWidth = getWidth();
	}
	
	private void drawRenderStats(Graphics g) {
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRect(0, 0, 275, 95);
		g.setColor(Color.GREEN);
		g.setFont(new Font("Monospaced", Font.ITALIC, 14));
		g.drawString("FPS: " + Math.round(1000/dt), 5, 15);
		g.drawString("Frame Time: " + Math.round(dt) + "ms", 5, 30);
		g.drawString("Resolution: " + frameBuffer.getWidth() + "x" + frameBuffer.getHeight(),5, 45);
		g.drawString("Cam Pos: " + cam.getPosition(), 5, 60);
		g.drawString("Cam Dir: " + cam.getDirection(), 5, 75);
		
		g.setFont(new Font("Monospaced", Font.BOLD, 14));
		if(!hasFocus()) {
			g.setColor(Color.RED);
			g.drawString("Not Capturing Keyboard Input!", 5, 90);
		} else {
			g.drawString("Capturing Keyboard Input", 5, 90);
		}
	}
	
	private BufferedImage createFrameBuffer(int width, int height) {
		return new BufferedImage((int) (width*resolutionScale), (int) (height*resolutionScale), BufferedImage.TYPE_INT_RGB);
	}
	
	private void loop() {
		new Timer(1000/30, new ActionListener() {
			private long prevTime = -1;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(prevTime == -1)
					prevTime = System.currentTimeMillis();
				dt = System.currentTimeMillis()-prevTime;
				
				scene.update(dt);
				cam.update(dt, getWidth(), getHeight());
				repaint();
								
				prevTime = System.currentTimeMillis();

			}
		}).start();
	}
	
	public Renderer getRenderer() {
		return renderer;
	}
	
	public boolean areRenderStatsShown() {
		return showRenderStats;
	}
	
	public void setShowRenderStats(boolean show) {
		showRenderStats = show;
	}
	
	public float getResolutionScale() {
		return resolutionScale;
	}
	
	public void setResolutionScale(float s) {
		resolutionScale = s;
		frameBuffer = createFrameBuffer(getWidth(), getHeight());
	}
}
