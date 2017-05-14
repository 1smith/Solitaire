package assignment.pkg2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author jmssmith047
 */
public class PaintingPanel extends javax.swing.JPanel {
    public CardDeck deck;
    public CardStack [] stacks;
    public CardList [] lists;
    
    public PaintingPanel(CardDeck deck, CardStack [] stacks, CardList [] lists) {
        this.setBackground(Color.WHITE);
        this.deck = deck;
        this.stacks = stacks;
        this.lists = lists;
    }        

    
    
     public void paintComponent(Graphics g){
        super.paintComponent(g);
        try {
             g.drawImage(ImageIO.read(getClass().getResource("/bg.jpg")), 0, 0, null); 
             
        }
        catch (Exception e) {
        }
        
        deck.paintThis(g);
        for(CardStack c : stacks)
            c.paintThis(g);
        for(CardList c : lists)
            c.paintThis(g);
        
        
    }
    
}
