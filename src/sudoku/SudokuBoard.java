/*
 * Copyright 2012 by Pooya Samizadeh-Yazd. All rights reserved.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class contains the solver methods and has a Collection of Nodes.
 * @author Pooya Samizadeh-Yazd
 */
public class SudokuBoard {

    LinkedList<Node> grid = new LinkedList<Node>();
    int filledUpNodes = 0;

    /**
     * The initializer method that creates an empty grid
     */
    public SudokuBoard() {

        for (int i = 0; i < 81; i++) {
            LinkedList<Integer> possibilities = new LinkedList<Integer>();
            for (int j = 0; j < 9; j++) {
                possibilities.add(j + 1);
            }
            grid.add(new Node(0, false, possibilities));
        }
    }

    /**
     * Finds the first index of the row for a given Node index
     * @param index of the Node
     * @return the first index of the Row
     */
    public int findRowFirstIndex(int index) {
        int result = index / 9;
        return result * 9;
    }

    /**
     * Finds the first index of the column for a given Node index
     * @param index of the Node
     * @return the first index of the Column
     */
    public int findColumnFirstIndex(int index) {
        int result = index % 9;
        return result;
    }

    /**
     * Finds the first index of the row for a given sub grid index
     * @param index of the Node
     * @return the first index of the sub grid
     */
    public int findSubGridFirstIndex(int index) {
        int row = findRowFirstIndex(index);
        int column = findColumnFirstIndex(index);

        if (column <= 2) {
            if (row <= 18) {
                return 0;
            }
            if (row <= 45 && row >= 27) {
                return 27;
            }
            if (row <= 72 && row >= 54) {
                return 54;
            }
        }
        if (column >= 3 && column <= 5) {
            if (row <= 18) {
                return 3;
            }
            if (row <= 45 && row >= 27) {
                return 30;
            }
            if (row <= 72 && row >= 54) {
                return 57;
            }
        }
        if (column >= 6 && column <= 8) {
            if (row <= 18) {
                return 6;
            }
            if (row <= 45 && row >= 27) {
                return 33;
            }
            if (row <= 72 && row >= 54) {
                return 60;
            }
        }
        return -1;
    }

    /**
     * It adds a value to a given index and also updates the possibilities.
     * @param index index of the node
     * @param value the value
     * @param permananat true if it is being read from the file
     */
    public void addValueToIndex(Integer index, Integer value, boolean permananat) {
        grid.get(index).setValue(value);

        if (permananat) {
            grid.get(index).setIsPermanant(permananat);
            grid.get(index).getPossibilities().clear();
            filledUpNodes++;
        }

        removePossibilitiesFromColumn(index, value);
        removePossibilitiesFromGrid(index, value);
        removePossibilitiesFromRow(index, value);
    }

