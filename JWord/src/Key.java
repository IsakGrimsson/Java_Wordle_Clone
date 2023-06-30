import javax.swing.*;
import java.awt.*;

//Key is a JButton object that represents each key in the keyboard
public class Key extends JButton{
    //Properties
    private final String value;
    private String colour;
    private Icon icon;

    //Constructor
    Key(String value){
        this.value = value;
        this.colour = "grey";
        updateIcon();
    }

    //Methods
    public void updateIcon(){
        String letterPath = "icons/"+value+"/"+colour+".png";
        ImageIcon icon = new ImageIcon(letterPath);
        //Scale it to the right size
        Image image = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(image);
    }

    //Getters & Setters
    public void setColour(String colour) {
        //If the color is green that's final
        if(this.colour.equals("lg")){
            return;
        }
        //If the colour is yellow it can be upgraded to green only
        if(this.colour.equals("gold")){
            if(colour.equals("lg")){
                this.colour = "lg";
                updateIcon();
            }
            return;
        }
        //If the color is black it can't be upgraded to gray
        if(this.colour.equals("black")){
            if(colour.equals("grey")){
                return;
            }
        }
        this.colour = colour;
        updateIcon();
    }

    public String getColour() {
        return colour;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getValue() {
        return value;
    }

}
