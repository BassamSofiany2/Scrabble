
package main;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

// Represents a box/square on the Graphical View (Grid) of the board.
// Accessed using getters (has private constructor)
//Acts like psuedo-enum
class Box extends StackPane
{
	private static final int imageSize = 44;
	// private blank character, for displaying.
	private static char blankChar = '*';
	// Images to be placed on each type of square
	private static final Image doubleLetterImage = new Image(Box.class.getResource("/resources/doubleletter.png").toString(), imageSize, imageSize, false, false);
	private static final Image tripleLetterImage = new Image(Box.class.getResource("/resources/tripleletter.png").toString(), imageSize, imageSize, false, false);
	private static final Image doubleWordImage = new Image(Box.class.getResource("/resources/doubleword.png").toString(), imageSize, imageSize, false, false);
	private static final Image tripleWordImage = new Image(Box.class.getResource("/resources/tripleword.png").toString(), imageSize, imageSize, false, false);
	private static final Image tileImage = new Image(Box.class.getResource("/resources/tile.png").toString(), imageSize, imageSize, false, false);
	
	private StackPane tilePane;
	private Box()
	{
		super();
		setMinWidth(imageSize);
		setMinHeight(imageSize);
		setMaxWidth(imageSize);
		setMaxHeight(imageSize);
		tilePane = new StackPane();
		// Some styling
		setStyle("-fx-background-color: rgb(212,169,117); -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: rgb(220,220,220);");
	}
	
	// create box using provided image
	private static Box createBox(ImageView image)
	{
		Box box = new Box();
		box.getChildren().add(image);
		setAlignment(image, Pos.CENTER);
		box.getChildren().add(box.tilePane);
		return box;
	}
	
	// Getters for the different types of boxes.
	public static Box getNormal()
	{
		Box box = new Box();
		box.getChildren().add(box.tilePane);
		return box;
	}
	
	public static Box getDoubleLetter()
	{
		return createBox(new ImageView(doubleLetterImage));
	}
	
	public static Box getTripleLetter()
	{
		return createBox(new ImageView(tripleLetterImage));
	}
	
	public static Box getDoubleWord()
	{
		return createBox(new ImageView(doubleWordImage));
	}
	
	public static Box getTripleWord()
	{
		return createBox(new ImageView(tripleWordImage));
	}
	
	// Add a tile to any box
	public void addTile(Tile tile)
	{
		if (tile == null)
		{
			tilePane.getChildren().clear();
			return;
		}
		char c = tile.getLetter();
		Text letter = new Text(Character.toString(c)); // The tile letter
		letter.setStyle("-fx-font-weight: bold");
		Text value = new Text(Integer.toString(tile.getValue())); // The tile value
		ImageView image = new ImageView(tileImage);
		tilePane.getChildren().addAll(image, value, letter);
		setAlignment(image, Pos.CENTER);
		setAlignment(letter, Pos.CENTER);
		setAlignment(value, Pos.TOP_LEFT);
		// Add an extra text at the top right corner in case of a blank tile
		if (tile.isBlank())
		{
			Text blank = new Text(Character.toString(blankChar));
			tilePane.getChildren().add(blank);
			setAlignment(blank, Pos.TOP_RIGHT);
		}
	}
}
