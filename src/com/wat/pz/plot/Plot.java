package com.wat.pz.plot;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JPanel;

import com.wat.pz.main.PropertiesWidget;
import com.wat.pz.utils.ColorSwatch;
import com.wat.pz.wizualizacja.collection.Measurement;
import com.wat.pz.wizualizacja.connection.Connect;

public class Plot extends JPanel {
	private Connect connect;
	private int wysokosc = 0;
	private JPanel p;
	private int dlugoscY;
	private Graph graph;
	private double skala = 1.0;
	private int odstep = 10;
	private int iteratorPodzialkiPionowej = 0;
	private boolean simulated = false;


	public boolean isSimulated() {
		return simulated;
	}

	public void setSimulated(boolean simulated) {
		this.simulated = simulated;
	}

	private PropertiesWidget propertiesWidget;
	private BufferStrategy bs;

	public int getOdstep() {
		return odstep;
	}

	public Plot(JPanel p, Graph graph, BufferStrategy bs) {
		this.bs = bs;
		this.p = p;
		this.setSize(p.getSize());
		this.graph = graph;
		// System.out.println(this.isDoubleBuffered());
		this.setDoubleBuffered(true);
		this.setIgnoreRepaint(true);
	}

	@Override
	public void paintComponent(Graphics gg) {

		super.paintComponent(gg);
		//
		Graphics2D g = (Graphics2D) gg;
		Color kolor = ((ColorSwatch) propertiesWidget.getColorButton()
				.getIcon()).getColor();
		int dlugosc = connect.getCustomCollection().size() - 1;
		// tu
		int j = 0;
		int v = 0;
		Measurement current = null;
		Measurement previous = null;
		this.setSize(p.getSize());

		wysokosc = this.getSize().height - odstep;

		g.setColor(kolor);
		int i = 45;
		i = this.getSize().width;

		if (dlugosc >= 0) {
			// System.out.println(connect.getCustomCollection().getMin());
			j = obliczPunkt((int) connect.getCustomCollection().get(dlugosc)
					.getValue()
					- (connect.getCustomCollection().getMin().intValue() / 2));
			previous = connect.getCustomCollection().get(dlugosc);
		}

		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(kolor);
		// synchronized (this) {

		while (i > 45) {

			if (dlugosc >= 0 && connect.getCustomCollection() != null) {

				skala = connect.getCustomCollection().getScaleNumber(
						(p.getSize().width - 40) / odstep);
				dlugoscY = graph.getScaleHeight();

				graph.scaleGraph(skala / dlugoscY, connect
						.getCustomCollection().getMin().intValue() / 2);
				skala = (double) dlugoscY / skala;
				current = connect.getCustomCollection().get(dlugosc);
				v = obliczPunkt((int) current.getValue()
						- (connect.getCustomCollection().getMin().intValue() / 2));

				// g.drawLine(i,graph.getSize().height -
				// graph.getOdstepDol(),i,graph.getMinA());
				g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND));
				g.draw(new Line2D.Double(i - odstep, v, i, j));
				long time = connect.getCustomCollection().get(dlugosc)
						.getTime();

				if (current.getTime() % 1000 == 0) {

					rysujPodzialke(g, i, current);
					g.setColor(kolor);
				} else if (previous != null) {

					if (previous.getTime() % 1000 != 0) {
						if ((current.getTime() - previous.getTime()) < 1000) {

							int previousCutted = (int) (previous.getTime() % 1000);
							int currentCutted = (int) (current.getTime() % 1000);

							if (previousCutted < currentCutted) {
								rysujPodzialke(g, i, current);
								g.setColor(kolor); // trzeba
													// narysowac
													// w
													// odpowiednim
													// miejscu

//								System.out.println("ELSE: " + "F: "
//										+ current.getTime() + " S: "
//										+ previous.getTime());

							}
						} else {
							int podzilkaCount = (int) (previous.getTime() - current
									.getTime() / 1000);
							// tu moznadopisac funkcje funkcje;, ale mi sie nie
							// chce.
						}
					}

				}

				dlugosc -= 1;
				previous = current;
				j = v;

			}

