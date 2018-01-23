import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	private Board[] solution;
	private int nMoves;
	
	private class Node{
		Board board;
		int moves;
		int manhattan;
		Node previous; 
		 public Node(Board newBoard, int newMoves, Node newPrevious){
			 board = newBoard;
			 moves = newMoves;
			 manhattan = newBoard.manhattan();
			 previous = newPrevious;
		 }
	}
	
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)
    	MinPQ<Node> PQ = new MinPQ<Node>(new BoardComparator());
    	MinPQ<Node> PQtwin = new MinPQ<Node>(new BoardComparator());
    	Node current = new Node(initial, 0, null);
    	Node twin = new Node(initial.twin(), 0, null);
    	
        PQ.insert(current);
    	PQtwin.insert(twin);
    	nMoves = -2;
    	
    	while(nMoves == -2) {
    		//initial board
    		current = PQ.delMin();
    		if(current.board.isGoal() == true) nMoves = current.moves;
    		else {
    			for(Board neighbor : current.board.neighbors()) {
    				if(isDuplicate(current, neighbor) == false) {
    					PQ.insert(new Node(neighbor, current.moves +1, current));
    				}
    			}
    		}
    		
    		twin = PQtwin.delMin();
    		if(twin.board.isGoal() == true) nMoves = -1;
    		else {
    			for(Board neighbor : twin.board.neighbors()) {
    				if(isDuplicate(twin, neighbor) == false) {
    					PQtwin.insert(new Node(neighbor, twin.moves + 1, twin));
    				}
    			}
    		}
    	}

    	if(nMoves != -1) {
        	int i=nMoves;
    		solution = new Board[nMoves+1];
    		while(current != null) {
    			solution[i--] = current.board;
    			current = current.previous;
    		}
    	}
    }
    private boolean isDuplicate(Node initial, Board key) {
    	Node temp = initial.previous;
    	while(temp!= null) {
    		if(temp.board.equals(key)) return true;
    		temp = temp.previous;
    	}
    	return false;
    }
    public boolean isSolvable() {
    	// is the initial board solvable?
    	if(moves() == -1) return false;
    	return true;
    }
    public int moves() {
    	// min number of moves to solve initial board; -1 if unsolvable
    	return nMoves;
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if unsolvable
    	if(isSolvable() == false) return null;
    	return new Iterable<Board>(){
	    	public Iterator<Board> iterator(){
	    		return new solutionIterator();
	    	}
    	};
    }
    private class solutionIterator implements Iterator<Board>{
    	private int index = 0;
    	public boolean hasNext() {
    		return index < solution.length;
    	}
    	public void remove() {
    		throw new java.lang.UnsupportedOperationException();
    	}
    	public Board next() {
    		if(hasNext()) {
        		return solution[index++];
    		}
    		throw new java.util.NoSuchElementException();
    	}
    }
    private static class BoardComparator implements Comparator<Node>{
    	public int compare(Node n1, Node n2) {
    		return (n1.manhattan + n1.moves) - (n2.manhattan + n2.moves);
    	}
    }

}