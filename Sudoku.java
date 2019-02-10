import java.util.Random;

public class Sudoku {

  /*
   * Final static variables that hold row and column
   */
  private static final int ROW = 9;
  private static final int COL = 9;
  private static final int size = 9;

  /*
   * Array that stores a number in the sudoku map
   */
  private int[][] board;

  /*
   * Initializes the sudoku board to 9x9
   */
  public Sudoku() {
    board = new int[9][9];
  }

  /*
   * Prints the layout of the board as well as the numbers stored inside the board
   */
  public void printBoard() {
    for (int i = 0; i < ROW; i++) {
      if (i % 3 == 0) {
        System.out.println("  ---------------------------");
      }

      for (int j = 0; j < COL; j++) {
        if (j % 3 == 0) {
          System.out.print("|");
        }
        System.out.print(" " + board[i][j] + " ");
      }
      System.out.println("|");
    }
    System.out.println("  ---------------------------");
  }

  /*
   * Fills out the board by inserting random numbers
   * Places a number on a board using random (use recursion)
   */
  public boolean fillBoard() {
    Random random = new Random();
    int randNum = random.nextInt(9) + 1; // 1 - 9 are the valid numbers
    
    for (int row = 0; row < ROW; row++) {
      for (int col = 0; col < COL; col++) {
        if (board[row][col] == 0) {
          for (int num = randNum, count = 0; count < 10; num++, count++) {
            
            num = num % 10;     // keep num within 1 - 9 (avoid 0 on the bottom if statement)
            
            if (num == 0) {
              num++;    // avoid making num = 0
            }

            // Checks if the value chosen is valid
            if (isValidNumber(num, row, col)) {
              board[row][col] = num;
              
              if (fillBoard()) {    // check the next column (uses the if statement)
                return true;
              } else {
                board[row][col] = 0;    // backtrack
              }
            }
          }
          return false;     // go back to the previous column
        }
      }
    }
    return true;    // sudoku finished generating
  }

  /*
   * Checks if there is no duplicate number on the rows
   * 
   * @return false if it there is a similar number in the row
   */
  private boolean notValidRow(int num, int col) {
    for (int i = 0; i < ROW; i++) {
      if (board[i][col] == num) {
        return false;
      }
    }

    return true;
  }

  /*
   * Checks if there is no duplicate number on the columns
   * 
   * @return false if it there is a similar number in the col
   */
  private boolean notValidCol(int num, int row) {
    for (int i = 0; i < COL; i++) {
      if (board[row][i] == num) {
        return false;
      }
    }

    return true;
  }

  /*
   * Checks if the 3x3 block contains the duplicate number
   * 
   * @return false if it there is a similar number in the 3x3 block
   */
  private boolean checkBlock(int num, int row, int col) {
    row = (row / 3) * 3;
    col = (col / 3) * 3;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[row + i][col + j] == num) {
          return false;
        }
      }
    }

    return true;
  }

  /*
   * Checks that there are no zeros in any of the arrays of the board
   */
  private boolean isValidNumber(int num, int row, int col) {
    return (notValidRow(num, col) && notValidCol(num, row) && checkBlock(num, row, col));
  }

  /*
   * Main function that will create the Sudoku object and run the functions
   */
  public static void main(String[] args) {
    Sudoku sudoku = new Sudoku();

    if (sudoku.fillBoard())
      sudoku.printBoard();
    else
      System.out.println("error");
  }
}
