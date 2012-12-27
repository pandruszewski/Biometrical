package com.wat.pz.plot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;

import javax.swing.JPanel;

public class Graph extends JPanel {
	private JPanel panel;
	private int wysokosc;
	private int odstep = 20;
	private int dlugoscY = 0;
	private double skala = 1.0;
	private int odstepGora = 0;
	private int odstepDol = 20;
	private int min = 0;
	private int minA;

	
	

	public Graph(JPanel panel) {
		this.panel = panel;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(createBackground(), 0, 0, this);
	}

	public BufferedImage createBackground() {
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.createGraphics();

		this.setSize(panel.getSize());
		int valueOnLeft = 0;
		wysokosc = this.getSize().height - 20;

		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
	//	 System.out.println(dlugoscY); 
		g2.setColor(Color.gray);
	
 minA =panel.getSize().height - odstepDol;
	

		for (int a = panel.getSize().height - odstepDol; a > 9; a -= odstep) {
			
			g2.drawLine(40, a, this.getSize().width, a);
			
			
			////POCZATEK EDYCJI KEMAL DZIS KOMBINUJE NAD SKALA
			
			int wartosc = (int) (valueOnLeft *skala);
			wartosc += min;
//			if(wartosc< 0) wartosc= -wartosc;
						
			
			
			g2.drawString(String.valueOf(wartosc), 0, a
					+ (odstep / 3));
			valueOnLeft += odstep;
			odstepGora = a;
			
			minA=a;
		}
		
																				//rysowanie podzialki pionowej				
	/*	for(int i = 40;i<panel.getSize().width;i+=10){
			g2.drawLine(i,panel.getSize().height - odstepDol, i, minA);
			
			
		}*/

		valueOnLeft = 0;
		/*for (int a = dlugoscY + 10 + odstep; a < wysokosc; a += odstep) {
			valueOnLeft -= odstep;

			g2.drawLine(40, a, this.getSize().width, a);
			g2.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
			odstepDol = a;
		}*/

		/*
		 * g2.drawLine(40, odstepGora, 40, odstepDol); int j =
		 * this.getSize().width;; while(j>45){ g2.setStroke(new
		 * BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		 * g2.setColor(Color.gray); g2.drawLine(j, getOdstepGora(), j,
		 * getOdstepDol()); j -= 10; }
		 */

		return bi;
	}

	public int getMinA() {
		return minA;
	}

	public void scaleGraph(double skala, int min) {
		this.skala = skala;
		this.min = min;
		this.repaint();
		
	}

	public synchronized int getScaleHeight() {
		return this.getSize().height - odstepDol - odstepGora;
	}

	public int getOdstepGora() {
		return odstepGora;
	}

	public int getOdstepDol() {
		return odstepDol;
	}

}
