package com.wat.pz.utils;

import java.awt.Dimension;

import javax.swing.JComponent;

public final class Toolkit {

	public static void setComponentAllSizes(JComponent component, int width,
			int height) {
		component.setSize(new Dimension(width, height));
		component.setPreferredSize(new Dimension(width, height));
		component.setMaximumSize(new Dimension(width, height));
	}

}
