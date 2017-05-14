package assignment.pkg2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author jmssmith047
 */
public  class Solitaire {
    public CardDeck deck;
    public CardStack [] stacks;
    public CardList [] lists;
    public boolean gameRunning;
    public boolean test = false;
    public static JFrame frame;
    public static PaintingPanel panel;
    public static JTextArea text;
    public static JMenuBar menu;
    public static JPanel jpanel;
    public static JMenu game;
    public static JMenuItem restart;
    public static JMenuItem help;
    public static JMenuItem quit;
    public static JScrollPane control;
    public static JScrollBar s;
    public boolean doRestart;
     
    public Solitaire(){
        create();
    }
    
    public static void main(String [] args) {
        Solitaire s = new Solitaire();
    }
    
    public static void showGUI(Solitaire game){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
        frame.setMaximumSize(new Dimension(617, Integer.MAX_VALUE));
        frame.setMinimumSize(new Dimension(617, 750));
        //frame.setResizable(false);
        /*
        frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                frame.setSize(new Dimension(617, frame.getHeight()));
                super.componentResized(e);
            }

        });
        */
        jpanel.setSize(617, 750);
        jpanel.setLocation(0, 0);
        panel.setSize(617, 512);
        jpanel.setLayout(new BorderLayout());
        jpanel.add(menu, BorderLayout.PAGE_START);
        jpanel.add(panel, BorderLayout.CENTER);
        text.setSize(new Dimension(617, 200));
        text.setPreferredSize(new Dimension(617, 200));
        control.add(text);
        control.setPreferredSize(new Dimension(617, 200));
        control.setVerticalScrollBarPolicy(control.VERTICAL_SCROLLBAR_ALWAYS);
        jpanel.add(control, BorderLayout.PAGE_END);
        frame.add(jpanel);
        frame.pack();
    }
    
