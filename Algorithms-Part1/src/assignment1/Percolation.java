import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int N;
	private int nOpen;
	private boolean[][] grid;
	private final WeightedQuickUnionUF connected;

	public Percolation(int n) {
		if(n <= 0) throw new java.lang.IllegalArgumentException();
		N = n;
		nOpen = 0;
		connected = new WeightedQuickUnionUF(n*n +2);
		grid = new boolean[n+1][n+1];
		
		for (int i = 1; i < n+1; i++) {
			for (int j = 1; j < n+1;j++) {
				grid[i][j] = false;
			}
		}
	}
	
	private void validate(int row, int col) {
		if (row > N || col > N|| row < 1 || col < 1) throw new java.lang.IllegalArgumentException();
	}
	
	private int getIndex(int row, int col) {
		return col+((row-1) * N);
	}

	public void open(int row, int col) {
		validate(row, col);
			
		if (grid[row][col] == false) {
			nOpen++;
			grid[row][col] = true;
			
			if (row == 1)connected.union(getIndex(row, col), 0);
			if (row == N)connected.union(getIndex(row, col), N*N +1);
			
			if (row > 1) {
				if(isOpen(row-1, col)) connected.union(getIndex(row, col), getIndex(row-1, col));
			}
			if (row < N) {
				if(isOpen(row+1, col)) connected.union(getIndex(row, col), getIndex(row+1, col));
			}
			if (col > 1) {
				if(isOpen(row, col-1)) connected.union(getIndex(row, col), getIndex(row ,col-1));
			}
			if (col < N) {
				if(isOpen(row, col+1)) connected.union(getIndex(row, col), getIndex(row, col+1));
			}
		}
	}

	public boolean isOpen(int row, int col) {
		validate(row, col);
		return grid[row][col];
	}

	public boolean isFull(int row, int col) {
		validate(row, col);
		if(grid[row][col] == false) return false;
		return connected.connected(0, getIndex(row,col));
	}

	public int numberOfOpenSites() {
		return nOpen;
	}

	public boolean percolates() {
		return connected.connected(0, N*N +1);
	}
}
