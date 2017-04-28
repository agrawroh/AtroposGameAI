package com.agrawroh.game.atropos.utils;

/**
 * Pair Class
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 * @param <Left>
 * @param <Right>
 */
public class Pair<Left, Right> {
    /* Global Variables */
    private final Left left;
    private final Right right;

    /*********** Constructor ***********/
    public Pair(Left left, Right right) {
        this.left = left;
        this.right = right;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new StringBuilder().append("<").append(left).append(",")
                .append(right).append(">").toString();
    }

    /**
     * Get Left
     * 
     * @return left
     */
    public Left getLeft() {
        return left;
    }

    /**
     * Get Right
     * 
     * @return right
     */
    public Right getRight() {
        return right;
    }
}