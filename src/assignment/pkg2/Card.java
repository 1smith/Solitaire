package assignment.pkg2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author jmssmith047
 */
public class Card {
    //The card’s index, from 1 to 52
    public int cardIndex;
    int x, y;
    public boolean visible;

    public Card(int cardIndex, int x, int y) {
        this.cardIndex = cardIndex;
        this.x = x;
        this.y = y;
        visible = false;
        colour();
    }

    //Returns the card’s suit
    public String getSuit() {
        if(cardIndex >= 1 && cardIndex <= 13)
            return "Heart";
        else if(cardIndex >= 14 && cardIndex <= 26)
            return "Diamond";
        else if(cardIndex >= 27 && cardIndex <= 39)
            return "Spade";
        else if(cardIndex >= 40 && cardIndex <= 52)
            return "Club";
        else
            return "ERROR" + cardIndex;
    }
    
    //Returns the card’s value (such as 10, king, etc)
    public String getValue() {
        int i;
        if(cardIndex == 1 || cardIndex == 14
                || cardIndex == 27 || cardIndex == 40)
            i = 1;
        else if(cardIndex == 2 || cardIndex == 15
                || cardIndex == 28 || cardIndex == 41)
            i = 2;
        else if(cardIndex == 3 || cardIndex == 16
                || cardIndex == 29 || cardIndex == 42)
            i = 3;
        else if(cardIndex == 4 || cardIndex == 17
                || cardIndex == 30 || cardIndex == 43)
            i = 4;
        else if(cardIndex == 5 || cardIndex == 18
                || cardIndex == 31 || cardIndex == 44)
            i = 5;
        else if(cardIndex == 6 || cardIndex == 19
                || cardIndex == 32 || cardIndex == 45)
            i = 6;
        else if(cardIndex == 7 || cardIndex == 20
                || cardIndex == 33 || cardIndex == 46)
            i = 7;
        else if(cardIndex == 8 || cardIndex == 21
                || cardIndex == 34 || cardIndex == 47)
            i = 8;
        else if(cardIndex == 9 || cardIndex == 22
                || cardIndex == 35 || cardIndex == 48)
            i = 9;
        else if(cardIndex == 10 || cardIndex == 23
                || cardIndex == 36 || cardIndex == 49)
            i = 10;
        else if(cardIndex == 11 || cardIndex == 24
                || cardIndex == 37 || cardIndex == 50)
            i = 11;
        else if(cardIndex == 12 || cardIndex == 25
                || cardIndex == 38 || cardIndex == 51)
            i = 12;
        else if(cardIndex == 13 || cardIndex == 26
                || cardIndex == 39 || cardIndex == 52)
            i = 13;
        else i = 0;
        
        
        if(i > 10 && i < 1)
            return String.valueOf(cardIndex);
        else if(i == 1)
            return "A";
        else if(i == 11)
            return "Jack";
        else if(i == 12)
            return "Queen";
        else if(i == 13)
            return "King";
        else return "";
    }
    
    public int getValueNumber() {
        int i;
        if(cardIndex == 1 || cardIndex == 14
                || cardIndex == 27 || cardIndex == 40)
            i = 1;
        else if(cardIndex == 2 || cardIndex == 15
                || cardIndex == 28 || cardIndex == 41)
            i = 2;
        else if(cardIndex == 3 || cardIndex == 16
                || cardIndex == 29 || cardIndex == 42)
            i = 3;
        else if(cardIndex == 4 || cardIndex == 17
                || cardIndex == 30 || cardIndex == 43)
            i = 4;
        else if(cardIndex == 5 || cardIndex == 18
                || cardIndex == 31 || cardIndex == 44)
            i = 5;
        else if(cardIndex == 6 || cardIndex == 19
                || cardIndex == 32 || cardIndex == 45)
            i = 6;
        else if(cardIndex == 7 || cardIndex == 20
                || cardIndex == 33 || cardIndex == 46)
            i = 7;
        else if(cardIndex == 8 || cardIndex == 21
                || cardIndex == 34 || cardIndex == 47)
            i = 8;
        else if(cardIndex == 9 || cardIndex == 22
                || cardIndex == 35 || cardIndex == 48)
            i = 9;
        else if(cardIndex == 10 || cardIndex == 23
                || cardIndex == 36 || cardIndex == 49)
            i = 10;
        else if(cardIndex == 11 || cardIndex == 24
                || cardIndex == 37 || cardIndex == 50)
            i = 11;
        else if(cardIndex == 12 || cardIndex == 25
                || cardIndex == 38 || cardIndex == 51)
            i = 12;
        else if(cardIndex == 13 || cardIndex == 26
                || cardIndex == 39 || cardIndex == 52)
            i = 13;
        else i = 0;
        return i;
    }
    
    //The colour of the card is ‘red’ if this 
    //card is a heart or diamond, and ‘black’ otherwise.
    public Color colour() {
       if(cardIndex >= 1 && cardIndex <= 26)
            return Color.BLACK;
        else if(cardIndex >= 27 && cardIndex <= 52)
            return Color.RED;
        else
            return Color.GRAY;
    }
   
    //Returns a string representation
    //of this card, including its suit and rank.
    public String toString() {
        String s;
        if(this.visible == true){
            if(getValue().equals("Jack"))
                s = getSuit() + "J";
            else if(getValue().equals("Queen"))
                s = getSuit() + "Q";
            else if(getValue().equals("King"))
                s = getSuit() + "K";
            else if(getValue().equals("A"))
                s = getSuit() + "A";
            else
                s = getSuit() + getValueNumber();
        }
        else s = "back";
        return s;
    }
    
    public void paintThis(Graphics g, int x, int y) {
        this.x = x;
        this.y = y;
        String args;
        if(this.visible == true) {
            args = String.valueOf(cardIndex);
        }
        else {
            args = "back";
        }
        String path = "/cards/" + args + ".png";
        try{
        g.drawImage(ImageIO.read(getClass().getResource(path)), x, y, null);
        } catch(Exception e) {

        }
    }
    
    public void isVisible(boolean v) {
        this.visible = v;
    }
}
