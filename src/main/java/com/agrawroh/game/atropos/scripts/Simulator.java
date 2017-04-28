package com.agrawroh.game.atropos.scripts;

import java.util.ArrayList;
import java.util.List;

import com.agrawroh.game.atropos.model.SimulatorStats;
import com.agrawroh.game.atropos.utils.Constants;

/**
 * Simulator
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class Simulator {
    /*********** Driver Method ************/
    public static void main(String[] args) {
        /* Print Statistics On Console */
        System.out.println("********** SIMULATION STATS **********");

        /* Iterate For Board Size */
        for (int boardSize = Constants.BOARD_START_SIZE; boardSize <= Constants.MAX_BOARD_SIZE; boardSize++) {
            /* Iterate For Tree Depth */
            for (int treeDepth = Constants.TREE_DEPTH_START; treeDepth <= Constants.MAX_TREE_DEPTH; treeDepth++) {
                /* Create Simulator Statistics Holder */
                List<SimulatorStats> simulatorStats = new ArrayList<>();

                /* Iterate For Test Iterations */
                for (int iteration = 1; iteration <= Constants.TEST_ITERATIONS; iteration++) {
                    /* Add Simulator Statistics */
                    simulatorStats.add(new AtroposGame().execute(
                            new String[] { Integer.toString(boardSize), },
                            treeDepth));
                }

                /* Add Accumulated Statistics */
                System.out.print(new SimulatorStats()
                        .accumulatStats(simulatorStats));
            }
        }
    }
}
