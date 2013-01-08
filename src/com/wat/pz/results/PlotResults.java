package com.wat.pz.results;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

public class PlotResults extends JPanel {

	private Data data = null;

	private JPanel panel = null;
	private int wysokosc = 0;
	private int szerokosc = 0;
	private int dlugoscY = 0;
	private int odstep = 20;
	private int odstepMilimeterX = 4;
	private int odstepMilimeterY = 4;
	private int odstepX = 10;
	private int odstepDol = 20;
	private double skala = 1.0;
	private int odstepGora = 0;
	private int odstepOdPoczatkuWykresu = 0;
	private int odstepPunkt = 0;
	private double przeskalujWykresX = 1.0;
	private double przeskalujWykresY = 1.0;
	private SelectionSquare square = new SelectionSquare();
	private int xPressed = 0;
	private int xReleased = 0;
	private Results noweOkno = null;
	private volatile int first = 0;
	private volatile int last = 0;
	private Kuleczka kuleczka = null;
	private boolean milimeterMode = false;
	private boolean timeMode = true;
	private int minA;
	private int maxA;
	private int lastMouseMoved;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int pozycjaYKulki;

	public PlotResults(Data dataObject, JPanel panel, JFrame f) {
		this.data = dataObject;
		this.panel = panel;

		ToolTipManager.sharedInstance().setInitialDelay(0);


		this.addHierarchyBoundsListener(new HierarchyBoundsListener() {

			@Override
			public void ancestorResized(HierarchyEvent arg0) {
				obliczKulke(lastMouseMoved);
				startY = (int) (minA);
				endY = (int) (maxA );
				square.setStartY((int) (startY * przeskalujWykresY));
				square.setEndY((int) (endY * przeskalujWykresY));
			}

			@Override
			public void ancestorMoved(HierarchyEvent arg0) {

			}
		});
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent o) {
				endX = (int) (o.getX() / przeskalujWykresX);
				endY = (int) (maxA);
				if (!((endX * przeskalujWykresX) <= przeskalujWykresX
						* ((data.points.size() * odstepX) + 30))) {
					endX = (int) ((((data.points.size()) * odstepX) + 30) * przeskalujWykresX);
					endX /= przeskalujWykresX;
				}
				square.setEndX((int) (endX * przeskalujWykresX));
				square.setEndY((int) (endY * przeskalujWykresY));
				repaint();

			}

			@Override
			public void mousePressed(MouseEvent o) {
				square.setEndX(null);
				square.setEndY(null);
				startX = (int) (o.getX() / przeskalujWykresX);
				if (startX * przeskalujWykresX >= 40 * przeskalujWykresX) {

				} else {
					startX = (int) (40);
				}
				startY = (int) (minA/* / przeskalujWykresY */);
				square.setStartX((int) (startX * przeskalujWykresX));
				square.setStartY((int) (startY * przeskalujWykresY));
				square.setStartXOnScreen(o.getXOnScreen());
				square.setStartYOnScreen(o.getYOnScreen());
				xPressed = (int) ((startX / przeskalujWykresX) - (odstepOdPoczatkuWykresu));
				repaint();

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
//				wasDragged = true;
				endX = (int) (e.getX() / przeskalujWykresX);
				endY = (int) (maxA /* / przeskalujWykresY */);
				if (!((endX * przeskalujWykresX) <= przeskalujWykresX
						* ((data.points.size() * odstepX) + 30))) {
					endX = (int) ((((data.points.size()) * odstepX) + 30) * przeskalujWykresX);
					endX /= przeskalujWykresX;
				}
				square.setEndX((int) (endX * przeskalujWykresX));
				square.setEndY((int) (endY * przeskalujWykresY));
				xReleased = (int) ((endX / przeskalujWykresX) - (odstepOdPoczatkuWykresu));

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

				setToolTipText("<html>Zaznaczy³eœ" + "<br>" + (last - first)
						+ " punktów " + "</html>");

				repaint();

			}

