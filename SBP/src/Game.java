/**
 * Created by dwhipple on 1/16/2016.
 *
 * This code is written for a course @ Drexel University, Introduction to AI, cs510
 *
 * It is intended to implement a Sliding Brick Puzzle
 */

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


/*
        try{
            //Create object of FileReader
            FileReader inputFile = new FileReader(path+fileName);

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);

            //Variable to hold the one line data
            String line;
            int lineNumber=1;

            // Read file line by line and print on the console
            while ((line = bufferReader.readLine()) != null)   {
                System.out.println(line);
            }
            //Close the buffer reader
            bufferReader.close();
        } catch(Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
*/
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

    public static void gameStateSolved(Game g){}

    public static void allMovesHelp(){}

    public static void allMoves() {}

    public static void applyMove() {}

    public static void applyMoveCloning(){}

    public static void stateEqual(){}

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

        Game level0Clone=cloneGameState(level0);
        outputGameState(level0Clone);

        //prettyPrintGameState(level0);
/*
        // Load SBP-Level0.txt
        Game level0Solved = new Game("Level 0 Solved", 0,0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level0-solved.txt";
        loadGameState(level0Solved, myPath, fileName);
        outputGameState(level0Solved);

        // Load SBP-Level1.txt
        Game level1 = new Game("Level 1", 0, 0);
        myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        fileName="SBP-level1.txt";
        loadGameState(level1, myPath, fileName);
        outputGameState(level1);

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
