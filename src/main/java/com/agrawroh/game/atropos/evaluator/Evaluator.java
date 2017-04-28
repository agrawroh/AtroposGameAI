package com.agrawroh.game.atropos.evaluator;

import java.util.List;

import com.agrawroh.game.atropos.model.Colors;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.utils.Pair;

/**
 * Evaluator Class
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class Evaluator {
    /**
     * Evaluate On Attribute
     * 
     * @param neighbors
     * @param moveScore
     * @param evalAttribute
     * @return evlauationScore
     */
    public int evaluateOnAttributeZ(int[] neighbors, int moveScore,
            int evalAttribute) {
        int evaluationScore = 0;
        /* Iterate Neighbors */
        for (final int neighbor : neighbors) {
            if (neighbor == evalAttribute) {
                evaluationScore += moveScore;
            }
        }
        /* Return Evaluation Score */
        return evaluationScore;
    }

    /**
     * Evaluate On Attribute
     * 
     * @param neighbors
     * @param moveScore
     * @param evalAttribute
     * @return evlauationScore
     */
    public int evaluateOnAttributeY(int currentColor, int[] neighbors,
            int moveScore) {
        Integer[] counts = { 0, 0, 0 };
        /* Iterate Neighbors */
        for (final int neighbor : neighbors) {
            if (0 == neighbor) {
                continue;
            }
            ++counts[neighbor - 1];
        }
        /* Return Evaluation Score */
        return counts[currentColor - 1]
                * (moveScore / 6 - counts[currentColor - 1]);
    }

    /**
     * Evaluate On Attribute
     * 
     * @param neighbors
     * @param moveScore
     * @param evalAttribute
     * @return evlauationScore
     */
    public int evaluateOnAttributeX(int currentColor, int[] neighbors,
            int moveScore) {
        int uncoloredCircles = 0;
        /* Iterate Neighbors */
        for (final int neighbor : neighbors) {
            if (neighbor == Colors.UNCOLORED.getValue()) {
                ++uncoloredCircles;
            }
        }

        /* Return Evaluation Score */
        return 0 == uncoloredCircles ? moveScore : 0;
    }

    /**
     * Evaluate On Attribute
     * 
     * @param neighbors
     * @param moveScore
     * @param evalAttribute
     * @return evlauationScore
     */
    public int evaluateOnAttributeW(int currentColor, int[] neighbors,
            int negMoveScore, int posMoveScore) {
        int uncoloredCircles = 0;
        /* Iterate Neighbors */
        for (final int neighbor : neighbors) {
            if (Colors.UNCOLORED.getValue() == neighbor) {
                ++uncoloredCircles;
            }
        }

        /* Return Evaluation Score */
        return 0 == uncoloredCircles ? negMoveScore
                : 1 == uncoloredCircles ? posMoveScore : uncoloredCircles;
    }

    /**
     * Evaluate On Position
     * 
     * @param neighbors
     * @param moveScore
     * @param positions
     * @return evlauationScore
     */
    public int evaluateOnPosition(int[] neighbors, int moveScore,
            List<Pair<Integer, Integer>> positions) {
        int evaluationScore = 0;
        /* Iterate Position Pairs */
        for (int i = 0; i < positions.size(); i++) {
            Pair<Integer, Integer> position = positions.get(i);
            if (Colors.UNCOLORED.getValue() != neighbors[position.getLeft()]
                    && Colors.UNCOLORED.getValue() != neighbors[position
                            .getRight()]
                    && neighbors[position.getLeft()] != neighbors[position
                            .getRight()]) {
                evaluationScore += moveScore;
            }
        }
        /* Return Evaluation Score */
        return evaluationScore;
    }

    /**
     * Get Neighbors
     * 
     * @param height
     * @param leftDistance
     * @param currentState
     * @return positions
     */
    public int[] getNeighbors(int height, int leftDistance,
            AtroposState currentState) {
        /*
         *  O O <-- Height + 1 
         * O X O <-- Height 
         *  O O <-- Height - 1
         *  ^ ^ 
         *  | | 
         *  | Left Distance + 1
         * Left Distance
         */
        return new int[] {
                currentState.circles[height + 1][leftDistance - 1].getColor(),
                currentState.circles[height + 1][leftDistance].getColor(),
                currentState.circles[height][leftDistance - 1].getColor(),
                currentState.circles[height][leftDistance + 1].getColor(),
                currentState.circles[height - 1][leftDistance].getColor(),
                currentState.circles[height - 1][leftDistance + 1].getColor() };
    }
}
