/** COMP 1006/1406
  * Summer 2017
  *
  * Assignment 1
  */

import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Random;
//testing



public class TicTacToeGame{
  /** symbol for X */
  public static final Character ex = 'X';

  /** symbol for O */
  public static final Character oh = 'O';

  /** symbol for empty grid element */
  public static final Character empty = ' ';

  /** board is the grid that the game is played on;
    * each element must be one of ex, oh or empty*/
  public static Character[][] board;


  /** rows is the number of rows (N) in the game board */
  public static int rows;

  /** columns is the number of columns (M) in the game board */
  public static int columns;

  /** win_condition is the value of K, the winning condition of the game */
  public static int win_condition;

  /** specifies if the game is 1p (human-human) or 2p (human-computer) */
  public static boolean human_human_game;

  public static boolean computerTurn;

  public static int[] gameData;

  public static int currentPlayer;

  public static int playCount;

  public static boolean draw;

  public static int testCount = 0;


  /** Checks for a win based on the last symbol played in the game
   *
   * It is assumed that the position specified by the last_row_played
   * and last_column_played is valid and that the symbol in the board
   * at that position is not empty. (It must be <em>ex</em> or <em>oh</em>)
   *
   * @param last_row_played is the row of the last symbol played
   * @param last_column_played is the column of the last symbol played
   * @return the length of the winning row/column/diagonal of symbols
   * if the last move is a winning move, or -1 if the last move is not
   * a winning move.
   */
  public static int win(int last_row_played, int last_column_played){
    char player = board[last_row_played][last_column_played];
    int diagCheck = 0;
/////////////////////////////////////////////////////////////////////////
    //check column
    for (int c = 0; c < columns; c++) {
      if (board[last_row_played][c] != player) {
        break;
      }
      if ((c == win_condition-1)&&(player == ex)) {
        System.out.println("Player one wins!");
        stats(1,1);
        gameOver();
        return win_condition;
      }
      if ((c == win_condition-1)&&(player == oh)) {
        System.out.println("Player two wins!");
        stats(1,2);
        gameOver();
        return win_condition;
      }
    }
/////////////////////////////////////////////////////////////////////////
    //check row
    for (int r = 0; r < rows; r++) {
      if (board[r][last_column_played]!= player) {
        break;
      }
      if ((r == win_condition-1)&&(player == ex)) {
        System.out.println("Player one wins!");
        stats(1,1);
        gameOver();
        return win_condition;
      }
      if ((r == win_condition-1)&&(player == oh)) {
        System.out.println("Player two wins!");
        stats(1,2);
        gameOver();
        return win_condition;
      }

    }
/////////////////////////////////////////////////////////////////////////
//diagonal

    diagCheck = diagonalCheck(last_row_played,last_column_played);
    if (diagCheck == 1) {
      gameData[3] += 1;
      stats(1,currentPlayer);
      gameOver();
      return -1;
    }

/////////////////////////////////////////////////////////////////////////
    if (playCount == (rows*columns)-1) {
      gameData[3] += 1;
      System.out.println("Draw!");
      gameOver();
      return -1;
    }

    gameSwitch();
    return -1;
  }

  /** main method to run the game
    *
    * @param args optional command line arguments to
    * specify if the game is 1p (human-computer) or
    * 2p (human-human). Expect the string "2p" if any
    * command line argument is given.
    */
  public static void main(String[] args){
    /*------------------------------------------
     * handle command line arguments if any
     * to determine if the game is human-human
     * or human-computer
     *------------------------------------------*/

     //game switch
    if( args.length > 0){
      /* there are commend line arguments present */
      human_human_game = true;
      System.out.println("This is a 2 player game.");
    } else {
      /* there are no command line argument present */
      // add your code here
      human_human_game = false;
      System.out.println("This is a computer game.");
    }



    /*------------------------------------
     * read N-M-K data from init file
       N = rows
       M = columns
       K = win_condition
     *------------------------------------*/

    /*-------------------------------------
     *-------------------------------------
     * BEGIN : Do NOT change the code here
     *-------------------------------------*/
    BufferedReader file_input;
    FileReader     file;
    String         file_name = "init";
    String         line;

    try{
      file            = new FileReader(file_name);
      file_input      = new BufferedReader(file);

      line            = file_input.readLine();
      rows            = Integer.parseInt(line);

      line            = file_input.readLine();
      columns         = Integer.parseInt(line);

      line            = file_input.readLine();
      win_condition   = Integer.parseInt(line);

      /* always close your files you are done with them! */
      file_input.close();

    }catch(Exception e){
      /* somethine went wrong! */
      System.err.println("Failure to read data from init file properly");
      System.err.println(e);
      System.err.println("Program ending");
      return;
    }

    /*-------------------------------------
     * END : Do NOT change the code here
     *-------------------------------------
     *-------------------------------------*/


    /* create and initialize the game board */

    /* allocate memory for the board array */
    board     = new Character[rows][columns];
    gameData  = new int[5];

    /** game data array
       0 : games played
       1 : player one wins
       2 : opponent wins
       3 : draws
       4 : best win
    */
    gameData[0] = 0;
    gameData[1] = 0;
    gameData[2] = 0;
    gameData[3] = 0;
    gameData[4] = 0;
    /* code to drive the game */

    // add your code here
    gameInit();
    //enter move message
    ///////////TESTING////////////
  }
//////////////////////////////////METHODS//////////////////////////////////

