package com.wat.pz.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import com.wat.pz.main.Window;

public class Plot extends JPanel {

	private int wysokosc = 0;
	private JPanel p;
	private int dlugoscY;
	private Graph graph;

	public Plot(JPanel p, Graph graph) {
		this.p = p;
		this.setSize(p.getSize());
		this.graph = graph;
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
		// wysokosc /= 10;
		// wysokosc *= 10;

		g.setColor(Color.yellow);
		int i = 45;
		i = this.getSize().width;

		int dlugosc = Window.customCollection.size() - 1;
		int j = 0;
		int v = 0;
		if (dlugosc > 1) {
			j = obliczPunkt(Window.customCollection.get(dlugosc).intValue());
		}
		g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));

		while (i > 45) {

			if (dlugosc > 1) {
				Window.customCollection.getScaleNumber(p.getSize().width
						/ odstep);
				v = obliczPunkt(Window.customCollection.get(dlugosc - 1)
						.intValue());
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
		if (punkt > 0) {
			return (dlugoscY - punkt + 10);
		}

		return (dlugoscY + punkt + 10);
	}

}
