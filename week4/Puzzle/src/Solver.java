import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private final MinPQ<SearchNode> mainSequence;
    private final MinPQ<SearchNode> alternativeSequance;
    private int countOfMoves;
    private int twinCountOfMoves;
    private boolean solveable = false;
    private int minMoves = 0;
    private SearchNode lastNode = null;
    private static class SearchNode implements Comparable<SearchNode>{
        private Board current = null;
        private SearchNode pred = null;
        private int moves = 0;
        private int priority = 0;
        public SearchNode(Board initial,int m,SearchNode pred){
            current = initial;
            moves = m;
            this.priority = m + initial.manhattan();
            this.pred = pred;
        }

        public int getPriority() {
            return priority;
        }

        public Board getCurrent() {
            return current;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPred() {
            return pred;
        }

        @Override
        public int compareTo(SearchNode o){
            return this.priority - o.priority;
        }
    }

    private final LinkedList<Board> seenBoards = new LinkedList<>();
    private final LinkedList<Board> twinSeenBoards = new LinkedList<>();
    public Solver(Board initial){
        if (initial==null)
            throw new java.lang.IllegalArgumentException();
        this.countOfMoves = 0;
        this.twinCountOfMoves = 0;

        this.mainSequence = new MinPQ<SearchNode>();
        this.alternativeSequance = new MinPQ<SearchNode>();

        SearchNode searchNode = new SearchNode(initial, countOfMoves, null);
        SearchNode twin = new SearchNode(initial.twin(), countOfMoves, null);

        mainSequence.insert(searchNode);
        alternativeSequance.insert(twin);
        findSolution();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return this.solveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(!this.isSolvable()) return -1;
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    private void findSolution(){
        boolean solved = false;
        boolean twinSolved = false;
        SearchNode current = null;
        SearchNode twinCurrent = null;
        while(!solved && !twinSolved){
            current = (SearchNode) this.mainSequence.delMin();
            Board temp = current.getCurrent();
            solved = temp.isGoal();


            twinCurrent = (SearchNode) this.alternativeSequance.delMin();
            Board twinTemp = twinCurrent.getCurrent();
            twinSolved = twinTemp.isGoal();

            for(Board b: temp.neighbors()){
                if(current.pred!=null && current.pred.getCurrent().equals(b)) continue;
                SearchNode newNeighboor = new SearchNode(b, current.getMoves() + 1, current);
                this.mainSequence.insert(newNeighboor);
            }

            for(Board b: twinTemp.neighbors()){
                if(twinCurrent.pred!=null && twinCurrent.pred.getCurrent().equals(b)) continue;
                SearchNode newNeighboor = new SearchNode(b, twinCurrent.getMoves() + 1, twinCurrent);
                this.alternativeSequance.insert(newNeighboor);
            }
            this.countOfMoves = current.getMoves() + 1;
            this.twinCountOfMoves = twinCurrent.getMoves() + 1;
            lastNode = current;
        }
        this.minMoves = this.countOfMoves - 1;
        solveable = !twinSolved;
    }

    public Iterable<Board> solution(){
        if(!this.isSolvable()) return null;
        Stack<Board> boards = new Stack<Board>();
        SearchNode lastNode = this.lastNode;
        while(lastNode.getPred()!=null){
            boards.push(lastNode.getCurrent());
            lastNode = lastNode.getPred();
        }
        boards.push(lastNode.getCurrent());
        return boards;
    }

//    // test client (see below)
//    public static void main(String[] args){
//        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
//        Board initial = new Board(tiles);
//
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
//    }
    public static void main(String[] args){
        int[][] n = new int[3][3];
        n[0][0] = 7;
        n[0][1] = 8;
        n[0][2] = 5;
        n[1][0] = 4;
        n[1][1] = 0;
        n[1][2] = 2;
        n[2][0] = 3;
        n[2][1] = 6;
        n[2][2] = 1;
        System.out.println(new Board(n).manhattan());
        System.out.println(new Solver(new Board(n)).minMoves);
    }

}
