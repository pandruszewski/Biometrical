package com.wat.pz.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ColorSwatch implements Icon {
	Color color= Color.yellow;

	public ColorSwatch(Color g) {
		color = g;
	}
	public ColorSwatch() {
	}

	public int getIconWidth() {
		return 11;
	}

	public int getIconHeight() {
		return 11;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(getColor());
		g.fillRect(x, y, getIconWidth(), getIconHeight());
		g.fillRect(x + 2, y + 2, getIconWidth() - 4, getIconHeight() - 4);
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}