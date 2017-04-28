package com.agrawroh.game.atropos.player.impl;

import java.io.IOException;

import com.agrawroh.game.atropos.core.ICoreAlgorithm;
import com.agrawroh.game.atropos.core.impl.AlphaBeta;
import com.agrawroh.game.atropos.evaluator.impl.NinjaEvaluator;
import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.player.AtroposPlayer;
import com.agrawroh.game.atropos.player.IPlayer;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.state.reader.AtroposStateReader;
import com.agrawroh.game.atropos.utils.Constants;

/**
 * Player Logic
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class RohitPlayer extends AtroposPlayer implements IPlayer {

    /********* Global Variable(s) *********/
    private final ICoreAlgorithm algorithm;

    /************** Default Constructor ***************/
    public RohitPlayer() {
        this(new AlphaBeta(Constants.DEFAULT_TREE_DEPTH, new NinjaEvaluator()));
    }

    /**************** Copy Constructor ****************/
    public RohitPlayer(String name, ICoreAlgorithm algorithm) {
        super(name);
        this.algorithm = algorithm;
    }

    public RohitPlayer(ICoreAlgorithm algorithm) {
        super(Constants.DEFAULT_PLAYER_NAME);
        this.algorithm = algorithm;
    }

    public RohitPlayer(String name) {
        super(name);
        this.algorithm = new AlphaBeta(Constants.DEFAULT_TREE_DEPTH,
                new NinjaEvaluator());
    }

    /**
     * Main Method
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /* Read Current State */
        AtroposState state = new AtroposStateReader().readState(args[0]);

        /* Make Next Move */
        AtroposState nextState = new RohitPlayer(new AlphaBeta(
                Constants.TREE_DEPTH, new NinjaEvaluator())).makeMove(state);

        /* Print Next State On Console */
        System.out.println(nextState.lastPlay.getColorLocationString());
    }

    /**
     * Make Move
     * 
     * @param currentState
     * @return AtroposState
     */
    @Override
    public AtroposState makeMove(AtroposState currentState) {
        /* Check Whether Moves Possible */
        if (0 == currentState.getPlayableCircles().size()) {
            return currentState;
        }

        /* Make Next Move */
        currentState.makePlay(getNextPlay(currentState));

        /* Return Modified Board Configuration */
        return currentState;
    }

    /**
     * Get Next Move
     * 
     * @param currentState
     * @return AtroposCircle
     */
    @Override
    public AtroposCircle getNextPlay(AtroposState currentState) {
        /* Return Best Circle */
        return algorithm.getNextMove(currentState);
    }
}
