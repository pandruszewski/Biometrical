package com.wat.pz.wizualizacja.connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.wat.pz.main.Window;
import com.wat.pz.plot.Plot;

public class Connect extends Thread {

	private Socket socket = null;
	private int port = 8180;
	private String addressIP;
	private Scanner in;
	public Queue<String> kolejka;
	private Plot plot;

	public Connect(Plot p) {

		Properties prop = new Properties();
		InputStream in;
		try {
			in = new FileInputStream(new File("conf.properties"));
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addressIP = prop.get("IP").toString();
		port = Integer.parseInt(prop.get("PORT").toString());
		this.plot = p;
		kolejka = new PriorityQueue<String>();
		connectDevice();

	}

	private void connectDevice() {
		if (socket == null) {
			String portNumber = "8180";
			/*
			 * addressIP = JOptionPane.showInputDialog(null,
			 * "Podaj adres ip urzadzenia w celu polaczenia"); portNumber =
			 * JOptionPane.showInputDialog("Podaj numer portu");
			 */

			if (portNumber.matches("[1-9][0-9]+")) {
				port = Integer.parseInt(portNumber);
				portNumber = null;
				System.gc();

			}
			if (addressIP.matches("[1-9][0-9]*[.][0-9]*[.][0-9]*[.][0-9]*")
					&& port > 0 && port < 65536) {
				try {
					socket = new Socket(addressIP, port);
					in = new Scanner(socket.getInputStream());
				} catch (IOException e) {

					JOptionPane.showMessageDialog(null,
							"Host o tym adresie IP nie zostal odnaleziony",
							"Blad", JOptionPane.ERROR_MESSAGE);

				}

			}

			// startReceive();
			/*
			 * try { socket.close(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); }
			 */
		}
	}

	public synchronized void run() {
		long a, b;
		if (in != null) {
			while (in.hasNext()) {

				String content = in.nextLine();
				// System.out.println(content);
				// kolejka.add(content);

				Window.customCollection.addLast(Double.parseDouble(content));
				//plot.repaint();

				/*plot.updateUI();
				plot.pai*/
				/*try {
					System.out.println("stopuje watek");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				// Main.database.addData(content);
				//System.out.println("zaraz wchodze do repainta");
				 //plot.repaint();
				
				
				/*try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				 

			}
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void save() {
		BufferedWriter bf = null;
		try {
			bf = new BufferedWriter(new FileWriter(
					"C:\\Users\\user\\Desktop\\Wyniki_Urzadzenia.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bf != null) {
			for (String s : kolejka) {
				try {

					bf.write(s);
					bf.newLine();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			bf.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
