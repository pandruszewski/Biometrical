package com.wat.pz.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.wat.pz.plot.Graph;
import com.wat.pz.plot.Plot;
import com.wat.pz.show.results.Results;
import com.wat.pz.wizualizacja.connection.Connect;
import com.wat.pz.wizualizacja.connection.ConnectToDB;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 258970289633208475L;

	public static JPanel p = new JPanel();

	private PropertiesWindow prop = new PropertiesWindow();
	public static ConnectToDB database;
	private Graph graph = new Graph(p);
	private static Plot plot;
	private JLayeredPane layer = this.getLayeredPane();
	private JPanel panelDolny = new JPanel();
	private JButton openProperties = new JButton("Wlasciwosci");
	private JButton startRead = new JButton("Zacznij Odczyt");
	private JButton saveResult = new JButton("Zapisz wyniki");
	private JButton stopMeasure = new JButton("Zatrzymaj pomiar");
	private JButton resultWindow = new JButton("Show Result Window");
	private ProgressMonitor progressMonitor;
	private ArrayList<Plot> plotList = new ArrayList<Plot>();

	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getLayeredPane().setSize(this.getSize().height,
				this.getSize().width);

		this.getContentPane().setLayout(new GridLayout(2, 1));

		this.getContentPane().add(p);
		panelDolny.add(openProperties);
		panelDolny.add(startRead);
		panelDolny.add(stopMeasure);
		panelDolny.add(saveResult);
		panelDolny.add(resultWindow);
		this.getContentPane().add(panelDolny);

		this.pack();
		this.setSize(500, 500);

		this.setVisible(true);
		graph.setSize(p.getSize());
		graph.setBackground(Color.black);

		database = null;
		layer.add(graph, new Integer(0));

		openProperties.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				prop.setVisible(true);

			}

		});
		if (database == null) {
			database = new ConnectToDB();
			database.removeAllRows();
		}
		startRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Plot pl : plotList) {
					layer.remove(pl);

				}
				plotList.clear();

				for (PropertiesWidget pw : prop.getOknaUstawien()) {
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					if (r < 0.5)
						r = (float) (r + 0.5);
					if (g < 0.5)
						r = (float) (g + 0.5);
					if (b < 0.5)
						r = (float) (b + 0.5);

					Color randomColor = new Color(r, g, b);
					plot = new Plot(p, graph, randomColor);
					plotList.add(plot);

					if (plot.getConnect() != null) {
						plot.getConnect().interrupt();
						plot.setConnect(null);
					} else
						plot.setConnect(null);
					System.gc();

					plot.setSize(p.getSize().width, 300);
					plot.setOpaque(false);

					plot.setConnect(new Connect(plot, (prop.getOknaUstawien()
							.indexOf(pw) + 1), database));
					// plot.getConnect().setCustomCollection(
					// new CustomCollection(new CustomListener(plot)));
					layer.add(plot, new Integer((prop.getOknaUstawien()
							.indexOf(pw) + 1))/* new Integer (1) */);
					plot.getConnect().start();
					// System.out.println((prop.getOknaUstawien().indexOf(pw) +
					// 1)
					// + "");

				}
			}

		});

		stopMeasure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Connect con = null;
				for (Plot p : plotList) {
					con = p.getConnect();
					con.interrupt();
					con.suspend();
					con.closeSocket();

				}

			}

		});

		saveResult.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg) {
				if (database.countData() > 0 && !isSumulating()) {
					JFileChooser fileChooser = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter(
							"TXT Files", "txt");
					// fileChooser.addChoosableFileFilter(filter);
					fileChooser.setFileFilter(filter);
					int wybor = fileChooser.showSaveDialog(null);
					if (wybor == JFileChooser.APPROVE_OPTION) {
						String filename = fileChooser.getSelectedFile()
								.toString();
						if (!filename.matches(".+[.][tT][xX][tT]")) {
							filename = filename + ".txt";
						}

						Component component = (Component) arg.getSource();

						// JOptionPane.showMessageDialog(null,
						// database.countData());
						progressMonitor = new ProgressMonitor(component,
								"Zapis do pliku", null, 0, database.countData());
						progressMonitor.setMinimum(0);
						progressMonitor.setMaximum(database.countData());
						int progress = 0;
						progressMonitor.setMillisToDecideToPopup(0);// pytanie
																	// czy
																	// to
																	// dobrze bo
																	// zalecenia
																	// mowia o
																	// 2
																	// sekundach
																	// dla
																	// swinga
						BufferedWriter bf = null;
						try {
							bf = new BufferedWriter(new FileWriter(new File(
									filename)));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						for (Plot p : plotList) {
							progress = database.WriteData(p.getConnect()
									.getIndexPlot(), progress, progressMonitor,
									bf);
						}
						try {
							bf.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						database.removeAllRows();
					}
				}

			}
		});

		resultWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String file = "";
				FileFilter filter = new FileNameExtensionFilter("Txt files",
						"txt");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(filter);
				int wybor = fileChooser.showOpenDialog(null);
				if (wybor == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile().toString();

					if (file.matches(".+[.][tT][xX][tT]")) {

						// Data parse = new Data(file);
						Results resultWindow = new Results(file);
						resultWindow.setVisible(true);

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

	public boolean isSumulating() {

		for (Plot pl : plotList) {
			System.out.println(pl.getConnect().isInterrupted() + " "
					+ pl.getConnect().isAlive());
			if (!pl.getConnect().isInterrupted() && pl.getConnect().isAlive()) {
				return true;
			}
		}
		return false;
	}
}
