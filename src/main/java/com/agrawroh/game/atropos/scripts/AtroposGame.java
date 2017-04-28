package com.agrawroh.game.atropos.scripts;

import java.util.Random;
import java.util.Stack;

import com.agrawroh.game.atropos.core.impl.AlphaBeta;
import com.agrawroh.game.atropos.evaluator.impl.NinjaEvaluator;
import com.agrawroh.game.atropos.model.AtroposCircle;
import com.agrawroh.game.atropos.model.SimulatorStats;
import com.agrawroh.game.atropos.player.AtroposPlayer;
import com.agrawroh.game.atropos.player.impl.RohitPlayer;
import com.agrawroh.game.atropos.player.impl.TianqiPlayer;
import com.agrawroh.game.atropos.state.AtroposState;
import com.agrawroh.game.atropos.utils.Constants;

public class AtroposGame {

    //instance variables

    /**
     * Stack of all the plays
     */
    private static Stack<AtroposCircle> plays;

    /**
     * Game State.
     */
    private static AtroposState gameState;

    /**
     * Array of players
     */
    private static AtroposPlayer[] players;

    /**
     * Player 1.
     */
    private static AtroposPlayer playerOne;

    /**
     * Player 2.
     */
    private static AtroposPlayer playerTwo;

    /**
     * Player's turn.
     */
    private int currentPlayer;

    //constants
    private static double totalTime = 0;
    private static double maxMoveTime = 0;
    private static int totalMoves = 0;
    private static boolean oneFirst;

    //public methods

    /**
     * Class constructor.
     * 
     * @param paramname
     *            Param description.
     */
    public AtroposGame(int size, AtroposPlayer atroposPlayerOne,
            AtroposPlayer atroposPlayerTwo) {
        gameState = new AtroposState(size);
        playerOne = atroposPlayerOne;
        playerTwo = atroposPlayerTwo;
        plays = new Stack<AtroposCircle>();
        currentPlayer = 1;
        players = new AtroposPlayer[2];
        players[0] = playerOne;
        players[1] = playerTwo;
    }

    public AtroposGame() {
        /* Default Constructor */
    }

    /**
     * Requests the next play from the current player, then applies it to the
     * current game.
     */
    public void makeNextPlay() {
        AtroposCircle nextPlay;
        if (gameState.isFinished()) {
            System.err.println("This game is already over!");
        }
        long timeBefore = System.currentTimeMillis();
        nextPlay = players[currentPlayer - 1].getNextPlay(gameState.clone());
        double timeTaken = (System.currentTimeMillis() - timeBefore) / 1000D;
        if (Constants.DEFAULT_PLAYER_NAME
                .equalsIgnoreCase(players[currentPlayer - 1].getName())) {
            totalTime += timeTaken;
            maxMoveTime = Math.max(maxMoveTime, timeTaken);
            ++totalMoves;
        }
        //System.out.print("Player: " + players[currentPlayer - 1].getName() + " | Move Time: " + timeTaken + " | Move: " + nextPlay);
        if (!gameState.isLegalPlay(nextPlay)) {
            System.err.println("Attention: Player "
                    + players[currentPlayer - 1].getName()
                    + " just tried to make an illegal move!");
            System.err.println("Attempted play was: " + nextPlay);
            System.err.println("Random player will make the move instead.");
            //make a move  
            AtroposPlayer randomPlayer = new AtroposPlayer("Random");
            nextPlay = randomPlayer.getNextPlay(gameState.clone());
        }
        gameState.makePlay(nextPlay);
        plays.push(nextPlay);
        currentPlayer = 3 - currentPlayer;
    }

    /**
     * Continues to make plays until the game is finished.
     */
    public void playEntireGame() {
        while (!gameState.isFinished()) {
            makeNextPlay();
        }
    }

    /**
     * Determines a winner.
     * 
     * @return 1 if the first player wins, 2 if the second does.
     */
    public int whoWins() {
        playEntireGame();
        return currentPlayer;
    }

    //private methods

    //toString

    /**
     * Returns a string version of
     * 
     * @param indent
     *            Indentation string.
     */
    public String toString(String indent) {
        String string = "";
        if (gameState.isFinished()) {
            string += "The game had the following moves:\n";
        } else {
            string += "The game has had the following moves:\n";
        }
        string += plays + "\n";
        if (gameState.isFinished()) {
            if ((oneFirst && plays.size() % 2 == 0)
                    || (!oneFirst && plays.size() % 2 != 0)) {
                string += playerOne.getName() + " has won ";
            } else {
                string += playerTwo.getName() + " has won ";
            }
            string += "in " + plays.size() + " moves!\n";
            string += "Final board:\n";
            string += gameState;
        } else {
            string += "The game is not finished!\n";
            string += "Current board:\n";
            string += gameState;
        }
        return string;
    }

    /**
     * Returns a string version of
     */
    public String toString() {
        return toString("");
    }

    /**
     * Testing Method
     * 
     * @param args
     * @return outputStats
     */
    public SimulatorStats execute(String[] args, int treeDepth) {
        AtroposPlayer playerOne;
        AtroposPlayer playerTwo;
        String defaultBoardSize = "6";
        int gameSize = Integer.parseInt(0 == args.length ? defaultBoardSize
                : args[0]);
        if (args.length == 1) {
            playerOne = new TianqiPlayer("XTQ Player");
            playerTwo = new RohitPlayer(Constants.DEFAULT_PLAYER_NAME,
                    new AlphaBeta(treeDepth, new NinjaEvaluator()));
        } else if (args.length == 2) {
            playerOne = new AtroposScriptPlayer("Script", args[1]);
            playerTwo = new AtroposPlayer("Default");
        } else {
            //args.length > 1
            playerOne = new AtroposScriptPlayer("Script One", args[1]);
            playerTwo = new AtroposScriptPlayer("Script Two", args[2]);
        }
        System.out.println("This will be a fantastic battle between "
                + playerOne.getName() + " and " + playerTwo.getName() + "!");
        //randomly determine the turn order
        Random randomGen = new Random();
        oneFirst = randomGen.nextBoolean();
        AtroposGame game;
        if (oneFirst) {
            game = new AtroposGame(gameSize, playerOne, playerTwo);
            System.out.println(playerOne.getName() + " will go first.");
        } else {
            game = new AtroposGame(gameSize, playerTwo, playerOne);
            System.out.println(playerTwo.getName() + " will go first.");
        }
        game.playEntireGame();
        System.out.println(game);

        /* Decide Winning Player */
        String winningPlayer;
        if ((oneFirst && plays.size() % 2 == 0)
                || (!oneFirst && plays.size() % 2 != 0)) {
            winningPlayer = playerOne.getName();
        } else {
            winningPlayer = playerTwo.getName();
        }

        /* Return Stats */
        return new SimulatorStats()
                .setIterations(1)
                .setGameSize(gameSize)
                .setTreeDepth(treeDepth)
                .setAverageTime(totalTime / totalMoves)
                .setMaxMoveTime(maxMoveTime)
                .setWinningPlayer(winningPlayer)
                .setWinningRate(
                        Constants.DEFAULT_PLAYER_NAME
                                .equalsIgnoreCase(winningPlayer) ? 100 : 0);
    }

    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
        new AtroposGame().execute(args, Constants.DEFAULT_TREE_DEPTH);
    }

} //end of AtroposGame.java
