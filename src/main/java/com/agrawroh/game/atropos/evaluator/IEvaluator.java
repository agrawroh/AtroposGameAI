package com.agrawroh.game.atropos.evaluator;

import java.util.List;

import com.agrawroh.game.atropos.model.ScoringScheme;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.utils.Pair;

/**
 * Evaluator Interface
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public interface IEvaluator {

    /**
     * Function To Evaluate Given Move
     * 
     * @return Net Score
     */
    int evaluateMove(AtroposState currentstate, ScoringScheme scoringScheme);

    /**
     * Function To Evaluate Given Move
     * 
     * @return Net Score
     */
    int evaluateMove(AtroposState currentstate);

    /**
     * Function To Get Positions Pair
     * 
     * @return Positions Pair List
     */
    List<Pair<Integer, Integer>> getPositions();

    /**
     * Get Neighbors
     * 
     * @param height
     * @param leftDistance
     * @param currentState
     * @return neighbors
     */
    int[] getNeighbors(int height, int leftDistance, AtroposState currentState);
}
