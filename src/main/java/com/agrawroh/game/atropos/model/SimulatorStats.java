package com.agrawroh.game.atropos.model;

import java.util.List;

import com.agrawroh.game.atropos.utils.Constants;

/**
 * Simulator Statistics POJO
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class SimulatorStats {

    private int iterations;
    private int gameSize;
    private int treeDepth;
    private double averageTime;
    private double maxMoveTime;
    private String winningPlayer;
    private double winningRate;

    /**
     * @return the iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * @param iterations
     *            the iterations to set
     */
    public SimulatorStats setIterations(int iterations) {
        this.iterations = iterations;
        return this;
    }

    /**
     * @return the gameSize
     */
    public int getGameSize() {
        return gameSize;
    }

    /**
     * @param gameSize
     *            the gameSize to set
     */
    public SimulatorStats setGameSize(int gameSize) {
        this.gameSize = gameSize;
        return this;
    }

    /**
     * @return the treeDepth
     */
    public int getTreeDepth() {
        return treeDepth;
    }

    /**
     * @param treeDepth
     *            the treeDepth to set
     */
    public SimulatorStats setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
        return this;
    }

    /**
     * @return the averageTime
     */
    public double getAverageTime() {
        return averageTime;
    }

    /**
     * @param averageTime
     *            the averageTime to set
     */
    public SimulatorStats setAverageTime(double averageTime) {
        this.averageTime = averageTime;
        return this;
    }

    /**
     * @return the maxMoveTime
     */
    public double getMaxMoveTime() {
        return maxMoveTime;
    }

    /**
     * @param maxMoveTime
     *            the maxMoveTime to set
     */
    public SimulatorStats setMaxMoveTime(double maxMoveTime) {
        this.maxMoveTime = maxMoveTime;
        return this;
    }

    /**
     * @return the winningPlayer
     */
    public String getWinningPlayer() {
        return winningPlayer;
    }

    /**
     * @param winningPlayer
     *            the winningPlayer to set
     */
    public SimulatorStats setWinningPlayer(String winningPlayer) {
        this.winningPlayer = winningPlayer;
        return this;
    }

    /**
     * @return the winningRate
     */
    public double getWinningRate() {
        return winningRate;
    }

    /**
     * @param winningRate
     *            the winningRate to set
     */
    public SimulatorStats setWinningRate(double winningRate) {
        this.winningRate = winningRate;
        return this;
    }

    /**
     * Accumulator
     * 
     * @return AccumulatedStats
     */
    public SimulatorStats accumulatStats(List<SimulatorStats> simulatorStats) {
        /* Iterate Simulator Statistics */
        for (final SimulatorStats simulatorStat : simulatorStats) {
            this.setIterations(this.getIterations()
                    + simulatorStat.getIterations());
            this.setAverageTime(this.getAverageTime()
                    + simulatorStat.getAverageTime());
            this.setMaxMoveTime(this.getMaxMoveTime()
                    + simulatorStat.getMaxMoveTime());
            this.setWinningRate(this.getWinningRate()
                    + simulatorStat.getWinningRate());
        }

        /* Set Global Properties */
        this.setGameSize(simulatorStats.get(0).getGameSize());
        this.setTreeDepth(simulatorStats.get(0).getTreeDepth());
        this.setWinningPlayer(Constants.DEFAULT_PLAYER_NAME);

        /* Calculate Averages */
        this.setAverageTime(this.getAverageTime() / this.getIterations());
        this.setMaxMoveTime(this.getMaxMoveTime() / this.getIterations());
        this.setWinningRate(this.getWinningRate() / this.getIterations());

        /* Return Accumulated Statistics */
        return this;
    }

    @Override
    public String toString() {
        /* Build & Return String */
        return new StringBuilder().append("Iterations: ").append(iterations)
                .append(" | Board Size: ").append(gameSize)
                .append(" | Tree Depth: ").append(treeDepth)
                .append(" | Average Time: ").append(averageTime)
                .append(" | Max Move Time: ").append(maxMoveTime)
                .append(" | Winning Player: ").append(winningPlayer)
                .append(" | Winning Rate: ").append(winningRate).append("\n")
                .toString();
    }
}
