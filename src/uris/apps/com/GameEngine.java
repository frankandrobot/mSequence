package uris.apps.com;

import java.util.Random;

public class GameEngine {
    //public settings
    static int EASY=0, MEDIUM=1, HARD=2;
    static int MAX_CHOICES=3;

    //private (inner) settings
    int difficulty=EASY; //default difficulty
    int no_art_pieces=0; //number of art pieces
    int no_stages;
    int no_choices=2; //default number of choices
    int[][] choices;
    int[] answers;
    int cur_stage=0;
    boolean game_complete=false;
    Random rg; //used to generate choices
    int[] current_settings; //used to save current settings

    //scoring
    boolean[] beenHereBefore;
    int guess_bonus;
    

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
	//setup variables
	no_art_pieces = art;
	difficulty = difficulty_;
	no_stages = stages;
	if ( difficulty == HARD ) {
	    no_choices = 3;
	}
	cur_stage=0;
	game_complete=false;
	beenHereBefore = new boolean[no_stages];
	//setup random number generator
	rg = new Random();
	//actually generate stages
	generateStages();
	//set countdown timer
	setCountdownTimer();
	//save current settings
	saveSettings();
    }

    public int getStages() { return no_stages; }

    private void saveSettings() { }	

    public void reset() { 
	cur_stage=0;
	game_complete=false;
	for(int i; i<no_stages; i++)
	    beenHereBefore[i] = false;
    }

    //Actually initializes the GameEngine given the difficulty
    //settings, no of art pices, and number of stages.
    public void generateStages() {
	choices = new int[no_stages][no_choices];
	scores = new int[no_stages][no_choices];
	for (int i=0; i<no_stages; i++) {
	    for (int j=0; j<no_choices; j++) {
		choices[i][j] = rg.nextInt( no_art_pieces );
	    }
	    if ( difficulty == EASY ) 
	    	eliminateDuplicates( i );
	}
	//set answers
	answers = new int[no_stages];
	for (int i=0; i<no_stages; i++) {
	    //randomly select one of the art pieces at that
	    //current stage
	    int choice = rg.nextInt( no_choices );
	    answers[i] = choice;
	}
    }
    
    //Eliminate duplicates in a given stage/level. Needed by
    //generateStages.
    private void eliminateDuplicates( int cur_stage ) {
	for (int j=1; j<no_choices; j++) {
	    for (int k=0; k<j; k++) {
		while ( choices[cur_stage][k] == choices[cur_stage][j] ) {
			choices[cur_stage][j] = rg.nextInt( no_art_pieces );
		    }
		k = 0;
	    }
	}
    }

    public int getCurrentChoice(int i) {
	return choices[cur_stage][i];
    }

    public int getNumberOfChoices() { return no_choices; }

    //start the game
    private void setCountdownTimer() {
    }

    //actually start the game
    public void startCountdownTimer() {
	startTime = s;
    }

    //playing game, lets...
    public boolean checkAnswer(int choice) {
	return answers[cur_stage] == choice;
    }

    //if answer correct, then 
    public void updateScores() {
	if ( !beenHereBefore[cur_stage] ) guess_bonus++;
	markLocation();
    }

    //and
    public boolean goNextStage(long finishTime) {
	cur_stage++;

	if ( cur_stage == no_stages ) { 
	    game_complete = true;
	    stopCountdownTimer();
	    //Report scores
	    Score.time_bonus = getTimeLeft() * 1000;
	    Score.guess_bonus = guess_bonus * 1000;
	    return true;
	}
	return false;
    }

    //otherwise if incorrect answer
    public void resetGame() {
	markLocation();
	cur_stage = 0;
    }

    private void markLocation() { //called regardless of correct or
				  //incorrect answer
	beenHereBefore[cur_stage] = true;
    }


    public boolean gameComplete() { return game_complete; }

    public void nextGame() {
	no_stages++;
	generateStages();
    }

    private void stopCountdownTimer() {}

    public long getTimeLeft() {}


    public int currentStage() { return cur_stage+1; } //human readable
						      //format
    public int totalStages() { return no_stages; }

    public String toString() { /* for debugging */
	String tmp="\n GameEngine Output:\n";
	for( int i=0; i < no_stages; i++) {
	    tmp += "Choices: ";
	    for (int j=0; j < no_choices; j++) {
		tmp += choices[i][j] + " ";
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