package com.agrawroh.game.atropos.model;

/**
 * Atropos Move
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 * 
 *       Houses a move in Atropos with a circle and color
 * 
 */
public class AtroposMove {
    /* Global Variables */
    private AtroposCircle circle;
    private Colors color;

    /*************** Copy Constructor ***************/
    AtroposMove(AtroposCircle circle, Colors color) {
        this.circle = circle;
        this.color = color;
    }

    /**
     * Get Circle
     * 
     * @return circle
     */
    public AtroposCircle getCircle() {
        return circle;
    }

    /**
     * Get Color
     * 
     * @return color
     */
    public Colors getColor() {
        return color;
    }
}
