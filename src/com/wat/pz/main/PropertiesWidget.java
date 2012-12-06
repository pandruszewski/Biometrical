package com.wat.pz.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wat.pz.utils.ColorSwatch;
import com.wat.pz.utils.Toolkit;

public class PropertiesWidget extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ip = new JTextField(15);
	private JTextField port = new JTextField(15);
	private int index;
	private JButton colorButton;
	JPanel pan;
	JLabel host;
	JLabel portHosta;
	Color diagramColor;

	public PropertiesWidget(int index, int width, int height) {
		init(index, width, height);
	}

	public PropertiesWidget(LayoutManager layout) {
		super(layout);
	}

	public PropertiesWidget(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public PropertiesWidget(LayoutManager layout, boolean isDoubleBuffered) {

		super(layout, isDoubleBuffered);
	}

	private void init(int index, int width, int height) {
		this.setIndex(index);
		this.setLayout(new BorderLayout());

		colorButton = new JButton("Zmieñ kolor", new ColorSwatch(randomColor()));
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		host = new JLabel("IP Hosta nr " + (index + 1) + " : ");
		p.add(host, BorderLayout.WEST);
		p.add(colorButton, BorderLayout.EAST);
		diagramColor = ((ColorSwatch) colorButton.getIcon()).getColor();
		colorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color current = getDiagramColor();

				// Bring up a color chooser
				Color c = JColorChooser.showDialog(getPanel(), "Wybierz kolor",
						current);

				setDiagramColor(c);

			}
		});
		p.add(ip);

		this.add(p, BorderLayout.NORTH);

		p = new JPanel();
		p.setLayout(new BorderLayout());
		portHosta = new JLabel("Port: ");
		p.add(portHosta, BorderLayout.WEST);
		pan = new JPanel();

		p.add(pan, BorderLayout.EAST);
		p.add(port);

		this.add(p, BorderLayout.SOUTH);
		Toolkit.setComponentAllSizes(this, width - 10, 55);

	}

	private Color randomColor() {
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		if (r < 0.5)
			r = (float) (r + 0.5);
		if (g < 0.5)
			r = (float) (g + 0.5);
		if (b < 0.5)
			r = (float) (b + 0.5);

		return new Color(r, g, b);

	}

	@Override
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		Toolkit.setComponentAllSizes(pan, colorButton.getWidth(),
				colorButton.getHeight());
		Toolkit.setComponentAllSizes(portHosta, host.getWidth(),
				host.getHeight());
	};

	@Override
	public void invalidate() {
		super.invalidate();
		if (pan != null)
			Toolkit.setComponentAllSizes(pan, colorButton.getWidth(),
					colorButton.getHeight());
		if (portHosta != null)
			Toolkit.setComponentAllSizes(portHosta, host.getWidth(),
					host.getHeight());
	};

	@Override
	public void paintAll(java.awt.Graphics g) {
		super.paintAll(g);
		if (pan != null)
			Toolkit.setComponentAllSizes(pan, colorButton.getWidth(),
					colorButton.getHeight());
		if (portHosta != null)
			Toolkit.setComponentAllSizes(portHosta, host.getWidth(),
					host.getHeight());
	};

	public JTextField getIp() {
		return ip;
	}

	public void setIp(JTextField ip) {
		this.ip = ip;
	}

	public JTextField getPort() {
		return port;
	}

	public void setPort(JTextField port) {
		this.port = port;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Color getDiagramColor() {
		return diagramColor;
	}

	public void setDiagramColor(Color diagramColor) {
		this.diagramColor = diagramColor;
		((ColorSwatch) colorButton.getIcon()).setColor(diagramColor);
	}

	private JPanel getPanel() {
		return this;

	}

	public JButton getColorButton() {
		return colorButton;
	}

	public void setColorButton(JButton colorButton) {
		this.colorButton = colorButton;
	}

}
