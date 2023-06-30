import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

//Generates a legal guess for the player with green letters in the right place
//And including all known yellow letters distributed randomly
//Any leftover spaces are generated randomly
//This class is called by LetterGrid.java
public class GenerateGuess {
    //properties
    char[] greenLetters;
    ArrayList<Character> yellowLetters;
    Guess guess;
    Guess_thread g1;
    Guess_thread g2;
    Guess_thread g3;
    Guess_thread g4;

    //Constructor
    GenerateGuess(char[] greenLetters, HashSet<Character> yellowLetters, SyncWaiter waiter){
        this.greenLetters = greenLetters;
        this.yellowLetters = new ArrayList<>(yellowLetters);
        this.guess = new Guess(waiter);

        //Create new threads
        this.g1= new Guess_thread(guess,this.greenLetters,this.yellowLetters);
        this.g2 = new Guess_thread(guess,this.greenLetters,this.yellowLetters);
        this.g3 = new Guess_thread(guess,this.greenLetters,this.yellowLetters);
        this.g4 = new Guess_thread(guess,this.greenLetters,this.yellowLetters);
        //Start the threads
        g1.start();
        g2.start();
        g3.start();
        g4.start();
    }

}

//Shared Instance of the Different threads
class Guess {
    //Properties
    private String guess;
    private boolean done;
    SyncWaiter waiter;

    //Constructor
    Guess(SyncWaiter waiter){
        this.guess = "00000";
        this.done = false;
        this.waiter = waiter;
    }

    //Getters&Setters
    public boolean isDone() {
        return done;
    }
    public String getGuess() {
        return this.guess;
    }
    public void setGuess(String newGuess) {
        this.guess = newGuess;
        this.done = true;
        synchronized (waiter){
            waiter.waitNoMore();
        }
    }
}

//Thread Class
class Guess_thread extends Thread{
    //properties
    Guess guess;
    char[] greenLetters;
    ArrayList<Character> yellowLetters;
    ArrayList<Character> allLetters;

    //Constructor
    Guess_thread(Guess guess, char[] greenLetters, ArrayList<Character> yellowLetters){
        this.guess = guess;
        this.greenLetters = greenLetters;
        //Add yellow letters to a new ArrayList;
        this.yellowLetters = yellowLetters;
    }

    //Methods
    public void run(){
        Random r = new Random();
        while(!guess.isDone()){
            StringBuilder guessBuilder = new StringBuilder();
            //Add the known green letters in the right place
            for(int i=0; i<5; i++){
                guessBuilder.append(greenLetters[i]);
            }
            //Add the known yellow letters in random places
            ArrayList<Character> yellowLettersCopy = new ArrayList<>();
            for(int i=0; i<yellowLetters.size(); i++){
                yellowLettersCopy.add(yellowLetters.get(i));
            }
            while(!yellowLettersCopy.isEmpty()){
                int i = r.nextInt(5);
                if(guessBuilder.charAt(i)=='0'){
                    guessBuilder.setCharAt(i,yellowLettersCopy.remove(0));
                }
            }
            //Add all the remaining non-black letters into an arrayList to choose from
            this.allLetters = new ArrayList<>();
            for(int i=0; i<GUI.keyboard.keys.length; i++){
                if(!GUI.keyboard.keys[i].getColour().equals("black")){

                    String s = GUI.keyboard.keys[i].getValue().toLowerCase();
                    char c = s.charAt(0);
                    this.allLetters.add(c);
                }
            }
            //Add random letters in the remaining places
            for (int i=0; i<5; i++) {
                char randomAlphabetLetter = allLetters.get(r.nextInt(allLetters.size()));
                if(guessBuilder.charAt(i)=='0'){
                    guessBuilder.setCharAt(i,randomAlphabetLetter);
                }
            }
            //Check to see if the word is in the dictionary, and return the guess if it is
            String string = guessBuilder.toString().toLowerCase();
            if(Main.dictionary.contains(string)){
                guess.setGuess(string);
            }
        }
    }
}