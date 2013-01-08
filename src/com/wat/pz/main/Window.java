package com.wat.pz.main;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.thehowtotutorial.splashscreen.JSplash;
import com.wat.pz.plot.Graph;
import com.wat.pz.plot.Plot;
import com.wat.pz.results.Results;
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
	public volatile static JLayeredPane layer;
	private JPanel panelDolny = new JPanel();
	private JButton openProperties = new JButton("Wlasciwosci");
	private JButton startRead = new JButton("Zacznij Odczyt");
	private JButton saveResult = new JButton("Zapisz wyniki");
	private JButton stopMeasure = new JButton("Zatrzymaj pomiar");
	private JButton resultWindow = new JButton("Show Result Window");
	private JButton exitButton = new JButton("Wyjdz");
	private ProgressMonitor progressMonitor;
	private ArrayList<Plot> plotList = new ArrayList<Plot>();
	private BufferStrategy bs;

	public Window() {
		super("Muscle Explorer");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.getLayeredPane().setSize(this.getSize().height,
				this.getSize().width);
		layer = new JLayeredPane();
		p.setLayout(new BorderLayout());
		p.add(layer);

		this.getContentPane().setLayout(new GridLayout(2, 1));

		bs = getBufferStrategy();
		this.getContentPane().add(p);

		panelDolny.add(openProperties);
		panelDolny.add(startRead);
		panelDolny.add(stopMeasure);
		panelDolny.add(saveResult);
		panelDolny.add(resultWindow);
		panelDolny.add(exitButton);
		this.getContentPane().add(panelDolny);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("ikonka.png");

		this.setIconImage(image);

		if (!SystemTray.isSupported()) {
			return;
		}

		SystemTray tray = SystemTray.getSystemTray();

		PopupMenu menu = new PopupMenu();

		MenuItem messageItem = new MenuItem("Show Window");
		messageItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
			}
		});
		menu.add(messageItem);

		MenuItem closeItem = new MenuItem("Close");
		closeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(closeItem);
		TrayIcon icon = new TrayIcon(image, "Muscle Explorer", menu);
		icon.setImageAutoSize(true);

		try {
			tray.add(icon);
		} catch (AWTException e2) {
			e2.printStackTrace();
		}

		this.pack();
		this.setSize(500, 500);

		graph.setSize(p.getSize());
		graph.setBackground(Color.black);

//		try {
//			JSplash jsplash = new JSplash(
//					Window.class.getResource("splash.jpg"), true, true, false,
//					null, null, Color.red, Color.black);
//			jsplash.splashOn();
//			jsplash.setProgress(20, "Loading");
//			Thread.sleep(500);
//			jsplash.setProgress(40, "Loading");
//			Thread.sleep(500);
//			jsplash.setProgress(60, "Loading");
//			Thread.sleep(500);
//			jsplash.setProgress(80, "Loading");
//			Thread.sleep(500);
//			jsplash.setProgress(100, "Loading");
//			Thread.sleep(500);
//			jsplash.splashOff();
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}

		database = null;
		layer.add(graph, new Integer(0));
		layer.setDoubleBuffered(true);
		this.setVisible(true);
		openProperties.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				prop.setVisible(true);

			}

		});

		startRead.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Plot pl : plotList) {
					layer.remove(pl);

				}
				plotList.clear();

				for (PropertiesWidget pw : prop.getOknaUstawien()) {

					plot = new Plot(p, graph, bs);
					plot.setPropertiesWidget(pw);
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
					layer.add(plot, new Integer((prop.getOknaUstawien()
							.indexOf(pw) + 1))/* new Integer (1) */);
					plot.getConnect().start();

				}
			}

		});

		stopMeasure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Connect con = null;
				for (Plot p : plotList) {
					con = p.getConnect();
					con.closeSocket();

				}

			}

		});

		saveResult.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg) {
				if (!isSumulating() && wasSimulated()) {
					JFileChooser fileChooser = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter(
							"TXT Files", "txt");
					fileChooser.setFileFilter(filter);
					int wybor = fileChooser.showSaveDialog(null);
					if (wybor == JFileChooser.APPROVE_OPTION) {
						String filename = fileChooser.getSelectedFile()
								.toString();
						if (!filename.matches(".+[.][tT][xX][tT]")) {
							filename = filename + ".txt";
						}

						Component component = (Component) arg.getSource();

						File file = new File("pomiar.txt");
						if (file.exists()) {
							file.renameTo(new File(filename));
						}

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

						Results resultWindow = new Results(file);

						resultWindow.setVisible(true);

					}
				}
			}

		});

		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

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

	public boolean wasSimulated() {
		for (Plot pl : plotList) {
			System.out.println(pl.getConnect().isInterrupted() + " "
					+ pl.getConnect().isAlive());
			if (pl.isSimulated()) {
				return true;
			}
		}
		return false;
	}
}
