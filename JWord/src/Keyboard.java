import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Keyboard creates a QWERTY keyboard for the player to use to enter input
//It listens for the input and passes the input on to LetterGrid to handle
//Relies on the Key.java JButton object to represent key's in the keyboard
public class Keyboard extends JPanel implements ActionListener {
    //Properties
    Key[] keys = new Key[26];

    //Constructor
    Keyboard(){
        this.setLayout(new GridLayout(4,1));
        initKeyboard();
        createKeyboard();
    }

    //Initialize the keys array
    public void initKeyboard(){
        String[] qwerty= {"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
        for(int i=0; i<26; i++){
            keys[i] = new Key(qwerty[i]);
        }
    }

    //Renders the keyboard
    public void createKeyboard(){
        this.removeAll();

        //Create 3 panels of buttons
        JPanel panel = new JPanel();
        for(int i=0; i<10;i++){
            panel.add(createButton(keys[i]));
        }
        this.add(panel);
        panel = new JPanel();
        for(int i=10; i<19;i++){
            panel.add(createButton(keys[i]));
        }
        this.add(panel);
        panel = new JPanel();
        for(int i=19; i<26;i++){
            panel.add(createButton(keys[i]));
        }
        this.add(panel);

        //Add Erase and Enter Buttons
        panel = new JPanel();
        JButton helpButton= new JButton("I'm stuck!");
        helpButton.addActionListener(this);
        panel.add(helpButton);
        JButton eraseButton= new JButton("Erase");
        eraseButton.addActionListener(this);
        panel.add(eraseButton);
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        panel.add(enterButton);
        this.add(panel);

        this.revalidate();
    }

    //Create an individual Jbutton Alphabetical Key
    public JButton createButton(Key key){
        JButton letterButton = new JButton(key.getIcon());
        letterButton.setName(key.getValue());
        letterButton.setPreferredSize(new Dimension(32,32));
        letterButton.addActionListener(this);
        return letterButton;
    }

    //Handles mouse clicks
    public void actionPerformed(ActionEvent ae){
        if(ae.getActionCommand().equals("I'm stuck!")){
            GUI.grid.helpInput();
        }
        if(ae.getActionCommand().equals("Erase")){
            GUI.grid.eraseInput();
        }
        else if(ae.getActionCommand().equals("Enter")){
            GUI.grid.enterInput();
        }
        else{
            //input = the value of the key pressed
            char input = ae.toString().charAt(ae.toString().length()-1);
            GUI.grid.keyboardInput(input);
        }
    }

    //Finds the correct key and updates its colour value
    public void updateColours(String value, String colour){
        for(int i=0;i<26;i++){
            if(value.equals(keys[i].getValue())){
                keys[i].setColour(colour);
            }
        }
    }
}
