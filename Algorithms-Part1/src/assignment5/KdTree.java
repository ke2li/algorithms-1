import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private static class Node{
		private Point2D p;
		private RectHV rect;
		private Node lb;
		private Node rt;
		private int level;
		
		public Node(Point2D newP, RectHV newRect, int newLevel) {
			p = newP;
			rect = newRect;
			level = newLevel;
		}
	}
	
	private int size;
	private Node first;
	private Point2D[] inRange;
	
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}
	public int size() {
		return size;
	}
	public void insert(Point2D p) {
		if(p==null) throw new java.lang.IllegalArgumentException();
		
		if(first == null) {
			first = new Node(p, new RectHV(0,0,1,1), 0);
			size++;
			return;
		}
		Node current = first;
		RectHV newRect;
		
		while(true) {
			if(current.p.equals(p))return;
			//compare x coordinates
			if(p.x() < current.p.x()) {
				if(current.lb == null) {
					newRect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.p.x(), current.rect.ymax());
					current.lb = new Node(p, newRect, current.level+1);
					size++;
					break;
				}
				current = current.lb;
			}
			else if(p.x() >= current.p.x()) {
				if(current.rt == null) {
					newRect = new RectHV(current.p.x(), current.rect.ymin(), current.rect.xmax(), current.rect.ymax());
					current.rt = new Node(p, newRect, current.level+1);
					size++;
					break;
				}
				current = current.rt;
			}
			
			if(current.p.equals(p))return;
			
			//compare y coordinates
			if(p.y() < current.p.y()) {
				if(current.lb == null) {
					newRect = new RectHV(current.rect.xmin(), current.rect.ymin(), current.rect.xmax(), current.p.y());
					current.lb = new Node(p, newRect,current.level+1);
					size++;
					break;
				}
				current = current.lb;
			}
			else if(p.y() >= current.p.y()) {
				if(current.rt == null) {
					newRect = new RectHV(current.rect.xmin(), current.p.y(), current.rect.xmax(), current.rect.ymax());
					current.rt = new Node(p, newRect, current.level+1);
					size++;
					break;
				}
				current = current.rt;
			}
		}
	}
	public boolean contains(Point2D p) {
		if(p==null) throw new java.lang.IllegalArgumentException();
		Node current =  first;
		while(current!= null) {
			if(p.equals(current.p)) return true;
			if(current.level % 2 == 0) {
				if(p.x() < current.p.x()) current = current.lb;
				else if(p.x() >= current.p.x()) current = current.rt;
			}
			else {
				if(p.y() < current.p.y()) current = current.lb;
				else if(p.y() >= current.p.y()) current = current.rt;
			}
		}
		return false;
	}
	public void draw() {
		if(first == null) return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		
		first.p.draw();
		first.rect.draw();
		draw(first.rt);
		draw(first.lb);
	}
	private void draw(Node node) {
		if(node == null) return;
		if(node.level % 2 == 0) {
			StdDraw.setPenColor(StdDraw.RED);
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
		}
		node.rect.draw();
		
		StdDraw.setPenColor(StdDraw.BLACK);
		node.p.draw();
		draw(first.rt);
		draw(first.lb);
	}
	private void constructRange(Node node, RectHV rect, ArrayList<Point2D> temp) {
		if(node == null) return;
		if(rect.contains(node.p)) temp.add(node.p);
		if(rect.intersects(node.rect)) {
			constructRange(node.lb, rect, temp);
			constructRange(node.rt, rect, temp);
		}
	}
	public Iterable<Point2D> range(RectHV rect){
		if(rect==null) throw new java.lang.IllegalArgumentException();
		if(first == null) return null;
		return new Iterable<Point2D>() {
			public Iterator<Point2D> iterator() {
				ArrayList<Point2D> temp = new ArrayList<>();
				constructRange(first, rect, temp);
				inRange = temp.toArray(new Point2D[temp.size()]);
				return new RangeIterator();
			}
		};
	}
	private class RangeIterator implements Iterator<Point2D>{
		int index = 0;
		public boolean hasNext() {
			return index<inRange.length;
		}
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		public Point2D next() {
			if(hasNext()) return inRange[index++];
			else {
				throw new java.util.NoSuchElementException();
			}
		}
	}
	public Point2D nearest(Point2D p) {
		if(p==null) throw new java.lang.IllegalArgumentException();
		if(first == null) return null;
		Point2D closest = first.p;
		searchNear(p, closest, closest.distanceSquaredTo(p), first);
		return closest;
	}
	private void searchNear(Point2D p, Point2D closest, double closestDistance, Node node) {
		if(node == null) return;
		if(closestDistance < node.rect.distanceSquaredTo(p))return;
		else {
			if(node.p.distanceSquaredTo(p) < closestDistance) {
				closestDistance = node.p.distanceSquaredTo(p);
				closest = node.p;
			}
			
			if(node.level % 2 == 0) {
				if(p.x() < node.p.x()) {
					searchNear(p, closest, closestDistance, node.lb);
					searchNear(p, closest, closestDistance, node.rt);
				}
				else {
					searchNear(p, closest, closestDistance, node.rt);
					searchNear(p, closest, closestDistance, node.lb);
				}
			}
			else {
				if(p.y() < node.p.y()) {
					searchNear(p, closest, closestDistance, node.lb);
					searchNear(p, closest, closestDistance, node.rt);
				}
				else {
					searchNear(p, closest, closestDistance, node.rt);
					searchNear(p, closest, closestDistance, node.lb);
				}
			}
		}
	}
}
