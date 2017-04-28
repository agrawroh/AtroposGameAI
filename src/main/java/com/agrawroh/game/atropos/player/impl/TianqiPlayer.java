package com.agrawroh.game.atropos.player.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.player.AtroposPlayer;
import com.agrawroh.game.atropos.player.IPlayer;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.state.reader.AtroposStateReader;

/**
 * Tianqi Player
 * 
 * @author Tianqi
 * @date 21-APR-2017
 */
public class TianqiPlayer extends AtroposPlayer implements IPlayer {

    //Declare the variables
    private static final int positiveNumber = 1000; //maximum number possible
    private static final int negativeNumber = -1000; //minimum number possible
    private static final int MAX_DEPTH = 6; //How many levels deep to go on the AB Pruning

    //instance variables

    /**
     * Array of AtroposCircles
     * 
     * The first coordinate is the height, the second is the distance from the
     * left.
     */
    private AtroposCircle[][] circles;

    /**
     * Last-colored circle.
     */
    private AtroposCircle lastPlay;

    //constants

    /**
     * Blank Color.
     */
    private static final int UNCOLORED = 0;

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

    public TianqiPlayer(String name) {
        super(name);
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
        AtroposState nextState = new TianqiPlayer("Tianqi Player")
                .makeMove(state);

        /* Print Next State On Console */
        System.out.println(nextState.lastPlay.getColorLocationString());
    }

    @Override
    public AtroposCircle getNextPlay(AtroposState state) {
        this.circles = state.circles;
        this.lastPlay = state.lastPlay;

        int[][] avail = this.availmove(state.circles.length);
        AtroposCircle bestMove = this.bestmove(avail);
        return bestMove;
    }

    //Print out the the best move based on the last move 
    // (color,height,leftdistance,rightdistance) 
    public String printString(AtroposCircle bestMove) {
        int height = bestMove.height();
        int leftdistance = bestMove.leftDistance();
        int rightdistance = bestMove.rightDistance();
        int color = bestMove.getColor();
        String string = "(";
        string = string + Integer.toString(color) + ",";
        string = string + Integer.toString(height) + ",";
        string = string + Integer.toString(leftdistance) + ",";
        string = string + Integer.toString(rightdistance) + ")";
        String bestmove = string;
        System.out.println(bestmove);
        return bestmove;
    }

    /**
     * Make Move
     * 
     * @param currentState
     * @return AtroposState
     */
    public AtroposState makeMove(AtroposState currentState) {
        /* Check Whether Moves Possible */
        if (0 == currentState.getPlayableCircles().size()) {
            return currentState;
        }

        /* Make Next Move */
        AtroposState modifiedState = currentState
                .makePlay(getNextPlay(currentState)) ? currentState : null;

        /* Return Modified Board Configuration */
        return modifiedState;
    }

