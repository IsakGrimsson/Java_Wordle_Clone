import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

//GameOverScreen is a popup window that invites the player to play again
//It is triggered by LetterGrid.java after 6 incorrect guesses
//OR it is triggered by LetterGrid.java after a correct guess
class GameOverScreen extends JFrame implements ActionListener {
    //Properties
    JFrame frame;
    String lost = "Better luck next time! Click here to Replay";
    String won = "Well done on winning Jword! Click here to Replay";

    // constructor
    GameOverScreen(boolean win)
    {
        frame = new JFrame("Play Again?");
        frame.setSize(400, 300);
        String buttonTitle = win ? won : lost;
        JButton replayButton = new JButton(buttonTitle);
        replayButton.addActionListener(this);
        frame.add(replayButton);
        frame.setVisible(true);
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        GUI.replay(Main.getTarget());
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}