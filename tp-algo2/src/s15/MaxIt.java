package s15;
import java.util.Random;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Stack;

public class MaxIt {
  //======================================================================
  static class Move {
    private int     row; 
    private int     col; 
    private boolean isPlayerA;

    public Move() {}

    public Move(int row, int col, boolean isPlayerA) {
      this.row = row; 
      this.col = col; 
      this.isPlayerA = isPlayerA;
    }

    public void assign(Move m) {
      row = m.row(); col = m.col(); 
      isPlayerA = m.isPlayerA();
    }

    public int row() {
      return row;
    }
    public int col() {
      return col;
    }
    public boolean isPlayerA() {
      return isPlayerA;
    }
  }
  //======================================================================
  static class Board {
    protected int    [][] grid;
    protected boolean[][] isUsed;
    protected int        pointsOfA = 0;
    protected int        pointsOfB = 0;
    protected int        crtRow    = 0;
    protected int        crtCol    = 0;
    protected int        usedCells = 0;
    private Stack<Move>  moves     = new Stack<Move>();

    public Board(int dimension) {
      grid   = new int    [dimension][dimension];
      isUsed = new boolean[dimension][dimension];
    }

    public int dimension() {return grid.length;}

    public boolean isValidMove(Move m) {
      if (m.col()<0 || m.col()>grid.length-1)          return false;
      if (m.row()<0 || m.row()>grid[m.col()].length-1) return false;
      if (isUsed[m.col()][m.row()])                    return false;
      if (usedCells == 0) return true;
      return (m.col() == crtCol)  || (m.row() == crtRow);
    }

    public Move[] possibleMoves(boolean isPlayerA) {
      Move [] res; 
      int i=0; int j=0; int n=0;
      Stack<Move> s = new Stack<Move>();
      Move m;

      for (i=0; i<grid.length; i++) {
        m = new Move(i, crtCol, isPlayerA);
        if (isValidMove(m)) {
          n++;
          s.push(m);
        }
      }
      for (j=0; j<grid[0].length; j++) {
        m = new Move(crtRow, j, isPlayerA);
        if (isValidMove(m)) {
          n++;
          s.push(m);
        }
      }
      res = new Move[n];
      while (!s.empty()) {
        res[--n] = (Move)(s.pop());
      }
      return res;
    }

    public void play(Move m) {
      moves.push(m);
      usedCells ++;
      isUsed[m.col()][m.row()] = true;
      if (m.isPlayerA())
        pointsOfA += grid[m.col()][m.row()];
      else
        pointsOfB += grid[m.col()][m.row()];
      crtCol = m.col(); crtRow = m.row();
    }

    public void undo() {
      // TODO - A COMPLETER
    }

    public int score() {
      return pointsOfA - pointsOfB;
    }

    public boolean isGameOver() {
      return possibleMoves(false).length == 0;
    }

    public String toString() {
      String res = "";
      for (int i=0; i<grid.length; i++) {
        for (int j=0; j<grid[i].length; j++) {
          if (isUsed[i][j]) {
            if (i==crtCol && j==crtRow) {
              res += " !!";
            } else {
              res += " --";
            }
          } else {
            res += " " +grid[i][j];
          }
        }
        res += "\n";
      }
      res += "          A: "+pointsOfA+",     B:"+pointsOfB ;
      res += "\n";
      return res;
    }

    public static Board rndBoard(int dim) {
      Board b = new Board(dim);
      for (int i=0; i<dim; i++) 
        for (int j=0; j<dim; j++) {
          b.grid[i][j] = rnd.nextInt(90)+10; 
        }
      return b;
    } 

    public static Board rndBoard(int dim, int seed) {
      rnd = new Random(seed);
      return rndBoard(dim);
    }
  }
  //======================================================================