    public void executeCommand(String command){
        command = command.toLowerCase();
        boolean deckToInvalid = false;
        boolean sendInvalid = false;
        boolean linkInvalid = false;
        if(command.equalsIgnoreCase("drawcard") || command.equalsIgnoreCase("draw card") || command.equalsIgnoreCase("dc")) {
            deck.currentCard = deck.drawCard();
        } 
        else if(command.startsWith("drawcard ")) {
            String i = "";
            if(command.length() == 10) {
                i += command.charAt(9);
                int no = 0;
                try{
                    no = Integer.parseInt(i);
                }catch(Exception e){
                    deckToInvalid = true;
                }
                while(no > 0){
                    executeCommand("DrawCard");
                    no--;
                }
            }
            else deckToInvalid = true;
        } 
        else if(command.startsWith("deckto") || command.startsWith("deck to") || command.startsWith("dt")) {
            StringTokenizer count = new StringTokenizer(command);
            if(deck.currentCard == null) {
                deckToInvalid = true;
            }
            else if(count.countTokens() == 2) {
                count.nextToken();
                String i = count.nextToken();
                int no = Integer.parseInt(i);
                no--;
                if(no >= 0 && no < 8) {
                    boolean b = lists[no].add(deck.currentCard);
                    if(b == true) {
                        deck.currentCard = null;
                        executeCommand("DrawCard");
                    }
                }
                else deckToInvalid = true;
            }
            else  if(count.countTokens() == 3) {
                count.nextToken();
                count.nextToken();
                String i = count.nextToken();
                int no = Integer.parseInt(i);
                no--;
                if(no >= 0 && no < 8) {
                    
                    boolean b = lists[no].add(deck.currentCard);
                    if(b == true) {
                        deck.currentCard = null;
                        executeCommand("DrawCard");
                    }
                }
            }
            else deckToInvalid = true;
        }
        else if(command.startsWith("link") || command.startsWith("l") ) {
            StringTokenizer s = new StringTokenizer(command);
            if(s.countTokens() == 3){
                s.nextToken();
                String name = s.nextToken();
                int no = 0;
                try{
                    no = Integer.parseInt(s.nextToken());
                    
                }
                catch(Exception e){
                    linkInvalid = true;
                }
                no--;
                int i;
                SingleLinkedList list = new SingleLinkedList();
                for(CardList l : lists){
                    for(Object o : l.cards) {
                        Card c = (Card) o;
                        if(c.toString().equalsIgnoreCase(name)) {
                            i = l.cards.indexOf(o);
                            if(lists[no].tailCard == null && c.getValueNumber() == 13
                                    || c.colour() != lists[no].tailCard.colour() &&
                                    c.getValueNumber() == lists[no].tailCard.getValueNumber() - 1) {
                                list = l.cut(i);
                                lists[no].link(list);
                            }
                            else
                            break;
                        }
                    }
                }
            }
            else {
                linkInvalid = true;
            }
        }
        else if(command.startsWith("send") || command.startsWith("s") ) {
            String i = "";
            if(command.length() == 6) {
                i += command.charAt(5);
                int no = Integer.parseInt(i);
                no--;
                Card c;
                if(no == -1) {
                    c = deck.currentCard;
                }
                else {
                    c = lists[no].tailCard;
                }
                if(no >= -1 && no < 8) {
                    int j;
                    if(c != null) {
                        if(c.getSuit().equalsIgnoreCase("Heart"))
                            j = 0;
                        else if(c.getSuit().equalsIgnoreCase("Diamond"))
                            j = 1;
                        else if(c.getSuit().equalsIgnoreCase("Spade"))
                            j = 2;
                        else
                            j = 3;
                        boolean b = stacks[j].add(c);
                        if(b == false) {
                            sendInvalid = true;
                        }
                        else {
                            if(no == -1) {
                                deck.currentCard = null;
                                 executeCommand("DrawCard");
                            }
                            else {
                                Card d = lists[no].moveTail();
                            }
                        }
                    }
                }
                else sendInvalid = true;
            }
            else if(command.length() == 3) {
                i += command.charAt(2);
                int no = Integer.parseInt(i);
                no--;
                Card c;
                if(no == -1) {
                    c = deck.currentCard;
                }
                else {
                    c = lists[no].tailCard;
                }
                if(no >= -1 && no < 8) {
                    int j;
                    if(c != null) {
                        if(c.getSuit().equalsIgnoreCase("Heart"))
                            j = 0;
                        else if(c.getSuit().equalsIgnoreCase("Diamond"))
                            j = 1;
                        else if(c.getSuit().equalsIgnoreCase("Spade"))
                            j = 2;
                        else
                            j = 3;
                        boolean b = stacks[j].add(c);
                        if(b == false) {
                            sendInvalid = true;
                        }
                        else {
                            if(no == -1) {
                                deck.currentCard = null;
                                 executeCommand("DrawCard");
                            }
                            else {
                                Card d = lists[no].moveTail();
                            }
                        }
                    }
                }
                else sendInvalid = true;
            } else sendInvalid = true;
        }
        else if(command.equalsIgnoreCase("restart")) {
            
            for(ActionListener a : quit.getActionListeners()) {
                quit.removeActionListener(a);
            }
            for(ActionListener a : restart.getActionListeners()) {
                restart.removeActionListener(a);
            }
            for(ActionListener a : help.getActionListeners()) {
                help.removeActionListener(a);
            }
            frame.dispose();
            Solitaire n = new Solitaire();
            this.executeCommand("quit");
        }
        else if(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
        else if(command.equalsIgnoreCase("test")) {
            test = true;
        }
        else {
            if(doRestart == false)
            JOptionPane.showMessageDialog(panel, "Invalid Command, No Valid Move",
                    "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        if(deckToInvalid == true) {
            String intro = "Invalid Command, No Valid Move \n"
                + "DeckTo comamnd format: DeckTo x: \n"
                + "x has to from 1-7 \n";
            JOptionPane.showMessageDialog(panel, intro, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        
        if(sendInvalid == true) {
            String intro = "Invalid Command, No Valid Move \n"
                + "Send comamnd format: Send x \n"
                + "x has to from 0-7 \n";
            JOptionPane.showMessageDialog(panel, intro, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        
        if(linkInvalid == true) {
            String intro = "Invalid Command, No Valid Move \n"
                + "Link comamnd format: Link c x \n"
                + "x has to from 1-7 \n"
                + "c has to be a in the format SuitY \n"
                + "\t \t Y has to be a number 2-10 or A, K, Q, J \n"
                + "c has to be a visible card in the list \n";
            JOptionPane.showMessageDialog(panel, intro, "Error Message", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void startGame(){
        while(gameRunning){
            displayText();
            
            Scanner s = new Scanner(System.in);
            restart();
            if(s.hasNextLine()) {
                executeCommand(s.nextLine());  
            }
            frame.repaint();
            text.setText("");
            if(checkVictory() == true) {
                JOptionPane.showMessageDialog(panel, "You Have Won",
                        "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        executeCommand("Quit");
    }
    
    public void createDeck(){
        int[] temp = new int[52];
        Random random = new Random();
        for(int i = 0; i < 52; i++) {
            temp[i] = i + 1;
        }
        
        for(int i = 51; i > 0; i--){
            int j = random.nextInt(i+1);
            int temporary = temp[i] ;
            temp[i] = temp[j];
            temp[j] = temporary;
        }
        
        for(int i : temp)
            deck.cards.addFirst(new Card(i ,0, 0));
        
        for(int j = 0; j < 7; j++){
            for(int k = j + 1; k > 0; k--) {
                executeCommand("DrawCard");
                lists[j].addSP(deck.takeCard());
            }
            lists[j].tailCard.visible = true;
        }
    }
    
    public void displayText() {
        System.out.println("------------------------------------"
                + "------------------------------Current Step------------------"
                + "------------------------------------------------");
        System.out.print("Card Deck: ");
        if(deck.cards.size == 0) 
            System.out.print("Empty");
        else System.out.print("Not Empty");

        System.out.print("      Open Card: ");
        if(deck.currentCard == null){
            System.out.print("None");
        } else {
            deck.currentCard.visible = true;
            System.out.print(deck.currentCard.toString());
        }

        System.out.println();
        System.out.print("Card Stacks: ");
        for(CardStack c : stacks){
            Card d = (Card) c.stack.getFirst();
            if(d != null) 
                System.out.print(d.toString());
            else System.out.print("Empty");
            System.out.print("  ");
        }
        System.out.println();


        System.out.println("Card List: ");
        for(int i = 0; i < lists.length; i++){
            System.out.print(i + 1 + ": ");
            for(Object o: lists[i].cards){
                
                Card temp = (Card) o;
                if(temp == null){
                } else
                System.out.print(temp.toString() + "  ");
            }
            System.out.println();
        }
    }
       
    public void create(){
        doRestart = false;
        frame = new JFrame("Solitaire");
        stacks = new CardStack[4];
        lists = new CardList[7];
        
        PipedInputStream in = new PipedInputStream();
        PipedInputStream out = new PipedInputStream();
        System.setIn(in);
        
        try {
            System.setOut(new PrintStream(new PipedOutputStream(out), true));
            PrintWriter inWriter = new PrintWriter(new PipedOutputStream(in), true);
            text = text(out, inWriter);
        }catch(IOException e){
        }
        
        gameRunning = true;
        for(int i = 0; i < 7; i++){
            lists[i] = new CardList(i * 85);
        }
        stacks[0] = new CardStack("Heart", 0);
        stacks[1] = new CardStack("Diamond", 85);
        stacks[2] = new CardStack("Spade", 2 * 85);
        stacks[3] = new CardStack("Club", 3 * 85);
        deck = new CardDeck();
        createDeck();
        panel = new PaintingPanel(deck, stacks, lists);
        jpanel = new JPanel();
        menu = new JMenuBar();
        control = new JScrollPane();
        game = new JMenu("Game");
        restart = new JMenuItem("Restart the Game");
        quit = new JMenuItem("Exit the Game");
        help = new JMenuItem("Help");
        menu.add(game);
        menu.add(help);
        game.add(restart);
        game.add(quit);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand("Quit");}});
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doRestart = true;
                JOptionPane.showMessageDialog(panel, "To restart please select"
                        + " the text box and press ENTER", "To Restart", 
                        JOptionPane.INFORMATION_MESSAGE);
            }});
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }});
        showGUI(this);
        help();
        startGame();
    }
    
    public void help(){
        String intro = "Welcome To Solitaire \n"
                + "Use the text box below to control the game: \n"
                + "\n"
                + "Commands: \n"
                + "DrawCard or dc: Open the next card on the card deck.\n" +
                "DeckTo x or dt x: Move one card from the deck to the xth list \n" +
                 "\t \t  X Accepts numbers: 1-7, of the list \n" +
                "Link c x or L c x: Moves the card c, and any cards below in the list, to the xth list\n" +
                "\t \t  X Accepts numbers: 1-7, of the list \n" +
                "\t \t  C is the name of the card, in the format SpadeK or Diamond2\n" +
                "Send x or s x: Moves the tail card in the cth list, to a card stack corresponding to its suit\n" +
                "\t \t  X Accepts numbers: 1-7 of the list or 0 to represent the deck \n" +
                "\n" +
                "Restart: Restart the game.\n" +
                "Quit: Stop the game. \n"
                + "\n"
                + "Have Fun, Press Ok to Begin";
        JOptionPane.showMessageDialog(panel, intro, "Welcome Message", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean checkVictory() {
        if(stacks[0].stack.size() == 13 && stacks[1].stack.size() == 13 &&
                stacks[2].stack.size() == 13 && stacks[3].stack.size() == 13){
            return true;
        }
        else if(test == true) {
            return true;
        }
        else return false;
    }
    
    public static JTextArea text(final InputStream out, final PrintWriter in) {
    final JTextArea area = new JTextArea();

        // handle "System.out"
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                Scanner s = new Scanner(out);
                while (s.hasNextLine()) publish(s.nextLine() + "\n");
                return null;
            }

            @Override
            protected void process(List chunks) {
                for (Object o : chunks) {
                    String line = (String) o;
                    area.append(line);
                }
            }
        }.execute();

        // handle "System.in"
        area.addKeyListener(new KeyAdapter() {
            private StringBuffer line = new StringBuffer();
            @Override 
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    in.println(line);
                    line.setLength(0); 
                } else if (c == KeyEvent.VK_BACK_SPACE) {
                    if(line.length() > 0)
                        line.setLength(line.length() - 1); 
                } else if (!Character.isISOControl(c)) {
                    line.append(e.getKeyChar());
                }
            }
        });

        return area;
    }
    
    public void restart(){
        if(doRestart == true){
            executeCommand("Restart");
        }
    }
    
}










