package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class CustomCollection extends LinkedList<Measurement> {

	private Double min=0.0 ;
	private Double max=0.0 ;
	
	CustomListener customListener;

	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public void setMax() {

	

	}

	public void setMin(Double e) {
		this.min = e;
	}

	public CustomCollection(CustomListener cL) {

		super();
		customListener = cL;
	}

	public CustomCollection(Collection<? extends Measurement> c, CustomListener cL) {
		super(c);
		customListener = cL;
	}

	public CustomCollection(int numElements, CustomListener cL) {
	
		customListener = cL;
	}

	@Override
	public boolean add(Measurement e) {

		boolean bol = super.add(e);

		customListener.actionPerformed(new ActionEvent(this, 1, "add"));

		return bol;
	}

	public synchronized double getScaleNumber(int rozmiar) {

		
		if (rozmiar > 0 && super.size() - 1 > 0) {
		List<Measurement> lista;
			if (rozmiar >= (super.size() - 1)) {
				lista =  super.subList(0, super.size() - 1);
			} else {
				lista =  super.subList((super.size() - 1 - rozmiar),
						super.size() - 1);
			}
		
			Measurement tmpMax = Collections.max(lista);
			Measurement tmpMin = Collections.min(lista);
			min= tmpMin.getValue();
			
			max= tmpMax.getValue();
		
			
		}
//		if(max - min < 100) {
//			return max - min;
//		}
		
//		return max - min;0
//		min = 35000.0;
//		max =48000.0;
//		min = 28000.0;
//		max =30000.0;
//		if(max - min < 1000){
			max += 500;
			min -= 500;
//		}else{
////			max += 500;
//			min -= 500;
//		}
//		
		
		return max  - min;
	}

	@Override
	public void addFirst(Measurement e) {

		super.addFirst(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	public synchronized void addLast(Measurement e) {

		super.addLast(e);

		customListener.refresh();// actionPerformed(new ActionEvent(this, 1,
									// "add"));
	}

	@Override
	public Measurement getFirst() {
		Measurement ret = super.getFirst();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public Measurement getLast() {
		Measurement ret = super.getLast();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public Measurement pop() {
		Measurement ret = super.pop();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public void push(Measurement e) {
		super.push(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public Measurement remove() {
		Measurement ret = super.remove();
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
	public Measurement removeFirst() {

		Measurement ret = super.removeFirst();
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
	public Measurement removeLast() {
		Measurement ret = super.removeLast();
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
