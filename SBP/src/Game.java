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

    Cellstate[][] board;

    // Constructor
    public Game(String name, int width, int height) {

        gameName=name;
        w=width;
        h=height;
        /*System.out.print("Initializing Game \"");
        System.out.print(gameName);
        System.out.print("\"");
        System.out.print(", width=");
        System.out.print(w);
        System.out.print(", height=");
        System.out.print(h);
        System.out.println(".");
        */
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
                        if (debugFunction) System.out.println("Game Width="+g1.w);
                        break;
                    case 2: g1.h=Integer.parseInt(token);
                        if (debugFunction) System.out.println("Game Heigth="+g1.h);
                        break;
                    default:
                        if (((tokenNumber-3)%g1.w)==0) {
                            currentRow++;
                            currentCol=0;
                            if (debugFunction) System.out.println("Row="+currentRow);
                        }
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

    public static void outputGameState() {}

    public static void cloneGameState(){}

    public static void gameStateSolved(Game g){}

    public static void allMovesHelp(){}

    public static void allMoves() {}

    public static void applyMove() {}

    public static void applyMoveCloning(){}

    public static void stateEqual(){}

    public static void normalizeState(){}

    public static void randomWalk(){}

    public static void main(String args[]) {

        String myPath="C:/Users/dwhip_000/IdeaProjects//SBP/SBP/data/";
        String fileName="SBP-level0.txt";

        System.out.println("Welcome to the Sliding Brick Puzzle Solver!");

        Game g1 = new Game("Game 1", 0,0);

        loadGameState(g1, myPath, fileName);

        System.out.print("Game name is ");
        System.out.println(g1.getName());
        System.out.print("Game width is ");
        System.out.println(g1.getWidth());
        System.out.print("Game heigth is ");
        System.out.println(g1.getHeight());

    }
}
