package uris.apps.com;

public class Score {
    static public int current_score;
    static public int time_bonus,guess_bonus;
    static public int total_score;
    //static public int score, current_score, total_score;
    //static public int time_bonus, error_bonus, guess_bonus;
    //static public int incorrect_penal, duplicate_penal;
    
    static public String mytoString() {
	String tmp;
        tmp = "Scores:";// + score;
	tmp += "  time bonus:" + time_bonus;
	//tmp += "  error bonus:" + error_bonus + "\n";
	//tmp += "Inc:" + incorrect_penal;
	//tmp += "  dup:" + duplicate_penal;
	return tmp;
    }

    static public void reportScores(boolean isLevelOver, int timeLeft) {
	time_bonus = (isLevelOver) ? 0 : timeLeft;
	guess_bonus = guess_bonus * 1000;
    }

    static public void setTotalScore() {
	total_score = current_score + time_bonus + guess_bonus;
    }

    static public void setCurrentScore() {
	current_score = current_score + time_bonus + guess_bonus;
    }

}