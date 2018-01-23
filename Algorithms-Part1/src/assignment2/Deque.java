import java.util.Iterator;

public class Deque <Item> implements Iterable<Item> {
		private Node first;
		private Node last;
		private int n;
		
		private class Node{
			Item item;
			Node next;
			Node back;
		}
		
	   public Deque() {
		   // construct an empty deque
		   first = null;
		   last = null;
		   n = 0;
	   }
	   public boolean isEmpty() {
		   // is the deque empty?
		   return n==0;
	   }
	   public int size() {
		   // return the number of items on the deque
		   return n;
	   }
	   public void addFirst(Item item) {
		   // add the item to the front
		   if(item == null) throw new java.lang.IllegalArgumentException();
		   Node oldFirst = first;
		   first = new Node();
		   first.item = item;
		   first.back = oldFirst;
		   first.next = null;
		   if(isEmpty()) last = first;
		   else {
			   oldFirst.next = first;
		   }
		   n++;
	   }
	   public void addLast(Item item) {
		   // add the item to the end
		   if(item == null) throw new java.lang.IllegalArgumentException();
		   Node oldLast = last;
		   last = new Node();
		   last.item = item;
		   last.back = null;
		   last.next = oldLast;
		   if(isEmpty()) first = last;
		   else {
			   oldLast.back = last;
		   }
		   n++;
	   }
	   public Item removeFirst() {
		   // remove and return the item from the front
		   if(isEmpty()) throw new java.util.NoSuchElementException();
		   n--;
		   Item item = first.item;
		   first = first.back;
		   if(isEmpty()) last = first;
		   else {
			   first.next = null;
		   }
		   return item;
	   }
	   public Item removeLast() {
		   // remove and return the item from the end
		   if(isEmpty()) throw new java.util.NoSuchElementException();
		   n--;
		   Item item = last.item;
		   last = last.next;
		   if(isEmpty()) first = last;
		   else {
			   last.back = null;
		   }
		   return item;
	   }
	   public Iterator<Item> iterator(){
		   return new ListIterator();
	   }
		   // return an iterator over items in order from front to end
	   private class ListIterator implements Iterator<Item>{
		   private Node current = first;
		   
		   public boolean hasNext() {
			   return !(current == null);
		   }
		   public void remove() {
			   throw new java.lang.UnsupportedOperationException();
		   }
		   public Item next() {
			   if(!hasNext()) throw new java.util.NoSuchElementException();
			   Item item = current.item;
			   current = current.back;
			   return item;
		   }
	   }
}