			@Override
			public void mouseMoved(MouseEvent o) {

				// }
				if ((int) (o.getX() / przeskalujWykresX) >= 40) {
					lastMouseMoved = ((int) (o.getX() / przeskalujWykresX));

				} else {
					lastMouseMoved = 40;
				}
				obliczKulke((int) (lastMouseMoved * przeskalujWykresX));

			}

		});

	}

	public void copyImage(int parametr) {

		if (square.getWidth() > 0 && square.getHeight() > 0) {
			int w = square.getSize().width;
			int h = square.getSize().height;
			square.setWidth(-1);
			paintImmediately(square.getStartX(), square.getStartY(), w, h);

			Rectangle screenRect = new Rectangle();
			screenRect.setBounds(square.getStartXOnScreen(),
					square.getStartYOnScreen(), w, h);

			BufferedImage capture = null;
			try {

				capture = new Robot().createScreenCapture(screenRect);

			} catch (AWTException e1) {
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

		if (isMilimeterMode()) {
			paintMilimeter(gg);

		} else if (isTimeMode()) {

			paintTime(gg);
		} else {
			paintNormally(gg);

		}

	}

	private void paintMilimeter(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

	

		square.paintSquare(g);
		g.scale(przeskalujWykresX, przeskalujWykresY);

		double pomoc = 0;
		skala = data.getMaxY();
		int wysokoscOkna = panel.getSize().height - 30;
		int szerokoscOkna = this.getSize().width;
		setPreferredSize(new Dimension(
				(int) (przeskalujWykresX * (data.getMaxX() + 30)),
				(int) (przeskalujWykresY * (panel.getSize().height - 30))));

		wysokosc = wysokoscOkna - 20;
		szerokosc = szerokoscOkna; 

		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
		int mojOdstepGora = (wysokoscOkna) / odstep;
		odstepGora = ((wysokoscOkna) - (mojOdstepGora * odstep));
		if (odstepGora <= 9)
			odstepGora += odstep;
		pomoc = skala;
		skala = skala / (((wysokoscOkna)) - odstepDol - odstepGora);

		int valueOnLeft = 0;

		g.setColor(Color.gray);
		minA = dlugoscY + 10 - odstep; 
		for (int a = panel.getSize().height - 30 - odstepDol; a > 9; a -= odstep) {

			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
			valueOnLeft += odstep;
			odstepGora = a;

			minA = a; 

		}
		int tmpbolek =0;
		for (int a = panel.getSize().height - 30 - odstepDol; a >= minA; a -= odstepMilimeterX) {
			
			if(tmpbolek==10) {
				tmpbolek=0;
				
				
				g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND));
			}
			
			g.drawLine(40, a, this.getSize().width, a);
			
			g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
			tmpbolek++;
			
			
		}

		maxA = panel.getSize().height - 30 - odstepDol;
		valueOnLeft = 0;

		

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
		int tmpbolek2 =0;
		for (int b = 40; b <= szerokosc; b += odstepMilimeterY) {
			
			if(tmpbolek2==10) {
				tmpbolek2=0;
				
				
				g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
						BasicStroke.JOIN_ROUND));
			}
			
			g.drawLine(b, minA, b, maxA);
			
			g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
			tmpbolek2++;
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

		if (kuleczka != null) {
			

			g.scale(1.0 / przeskalujWykresX, 1.0 / przeskalujWykresY);
			kuleczka.paintKuleczka(g);
		}

	}

	private void paintNormally(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

		square.paintSquare(g);

		g.scale(przeskalujWykresX, przeskalujWykresY);

		double pomoc = 0;
		skala = data.getMaxY();
		int wysokoscOkna = panel.getSize().height - 30;
		int szerokoscOkna = this.getSize().width; 
		setPreferredSize(new Dimension(
				(int) (przeskalujWykresX * (data.getMaxX() + 30)),
				(int) (przeskalujWykresY * (panel.getSize().height - 30))));

		wysokosc = wysokoscOkna - 20;
		szerokosc = szerokoscOkna; 

		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
		int mojOdstepGora = (wysokoscOkna) / odstep;
		odstepGora = ((wysokoscOkna) - (mojOdstepGora * odstep));
		if (odstepGora <= 9)
			odstepGora += odstep;
		pomoc = skala;
		skala = skala / (((wysokoscOkna)) - odstepDol - odstepGora);

		int valueOnLeft = 0;

		g.setColor(Color.gray);
		minA = dlugoscY + 10 - odstep; 
										
		for (int a = panel.getSize().height - 30 - odstepDol; a > 9; a -= odstep) {

			g.drawLine(40, a, this.getSize().width, a);

			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
			valueOnLeft += odstep;
			odstepGora = a;

			minA = a;

		}

		maxA = panel.getSize().height - 30 - odstepDol;
		valueOnLeft = 0;

		
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

		if (kuleczka != null) {
		
			g.scale(1.0 / przeskalujWykresX, 1.0 / przeskalujWykresY);
			kuleczka.paintKuleczka(g);
		}

	}

	private void paintTime(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;

		
		square.paintSquare(g);

		g.scale(przeskalujWykresX, przeskalujWykresY);

		double pomoc = 0;
		skala = data.getMaxY();
		int wysokoscOkna = panel.getSize().height - 30;
		int szerokoscOkna = this.getSize().width; 
		setPreferredSize(new Dimension(
				(int) (przeskalujWykresX * (data.getMaxX() + 30)),
				(int) (przeskalujWykresY * (panel.getSize().height - 30))));

		wysokosc = wysokoscOkna - 20;
		szerokosc = szerokoscOkna;

		dlugoscY = (int) Math.ceil((wysokosc - 10) / 2);
		int mojOdstepGora = (wysokoscOkna) / odstep;
		odstepGora = ((wysokoscOkna) - (mojOdstepGora * odstep));
		if (odstepGora <= 9)
			odstepGora += odstep;
		pomoc = skala;
		skala = skala / (((wysokoscOkna)) - odstepDol - odstepGora);

		int valueOnLeft = 0;

		g.setColor(Color.gray);
		minA = dlugoscY + 10 - odstep; 

		for (int a = dlugoscY + 10 - odstep; a > 9; a -= odstep) {

			minA = a; 

		}

		for (int a = panel.getSize().height - 30 - odstepDol; a > 9; a -= odstep) {

			g.drawLine(40, a, this.getSize().width, a);

			g.drawString(String.valueOf((int) (valueOnLeft * skala)), 0, a
					+ (odstep / 3));
			valueOnLeft += odstep;
			odstepGora = a;

			minA = a;

		}

		maxA = panel.getSize().height - 30 - odstepDol;
		valueOnLeft = 0;

	

		// EDITED By BOLEC -- wyswietlanie podzialki pionowej

		FontRenderContext frc = g.getFontRenderContext();
	

		skala = (wysokoscOkna - odstepDol - odstepGora) / pomoc;

		g.setColor(Color.yellow);
		Point o1 = data.points.get(0);
		Point o2;
		odstepOdPoczatkuWykresu = 40 - o1.getX();
		odstepPunkt = o1.getX();

		for (int i = 1; i < data.points.size(); i++) {

			o2 = data.points.get(i);

			if (!o1.getDate().equals(o2.getDate())) {
				g.setColor(Color.gray);
				g.drawLine(o2.getX() + odstepOdPoczatkuWykresu, minA, o2.getX()
						+ odstepOdPoczatkuWykresu, maxA);
				g.drawString(o2.getDate().toString(), o2.getX()
						+ (int) (odstepOdPoczatkuWykresu / 2.5), maxA + 20);
				g.setColor(Color.yellow);
			}

			g.drawLine(o1.getX() + odstepOdPoczatkuWykresu,
					obliczPunkt(o1.getY()),
					o2.getX() + odstepOdPoczatkuWykresu, obliczPunkt(o2.getY()));
			o1 = o2;
		}

		if (kuleczka != null) {
			
			g.scale(1.0 / przeskalujWykresX, 1.0 / przeskalujWykresY);
			kuleczka.paintKuleczka(g);
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
		if (square.getEndX() != null) {
			square.setStartX((int) (startX * przeskalujWykresX));
			square.setEndX((int) (endX * przeskalujWykresX));
		}
		obliczKulke((int) (lastMouseMoved * przeskalujWykresX));

	}

	public void setPrzeskalujWykresY(int y) {
		przeskalujWykresY = (double) y / 10.0;
		if (square.getEndY() != null) {
			square.setStartY((int) (startY * przeskalujWykresY));
			square.setEndY((int) (endY * przeskalujWykresY));
		}
		obliczKulke((int) (lastMouseMoved * przeskalujWykresX));

	}

	public int ilePowinno(int x, Point p1, Point p2) {

		double a;
		int ile = 0;
		if ((p1.getX()) - (p2.getX()) == 0) {
			a = 0;
		} else {
			a = ((double) (p1.getY() - p2.getY()) / ((double) (p1.getX()) - (p2
					.getX())));
		}
		double b = p1.getY() - ((p1.getX() * a));
		
		ile = (int) Math.round(((a * x) + b));
		return ile;

	}

	public void zaznaczenie() {

		if (last - first < 2)
			return;

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
				fw.write(String.valueOf(i.getTime()));
				fw.write("\n");
				fw.write(String.valueOf(i.getDate()));
				fw.write("\n");

			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		noweOkno = new Results(nowDate.toString() + ".txt");
		noweOkno.setVisible(true);

	}

	public boolean isMilimeterMode() {

		return milimeterMode;
	}

	private boolean isTimeMode() {

		return timeMode;
	}

	public void setMilimeterMode(boolean milimeterMode) {
		this.milimeterMode = milimeterMode;
		repaint();
	}

	public void obliczKulke(int x) {

		int wspX = (int) (((x / przeskalujWykresX) - (odstepOdPoczatkuWykresu)) / (odstepPunkt));

		if (wspX >= 0 && wspX < data.points.size()) {
			

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

			
			int tmpX = (int) ((x / przeskalujWykresX) - (odstepOdPoczatkuWykresu));
			int ilepowinnobyc2 = ilePowinno(tmpX, p1, p2);
			if (kuleczka == null) {
				kuleczka = new Kuleczka(x,
						obliczPunkt(ilePowinno(tmpX, p1, p2)));
			} else if (((x / przeskalujWykresX) - (odstepOdPoczatkuWykresu) >= data.points
					.get(0).getX())) {
			
				pozycjaYKulki = (int) (obliczPunkt(ilePowinno(tmpX, p1, p2)) * przeskalujWykresY);
				kuleczka.setX((int) (x));
				kuleczka.setY(pozycjaYKulki);

				setToolTipText("<html>Wspolrzedna X: "
						+ (int) ((x / przeskalujWykresX) - (odstepOdPoczatkuWykresu))
						+ "<br>Wspolrzedna Y: " + ilepowinnobyc2
						+ "<br>Pomiar urzadzenie nr: " + p1.getIndeks()
						+ "<br>Czas " + p1.getTime() + "<br>Data: "
						+ p1.getDate()

						+ "</html>");

				repaint();

			}

			
		}
	}

	@Override
	public java.awt.Point getToolTipLocation(MouseEvent event) {
		if (event.getID() == MouseEvent.MOUSE_MOVED) {
			return new java.awt.Point(
					(int) ((lastMouseMoved) * przeskalujWykresX),
					pozycjaYKulki + 5);
		}
		return super.getToolTipLocation(event);
	}

}
