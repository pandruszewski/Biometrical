package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.wat.pz.plot.Plot;
import com.wat.pz.wizualizacja.connection.Connect;

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

	public synchronized double getScaleNumber(int rozmiar) {

		/* if (rozmiar > 0 && rozmiar < super.size()) { */
		if (rozmiar > 0 && super.size() - 1 > 0) {
			List<Double> lista;
			if (rozmiar >= (super.size() - 1)) {
				lista = super.subList(0, super.size() - 1);
			} else {
				lista = super.subList((super.size() - 1 - rozmiar),
						super.size() - 1);
			}
			// System.out.println("wjechal  "+ rozmiar);
			max = Collections.max(lista);
			min = Collections.min(lista);
			//lista = null;
			//System.gc();
			// System.out.println("max= " + max + " min = " + min + " size = " +
			// super.size());
		}
		if ((-min) > max) {
			return (-min);
		}
		if( max- min > 2000) return /* max */max - min;
		else if(max - min < 1000 ) return 20000;
		return 1000;
	}

	@Override
	public void addFirst(Double e) {

		super.addFirst(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	public synchronized void addLast(Double e) {

		super.addLast(e);

		customListener.refresh();// actionPerformed(new ActionEvent(this, 1,
									// "add"));
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
