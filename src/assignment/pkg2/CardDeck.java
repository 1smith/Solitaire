package assignment.pkg2;

import java.awt.Graphics;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author jmssmith047
 */
public class CardDeck {
    //A circularly-linked list storing 
    //all the cards in the deck
    public CircularlyLinkedList cards;
    
    //The current card.
    public Card currentCard;

    
    public CardDeck() {
       cards = new CircularlyLinkedList();
       currentCard = null;
    }
    
    //Open the next card, if this is the tail card,
    //return null.
    public Card drawCard() {
        if(cards.getFirst() != cards.getLast())
            
            if(currentCard != null){
                cards.addLast(currentCard);
            }
            return (Card) cards.removeFirst();
    }
    
    //Delete and return the current card 
    //(so we can place it in a list or a stack).
    public Card takeCard() {
        Card temp = currentCard;
        currentCard = null;
        return temp;
    }
    
    public void paintThis(Graphics g) {
        if(currentCard == null) {
            try{
                g.drawImage(ImageIO.read(getClass().getResource
                        ("/cards/blank.png")), 100, 15, null);
            } 
            catch(Exception e) {

            }
        }  
        else {
            currentCard.paintThis(g, 100, 15);
        }
        
        try{
            g.drawImage(ImageIO.read(getClass().getResource
                    ("/cards/back.png")), 15, 15, null);
        } 
        catch(Exception e) {

        }
        
    }
}
