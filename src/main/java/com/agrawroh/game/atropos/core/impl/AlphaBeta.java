package com.agrawroh.game.atropos.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.agrawroh.game.atropos.core.CoreAlgorithm;
import com.agrawroh.game.atropos.core.ICoreAlgorithm;
import com.agrawroh.game.atropos.evaluator.IEvaluator;
import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.model.Colors;
import com.agrawroh.game.atropos.state.AtroposState;

/**
 * Alpha-Beta
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class AlphaBeta extends CoreAlgorithm implements ICoreAlgorithm {
    /******** Default Constructor *********/
    public AlphaBeta(int treeDepth, IEvaluator evaluator) {
        super(treeDepth, evaluator);
    }

    /**
     * Compute Best Score
     * 
     * @param state
     * @param depth
     * @param alpha
     * @param beta
     * @return bestScore
     */
    @Override
    public int computeBestScore(AtroposState state, int depth, int... params) {
        return doAlphaBeta(state, depth, params[0], params[1]);
    }

    /**
     * Get Next Move
     * 
     * @param currentState
     * @return AtroposCircle
     */
    @Override
    public AtroposCircle getNextMove(AtroposState currentState) {
        AtroposCircle bestCircle = null;

        /* Evaluate Corners */
        if ((bestCircle = this.evaluateCorners(currentState)) != null) {
            return bestCircle;
        }

        int bestScore = Integer.MIN_VALUE;
        /* Iterate Playable Circles */
        for (final AtroposCircle circle : currentState.getPlayableCircles()) {
            /* Iterate Colors */
            for (final Colors color : getColorSet(circle, 0)) {
                /* Create Current Circle Clone */
                AtroposCircle currentCircle = circle.clone();

                /* Color Circle */
                currentCircle.color(color.getValue());

                /* Clone CurrentState; Initialize NewState */
                AtroposState nextState = currentState.clone();

                /* Make Move */
                nextState.makePlay(currentCircle);

                /* Compute Move Best Score */
                int moveBestScore = this.computeBestScore(nextState,
                        this.getTreeDepth(), Integer.MIN_VALUE,
                        Integer.MAX_VALUE);

                /* Check Whether Best */
                if (moveBestScore > bestScore) {
                    /* Update Best Score */
                    bestScore = moveBestScore;

                    /* Update Best Circle */
                    bestCircle = currentCircle;
                }
            }
        }

        /* Return Best Circle */
        return null == bestCircle ? currentState.getPlayableCircles().get(0)
                .color(1) : bestCircle;
    }

    /**
     * Do Alpha-Beta
     * 
     * @param currentState
     * @param depth
     * @param alpha
     * @param beta
     * @return bestScore
     */
    private int doAlphaBeta(AtroposState currentState, int depth, int alpha,
            int beta) {
        /* Initialize Child States */
        List<AtroposState> childStates = new ArrayList<>();

        /* Return If Depth Reached */
        if (currentState.isFinished() || 0 == depth)
            return this.getEvaluator().evaluateMove(currentState);

        /* Iterate Playable Circles */
        for (final AtroposCircle circle : currentState.getPlayableCircles()) {
            /* Iterate Colors */
            for (final Colors color : getColorSet(circle, 1)) {
                /* Create Current Circle Clone */
                AtroposCircle currentCircle = circle.clone();

                /* Color Circle */
                currentCircle.color(color.getValue());

                /* Clone CurrentState; Initialize NewState */
                AtroposState nextState = currentState.clone();

                /* Make Move */
                nextState.makePlay(currentCircle);

                /* Add Child State */
                childStates.add(nextState);
            }
        }

        /* Iterate Child States */
        for (final AtroposState childState : childStates) {
            /* Do Alpha-Beta */
            alpha = Math.max(alpha,
                    -doAlphaBeta(childState, depth - 1, -beta, -alpha));

            /* Terminate Condition */
            if (beta <= alpha)
                break;
        }

        /* Return Best Score */
        return alpha;
    }
}