    /**
     * Find all the available moves and put it in a AtroposState circle
     * 
     * @param the
     *            size of the board
     * 
     */
    public int[][] availmove(int size) {
        // Create a same size 2d array and store the availmove in the same position as the gamer board based on the last move
        int[][] avail = new int[size][size];
        // check to see if the lastplay is not null
        /*
         * System.out.println("Size: " + size + " Last Play Height: " +
         * lastPlay.height() + " Left Distance: " + lastPlay.leftDistance());
         */
        if (this.lastPlay != null) {
            // get the index of the move
            int height = this.lastPlay.height();
            int leftdistance = this.lastPlay.leftDistance();
            // go through every possibility to find the corrdinates
            // above right
            if (this.circles[height + 1][leftdistance].getColor() == 0) {
                avail[height + 1][leftdistance] = 1;
            } else {
                avail[height + 1][leftdistance] = 0;
            }
            // below left
            if (this.circles[height - 1][leftdistance].getColor() == 0) {
                avail[height - 1][leftdistance] = 1;
            } else {
                avail[height - 1][leftdistance] = 0;
            }
            // left
            if (this.circles[height][leftdistance - 1].getColor() == 0) {
                avail[height][leftdistance - 1] = 1;
            } else {
                avail[height][leftdistance - 1] = 0;
            }
            // right
            if (this.circles[height][leftdistance + 1].getColor() == 0) {
                avail[height][leftdistance + 1] = 1;
            } else {
                avail[height][leftdistance + 1] = 0;
            }
            // above left
            if (this.circles[height + 1][leftdistance - 1].getColor() == 0) {
                avail[height + 1][leftdistance - 1] = 1;
            } else {
                avail[height + 1][leftdistance - 1] = 0;
            }
            // below right
            if (this.circles[height - 1][leftdistance + 1].getColor() == 0) {
                avail[height - 1][leftdistance + 1] = 1;
            } else {
                avail[height - 1][leftdistance + 1] = 0;
            }

            // if there is no adjacent circles, choose the next available circle
            if (avail[height + 1][leftdistance] == 0
                    && avail[height - 1][leftdistance] == 0
                    && avail[height][leftdistance - 1] == 0
                    && avail[height][leftdistance + 1] == 0
                    && avail[height + 1][leftdistance - 1] == 0
                    && avail[height - 1][leftdistance + 1] == 0) {
                for (int i = 1; i < size - 1; i++) {
                    for (int j = 1; j < size - i; j++) {
                        if (this.circles[i][j].getColor() == 0) {
                            avail[i][j] = 1;
                        }
                    }
                }
            }
            return avail;
        }
        // if lastplay is null, do nothing.
        else {
            return avail;
        }
    }

    /**
     * Go through all the available moves and find the best move Usingthe minmax
     * and alpha-beta pruning to find the best score move
     * 
     * @param [][]avail contains all the availmoves base on the last move
     */
    public AtroposCircle bestmove(int[][] avail) {

        // initialize max to a loss
        int max = negativeNumber;
        int score = max;
        // make a clone of the current circle
        TianqiPlayer clone = this.clone();
        // declare new variable to set to (0,0,0,0)
        AtroposCircle bestMove = new AtroposCircle(0, 0, 0, 0);

        // Find all available moves based on the last move
        // go through each option
        int size = avail[0].length;
        if (this.lastPlay != null) {
            int c = 0;
            int h = 0;
            int l = 0;
            // the tracker keeps track of the not losing move incase the Player lose
            int tracker = 0;
            for (int i = 1; i <= size - 1; i++) {
                for (int j = 1; j <= size - 1; j++) {
                    //go through each color
                    for (int color = 1; color < 4; color++) {
                        //if coordinates are valid
                        if (avail[i][j] == 1) {
                            if (clone.makePlay(i, j, color) == true
                                    && clone.isFinished() == false) {
                                // indicates that the Player won't lose in the next move
                                tracker++;
                                //update the available into c, h, j in order to avoid an unnormal invaild move
                                c = color;
                                h = i;
                                l = j;
                                // Run the minMax and ap-Pruning to update the score and cut the impossible move
                                score = Math.max(
                                        score,
                                        minMax(clone, MAX_DEPTH,
                                                negativeNumber, positiveNumber,
                                                size));
                                //If a win, set as best move and remove the color
                                if (clone.eval(size) == negativeNumber) {
                                    bestMove = new AtroposCircle(color, i, j,
                                            size - i - j);
                                    clone.circles[i][j] = new AtroposCircle(0,
                                            i, j, size - i - j);
                                    clone.lastPlay = this.lastPlay;
                                }
                                //reset max and bestMove according to highest value
                                if (score > max) {
                                    bestMove = new AtroposCircle(color, i, j,
                                            size - i - j);
                                    max = score;
                                }
                            }
                            // Reset the board position to 0
                            clone.circles[i][j] = new AtroposCircle(0, i, j,
                                    size - i - j);
                            clone.lastPlay = this.lastPlay;
                        }
                    }
                }
            }
            /*
             * the tracker keeps track of the not losing moves, if tracker == 0
             * then we lose return a move based on the available spots
             */
            if (bestMove.getColor() == 0 && tracker == 0) {
                for (int i = 1; i < size - 1; i++) {
                    for (int j = 1; j < size - 1; j++) {
                        if (avail[i][j] == 1) {
                            bestMove = new AtroposCircle(2, i, j, size - i - j);
                        }
                    }
                }
            }
            /*
             * Check is the AI Plays a unavailable move If it is unvailable,
             * choose the last available move.
             */
            if (bestMove.getColor() == 0) {
                bestMove = new AtroposCircle(c, h, l, size - h - l);
            }
            return bestMove;
        }

        // If we need to play the first move, randomly choose a available move
        else {
            int height = (int) (Math.random() * (size - 3) + 1);
            int leftdistance = (int) (Math.random() * (size - height - 1) + 1);
            int color = (int) (Math.random() * 3 + 1);
            bestMove = new AtroposCircle(color, height, leftdistance, size
                    - height - leftdistance);
            clone.makePlay(bestMove);
            // If we lose after play the first move, we randomly choose another move until it is available and not lose.
            while (clone.isFinished() == true) {
                clone.circles[height][leftdistance] = new AtroposCircle(0,
                        height, leftdistance, size - height - leftdistance);
                clone.lastPlay = this.lastPlay;
                height = (int) (Math.random() * (size - 3) + 1);
                leftdistance = (int) (Math.random() * (size - height - 1) + 1);
                color = (int) (Math.random() * 3 + 1);
                bestMove = new AtroposCircle(color, height, leftdistance, size
                        - height - leftdistance);
                clone.makePlay(bestMove);
            }
            //reset the position to 0
            //clone.circles[height][leftdistance] = new AtroposCircle(0,height,leftdistance,size-height-leftdistance);
            clone.lastPlay = this.lastPlay;
            return bestMove;
        }
    }

