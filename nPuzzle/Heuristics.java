package nPuzzle;
import java.lang.Math;
import java.util.HashMap;

public class Heuristics {
    
    public static int manhattanDistance(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }

    // CORRECT BOARD -> 1   2   3
    //                  4   5   6
    //                  8   9   0
    //                  
    public static int overallManhattanDistance(int[][] board) {
        HashMap<Integer, int[]> GOAL_BOARD = new HashMap<Integer, int[]>();
        int count = 1;
        int overall = 0;
        int goalRow;
        int goalCol;
        int FINAL_COUNT = board.length * board[0].length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (count == FINAL_COUNT) {
                    GOAL_BOARD.put(0, new int[] {i, j});
                }
                GOAL_BOARD.put(count++, new int[] {i, j});
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int key = board[i][j];
                int[] goal = GOAL_BOARD.get(key);
                goalRow = goal[0];
                goalCol = goal[1];
                overall += manhattanDistance(i, j, goalRow, goalCol);
            }
        }

        return overall;

    }

}
