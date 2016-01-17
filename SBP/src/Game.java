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
//import java.lang.Object;
//import java.io.BufferedReader;
//import java.lang.System.*;
// import java.io.FileNotFoundException;
//import java.io.FileReader;

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
    public static boolean loadGameState(Game g1, String path, String fileName) {

        String logInfo="Reading game state from "+path+fileName+".";

        log("INFO",logInfo);

        int tokenNumber=1;
        int currentRow=-1;
        int currentCol=0;
        String token;
        boolean debugFunction=false;

        try {
            File text=new File(path+fileName);
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
                    //System.out.print(g1.board[j][i]+",");
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
        newName=g1.getName()+" clone";

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

    // Returns the Hieght of a piece on the board
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
                    //System.out.println("Piece found @ row="+j+", col="+i+", Wid="+pWidth+", Height="+pHeight);

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

       // listOfMoves.add(myMove);
       // listOfMoves.add(myMove2);

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
        //log("INFO", logInfo);
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
        newName=g1.getName()+" clone";

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
        Move nextMove;

        while (n>0) {

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
                logInfo="Game Solved";
                log("SUCCESS", logInfo);
                System.exit(0);
            }
            else {
                logInfo="Game not yet solved.";
                log("INFO", logInfo);
            }

            listOfMoves.clear();

            n--;
        }
        outputGameState(g1);
    }

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
    public static void breadthFirstSearch() {}

    public static void depthFirstSearch() {}

    public static void log(String severity, String toPrint) {

        System.out.println("["+severity+"] "+toPrint);

    }
    public static void main(String args[]) {

        String myPath;
        String fileName;
        System.out.println("Welcome to the Sliding Brick Puzzle Solver!");


        // Load SBP-Level0.txt
        Game level0 = new Game("Level 0", 0,0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level0.txt";
        loadGameState(level0, myPath, fileName);
        outputGameState(level0);

        randomWalk(level0, 200);

        //int piece=3;
        //System.out.println("Piece Width of piece="+piece+" is "+pieceWidth(level0,piece));
        //System.out.println("Piece Height of piece="+piece+" is "+pieceHeight(level0,piece));
/*
        if (gameStateSolved(level0)) {
            System.out.println("Game level0 is solved");
        }
        else {
            System.out.println("Game level0 not solved");
        }
        Game level0Clone=cloneGameState(level0);
        //outputGameState(level0Clone);
        if(stateEqual(level0, level0Clone)) {
            System.out.println("level0 and level0-clone are equal");
        }
        else {
            System.out.println("level0 and level0-clone are NOT equal");
        }


        //prettyPrintGameState(level0);

        // Load SBP-Level0-solved.txt
        Game level0Solved = new Game("Level 0 Solved", 0,0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level0-solved.txt";
        loadGameState(level0Solved, myPath, fileName);
        //outputGameState(level0Solved);

        if (gameStateSolved(level0Solved)) {
            System.out.println("Game level0-solved is solved");
        }
        else {
            System.out.println("Game level0-solved not solved");
        }

        //System.out.println("Move piece="+myMove.getPiece()+", Direction="+myMove.getDirection());
*/
/*
        List<Move> listOfMoves=new ArrayList<Move>();
        int piece=4;

        allMovesHelp(level0, piece, listOfMoves);

        allMoves(level0, listOfMoves);

        System.out.println("Number of list items="+listOfMoves.size());

        ListIterator<Move> itr=listOfMoves.listIterator();

        while (itr.hasNext())
        {
            Move newMove=itr.next();
            applyMove(level0, newMove);
            outputGameState(level0);
            System.out.println("Move piece="+newMove.getPiece()+", Direction="+newMove.getDirection());
        }
*/
        /*
        // Load SBP-Level1.txt
        Game level1 = new Game("Level 1", 0, 0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level1.txt";
        loadGameState(level1, myPath, fileName);
        //outputGameState(level1);

        if(stateEqual(level0, level1)) {
            System.out.println("level0 and level1 are equal");
        }
        else {
            System.out.println("level0 and level1 are NOT equal");
        }

        // Load SBP-Level2.txt
        Game level2 = new Game("Level 2", 0, 0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level2.txt";
        loadGameState(level2, myPath, fileName);
        outputGameState(level2);

        // Load SBP-Level3.txt
        Game level3 = new Game("Level 3", 0, 0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level3.txt";
        loadGameState(level3, myPath, fileName);
        outputGameState(level3);
*/
/*        // Load SBP-test-not-normalized.txt
        Game notNormalized = new Game("Test Not Normalized", 0, 0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-test-not-normalized.txt";
        loadGameState(notNormalized, myPath, fileName);
        outputGameState(notNormalized);

        normalizeState(notNormalized);
        outputGameState(notNormalized);

        //prettyPrintGameState(notNormalized);
*/
 /*System.out.print("Game name is ");
        System.out.println(g1.getName());
        System.out.print("Game width is ");
        System.out.println(g1.getWidth());
        System.out.print("Game heigth is ");
        System.out.println(g1.getHeight());
*/


    }
}
