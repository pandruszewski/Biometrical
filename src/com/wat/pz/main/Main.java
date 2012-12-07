package com.wat.pz.main;

import javax.swing.RepaintManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


public class Main {

	public static void main(String[] args){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ee) {
			
		}
		

		Window window = new Window();
		 RepaintManager.currentManager(window).setDoubleBufferingEnabled(true);
		window.setSize(500, 500);
		
	}
}
