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
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private int odstepMilimeterX = 4;
	private int odstepMilimeterY = 4;
	private int odstepX = 10;
	private int odstepDol = 20;
	private double skala = 1.0;
	private int odstepGora = 0;
	private int odstepOdPoczatkuWykresu = 0;
	private int odstepPunkt = 0;
	private Graphics graphic = null;
	private PlotResults plot = null;
	private double przeskalujWykresX = 1.0;
	private double przeskalujWykresY = 1.0;
	private SelectionSquare square = new SelectionSquare();
	private boolean wasDragged = false;
	private boolean pressed = false;
	private int xPressed = 0;
	private int xReleased = 0;
	private JFrame frame;
	private AWTEventListener awtListener;
	private Results noweOkno = null;
	private volatile int first = 0;
	private volatile int last = 0;
	private Kuleczka kuleczka = null;
	private boolean milimeterMode = true;

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
				/*
				 * if (o.getXOnScreen() < (frame.getSize().width - 22) &&
				 * o.getYOnScreen() < (frame.getSize().height - 20))
				 */
				square.setEndX(o.getX());
				square.setEndY(o.getY());
				repaint();

				pressed = false;

				// copyToClipboard();

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
				wasDragged = false;
				xPressed = (int) ((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu));
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

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				wasDragged = true;

				/*
				 * if (pressed && e.getXOnScreen() < (frame.getSize().width -
				 * 22) && e.getYOnScreen() < (frame.getSize().height - 20))
				 */

				square.setEndX(e.getX());
				square.setEndY(e.getY());

				xReleased = (int) ((e.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu));

				// System.out.println(e.getX()+"|"+e.getY());

				System.out.println("Przed : " + xPressed + "|" + xReleased);
				/*
				 * if (xPressed > xReleased) { int tmp = xPressed; xPressed =
				 * xReleased; xReleased = tmp;
				 * 
				 * }
				 */

				System.out.println("Po : " + xPressed + "|" + xReleased);

				if (xPressed < xReleased) {

					first = 0;
					while ((data.points.get(first) != null)
							&& (first < data.points.size())
							&& (xPressed > data.points.get(first).getX())) {
						first++;

					}
					last = first;
					while ((data.points.get(last) != null)
							&& (last < data.points.size())
							&& (xReleased > data.points.get(last).getX())) {
						last++;

					}

				} else {

					first = 0;
					while ((data.points.get(first) != null)
							&& (first < data.points.size())
							&& (xReleased > data.points.get(first).getX())) {
						first++;
					}
					last = first;
					while ((data.points.get(last) != null)
							&& (last < data.points.size())
							&& (xPressed > data.points.get(last).getX())) {
						last++;

					}
				}

				System.out.println(first + " asdf " + last);

				setToolTipText("<html>Zaznaczy³eœ" + "<br>" + (last - first)
						+ " punktów " + "</html>");

				repaint();

			}

			@Override
			public void mouseMoved(MouseEvent o) {
				// System.out.println("dupa2");
				int wspX = (int) (((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu)) / (odstepPunkt));

				if (wspX >= 0 && wspX < data.points.size()) {
					Point p = data.points.get(wspX);

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

					System.out.println(p1.getX() + "|" + p2.getX());
					int tmpX = (int) ((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu));
					int ilepowinnobyc2 = ilePowinno(tmpX, p1, p2);
					if (kuleczka == null) {
						kuleczka = new Kuleczka(o.getX(),
								obliczPunkt(ilePowinno(tmpX, p1, p2)));
					} else if (((o.getX() / przeskalujWykresX)
							- (odstepOdPoczatkuWykresu) >= 10)) {
						// 10 to zmienna kosmiczna oznaczajaca wart x
						// pierwszego punktu pomiarowego :)

						kuleczka.setX((int) (o.getX()));
						kuleczka.setY((int) (obliczPunkt(ilePowinno(tmpX, p1,
								p2)) * przeskalujWykresY));

						setToolTipText("<html>Wspolrzedna X: "
								+ (int) ((o.getX() / przeskalujWykresX) - (odstepOdPoczatkuWykresu))
								+ "<br>Wspolrzedna Y: " + ilepowinnobyc2
								+ "<br>Pomiar urzadzenie nr: " + p1.getIndeks()
								+ "</html>");

						repaint();

					}
					// System.out.println("P1  " + p1.getX() + "|" + p1.getY()
					// + "P2  " + p2.getX() + "|" + p2.getY());
					// System.out.println("obliczpunkt " +
					// obliczPunkt(p1.getY())
					// + "|" + obliczPunkt(p2.getY()));

					/*
					 * if ((o.getY() / przeskalujWykresY) >= plot.obliczPunkt(p1
					 * .getY()) && (o.getY() / przeskalujWykresY) <= plot
					 * .obliczPunkt(p2.getY()))
					 * 
					 * { int oX = (int) ((o.getX() / przeskalujWykresX) -
					 * (odstepOdPoczatkuWykresu));
					 * 
					 * int ilepowinnobyc = ilePowinno(oX, p1, p2);
					 * 
					 * 
					 * System.out.println(ilepowinnobyc + "|" +
					 * plot.obliczPunkt2((int) (o.getY() / przeskalujWykresY)));
					 * 
					 * //
					 * if(plot.obliczPunkt2((int)(o.getY()/przeskalujWykresY))
					 * >=ilepowinnobyc-3 // && //
					 * plot.obliczPunkt2((int)(o.getY(
					 * )/przeskalujWykresY))<=ilepowinnobyc+3) if
					 * (plot.obliczPunktOdwotny((int) (o.getY() /
					 * przeskalujWykresY)) >= ilepowinnobyc - (0.1 *
					 * ilepowinnobyc) && plot.obliczPunktOdwotny((int) (o.getY()
					 * / przeskalujWykresY)) <= ilepowinnobyc + (0.1 *
					 * ilepowinnobyc)) // if(true) {
					 * 
					 * // System.out.print("jest git");
					 * setToolTipText("<html>Wspolrzedna X: " + (int) ((o.getX()
					 * / przeskalujWykresX) - (odstepOdPoczatkuWykresu)) +
					 * "<br>Wspolrzedna Y: " + ilepowinnobyc +
					 * "<br>Pomiar urzadzenie nr: " + p1.getIndeks() +
					 * "</html>"); } }
					 * 
					 * 
					 * 
					 * else if ((o.getY() / przeskalujWykresY) + 3 >= plot
					 * .obliczPunkt(p.getY()) && (o.getY() / przeskalujWykresY)
					 * - 3 <= plot .obliczPunkt(p.getY())) {
					 * 
					 * // plot.setToolTipText("punkt");
					 * 
					 * // System.out.println("kursor" + o.getY() + //
					 * "\nznalezione" // + plot.obliczPunkt(p.getY()));
					 * 
					 * plot.setToolTipText("<html>Wspolrzedna X: " + p.getX() +
					 * "<br>Wspolrzedna Y: " + p.getY() +
					 * "<br>Pomiar urzadzenie nr: " + p.getIndeks() +
					 * "</html>");
					 * 
					 * } else if (!wasDragged) { setToolTipText(null); }
					 */

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

		if (!isMilimeterMode()) {
			paintNormally(gg);
		} else {
			paintMilimeter(gg);
		}

	}

	private void paintMilimeter(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

		float alpha = 0.35f;
		Color color = new Color(0, 0, 1, alpha);
		g.setPaint(color);
		square.paintSquare(g);
		if (kuleczka != null)
			kuleczka.paintKuleczka(g);

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
		int mojOdstepGora = (wysokoscOkna) / odstep;
		odstepGora = ((wysokoscOkna) - (mojOdstepGora * odstep));
		if (odstepGora <= 9)
			odstepGora += odstep;
		pomoc = skala;
		skala = skala / (((wysokoscOkna)) - odstepDol - odstepGora);

		int valueOnLeft = 0;

		g.setColor(Color.gray);
		int minA = dlugoscY + 10 - odstep; // dodal BOlec do rysowania podzialki
											// pionowej

		for (int a = panel.getSize().height - 30 - odstepDol; a > 9; a -= odstep) {

			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
			valueOnLeft += odstep;
			odstepGora = a;

			minA = a; // dodal BOlec do rysowania podzialki pionowej

		}
		for (int a = panel.getSize().height - 30 - odstepDol; a >12; a -= odstepMilimeterX) {
			g.drawLine(40, a, this.getSize().width, a);
		}

		int maxA = panel.getSize().height - 30 - odstepDol;
		valueOnLeft = 0;

		int bolectmp = (int) Math.ceil(wysokosc * skala);

		int roznicaWysY = 0;

		// EDITED By BOLEC -- wyswietlanie podzialki pionowej

		FontRenderContext frc = g.getFontRenderContext();
		int fontWidth = (int) g.getFont().getMaxCharBounds(frc).getWidth();

		for (int b = 40, i = 0; b < szerokosc; b += odstepX, i++) {

			roznicaWysY = 20;
			if (i % 4 == 0) {

				if (i < this.data.points.size()) {
					String str = String.valueOf(this.data.points.get(i).getX());
					g.drawString(
							str,
							b
									- ((fontWidth / str.length()) * (str
											.length() / 2)), maxA + roznicaWysY);
				}
			}

		}
		for (int b = 40; b <= szerokosc; b += odstepMilimeterY) {
			g.drawLine(b, minA, b, maxA);
		}

		skala = (wysokoscOkna - odstepDol - odstepGora) / pomoc;

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

	private void paintNormally(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

		float alpha = 0.35f;
		Color color = new Color(0, 0, 1, alpha);
		g.setPaint(color);
		square.paintSquare(g);
		if (kuleczka != null)
			kuleczka.paintKuleczka(g);

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
		int mojOdstepGora = (wysokoscOkna) / odstep;
		odstepGora = ((wysokoscOkna) - (mojOdstepGora * odstep));
		if (odstepGora <= 9)
			odstepGora += odstep;
		pomoc = skala;
		skala = skala / (((wysokoscOkna)) - odstepDol - odstepGora);

		int valueOnLeft = 0;

		g.setColor(Color.gray);
		int minA = dlugoscY + 10 - odstep; // dodal BOlec do rysowania podzialki
											// pionowej

		/*
		 * for (int a = dlugoscY + 10 - odstep; a > 9; a -= odstep) {
		 * 
		 * valueOnLeft += odstep; g.drawLine(40, a, this.getSize().width, a);
		 * g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a +
		 * (odstep / 3));
		 * 
		 * minA = a; // dodal BOlec do rysowania podzialki pionowej
		 * 
		 * }
		 */

		for (int a = panel.getSize().height - 30 - odstepDol; a > 9; a -= odstep) {

			/*
			 * valueOnLeft += odstep; g.drawLine(40, a, this.getSize().width,
			 * a); g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0,
			 * a + (odstep / 3));
			 */
			g.drawLine(40, a, this.getSize().width, a);

			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
			valueOnLeft += odstep;
			odstepGora = a;

			minA = a; // dodal BOlec do rysowania podzialki pionowej

		}

		int maxA = panel.getSize().height - 30 - odstepDol;
		valueOnLeft = 0;
		/*
		 * for (int a = dlugoscY + 10 + odstep; a < wysokosc; a += odstep) {
		 * 
		 * valueOnLeft -= odstep;
		 * 
		 * g.drawLine(40, a, this.getSize().width, a);
		 * g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a +
		 * (odstep / 3));
		 * 
		 * maxA = a; // dodal BOlec do rysowania podzialki pionowej }
		 */

		int bolectmp = (int) Math.ceil(wysokosc * skala);

		int roznicaWysY = 0;

		// EDITED By BOLEC -- wyswietlanie podzialki pionowej

		FontRenderContext frc = g.getFontRenderContext();
		int fontWidth = (int) g.getFont().getMaxCharBounds(frc).getWidth();

		for (int b = 40, i = 0; b < szerokosc; b += odstepX, i++) {
			g.drawLine(b, minA, b, maxA);
			roznicaWysY = 20;
			if (i % 4 == 0) {

				if (i < this.data.points.size()) {
					String str = String.valueOf(this.data.points.get(i).getX());
					g.drawString(
							str,
							b
									- ((fontWidth / str.length()) * (str
											.length() / 2)), maxA + roznicaWysY);
				}
			}

		}

		// end edit by bolec

		skala = (wysokoscOkna - odstepDol - odstepGora) / pomoc;

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
			return (int) ((int) (panel.getSize().height - 30 - odstepDol) - (skala * punkt));
		}

		return (int) ((int) (panel.getSize().height - 30 - odstepDol) + (skala * punkt));
	}

	public int obliczPunktOdwotny(int punkt) {
		return (int) ((panel.getSize().height - 30 - odstepDol - punkt) / skala);
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

		System.out.println("punktyX " + p1.getX() + "|" + p2.getX());

		System.out.println("punktyY " + p1.getY() + "|" + p2.getY());
		double a;
		int ile = 0;
		if ((p1.getX()) - (p2.getX()) == 0) {
			a = 0;
		} else {
			a = ((double) (p1.getY() - p2.getY()) / ((double) (p1.getX()) - (p2
					.getX())));
		}
		double b = p1.getY() - ((p1.getX() * a));
		System.out.println("b= " + b + "a= " + a + "x= " + x);
		ile = (int) Math.round(((a * x) + b));
		System.out.println((a * x) + b);
		System.out.println("ile= " + ile);
		return ile;

	}

	public void zaznaczenie() {

		if (last - first < 2)
			return;

		System.out.println("dsaasdasdsdfafsd" + first + "|" + last);

		List<com.wat.pz.results.Point> tmpPoints = data.points.subList(first,
				last);

		Date dateNow = new Date();

		SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(
				"yyyy-MM-dd  HH-mm-ss");

		StringBuilder nowDate = new StringBuilder(
				dateformatYYYYMMDD.format(dateNow));

		FileWriter fw;
		try {
			fw = new FileWriter(nowDate.toString() + ".txt");
			fw.write("\n");
			for (com.wat.pz.results.Point i : tmpPoints) {

				fw.write(String.valueOf(i.getX()
						- data.points.get(first).getX() + 10));
				fw.write("\n");
				fw.write(String.valueOf(i.getY()));
				fw.write("\n");
				fw.write(String.valueOf(i.getIndeks()));
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		noweOkno = new Results(nowDate.toString() + ".txt");
		noweOkno.setVisible(true);
		wasDragged = false;

	}

	public boolean isMilimeterMode() {
		return milimeterMode;
	}

	public void setMilimeterMode(boolean milimeterMode) {
		this.milimeterMode = milimeterMode;
		repaint();
	}

}
