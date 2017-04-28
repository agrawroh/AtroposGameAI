package com.agrawroh.game.atropos.core;

import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.state.AtroposState;

/**
 * Core Algorithm Interface
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public interface ICoreAlgorithm {
    /**
     * Compute Best Score
     * 
     * @param state
     * @param depth
     * @param params
     * @return bestScore
     */
    int computeBestScore(AtroposState state, int depth, int... params);

    /**
     * Get Next Move
     * 
     * @param currentState
     * @return AtroposCircle
     */
    public AtroposCircle getNextMove(AtroposState currentState);
}
