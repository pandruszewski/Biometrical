package com.wat.pz.show.results;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.xpath.XPathExpressionException;

import com.wat.pz.plot.Graph;

public class Results extends JFrame{
	private PlotResults plot = null;
	private Graph graph = null;
	private Data data = null;
	
	
	public Results(String file){
		try {
			data = new Data(file);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//setSize(300,300);
		JPanel panel = new JPanel();
		//panel.setSize(this.getSize());
		plot = new PlotResults(data, (JPanel)getContentPane());
		plot.setBackground(Color.black);
		JScrollPane scroll = new JScrollPane(plot);
		scroll.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				plot.repaint();
				
			}
		});
		//scroll.add(plot);
		//add(scroll);
		//add(panel);
		
		add(scroll, BorderLayout.CENTER);
		//this.pack();
		
		//graph = new Graph(panel);
		
	}
	
}