			i -= odstep;

		}
		// }
		// gg.drawImage(drawImage(), 0, 0, this);
	}

	/*
	 * public BufferedImage drawImage() { BufferedImage bi = new
	 * BufferedImage(this.getWidth(), this.getHeight(),
	 * BufferedImage.TYPE_INT_ARGB); Graphics2D g = bi.createGraphics(); Color
	 * kolor = ((ColorSwatch) propertiesWidget.getColorButton()
	 * .getIcon()).getColor(); int dlugosc =
	 * connect.getCustomCollection().size() - 1; int j = 0; int v = 0;
	 * this.setSize(p.getSize());
	 * 
	 * wysokosc = this.getSize().height - odstep;
	 * 
	 * g.setColor(kolor); int i = 45; i = this.getSize().width;
	 * 
	 * if (dlugosc >= 0) { j = obliczPunkt((int)
	 * connect.getCustomCollection().get(dlugosc) .getValue()); }
	 * g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
	 * BasicStroke.JOIN_ROUND)); g.setColor(kolor); // - synchronized (this) {
	 * while (i > 45) {
	 * 
	 * if (dlugosc >= 0 && connect.getCustomCollection() != null) {
	 * 
	 * skala = connect.getCustomCollection().getScaleNumber( (p.getSize().width
	 * - 40) / odstep); dlugoscY = graph.getScaleHeight();
	 * 
	 * graph.scaleGraph(skala / dlugoscY, connect
	 * .getCustomCollection().getMin().intValue()); skala = (double) dlugoscY /
	 * skala; v = obliczPunkt((int) (connect.getCustomCollection()
	 * .get(dlugosc).getValue() - connect
	 * .getCustomCollection().getMin().intValue())); // g.drawLine (i - odstep,
	 * v, i, j); g.draw(new Line2D.Double(i - odstep, v, i, j));
	 * 
	 * dlugosc -= 1; j = v;
	 * 
	 * }
	 * 
	 * i -= odstep;
	 * 
	 * } // }
	 * 
	 * return bi; }
	 */
	@Override
	public void update(Graphics g) {
		super.update(g);
	};

	public int obliczPunkt(int punkt) {
		// / if (punkt > 0) {
		// System.out.println(punkt + "   >>>  " + (int) ((this.getSize().height
		// - graph.getOdstepDol()) - (skala * punkt)));
		return (int) (((p.getSize().height - graph.getOdstepDol()) - (skala * punkt) /*
																					 * +
																					 * graph
																					 * .
																					 * getOdstepGora
																					 * (
																					 * )
																					 */));
		// }

		// return (int) ((this.getSize().height - graph.getOdstepDol()) + (skala
		// * punkt) /*+ graph.getOdstepGora()*/);
	}

	public Connect getConnect() {
		return connect;
	}

	public void setConnect(Connect connect) {
		this.connect = connect;
	}

	public PropertiesWidget getPropertiesWidget() {
		return propertiesWidget;
	}

	public void setPropertiesWidget(PropertiesWidget propertiesWidget) {
		this.propertiesWidget = propertiesWidget;
	}

	public void rysujPodzialke(Graphics g, int i, Measurement m) {
		g.setColor(Color.gray);

		((Graphics2D) g).setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.drawLine(i, graph.getSize().height - graph.getOdstepDol(), i,
				graph.getMinA());

		// double d = (Math.round(((double) m.getTime() / 1000) * 100)) / 100;
	

		Date date = m.getDate();

		SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(
				"HH:mm:ss");

		StringBuilder time = new StringBuilder(
				dateformatYYYYMMDD.format(date));
		
	g.drawString(time.toString(), i-25,
				graph.getSize().height - graph.getOdstepDol() + 20);

	}

}
