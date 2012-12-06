package com.wat.pz.show.results;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class PlotResults extends JPanel {

	private Data data = null;
	private ArrayList<Point> uporzadkowanePunkty = null;
	private JPanel panel = null;
	private int wysokosc = 0;
	private int dlugoscY = 0;
	private int odstep = 20;
	private int odstepDol = 0;
	private double skala = 1.0;
	private int odstepGora = 0;
	private int odstepOdPoczatkuWykresu = 0;
	private int odstepPunkt = 0;
	private Graphics graphic = null;
	private PlotResults plot = null;
	private double przeskalujWykresX = 1.0;
	private double przeskalujWykresY = 1.0;

	public PlotResults(Data dataObject, JPanel panel) {
		this.data = dataObject;
		this.panel = panel;
		plot = this;
		ToolTipManager.sharedInstance().setInitialDelay(0);

		// ToolTipManager.sharedInstance().setDismissDelay(500);
		// this.setToolTipText("lalalal");
		// graphic = this.getGraphics();
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(MouseEvent o) {
				// System.out.println(o.getX() + "  " + o.getY());
				int wspX = (int) (((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu)) / (odstepPunkt));
				// System.out.println(wspX);
				if (wspX >= 0 && wspX < data.points.size()) {
					Point p = data.points.get(wspX);
					// System.out.println("x= " + p.getX() + " y= " + p.getY());
					// paintInfoDialog(graphic);
					if ((o.getY() / przeskalujWykresY) + 3 >= plot
							.obliczPunkt(p.getY())
							&& (o.getY() / przeskalujWykresY) - 3 <= plot
									.obliczPunkt(p.getY())) {
						// plot.setToolTipText("punkt");

						System.out.println("kursor" + o.getY() + "\nznalezione"
								+ plot.obliczPunkt(p.getY()));

						plot.setToolTipText("<html>Wspolrzedna X: " + p.getX()
								+ "<br>Wspolrzedna Y: " + p.getY()
								+ "<br>Pomiar urzadzenie nr: " + p.getIndeks()
								+ "</html>");

					} else {
						plot.setToolTipText(null);
					}
					p = null;
					System.gc();
				}

			}

		});

	}

	@Override
	protected void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		if (graphic == null)
			graphic = gg;
		Graphics2D g = (Graphics2D) gg;
		g.scale(przeskalujWykresX, przeskalujWykresY);
		double pomoc = 0;
		skala = data.getMaxY();
		int wysokoscOkna = panel.getSize().height - 50;
		setPreferredSize(new Dimension(
				(int) (przeskalujWykresY * (data.getMaxX() + 30)),
				(int) (przeskalujWykresY * (panel.getSize().height - 50))));

		// g.scale(2.0, 2.0);
		wysokosc = wysokoscOkna - 20;
		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
		int mojOdstepDol = (wysokosc - (dlugoscY + 10)) / odstep;
		odstepDol = ((mojOdstepDol * odstep) + dlugoscY + 10);
		int mojOdstepGora = (dlugoscY) / odstep;
		odstepGora = ((dlugoscY + 10) - (mojOdstepGora * odstep));
		pomoc = skala;
		skala = skala / ((dlugoscY + 10) - odstepGora);

		int valueOnLeft = 0;

		g.setColor(Color.gray);
		g.drawString(String.valueOf((int) (valueOnLeft)), 0, dlugoscY + 10
				+ (odstep / 3));
		g.drawLine(40, dlugoscY + 10, this.getSize().width, dlugoscY + 10);

		for (int a = dlugoscY + 10 - odstep; a > 9; a -= odstep) {
			valueOnLeft += odstep;
			g.drawLine(40, a, this.getSize().width, a);
			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));

		}
		// System.out.println("odstep gora:  " + odstepGora);
		valueOnLeft = 0;
		for (int a = dlugoscY + 10 + odstep; a < wysokosc; a += odstep) {
			valueOnLeft -= odstep;

			g.drawLine(40, a, this.getSize().width, a);
			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
		}

		dlugoscY = (dlugoscY + 10 - odstepGora);
		skala = (dlugoscY) / pomoc;

		g.setColor(Color.yellow);
		Point o1 = data.points.get(0);
		Point o2;
		odstepOdPoczatkuWykresu = 40 - o1.getX();
		odstepPunkt = o1.getX();
		for (int i = 1; i < data.points.size(); i++) {
			o2 = data.points.get(i);
			g.drawLine(o1.getX() + odstepOdPoczatkuWykresu,
					obliczPunkt(o1.getY()),
					o2.getX() + odstepOdPoczatkuWykresu, obliczPunkt(o2.getY()));
			o1 = o2;
		}

	}

	public int obliczPunkt(int punkt) {
		if (punkt > 0) {
			return (int) (dlugoscY - (punkt * skala) /* +odstep */+ odstepGora);
		}

		return (int) (dlugoscY - (punkt * skala) /* +odstep */+ odstepGora);
	}

	public void paintInfoDialog(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(30, 30, 30, 30);
	}

	public void setPrzeskalujWykresX(int x) {
		przeskalujWykresX = (double)x / 10.0;
		repaint();
	}

	public void setPrzeskalujWykresy(int y) {
		przeskalujWykresY = (double)y / 10.0;
		repaint();
	}
}