    /**
     * It is for updating the possibilities in nodes of the grid
     * @param index index of the new node
     * @param value value of the new node
     */
    public void removePossibilitiesFromGrid(Integer index, Integer value) {
        int firstIndexOfGrid = findSubGridFirstIndex(index);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.get(firstIndexOfGrid + ((i * 9) + j)).getPossibilities().contains(value)) {
                    grid.get(firstIndexOfGrid + ((i * 9) + j)).getPossibilities().remove(value);
                }
            }
        }
    }

    /**
     * It is for updating the possibilities in nodes of the row
     * @param index index of the new node
     * @param value value of the new node
     */
    public void removePossibilitiesFromRow(Integer index, Integer value) {
        int firstIndexOfRow = findRowFirstIndex(index);
        for (int i = 0; i < 9; i++) {

            if (grid.get(firstIndexOfRow + i).getPossibilities().contains(value)) {
                grid.get(firstIndexOfRow + i).getPossibilities().remove(value);
            }
        }
    }

    /**
     * It is for updating the possibilities in nodes of the column
     * @param index index of the new node
     * @param value value of the new node
     */
    public void removePossibilitiesFromColumn(Integer index, Integer value) {
        int firstIndexOfColumn = findColumnFirstIndex(index);
        for (int i = 0; i < 9; i++) {
            if (grid.get(firstIndexOfColumn + (i * 9)).getPossibilities().contains(value)) {
                grid.get(firstIndexOfColumn + (i * 9)).getPossibilities().remove(value);
            }
        }
    }

    /**
     * It starts solving the puzzle logically. It is a recursive method and
     * continues until it cannot update anything else.
     */
    public void solveLogically() {

        boolean changed = false;
        for (int i = 0; i < 81; i++) {
            if (grid.get(i).getPossibilities().size() == 1) {
                addValueToIndex(i, grid.get(i).getPossibilities().getFirst(), true);
                changed = true;
            }
        }
        if (changed) {
            solveLogically();
        }

    }

    /**
     * It searches through the nodes and of the board and returns the index
     * of the empty nodes in an ArrayList
     * @return the indexes of empty nodes in an ArrayList
     */
    public ArrayList<Integer> findEmptyNodes() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++) {
            if (grid.get(i).getValue() == 0) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * It solves the puzzle using brute-force method
     */
    public void solveBruteForce() {
        ArrayList<Integer> emptyNodes = findEmptyNodes();
        int emptyNodeArrayIndex = 0;

        while (emptyNodeArrayIndex < emptyNodes.size()) {
            int emptyNodeIndex = emptyNodes.get(emptyNodeArrayIndex);
            int value = grid.get(emptyNodeIndex).getValue();

            if (value == 9) {
                grid.get(emptyNodeIndex).setValue(0);
                emptyNodeArrayIndex--;
            } else {
                grid.get(emptyNodeIndex).setValue(value + 1);
                if (rowCheckConsistency(emptyNodeIndex) && columnCheckConsistency(emptyNodeIndex)
                        && gridCheckConsistency(emptyNodeIndex)) {
                    emptyNodeArrayIndex++;
                }

            }
        }
    }

    /**
     * It returns true if the value can be added to that row
     * @param index of the row that should be checked for consistency
     * @return true if it is consistent
     */
    public boolean rowCheckConsistency(Integer index) {
        int firstIndexOfRow = findRowFirstIndex(index);
        for (int i = 0; i < 9; i++) {
            if (grid.get(firstIndexOfRow + i).getValue() == grid.get(index).getValue() && (firstIndexOfRow + i) != index) {
                return false;
            }
        }
        return true;
    }

    /**
     * It returns true if the value can be added to that column
     * @param index of the column that should be checked for consistency
     * @return true if it is consistent
     */
    public boolean columnCheckConsistency(Integer index) {
        int firstIndexOfColumn = findColumnFirstIndex(index);
        for (int i = 0; i < 9; i++) {
            if (grid.get(firstIndexOfColumn + (i * 9)).getValue() == grid.get(index).getValue() && (firstIndexOfColumn + (i * 9) != index)) {
                return false;
            }
        }
        return true;
    }

    /**
     * It returns true if the value can be added to that grid
     * @param index of the grid that should be checked for consistency
     * @return true if it is consistent
     */
    public boolean gridCheckConsistency(Integer index) {
        int firstIndexOfGrid = findSubGridFirstIndex(index);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.get(firstIndexOfGrid + ((i * 9) + j)).getValue() == grid.get(index).getValue() && (firstIndexOfGrid + ((i * 9) + j)) != index) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * it returns the grid
     * @return the grid
     */
    LinkedList<Node> getGrid() {
        return grid;
    }

    /**
     * It is for setting the grid
     * @param grid the grid to be set
     */
    public void setGrid(LinkedList<Node> grid) {
        this.grid = grid;
    }

    /**
     * it prints the grid in a formatted way
     */
    public void printGrid() {
        Iterator<Node> iterator = grid.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            for (int i = 0; i < 3; i++) {
                System.out.print(" " + iterator.next().getValue() + " ");
                index++;
            }
            if ((index % 9) == 0) {
                System.out.print("\n");

                if (index == 27 || index == 54) {
                    System.out.println(" --------+---------+--------");
                }
            } else {
                System.out.print("|");
            }
        }
    }

    /**
     * it returns the number of the nodes that are filled already.
     * @return number of the nodes that are not empty.
     */
    public int getFilledUpNodes() {
        return filledUpNodes;
    }
}
