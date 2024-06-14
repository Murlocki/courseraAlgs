
import java.util.LinkedList;


public class Board {
    private final int[][] boardTitles;
    private final int dim;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        boardTitles = new int[tiles.length][tiles.length];
        this.dim = tiles.length;
        for(int i=0;i<tiles.length;i++){
            for(int j=0;j<tiles.length;j++){
                boardTitles[i][j] = tiles[i][j];
            }
        }
    }
    private int[][] calcRealIndexes(){
        int[][] realIndexes = new int[this.dim*this.dim][2];
        int rowIndex = 0;
        for(int i=0;i<this.dim;i++){
            int colIndex = 0;
            for(int j=0;j<this.dim;j++){
                int index = i*this.dim + j;
                realIndexes[index][0] = rowIndex;
                realIndexes[index][1] = colIndex;
                colIndex+=1;
            }
            rowIndex+=1;
        }
        return realIndexes;
    }
    // string representation of this board
    public String toString(){
        String result = dim+"\n";
        for(int i=0;i<dim;i++){
            StringBuilder prom = new StringBuilder();
            for(int j=0;j<dim;j++){
                prom.append(boardTitles[i][j]).append(" ");
            }
            prom.deleteCharAt(prom.length() - 1).append("\n");
            result+=prom;
        }
        return result;
    }

    // board dimension n
    public int dimension(){
        return this.dim;
    }

    // number of tiles out of place
    public int hamming(){
        int counter = 0;
        for(int i = 0;i<this.dim;i++){
            for(int j=0;j<this.dim;j++){
                if(this.boardTitles[i][j]!=0) {
                    if(this.boardTitles[i][j]!=i*this.dim+j+1) counter++;
                }
            }
        }
        return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int result = 0;
        int[][] realIndexes = calcRealIndexes();
        for(int i=0;i<this.dim;i++){
            for(int j=0;j<this.dim;j++){
                if(this.boardTitles[i][j]!=0 && this.boardTitles[i][j]!=i*this.dim+j+1){
                    result += Math.abs(realIndexes[this.boardTitles[i][j] - 1][0] - i) + Math.abs(realIndexes[this.boardTitles[i][j] - 1][1] - j);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return this.manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y==null)return false;
        if(!(y instanceof Board)) return false;
        Board secondBoard = (Board) y;
        if(this.dim!=secondBoard.dim) return false;
        for(int i=0;i<this.dim;i++){
            for(int j=0;j<this.dim;j++){
                if(this.boardTitles[i][j]!=secondBoard.boardTitles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        LinkedList<Board> boards = new LinkedList<>();

        int zeroRow = 0;
        int zeroCol = 0;
        for(int i=0;i<this.dim;i++){
            for(int j=0;j<this.dim;j++){
                if(this.boardTitles[i][j]==0){
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        if(zeroRow>0){
            swap(zeroRow,zeroCol,zeroRow-1,zeroCol);
            Board newBoard = new Board(this.boardTitles);
            swap(zeroRow-1,zeroCol,zeroRow,zeroCol);
            boards.add(newBoard);
        }
        if(zeroRow<this.dim - 1){
            swap(zeroRow,zeroCol,zeroRow+1,zeroCol);
            Board newBoard = new Board(this.boardTitles);
            swap(zeroRow+1,zeroCol,zeroRow,zeroCol);
            boards.add(newBoard);
        }
        if(zeroCol>0){
            swap(zeroRow,zeroCol,zeroRow,zeroCol - 1);
            Board newBoard = new Board(this.boardTitles);
            swap(zeroRow,zeroCol - 1,zeroRow,zeroCol);
            boards.add(newBoard);
        }
        if(zeroCol< this.dim - 1){
            swap(zeroRow,zeroCol,zeroRow,zeroCol + 1);
            Board newBoard = new Board(this.boardTitles);
            swap(zeroRow,zeroCol + 1,zeroRow,zeroCol);
            boards.add(newBoard);
        }
        return boards;
    }
    private void swap(int firstRow,int firstCol,int secondRow,int secondCol){
        int prom = this.boardTitles[firstRow][firstCol];
        this.boardTitles[firstRow][firstCol] = this.boardTitles[secondRow][secondCol];
        this.boardTitles[secondRow][secondCol] = prom;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int firstIndexRow = 0,firstIndexCol = 0;
        int secondIndexRow = 0,secondIndexCol = 0;
        for(int i=0;i<this.dim;i++){
            for(int j=0;j<this.dim;j++){
                if(this.boardTitles[i][j]!=0){
                    firstIndexRow = i;
                    firstIndexCol = j;
                }
            }
        }
        for(int i=this.dim - 1;i>=0;i--){
            for(int j=this.dim - 1;j>=0;j--){
                if(this.boardTitles[i][j]!=0){
                    secondIndexRow = i;
                    secondIndexCol = j;
                }
            }
        }
        Board newBoard = new Board(this.boardTitles);
        newBoard.swap(firstIndexRow,firstIndexCol,secondIndexRow,secondIndexCol);
        return newBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] matrix = new int[3][3];
        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 3;
        matrix[1][0] = 4;
        matrix[1][1] = 2;
        matrix[1][2] = 5;
        matrix[2][0] = 7;
        matrix[2][1] = 8;
        matrix[2][2] = 6;
        Board b = new Board(matrix);
        Board a = new Board(matrix);
        LinkedList<Board> pr = new LinkedList<>();
        pr.add(b);
        System.out.println(pr.contains(a));
        System.out.println(b);
        System.out.println(b.hamming());
        //System.out.println(b.manhattan());
        //System.out.println(b.neighbors());
        //System.out.println(b.twin());
    }
}
