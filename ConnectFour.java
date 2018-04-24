import java.util.Scanner;
/**
 * Simple simulation of a ConnectFour Game in console.
 */
public class ConnectFour{
  //global constants for number of rows, columns, and the win condition.
  final static int ROWS = 6;
  final static int COLUMNS = 7;
  final static int WIN_CONDITION = 4;

  public static void main(String[] args){
    //constants for max number of turns before board fills up, and players
    final int MAX_NUM_TURNS = ROWS * COLUMNS;
    final String COLOUR_1_SYMBOL = "Y";
    final String COLOUR_1 = "yellow";
    final String COLOUR_2_SYMBOL = "R";
    final String COLOUR_2 = "red";
    //create new empty board
    String[][] board = new String[ROWS][COLUMNS];
    //set counter for how many turns have been taken
    int numTurns = 0;
    //container for current player symbol
    String playerS;
    //container for current player colour
    String player;
    Scanner input = new Scanner(System.in);
    //player specified column to drop disk
    int col;
    //current board status
    String current;
    //loop condition
    boolean flag = false;
    //loop asking for input
    while(!flag){
      //set current player (even is P1, odd is P2)
      if(numTurns % 2 == 0){
        playerS = COLOUR_1_SYMBOL;
        player = COLOUR_1;
      }//end if
      else{
        playerS = COLOUR_2_SYMBOL;
        player = COLOUR_2;
      }//end else
      //prompt player for input on console
      System.out.println("Select an empty column (0-" + (COLUMNS-1) + ") to drop a " + player + " disk into:");
      //if input is int value
      if(input.hasNextInt()){
        //set col to input
        col = input.nextInt();
        //if col value is valid
        if(col >-1 && col < COLUMNS){
          //check if board is full at that column
          if(checkFull(board, col)){
            //inform player that the board is full
            System.out.println("The column is full.");
          }//end if
          //else board is not full
          else{
            //call updateBoard (dropping disk into correct position) and store its value in current
            current = updateBoard(board,col, playerS);
            //print the board to console
            printBoard(board);
            //increase number of turns taken by one
            numTurns++;
            //if value in current is not continue
            if(current != ("continue")){
              //print the player's name (winner name)
              System.out.println("Congratulations, the " + player + " player wins!!!");
              //exit loop
              flag = true;
            }//end if
            //no winner produced from this turn
            else{
              //if number of turn is equal to the maximum turns allowed (board is full)
              if(numTurns == MAX_NUM_TURNS){
                //print to console that the game was a draw
                System.out.println("This game was a draw! Please play again.");
                //exit loop
                flag = true;
              }//end if
            }//end else
          }//end else
        }//end if
        //else number is out of range
        else{
          System.out.println("Sorry, you have entered an invalid column number.");
        }//end else
      }//end if
      //input was not an integer
      else{
        System.out.println("Sorry, you have entered an invalid column number.");
        //skip over current token
        input.next();
      }//end else
    }//end while loop
  }//end main

  /**
  * update the boardstate with current move from player and check for a win from said move.
  *
  * @param    board      Array representation of the current board state.
  * @param    col        Player specified column to drop new disk into.
  * @param    player     Current player that owns the disk.
  * @return   A string representing either the winner or "continue" if no winner.
  */
  public static String updateBoard(String[][] board, int col, String player){
    //set loop condition
    boolean flag = true;
    //set row counter from bottem up
    int m = ROWS-1;
    //loop over board checking each row at specified column
    while(flag && m>-1){
      //if spot in ixj (current row and specifed column) is empty
      if(board[m][col] == null){
        //set that slot to contain current player's disk
        board[m][col] = player;
        //exit loop
        flag = false;
      }//end if
      //else slot is filled, move in to next row
      else{
        m--;
      }//end else
    }//end while loop
    //call method checkWinAt and if it returns true:
    if(checkWinAt(board, m, col, player)){
      //return current player symbol
      return player;
    }//end if
    //else no winner thus return continue
    else{
      return "continue";
    }//end else
  }//end updateBoard

  /**
  * Check if column in board is full.
  *
  * @param    board      Array representation of the current board state.
  * @param    col        column to be checked.
  * @return   A boolean true if column is full and false otherwise.
  */
  public static boolean checkFull(String[][] board, int col){
    //if top row of board at coloum col is not empty
    if(board[0][col] != null){
      //return true
      return true;
    }//end if
    //else it is empty
    else{
      return false;
    }//end else
  }//end checkFull

  /**
  * Check for a win propagating from location ixj of current player.
  *
  * @param    board      Array representation of the current board state.
  * @param    i          Row location.
  * @param    j          Column location.
  * @param    player     Current player that owns the disk.
  * @return   A boolean true if win and false if not.
  */
  public static boolean checkWinAt(String[][] board, int i, int j, String player){
    //counter for vertical matches
    int counterI = WIN_CONDITION - 1;
    //counter for horizontal matches
    int counterJ = WIN_CONDITION - 1;
    //counter for diagonal up matches
    int counterDU = WIN_CONDITION - 1;
    //counter for diagonal down matches
    int counterDD = WIN_CONDITION - 1;
    //set loop counter to 1
    int x = 1;
    //loop while x is less than the wincon and each counter is greater than 0
    while(x < WIN_CONDITION && counterI>0 && counterJ>0 && counterDU>0 && counterDD>0){
      //if slot below the current position exists and is the same increment counter
      if(i+x<ROWS && board[i+x][j] == player){
        counterI--;
      }//end if
      //if the slot above current pos exists and is the same increment counter
      if(i-x>=0 && board[i-x][j] == player){
        counterI--;
      }//end if
      //check slot to the right
      if(j+x<COLUMNS && board[i][j+x] == player){
        counterJ--;
      }//end if
      //check left
      if(j-x>=0 && board[i][j-x] == player){
        counterJ--;
      }//end if
      //check diagonal
      if(i+x<ROWS && j+x<COLUMNS && board[i+x][j+x] == player){
        counterDD--;
      }//end if
      //check diagonal
      if(i-x>=0 && j+x<COLUMNS && board[i-x][j+x] == player){
        counterDU--;
      }//end if
      //check diagonal
      if(i+x<ROWS && j-x>=0 && board[i+x][j-x] == player){
        counterDU--;
      }//end if
      //check diagonal
      if(i-x>=0 && j-x>=0 && board[i-x][j-x] == player){
        counterDD--;
      }//end if
      x++;
    }//end while loop
    //if any counter is equal to 0 (thus 4 of the same disks in a row)
    if(counterI == 0 || counterJ == 0 || counterDU == 0 || counterDD == 0 ){
      return true;
    }//end if
    else{
      return false;
    }//end else
  }//end checkWinAt

  /**
  * Print a visual representation of the boardstate on console.
  *
  * @param    board      Array representation of the current board state.
  */
  public static void printBoard(String[][] board){
    //for each row in board
    for(int m=0;m<ROWS;m++){
      //print line for border of board
      System.out.print("|");
      //for each slot in each column of each row
      for(int n=0;n<COLUMNS;n++){
        //if slot is empty
        if(board[m][n] == null){
          //print a space
          System.out.print(" ");
        }//end if (chk empty)
        //else slot is not empty
        else{
          //print contents of slot
          System.out.print(board[m][n]);
        }//end else
        //print a closing border for the slot
        System.out.print("|");
      }//end inner for loop (column)
      //print a newline after each row
      System.out.println("");
    }//end outer for loop (row)
  }//end printBoard

}//end class
