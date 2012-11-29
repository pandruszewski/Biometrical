package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CustomCollection extends LinkedList<Double> {

	private Double min = 0.0;
	private Double max = 0.0;
	CustomListener customListener;

	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public void setMax() {

		/* this.max = Collections.max(this, ) */

	}

	public void setMin(Double e) {
		this.min = e;
	}

	public CustomCollection(CustomListener cL) {

		super();
		customListener = cL;
	}

	public CustomCollection(Collection<? extends Double> c, CustomListener cL) {
		super(c);
		customListener = cL;
	}

	public CustomCollection(int numElements, CustomListener cL) {
		// super(numElements);
		customListener = cL;
	}

	@Override
	public boolean add(Double e) {

		boolean bol = super.add(e);
	
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));

		return bol;
	}

	public double getScaleNumber(int rozmiar) {
		double a = 0.0;

		if (rozmiar > 0 && rozmiar < super.size()) {
			List<Double> lista = super.subList(rozmiar-1, super.size() - 1);
			max = Collections.max(lista);
			min = Collections.min(lista);
			//System.out.println("max= " + max + " min = " + min);
		}
		return a;
	}

	@Override
	public void addFirst(Double e) {

		super.addFirst(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public void addLast(Double e) {

		super.addLast(e);
		if (e > max) {
			max = e;
		} else if (e < min) {
			min = e;
		}
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public Double getFirst() {
		Double ret = super.getFirst();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public Double getLast() {
		Double ret = super.getLast();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public Double pop() {
		Double ret = super.pop();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public void push(Double e) {
		super.push(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public Double remove() {
		Double ret = super.remove();
		customListener.actionPerformed(new ActionEvent(this, 1, "remove"));
		return ret;
	}

	@Override
	public boolean remove(Object o) {

		boolean ret = super.remove(o);
		customListener.actionPerformed(new ActionEvent(this, 1, "remove"));
		return ret;
	}

	@Override
	public Double removeFirst() {

		Double ret = super.removeFirst();
		customListener.actionPerformed(new ActionEvent(this, 1, "remove"));
		return ret;

	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		boolean ret = super.removeFirstOccurrence(o);
		customListener.actionPerformed(new ActionEvent(this, 1, "remove"));
		return ret;
	}

	@Override
	public Double removeLast() {
		Double ret = super.removeLast();
		customListener.actionPerformed(new ActionEvent(this, 1, "remove"));
		return ret;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		boolean ret = super.removeLastOccurrence(o);
		customListener.actionPerformed(new ActionEvent(this, 1, "remove"));
		return ret;
	}

}
