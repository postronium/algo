package s15;

//=============================================================

import com.sun.glass.ui.Size;

public class Queens {

    private static final int SIZE = 6;

    public interface QueenBoard {
        /**
         * true if that (typically free) cell is already threatened
         */
        public boolean isSquareAttacked(int row, int col);

        public void putQueen(int row, int col);

        public void removeQueen(int row, int col);

        public int boardSize();
    }

    //=============================================================
    public static void main(String[] args) {
        boolean WITH_GUI = true;      // TODO: toggle when measuring performances ;)
        int size = SIZE;
        if (args.length != 0) size = Integer.parseInt(args[0]);
        if (WITH_GUI) {
            int slowdownMs = 700;
            QueensGui.main(new String[]{"" + size, "" + slowdownMs});
        } else {
            solve(size);
        }
    }

    public static void solve(int n) {
        QueenBoard b = new QueenBoardBasic(n);
        if (isSolvable(b))
            System.out.println("Found !\n" + b);
        else
            System.out.println("Not found !");
    }

    // ------------------------------------------------------------
    public static boolean isSolvable(QueenBoard b) {
        // b is an 'inout' parameter
        return isSolvableFromColumn(0, b);
    }
    // ------------------------------------------------------------

    // If a solution is found, returns true and gives the solution
    // in board. Otherwise, returns false and keeps board as it was received.
    public static boolean isSolvableFromColumn(int col,
            /* inout */  QueenBoard board) {

        if (col == SIZE)
            return true;

        for (int i = 0; i < SIZE; i++) {
            if (!board.isSquareAttacked(i, col)) {
                board.putQueen(i, col);
                if (isSolvableFromColumn(col+1, board)) {
                    return true;
                }
                board.removeQueen(i, col);
            }
        }
        return false;

    }

    //=============================================================
    static class QueenBoardBasic implements QueenBoard {
        private final boolean[][] grid;
        private final int[] inRow;
        private final int[] inCol;
        private final int[] inDiagonal1;
        private final int[] inDiagonal2;
        private final int size;

        public QueenBoardBasic(int dim) {
            size = dim;
            grid = new boolean[size][size];
            inRow = new int[size];
            inCol = new int[size];
            inDiagonal1 = new int[2 * size - 1];
            inDiagonal2 = new int[2 * size - 1];
        }

        public int boardSize() {
            return size;
        }

        public void putQueen(int row, int col) {
            if (grid[row][col]) throw new IllegalArgumentException("one queen is already there...");
            grid[row][col] = true;
            inRow[row]++;
            inCol[col]++;
            inDiagonal1[row + col]++;
            inDiagonal2[row - col + size - 1]++;
        }

        public void removeQueen(int row, int col) {
            if (!grid[row][col]) throw new IllegalArgumentException("no queen is there...");
            grid[row][col] = false;
            inRow[row]--;
            inCol[col]--;
            inDiagonal1[row + col]--;
            inDiagonal2[row - col + size - 1]--;
        }

        public boolean isSquareAttacked(int row, int col) {
            return inRow[row] > 0
                    || inCol[col] > 0
                    || inDiagonal1[row + col] > 0
                    || inDiagonal2[row - col + size - 1] > 0;
        }

        public String toString() {
            String res = "";
            for (int i = 0; i < size; i++, res += "\n")
                for (int j = 0; j < size; j++)
                    res += (grid[i][j]) ? "X" : "-";
            return res;
        }

        public boolean isQueenThere(int row, int col) {
            return grid[row][col];
        }
    }
}
