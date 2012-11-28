package com.wat.pz.connect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.wat.pz.main.Main;
import com.wat.pz.plot.Plot;

public class Connect {

	private Socket socket = null;
	private int port = 8180;
	private String addressIP = "169.254.144.49";
	private Scanner in;
	public Queue<String> kolejka;
	private Plot plot;

	public Connect(Plot p) {
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

				} catch (IOException e) {

					JOptionPane.showMessageDialog(null,
							"Host o tym adresie IP nie zostal odnaleziony",
							"Blad", JOptionPane.ERROR_MESSAGE);

				}
				try {
					in = new Scanner(socket.getInputStream());
				} catch (IOException e) {

					e.printStackTrace();
				}

			}

			startReceive();
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startReceive() {
		if (in != null) {
			while (in.hasNext()) {
				String content = in.nextLine();
				// System.out.println(content);
				// kolejka.add(content);
				Main.customCollection.addLast(content);
				//Main.database.addData(content);
				plot.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
