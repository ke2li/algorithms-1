import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
	private LineSegment[] segments;
	
	public BruteCollinearPoints(Point[] points) {
	   // finds all line segments containing 4 points
		if(points == null) throw new java.lang.IllegalArgumentException();
		for(int i=0;i< points.length; i++) {
			   if(points[i] == null) throw new java.lang.IllegalArgumentException();
		}
		
		   for(int i=0; i<points.length; i++) {
			   for(int j=i+1; j<points.length; j++) {
				   if(points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
			   }
		   }
		   
	   ArrayList<LineSegment> collinear = new ArrayList<>();
	   Point[] pointsCopy = Arrays.copyOf(points, points.length);
	   int n = pointsCopy.length;
	   

	   
	   Arrays.sort(pointsCopy);
	   for(int i=0; i<n; i++) {
		   for(int j=i+1; j<n; j++) {
			   for(int k=j+1; k<n; k++) {
				   for(int l=k+1; l<n; l++) {
					   if(pointsCopy[i].slopeTo(pointsCopy[j]) == pointsCopy[j].slopeTo(pointsCopy[k]) &&
					      pointsCopy[k].slopeTo(pointsCopy[l]) == pointsCopy[j].slopeTo(pointsCopy[k])) {
						   collinear.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
					   }
				   }
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