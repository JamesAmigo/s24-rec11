package edu.cmu.cs.cs214.rec11.plugin;

import edu.cmu.cs.cs214.rec11.framework.core.GameFramework;
import edu.cmu.cs.cs214.rec11.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec11.games.TicTacToe;

public class TicTacToePlugin implements GamePlugin<String>{
        
    private static final String GAME_NAME = "Tic Tac Toe";

    private static final int WIDTH = 3;
    private static final int HEIGHT = 3;

    private static final String PLAYER_X_WON_MSG = "X won!";
    private static final String PLAYER_O_WON_MSG = "O won!";
    private static final String GAME_TIED_MSG = "The game ended in a tie.";

    private static final String GAME_START_FOOTER = "You are playing Rocks Paper Scissors with a computer!";

    private GameFramework framework;
    private boolean currentPlayerMoved;

    private TicTacToe ticTacToeGame;
    /**
     * Gets the name of the plug-in game.
     */
    @Override
    public String getGameName()
    {
        return GAME_NAME;
    }

    /**
     * Gets the width (in squares) of the plug-in game's grid.
     */
    @Override
    public int getGridWidth()
    {
        return WIDTH;
    }

    /**
     * Returns the width (in squares) of the plug-in game's grid.
     */
    @Override
    public int getGridHeight()
    {
        return HEIGHT;
    }

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the game has begun (if necessary).
     *
     * @param framework The {@link GameFramework} instance with which the plug-in
     *                  was registered.
     */
    @Override
    public void onRegister(GameFramework framework)
    {
        this.framework = framework;
    }

    /**
     * Called when a new game is about to begin.
     */
    @Override
    public void onNewGame()
    {
        ticTacToeGame = new TicTacToe();
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                framework.setSquare(i, j, "");
            }
        }
        framework.setFooterText(GAME_START_FOOTER);
    }

    /**
     * Called when a new move is about to begin (i.e. immediately after
     * {@link #onNewGame()}, and before each subsequent move in the game). This
     * method will only be called if the last move was valid and did not end the
     * game.
     */
    @Override
    public void onNewMove()
    {
        currentPlayerMoved = false;
    }

    /**
     * Returns true if a move at location (x, y) is allowed (based on the game's
     * current state). Returns false otherwise.
     */
    @Override
    public boolean isMoveValid(int x, int y)
    {
        return ticTacToeGame.isValidPlay(x, y);
    }

    /**
     * Returns true if the current move is over (based on the game's current
     * state). Returns false otherwise.
     */
    @Override
    public boolean isMoveOver()
    {
        return currentPlayerMoved;
    }

    /**
     * Called when a move has been played.
     *
     * @param x The x coordinate of the grid square that has been played.
     * @param y The y coordinate of the grid square that has been played.
     */
    @Override
    public void onMovePlayed(int x, int y)
    {
        framework.setSquare(x, y, ticTacToeGame.currentPlayer().name());
        ticTacToeGame.play(x, y);
        currentPlayerMoved = true;
    }

    /**
     * Returns true if the game is over (based on the game's current state).
     * Returns false otherwise.
     */
    @Override
    public boolean isGameOver()
    {
        return ticTacToeGame.isOver();
    }

    /**
     * Returns the message to display when this game is deemed to be over. This
     * method is called immediately after {@link #isGameOver()} returns true.
     */
    @Override
    public String getGameOverMessage()
    {
        if (ticTacToeGame.winner() == TicTacToe.Player.X)
        {
            return PLAYER_X_WON_MSG;
        }
        else if (ticTacToeGame.winner() == TicTacToe.Player.O)
        {
            return PLAYER_O_WON_MSG;
        }
        else
        {
            return GAME_TIED_MSG;
        }
    }

    /**
     * Called when the plugin is closed to allow the plugin to do any final cleanup.
     */
    @Override
    public void onGameClosed()
    {

    }

    /**
     * Returns the player whose turn it is to play.
     */
    @Override
    public String currentPlayer()
    {
        return ticTacToeGame.currentPlayer().name();
    }
}
