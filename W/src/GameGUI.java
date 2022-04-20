import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GameGUI extends JPanel {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);		
			new GameGUI();
		});	
	}
	
	// I suck at GUIs

	private GameLogic game;

	private JLabel[][] wordDisp;
	private JTextField textField = new JTextField(5);
	private JLabel gameState;

	private final int HEIGHT = 475;
	private final int WIDTH = 420;
	private final int JLSIZE = 65;

	private final Font SWAGFONT = new Font("SWAG", Font.PLAIN, 21);

	public GameGUI() {
		try {
			Const.initializeLists();
		} catch (IOException e) {
			System.exit(1);
		}

		game = new GameLogic();
		setupDIO();
		setupPanel();
		setupFrame();
	}

	private void setupDIO() {
		wordDisp = new JLabel[5][6];
		for (int i = 0; i < wordDisp.length; i ++) {
			for (int j = 0; j < wordDisp[i].length; j ++) {
				wordDisp[i][j] = new JLabel(" ");
				wordDisp[i][j].setBounds(i * JLSIZE + 70, j * JLSIZE, JLSIZE, JLSIZE);
				wordDisp[i][j].setForeground(Color.WHITE);
				wordDisp[i][j].setFont(SWAGFONT);
			}
		}

		textField.addActionListener(e -> {
			if (e.getActionCommand().equals("tf")) {
				String guess = textField.getText().toLowerCase();
				textField.setText("");
				if (game.makeGuess(guess)) {
					update(guess);
				}
			}
		});
		textField.setActionCommand("tf");
		textField.setBounds(50, 400, 310, 25);

		gameState = new JLabel(" ");
		gameState.setForeground(Color.WHITE);
		gameState.setBounds(10, 425, 500, 50);
		gameState.setFont(SWAGFONT);
	}

	private void setupPanel() {
		setLayout(null);
		for (int i = 0; i < wordDisp.length; i ++) {
			for (int j = 0; j < wordDisp[i].length; j ++) {
				add(wordDisp[i][j]);
			}
		}
		add(textField);
		add(gameState);
		setBackground(Color.BLACK);
		setSize(WIDTH, HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setBounds(new java.awt.Rectangle(WIDTH, HEIGHT));
	}

	private void setupFrame() {
		JFrame frame = new JFrame("Wordle");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.pack();
		frame.setVisible(true);
	}

	private void update(String guess) {
		int attemptsMade = game.attemptsMade() - 1;
		char[] cGuess = guess.toCharArray();
		Color[] colors = getColor(guess);
		for (int i = 0; i < wordDisp.length; i ++) {
			wordDisp[i][attemptsMade].setText(("" + cGuess[i]).toUpperCase());
			wordDisp[i][attemptsMade].setForeground(colors[i]);
		}

		checkGameState();
	}

	private void checkGameState() {
		switch (game.getState()) {
		case WIN:
			gameState.setText("YOU WIN!");
			break;
		case LOSE:
			gameState.setText("YOU LOSE :( THE WORD WAS " + game.getAnswer().toUpperCase());
			break;
		case PLAYING:

		}
	}

	private Color[] getColor(String guess) {
		char[] cLastGuess = guess.toCharArray();
		char[] cAns = game.cGetAnswer();

		ArrayList<String> amountPerGuess = new ArrayList<>();
		for (char c : cLastGuess) {
			amountPerGuess.add(""+ c + containsHowMany(cAns, c));
		}

		Color[] letterCols = new Color[Const.MAX_WORD_LENGTH];
		for (int i = 0; i < Const.MAX_WORD_LENGTH; i ++) {
			if (Integer.parseInt(amountPerGuess.get(i).substring(1)) >= 1) {
				if (cLastGuess[i] == cAns[i]) {
					letterCols[i] = Color.GREEN;
					amountPerGuess = changeAll(amountPerGuess, cLastGuess[i]);
				} else {
					letterCols[i] = Color.YELLOW;
					amountPerGuess = changeAll(amountPerGuess, cLastGuess[i]);
				}
			} else {
				letterCols[i] = Color.WHITE;
			}
		}

		return letterCols;
	}

	private static int containsHowMany(char[] array, char c) {
		int count = 0;
		for (char ch : array) {
			if (ch == c) {
				count++;
			}
		}
		return count;
	}

	private static ArrayList<String> changeAll(ArrayList<String> amountPerGuess, char c) {
		int i = 0;
		for (String s : amountPerGuess) {
			if (s.substring(0, 1).equals("" + c)) {
				amountPerGuess.set(i, "" + c + (Integer.parseInt(amountPerGuess.get(i).substring(1)) - 1));
			}
			i++;
		}
		return amountPerGuess;
	}
}