    /**
     * evaluate the AtroposState per move
     * 
     * we eval
     */
    public int eval(int size) {
        //if you lose
        if (this.isFinished()) {
            return negativeNumber;
        }

        //get the height and leftdistance of the last move
        int height = this.lastPlay.height();
        int leftdistance = this.lastPlay.leftDistance();
        int middlecolor = this.circles[height][leftdistance].getColor();
        int leftcolor = this.circles[height][leftdistance - 1].getColor();
        int rightcolor = this.circles[height][leftdistance + 1].getColor();
        int rightupcolor = this.circles[height + 1][leftdistance].getColor();
        int leftupcolor = this.circles[height + 1][leftdistance - 1].getColor();
        int rightdowncolor = this.circles[height - 1][leftdistance + 1]
                .getColor();
        int leftdowncolor = this.circles[height - 1][leftdistance].getColor();

        // see how many of the other options are filled. The more that are filled, the higher the score of this move
        //similar approach to finding all options
        int score = 0;
        //Above right
        if (this.circles[height + 1][leftdistance].getColor() != 0) {
            score += 10;
        }
        //Below left
        if (this.circles[height - 1][leftdistance].getColor() != 0) {
            score += 10;
        }
        //left
        if (this.circles[height][leftdistance - 1].getColor() != 0) {
            score += 10;
        }
        //right
        if (this.circles[height][leftdistance + 1].getColor() != 0) {
            score += 10;
        }
        //above left
        if (this.circles[height + 1][leftdistance - 1].getColor() != 0) {
            score += 10;
        }
        //below right
        if (this.circles[height - 1][leftdistance + 1].getColor() != 0) {
            score += 10;
        }

        // if the neighbour has a different color
        if (leftcolor != leftupcolor) {
            score += 5;
        }
        if (leftcolor != leftdowncolor) {
            score += 5;
        }
        if (leftdowncolor != rightdowncolor) {
            score += 5;
        }
        if (rightdowncolor != rightcolor) {
            score += 5;
        }
        if (rightcolor != rightupcolor) {
            score += 5;
        }
        if (rightupcolor != leftupcolor) {
            score += 5;
        }

        // if the neighbours has the same color with the current circle
        if (leftupcolor == middlecolor) {
            score--;
        }
        if (leftcolor == middlecolor) {
            score--;
        }
        if (leftdowncolor == middlecolor) {
            score--;
        }
        if (rightdowncolor == middlecolor) {
            score--;
        }
        if (rightcolor == middlecolor) {
            score--;
        }
        if (rightupcolor == middlecolor) {
            score--;
        }

        return score;
    }

