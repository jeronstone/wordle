import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Const {

	public static Set<String> possibleGuesses;
	public static Set<String> possibleWords;

	public static final int MAX_WORD_LENGTH = 5;

	public static void initializeLists() throws IOException {
		possibleGuesses = fileInit("src/possible_guesses.txt");
		possibleWords = fileInit("src/possible_words.txt");
	}

	private static HashSet<String> fileInit(String file) throws IOException {
		BufferedReader bufReader = new BufferedReader(new FileReader(file));

		HashSet<String> fileList =  new HashSet<String>();

		String line = bufReader.readLine();
		while (line != null) {
			fileList.add(line);
			line = bufReader.readLine();

		}

		bufReader.close();
		return fileList;
	}
}
