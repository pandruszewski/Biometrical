package com.wat.pz.main;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wat.pz.utils.Toolkit;

public class PropertiesWidget extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ip = new JTextField(15);
	private JTextField port = new JTextField(15);

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

	private void init(int index,int width, int height) {
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("IP Hosta nr " + (index+1) + " : "),BorderLayout.WEST);
		p.add(ip,BorderLayout.EAST);
		this.add(p,BorderLayout.NORTH);


		p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Port: "),BorderLayout.WEST);
		p.add(port,BorderLayout.EAST);
		this.add(p,BorderLayout.SOUTH);
		Toolkit.setComponentAllSizes(this, width-40, 50);
	
	}

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

}
