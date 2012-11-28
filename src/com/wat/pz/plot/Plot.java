package com.wat.pz.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import com.wat.pz.main.Main;

public class Plot extends JPanel {

	private int wysokosc = 0;
	private JPanel p;
	private int dlugoscY;

	public Plot(JPanel p) {
		this.p = p;
		this.setSize(p.getSize());

	}

	Random r = new Random();


	@Override
	protected void paintComponent(Graphics gg) {

		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
		
		
		this.setSize(p.getSize());

		int odstep = 20;
		wysokosc = this.getSize().height - 20;
		//wysokosc /= 10;
		//wysokosc *= 10;

		g.setColor(Color.yellow);
		int i = 45;
		i = this.getSize().width;

		int dlugosc = Main.customCollection.size() - 1;
		int j = 0;
		int v = 0;
		if (dlugosc > 1) {
			j = obliczPunkt(Integer.parseInt(Main.customCollection.get(dlugosc)));
		}
		g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		while (i > 45) {

			if (dlugosc > 1) {
				v = obliczPunkt(Integer.parseInt(Main.customCollection.get(dlugosc - 1)));
				g.setColor(Color.yellow);
				g.drawLine(i - odstep, v, i, j);
				dlugosc -= 1;
				j = v;

			}
			g.setColor(Color.gray);
			g.drawLine(i, 10, i, wysokosc);
			i -= odstep;

		}

	}

	public int obliczPunkt(int punkt) {
		if(punkt > 0){
			return (dlugoscY - punkt + 10);
		}
		
		return (dlugoscY + punkt + 10);
	}

}
