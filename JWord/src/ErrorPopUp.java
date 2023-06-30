import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

//ErrorPopUp is a popup window that lets the player know what input error they made
//It is triggered by InputHandlers in LetterGrid.java
class ErrorPopUp extends JFrame implements ActionListener {
    //Properties
    JFrame frame;
    String errorCode1 = "Your guess must have 5 letters, Click here to return";
    String errorCode2 = "Your guess is not in the word list, Click here to return";
    String errorCode3 = "There are no letters to erase, Click here to return";

    //constructor
    ErrorPopUp(int errorCode)
    {
        frame = new JFrame("Oops, that's an Error");
        frame.setSize(400, 300);
        String buttonTitle = "";
        buttonTitle = errorCode==1 ? errorCode1 : buttonTitle;
        buttonTitle = errorCode==2 ? errorCode2 : buttonTitle;
        buttonTitle = errorCode==3 ? errorCode3 : buttonTitle;
        JButton replayButton = new JButton(buttonTitle);
        replayButton.addActionListener(this);
        frame.add(replayButton);
        frame.setVisible(true);
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}