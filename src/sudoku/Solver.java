/*
 * Copyright 2012 by Pooya Samizadeh-Yazd. All rights reserved.
 */
package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class is the wrapper class for SudokuBoard and it also populates
 * the grid from the file.
 * @author Pooya Samizadeh-Yazd
 */
public class Solver {

    SudokuBoard board = new SudokuBoard();
    String filename;

    /**
     * The initializer
     * @param filename
     */
    public Solver(String filename) {
        this.filename = filename;
    }

    /**
     * returns the board
     * @return the board
     */
    public SudokuBoard getBoard() {
        return board;
    }

    /**
     * It loads the grid from the contents of the text file.
     * @throws IOException
     */
    public void populateTheGridFromFile() throws IOException {
        BufferedReader reader = null;
        try {
            File file = new File(filename);
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            //System.out.println(line);
            Integer nodeValue;
            int stringIndex1 = 0;
            int index = 0;
            while (index < 81) {
                nodeValue = Integer.parseInt(line.substring(stringIndex1, stringIndex1 + 1));
                line = line.substring(1);
                if (nodeValue != 0) {
                    board.addValueToIndex(index, nodeValue, true);
                } else {
                    board.addValueToIndex(index, 0, false);
                }
                index++;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist.");
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                System.out.println("File cannot be closed.");
            }
        }
    }

    /**
     * it invokes the logic solver and also keeps track of the time it
     * takes for solving.
     */
    public void solveLogically() {
        long begin = System.currentTimeMillis();
        board.solveLogically();
        long end = System.currentTimeMillis();
        System.out.println("Solving time = " + (end - begin) + " ms");
    }

    /**
     * getter method for the board
     * @return the board
     */
    public LinkedList<Node> getGrid() {
        return board.getGrid();
    }

    /**
     * it invokes the brute-force solver and also keeps track of the time
     * it takes for solving
     */
    public void solveBruteForce() {
        long begin = System.currentTimeMillis();
        board.solveBruteForce();
        long end = System.currentTimeMillis();
        System.out.println("Solving time = " + (end - begin) + " ms");
    }

    /**
     * it invokes the print method for printing the grid.
     */
    public void print() {
        board.printGrid();
    }

    /**
     * checks if the puzzle is complete.
     * @return true if it is all completed.
     */
    public boolean isSolved() {
        if (board.getFilledUpNodes() == 81) {
            return true;
        } else {
            return false;
        }
    }
}
