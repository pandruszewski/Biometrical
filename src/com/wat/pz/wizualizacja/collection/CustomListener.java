package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

public class CustomListener implements ActionListener {

	JComponent jcomp;

	public CustomListener(JComponent tmpJcomp) {

		super();
		this.jcomp = tmpJcomp;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if ("add".equals(e.getActionCommand())) {
			jcomp.repaint();
			// System.out.println("dodano");

			
		} else if ("remove".equals(e.getActionCommand())) {
			//jcomp.repaint();
			
		}

	}

}
