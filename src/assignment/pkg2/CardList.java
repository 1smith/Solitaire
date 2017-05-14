package assignment.pkg2;

import java.awt.Graphics;
import javax.imageio.ImageIO;

/**
 *
 * @author jmssmith047
 */
public class CardList {
    public SingleLinkedList<Card> cards;
    public int opendedIndex;
    public Card tailCard;
    public int x;
    
    public CardList(int x) {
        cards = new SingleLinkedList();
        opendedIndex = 0;
        tailCard = null;
        this.x = x;
    }
    
    //Separate the list into two: [0 .. (i-1)] and [i ..count].
    //Open the card at (i-1) if necessary. Return the second list.
    public SingleLinkedList cut(int index) {
        SingleLinkedList a = new SingleLinkedList();
        int s = cards.size;
        while(s > index) {
            a.addFirst(cards.removeLast());
            s--;
        }
        
        if(!cards.isEmpty()) {
            opendedIndex = this.cards.size -1;
            tailCard = cards.getLast();
        } else
           tailCard = null;
        if(tailCard != null)
            tailCard.isVisible(true);
        return a;
    }
    
    //Join this list to the tail of the other list, 
    //if the rules allow this.
    public void link(SingleLinkedList<Card> other) {
        Card card = other.getFirst();
        if(tailCard == null && card.getValueNumber() == 13) {
             while(!other.isEmpty()){
                    cards.addLast(other.removeFirst());
            }
            tailCard = cards.getLast();
            opendedIndex = 0;
            for(Object o : cards){
                Card c = (Card) o;
                opendedIndex++;
            }
                
        }
        else if(card.colour() != tailCard.colour() && 
                card.getValueNumber() == tailCard.getValueNumber() - 1) {
            
            while(!other.isEmpty()){
                cards.addLast(other.removeFirst());
            }
            tailCard = cards.getLast();
            opendedIndex = 0;
            for(Object o : cards){
                Card c = (Card) o;
                opendedIndex++;
            }
                
        }
        
    }
     
   
    /* Add c as the new tail card, if the rules allow this.
     * In a list, you can only put a red card (hearts, diamonds) on top of a 
     * black card (spades,clubs), or vice versa.
    */
    public boolean add(Card c) {
        if(tailCard == null && c.getValueNumber() == 13) {
            cards.addLast(c);
            tailCard = c;
            return true;
        }
        else 
            if(c.getValueNumber() == tailCard.getValueNumber() - 1 
                && c.colour() != tailCard.colour()) {
            cards.addLast(c);
            tailCard = c;
            return true;
        }
        return false;
    }
    
    //special add, return card to list
    public void addSP(Card c) {
            cards.addLast(c);
            tailCard = c;
    }
    
    //Delete and return the tail card. Set the card beneath it as 
    //the new tail card. Open the new tail card if necessary.
    public Card moveTail() {
        Card temp = tailCard;
        cards.removeLast();
        tailCard = cards.getLast();
        if(tailCard != null)
            tailCard.isVisible(true);
        return temp;
    }
    
    
     public void paintThis(Graphics g) {
        for(Object o : cards){
            Card c = (Card) o;
            if(c == null) {
                try{
                    g.drawImage(ImageIO.read(getClass().getResource
                            ("/cards/blank.png")), x + 15, 150, null);
                } 
                catch(Exception e) {

                }
            }  
            else {
                int i = cards.indexOf(o) * 20;
               c.paintThis(g, x + 15, i + 150);
            }
        }
    }
    
    
}
