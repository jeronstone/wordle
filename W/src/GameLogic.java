//import java.util.ArrayList;
import java.util.Random;

public class GameLogic {

	private int attempts;
	private final int MAX_ATTEMPTS = 6;
	
	private String answer;
	
	//private ArrayList<String> guesses;
	
	public enum GameState {WIN, LOSE, PLAYING};
	private GameState gameState;
	
	public GameLogic() {
		int index = new Random().nextInt(Const.possibleWords.size());
		this.answer = (String) Const.possibleWords.toArray()[index];
		
		attempts = 0;
		//guesses = new ArrayList<String>();
		
		gameState = GameState.PLAYING;
	}
	
	public boolean makeGuess(String guess) {
		if (!Const.possibleGuesses.contains(guess) || !gameState.equals(GameState.PLAYING)) {
			return false;
		}
		
		//this.guesses.add(guess);
		attempts++;
		
		if (guess.equals(answer)) {
			gameState = GameState.WIN;
		} else if (attempts == MAX_ATTEMPTS) {
			gameState = GameState.LOSE;
		}
		return true;
	}
	
	/*public ArrayList<String> getGuesses() {
		return new ArrayList<String>(guesses);
	} */
	
	public String getAnswer() {
		return answer;
	}
	
	public char[] cGetAnswer() {
		return answer.toCharArray();
	}
	
	public GameState getState() {
		return gameState;
	}
	
	public int attemptsMade() {
		return attempts;
	}
}
