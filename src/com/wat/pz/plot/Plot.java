package com.wat.pz.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

import com.wat.pz.wizualizacja.connection.Connect;

public class Plot extends JPanel {
	private Color kolor;
	private Connect connect;
	private int wysokosc = 0;
	private JPanel p;
	private int dlugoscY;
	private Graph graph;
	private double skala = 1.0;
	private int odstep = 10;

	public int getOdstep() {
		return odstep;
	}


	// private CustomCollection customCollection;
	public Plot(JPanel p, Graph graph, Color color) {
		this.kolor = color;
		this.p = p;
		this.setSize(p.getSize());
		this.graph = graph;
	}

	Random r = new Random();

	@Override
	protected synchronized void paintComponent(Graphics gg) {

		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;

		int dlugosc = connect.getCustomCollection().size() - 1;
		int j = 0;
		int v = 0;
		this.setSize(p.getSize());

		wysokosc = this.getSize().height - odstep;

		g.setColor(kolor);
		int i = 45;
		i = this.getSize().width;

		if (dlugosc > 1) {
			j = obliczPunkt(connect.getCustomCollection().get(dlugosc)
					.intValue());
		}

		while (i > 45) {
			g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
			g.setColor(Color.gray);
			g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
			if (dlugosc > 1 && connect.getCustomCollection() != null) {

				skala = connect.getCustomCollection().getScaleNumber(
						(p.getSize().width - 40) / odstep);
				dlugoscY = graph.getScaleHeight();

				graph.scaleGraph(skala / dlugoscY);
				skala = (double) dlugoscY / skala;
				v = obliczPunkt((connect.getCustomCollection().get(dlugosc)
						.intValue()));
				g.setColor(kolor);
				g.drawLine(i - odstep, v, i, j);
				dlugosc -= 1;
				j = v;

			}

			i -= odstep;

		}

	}

	public int obliczPunkt(int punkt) {
		if (punkt > 0) {
			return (int) (dlugoscY - (skala * punkt) + graph.getOdstepGora());
		}

		return (int) (dlugoscY - (skala * punkt) + graph.getOdstepGora());
	}

	public Connect getConnect() {
		return connect;
	}

	public void setConnect(Connect connect) {
		this.connect = connect;
	}

	public Color getKolor() {
		return kolor;
	}

	public void setKolor(Color kolor) {
		this.kolor = kolor;
	}

}
