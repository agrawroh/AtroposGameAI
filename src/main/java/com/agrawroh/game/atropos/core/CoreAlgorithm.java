package com.agrawroh.game.atropos.core;

import java.util.EnumSet;
import java.util.Set;

import com.agrawroh.game.atropos.evaluator.IEvaluator;
import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.model.Colors;
import com.agrawroh.game.atropos.state.AtroposState;

/**
 * Core Algorithm
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class CoreAlgorithm {
    /* Global Variables */
    private final int treeDepth;
    private IEvaluator evaluator;

    /************ Copy Constructor ************/
    public CoreAlgorithm(int treeDepth, IEvaluator evaluator) {
        this.treeDepth = treeDepth;
        this.evaluator = evaluator;
    }

    /**
     * Evaluate Corners
     * 
     * @param currentState
     * @return AtroposCircle
     */
    public AtroposCircle evaluateCorners(AtroposState currentState) {
        /* Define Best Circle Holder */
        AtroposCircle bestCircle = null;

        /* Check Whether Corners Should Be Evaluated */
        if (null != currentState.lastPlay
                && !validateNeighbors(this.evaluator.getNeighbors(
                        currentState.lastPlay.height(),
                        currentState.lastPlay.leftDistance(), currentState))) {
            return null;
        }

        /* Bottom-Left Corner */
        /*
         *     G B
         *    R O G
         *   G O O B
         *  R O O O G
         * G X O O O B <-- X = Selection
         *  R B R B R
         * 
         */
        if (!currentState.circleIsColored(1, 1)
                && !currentState.circleIsColored(1, 2)
                && !currentState.circleIsColored(2, 1)) {
            bestCircle = currentState.circles[1][1].clone();
            bestCircle.color(Colors.RED.getValue());
            return bestCircle;
        }

        /* Bottom-Right Corner */
        /*
         *     G B
         *    R O G
         *   G O O B
         *  R O O O G
         * G O O O X B <-- X = Selection
         *  R B R B R
         *  
         */
        if (!currentState.circleIsColored(1, currentState.circles.length - 2)
                && !currentState.circleIsColored(1,
                        currentState.circles.length - 3)
                && !currentState.circleIsColored(2,
                        currentState.circles.length - 3)) {
            bestCircle = currentState.circles[1][currentState.circles.length - 2]
                    .clone();
            bestCircle.color(Colors.BLUE.getValue());
            return bestCircle;
        }

        /* Top Corner */
        /*
         *     G B
         *    R X G    <-- X = Selection
         *   G O O B
         *  R O O O G
         * G O O O O B
         *  R B R B R
         *  
         */
        if (!currentState.circleIsColored(currentState.circles.length - 2, 1)
                && !currentState.circleIsColored(
                        currentState.circles.length - 3, 1)
                && !currentState.circleIsColored(
                        currentState.circles.length - 3, 2)) {
            bestCircle = currentState.circles[currentState.circles.length - 2][1]
                    .clone();
            bestCircle.color(Colors.GREEN.getValue());
            return bestCircle;
        }

        /* Return Null */
        return bestCircle;
    }

    /**
     * Get Color Set
     * 
     * @param circle
     * @return Set<Colors>
     */
    public Set<Colors> getColorSet(AtroposCircle circle, int flag) {
        /* Inner-Bottom Boundary */
        /*
         *     G B
         *    R O G    
         *   G O O B
         *  R O O O G
         * G X X X X B <-- X = Selection; No GREEN!
         *  R B R B R
         *  
         */
        if (1 == circle.height()) {
            return 0 == flag ? EnumSet.of(Colors.RED, Colors.BLUE) : EnumSet
                    .of(Colors.UNCOLORED, Colors.RED, Colors.BLUE);
        }

        /* Inner-Left Boundary */
        /*
         *     G B
         *    R X G    <-- X = Selection; No BLUE!
         *   G X O B
         *  R X O O G
         * G X O O O B
         *  R B R B R
         *  
         */
        if (1 == circle.leftDistance()) {
            return 0 == flag ? EnumSet.of(Colors.RED, Colors.GREEN) : EnumSet
                    .of(Colors.UNCOLORED, Colors.RED, Colors.GREEN);
        }

        /* Inner-Right Boundary */
        /*
         *     G B
         *    R X G    <-- X = Selection; No RED!
         *   G O X B
         *  R O O X G
         * G O O O X B
         *  R B R B R
         *  
         */
        if (1 == circle.rightDistance()) {
            return 0 == flag ? EnumSet.of(Colors.GREEN, Colors.BLUE) : EnumSet
                    .of(Colors.UNCOLORED, Colors.GREEN, Colors.BLUE);
        }

        /* Bottom-Left Corner */
        /*
         *     G B
         *    R O G    
         *   G O O B
         *  R O O O G
         * G X O O O B <-- X = Selection; No BLUE, GREEN!
         *  R B R B R
         *  
         */
        if (1 == circle.height() && 1 == circle.leftDistance()) {
            return 0 == flag ? EnumSet.of(Colors.RED) : EnumSet.of(
                    Colors.UNCOLORED, Colors.RED);
        }

        /* Bottom-Right Corner */
        /*
         *     G B
         *    R O G    
         *   G O O B
         *  R O O O G
         * G O O O X B <-- X = Selection; No RED, GREEN!
         *  R B R B R
         *  
         */
        if (1 == circle.height() && 1 == circle.rightDistance()) {
            return 0 == flag ? EnumSet.of(Colors.BLUE) : EnumSet.of(
                    Colors.UNCOLORED, Colors.BLUE);
        }

        /* Top Corner */
        /*
         *     G B
         *    R X G    <-- X = Selection; No RED, BLUE!
         *   G O O B   
         *  R O O O G  
         * G O O O O B 
         *  R B R B R
         *  
         */
        if (1 == circle.rightDistance() && 1 == circle.leftDistance()) {
            return 0 == flag ? EnumSet.of(Colors.GREEN) : EnumSet.of(
                    Colors.UNCOLORED, Colors.GREEN);
        }

        /* Fall-Back; Return RGB */
        return 0 == flag ? EnumSet.of(Colors.RED, Colors.GREEN, Colors.BLUE)
                : EnumSet.of(Colors.UNCOLORED, Colors.RED, Colors.GREEN,
                        Colors.BLUE);
    }

    /**
     * Validate Neighbors
     * 
     * @param neighbors
     * @return success
     */
    private boolean validateNeighbors(int[] neighbors) {
        /* Iterate Neighbors */
        for (final int neighbor : neighbors) {
            /* Check Whether Uncolored */
            if (0 == neighbor) {
                return false;
            }
        }

        /* Return True */
        return true;
    }

    /**
     * Get Evaluator
     * 
     * @return evaluator
     */
    public IEvaluator getEvaluator() {
        return evaluator;
    }

    /**
     * Set Evaluator
     * 
     * @param evaluator
     *            The Evaluator To Set
     */
    public void setEvaluator(IEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Get Tree Depth
     * 
     * @return the treeDepth
     */
    public int getTreeDepth() {
        return treeDepth;
    }
}
