package com.agrawroh.game.atropos.evaluator.impl;

import java.util.ArrayList;
import java.util.List;

import com.agrawroh.game.atropos.evaluator.Evaluator;
import com.agrawroh.game.atropos.evaluator.IEvaluator;
import com.agrawroh.game.atropos.model.Colors;
import com.agrawroh.game.atropos.model.ScoringScheme;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.utils.Pair;

/**
 * Ninja Evaluator
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class NinjaEvaluator extends Evaluator implements IEvaluator {

    @Override
    public int evaluateMove(AtroposState currentstate) {
        /* Set Default Scoring Scheme */
        ScoringScheme scoringScheme = new ScoringScheme(new int[] { 20, -60,
                -1, 1, 3 });

        /* Get Move Evaluation */
        return evaluateMove(currentstate, scoringScheme);
    }

    @Override
    public List<Pair<Integer, Integer>> getPositions() {
        /* Initialize Positions List */
        List<Pair<Integer, Integer>> positionsList = new ArrayList<>();

        /*
         *  C O
         * C X O
         *  O O
         */
        positionsList.add(new Pair<Integer, Integer>(0, 2));

        /*
         *  O O
         * C X O
         *  C O
         */
        positionsList.add(new Pair<Integer, Integer>(2, 4));

        /*
         *  O O
         * O X O
         *  C C
         */
        positionsList.add(new Pair<Integer, Integer>(4, 5));

        /*
         *  O O
         * O X C
         *  O C
         */
        positionsList.add(new Pair<Integer, Integer>(5, 3));

        /*
         *  O C
         * O X C
         *  O O
         */
        positionsList.add(new Pair<Integer, Integer>(3, 1));

        /*
         *  C C
         * O X O
         *  O O
         */
        positionsList.add(new Pair<Integer, Integer>(1, 0));

        /* Return Positions List */
        return positionsList;
    }

    @Override
    public int evaluateMove(AtroposState currentState,
            ScoringScheme scoringScheme) {
        /* Check Whether Game Already Finished */
        if (currentState.isFinished())
            return Integer.MIN_VALUE;

        /* Initialize Total Score */
        int totalScore = 0;

        /* Get Neighbors */
        int[] neighbors = getNeighbors(currentState.lastPlay.height(),
                currentState.lastPlay.leftDistance(), currentState);

        /* Evaluate For Number Of Open Space */
        /*
         *  C C                                         C O
         * C X C                                       O X C  <-- More Open Space; Less Score
         *  O C  <-- Single Open Space; Really Good!    C C        
         */
        totalScore += evaluateOnAttributeW(currentState.lastPlay.getColor(),
                neighbors, scoringScheme.getScheme()[1],
                scoringScheme.getScheme()[0]);

        /* Evaluate For Same Colored Neighbors */
        /*
         *  B R  
         * B X R <-- For X=R, Score: ScoringScheme * RED Counts
         *  R R               In General, Score: ScoringScheme * X-Colored Neighbors
         */
        if (Colors.UNCOLORED.getValue() != currentState.lastPlay.getColor()) {
            totalScore += evaluateOnAttributeY(
                    currentState.lastPlay.getColor(), neighbors,
                    scoringScheme.getScheme()[2]);
        }

        /* Evaluate For Surrounding Open Spaces */
        /*
         *  O O  <-- 
         * O X R <-- Score: ScoringScheme * Open Spaces Count
         *  R O  <-- 
         */
        totalScore += evaluateOnAttributeZ(neighbors,
                scoringScheme.getScheme()[3], Colors.UNCOLORED.getValue());

        /* Evaluate For Different Colored Neighbor Pairs */
        /*
         *  B O  <-- Pairs (Clockwise) : { RB, BO, OR, RO, OB, BR } 
         * R X R     Score: ScoringScheme * 2
         *  B O      In General, Score: ScoringScheme * Different Colored Neighbor Pairs
         */
        totalScore += evaluateOnPosition(neighbors,
                scoringScheme.getScheme()[4], getPositions());

        /* Return Evaluation Score */
        return totalScore;
    }
}
