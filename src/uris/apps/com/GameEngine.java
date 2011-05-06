package uris.apps.com;

import java.util.Random;
import android.os.SystemClock;

public class GameEngine {
    //public settings
    static int EASY=0, MEDIUM=1, HARD=2;
    static int MAX_CHOICES=3;

    //private (inner) settings
    int difficulty=EASY; //default difficulty
    int no_stages;
    int no_choices=2; //default number of choices
    int[][] stages;
    int[] answers;
    int cur_stage=0;
    boolean game_complete=false;
    Random rg; //used to generate choices
    int[] current_settings; //used to save current settings
    int no_art_pieces=0; //number of art pieces

    //scoring
    boolean[] beenHereBefore;
    int guess_bonus;
    //clock
    long countdownDuration;
    long keyPress = 1500; //1.5 seconds

    GameClock mGameClock;

    //This creates a GameEngine with no_art_pieces of art pieces, given
    //difficulty, and the given number of stages.

    //Difficulty is self-explanatory. no_stages tells how many levels
    //or stages each game will have. The choices array actually holds
    //the choices for each stage.

    //The way it works is that no_art_pieces is actually the size of
    //an array containing the art pieces: 0, 1, 2, ... #no_art_pieces.
    //Each stage consists of a fixed number of choices. These *could*
    //be simple booleans (in the case of two choices) or numbers (ex:
    //1, 2, 3). However, the catch is that the choices actually
    //represent art pieces and sometimes you don't want repetitions.

    public GameEngine(int art, int difficulty_, int stages) {
	//setup game variables
	no_art_pieces = art;
	difficulty = difficulty_;
	no_stages = stages;
	no_choices = 2;
	if ( difficulty == MEDIUM ) {
	    no_choices = 3;
	} 
	else if ( difficulty == HARD ) {
	    no_choices = 4;
	}
	//setup random number generator
	rg = new Random();
	//setup internal logic variables
	initStages();
	initScoring();
	cur_stage=0;
	game_complete=false;
	//set countdown timer
	initCountdownTimer();
	//save current settings
	//saveSettings();
    }

    //initializes the GameEngine given the difficulty
    //settings, no of art pieces, and number of stages.
    //sets stages array and answers array
    private void initStages() {
	stages = new int[no_stages][no_choices];
	for (int i=0; i<no_stages; i++) {
	    for (int j=0; j<no_choices; j++) {
		stages[i][j] = rg.nextInt( no_art_pieces );
	    }
	    if ( difficulty == EASY ) 
	    	eliminateDuplicates( i );
	}
	//set answers
	answers = new int[no_stages];
	for (int i=0; i<no_stages; i++) {
	    //randomly select one of the art pieces at that
	    //current stage
	    answers[i] = rg.nextInt( no_choices );
	}
    }

    private void eliminateDuplicates( int cur_stage ) {
	for (int j=1; j<no_choices; j++) {
	    for (int k=0; k<j; k++) {
		while ( stages[cur_stage][k] == stages[cur_stage][j] ) {
			stages[cur_stage][j] = rg.nextInt( no_art_pieces );
		    }
		k = 0;
	    }
	}
    }

    private void initScoring() {
	beenHereBefore = new boolean[no_stages];
	for( int i=0; i<no_stages; i++) 
	    beenHereBefore[i] = false;
    }

    // Scoring system:
    //  1.5(no choices)n + 0 + 1.5 + 1.5(2) + ... + 1.5(n-1)
    // = 1.5(choices)(n) + 1.5(n-1)(n)/2 seconds
    // where 1.5 is button-press duration, no choices is the number of
    // choices (for each stage), and n is total number of stages.
    private void initCountdownTimer() {
	countdownDuration = keyPress*no_choices*no_stages 
	    + keyPress*(no_choices-1)*no_choices/2;
    }

    //start the game
 
    //playing game, lets...
    public boolean checkAnswer(int choice) {
	return answers[cur_stage] == choice;
    }

    //if answer correct, then 
    public void updateScores() {
	if ( !beenHereBefore[cur_stage] ) guess_bonus++;
	markLocation();
    }

    private void markCurrentLocation() { 
	//called regardless of correct or
	//incorrect answer
	beenHereBefore[cur_stage] = true;
    }

    //and
    public boolean gotoNextStage() {
	cur_stage++;
	if ( cur_stage == no_stages ) { 
	    game_complete = true;
	    //Report scores
	    Score.time_bonus = mGameClock.getTimeLeft();
	    Score.guess_bonus = guess_bonus * 1000;
	    return true;
	}
	return false;
    }
    
    //otherwise incorrect
    public void resetCurrentLevel() {
	markCurrentLocation();
	cur_stage = 0;
    }

    //check game over/level complete

    public boolean isLevelComplete() { return game_complete; }

    //if successfully completed level,
    public void gotoNextLevel() {
	no_stages++;
	initStages();
	initScoring();
	cur_stage=0;
	game_complete=false;
	initCountdownTimer();
    }

    //otherwise game is over

    //interface

    //interface: time
    public long getCountdownDuration() { return countdownDuration; }

    //interface: inner logic
    public int currentStage() { return cur_stage+1; } //human readable
						      //format
    public int totalStages() { return no_stages; }

    public int getStages() { return no_stages; }

    public int getCurrentChoice(int i) {
	return stages[cur_stage][i];
    }

    public int getNumberOfChoices() { return no_choices; }

    public String toString() { /* for debugging */
	String tmp="\n GameEngine Output:\n";
	for( int i=0; i < no_stages; i++) {
	    tmp += "Choices: ";
	    for (int j=0; j < no_choices; j++) {
		tmp += stages[i][j] + " ";
	    }
	    tmp += " Ans: " + answers[i] + "\n";
	}
	tmp += "Current level: " + cur_stage + "\n";
	for ( int i=0; i < no_choices; i++)
	    tmp += getCurrentChoice(i) + " ";
	tmp += "\n";
	tmp += "GameComplete? " + gameComplete() + "   ";
	tmp += "No. Choices: " + getNumberOfChoices() + "\n";
	tmp += "Progress: " + currentStage() + "/" + totalStages() + "\n";
	tmp += "Score: duplicates=" + no_duplicates;
	tmp += " incorrect=" + no_incorrect;
	return tmp;
     }

}