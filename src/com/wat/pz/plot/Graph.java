package com.wat.pz.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Graph extends JPanel {
	private JPanel panel;
	private int wysokosc;
	private int odstep = 20;
	private int dlugoscY = 0;
	private double skala = 1.0;
	private int odstepGora = 0;
	private int odstepDol = 0;


	public Graph(JPanel panel) {
		this.panel = panel;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		this.setSize(panel.getSize());
		int valueOnLeft = 0;
		wysokosc = this.getSize().height - 20;

		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
		/*System.out.println(dlugoscY);*/
		g.setColor(Color.gray);
		/*g.drawLine(40, 10, 40, wysokosc);*/
		g.drawString(String.valueOf((int)(valueOnLeft)), 0, dlugoscY + 10
				+ (odstep / 3));
		g.drawLine(40, dlugoscY + 10, this.getSize().width, dlugoscY + 10);

		
		for (int a = dlugoscY + 10 - odstep; a > 9; a -= odstep) {
			valueOnLeft += odstep;
			g2.drawLine(40, a, this.getSize().width, a);
			g.drawString(String.valueOf((int)(valueOnLeft*skala)), 0, a + (odstep / 3));
			odstepGora = a;
		}
		
		valueOnLeft = 0;
		for (int a = dlugoscY + 10 + odstep; a < wysokosc; a += odstep) {
			valueOnLeft -= odstep;

			g2.drawLine(40, a, this.getSize().width, a);
			g.drawString(String.valueOf((int)(valueOnLeft*skala)), 0, a + (odstep / 3));
			odstepDol = a;
		}
		
		
		
		
		
		
		g.drawLine(40, odstepGora, 40, odstepDol);
		
		
		//valueOnLeft = 0;
		
	}
	
	
	public void scaleGraph(double skala){
		this.skala = skala;
		this.repaint();
	}
	
	public int getScaleHeight(){
		return (dlugoscY + 10) - odstepGora;
	}
	
	public int getOdstepGora(){
		return odstepGora;
	}
	public int getOdstepDol(){
		return odstepDol;
	}

}
