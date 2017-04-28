package com.agrawroh.game.atropos.controller;

import java.io.IOException;

import com.agrawroh.game.atropos.core.impl.AlphaBeta;
import com.agrawroh.game.atropos.evaluator.impl.NinjaEvaluator;
import com.agrawroh.game.atropos.player.IPlayer;
import com.agrawroh.game.atropos.player.impl.RohitPlayer;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.state.reader.AtroposStateReader;
import com.agrawroh.game.atropos.utils.Constants;

/**
 * Main Controller
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class MainController {
    /* Global Variable */
    private static final IPlayer AI_PLAYER = new RohitPlayer(new AlphaBeta(
            Constants.TREE_DEPTH, new NinjaEvaluator()));

    /**
     * @param args
     *            - the command-line arguments
     * @throws IOException
     *             - if there is an error processing the command-line arguments
     */
    public static void main(String[] args) throws IOException {
        /* Read Current State */
        AtroposState state = new AtroposStateReader().readState(args[0]);

        /* Make Next Move */
        AtroposState nextState = AI_PLAYER.makeMove(state);

        /* Print Next State On Console */
        System.out.println(nextState.lastPlay.getColorLocationString());
    }
}
