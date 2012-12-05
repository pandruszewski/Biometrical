package com.wat.pz.show.results;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

public class Data {

	private DocumentBuilder builder = null;
	private XPath xpath = null;
	private File f = null;
	private Document doc = null;
	public List<Point> points = new LinkedList<Point>();
	
	

	public Data(String path) throws XPathExpressionException {
		
		f = new File(path);
		parseDocument();
		
	}
	private void parseDocument() throws XPathExpressionException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String s = null;
			while((s = br.readLine() )!= null){
				//System.out.println(">>" + s);
				points.add(new Point(Integer.parseInt(s), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine())));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

		
	}

	public int getMaxX() {
		Comparator c = new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				
				return Math.max(o1.getX(), o2.getX());
			}

		};
		Point max = Collections.max(points, c);
		return max.getX();

	}

	public int getMaxY() {

		Point max = Collections.max(points);
		Point min = Collections.min(points);
		if (min.getY() < 0) {
			if (-min.getY() > max.getY()) {
				return -min.getY();
			}
		}
		return max.getY();

	}

	
}
