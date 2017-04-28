package com.agrawroh.game.atropos.player;

import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.state.AtroposState;

/**
 * Player Interface
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public interface IPlayer {
    /**
     * Make Move
     * 
     * @param currentState
     * @return AtroposState
     */
    AtroposState makeMove(AtroposState currentState);

    /**
     * Get Next Play
     * 
     * @param currentState
     * @return AtroposCircle
     */
    AtroposCircle getNextPlay(AtroposState currentState);
}
