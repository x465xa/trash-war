import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A client that connects to the game. Its main purpose is to sync
 * the local game state with the version on the server.
 * 
 * @author  Anne Xia
 * @version 05/10/2022
 * 
 * @author Sources - Meenakshi, Vaishnavi
 */
public class Client extends PlayerComputer {
    private Socket s;
    private GameThread gThread;
    
    private boolean isPlaying;
    private Player self;
    private Score scores;
    
    /**
     * Constructs a client and connects to a server.
     * @param address the IP address of the server.
     * @param playerName the name of this player.
     */
    public Client(String address, String playerName) {
        self = new Player(playerName);
        // TODO initialize scores
        connectToServer(address);
    }

    /**
     * Connects to a given server.
     * @param address the IP address of the server
     */
    private void connectToServer(String address) {
        try {
            s = new Socket(address, PlayerComputer.PORT);

            try {
                ObjectOutputStream oStream = new ObjectOutputStream(s.getOutputStream());
                oStream.flush();
                oStream.writeUTF(self.getName()); // sends player name to server
                oStream.close();
            } catch (Exception e) {
                System.out.println("Error in Client:");
                e.printStackTrace();
                return;
            }

            isPlaying = false;
            gThread = new GameThread(this, false, s);
            gThread.start();
        } catch (Exception ex) {
            System.out.println("Error connecting to server:");
            ex.printStackTrace();
        }
    }

    /**
     * Starts the game by creating a GameThread for the current user.
     */
    public void startGame() {
        isPlaying = true;
        try {
            ObjectInputStream iStream = new ObjectInputStream(s.getInputStream());
            players = (ArrayList<Player>) iStream.readObject(); // gets list of players from server
        } catch (Exception e) {
            System.out.println("Error in Client: " + e);
            return;
        }
    }

    /**
     * Stops the game by stopping the current user's thread.
     */
    public void stopGame() {
        isPlaying = false;
        gThread.stopThread();
    }
    
    /**
     * Sends a slap card update to the server.
     */
    public void slapCard() {
        if (isPlaying) {
            gThread.slapCard(self);
        }
    }

    /**
     * Updates the score of a given player.
     * @param player a player.
     * @param newScore the new score.
     */
    public void updatePoints(Player player, int newScore) {
        if (isPlaying) {
            player.setPoints(newScore);
            // TODO refresh scoreboard
        }
    }

    /**
     * Simulates a new card being dealt by this player.
     * Randomly generates a new card from the deck.
     */
    public void dealCard() {
        if (isPlaying) {
            gThread.dealCard(self, null);
            // note: actual card will be generated on server, null is a filler "default" value
        }
    }

    /**
     * Displays the random card generated. Method should be called
     * only by GameThread, use the no-args version of this method for
     * other card dealing.
     */
    public void dealCard(Player player, Card card) {
        if (isPlaying) {
            // TODO notify local class to update center deck
            // TODO notify GUI window to draw the card (if not done already)
        }
    }
}
