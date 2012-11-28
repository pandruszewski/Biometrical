package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

public class CustomListener implements ActionListener   {

	
	JComponent jcomp;
	public CustomListener(JComponent tmpJcomp) {
		
		super();		
		this.jcomp=tmpJcomp;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if("add".equals(e.getActionCommand())){
		
		System.out.println("dodano");	
		jcomp.repaint();
		}
		else if("remove".equals(e.getActionCommand())){
		
		System.out.println("usunieto");	
		jcomp.repaint();
		}
		
		
	}

}
