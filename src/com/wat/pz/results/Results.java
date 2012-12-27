package com.wat.pz.results;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.xpath.XPathExpressionException;

import com.wat.pz.plot.Graph;

public class Results extends JFrame {
	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			Toolkit.getDefaultToolkit().removeAWTEventListener(awt);
			//System.out.println("Usuwanie");
		}
	};

	private PlotResults plot = null;
	private Graph graph = null;
	private Data data = null;
	private JSlider sliderY;
	private JSlider sliderX;
	private AWTEventListener awt;

	public Results(String file) {
		try {
			data = new Data(file);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// setSize(300,300);
		JPanel panel = new JPanel();
		sliderY = new JSlider(JSlider.VERTICAL, 10, 30, 10);
		sliderX = new JSlider(JSlider.HORIZONTAL, 10, 30, 10);
		// panel.setSize(this.getSize());
		plot = new PlotResults(data, (JPanel) getContentPane(), this);

		awt = new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				// TODO Auto-generated metho stub
				KeyEvent e = (KeyEvent) event;

				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {

					if (e.getID() == KeyEvent.KEY_PRESSED) {
						plot.copyImage(1);
					}
				}
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
					plot.copyImage(2);
					
				}
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
					if (e.getID() == KeyEvent.KEY_PRESSED) {
						plot.zaznaczenie();
					}
					
					
				}
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
					if (e.getID() == KeyEvent.KEY_PRESSED) {
						plot.setMilimeterMode(!plot.isMilimeterMode());
					}
					
					
				}
				
			
			}
		};
		Toolkit.getDefaultToolkit().addAWTEventListener(awt,
				AWTEvent.KEY_EVENT_MASK);

		this.addWindowListener(new WindowEventHandler());

		plot.setBackground(Color.black);
		JScrollPane scroll = new JScrollPane(plot,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getHorizontalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {

					@Override
					public void adjustmentValueChanged(AdjustmentEvent arg0) {
						plot.repaint();

					}
				});
		scroll.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {

					@Override
					public void adjustmentValueChanged(AdjustmentEvent e) {
						plot.repaint();

					}
				});

		sliderY.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg) {
				System.out.println(sliderY.getValue());
				plot.setPrzeskalujWykresY(sliderY.getValue());
			}

		});
		sliderX.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				plot.setPrzeskalujWykresX(sliderX.getValue());
			}

		});
		// scroll.add(plot);
		// add(scroll);
		// add(panel);

		add(scroll, BorderLayout.CENTER);
		add(sliderX, BorderLayout.NORTH);
		add(sliderY, BorderLayout.WEST);
		this.pack();

		// graph = new Graph(panel);

	}

}
