package uris.apps.com;

public class Score {
    static public int score, current_score, total_score;
    static public int time_bonus, error_bonus, guess_bonus;
    static public int incorrect_penal, duplicate_penal;
    
    static public String mytoString() {
	String tmp;
        tmp = "Score:" + score;
	tmp += "  time bonus:" + time_bonus;
	tmp += "  error bonus:" + error_bonus + "\n";
	tmp += "Inc:" + incorrect_penal;
	tmp += "  dup:" + duplicate_penal;
	return tmp;
    }
}