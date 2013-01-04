package com.wat.pz.wizualizacja.connection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.wat.pz.plot.Plot;
import com.wat.pz.wizualizacja.collection.CustomCollection;
import com.wat.pz.wizualizacja.collection.CustomListener;
import com.wat.pz.wizualizacja.collection.Measurement;

public class Connect extends Thread {

	private Socket socket = null;
	private int port = 8180;
	private String addressIP;
	private Scanner in;
	public Queue<String> kolejka;
	private Plot plot;
	private CustomCollection customCollection;
	private int indexPlot;
	private int odstepPunktow = 0;
	private long wspolrzednaX = 0;
	
	private BufferedWriter bw = null;
	long connectTime = System.currentTimeMillis();

	public Connect(Plot p, int indexPlot, ConnectToDB database) {
		this.plot = p;
		odstepPunktow = p.getOdstep();
		
		customCollection = new CustomCollection(new CustomListener(p, p));
		this.indexPlot = indexPlot;
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
		try {
			bw = new BufferedWriter(new FileWriter(new File("pomiar.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addressIP = prop.get("IP_" + indexPlot).toString();
		port = Integer.parseInt(prop.get("PORT_" + indexPlot).toString());
		this.setPlot(p);
		kolejka = new PriorityQueue<String>();
		connectDevice();

	}

	private void connectDevice() {
		if (socket == null) {

			if (addressIP.matches("[1-9][0-9]*[.][0-9]*[.][0-9]*[.][0-9]*")
					&& port > 0 && port < 65536) {
				try {
					socket = new Socket(addressIP, port);
					in = new Scanner(socket.getInputStream());
					plot.setSimulated(true);
				} catch (IOException e) {

					JOptionPane.showMessageDialog(null,
							"Host o tym adresie IP nie zostal odnaleziony",
							"Blad", JOptionPane.ERROR_MESSAGE);

				}

			}

		}
	}

	public synchronized void run() {
		long a, b;
		connectTime = System.currentTimeMillis();
		if (in != null) {
			while (in.hasNext() && !socket.isClosed()) {

				// String content = in.nextLine();
				int content = in.nextInt();
				// System.out.println(content);
				long time = System.currentTimeMillis()-connectTime ;
				Date date = new Date();
				SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(
						"HH:mm:ss");

				StringBuilder sDateB = new StringBuilder(
						dateformatYYYYMMDD.format(date));
				
				String sDate=sDateB.toString();
				Measurement m = new Measurement(content,
						time,date,sDate);
			//	System.out.println("t " +time);
				this.customCollection.addLast(m);
				System.out.println("t "+m.getTime());
				wspolrzednaX = wspolrzednaX + odstepPunktow;
				try {
					bw.newLine();
					bw.write(String.valueOf(wspolrzednaX));
					bw.newLine();
					bw.write(String.valueOf(content));
					bw.newLine();
					bw.write(String.valueOf(indexPlot));
					bw.newLine();
					bw.write(String.valueOf(time));
					bw.newLine();
					bw.write(String.valueOf(sDate));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			try {
				System.out.println("zakmnieto");
				bw.close();
				socket.close();
				System.out.println(this.isAlive());
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

	public Plot getPlot() {
		return plot;
	}

	public void setPlot(Plot plot) {
		this.plot = plot;
	}

	public CustomCollection getCustomCollection() {
		return customCollection;
	}

	public void setCustomCollection(CustomCollection customCollection) {
		this.customCollection = customCollection;
	}

	public int getIndexPlot() {
		return indexPlot;
	}

	public void setIndexPlot(int indexPlot) {
		this.indexPlot = indexPlot;
	}

	public void closeSocket() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
