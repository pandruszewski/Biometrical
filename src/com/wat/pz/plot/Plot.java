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
import java.util.Random;

import javax.swing.JPanel;

import com.wat.pz.main.PropertiesWidget;
import com.wat.pz.utils.ColorSwatch;
import com.wat.pz.wizualizacja.connection.Connect;

public class Plot extends JPanel {
	private Connect connect;
	private int wysokosc = 0;
	private JPanel p;
	private int dlugoscY;
	private Graph graph;
	private double skala = 1.0;
	private int odstep = 10;
	private PropertiesWidget propertiesWidget;
	private BufferStrategy bs;
	public int getOdstep() {
		return odstep;
	}

	// private CustomCollection customCollection;
	public Plot(JPanel p, Graph graph, BufferStrategy bs) {
		this.bs= bs;
		this.p = p;
		this.setSize(p.getSize());
		this.graph = graph;
		System.out.println(this.isDoubleBuffered());
		this.setDoubleBuffered(true);
		this.setIgnoreRepaint(true);
	}

	@Override
	public synchronized void paintComponent(Graphics gg) {

		super.paintComponent(gg);
//
//		Graphics2D g = (Graphics2D) gg;
//		
//		Color kolor = ((ColorSwatch) propertiesWidget.getColorButton()
//				.getIcon()).getColor();
//		int dlugosc = connect.getCustomCollection().size() - 1;
//		int j = 0;
//		int v = 0;
//		this.setSize(p.getSize());
//
//		wysokosc = this.getSize().height - odstep;
//
//		g.setColor(kolor);
//		int i = 45;
//		i = this.getSize().width;
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
//		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//		if (dlugosc > 1) {
//			j = obliczPunkt(connect.getCustomCollection().get(dlugosc)
//					.intValue());
//		}
//		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
//				BasicStroke.JOIN_ROUND));
//		g.setColor(kolor);
//		 synchronized (this) {
//		while (i > 45) {
//
//			if (dlugosc > 1 && connect.getCustomCollection() != null) {
//
//				skala = connect.getCustomCollection().getScaleNumber(
//						(p.getSize().width - 40) / odstep);
//				dlugoscY = graph.getScaleHeight();
//
//				graph.scaleGraph(skala / dlugoscY);
//				skala = (double) dlugoscY / skala;
//				v = obliczPunkt((connect.getCustomCollection().get(dlugosc)
//						.intValue()));
//				//g.drawLine        (i - odstep, v, i, j);
//				g.draw(new Line2D.Double(i - odstep, v, i, j));
//				dlugosc -= 1;
//				j = v;
//				
//
//			}
//
//			i -= odstep;
//
//		}
//		 }
gg.drawImage(drawImage(), 0, 0, this);
	}
	
public BufferedImage drawImage(){
		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
	  Graphics2D g = bi.createGraphics();
		Color kolor = ((ColorSwatch) propertiesWidget.getColorButton()
				.getIcon()).getColor();
		int dlugosc = connect.getCustomCollection().size() - 1;
		int j = 0;
		int v = 0;
		this.setSize(p.getSize());

		wysokosc = this.getSize().height - odstep;

		g.setColor(kolor);
		int i = 45;
		i = this.getSize().width;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		if (dlugosc > 1) {
			j = obliczPunkt(connect.getCustomCollection().get(dlugosc)
					.intValue());
		}
		g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(kolor);
		 synchronized (this) {
		while (i > 45) {

			if (dlugosc > 1 && connect.getCustomCollection() != null) {

				skala = connect.getCustomCollection().getScaleNumber(
						(p.getSize().width - 40) / odstep);
				dlugoscY = graph.getScaleHeight();

				graph.scaleGraph(skala / dlugoscY);
				skala = (double) dlugoscY / skala;
				v = obliczPunkt((connect.getCustomCollection().get(dlugosc)
						.intValue()));
				//g.drawLine        (i - odstep, v, i, j);
				g.draw(new Line2D.Double(i - odstep, v, i, j));
				dlugosc -= 1;
				j = v;
				

			}

			i -= odstep;

		}
		 }
		 
	return bi;
}

	@Override
	public void update(Graphics g) {
		super.update(g);
	};

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

	public PropertiesWidget getPropertiesWidget() {
		return propertiesWidget;
	}

	public void setPropertiesWidget(PropertiesWidget propertiesWidget) {
		this.propertiesWidget = propertiesWidget;
	}

}
