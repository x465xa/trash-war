import java.io.IOException;
import java.util.List;

/**
 * Abstract parent class for the Server and Client. Provides
 * methods to perform actions and update the game state.
 * 
 * @author  Anne Xia
 * @version 05/15/2022
 * 
 * @author Sources - Meenakshi, Vaishnavi
*/
public abstract class PlayerComputer {
    /**
     * The port to use.
     */
    public static final int PORT = 12345;

    protected List<Player> players;
    protected Game gameWindow;
    protected boolean isPlaying = false;

    /**
     * Starts the game.
     */
    public void startGame() {
        isPlaying = true;
    }
    
    /**
     * Stops the game.
     */
    public void stopGame() {
        isPlaying = false;
    }
    
    /**
     * Simulates a player slapping a card.
     */
    public abstract void slapCard();
    
    /**
     * Overloaded version of slapCard for Server class.
     * @param player the player who slapped the card.
     */
    public void slapCard(Player player) {
        // do nothing
    }
    
    /**
     * Updates the score of a given player.
     * @param player the player whose score to update.
     * @param diff how much the score changed by,
     *             positive for add points and negative for subtract.
     */
    // TODO pos = empty out deck, neg = burn and do nothing
    public void updatePoints(Player player, int diff) {
        if (isPlaying) {
            player.addPoints(diff);
            if (diff > 0) {
                clearDeck();
            }
        }
    }

    /**
     * Empties out the center deck.
     */
    private void clearDeck() {
        if (isPlaying) {
            // TODO access centerDeck and empty it out
        }
    }
    
    /**
     * Simulates a new card being dealt.
     * Randomly generates a new card from the current player's deck.
     */
    public abstract void dealCard();
    
    /**
     * Displays a card on the GUI game window.
     * @param card the card to draw.
     * @throws IOException
     */
    public void drawCard(Card card) throws IOException {
        if (isPlaying) {
            // TODO draw the card
        }
    }

    /**
     * Gets the player with a given name from the current
     * object's list of players, or null if player not found.
     * @param name player name to search for, or null if not found.
     */
    public Player getMatch(String name) {
        if (players == null) {
            return null;
        }
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns a list of all players.
     * @return a list of all players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * For client class only, sets the local players list
     * to the given list.
     * @param players a list of players.
     */
    public void setPlayers(List<Player> players) {
        // do nothing
    }

    /**
     * Sets the local game window to the given one.
     * @param theGame a Game object.
     */
    public void setGUI(Game theGame) {
        gameWindow = theGame;
    }
}
