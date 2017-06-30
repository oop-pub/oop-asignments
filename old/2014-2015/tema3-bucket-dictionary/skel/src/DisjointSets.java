import java.util.ArrayList;

public class DisjointSets<T> {
	private ArrayList<Entry<T>> elements;
	private MyHashMap<T, Integer> indexes; 
	
	private class Entry<T> {
		public T element;
		public int setIndex;
		public  Entry(T element) {
			this.element = element;
			setIndex = elements.size();
		}
 	}
	
	public void addElement(T elem) {
		
	}
	
	public void mergeSetsOf(T e1, T e2) {

	}
	
	public ArrayList<T> setOf(T elem) {
		return null;
	}
	
	public DisjointSets() {
		
	}
}