import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConfigPanel extends JPanel {
	
	public ConfigPanel(Renderer renderer, RenderPanel rp, Scene scene) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createTitledBorder("Configuration"));
		setAlignmentY(TOP_ALIGNMENT);
		
		JCheckBox rStatsCheckBox = new JCheckBox("Show Renderer Statistics");
		rStatsCheckBox.setAlignmentX(LEFT_ALIGNMENT);
		rStatsCheckBox.setSelected(rp.areRenderStatsShown());
		rStatsCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rp.setShowRenderStats(rStatsCheckBox.isSelected());
			}
			
		});
		
		JLabel resolutionLbl = new JLabel("Render Resolution Scale (%)");
		resolutionLbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JSlider renderResSlider = new JSlider(0, 100);
		renderResSlider.setMajorTickSpacing(10);
		renderResSlider.setValue((int) (rp.getResolutionScaler() * 100f));
		//numBounceSlider.setSnapToTicks(true);
		renderResSlider.setPaintTicks(true);
		renderResSlider.setPaintLabels(true);
		renderResSlider.setAlignmentX(LEFT_ALIGNMENT);
		
		renderResSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				rp.setResolutionScaler(Math.max(0.01f,renderResSlider.getValue()/100f));
			}
		});
		
		JLabel lightBounceLbl = new JLabel("Number of Light Bounces");
		lightBounceLbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JSlider numBounceSlider = new JSlider(0, 4);
		numBounceSlider.setMajorTickSpacing(1);
		numBounceSlider.setSnapToTicks(true);
		numBounceSlider.setPaintTicks(true);
		numBounceSlider.setPaintLabels(true);
		numBounceSlider.setValue(renderer.getNumBounce());
		numBounceSlider.setAlignmentX(LEFT_ALIGNMENT);
		
		numBounceSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				renderer.setNumBounce(numBounceSlider.getValue());
			}
		});
		
		JLabel sceneLbl = new JLabel("Scene Objects (" + scene.getObjects().size() + ")");
		sceneLbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		JList list = new JList(scene.getObjects().toArray()); //data has type Object[]
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(list);
		
		JButton captureInputsBtn = new JButton("Capture Input");
		captureInputsBtn.setAlignmentX(LEFT_ALIGNMENT);
		captureInputsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rp.requestFocus();
				InputHandler.main.reset();
			}
		});
		
		new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listScroller.repaint();
			}
		}).start();
		
		JButton addObjectBtn = new JButton("Add Object");
		addObjectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				//scene.addObject(new BouncingSphere(new Vector3(-5, 0, 5), 0.5f));
			}
		});
		
		add(rStatsCheckBox);
		add(Box.createVerticalStrut(8));
		add(resolutionLbl);
		add(renderResSlider);
		add(lightBounceLbl);
		add(numBounceSlider);
		add(Box.createVerticalStrut(8));
		add(sceneLbl);
		add(listScroller);
		add(Box.createVerticalStrut(8));
		add(addObjectBtn);
		add(captureInputsBtn);
	}
}
