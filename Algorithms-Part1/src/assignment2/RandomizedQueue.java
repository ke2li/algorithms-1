import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>{
		private Item[] q;
		private int n;
		
	   public RandomizedQueue() {
		   // construct an empty randomized queue
		   q = (Item[]) new Object[1];
		   n = 0;
		   q[0] = null;
	   }
	   public boolean isEmpty() {
		   // is the randomized queue empty?
		   return n==0;
	   }
	   public int size() {
		   // return the number of items on the randomized queue
		   return n;
	   }
	   private void resize(int capacity) {
		   Item[] copy =(Item[]) new Object[capacity];
		   for(int i=0; i<n; i++) {
			   copy[i] = q[i];
		   }
		   q = copy;
	   }
	   public void enqueue(Item item) {
		   // add the item
		   if(item == null) throw new java.lang.IllegalArgumentException();
		   q[n++] = item;
		   if(n == q.length) resize(q.length *2);
	   }
	   public Item dequeue() {
		   // remove and return a random item
		   if(n == 0) throw new java.util.NoSuchElementException();
		   StdRandom.shuffle(q, 0, --n);
		   Item item = q[n];
		   q[n] = null;
		   if(n == (q.length/4)) resize(q.length/2);
		   return item;
	   }
	   public Item sample() {
		   // return a random item (but do not remove it)
		   if(n == 0) throw new java.util.NoSuchElementException();
		   StdRandom.shuffle(q,0,n-1);
		   return q[n-1];
	   }
	   public Iterator<Item> iterator() {
		   return new ListIterator();
	   }
	   private class ListIterator implements Iterator<Item>{
		   private int i=n;
		   
		   public boolean hasNext() {
			   return i>0;
		   }
		   public void remove() {
			   throw new java.lang.UnsupportedOperationException();
		   }
		   public Item next() {
			   if(hasNext()==false) throw new java.util.NoSuchElementException();
			   StdRandom.shuffle(q);
			   return q[--i];
		   }
	   }
}
