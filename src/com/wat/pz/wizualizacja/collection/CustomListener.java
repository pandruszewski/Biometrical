package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.wat.pz.main.Window;

public class CustomListener implements ActionListener {

	JComponent jcomp;
	JPanel jpanel;

	public CustomListener(JComponent tmpJcomp,JPanel jpanel) {

		super();
		this.jcomp = tmpJcomp;
		this.jpanel=jpanel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	/*	if ("add".equals(e.getActionCommand())) {
			jcomp.repaint();
			//
			Window.layer.repaint();
			// System.out.println("dodano");

			
		} else if ("remove".equals(e.getActionCommand())) {
			//jcomp.repaint();
			
		}*/

	}

	public void refresh() {
		// TODO Auto-generated method stub
		//Window.layer.repaint();
		jcomp.repaint();
	}

}
