/**
 * Created by dwhipple on 1/16/2016.
 *
 * This code is written for a course @ Drexel University, Introduction to AI, cs510
 *
 * It is intended to implement a Sliding Brick Puzzle
 */

import java.lang.System.*;
import java.util.*;
import java.lang.Object;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Game {

    public enum Cellstate {FREE, WALL, GOAL, MASTER}

    String gameName;
    int w;   // Width of board
    int h;   // Height of board

    int[][] board;

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

    public static boolean loadGameState(Game g1, String path, String fileName) {

        System.out.print("Reading Game State from ");
        System.out.print(path);
        System.out.print(fileName);
        System.out.println(".");

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
                        System.out.println("Game Width="+g1.w);
                        break;
                    case 2: g1.h=Integer.parseInt(token);
                        System.out.println("Game Heigth="+g1.h);
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
            System.out.println("Error opening file");
        }
        return false;
    }

    public static void outputGameState(Game g1) {
        System.out.println("Dumping game \""+g1.getName()+"\" (Height="+g1.getHeight()+", Width="+g1.getWidth()+")");
        System.out.println(g1.w+","+g1.h+",");
        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                System.out.print(g1.board[j][i]+",");
            }
            System.out.println();
        }
    }

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

    // This function clones the game state
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

    /*public static boolean canMoveUp(Game g1, int piece) {
        if
    }
    public static boolean canMoveDown {}
    public static boolean canMoveLeft {}
    public static boolean canMoveRight {}
*/

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
            if (g1.board[curRow][curCol-1]!=0){
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
            if (g1.board[curRow][curCol+1]!=0){
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
            if (g1.board[curRow-1][curCol]!=0){
                canMoveLeft=false;
            }
            curCol++;
            pHeight--;
        }
        if (canMoveLeft) {
            System.out.println("canMoveLeft=true");
            Move myMove=new Move(piece, Move.Direction.LEFT);
            listOfMoves.add(myMove);
        }
        return canMoveLeft;
    }

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
            System.out.println("g1.board[curRow+1][curCol+1]="+g1.board[curRow+1][curCol]);
            if (g1.board[curRow+1][curCol] != 0) {
                canMoveRight = false;
            }
            curCol++;
            pHeight--;
        }
        if (canMoveRight) {
            System.out.println("canMoveRight=true");
            Move myMove = new Move(piece, Move.Direction.RIGHT);
            listOfMoves.add(myMove);
        }
        return canMoveRight;
    }

    public static void allMovesHelp(Game g1, int piece, List<Move> listOfMoves){

        int pWidth, pHeight;
        System.out.println("Finding moves for piece="+piece+" on game state \""+g1.getName()+"\"");

        //Move myMove=new Move(2, Move.Direction.DOWN);
        //Move myMove2=new Move(7, Move.Direction.RIGHT);

        for (int i=0;i<g1.getHeight();i++) {
            for (int j=0;j<g1.getWidth();j++) {
                if (g1.board[j][i]==piece) {
                    pWidth=pieceWidth(g1,piece);
                    pHeight=pieceHeight(g1, piece);
                    System.out.println("Piece found @ row="+j+", col="+i+", Wid="+pWidth+", Height="+pHeight);

                    boolean moveUp=canMoveUp(g1, piece, i,j,listOfMoves);
                    boolean moveDown=canMoveDown(g1, piece, i,j,listOfMoves);
                    boolean moveLeft=canMoveLeft(g1, piece, i,j,listOfMoves);
                    boolean moveRight=canMoveRight(g1, piece, i,j,listOfMoves);

                    // Force the loops to end
                    i=g1.getHeight();
                    j=g1.getWidth();
                }
            }
        }

       // listOfMoves.add(myMove);
       // listOfMoves.add(myMove2);

    }

    public static void allMoves() {}

    public static void applyMove() {}

    public static void applyMoveCloning(){}

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

    public static void normalizeState(){}

    public static void randomWalk(){}

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
        List<Move> listOfMoves=new ArrayList<Move>();
        int piece=2;

        allMovesHelp(level0, piece, listOfMoves);

        System.out.println("Number of list items="+listOfMoves.size());

        ListIterator<Move> itr=listOfMoves.listIterator();

        while (itr.hasNext())
        {
            Move newMove=itr.next();
            System.out.println("Move piece="+newMove.getPiece()+", Direction="+newMove.getDirection());
        }

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

        // Load SBP-test-not-normalized.txt
        Game notNormalized = new Game("Test Not Normalized", 0, 0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-test-not-normalized.txt";
        loadGameState(notNormalized, myPath, fileName);
        outputGameState(notNormalized);
        prettyPrintGameState(notNormalized);
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
