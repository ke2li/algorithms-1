import java.util.Set;

import edu.princeton.cs.algs4.SET
import javafx.geometry.Point2D;<Key>;


public class Point2De {
	private static class Node{
		private Point2D p;
		private RectHV rect;
		private Node lb;
		private Node rt;
	}
	
	private Set<Node> set;
	public KdTree() {
		set = new SET();
	}
	public boolean isEmpty() {
		if(set.size() == 0) {
			return true;
		}
		return false;
	}
	public int size() {
		return set.size();
	}
	public void insert(Point2D p) {
		if(p==null) throw new java.lang.IllegalArgumentException();
		set.add(p);
	}
	public boolean contains(Point2D p) {
		if(p==null) throw new java.lang.IllegalArgumentException();
		for(Point2D i :set) {
			if(p.equals(i)) return true;
		}
		return false;
	}
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		for(Point2D i :set) {
			i.draw();
		}
	}
	public Iterable<Point2D> range(RectHV rect){
		if(rect==null) throw new java.lang.IllegalArgumentException();
	}
	public Point2D nearest(Point2D p) {
		if(p==null) throw new java.lang.IllegalArgumentException();
		
	}
}
