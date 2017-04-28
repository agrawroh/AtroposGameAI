package com.agrawroh.game.atropos.state.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.utils.Constants;

/**
 * Board State Reader
 * 
 * @author agraw_ds7m
 * @date 23-APR-2017
 */
public class AtroposStateReader {
    /* Global Variables */
    private static final Pattern PATTERN = Pattern
            .compile(Constants.ARG_PATTERN);

    /**
     * Read State
     * 
     * @param input
     * @return AtroposState
     */
    public AtroposState readState(String input) {
        /* Read * Return Current Board State */
        return new AtroposState(
                getCircles(getBoardPositions(PATTERN.matcher(input))),
                getLastPlay(input.substring(9 + input
                        .indexOf(Constants.LAST_PLAY))));
    }

    /**
     * Get Board Positions
     * 
     * @param matcher
     * @return List<String>
     */
    private List<String> getBoardPositions(Matcher matcher) {
        /* Initialize Board Positions List */
        List<String> boardPositions = new ArrayList<>();

        /* Find Pattern Match(s) */
        while (matcher.find()) {
            boardPositions.add(matcher.group(0));
        }

        /* Return Board Positions String */
        return boardPositions;
    }

    /**
     * Get Last Play
     * 
     * @param lastPlayString
     * @return AtroposCircle
     */
    private AtroposCircle getLastPlay(String lastPlayString) {
        /* Check Whether NULL */
        if (Constants.STR_NULL.equalsIgnoreCase(lastPlayString))
            return null;

        /* Get State Values */
        String[] values = lastPlayString.substring(1,
                lastPlayString.length() - 1).split(Constants.SEPERATOR);

        /* Return Last Play State */
        AtroposCircle lastPlayedCircle = new AtroposCircle(
                Integer.parseInt(values[1]), Integer.parseInt(values[2]),
                Integer.parseInt(values[3]));
        lastPlayedCircle.color(Integer.parseInt(values[0]));
        return lastPlayedCircle;
    }

    /**
     * Get Circles
     * 
     * @param boardPositions
     * @return AtroposCircle[][]
     */
    private AtroposCircle[][] getCircles(List<String> boardPositions) {
        /* Initialize Circles Array */
        AtroposCircle[][] circles = new AtroposCircle[boardPositions.size()][];

        /* Iterate Positions */
        for (int index = boardPositions.size() - 1, counter = 0; index >= 0; index--, counter++) {
            /* Get Board Position String */
            String boardPosition = boardPositions.get(index);
            boardPosition = boardPosition.substring(1,
                    boardPosition.length() - 1);

            /* Initialize #Circles On Current Row */
            circles[counter] = new AtroposCircle[boardPositions.size()];

            int leftPosition = 0 == counter ? 1 : 0;
            int rightPosition = boardPosition.length() - 1;
            /* Iterate Position String; Fill Values */
            for (final char c : boardPosition.toCharArray()) {
                /* Create New Circle Object */
                AtroposCircle circle = new AtroposCircle(
                        Integer.valueOf(Character.toString(c)), counter,
                        leftPosition, rightPosition--);
                /* Set Circle Object */
                circles[counter][leftPosition++] = circle;
            }
        }

        /* Return Circles */
        return circles;
    }
}
