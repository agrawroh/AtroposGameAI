package com.agrawroh.game.atropos.model;

/**
 * Scoring Scheme POJO
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class ScoringScheme {
    /* Global Variable */
    private int[] scheme;

    /******* Default Constructor ******/
    public ScoringScheme() {
        /* Default Constructor */
    }

    /******** Copy Constructor ********/
    public ScoringScheme(int[] scheme) {
        this.scheme = scheme;
    }

    /**
     * Get Scoring Scheme
     * 
     * @return scoringScheme
     */
    public int[] getScheme() {
        return scheme;
    }

    /**
     * Set Scoring Scheme
     * 
     * @param scoringScheme
     */
    public void setScheme(int[] scheme) {
        this.scheme = scheme;
    }

}
