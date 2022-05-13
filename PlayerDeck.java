/**
 * Description
 * 
 * @author  Vaishnavi Kunapuli
 * @version 04/29/2022
 * 
 * @author Sources - Meenakshi, Anne
 */

import java.awt.event.*;
import java.util.Stack;

import javax.tools.DocumentationTool.Location;

public class PlayerDeck implements MouseListener {

    private Stack<Card> playersCards;
    CenterDeck d; 
    int xCoor;
    int yCoor;
    int width; 
    int length;
    
    /**
     * 
     * @param d CenterDeck
     * @param x top left corner of playerdeck card x-coord
     * @param y top left corner of playerdeck card y-coord
     * @param width width of playerdeck card
     * @param length length of playerdeck card
     */
    public PlayerDeck(CenterDeck d, int x, int y, int width, int length){

        playersCards = new Stack<Card>();
        this.d = d;
        this.xCoor = x;
        this.yCoor = y;
        this.width = width;
        this.length = length;
    }

    public Stack<Card> getplayersCards(){
        return playersCards;
    }

    /**
     * This method generates and returns a randomly generate card
     * @return Card
     */
    public Card randomlyGenerateCard(){
        Card c = new Card();
        return c;
    }

    /**
     * Populates the player's deck with randomly generated cards
     * @param numCards
     */
    public void populate(int numCards)
    {
        for (int i = 0; i < numCards; i++)
        {
            playersCards.push(randomlyGenerateCard());
        }
    }

    /**
     * Check if the players clicked inside the bounds of their cards. 
     * @return
     */
    public boolean isInBounds(int x, int y){
        if (((x <= xCoor + width) && (x >= xCoor)) && ((y <= yCoor + length) && (y >= yCoor)))
        {
            return true;
        }

        return false;
        
    }

    /**
     * This method is called whenever the mouse is pressed.
     * This method also makes a method call to the isIsBounds method to check if the players cards were clicked.
     * Uses getX() and getY() methods to get coordinates of the click
     * @param e
     */
    
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        isInBounds(x, y);

        
    }

    /**
     * Move a cardfrom the player's deck to the center deck
     * @param d
     */
    public void moveCard()
    {
        Card c = randomlyGenerateCard(); 
        d.addCard(c);
    }


    /**
     * Takes a card from the PlayerDeck and places it as the bottom of the CenterDeck (index 0 of the linkedlist)
     * @param d the centerdeck to burn the card in 
     */
    public void burn()
    {
        Card c = randomlyGenerateCard(); 
        d.addBottom(c);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub
        
    }
    
}
