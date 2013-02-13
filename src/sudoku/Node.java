/*
 * Copyright 2012 by Pooya Samizadeh-Yazd. All rights reserved.
 */

package sudoku;

import java.util.LinkedList;

/**
 * This class is for holding the information of each node. It consists of
 * fields such as Value, permanent (boolean) and a list of possibilities
 * for that node.
 * @author Pooya Samizadeh-Yazd
 */
public class Node {
    private int value;
    private boolean permanant;
    private LinkedList<Integer> possibilities;

    public Node(int value, boolean isPermanant, LinkedList<Integer> possibilities) {
        this.value = value;
        this.permanant = isPermanant;
        this.possibilities = possibilities;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the permanent
     */
    public boolean isIsPermanant() {
        return permanant;
    }

    /**
     * @param permanant the permanent to set
     */
    public void setIsPermanant(boolean permanant) {
        this.permanant = permanant;
    }

    /**
     * @return the possibilities
     */
    public LinkedList<Integer> getPossibilities() {
        return possibilities;
    }

    /**
     * @param possibilities the possibilities to set
     */
    public void setPossibilities(LinkedList<Integer> possibilities) {
        this.possibilities = possibilities;
    }

    
}
