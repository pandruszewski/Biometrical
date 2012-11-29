package com.wat.pz.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PropertiesWindow extends JFrame {

	private File f = new File("conf.properties");
	private Properties properties = new Properties();
	private JTextField ip = new JTextField(15);
	private JTextField port = new JTextField(15);
	private JButton saveButton = new JButton("Zapisz");

	public PropertiesWindow() {
		this.setSize(400, 400);
		this.getContentPane().setLayout(new GridLayout(3, 2));
		preparePanel();
		System.setProperty("file.encoding", "UTF-8");

		loadProperties();
		ip.setText(properties.getProperty("IP"));
		port.setText(properties.getProperty("PORT"));

	}

	private void preparePanel() {
		JPanel p = new JPanel();
		p.add(new JLabel("IP Hosta: "));
		this.add(p);
		p = new JPanel();
		p.add(ip);
		this.add(p);

		p = new JPanel();
		p.add(new JLabel("Port: "));
		this.add(p);
		p = new JPanel();
		p.add(port);
		this.add(p);

		p = new JPanel();
		this.add(new JPanel());

		p.add(saveButton);
		this.add(p);

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveProperties();
				dispose();
			}
		});

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
}