    //MinMax method with AB Pruning
    public int minMax(TianqiPlayer clone, int depth, int alpha, int beta,
            int size) {
        // if the playerMove is done, one winner has won, or it has reach the max depth
        // then return the evaulation
        //System.out.println("depth");
        //System.out.println(depth);
        if (depth == 0 || this.isFinished()) {
            return clone.eval(size);
            //return new NinjaEvaluator().evaluateMove(new AtroposState(circles,lastPlay));
        }

        //if it's the AI's turn 
        else if (depth % 2 == 0) {
            /*
             * Find out all possible moves based on the lastmove and store them
             * into a 2d array with the same position as the board 2d array.
             */
            int[][] availboard = clone.availmove(size);
            int score = negativeNumber;
            AtroposCircle lastplay = clone.lastPlay;
            for (int i = 1; i < size - 1; i++) {
                for (int j = 0; j < size; j++) {
                    for (int color = 1; color < 4; color++) {
                        if (availboard[i][j] == 1) {
                            if (clone.makePlay(i, j, color) == true
                                    && clone.isFinished() == false) {
                                //System.out.println(clone);
                                score = Math.max(
                                        score,
                                        minMax(clone, depth - 1,
                                                negativeNumber, positiveNumber,
                                                size));
                                //set alpha
                                alpha = Math.max(alpha, score);
                                clone.circles[i][j] = new AtroposCircle(0, i,
                                        j, size - i - j);
                                clone.lastPlay = lastplay;
                                //if alpha and beta overlap, then finish the recurrence
                                //AB pruning
                                if (beta < alpha) {
                                    clone.circles[i][j] = new AtroposCircle(0,
                                            i, j, size - i - j);
                                    clone.lastPlay = lastplay;
                                    return alpha;
                                }
                            }
                            clone.circles[i][j] = new AtroposCircle(0, i, j,
                                    size - i - j);
                            clone.lastPlay = lastplay;
                        }
                    }
                }
            }
            return alpha;
        }
        // If it's oppoents' turn 
        else {
            //System.out.println("player's move");
            int[][] availboard = clone.availmove(size);
            int score = positiveNumber;
            AtroposCircle lastplay = clone.lastPlay;
            for (int i = 1; i < size - 1; i++) {
                for (int j = 0; j < size; j++) {
                    for (int color = 1; color < 4; color++) {
                        if (availboard[i][j] == 1) {
                            if (clone.makePlay(i, j, color) == true
                                    && clone.isFinished() == false) {
                                //System.out.println(clone);
                                score = Math.min(
                                        score,
                                        minMax(clone, depth - 1,
                                                negativeNumber, positiveNumber,
                                                size));
                                //set alpha
                                beta = Math.min(beta, score);
                                clone.circles[i][j] = new AtroposCircle(0, i,
                                        j, size - i - j);
                                clone.lastPlay = lastplay;
                                //if alpha and beta overlap, then finish the recurrence
                                if (beta < alpha) {
                                    clone.circles[i][j] = new AtroposCircle(0,
                                            i, j, size - i - j);
                                    clone.lastPlay = lastplay;
                                    return beta;
                                }
                            }
                            clone.circles[i][j] = new AtroposCircle(0, i, j,
                                    size - i - j);
                            clone.lastPlay = lastplay;
                        }
                    }
                }
            }
            return beta;
        }
    }

    /**
     * Makes a deep clone of this object.
     */
    public TianqiPlayer clone() {
        TianqiPlayer clone;
        if (this.lastPlay != null) {
            clone = new TianqiPlayer(this.circles, this.lastPlay.clone());
        } else {
            clone = new TianqiPlayer(this.circles, null);
        }
        return clone;
    }

    //public methods

