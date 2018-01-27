import java.awt.geom.Point2D;
import java.util.Set;
import edu.princeton.cs.algs4.SET<Key>;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointSET {
	private Set<Point2D> set;
	private Point2D[] inRange;
	
	public PointSET() {
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
		for(Point2D i: set) {
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
	private void constructRange(RectHV rect) {
		List<Point2D> temp = new ArrayList<>();
		for(Point2D i : set) {
			if(rect.contains(i)) temp.add(i);
		}
		inRange = temp.toArray(new Point2D[temp.size()])
	}
	public Iterable<Point2D> range(RectHV rect){
		if(rect==null) throw new java.lang.IllegalArgumentException();
		return new Iterable<Point2D> {
			public Iterator<Point2D> iterator() {
				constructRange(rect);
				return new RangeIterator();
			}
		};
	}
	private class RangeIterator implements Iterator<Point2D>{
		int index = 0;
		public boolean hasNext() {
			return index<inRange.size();
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
		Point2D min = null;
		for(Point2D i :set) {
			if(min == null) min = i;
			else if(p.distanceSquaredTo(i) < p.distanceSquaredTo(min)) {
				min = i;
			}
		}
	}
}
