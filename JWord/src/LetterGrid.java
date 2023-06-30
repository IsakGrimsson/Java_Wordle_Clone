import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

//LetterGrid creates a 6x5 grid used as the game board
//It also receives the players input from Keyboard.java and updates the grid
//Relies on the Letter.java object to represent letters in the grid
public class LetterGrid extends JPanel{
    //Properties
    private int currentRow = 0;
    private int currentLetter = 0;
    private Letter[] grid = new Letter[30];
    private JWordLogic logic;
    char[] knownGreens = new char[]{'0', '0', '0', '0', '0'};
    HashSet<Character> knownYellows = new HashSet<>();

    //Constructor
    LetterGrid(String word){
        this.setBorder(BorderFactory.createEmptyBorder(15,15,0,15));
        this.setLayout(new GridLayout(6,5));
        logic = new JWordLogic(word);
        initGrid();
        createGrid();
    }

    //Methods

    //A method to handle any key alphabetical key input
    public void keyboardInput(char s){
        //If you have entered more than 5 letters return
        if(currentLetter>4){
            return;
        }
        //Create the new letter, that the user entered
        Letter letter = new Letter();
        letter.setLetter(Character.toString(s),"grey");
        grid[currentRow*5+currentLetter] = letter;
        //Redraw the grid
        createGrid();
        //update CurrentLetterPosition
        currentLetter += 1;
    }

    //A method to handle the "I'm stuck!" key input
    public void helpInput(){
        //Start be tidying up the knownYellows array
        //Remove any letters that are found in knownGreens
        for(int i=0; i<knownGreens.length; i++){
            if(knownYellows.contains(knownGreens[i])){
                knownYellows.remove(knownGreens[i]);
            }
        }
        //Creates a waiter to know when the multithreads have finished running
        SyncWaiter waiter = new SyncWaiter();
        //Start the treads, and passes the waiter to them
        GenerateGuess guess = new GenerateGuess(knownGreens,knownYellows,waiter);
        //Wait here until the threads are done
        synchronized (waiter){
            waiter.waitUntilISaySo();
        }
        //The threads are now done and we can continue
        String s = guess.guess.getGuess();
        guess = null;
        //Add the suggested guess into the grid
        currentLetter = 0;
        for(int i=0; i<5; i++){
            Letter letter = new Letter();
            letter.setLetter(s.substring(i,i+1),"grey");
            grid[currentRow*5+currentLetter] = letter;
            currentLetter += 1;
        }
        //Redraw the grid
        createGrid();
    }

    //A method to handle the erase key input
    public void eraseInput(){
        //If the user hasn't entered any letters return
        if(currentLetter<1){
            ErrorPopUp errorPopUp = new ErrorPopUp(3);
            return;
        }
        //update CurrentLetterPosition
        currentLetter -= 1;
        //Create a new blank letter
        Letter letter = new Letter();
        letter.setLetter("Blank","black");
        grid[currentRow*5+currentLetter] = letter;
        //Redraw the grid
        createGrid();
    }

    //A method to handle the enter key input
    public void enterInput(){
        //return if  player hasn't entered a 5 letter word
        if(currentLetter!=5){
            ErrorPopUp errorPopUp = new ErrorPopUp(1);
            return;
        }
        //Create a 5 letter string
        StringBuilder guessBuilder = new StringBuilder();
        for(int i=0; i<5; i++){
            guessBuilder.append(grid[currentRow*5+i].getStringLetter());
        }
        String guess = guessBuilder.toString().toLowerCase();
        //return if the string doesn't exists in the dictionary
        if(!Main.dictionary.contains(guess)){
            ErrorPopUp errorPopUp = new ErrorPopUp(2);
            return;
        }
        //checkGuess returns an array of colour strings for the guess, based on the rules games
        String[] colours = logic.checkGuess(guess);
        //Updates the grid and keyboard colours correspondingly
        for(int i=0; i<5;i++){
            grid[currentRow*5+i].setColour(colours[i]);
            GUI.keyboard.updateColours(grid[currentRow*5+i].getStringLetter().toUpperCase(),colours[i]);
            //Update the guess trackers
            if(colours[i].equals("lg")){
                knownGreens[i] = guessBuilder.charAt(i);
            }
            if(colours[i].equals("gold")){
                knownYellows.add(guessBuilder.charAt(i));
            }
        }
        //Re-renders the components
        createGrid();
        GUI.keyboard.createKeyboard();
        //Update the input position to the beginning of the next row
        currentRow +=1;
        currentLetter = 0;
        //Checks to see if you won/lost the game and shows a popup if so
        boolean won = true;
        for(int i=0; i<5;i++){
            if(!colours[i].equals("lg")){
                won = false;
            }
        }
        if(won){
            GameOverScreen gameOverScreen = new GameOverScreen(won);
        }
        else if(currentRow>5){
            GameOverScreen gameOverScreen = new GameOverScreen(won);
        }
    }
    //Initialize the grid
    public void initGrid(){
        Letter letter = new Letter();
        for(int i=0; i<30; i++){
            grid[i] = letter;
        }
    }
    //Create a new grid
    public void createGrid(){
        this.removeAll();
        for(int i=0; i<30; i++){
            JLabel label = new JLabel(grid[i].getLetter());
            this.add(label);
        }
        this.revalidate();
    }

    //Resets variables for a new game state
    public void resetGridTrackers(String word){
        currentLetter = 0;
        currentRow = 0;
        logic.setCorrectWord(word);
        knownGreens = new char[]{'0', '0', '0', '0', '0'};
        knownYellows = new HashSet<>();
    }
}