  //method for diagonal check
  public static int diagonalCheck(int row, int col) {
    int upRight   = 0;
    int downRight = 0;
    int upLeft    = 0;
    int downLeft  = 0;
    int diagCount = 0;
    while(diagCount < win_condition) {
      //upRight
      try {
        if (board[row][col] == board[row+diagCount][col+diagCount]) {
          upRight += 1;
        }
      } catch (Exception ur) {
        //
      }

      //downRight
      try {
        if (board[row][col] == board[row-diagCount][col+diagCount]) {
          downRight += 1;
        }
      } catch (Exception dr) {
        //
      }

      //upLeft
      try {
        if (board[row][col] == board[row+diagCount][col-diagCount]) {
          upLeft += 1;
        }
      } catch (Exception ul) {
        //
      }

      //downLeft
      try {
        if (board[row][col] == board[row-diagCount][col-diagCount]) {
          downLeft += 1;
        }
      } catch (Exception dl) {
        //
      }
      diagCount += 1;
    }
    if (
    (upRight == win_condition) ||
    (downRight == win_condition) ||
    (upLeft == win_condition) ||
    (downLeft == win_condition)) {

      System.out.println("Diagonal Win! at : "+row +""+ col);
      return 1;
    }
    return 0;
  }

  //game over display stats
  public static void gameOver() {
    Scanner keyboard;
    keyboard = new Scanner(System.in);


    String game;
    gameData[0] += 1;
    System.out.println("Game Over!");
    System.out.println("Total games played: "+gameData[0]);
    System.out.println("Player one wins: "+gameData[1]);
    System.out.println("Opponent wins: "+gameData[2]);
    System.out.println("Draws: "+gameData[3]);
    System.out.println("Best win: "+gameData[4]);
    System.out.println("Would you like to play again?");
    //game = keyboard.next();
    testCount += 1;
    if (testCount < 1) {
      gameInit();
    }

    /*testing
    if (game.equals("yes")) {
      gameInit();
    }
    */
  }


  //computer move
  public static void computerMove() {
    System.out.println("Starting computer move");
    Random rand = new Random();
    int row;
    int col;
    boolean played = true;
    do {
      row = rand.nextInt(rows);
      col = rand.nextInt(columns);
      if (board[row][col] == empty) {
        System.out.println(row+""+col);
        played = false;
        addToBoard(row,col);
        break;
      }
    } while (played);
  }

  //method to initialize game
  public static void gameInit() {
    playCount = 0;
    currentPlayer = 1;
    clearBoard();
    humanMove();
  }

  //method to control game flow path
  public static void gameSwitch() {
    playCount += 1;
    if ((human_human_game == false) && (currentPlayer == 1)) {
      currentPlayer = 3;
      computerMove();
      return;
    }
    if ((human_human_game == true) && (currentPlayer == 1)) {
      currentPlayer = 2;
      humanMove();
      return;
    }
    if (currentPlayer != 1) {
      currentPlayer = 1;
      /*testing mode
      humanMove();
      */
      computerMove();
      return;
    }
  }

  //method to update game statistics
  public static void stats(int end, int playerWon) {

    if (playerWon == 1) {
      gameData[1] += 1;
    } else {
        gameData[2] += 1;
    }

    if ((end == 1)&&(gameData[4] < win_condition)) {
      gameData[4] = win_condition;
    }
  }

  //method for converting string input to good format
  public static String textChange(String i) {
    i = i.toLowerCase();
    i = i.trim();
    return i;
  }

  //method to get move input
  public static void humanMove() {
    /* testing */
    computerMove();
    return;
    /*
    Scanner keyboard;
    keyboard = new Scanner(System.in);
    int row;
    int col;
    int swap;
    String a;
    String b;
    System.out.print("Player "+currentPlayer+" move : ");
    a = keyboard.next();
    a = textChange(a);
    //check for end game command
    if (a.equals("end")) {
      System.exit(0);
    }
    row = keyboard.nextInt();
    b = keyboard.next();
    b = textChange(b);
    col = keyboard.nextInt();
    //checks for valid inputs and swaps value if needed
    while (true) {
      if (((b.equals("r"))||(b.equals("row"))) && ((a.equals("c"))||(a.equals("col"))||(a.equals("column")))) {
        swap = row;
        row = col;
        col = swap;
        addToBoard(row, col);
        computerTurn = true;
        break;
      } else if (((a.equals("r"))||(a.equals("row"))) && ((b.equals("c"))||(b.equals("col"))||(b.equals("column")))) {
          row = row;
          col = col;
          addToBoard(row, col);
          computerTurn = true;
          break;
      } else {
          System.out.println("Invalid input!");
          humanMove();
          break;
      }
    }
    */
  }

  //method to display the board
  public static void drawBoard() {
    System.out.println(playCount);
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        System.out.print("|" + board[r][c] );
        if (c == (columns-1)) {
          System.out.print("|");
          System.out.print("\n");
        }
      }
    }
    /*
    for (int y = 0;y < rows; y++) {
      System.out.print("|");
      for (int z = 0;z < columns; z++) {
        System.out.print(board[y][z] + "|");
        if (z == 9) {
          System.out.print("\n");
        }
      }
    }
    */
  }

  //method to clear board
  public static void clearBoard() {
    for (int i = 0; i < rows; i++) {
      for (int x = 0; x < columns; x++) {
        board[i][x] = empty;
      }
    }
  }

  //method to add to board
  public static void addToBoard(int row, int col) {
    if (board[row][col] == empty) {
      if (currentPlayer == 1) {
        board[row][col] = ex;
      } else {
          board[row][col] = oh;
      }
      drawBoard();
      if (playCount > (win_condition*2)-1) {
        win(row,col);
      } else {
        gameSwitch();
      }
    } else {
        System.out.println("Already taken!");
        humanMove();
    }
  }
}
