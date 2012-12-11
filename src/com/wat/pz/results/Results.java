package com.wat.pz.results;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.xpath.XPathExpressionException;

import com.wat.pz.plot.Graph;

public class Results extends JFrame{
	private PlotResults plot = null;
	private Graph graph = null;
	private Data data = null;
	private JSlider sliderY;
	private JSlider sliderX;
	
	
	public Results(String file){
		try {
			data = new Data(file);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setSize(300,300);
		JPanel panel = new JPanel();
		sliderY = new JSlider(JSlider.VERTICAL, 10, 30, 10);
		sliderX = new JSlider(JSlider.HORIZONTAL, 10, 30, 10);
		//panel.setSize(this.getSize());
		plot = new PlotResults(data, (JPanel)getContentPane());
		plot.setBackground(Color.black);
		JScrollPane scroll = new JScrollPane(plot, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				plot.repaint();
				
			}
		});
		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				plot.repaint();
				
			}
		});
		
		sliderY.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg) {
				System.out.println(sliderY.getValue());
				plot.setPrzeskalujWykresY(sliderY.getValue());
			}
			
		});
		sliderX.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				plot.setPrzeskalujWykresX(sliderX.getValue());
			}
			
		});
		//scroll.add(plot);
		//add(scroll);
		//add(panel);
		
		add(scroll, BorderLayout.CENTER);
		add(sliderX, BorderLayout.NORTH);
		add(sliderY, BorderLayout.WEST);
		this.pack();
		
		//graph = new Graph(panel);
		
	}
	
}