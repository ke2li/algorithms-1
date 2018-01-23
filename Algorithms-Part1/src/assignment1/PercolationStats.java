import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats{
	private final int nTrials;
	private final double mean;
	private final double stddev;
	private final double confidenceLo;
	private final double confidenceHi;
	
	public PercolationStats(int n, int trials){
		if(n<=0 || trials <= 0) throw new java.lang.IllegalArgumentException();
		double[] stats = new double[trials];
		nTrials = trials;
		int row;
		int col;
		
		for(int i=0; i<trials; i++){
			Percolation p = new Percolation(n);

			while(p.percolates() == false) {
				stats[i]++;
				do {
					row = StdRandom.uniform(1,n+1);
					col = StdRandom.uniform(1,n+1);
				}while(!p.isOpen(row, col));
					
				p.open(StdRandom.uniform(1,n+1), StdRandom.uniform(1,n+1));
			}
			stats[i] = stats[i] / (double)(n*n);
		}
		
		mean = StdStats.mean(stats);
		stddev = StdStats.stddev(stats);
		confidenceLo = mean - (1.96 * stddev)/ Math.sqrt(trials);
		confidenceHi = mean + (1.96 * stddev) / Math.sqrt(trials);
	}

	public double mean(){
		return mean;
	}

	public double stddev(){
		if(nTrials == 1) return Double.NaN;
		return stddev;
	}

	public double confidenceLo(){
		return confidenceLo;
	}

	public double confidenceHi(){
		return confidenceHi;
	}
	
	public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
