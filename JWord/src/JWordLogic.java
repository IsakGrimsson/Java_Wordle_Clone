//JWordLogic is used by LetterGrid to compare the players guess to the correctWord
public class JWordLogic {
    //Properties
    private String correctWord;

    //Constructor
    JWordLogic(String correctWord){
        this.correctWord = correctWord;
    }

    //Methods

    //A method to check the validity of a players guess
    //it returns an array of colour strings for each letter in the guess, based on the game rules
    //Example: Correct word:JWord, guess:JxxWx, output:[lg,black,black,gold,black]
    public String[] checkGuess(String guess){
        StringBuilder currentWord = new StringBuilder(guess.toLowerCase());
        StringBuilder lettersLeft = new StringBuilder(correctWord.toLowerCase());
        String[] output = new String[5];
        //Originally label everything as a non match
        for(int i=0; i<5; i++){
            output[i]="black";
        }
        //Find the exact matches and replace the character once matched
        for(int i=0; i<5; i++){
            if(lettersLeft.charAt(i)==currentWord.charAt(i)){
                output[i]="lg";
                lettersLeft.setCharAt(i,'0');
                currentWord.setCharAt(i,'0');
            }
        }
        //Find partial matches and replace the character once matched
        for(int ll=0; ll<5; ll++){
            for(int cw=0; cw<5; cw++){
                if(lettersLeft.charAt(ll)!='0'){
                    if(lettersLeft.charAt(ll)==currentWord.charAt(cw)){
                        output[cw]="gold";
                        lettersLeft.setCharAt(ll,'0');
                        currentWord.setCharAt(cw,'0');
                    }
                }
            }
        }
        return output;
    }

    //Setters
    public void setCorrectWord(String correctWord) {
        this.correctWord = correctWord;
    }
}