    /**
     * Class constructor.
     * 
     * @param circles
     *            The laid-out circles.
     * @param lastPlay
     *            The last play on the board.
     */
    public TianqiPlayer(AtroposCircle[][] circles, AtroposCircle lastPlay) {
        super("Default Name");
        this.circles = new AtroposCircle[circles.length][circles.length];
        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[i].length; j++) {
                if (circles[i][j] != null) {
                    this.circles[i][j] = circles[i][j].clone();
                }
            }
        }
        this.lastPlay = null;
        if (lastPlay != null) {
            this.lastPlay = lastPlay.clone();
        }
    }

    /**
     * Checks to see whether a circle is a valid move.
     * 
     */
    public boolean isLegalPlay(AtroposCircle play) {
        //make sure the color is legal
        int color = play.getColor();
        if (color < 1 || color > 3) {
            return false;
        }
        //check that the dimensions add up
        return this.isLegalPlayLocation(play.height(), play.leftDistance());
    }

    /**
     * Performs a move on the board.
     * 
     * @param play
     *            Next move to make.
     */
    public boolean makePlay(AtroposCircle play) {
        return this.makePlay(play.height(), play.leftDistance(),
                play.getColor());
    }

    /**
     * Performs a move on the board.
     * 
     * @param height
     *            Height of the circle to play.
     * @param leftDistance
     *            Distance of the circle from the left.
     * @param color
     *            Color to play.
     */
    public boolean makePlay(int height, int leftDistance, int color) {
        if (this.isFinished()) {
            return false;
        }
        /*
         * if (!this.isLegalPlayLocation(height, leftDistance)) {
         * System.err.println("This is not a legal move!"); return false; }
         */
        this.colorCircle(height, leftDistance, color);
        this.lastPlay = this.circleAt(height, leftDistance);
        return true;
    }

    /**
     * Determines whether the game is over.
     */
    public boolean isFinished() {
        if (this.lastPlay == null) {
            return false;
        }
        //System.out.println("start checking if is finished");
        int middleColor = this.lastPlay.getColor();
        //System.out.println(middleColor);
        int height = this.lastPlay.height();
        int leftDistance = this.lastPlay.leftDistance();
        int leftUpColor = this.circles[height + 1][leftDistance - 1].getColor();
        //System.out.println(leftUpColor);
        int leftColor = this.circles[height][leftDistance - 1].getColor();
        //System.out.println(leftColor);
        int leftDownColor = this.circles[height - 1][leftDistance].getColor();
        //System.out.println(leftDownColor);
        int rightDownColor = this.circles[height - 1][leftDistance + 1]
                .getColor();
        //System.out.println(rightDownColor);
        int rightColor = this.circles[height][leftDistance + 1].getColor();
        //System.out.println(rightColor);
        int rightUpColor = this.circles[height + 1][leftDistance].getColor();
        //System.out.println(rightUpColor);

        return ((this.colorConflict(middleColor, leftUpColor)
                && this.colorConflict(middleColor, leftColor) && this
                    .colorConflict(leftUpColor, leftColor))
                || (this.colorConflict(middleColor, leftColor)
                        && this.colorConflict(middleColor, leftDownColor) && this
                            .colorConflict(leftColor, leftDownColor))
                || (this.colorConflict(middleColor, leftDownColor)
                        && this.colorConflict(middleColor, rightDownColor) && this
                            .colorConflict(leftDownColor, rightDownColor))
                || (this.colorConflict(middleColor, rightDownColor)
                        && this.colorConflict(middleColor, rightColor) && this
                            .colorConflict(rightDownColor, rightColor))
                || (this.colorConflict(middleColor, rightColor)
                        && this.colorConflict(middleColor, rightUpColor) && this
                            .colorConflict(rightColor, rightUpColor)) || (this
                .colorConflict(middleColor, rightUpColor)
                && this.colorConflict(middleColor, leftUpColor) && this
                    .colorConflict(rightUpColor, leftUpColor)));
    }

    /**
     * Options for the next play
     */
    public Iterator<AtroposCircle> playableCircles() {
        Vector<AtroposCircle> vector = new Vector<AtroposCircle>();
        for (int height = 1; height < this.circles.length; height++) {
            for (int leftDistance = 1; leftDistance < this.circles.length
                    - height; leftDistance++) {
                if (this.isLegalPlayLocation(height, leftDistance)) {
                    vector.add(this.circleAt(height, leftDistance));
                }
            }
        }
        return vector.iterator();
    }

    //private methods

    /**
     * Checks to see whether a location is a valid place to make the next move.
     * 
     */
    private boolean isLegalPlayLocation(int height, int leftDistance) {
        if (this.isFinished()) {
            System.out.println("isfinished");
            return false;
        }
        // Check is the move is out of the board
        AtroposCircle circle = this.circleAt(height, leftDistance);
        if (!circle.insideBoardOfSize(this.circles.length)) {
            System.out.println(this.circles.length);
            System.out.println("outofboardofsize");
            return false;
        }
        //check is the move was already played
        if (circle.isColored()) {
            System.out.println("iscolored");
            return false;
        }
        //
        if (this.canPlayAnywhere()) {
            return true;
        } else {
            return circle.adjacentTo(this.lastPlay);
        }
    }

    /**
     * Checks to see if the next play can be anywhere.
     */
    private boolean canPlayAnywhere() {
        // Check is the game is finished
        if (this.isFinished()) {
            return false;
        }
        // Check if it is the first play 
        if (this.lastPlay == null) {
            return true;
        }
        int height = this.lastPlay.height();
        int leftDistance = this.lastPlay.leftDistance();
        return ((this.circles[height - 1][leftDistance].isColored())
                && (this.circles[height - 1][leftDistance + 1].isColored())
                && (this.circles[height][leftDistance + 1].isColored())
                && (this.circles[height + 1][leftDistance].isColored())
                && (this.circles[height + 1][leftDistance - 1].isColored()) && (this.circles[height][leftDistance - 1]
                .isColored()));
    }

    /** Determines whether the two colors are not equal if both are colored. */
    private boolean colorConflict(int colorOne, int colorTwo) {
        return ((colorOne != UNCOLORED) && (colorTwo != UNCOLORED) && (colorOne != colorTwo));
    }

    /**
     * Finds the circle at a certain location.
     */
    private AtroposCircle circleAt(int height, int leftDistance) {
        return this.circles[height][leftDistance];
    }

    /**
     * Colors a given circle.
     * 
     * @param height
     *            Height of the circle.
     * @param leftDistance
     *            Distance of the circle from the left.
     * @param color
     *            New color for the circle.
     */
    private void colorCircle(int height, int leftDistance, int color) {
        // Check if the move was already played.
        if (this.circles[height][leftDistance].isColored()) {
            System.out.println(height);
            System.out.println(leftDistance);
            System.err.println("Error!  This circle is already colored!");
            return;
        }
        // If the color is ilegal, print out an error
        if (color < 0 || color > 3) {
            System.err.println("Error!  This is not a legal color!");
        }
        this.circles[height][leftDistance].color(color);
    }

    //toString

    /**
     * Returns a string version of this.
     * 
     * @param indent
     *            Indentation string.
     */
    public String toString(String indent) {
        String string = "";
        for (int i = this.circles.length - 1; i >= 0; i--) {
            //set up some nice spacing.
            for (int space = 0; space < 2 * (i - 1); space++) {
                string += " ";
            }
            if (i == 0) {
                string += "  ";
            }
            string += "[";
            for (int j = 0; j < this.circles.length; j++) {
                if (this.circles[i][j] != null) {
                    string += this.circles[i][j].getColor();
                    if (j + i < this.circles.length) {
                        if (i != 0 || j != this.circles.length - 1) {
                            string += "   ";
                        }
                    }
                }
            }
            string += "]\n";
        }
        string += "Last Play: ";
        if (this.lastPlay == null) {
            string += "null";
        } else {
            string += this.lastPlay.getColorLocationString();
        }
        string += "\n";
        return string;
    }

    /**
     * Returns a string version of this.
     */
    public String toString() {
        return this.toString("");
    }

}
