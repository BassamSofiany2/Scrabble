
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
    private static Pattern refPattern = Pattern.compile("^[A-O](0?[1-9]|1[0-5])$");
    private static char blankChar = '*';
    
	private TextField input;
	private TextArea output;
	private BorderPane consolePane;
	private Grid grid;
	private ScrollPane gridContainer;
	
	private Player player;
	private Board board;
	
	public UI(Board board, Player player)
	{
		super();
		this.player = player;
		this.board = board;
		setStyle("-fx-box-border: transparent;");
		setSpacing(20);
		input = new TextField();
		output = new TextArea();
		grid = new Grid(board);
		grid.setAlignment(Pos.CENTER);
		gridContainer = new ScrollPane();
		StackPane gridHolder = new StackPane(grid);
		gridHolder.minWidthProperty().bind(Bindings.createDoubleBinding(() -> gridContainer.getViewportBounds().getWidth(), gridContainer.viewportBoundsProperty()));
		gridHolder.minHeightProperty().bind(Bindings.createDoubleBinding(() -> gridContainer.getViewportBounds().getHeight(), gridContainer.viewportBoundsProperty()));
		gridContainer.setContent(gridHolder);
		setupConsole();
		getChildren().addAll(gridContainer, consolePane);
	}
	
	private void setupConsole()
	{
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
        	input.setText(inputPrompt);
            input.positionCaret(inputPrompt.length());
            handleInput(text);
        });
        
        output.setEditable(false);
        
        consolePane = new BorderPane();
        consolePane.setCenter(output);
        consolePane.setBottom(input);
	}
	public void exchangeCommand(String[] tokens)
	{
//		if (tokens.length < 2)
//		{
//			System.out.println("Error: Incomplete Command.");
//		}
//		else
//		{
//			String letters = tokens[1];
//			if (this.current_player.frame.exchange(letters, pool))
//			{
//				System.out.println("Letters Exchanged from Pool");
//			}
//			else
//			{
//				System.out.println("Error: Some letters were not found in your rack.");
//			}
//		}
	}

	public void passCommand()
	{
//		changePlayer();
	}

	public void quitCommand()
	{
		System.exit(0);
	}

	public void helpCommand()
	{
		
	}
	
	public void placeCommand(String[] tokens)
	{
		if (tokens.length < 3)
		{
			output.appendText("Error: Incomplete Command.\n");
			return;
		}
		boolean blank = false;
		String word = tokens[2];
		if (word.indexOf(blankChar) != -1)
		{
			if (tokens.length < 4)
			{
				output.appendText("Error: Blank Replacment Not Mentioned.\n");
				return;
			}
			blank = true;
			word = word.replace(blankChar, Pool.blankChar);
		}
		
		String ref = tokens[0];
		int col = ref.charAt(0) % 'A';
		int row = Integer.parseInt(ref.substring(1)) - 1;
		String dir = tokens[1];
		System.out.println(dir);
		if (dir.equals("A") || dir.equals("D"))
		{
			Direction direction = dir.equals("A") ? Direction.HORIZONTAL : Direction.VERTICAL;
			Location location = new Location(row, col, direction);
			Frame frame = player.getFrame();
			ErrorCode status = board.isLegal(frame, location, word);
			if (status == ErrorCode.SUCCESS)
			{
				if (blank)
				{
					board.placeWord(frame, location, word, tokens[3]);
				}
				else
				{
					board.placeWord(frame, location, word);
				}
				board.print();
				grid.update();
				output.appendText("Word Placed");
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
	private void handleInput(String text)
	{
		output.appendText(player.getFrame().toString());
		String[] tokens = text.toUpperCase().split("\\s+");
		Matcher matcher = refPattern.matcher(tokens[0]);
        boolean matchFound = matcher.matches();
		if (matchFound)
		{
			placeCommand(tokens);
		}
		else
		{
			output.appendText("notref\n");
			String command = tokens[0].toUpperCase();
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
				}
			}
		}
	}
}


//System.out.print("\n\nIt is your turn ");
//current_player.displayName();
//System.out.printf("Your score: %d\n", this.current_player.getScore());
//System.out.print("Your rack: ");
//current_player.frame.display();
//String move = current_move == null ? "" : current_move.word;
//System.out.printf("Your current move: %s\n>> ", move);
//String line = scanner.nextLine();