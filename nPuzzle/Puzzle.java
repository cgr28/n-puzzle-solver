package nPuzzle;
import java.util.*;


public class Puzzle {

    int[][] board;
    int emptyRow; // the x of the empty spot in the board
    int emptyCol; // the y of the empty spot in the board

    public enum Move {
        LEFT,
        RIGHT,
        UP,
        DOWN;
    }

    public Puzzle(int[][] board) {
        this.board = board;
        this.emptyRow = this.findEmpty()[0];
        this.emptyCol = this.findEmpty()[1];
    }

    public int[] findEmpty() {
        int row = -1;
        int col = -1;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == 0) {
                    return new int[] { i, j };
                }
            }
        }
        return new int[] { row, col };
    }

    private void swapTiles(int x1, int y1, int x2, int y2) {
        int temp = this.board[x1][y1];
        this.board[x1][y1] = this.board[x2][y2];
        this.board[x2][y2] = temp;
    }

    private boolean canMove(int row, int col) {
        if (row < 0 || row > this.board.length-1) {
            // System.out.println("row");
            return false;
        }

        if (col < 0 || col > this.board[0].length-1) {
            // System.out.println("this.board[0].length: " + (this.board[0].length-1));
            // System.out.println("col: " + col);
            return false;
        }

        return true;
    }

    private boolean moveLeft() {
        int tempCol = this.emptyCol + 1;
        if (!canMove(this.emptyRow, tempCol)) {
            return false;
        }

        swapTiles(this.emptyRow, this.emptyCol, this.emptyRow, tempCol);
        this.emptyCol = tempCol;
        return true;
    }

    private boolean moveRight() {
        int tempCol = this.emptyCol - 1;
        if (!canMove(this.emptyRow, tempCol)) {
            return false;
        }

        swapTiles(this.emptyRow, this.emptyCol, this.emptyRow, tempCol);
        this.emptyCol = tempCol;
        return true;
    }

    private boolean moveUp() {
        int tempRow = this.emptyRow + 1;
        if (!canMove(tempRow, this.emptyCol)) {
            return false;
        }

        swapTiles(this.emptyRow, this.emptyCol, tempRow, this.emptyCol);
        this.emptyRow = tempRow;
        return true;
    }

    private boolean moveDown() {
        int tempRow = this.emptyRow - 1;
        if (!canMove(tempRow, this.emptyCol)) {
            return false;
        }

        swapTiles(this.emptyRow, this.emptyCol, tempRow, this.emptyCol);
        this.emptyRow = tempRow;
        return true;
    }

    public boolean moveTile(Move move) {
        if (move == Move.LEFT) {
            return moveLeft();
        } else if (move == Move.RIGHT) {
            return moveRight();
        } else if (move == Move.UP) {
            return moveUp();
        } else if (move == Move.DOWN) {
            return moveDown();
        }
        return false;
    }

    public void shuffle(int n) {
        Random rand = new Random();
        int ind = 0;
        ArrayList<Move> moves = new ArrayList<Move>();
        boolean moved = false;
        for (int i = 0; i < n; i++) {
            moved = false;
            moves.add(Move.RIGHT);
            moves.add(Move.LEFT);
            moves.add(Move.UP);
            moves.add(Move.DOWN);
            while (!moved) {
                if (moves.size() == 0) {
                    return;
                }
                ind = rand.nextInt(moves.size());
                Move move = moves.remove(ind);
                moved = this.moveTile(move);
            }
            moves.clear();
        }

    }

    public String toString() {
        String puzzleRep = "";

        for (int i = 0; i < this.board.length; i++) {
            if (i > 0) {
                puzzleRep += "\n";
            }
            for (int j = 0; j < this.board[i].length; j++) {
                puzzleRep += (this.board[i][j] + "\t"); 
            }
        }

        return puzzleRep;
    }

    public static void main(String[] args) {
        int[][] BOARD = {{1, 2, 3, 4},
                         {5, 6, 7, 8}, 
                         {9, 10, 11, 12},
                         {13, 14, 15, 0}};

        Puzzle puzzle = new Puzzle(BOARD);
        System.out.println(puzzle.toString());
        System.out.println(puzzle.emptyRow + " " + puzzle .emptyCol);
        System.out.println();
        puzzle.moveTile(Move.RIGHT);
        System.out.println(puzzle.emptyRow + " " + puzzle .emptyCol);
        puzzle.moveTile(Move.RIGHT);
        System.out.println(puzzle.emptyRow + " " + puzzle .emptyCol);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.LEFT);
        puzzle.moveTile(Move.LEFT);
        puzzle.moveTile(Move.LEFT);
        puzzle.moveTile(Move.LEFT);
        puzzle.moveTile(Move.DOWN);
        puzzle.moveTile(Move.DOWN);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.RIGHT);
        puzzle.moveTile(Move.DOWN);
        System.out.println(puzzle.toString());
        System.out.println(puzzle.emptyRow + " " + puzzle .emptyCol);
    }
    
}