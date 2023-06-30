import javax.swing.*;

//GUI handles the creation of the UI (The two major components within that LetterGrid and Keyboard)
//Also resets the UI components if player wants to play again after the game
public class GUI{
    //Properties
    public static LetterGrid grid;
    public static Keyboard keyboard;
    public static JFrame frm;

    //Constructor
    GUI(String word){
        //Create the Top level window
        frm = new JFrame("Wordle");
        frm.setLayout(new BoxLayout(frm.getContentPane(),BoxLayout.Y_AXIS));
        //frm.setLayout(new FlowLayout());
        frm.setSize(384,620);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the 2d grid
        grid = new LetterGrid(word);
        frm.add(grid);

        //Create the keyboard
        keyboard = new Keyboard();
        frm.add(keyboard);

        frm.setVisible(true);
    }

    //Reloads the frame if the user wants to play again
    public static void replay(String word){
        keyboard.initKeyboard();
        keyboard.createKeyboard();
        grid.initGrid();
        grid.createGrid();
        grid.resetGridTrackers(word);
    }
}