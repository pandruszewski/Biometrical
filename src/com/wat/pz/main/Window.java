package com.wat.pz.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.wat.pz.plot.Graph;
import com.wat.pz.plot.Plot;
import com.wat.pz.wizualizacja.collection.CustomCollection;
import com.wat.pz.wizualizacja.collection.CustomListener;
import com.wat.pz.wizualizacja.connection.Connect;
import com.wat.pz.wizualizacja.connection.ConnectToDB;

public class Window extends JFrame {

	public static JPanel p = new JPanel();

	public static CustomCollection customCollection;

	public static ConnectToDB database;
	private Graph graph = new Graph(p);
	public static Plot plot;
	private JLayeredPane layer = this.getLayeredPane();
	private JPanel panelDolny = new JPanel();
	private JButton openProperties = new JButton("Wlasciwosci");
	private JButton startRead = new JButton("Zacznij Odczyt");
	private Connect connect;

	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getLayeredPane().setSize(this.getSize().height,
				this.getSize().width);

		this.getContentPane().setLayout(new GridLayout(2, 1));

		this.getContentPane().add(p);
		panelDolny.add(openProperties);
		panelDolny.add(startRead);
		this.getContentPane().add(panelDolny);

		this.pack();
		this.setSize(500, 500);

		this.setVisible(true);
		graph.setSize(p.getSize());
		graph.setBackground(Color.black);

		layer.add(graph, new Integer(0));

		openProperties.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				PropertiesWindow prop = new PropertiesWindow();
				prop.setVisible(true);

			}

		});

		startRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				plot = null;
				customCollection = null;
				if (connect != null) {
					connect.interrupt();
					// connect.suspend();
					// connect.destroy();
					connect = null;
				} else
					connect = null;
				System.gc();

				plot = new Plot(p, graph);
				customCollection = new CustomCollection(
						new CustomListener(plot));

				// database = new ConnectToDB();

				// watek = new Watek(plot);
				// watek.start();
				plot.setSize(p.getSize().width, 300);
				plot.setOpaque(false);
				layer.add(plot, new Integer(1));
				connect = new Connect(plot);
				connect.start();
			
				
			}

		});
	}

}
