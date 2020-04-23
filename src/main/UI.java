
package main;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI extends HBox
{
	private static String inputPrompt = ">>  ";
	// A regex pattern representing a valid location for the board.
    private static Pattern refPattern = Pattern.compile("^[A-O](0?[1-9]|1[0-5])$");
    private static char blankChar = '*';
    
    // GUI components
	private TextField input; // For getting player input.
	private TextArea output; // For showing the command output.
	private BorderPane consolePane; // Container for input and output
	private Grid grid; // The graphical scrabble board
	private ScrollPane gridContainer; // To allow scrolling
	
	private Player[] players; // Array of players
	private Player player; // The current player
	private Board board;
	private Pool pool;
	private int currentPlayer; // The index of the current player
	private int scorelessTurns; // The number of turns since someone scored
	private Dictionary dictionary; // For checking valid words
	
	private String lastWord;
	private int lastScore;
	private int lastPlayer;
	
	public UI(Board board, Player[] players, Pool pool, Dictionary dic)
	{
		super();
		dictionary = dic;
		this.pool = pool;
		this.players = players;
		for (Player player : this.players)
		{
			player.getFrame().refill(this.pool);
		}
		currentPlayer = 0;
		scorelessTurns = 0;
		player = this.players[0];
		this.board = board;
		setStyle("-fx-box-border: transparent;");
		setSpacing(20);
		input = new TextField();
		output = new TextArea();
		output.setWrapText(true);
		grid = new Grid(board);
		grid.setAlignment(Pos.CENTER);
		gridContainer = new ScrollPane();
		StackPane gridHolder = new StackPane(grid);
		
		// Bind width of the two layers of containers of grid to make them the same size.
		// Allowing the grid to be centered wrt the window.
		gridHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() -> gridContainer.getViewportBounds().getWidth(), gridContainer.viewportBoundsProperty()));
		gridHolder.minHeightProperty().bind(Bindings.createDoubleBinding(() -> gridContainer.getViewportBounds().getHeight(), gridContainer.viewportBoundsProperty()));
		gridContainer.setContent(gridHolder);
		setupConsole();
		getChildren().addAll(gridContainer, consolePane);
		startTurn();
	}
	
	// Show the output for the start of a turn
	private void startTurn()
	{
		String name = player.getName();
		String frame = player.getFrame().toString().replace(Pool.blankChar, blankChar);
		output.appendText("\n" + name + "'s turn.\nAvailable Tiles: " + frame + "\n> ");
	}
	
	// Round Robin through the players
	private void changePlayer()
	{
		currentPlayer++;
		currentPlayer %= players.length;
		player = players[currentPlayer];
		player.getFrame().refill(pool);
	}
	
	// Setup the I/O GUI
	private void setupConsole()
	{
		// A filter on changes made on input
		// Just to show a permanent prompt (>>).
        UnaryOperator<TextFormatter.Change> filter = change ->
        {
        	if (change.getText() == inputPrompt && change.getCaretPosition() == 0)
        	{
        		return change;
        	}
        	else if (change.getCaretPosition() < inputPrompt.length() || change.getAnchor() < inputPrompt.length())
            {
                return null;
            }
            else
            {
                return change;
            }
        };
        input.setTextFormatter(new TextFormatter<String>(filter));
        input.setText(inputPrompt);
        input.positionCaret(inputPrompt.length());
        
        // Handle input when enter is pressed
        input.setOnAction(event ->
        {
        	String text = input.getText().substring(inputPrompt.length());
        	if (text.isEmpty())
        	{
        		return;
        	}
        	input.setText(inputPrompt);
            input.positionCaret(inputPrompt.length());
            handleInput(text);
        });
        
        output.setEditable(false);
        
        consolePane = new BorderPane();
        consolePane.setCenter(output);
        consolePane.setBottom(input);
	}
	
	// Handle the EXCHANGE command
	public void exchangeCommand(String[] tokens)
	{
		if (tokens.length < 2)
		{
			output.appendText("Error: Tiles to be exchanged were not mentioned.\n");
		}
		else
		{
			String letters = tokens[1];
			if (pool.getSize() < letters.length()) // Pool too small
			{
				output.appendText("Error: Pool doesn't have enough tiles.\n");
			}
			else if (!player.getFrame().hasLetters(letters))
			{
				output.appendText("Error: Some tiles were not found in your rack.\n");
			}
			else
			{
				scorelessTurns++;
				player.getFrame().exchange(letters, pool);
				output.appendText(player.getName() + " exchanged their tiles\n");
				lastScore = 0;
				lastWord = null;
				changePlayer();
			}
		}
	}

	// Handle the PASS command (change the turn)
	public void passCommand()
	{
		scorelessTurns++;
		lastScore = 0;
		lastWord = null;
		output.appendText(player.getName() + " passed their turn.\n");
		changePlayer();
	}

	// Handle the QUIT command
	public void quitCommand()
	{
		System.exit(0);
	}

	// Handle the HELP command (display instructions).
	public void helpCommand()
	{
		output.appendText
		(
			  "\n\nValid Commands: QUIT, PASS, EXCHANGE, SCORE, POOL\n"
			+ "\nTo place a word, use this command: <COLUMN><ROW> <DIRECTION> <WORD> [<BLANK LETTERS>]\n"
			+ "COLUMN are from A to O, ROW from 1 to 15, DIRECTION are D (down) or A (across) and WORD is your choice.\n"
			+ "Example: H8 D TEST\n"
			+ "\nBLANK LETTER are required when a blank tile is used\n"
			+ "To use a blank tile, place '*' where u want the blanks to be in the WORD and follow by their letters\n"
			+ "Example: B6 A T*S* ET\n"
			+ "\nQUIT(close game), PASS(pass the turn), SCORE(show scores) and POOL(show number of tiles in pool) are single word commands, i.e. no arguements required\n"
			+ "\nEXCHANGE can be used to swap some number of the with the pool. After which the turn will be lost\n"
			+ "EXCHANGE uses this syntax: EXCHANGE <LETTERS>\n"
			+ "LETTERS are the tiles you want to swap\n"
			+ "Example: EXCHANGE AC*\n\n"
		);
	}
	
	// Handle the command to place word on board
	public void placeCommand(String[] tokens)
	{
		if (tokens.length < 3)
		{
			output.appendText("Error: Incomplete Command.\n");
			return;
		}
		boolean blank = false;
		String word = tokens[2];
		if (word.indexOf(blankChar) != -1) // Check if word includes blanks
		{
			if (tokens.length < 4)
			{
				output.appendText("Error: Blank Replacment Not Mentioned.\n");
				return;
			}
			blank = true;
			word = word.replace(blankChar, Pool.blankChar); // Replace GUI blanks with Pool blanks.
		}
		
		// Find actual indices from the command
		String ref = tokens[0];
		int col = ref.charAt(0) % 'A';
		int row = Integer.parseInt(ref.substring(1)) - 1;
		
		// Extract direction from the command
		String dir = tokens[1];
		
		if (dir.equals("A") || dir.equals("D")) // Check for valid direction
		{
			// Create a location object for the move
			Direction direction = dir.equals("A") ? Direction.HORIZONTAL : Direction.VERTICAL;
			Location location = new Location(row, col, direction);
			
			// Check if the move is allowed
			Frame frame = player.getFrame();
			ErrorCode status = board.isLegal(frame, location, word);
			if (status == ErrorCode.SUCCESS) // If it is allowed.
			{
				int score = board.getScore(location, word); // Find the score of the move
				if (blank)
				{
					board.placeWord(frame, location, word, tokens[3]); // Place word with blank.
					char[] array = new char[word.length()];
					int index = 0;
					for (int i = 0 ; i < word.length() ; i++)
					{
						if (word.charAt(i) == Pool.blankChar)
						{
							array[i] = tokens[3].charAt(index);
							index++;
						}
						else
						{
							array[i] = word.charAt(i);
						}
					}
					word = new String(array);
				}
				else
				{
					board.placeWord(frame, location, word); // Place word with blank.
				}
				lastWord = word;
				lastScore = score;
				lastPlayer = currentPlayer;
				grid.update(); // Update the GUI Board
				player.changeScore(score); // Update the current player score
				output.appendText("Word Placed: " + word + "\n");
				changePlayer();
			}
			else
			{
				output.appendText("Invalid Move: " + status.toString() + "\n");
			}
		}
		else
		{
			System.out.println("Error: Invalid Direction");
		}
	}
	
	
	private void challengeCommand()
	{
		if (board.isFirstTurn() || lastPlayer == currentPlayer || lastWord == null)
		{
			output.appendText("Error: Your Opponent did not make the last move\n");
		}
		else if (dictionary.isWord(lastWord))
		{
			output.appendText(lastWord + " is a word\n" + player.getName() + " lost the challenge.\nChanging Turn...\n");
			changePlayer();
			lastScore = 0;
			lastWord = null;
		}
		else
		{
			output.appendText(lastWord + " is not a word\n" + player.getName() + " won the challenge.\nRemoving Word and Score of last move...\n");
			board.undoLast(players[lastPlayer].getFrame());
			players[lastPlayer].changeScore(-lastScore);
			lastScore = 0;
			lastWord = null;
			grid.update();
		}
	}
	
	// Print the current scores
	private void scoreCommand()
	{
		output.appendText("Scores:\n");
		for (Player player : players)
		{
			output.appendText("\t" + player.getName() + ": " + player.getScore() + "\n");
		}
	}
	
	// Decides which command handler needs to be invoked
	private void handleInput(String text)
	{
		output.appendText(text.toUpperCase() + "\n");
		String[] tokens = text.toUpperCase().split("\\s+"); // Split the command at any whitespace
		
		// Check if the first token is a valid board location
		Matcher matcher = refPattern.matcher(tokens[0]);
        boolean matchFound = matcher.matches();
		if (matchFound)
		{
			placeCommand(tokens);
		}
		else
		{
			// Choose the command if it is not a word placement
			String command = tokens[0];
			switch (command)
			{
				case "QUIT":
				{
					quitCommand();
					break;
				}
				case "PASS":
				{
					passCommand();
					break;
				}
				case "HELP":
				{
					helpCommand();
					break;
				}
				case "EXCHANGE":
				{
					exchangeCommand(tokens);
					break;
				}
				case "SCORE":
				{
					scoreCommand();
					break;
				}
				case "CHALLENGE":
				{
					challengeCommand();
					break;
				}
				default:
				{
					output.appendText("Error: Invalid Command Received.\n");
				}
			}
		}
		startTurn(); // Starting text of the next turn
	}
}
