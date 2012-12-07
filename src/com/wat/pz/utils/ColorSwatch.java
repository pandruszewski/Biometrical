package com.wat.pz.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
public class ColorSwatch implements Icon { 
 	Color color; 
  
 	public ColorSwatch(Color g ) { 
 	   
 	    color = g; 
 	} 
  
 	public int getIconWidth() { 
 	    return 11; 
 	} 
  
 	public int getIconHeight() { 
 	    return 11; 
 	} 
 	@Override
 	public void paintIcon(Component c, Graphics g, int x, int y) { 
 	    g.setColor(color); 
 	    g.fillRect(x, y, getIconWidth(), getIconHeight()); 
 	   
 	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}


     } 