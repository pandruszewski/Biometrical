package com.wat.pz.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 258970289633208475L;

	public static JPanel p = new JPanel();

	public static CustomCollection customCollection;
	private PropertiesWindow prop = new PropertiesWindow();
	public static ConnectToDB database;
	private Graph graph = new Graph(p);
	private static Plot plot;
	private JLayeredPane layer = this.getLayeredPane();
	private JPanel panelDolny = new JPanel();
	private JButton openProperties = new JButton("Wlasciwosci");
	private JButton startRead = new JButton("Zacznij Odczyt");
	private Connect connect;
	private ArrayList<Plot> plotList;

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

				prop.setVisible(true);

			}

		});

		startRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				plot = null;
				if(plotList==null)
				{
					plotList=new ArrayList<Plot>();
				
				for (PropertiesWidget pw : prop.getOknaUstawien()) {
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					if(r<0.5)r=(float) (r+0.5);
					if(g<0.5)r=(float) (g+0.5);
					if(b<0.5)r=(float) (b+0.5);
					
					
					Color randomColor = new Color(r, g, b);
					plot = new Plot(p, graph,randomColor);
					plotList.add(plot);
					
					if (plot.getConnect() != null) {
						plot.getConnect().interrupt();
						plot.setConnect(null);
					} else
						plot.setConnect(null);
					System.gc();

					
					

					plot.setSize(p.getSize().width, 300);
					plot.setOpaque(false);
					layer.add(plot, new Integer((prop.getOknaUstawien().indexOf(pw)+1))/*new Integer (1)*/);
					plot.setConnect(new Connect(plot, (prop.getOknaUstawien().indexOf(pw)+1)));
					plot.getConnect().setCustomCollection(new CustomCollection(new CustomListener(
							plot)));
					plot.getConnect().start();
					System.out.println((prop.getOknaUstawien().indexOf(pw)+1)+"");

				}
			}
			}
		});
	}

	public PropertiesWindow getProp() {
		return prop;
	}

	public void setProp(PropertiesWindow prop) {
		this.prop = prop;
	}

	public ArrayList<Plot> getPlotList() {
		return plotList;
	}

	public void setPlotList(ArrayList<Plot> plotList) {
		this.plotList = plotList;
	}

}
