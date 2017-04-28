package com.agrawroh.game.atropos.model;

/**
 * Colors Enumeration
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public enum Colors {
    UNCOLORED(0), RED(1), BLUE(2), GREEN(3);

    /********* Global Variable(s) *********/
    private final int cval;

    /* Private Constructor */
    private Colors(int colorValue) {
        this.cval = colorValue;
    }

    /**
     * Get Value
     * 
     * @return value
     */
    public int getValue() {
        return cval;
    }
}
