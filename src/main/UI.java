
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

public class UI extends HBox
{
	private static String inputPrompt = ">>  ";
	private TextField input;
	private TextArea output;
	private BorderPane consolePane;
	private Grid grid;
	private ScrollPane gridContainer;
	
	public UI(Board board)
	{
		super();
		setStyle("-fx-box-border: transparent;");
		setSpacing(20);
		input = new TextField(inputPrompt);
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
            if (change.getCaretPosition() < inputPrompt.length() || change.getAnchor() < inputPrompt.length())
            {
                return null ;
            }
            else
            {
                return change;
            }
        };
        
        input.setTextFormatter(new TextFormatter<String>(filter));
        input.positionCaret(inputPrompt.length());
        
        // Handle input when enter is pressed
        input.setOnAction(event ->
        {
        	
        });
        
        output.setEditable(false);
        
        consolePane = new BorderPane();
        consolePane.setCenter(output);
        consolePane.setBottom(input);
	}
	
	public void addOutput(String text)
	{
		output.appendText(text);
	}
	
	public String getInput()
	{
		return input.getText();
	}
	
}