  public static Move readMove(Board b, boolean isPlayerA) {
    Move m = null;
    try {
      do {
        m = enterMove(isPlayerA);
      } while (! b.isValidMove(m));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return m;
  }

  private static Move enterMove(boolean isPlayerA) throws IOException {
    BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
    System.out.print(isPlayerA?"PlayerA":"PlayerB");
    System.out.print(", enter row and column: ");
    String s = is.readLine();
    StringTokenizer st = new StringTokenizer(s);
    int row = Integer.parseInt(st.nextToken());
    int col = Integer.parseInt(st.nextToken());
    return new Move(row, col, isPlayerA);
  }

  public static Move rndMove(Board board, boolean isPlayerA) {
    Move [] mt = board.possibleMoves(isPlayerA);
    int i = rnd.nextInt(mt.length);
    return mt[i];
  }

  public static Move greedyMove(Board board, boolean isPlayerA) {
    Move [] mt = board.possibleMoves(isPlayerA);
    Move bestMove=mt[0];
    int best=board.grid[bestMove.col()][bestMove.row()];
    for (Move m:mt) {
      int x = board.grid[m.col()][m.row()];
      if (x>best) {
        best=x; 
        bestMove=m;
      }
    }
    return bestMove;
  }

  static Move bestMove(Board board, boolean isPlayerA, int levels) {
    Move decision = new Move();
    // unused returned value
    expectedScore(board, isPlayerA, levels, decision); 
    return decision;
  }
  // ------------------------------------------------------------
  // "decision" is an output parameter, giving back the move that 
  // has been chosen by the minimax algorithm, limited in a 
  // computation depth of "levels".
  static int expectedScore(Board board,  boolean isPlayerA, 
                                  int   levels, Move    decision) {
    // "terminal" configuration
    if (board.isGameOver())  
      return board.score();
    
    Move[] mt = board.possibleMoves(isPlayerA);
    // "quasi-terminal" configuration
    if (levels==0) {          
      decision.assign(mt[0]); // just to announce something (probably the 
      return board.score();   //      caller won't use it anyway!)
    }
    
 // TODO - A COMPLETER
    return 0;
  }

  static Move bestMoveAlphaBeta(Board board, boolean isPlayerA, int levels) {
    return null;  // TODO - A ADAPTER... 
  }

  public static void playGame(int levels, int boardSize) {
    Board   board      = Board.rndBoard(boardSize);
    boolean isPlayerA  = true;
    Move    move;
    long t1 = System.nanoTime();
    while (!board.isGameOver()) {
      // TODO: comment that line when testing performance!
      System.out.println(board); 
      if (isPlayerA) {
        //move = bestMoveAlphaBeta(board, isPlayerA, levels);
        move = rndMove(board, isPlayerA);
        //move = greedyMove(board, isPlayerA);
        //move = readMove(board, isPlayerA);
        //move = bestMove(board, isPlayerA, levels);
      } else {
        //move = readMove(board, isPlayerA);
        //move = rndMove(board, isPlayerA);
        //move = greedyMove(board, isPlayerA);
        //move = bestMove(board, isPlayerA, levels);
        move = bestMoveAlphaBeta(board, isPlayerA, levels);
      }
      board.play(move);
      isPlayerA = ! isPlayerA;
    }
    long t2 = System.nanoTime();
    System.out.println("total time: " + (t2-t1)/1000/1000 +" [ms]");
    System.out.println(board);
    System.out.println("Game over");    
  }
  // ------------------------------------------------------------
  private static Random rnd = new Random(12);
  
  // ------------------------------------------------------------
  public static void main(String[] args) {
    int levels    = 5;
    int boardSize = 6;
    if (args.length == 2) {
      levels    = Integer.parseInt(args[0]);
      boardSize = Integer.parseInt(args[1]);
    }
    playGame(levels, boardSize);
  }
  // ------------------------------------------------------------
  // Il n'y a pas de programme test. La stratégie miniMax (B) devrait gagner
  // nettement contre la version "random" (A). Voici ce que j'obtiens
  // avec level=5 et boardSize=6   ------>     A: 688,     B:1052
}
