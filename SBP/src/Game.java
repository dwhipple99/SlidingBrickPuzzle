/**
 * Created by dwhipple on 1/10/2016.
 *
 * This code is written for a course @ Drexel University, Introduction to AI, cs510
 *
 * It is intended to implement a Sliding Brick Puzzle
 */


import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

// This is the primary Class to represent the state of a game
//
public class Game {

    // A text name for a game.
    String gameName;
    // a board is w by h
    int w;   // Width of board
    int h;   // Height of board

    // The board
    int[][] board;

    String logSeverity="INFO";

    // Constructor
    public Game(String name, int width, int height) {

        gameName=name;
        w=width;
        h=height;
        System.out.print("Initializing Game \"");
        System.out.print(gameName);
        System.out.print("\"");
        System.out.print(", width=");
        System.out.print(w);
        System.out.print(", height=");
        System.out.print(h);
        System.out.println(".");

        }

    // Retrieve the width
    public String getName() { return gameName;}
    // Retrieve the width
    public int getWidth() { return w;}
    // Retrieve the height
    public int getHeight() { return h;}

    // Required for Assignment
    //
    public static boolean loadGameState(Game g1, String fileName) {

        String logInfo="Reading game state from "+fileName+".";

        log("INFO",logInfo);

        int tokenNumber=1;
        int currentRow=-1;
        int currentCol=0;
        String token;
        boolean debugFunction=false;

        try {
            File text=new File(fileName);
            Scanner scnr=new Scanner(text);

            scnr.useDelimiter(",");

            while (scnr.hasNext()) {
                token=scnr.next();
                token=token.replaceAll("\\s", "");
                switch (tokenNumber) {
                    case 1: g1.w=Integer.parseInt(token);
                        logInfo="Game Width="+g1.w;
                        //log("INFO", logInfo);
                        break;
                    case 2: g1.h=Integer.parseInt(token);
                        logInfo="Game Heigth="+g1.h;
                        //log("INFO", logInfo);
                        g1.board = new int[g1.w][g1.h];
                        break;
                    default:
                        if (((tokenNumber-3)%g1.w)==0) {
                            currentRow++;
                            currentCol=0;
                            if (debugFunction) System.out.println("Row="+currentRow);
                        }
                        if (debugFunction) System.out.println("Setting Col="+currentCol+", Row="+currentRow+"="+token);
                        g1.board[currentCol][currentRow]=Integer.parseInt(token);
                        if (debugFunction) System.out.println("Col="+currentCol+", token="+token);
                        currentCol++;
                    break;
                }
                tokenNumber++;
            }

            scnr.close();

        } catch(Exception e) {
            log("ERROR", "Error opening file, exiting");
        }
        return false;
    }

