package com.wat.pz.wizualizacja.collection;

import java.awt.event.ActionEvent;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CustomCollection<E> extends LinkedList<E> {

	E min;
	E max;
	CustomListener customListener;

	public E getMax() {
		return max;
	}

	public E getMin() {
		return min;
	}

	public void setMax() {

		/* this.max = Collections.max(this, ) */

	}

	public void setMin(E e) {
		this.min = e;
	}

	public CustomCollection(CustomListener cL) {

		super();
		customListener = cL;
	}

	public CustomCollection(Collection<? extends E> c, CustomListener cL) {
		super(c);
		customListener = cL;
	}

	public CustomCollection(int numElements, CustomListener cL) {
		//super(numElements);
		customListener = cL;
	}

	@Override
	public boolean add(E e) {

		boolean bol = super.add(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));

		return bol;
	}

	@Override
	public void addFirst(E e) {

		super.addFirst(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public void addLast(E e) {

		super.addLast(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public E getFirst() {
		E ret = super.getFirst();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public E getLast() {
		E ret = super.getLast();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public E pop() {
		E ret = super.pop();
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
		return ret;
	}

	@Override
	public void push(E e) {
		super.push(e);
		customListener.actionPerformed(new ActionEvent(this, 1, "add"));
	}

	@Override
	public E remove() {
		E ret = super.remove();
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
	public E removeFirst() {

		E ret = super.removeFirst();
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
	public E removeLast() {
		E ret = super.removeLast();
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
