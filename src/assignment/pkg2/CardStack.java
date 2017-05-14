package assignment.pkg2;

import java.awt.Graphics;
import javax.imageio.ImageIO;


/**
 *
 * @author jmssmith047
 */
public class CardStack {
    //A stack to store the cards.
    public Stack<Card> stack;
    public String suit;
    public int x;
    
    public CardStack(String suit, int x) {
        stack = new Stack();
        this.suit = suit;
        this.x = x;
    }
    
    /*Adds c to the top of the stack, if the rules allow this.
     * The cards on a card stack must start from ace and have consecutive 
     * ranks in increasing order.
     – Each card stack corresponds to a speciﬁc suit (Hearts, Clubs, Spades, 
     * Diamonds). You can only place cards to the stack that corresponds 
     * to their suits.
     */
    public boolean add(Card c) {
        if(stack.getFirst() == null){
            if(c.getSuit().equals(suit) && c.getValueNumber() == 1){
                    stack.push(c);
                    return true;
            }
            else return false;
        }
        else {
            Card temp = (Card) stack.getFirst();
            if(temp.getValueNumber()+ 1 ==  c.getValueNumber()) {
                if(c.getSuit().equals(suit)){
                    stack.push(c);
                    return true;
                }
                else return false;
            }
            else return false;
        }   
    }
    
     public void paintThis(Graphics g) {
        if(stack.getFirst() == null) {
            try{
                g.drawImage(ImageIO.read(getClass().getResource
                        ("/cards/blank.png")), x + 270, 15, null);
            } 
            catch(Exception e) {

            }
        }  
        else {
            Card c = (Card) stack.getFirst();
            c.paintThis(g, x + 270, 15);
        }
        
    }
}







