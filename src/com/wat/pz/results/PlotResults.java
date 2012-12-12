package com.wat.pz.results;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class PlotResults extends JPanel {

	private Data data = null;
	private ArrayList<Point> uporzadkowanePunkty = null;
	private JPanel panel = null;
	private int wysokosc = 0;
	private int szerokosc = 0;
	private int dlugoscY = 0;
	private int dlugoscX = 0; // dodal Bolec do rysowania podzialki pionowej
	private int odstep = 20;
	private int odstepX = 10;
	private int odstepDol = 0;
	private double skala = 1.0;
	private int odstepGora = 0;
	private int odstepOdPoczatkuWykresu = 0;
	private int odstepPunkt = 0;
	private Graphics graphic = null;
	private PlotResults plot = null;
	private double przeskalujWykresX = 1.0;
	private double przeskalujWykresY = 1.0;
	private SelectionSquare square = new SelectionSquare();
	private boolean pressed = false;
	private JFrame frame;

	public PlotResults(Data dataObject, JPanel panel, JFrame f) {
		frame = f;
		this.data = dataObject;
		this.panel = panel;
		plot = this;
		ToolTipManager.sharedInstance().setInitialDelay(0);

		// ToolTipManager.sharedInstance().setDismissDelay(500);
		// this.setToolTipText("lalalal");
		// graphic = this.getGraphics();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent o) {
				if (o.getXOnScreen() < (frame.getSize().width - 22)
						&& o.getYOnScreen() < (frame.getSize().height - 20)) {
					square.setEndX(o.getX());
					square.setEndY(o.getY());
					repaint();
					pressed = false;
					// copyToClipboard();
				}
			}

			@Override
			public void mousePressed(MouseEvent o) {
				// System.out.println(o.getLocationOnScreen().x);
				square.setEndX(null);
				square.setEndY(null);
				pressed = true;
				square.setStartX(o.getX());
				square.setStartY(o.getY());
				square.setStartXOnScreen(o.getXOnScreen());
				square.setStartYOnScreen(o.getYOnScreen());
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if (pressed && e.getXOnScreen() < (frame.getSize().width - 22)
						&& e.getYOnScreen() < (frame.getSize().height - 20)) {
					square.setEndX(e.getX());

					square.setEndY(e.getY());
					repaint();

				}

			}

			@Override
			public void mouseMoved(MouseEvent o) {
				// System.out.println(o.getX() + "  " + o.getY());
				int wspX = (int) (((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu)) / (odstepPunkt));
				// System.out.println(wspX);

				// System.out.println(wspX);
				if (wspX >= 0 && wspX < data.points.size()) {
					Point p = data.points.get(wspX);
					// System.out.println("x= " + p.getX() + " y= " + p.getY());
					// paintInfoDialog(graphic);

					Point p2;
					Point p1 = data.points.get(wspX);
					if (wspX == 0) {
						p2 = data.points.get(wspX);
					} else {
						p2 = data.points.get(wspX - 1);
					}

					Point pTmp = null;

					if (p1.getY() < p2.getY()) {
						pTmp = p1;
						p1 = p2;
						p2 = pTmp;
					}

					// System.out.println("P1  " + p1.getX() + "|" + p1.getY()
					// + "P2  " + p2.getX() + "|" + p2.getY());
					// System.out.println("obliczpunkt " +
					// obliczPunkt(p1.getY())
					// + "|" + obliczPunkt(p2.getY()));

					if ((o.getY() / przeskalujWykresY) >= plot.obliczPunkt(p1
							.getY())
							&& (o.getY() / przeskalujWykresY) <= plot
									.obliczPunkt(p2.getY()))

					{
						int oX = (int) ((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu));

						int ilepowinnobyc = ilePowinno(oX, p1, p2);

						/*
						 * System.out.println(ilepowinnobyc + "|" +
						 * plot.obliczPunkt2((int) (o.getY() /
						 * przeskalujWykresY)));
						 */
						// if(plot.obliczPunkt2((int)(o.getY()/przeskalujWykresY))>=ilepowinnobyc-3
						// &&
						// plot.obliczPunkt2((int)(o.getY()/przeskalujWykresY))<=ilepowinnobyc+3)
						if (plot.obliczPunkt2((int) (o.getY() / przeskalujWykresY)) >= ilepowinnobyc
								- (0.1 * ilepowinnobyc)
								&& plot.obliczPunkt2((int) (o.getY() / przeskalujWykresY)) <= ilepowinnobyc
										+ (0.1 * ilepowinnobyc))
						// if(true)
						{

							// System.out.print("jest git");
							plot.setToolTipText("<html>Wspolrzedna X: "
									+ (int) ((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu))
									+ "<br>Wspolrzedna Y: " + ilepowinnobyc
									+ "<br>Pomiar urzadzenie nr: "
									+ p1.getIndeks() + "</html>");
						}
					}

					else if ((o.getY() / przeskalujWykresY) + 3 >= plot
							.obliczPunkt(p.getY())
							&& (o.getY() / przeskalujWykresY) - 3 <= plot
									.obliczPunkt(p.getY())) {

						// plot.setToolTipText("punkt");

						// System.out.println("kursor" + o.getY() +
						// "\nznalezione"
						// + plot.obliczPunkt(p.getY()));

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

	public void copyImage(int parametr) {

		// System.out.println(square.getStartX() + " " +
		// getSize().width);

		if (square.getWidth() > 0 && square.getHeight() > 0) {
			int w = square.getSize().width;
			int h = square.getSize().height;
			square.setWidth(-1);
			plot.paintImmediately(square.getStartX(), square.getStartY(), w, h);

			Rectangle screenRect = new Rectangle();
			// System.out.println(odstepGora + "  " + square.getStartYOnScreen()
			// + "   " + (frame.getBounds().y + square.getStartYOnScreen()));
			// plot.repaint();

			screenRect.setBounds(square.getStartXOnScreen(),
					square.getStartYOnScreen(), w, h);

			BufferedImage capture = null;
			try {

				capture = new Robot().createScreenCapture(screenRect);

			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (parametr == 1) {
				Clipboard clipboard = Toolkit.getDefaultToolkit()
						.getSystemClipboard();
				ImageClipboard imageSel = new ImageClipboard(capture);
				clipboard.setContents(imageSel, null);
				clipboard = null;
				imageSel = null;
			} else if (parametr == 2) {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					chooser.hide();
					try {
						ImageIO.write(
								capture,
								"png",
								new File((chooser.getSelectedFile().toString()
										.endsWith(".png") ? chooser
										.getSelectedFile().toString() : chooser
										.getSelectedFile().toString() + ".png")));
						capture = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			System.gc();

		}
	}

	@Override
	protected void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		// Graphics2D g2 = (Graphics2D) gg;

		float alpha = 0.35f;

		// setSize(frame.getSize());
		Color color = new Color(0, 0, 1, alpha);
		g.setPaint(color);
		square.paintSquare(g);

		g.scale(przeskalujWykresX, przeskalujWykresY);
		double pomoc = 0;
		skala = data.getMaxY();
		int wysokoscOkna = panel.getSize().height - 30;
		int szerokoscOkna = this.getSize().width; // dodal BOlec do rysowania
													// podzialki pionowej
		setPreferredSize(new Dimension(
				(int) (przeskalujWykresX * (data.getMaxX() + 30)),
				(int) (przeskalujWykresY * (panel.getSize().height - 30))));

		wysokosc = wysokoscOkna - 20;
		szerokosc = szerokoscOkna; // dodal Bolec

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

		int minA = dlugoscY + 10 - odstep; // dodal BOlec do rysowania podzialki
											// pionowej

		for (int a = dlugoscY + 10 - odstep; a > 9; a -= odstep) {

			valueOnLeft += odstep;
			g.drawLine(40, a, this.getSize().width, a);
			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));

			minA = a; // dodal BOlec do rysowania podzialki pionowej

		}

		int maxA = dlugoscY + 10 + odstep;
		// System.out.println("odstep gora:  " + odstepGora);
		valueOnLeft = 0;
		for (int a = dlugoscY + 10 + odstep; a < wysokosc; a += odstep) {

			valueOnLeft -= odstep;

			g.drawLine(40, a, this.getSize().width, a);
			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));

			maxA = a; // dodal BOlec do rysowania podzialki pionowej
		}

		int bolectmp = (int) Math.ceil(wysokosc * skala);

		int roznicaWysY = 0;

		// EDITED By BOLEC -- wyswietlanie podzialki pionowej

		FontRenderContext frc = g.getFontRenderContext();
		int fontWidth = (int) g.getFont().getMaxCharBounds(frc).getWidth();

		for (int b = 40, i = 0; b < szerokosc; b += odstepX, i++) {
			g.drawLine(b, minA, b, maxA);
			roznicaWysY = 20;
			if (i % 4 == 0) {

				if (i > this.data.points.size())
					i = 0;

				String str = String.valueOf(this.data.points.get(i).getX());
				g.drawString(str,
						b - ((fontWidth / str.length()) * (str.length() / 2)),
						maxA + roznicaWysY);

			}

		}

		// end edit by bolec

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

	public int obliczPunkt2(int punkt) {
		return (int) ((dlugoscY + odstepGora - punkt) / skala);
	}

	public void paintInfoDialog(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(30, 30, 30, 30);
	}

	public void setPrzeskalujWykresX(int x) {
		przeskalujWykresX = (double) x / 10.0;
		repaint();
	}

	public void setPrzeskalujWykresY(int y) {
		przeskalujWykresY = (double) y / 10.0;
		repaint();
	}

	public int ilePowinno(int x, Point p1, Point p2) {
		double a = 0;
		int ile = 0;
		if ((p1.getX()) - (p2.getX()) == 0) {
			a = 0;
		} else {
			a = ((p1.getY() - p2.getY()) / ((p1.getX()) - (p2.getX())));
		}
		double b = p1.getY() - (p1.getX() * a);

		ile = (int) Math.round(((a * x) + b));
		// System.out.println(p1.getX()+"|"+p1.getY()+"|||"+p2.getX()+"|"+p2.getY());
		// System.out.println(a+"|"+x+"|"+ile);
		return ile;
	}
}
