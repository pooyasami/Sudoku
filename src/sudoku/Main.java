/*
 * Copyright 2012 by Pooya Samizadeh-Yazd. All rights reserved.
 */
package sudoku;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the main method which contains the User Interface part of the
 * Sudoku Solver Application
 * @author Pooya Samizadeh-Yazd
 */
public class Main {

    static Solver solver;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        System.out.println("Welcome to Soduko solver Program");
        while (true) {
            start();
        }

    }

    /**
     * It shows the list of the files that are in the directory and gives the
     * user options to choose.
     * @throws IOException
     */
    public static void start() throws IOException {


        System.out.println("\nPlease choose one of the puzzles below (1, 2, ...)\n");

        File[] files = finder("./");

        for (int i = 1; i <= files.length; i++) {
            System.out.println(i + ". " + files[i - 1].getName());
        }

        System.out.print(">");

        Scanner reader = new Scanner(System.in);
        Integer command = new Integer(0);
        try {
            command = Integer.parseInt(reader.nextLine());
            if (command <= files.length && command > 0) {
                solver = new Solver(files[command - 1].getName());
                solver.populateTheGridFromFile();
                solver.print();

                chooseMethod();

            } else {
                System.out.println("\"" + command + "\"" + " is an invalid input.\n");
            }

        } catch (NumberFormatException ex) {
            System.out.println("\"" + command + "\"" + " is an invalid input.\n");
        }
    }

    /**
     * it is for prompting and getting the command from user in regards to
     * the method of solving.
     */
    public static void chooseMethod() {
        System.out.println("Please Choose solving method");
        System.out.println("1.Logic (Heuristic)");
        System.out.println("2.Brute-Force (Backtracking)\n");
        System.out.print(">");

        Integer command = new Integer(0);
        Scanner reader = new Scanner(System.in);
        try {
            command = Integer.parseInt(reader.nextLine());
            switch (command) {
                case 1:
                    solver.solveLogically();
                    if (solver.isSolved()) {
                        solver.print();
                        System.out.println("Done logically");
                    } else {
                        solver.print();
                        System.out.println("\nThe puzzle has been solved partially by heuristics,"
                                + "now I will continue solving it by brute-force.");
                        solver.solveBruteForce();
                        solver.print();
                        System.out.println("\nDone logically + brute-force.");
                    }
                    break;
                case 2:
                    solver.solveBruteForce();
                    solver.print();
                    System.out.println("\nDone with brute-force.");
                    break;
                default:
                    System.out.println("\"" + command + "\"" + " is an invalid input.\n");
            }

        } catch (NumberFormatException ex) {
            System.out.println("\"" + command + "\"" + " is an invalid input.\n");
        }
    }

    /**
     * It finds the .txt files in the directory.
     * @param dirName the directory path
     * @return an array of the files.
     */
    public static File[] finder(String dirName) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });

    }
}
