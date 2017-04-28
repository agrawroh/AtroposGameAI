package com.agrawroh.game.atropos.player;

import java.util.Iterator;
import java.util.Vector;

import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.model.Colors;
import com.agrawroh.game.atropos.state.AtroposState;

public class AtroposPlayer {

    //instance variables

    /**
     * Indicates the level of playing ability of this opponent.
     */
    //private int level;

    /**
     * Name of this player.
     */
    protected String name;

    /**
     * Color Red.
     */
    public static final int RED = 1;

    /**
     * Color Blue.
     */
    public static final int BLUE = 2;

    /**
     * Color Green.
     */
    public static final int GREEN = 3;

    //public methods

    /**
     * Class constructor.
     * 
     * @param paramname
     *            Param description.
     */
    public AtroposPlayer(String name) {
        this.name = name;
    }

    /**
   * 
   */
    public AtroposCircle getNextPlay(AtroposState state) {
        Vector<AtroposCircle> circles = new Vector<>();
        AtroposCircle circle;
        int randomIndex;
        for (Iterator<AtroposCircle> circleIterator = state.playableCircles(); circleIterator
                .hasNext();) {
            circle = (AtroposCircle) circleIterator.next();
            if (!this.wouldLose(state.clone(), circle.clone(),
                    Colors.RED.getValue())
                    || !this.wouldLose(state.clone(), circle.clone(),
                            Colors.BLUE.getValue())
                    || !this.wouldLose(state.clone(), circle.clone(),
                            Colors.GREEN.getValue())) {
                circles.add(circle);
            }
        }
        if (circles.isEmpty()) {
            //no moves are safe.  Time to lose
            Iterator<AtroposCircle> circleIterator = state.playableCircles();
            circle = (AtroposCircle) circleIterator.next();
            randomIndex = Colors.RED.getValue();
        } else {
            randomIndex = (int) Math.floor(circles.size() * Math.random());
            circle = (AtroposCircle) circles.get(randomIndex);
            //choose a random color
            randomIndex = (int) Math.floor(3 * Math.random()) + 1;
            while (this.wouldLose(state.clone(), circle.clone(), randomIndex)) {
                randomIndex = (int) Math.floor(3 * Math.random()) + 1;
            }
        }
        circle = circle.clone();
        circle.color(randomIndex);
        return circle;
    }

    /**
     * Returns the name of this player.
     */
    public String getName() {
        return this.name;
    }

    //private methods

    /**
     * Determines whether the specified play would lose.
     */
    private boolean wouldLose(AtroposState state, AtroposCircle circle,
            int color) {
        circle.color(color);
        state.makePlay(circle);
        return state.isFinished();
    }
}