import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final WeightedQuickUnionUF mainUnion;
    private final boolean[][] matrixOpen;
    private final int size;
    private int openSites;
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException();
        this.size = n;
        this.matrixOpen = new boolean[ n ][ n ];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                this.matrixOpen[i][j] = false;
            }
        }
        this.mainUnion = new WeightedQuickUnionUF(n * n + 2);
        this.openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){

        if (row < 1 || row > this.size || col < 1 || col > this.size) throw new IllegalArgumentException("");

        int normalRow = row - 1;
        int normalCol = col - 1;

        if (isOpen(row, col)) return;
        this.matrixOpen[normalRow][normalCol] = true;

        int index = normalRow * this.size + normalCol + 1;
        if (normalRow == 0)mainUnion.union( index, 0);
        if (normalRow == this.size - 1)mainUnion.union(index, this.size*this.size+1);
        if (row > 1 && isOpen(row - 1, col)) mainUnion.union(index, col + this.size * (normalRow - 1));
        if (row < this.size  && isOpen(row + 1, col )) mainUnion.union( index, col+this.size * (normalRow + 1));
        if (col > 1 && isOpen( row, col - 1)) mainUnion.union(index, col-1 + this.size*normalRow);
        if (col < this.size  && isOpen( row, col + 1)) mainUnion.union(index, col + 1 + this.size * normalRow);

        this.openSites=this.openSites+1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row < 1 || row > this.size || col < 1 || col > this.size) throw new IllegalArgumentException("");
        return this.matrixOpen[ row - 1][ col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row < 1 || row > this.size || col < 1 || col > this.size) throw new IllegalArgumentException("");
        if (!isOpen(row, col)) return false;
        return this.mainUnion.connected(0,(row - 1) * this.size + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return this.mainUnion.connected(0,this.size * this.size + 1);
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(4, 2);
        p.open(5, 2);

        System.out.println("Percolates: " + p.percolates());
        System.out.println("Number of open sites: " + p.numberOfOpenSites());
    }
}
