package com.wat.pz.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PropertiesWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int liczbaOdczytow;
	private File f = new File("conf.properties");
	private Properties properties = new Properties();
	private JTextField ip = new JTextField(15);
	private JTextField zrodla = new JTextField(15);
	private JTextField port = new JTextField(15);
	private JButton saveButton = new JButton("Zapisz");
	private JButton ustawZrodla = new JButton("Ustaw iloœæ Ÿróde³");
	private final int WINDOW_HEIGHT = 400;
	private final int WINDOW_WIDTH = 400;
	private JPanel panel = new JPanel();
	private JScrollPane scroll = new JScrollPane(panel);
	private ArrayList<PropertiesWidget> oknaUstawien = new ArrayList<PropertiesWidget>();

	public PropertiesWindow() {

		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setResizable(false);
		System.setProperty("file.encoding", "UTF-8");

		loadProperties();

		liczbaOdczytow = Integer.valueOf(properties.getProperty("HOSTS"));
		preparePanel();
		setProperties();
		 zrodla.setText(properties.getProperty("HOSTS"));
		System.out.println(liczbaOdczytow + "");

	}

	private void setProperties() {
		ip.setText(properties.getProperty("IP"));
		port.setText(properties.getProperty("PORT"));
  
		for (PropertiesWidget pw : oknaUstawien) {
			pw.getIp().setText(
					properties.getProperty("IP_"
							+ (oknaUstawien.indexOf(pw) + 1)));
			pw.getPort().setText(
					properties.getProperty("PORT_"
							+ (oknaUstawien.indexOf(pw) + 1)));
		}
	}

	private void preparePanel() {
		this.setLayout(new BorderLayout());

		JPanel iloscKomponentow = new JPanel();
		iloscKomponentow.add(ustawZrodla);
		iloscKomponentow.add(zrodla);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < liczbaOdczytow; i++) {

			PropertiesWidget pw = new PropertiesWidget(i, WINDOW_WIDTH,
					WINDOW_HEIGHT);
			oknaUstawien.add(pw);
			panel.add(pw);
		}
		this.add(iloscKomponentow, BorderLayout.NORTH);
		this.add(saveButton, BorderLayout.SOUTH);
		scroll = new JScrollPane(panel);
		this.add(scroll, BorderLayout.WEST);


		ustawZrodla.addActionListener(new ActionListener() {

			@Override
			// astanowilbym sie nad sensem tych akcji... przeciez to sa an akzda
			// akcje bajery.... o_O
			public void actionPerformed(ActionEvent e) {
				setSources();
				setProperties();
			}
		});

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveProperties();
				dispose();
			}
		});

	}

	private void setSources() {// wyedytowac save proprties
		liczbaOdczytow = Integer.parseInt(zrodla.getText());
		
		panel.removeAll();
		oknaUstawien.clear();
		this.remove(scroll);
		for (int i = 0; i < liczbaOdczytow; i++) {

			PropertiesWidget pw = new PropertiesWidget(i, WINDOW_WIDTH,
					WINDOW_HEIGHT);
			oknaUstawien.add(pw);
			panel.add(pw);
		}
		scroll = new JScrollPane(panel);
		this.add(scroll, BorderLayout.WEST);

		this.revalidate();
		this.repaint();

	}

	public void loadProperties() {
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		InputStream is;
		try {
			is = new FileInputStream(f);
			// ³adujemy nasze ustawienia
			properties.load(is);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveProperties() {

		OutputStream os;
		try {
			os = new FileOutputStream(f);
			properties.setProperty("IP", ip.getText());
			properties.setProperty("PORT", port.getText());
			
			for (PropertiesWidget pw : oknaUstawien) {
				
				properties.setProperty("IP_"+(pw.getIndex()+1), pw.getIp().getText());
				
				properties.setProperty("PORT_"+(pw.getIndex()+1), pw.getPort().getText());
			}
			properties.store(os, null);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

			// setVisible(false);
			dispose();
		}

	}

	public ArrayList<PropertiesWidget> getOknaUstawien() {
		return oknaUstawien;
	}

	public void setOknaUstawien(ArrayList<PropertiesWidget> oknaUstawien) {
		this.oknaUstawien = oknaUstawien;
	}

	public int getLiczbaOdczytow() {
		return liczbaOdczytow;
	}

	public void setLiczbaOdczytow(int liczbaOdczytow) {
		this.liczbaOdczytow = liczbaOdczytow;
	}
}
