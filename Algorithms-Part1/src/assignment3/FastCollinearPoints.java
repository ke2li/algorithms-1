import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
	private LineSegment[] segments;
	
   public FastCollinearPoints(Point[] points) {
	   //check duplicates
	   if(points == null) throw new java.lang.IllegalArgumentException();
		for(int i=0;i< points.length; i++) {
			   if(points[i] == null) throw new java.lang.IllegalArgumentException();
		}
	   
		for(int i=0; i<points.length; i++) {
		   for(int j=i+1; j<points.length; j++) {
			   if(points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
		   }
	   }
	   
	   // finds all line segments containing 4 or more points
	   ArrayList<LineSegment> collinear = new ArrayList<>();
	   int n = points.length;
	   Point[] pointsCopy = Arrays.copyOf(points, points.length);
	   
	   for(int j=0; j<n-1; j++) {
		   Arrays.sort(pointsCopy, j+1, points.length, pointsCopy[j].slopeOrder());
		   
		   for(int i=j+2; i<n-3; i++) {
			   if(pointsCopy[i].slopeTo(points[i+1])==pointsCopy[i].slopeTo(pointsCopy[i+2])&&
				  pointsCopy[i].slopeTo(pointsCopy[i+3]) == pointsCopy[i].slopeTo(pointsCopy[i+2])){
					  collinear.add(new LineSegment(pointsCopy[i],pointsCopy[i+3]));
				  }
		   }
	   }
	   segments = collinear.toArray(new LineSegment[collinear.size()]);
   }
   public int numberOfSegments() {
	   // the number of line segments
	   return segments.length;
   }
   public LineSegment[] segments() {
	   // the line segments
	   return Arrays.copyOf(segments, segments.length);
   }
 
}