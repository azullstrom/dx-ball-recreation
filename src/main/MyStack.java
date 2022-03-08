package main;

import javax.swing.DefaultListModel;

import interfaces.StackADT;

public class MyStack<T> implements StackADT<T> {

	private DefaultListModel<T> list;
	private int size;
	
	public MyStack(int size) {
		list = new DefaultListModel<T>();
		this.size = size;
	}
	
	@Override
	public void push(T element) {
		list.add(0, element);
	}
	
	public void push(T element, int i) {
		list.add(i, element);
	}

	@Override
	public T pop() {
		T temp = list.get(0);
		list.remove(0);
		return temp;
	}
	
	public T pop(int i) {
		T temp = list.get(i);
		list.remove(i);
		return temp;
	}
	
	public T getPosition(int i) {
		return list.get(i);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return list.size() == size;
	}
	
	public DefaultListModel getModel() {
		return list;
	}

}
