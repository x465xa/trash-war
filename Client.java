import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

/**
 * A client that connects to the game. Its main purpose is to sync
 * the local game state with the version on the server.
 * 
 * @author  Anne Xia
 * @version 05/22/2022
 * 
 * @author Sources - Meenakshi, Vaishnavi
 */
public class Client extends PlayerComputer {
    private Socket s;
    private GameThread gThread;
    
    private DataOutputStream oStream;

    private Score scores;
    
    /**
     * Constructs a client and connects to a server.
     * @param address the IP address of the server.
     * @param playerName the name of this player.
     * @throws IOException
     * @throws UnknownHostException
     */
    public Client(String address, String playerName) throws UnknownHostException, IOException {
        name = playerName;
        // TODO initialize scores
        connectToServer(address);
    }

    /**
     * Constructs a client and connects to a server. Player is given a default name.
     * @param address the IP address of the server.
     * @throws UnknownHostException
     * @throws IOException
     */
    public Client(String address) throws UnknownHostException, IOException {
        this(address, "Player 1");
    }

    /**
     * Connects to a given server. Once connected, creates a GameThread and waits
     * for the signal from the server to start the game.
     * @param address the IP address of the server
     * @throws IOException
     * @throws UnknownHostException
     */
    private void connectToServer(String address) throws UnknownHostException, IOException {
        s = new Socket(address, PlayerComputer.PORT);
        
        try {
            oStream = new DataOutputStream(s.getOutputStream());
            oStream.writeUTF(StateUpdate.encode64(name)); // sends player name to server
            /* We need to encode because DataInputStream stops reading at whitespace
               and a single player name may contain spaces. */

            gThread = new GameThread(this, s);
            gThread.start();
        } catch (Exception ex) {
            System.out.println("Error connecting to server:");
            ex.printStackTrace();
        }
    }

    /**
     * Stops the game by stopping the current user's thread.
     */
    public void stopGame() {
        gThread.stopThread();
        super.stopGame();
    }
    
    /**
     * Sends a slap card update to the server.
     */
    public void slapCard() {
        if (isPlaying) {
            gThread.slapCard(name);
        }
    }

    /**
     * Updates the score of a given player.
     * @param player a player.
     * @param diff the change in score.
     */
    public void updatePoints(String player, int diff) {
        if (isPlaying) {
            super.updatePoints(player, diff);
            // TODO refresh scoreboard
        }
    }

    /**
     * Simulates a new card being dealt by this player.
     * Card will be randomly generated by server and sent back to the client
     * to draw on the game window.
     */
    public void dealCard() {
        if (isPlaying) {
            gThread.dealCard(null);
            // Actual card will be generated on server, null is a "default" filler value.
        }
    }

    /**
     * Displays a card on the game window.
     * @param card the card to draw.
     */
    public void drawCard(Card card) {
        if (isPlaying) { 
            try {
                super.drawCard(card);
            } catch (Exception e) {
                System.out.println("Exception in Client:");
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the local player list to the given list.
     * @param players a list of players.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * For debugging purposes only. Simulates a short sequence of actions.
     * @param args
     */
    // TODO delete this later
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Client aTest;
        try {
            aTest = new Client("localhost", "CoolClient");
        } catch (Exception e) {
            e.printStackTrace();
            scan.close();
            return;
        }

        System.out.println("#### Created client, enter to see players");
        scan.nextLine();

        System.out.println("#### Players: ");
        for (Player p : aTest.getPlayers()) {
            System.out.println(p.getName() + " - " + p.getPoints());
        }

        System.out.println("#### Enter to see CoolClient's point value");
        scan.nextLine();
        System.out.println(aTest.getMatch("CoolClient").getPoints());

        System.out.println("#### Enter to see sErVeR's point value");
        scan.nextLine();
        System.out.println(aTest.getMatch("sErVeR").getPoints());

        System.out.println("#### Enter to slap a card");
        scan.nextLine();
        aTest.slapCard();

        scan.close();
    }
}
