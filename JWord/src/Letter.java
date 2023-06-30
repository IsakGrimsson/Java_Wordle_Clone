import javax.swing.*;
import java.awt.*;

//Letter is an object that represents each letter in the grid
public class Letter extends JLabel {
    //Properties
    private ImageIcon icon;
    private String stringLetter = "0";

    //Constructor
    Letter(){
        setLetter("Blank","black");
    }

    //Setters

    //updates the Icon if given a letter and colour
    public void setLetter(String letter, String colour){
        this.stringLetter = letter;
        String path = "icons/" + letter + "/"+colour+".png";
        icon = new ImageIcon(path);

        //Scale it to the right size
        Image image = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
    }
    //updates the Icon if given a  colour
    public void setColour(String colour){
        String path = "icons/" + stringLetter + "/"+colour+".png";
        icon = new ImageIcon(path);

        //Scale it to the right size
        Image image = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
    }

    //Getters
    public ImageIcon getLetter(){
        return icon;
    }

    public String getStringLetter() {
        return stringLetter;
    }
}