    // Required for Assignment
    //
    public static void outputGameState(Game g1) {

        String logInfo;
        logInfo="Dumping game \""+g1.getName()+"\" (Height="+g1.getHeight()+", Width="+g1.getWidth()+")";
        log("INFO", logInfo);
        System.out.println(g1.w+","+g1.h+",");
        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                System.out.print(g1.board[j][i]+",");
            }
            System.out.println();
        }
    }

    //  This function was not required for the assignment, just added for fun.
    //
    public static void prettyPrintGameState(Game g1) {
        System.out.println("Dumping game \""+g1.getName()+"\" (Height="+g1.getHeight()+", Width="+g1.getWidth()+")");
        //System.out.println(g1.w+","+g1.h+",");
        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                switch (g1.board[j][i]) {
                    case 1:
                        System.out.print("**");
                        break;
                    case 0:
                        System.out.print("  ");
                        break;
                    case -1:
                        System.out.print("--");
                        break;
                    case 2:
                        System.out.print("MM");
                        break;
                    default:
                        System.out.format("%2d", g1.board[j][i]);
                        break;
                }
            }
            System.out.println();
        }
    }

    // Required for Assignment
    //
    // This function clones the game state and returns a completely new game state
    //
    public static Game cloneGameState(Game g1){

        System.out.println("Cloning \""+g1.getName()+"\"");

        String newName;
        newName=g1.getName();

        Game clone = new Game(newName, 0,0);

        clone.w=g1.getWidth();
        clone.h=g1.getHeight();
        System.out.println("Clone \""+clone.getName()+"\" (Height="+clone.getHeight()+", Width="+clone.getWidth()+")");

        clone.board = new int[clone.w][clone.h];

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                clone.board[j][i]=g1.board[j][i];
            }
        }
        return clone;
    }

    // See if the game is solved
    // This is done by checking if if a -1 exists anywhere in the matrix
    // if so that means the puzzle is not solved
    public static boolean gameStateSolved(Game g1){

        boolean solved=true;

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==-1) {
                    solved=false;
                }
            }
        }
        return solved;
    }

    // Returns the Column number of a piece
    //
    // Built as a helper function
    public static int getColumn(Game g1, int piece){

        int column=0;

        if (piece < 2) {
            System.out.println("Error: Invalid piece number -- EXITING");
            System.exit(1);
        }

        //System.out.println("Board Width="+g1.getWidth());
        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==piece) {
                    column=j;
                    i=g1.getHeight();
                    j=g1.getWidth();
                }
            }
        }
        return column;
    }

    // Returns the Row number of a piece
    //
    // Built as a helper function
    public static int getRow(Game g1, int piece){

        int row=0;

        if (piece < 2) {
            System.out.println("Error: Invalid piece number -- EXITING");
            System.exit(1);
        }

        //System.out.println("Board Width="+g1.getWidth());
        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==piece) {
                    row=i;
                    i=g1.getHeight();
                    j=g1.getWidth();
                }
            }
        }
        return row;
    }

    // Returns the Width of a piece on the board
    //
    // Built as a helper function
    public static int pieceWidth(Game g1, int piece){

        int width=1;

        if (piece < 2) {
            System.out.println("Error: Invalid piece number -- EXITING");
            System.exit(1);
        }

        //System.out.println("Board Width="+g1.getWidth());
        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==piece) {
                    //System.out.println("i="+i+" j="+j+" width="+width);
                    //System.out.println("g1.board[j+width][i]="+g1.board[j+width][i]);
                    while(g1.board[j+width][i]==piece) {
                        width++;
                    }
                    // Force the loops to end
                    i=g1.getHeight();
                    j=g1.getWidth();
                }
            }
        }
        return width;
    }

    // Returns the Height of a piece on the board
    //
    // Built as a helper function
    public static int pieceHeight(Game g1, int piece){

        int height=1;

        if (piece < 2) {
            System.out.println("Error: Invalid piece number -- EXITING");
            System.exit(1);
        }

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==piece) {
                    //System.out.println("i="+i+" j="+j+" width="+width);
                    //System.out.println("g1.board[j+width][i]="+g1.board[j+width][i]);
                    while(g1.board[j][i+height]==piece) {
                        height++;
                    }
                    // Force the loops to end
                    i=g1.getHeight();
                    j=g1.getWidth();
                }
            }
        }
        return height;
    }

    //  Returns True if the piece can move UP
    //
    // Built as a helper function
    public static boolean canMoveUp(Game g1, int piece, int i, int j, List<Move> listOfMoves){
        int pWidth, pHeight;
        int curCol, curRow;

        pWidth=pieceWidth(g1,piece);
        pHeight=pieceHeight(g1, piece);

        curCol=i;
        curRow=j;

        // Check if can move up, entire width must be able to move up
        boolean canMoveUp=true;
        while ((pWidth >= 1) && (canMoveUp)) {
            if ((g1.board[curRow][curCol-1]!=0) && ((g1.board[curRow][curCol-1]!=-1) || piece!=2)) {
                canMoveUp=false;
            }
            curRow++;
            pWidth--;
        }
        if (canMoveUp) {
            Move myMove=new Move(piece, Move.Direction.UP);
            listOfMoves.add(myMove);
        }
        return canMoveUp;
    }

    //  Returns True if the piece can move DOWN
    //
    // Built as a helper function
    public static boolean canMoveDown(Game g1, int piece, int i, int j, List<Move> listOfMoves){
        int pWidth, pHeight;
        int curCol, curRow;

        pWidth=pieceWidth(g1,piece);
        pHeight=pieceHeight(g1, piece);

        curCol=i;
        curRow=j;

        // Check if can move up, entire width must be able to move up
        boolean canMoveDown=true;
        while ((pWidth >= 1) && (canMoveDown)) {
            if ((g1.board[curRow][curCol+1]!=0) && ((g1.board[curRow][curCol+1]!=-1) || piece!=2)) {
                canMoveDown=false;
            }
            curRow++;
            pWidth--;
        }
        if (canMoveDown) {
            Move myMove=new Move(piece, Move.Direction.DOWN);
            listOfMoves.add(myMove);
        }
        return canMoveDown;
    }

    //  Returns True if the piece can move LEFT
    //
    // Built as a helper function
    public static boolean canMoveLeft(Game g1, int piece, int i, int j, List<Move> listOfMoves){
        int pWidth, pHeight;
        int curCol, curRow;

        pWidth=pieceWidth(g1,piece);
        pHeight=pieceHeight(g1, piece);

        curCol=i;
        curRow=j;

        // Check if can move up, entire width must be able to move up
        boolean canMoveLeft=true;
        while ((pHeight >= 1) && (canMoveLeft)) {
            // If above is not empty
            //System.out.println("g1.board[curRow-1][curCol]="+g1.board[curRow-1][curCol]);
            if ((g1.board[curRow-1][curCol]!=0) && ((g1.board[curRow-1][curCol]!=-1) || piece!=2)){
                canMoveLeft=false;
            }
            curCol++;
            pHeight--;
        }
        if (canMoveLeft) {
            //System.out.println("canMoveLeft=true");
            Move myMove=new Move(piece, Move.Direction.LEFT);
            listOfMoves.add(myMove);
        }
        return canMoveLeft;
    }

    //  Returns True if the piece can move RIGHT
    //
    // Built as a helper function
    public static boolean canMoveRight(Game g1, int piece, int i, int j, List<Move> listOfMoves) {
        int pWidth, pHeight;
        int curCol, curRow;

        pWidth = pieceWidth(g1, piece);
        pHeight = pieceHeight(g1, piece);

        curCol = i;
        curRow = j;
        boolean canMoveRight = true;
        while ((pHeight >= 1) && (canMoveRight)) {
            // If above is not empty
            //System.out.println("g1.board[curRow+1][curCol+1]="+g1.board[curRow+1][curCol]);
            if ((g1.board[curRow+1][curCol] != 0) && ((g1.board[curRow+1][curCol] != 0) || piece!=2)) {
                canMoveRight = false;
            }
            curCol++;
            pHeight--;
        }
        if (canMoveRight) {
            //System.out.println("canMoveRight=true");
            Move myMove = new Move(piece, Move.Direction.RIGHT);
            listOfMoves.add(myMove);
        }
        return canMoveRight;
    }

    // Required for Assignment
    //
    // Returns a list of moves that a single piece can make
    public static void allMovesHelp(Game g1, int piece, List<Move> listOfMoves){

        int pWidth, pHeight;

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i] == piece) {
                    pWidth = pieceWidth(g1, piece);
                    pHeight = pieceHeight(g1, piece);

                    boolean moveUp = canMoveUp(g1, piece, i, j, listOfMoves);
                    boolean moveDown = canMoveDown(g1, piece, i, j, listOfMoves);
                    boolean moveLeft = canMoveLeft(g1, piece, i, j, listOfMoves);
                    boolean moveRight = canMoveRight(g1, piece, i, j, listOfMoves);

                    // Force the loops to end
                    i = g1.getHeight();
                    j = g1.getWidth();
                }
            }
        }
    }

    // Helper function for allMoves
    //
    // Returns TRUE if the piece number already has been searched for possible moves
    //
    public static boolean notYetSearched(Game g1,int piece, List<Move> listOfMoves) {

        boolean found=false;
        String logInfo;

        ListIterator<Move> itr=listOfMoves.listIterator();

        while (itr.hasNext())
        {
            Move newMove=itr.next();
            if (piece==newMove.getPiece()) {
                found=true;
                logInfo="Already searched for piece "+piece+", Found="+found;
                //log("INFO", logInfo);
            }
            //System.out.println("Move piece="+newMove.getPiece()+", Direction="+newMove.getDirection());
        }

        //System.out.println("Found="+found);
        return found;
    }

    // Required for Assignment
    //
    // Returns all possible moves on a board
    public static void allMoves(Game g1, List<Move> listOfMoves) {

        int piece;
        boolean found;
        String logInfo;

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                piece=g1.board[j][i];
                found=notYetSearched(g1, piece, listOfMoves);
                if ((g1.board[j][i]>1) && (!found)) {
                    piece=g1.board[j][i];
                    allMovesHelp(g1, piece, listOfMoves);
                    //logInfo="Checked moves for piece "+piece+", Total moves in list="+listOfMoves.size();
                    //log("INFO",logInfo);
                }
            }
        }
    }

    // Required for Assignment
    //
    // Actually applies a move to a board
    public static void applyMove(Game g1, Move m1) {
        int pWidth, pHeight;
        int column, row;
        int piece=m1.getPiece();
        String logInfo;

        pWidth=pieceWidth(g1,m1.getPiece());
        pHeight=pieceHeight(g1, m1.getPiece());

        column=getColumn(g1, m1.getPiece());
        row=getRow(g1, m1.getPiece());

        logInfo="Move piece is "+m1.getPiece()+", Move direction is "+m1.getDirection();
        log("INFO", logInfo);
        logInfo="Currently in column "+column+", row="+row;
        //log("INFO", logInfo);

        switch (m1.getDirection()) {
            case UP:
                for (int k=column;k<column+pWidth;k++) {
                    for (int i = row; i < row + pHeight; i++) {
                        g1.board[k][i - 1] = piece;
                        g1.board[k][i] = 0;
                    }
                }
                break;
            case DOWN:
                for (int k=column;k<column+pWidth;k++) {
                    for (int i = row; i < row + pHeight; i++) {
                        g1.board[k][i + 1] = piece;
                        g1.board[k][i] = 0;
                    }
                }
                break;
            case LEFT:
                for (int k=row;k<row+pHeight;k++) {
                    for (int i=column;i<column+pWidth;i++) {
                        g1.board[i-1][k]=piece;
                        g1.board[i][k]=0;
                    }
                }
                break;
            case RIGHT:
                for (int k=row;k<row+pHeight;k++) {
                    for (int i=column;i<column+pWidth;i++) {
                        g1.board[i+1][k]=piece;
                        g1.board[i][k]=0;
                    }
                }
                break;
            default:
                System.out.println("<ERROR>: Invalid Move Type (EXITING)");
                System.exit(1);
                break;
        }
    }

    // Required for Assignment
    //
    // Creates a clone and then applies a move to the clone, returns the clone
    public static Game applyMoveCloning(Game g1, Move m1){

        String newName;
        newName=g1.getName();

        Game clone = new Game(newName, 0,0);

        clone=cloneGameState(g1);

        applyMove(clone, m1);

        return clone;
    }

    // Required for Assignment
    //
    // Looks at two boards and declares if they are Exactly equal
    public static boolean stateEqual(Game g1, Game g2){

        boolean equal=true;

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]!=g2.board[j][i]) {
                    equal=false;
                }
            }
        }
        return equal;

    }

    // Required for Assignment
    //
    // Normalizes the board
    public static void normalizeState(Game g1){
        int nextIdx=3;
        for (int i=0;i<g1.getHeight();i++){
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==nextIdx) {
                    nextIdx++;
                }
                else {
                    if (g1.board[j][i] > nextIdx) {
                        swapIdx(g1, nextIdx,g1.board[j][i]);
                        nextIdx++;
                    }
                }
            }
        }
    }

    // Helper function for Normalized
    public static void swapIdx(Game g1, int idx1,int idx2){
        for (int i=0;i<g1.getHeight();i++){
            for (int j=0;j<g1.getWidth();j++){
                if (g1.board[j][i]==idx1) {
                    g1.board[j][i]=idx2;
                }
                else {
                    if (g1.board[j][i]==idx2) {
                        g1.board[j][i]=idx1;
                    }
                }
            }
        }
    }

    // Given a state and a integer n, this function:
    // 1. Generates all moves that can be generated
    // 2. Selects one at Random
    // 3. Executes that move
    // 4. Noramlizes the resulting game state
    // 5. If we have reached the goal, or we have executed n
    //    moves, stop, otherwise loop
    //
    public static void randomWalk(Game g1, int n){

        String logInfo;
        logInfo="Running Random Walk on \""+g1.getName()+"\" with n="+n+"(max)";
        log("INFO", logInfo);

        // Define a list for all moves
        List<Move> listOfMoves=new ArrayList<Move>();

        int min=0;
        int max=0;
        int pickedRandomNumber;
        int moveNumber=0;
        Move nextMove;

        while (n>0) {
            moveNumber++;

            allMoves(g1, listOfMoves);
            max=listOfMoves.size();

            Random rand = new Random();
            pickedRandomNumber=rand.nextInt(max);

            dumpMoves(listOfMoves);
            //System.out.println("Selecting a random number between "+min+" and "+(max-1)+", it is "+pickedRandomNumber);
            logInfo="Selecting a random number between "+min+" and "+(max-1)+", it is "+pickedRandomNumber;
            log("INFO", logInfo);

            nextMove=listOfMoves.get(pickedRandomNumber);

            logInfo="Next move is ("+nextMove.getPiece()+","+nextMove.getDirection()+")";
            log("INFO", logInfo);

            applyMove(g1, nextMove);

            //logInfo="This is the board after the move applied:";
            //log("INFO", logInfo);

            //outputGameState(g1);

            normalizeState(g1);
            logInfo="This is the board after normalized:";
            log("INFO", logInfo);
            outputGameState(g1);

            if (gameStateSolved(g1)) {
                logInfo="Game Solved in "+moveNumber+" moves.";
                log("SUCCESS", logInfo);
                System.exit(0);
            }
            else {
                logInfo="Game not yet solved after "+moveNumber+" moves.";
                log("INFO", logInfo);
            }

            listOfMoves.clear();

            n--;
        }
        outputGameState(g1);
    }

    // Used for troubleshooting to print out the list of moves
    //
    public static void dumpMoves(List<Move> listOfMoves){

        String logInfo;

        logInfo="Total="+listOfMoves.size()+" {";

        ListIterator<Move> itr=listOfMoves.listIterator();

        while (itr.hasNext())
        {
            Move newMove=itr.next();
            logInfo=logInfo+"("+newMove.getPiece()+","+newMove.getDirection()+")";
            //System.out.println("Move piece="+newMove.getPiece()+", Direction="+newMove.getDirection());
        }
        logInfo=logInfo+"}";

        log("INFO", logInfo);

    }

    // Required for the homework, This is the BFS
    // Algorithm from the book.
    //
    public static boolean breadthFirstSearch(Game g1) {

        String logInfo="Solving game with BFS";
        log("INFO", logInfo);
        List<Move> listOfMoves=new ArrayList<Move>();
        int moveNumber=0;
        Game nodeState = cloneGameState(g1);
        Queue<Game> frontier=new LinkedList<Game>();
        Queue<Game> explored=new LinkedList<Game>();

        ListIterator<Move> itr=listOfMoves.listIterator();

        boolean notDone=true;

        if (gameStateSolved(nodeState)) {
            return true;
        }

        normalizeState(nodeState);
        frontier.add(nodeState);

        do {
            if (frontier.isEmpty()) {
                return false;
            }
            Game newGame=frontier.remove();
            explored.add(newGame);

            Game cloneGame = cloneGameState(newGame);

            listOfMoves.clear();

            allMoves(newGame, listOfMoves);
            dumpMoves(listOfMoves);

            for (Move newMove: listOfMoves ) {
                cloneGame = cloneGameState(newGame);
                moveNumber++;
                logInfo = "Move number " + moveNumber + ", Piece " + newMove.getPiece() + ", Direction=" + newMove.getDirection();
                log("INFO", logInfo);

                applyMove(cloneGame, newMove);
                outputGameState(cloneGame);
                if (gameStateSolved(cloneGame)) {
                    return true;
                }

                normalizeState(cloneGame);

                boolean inLoop=checkIfLoopQ(cloneGame, explored);
                if (!inLoop) {
                    frontier.add(cloneGame);
                }
            }
        } while (notDone);
        return false;
    }

    // This is the DFS
    //
    public static boolean depthFirstSearch(Game g1, int moveNumber) {
        String logInfo = "Solving game with DFS";
        log("INFO", logInfo);
        List<Move> listOfMoves = new ArrayList<Move>();
        Game nodeState = cloneGameState(g1);
        Deque<Game> frontier = new LinkedList<Game>();
        Deque<Game> explored = new LinkedList<Game>();

        ListIterator<Move> itr = listOfMoves.listIterator();

        boolean notDone = true;

        if (gameStateSolved(nodeState)) {
            return true;
        }

        normalizeState(nodeState);
        frontier.addFirst(nodeState);

        do {
            x--;
            if (frontier.isEmpty()) {
                return false;
            }
            Game newGame = frontier.removeFirst();
            explored.addFirst(newGame);

            //Game cloneGame = cloneGameState(newGame);

            listOfMoves.clear();

            allMoves(newGame, listOfMoves);
            dumpMoves(listOfMoves);

            for (Move newMove : listOfMoves) {
                Game cloneGame = cloneGameState(newGame);

                moveNumber++;
                logInfo = "Move number " + moveNumber + ", Piece " + newMove.getPiece() + ", Direction=" + newMove.getDirection();
                log("INFO", logInfo);

                applyMove(cloneGame, newMove);
                outputGameState(cloneGame);
                if (gameStateSolved(cloneGame)) {
                    return true;
                }

                normalizeState(cloneGame);

                boolean inLoop=checkIfLoop(cloneGame, explored);

                if (!inLoop) {
                    frontier.addFirst(cloneGame);
                }
            }
        } while (notDone);
        return false;
    }

    // This function is used to make sure we are not in a loop in the BFS code.
    //
    public static boolean checkIfLoopQ(Game g1, Queue<Game> frontier){

        boolean inLoop=false;

        for (Game g: frontier){
            if (stateEqual(g1, g)){
                inLoop=true;
            }
        }
        return inLoop;
    }

    // This function is used to make sure we are not in a loop in the DFS code.
    //
    public static boolean checkIfLoop(Game g1, Deque<Game> frontier){

        boolean inLoop=false;

        for (Game g: frontier){
            if (stateEqual(g1, g)){
                inLoop=true;
            }
        }
        return inLoop;
    }

    // A simple logging function for use througout
    //
    public static void log(String severity, String toPrint) {

        System.out.println("["+severity+"] "+toPrint);

    }

    // This is my main, all driven from the command line.
    //
    public static void main(String args[]) {

        System.out.println("Welcome to the Sliding Brick Puzzle Solver!");

        String firstArg;
        String logInfo;
        logInfo="Number of CMD line arguments="+args.length;
        int iterations=0;

        if ((args.length != 4) && (args.length != 3)) {
            System.out.println("Usage: java Game [game-name] [filename] [mode] [iterations]\n");
            System.out.println("             [game-name] is an arbitrary string to represent the name");
            System.out.println("             [filename] is the name of the input file\n");
            System.out.println(" mode can be 1: run random-search for [iterations]");
            System.out.println("             2: run breadth-first-search");
            System.out.println("             3: run depth-first-search\n");
            System.out.println("             [iterations] is the number N in random-search, can be left off for other searchs");
            System.out.println(" EXAMPLE:" );
            System.out.println("             \"java Game level0 SBP-level0.txt 1 100 (runs random-search for 100 iterations)\"");
            System.out.println("             \"java Game level0 SBP-level0.txt 2 (runs breadth-first-search)\"");
            System.out.println("             \"java Game level0 SBP-level0.txt 3 (runs depth-first-search)\"");
            System.exit(2);
        }
        if (args.length==4) {
            iterations=Integer.parseInt(args[3]);
        }
        String gameName=args[0];
        String fileName=args[1];
        int mode=Integer.parseInt(args[2]);

        logInfo="game-name="+gameName+", fileName="+fileName+", mode="+mode+", iterations="+iterations;
        log("INFO", logInfo);

        // Load SBP-Level0.txt
        Game game = new Game(gameName, 0,0);
        loadGameState(game, fileName);
        outputGameState(game);

        switch (mode) {
            case 1: randomWalk(game, iterations);
                break;
            case 2:  boolean solution = breadthFirstSearch(game);
                     if (solution) {
                         logInfo = "Game Solved with BFS";
                         log("SUCCESS", logInfo);
                     }
                     else {
                         logInfo = "Game NOT solved with BFS";
                         log("FAILURE", logInfo);
                     }
                    break;
            case 3:  boolean dfssolution = depthFirstSearch(game, 1);
                if (dfssolution) {
                    logInfo = "Game Solved with DFS";
                    log("SUCCESS", logInfo);
                }
                else {
                    logInfo = "Game NOT solved with DFS";
                    log("FAILURE", logInfo);
                }
                break;
            default:  log("ERROR", "Invalid game mode.");
                break;
        }

        System.exit(0);
    }
}
