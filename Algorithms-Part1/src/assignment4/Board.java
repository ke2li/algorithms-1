import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
	private int n;
	private int[][] board; 
	private Board[] neighbors = null;
	
	private int[][] copyBoard(){
		int[][] copy = new int[n][n];
		for(int i=0;i<n;i++) {
			for(int j=0; j<n; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}
	
    public Board(int[][] blocks) {         
    	// construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
    	if(blocks == null) throw new java.lang.IllegalArgumentException();
    	n = blocks.length;
    	board = new int[n][n];
    	for(int i=0; i<n; i++) {
    		for(int j=0; j<n; j++) {
    			//if(blocks[i][j] == null) throw new java.lang.IllegalArgumentException();
    			board[i][j] = blocks[i][j];
    		}
    	}
    }
    public int dimension() {
    	// board dimension n
    	return n;
    }
    public int hamming() {
    	// number of blocks out of place
    	int sum =0;
    	for(int i=0; i<n; i++) {
    		for(int j=0; j<n; j++) {
    			if(board[i][j] != (i*n + j +1) && board[i][j]!=0) sum++;
    		}
    	}
    	return sum;
    }
    public int manhattan() {
    	// sum of Manhattan distances between blocks and goal
    	int sum=0;
    	for(int i=0; i<n; i++) {
    		for(int j=0; j<n; j++) {
    			if(board[i][j]!= (i*n + j+1) && board[i][j] !=0) {
    				sum += Math.abs(((board[i][j]-1) / n) -i);
    				sum += Math.abs(((board[i][j]-1) % n) -j);
    			}
    		}
    	}
    	return sum;
    }
    public boolean isGoal() {
    	// is this board the goal board?
    	return hamming() == 0;
    }
    public Board twin() {
    	// a board that is obtained by exchanging any pair of blocks
    	int [][] copy = copyBoard();
    	
    	int i=0;
    	int j=0;
    	while(copy[i][j] == 0 || copy[i][j+1] == 0) {
    		i++;
    		if(i>=n) {
    			i=0;
    			j++;
    		}
    	}
    	
    	int temp = copy[i][j];
    	copy[i][j] = copy[i][j+1];
    	copy[i][j+1] = temp;
    	
    	return new Board(copy);
    }
    public boolean equals(Object y) {
    	// does this board equal y?
    	if(y == this) return true;
    	if(y == null) return false;
    	if(y.getClass() != this.getClass()) return false;
    	Board that = (Board) y;
    	if(that.board.length != this.board.length) return false;
    	for(int i=0;i<n;i++) {
    		for(int j=0; j<n; j++) {
    			if(board[i][j] != that.board[i][j]) return false;
    		}
    	}
    	return true;
    }
    public Iterable<Board> neighbors() {
    	// all neighboring boards
    	return new Iterable<Board>() {
    		public Iterator<Board> iterator(){
	    		if(neighbors == null) {
	    			List<Board> foundNeighbors = new ArrayList<>();
	    			
	    			int p=0;
	    			int q=0;
	    			while(board[p][q]!=0) {
	    				q++;
	    				if(q>=n) {
	    					p++;
	    					q=0;
	    				}
	    			}
	    			
	    			int temp;
	    			
	    			if(p!=0) {
	    				int [][] copy = copyBoard();
	    				
	    		    	temp = copy[p][q];
	    		    	copy[p][q] = copy[p-1][q];
	    		    	copy[p-1][q] = temp;
	    		    	
	    		    	foundNeighbors.add(new Board(copy));
	    			}
	    			
	    			if(p!= n-1) {
	    				int[][] copy = copyBoard();
	    				
	    		    	temp = copy[p][q];
	    		    	copy[p][q] = copy[p+1][q];
	    		    	copy[p+1][q] = temp;
	    		    	
	    		    	foundNeighbors.add(new Board(copy));
	    			}
	    			
	    			if(q!= 0) {
	    				int [][] copy = copyBoard();
	    				
	    		    	temp = copy[p][q];
	    		    	copy[p][q] = copy[p][q-1];
	    		    	copy[p][q-1] = temp;
	    		    	
	    		    	foundNeighbors.add(new Board(copy));
	    			}
	    			
	    			if(q!= n-1) {
	    				int[][] copy = copyBoard();
	    				
	    		    	temp = copy[p][q];
	    		    	copy[p][q] = copy[p][q+1];
	    		    	copy[p][q+1] = temp;
	    		    	
	    		    	foundNeighbors.add(new Board(copy));
	    			}
	    			
	    			neighbors = foundNeighbors.toArray(new Board[foundNeighbors.size()]);
	    		}
	    		return new NeighborIterator();
    		}
    	};
    }
    private class NeighborIterator implements Iterator<Board>{
    	int index = 0;
    	public boolean hasNext() {
    		return index < neighbors.length;
    	}
    	public Board next() {
    		if(hasNext()) return neighbors[index++];
    		else {
    			throw new java.util.NoSuchElementException();
    		}
    	}
    	public void remove(){
    		throw new java.lang.UnsupportedOperationException();
    	}
    }
    public String toString() {
    	// string representation of this board (in the output format specified below)
    	StringBuilder s = new StringBuilder();
    	s.append(n + "\n");
    	for(int i=0; i<n; i++) {
    		for(int j=0; j<n; j++) {
    			s.append(String.format("%2d ", board[i][j]));
    		}
    		s.append("\n");
    	}
    	return s.toString();
    }
}