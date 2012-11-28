package com.wat.pz.main;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.wat.pz.connect.Connect;
import com.wat.pz.db.ConnectToDB;
import com.wat.pz.plot.Graph;
import com.wat.pz.plot.Plot;
import com.wat.pz.plot.Watek;
import com.wat.pz.wizualizacja.collection.CustomCollection;
import com.wat.pz.wizualizacja.collection.CustomListener;

public class Main {
	
	public static JPanel p = new JPanel();
	 public static	Plot plot = new Plot(p);
	public static CustomCollection<String> customCollection = new CustomCollection<String>(new CustomListener(plot));
	public static ConnectToDB database = new ConnectToDB();

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		
		Graph graph = new Graph(p);
		JLayeredPane layer = frame.getLayeredPane();
		frame.setSize(500, 500);
		frame.getLayeredPane().setSize(frame.getSize().height,
				frame.getSize().width);

		frame.getContentPane().setLayout(new GridLayout(2, 1));



		Watek watek = new Watek(plot);
		watek.start();

		frame.getContentPane().add(p);
		frame.getContentPane().add(new JButton("przycisk"));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500, 500);

		frame.setVisible(true);
		graph.setSize(p.getSize());
		graph.setBackground(Color.black);
		plot.setSize(p.getSize().width, 300);
		plot.setOpaque(false);
		
		layer.add(plot, new Integer(1));
		layer.add(graph, new Integer(0));

		Connect connect = new Connect(plot);

	}

}
