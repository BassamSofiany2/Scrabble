
package main;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

class Box extends StackPane
{
	private static final int imageSize = 44;
	private static char blankChar = '*';
	private static final Image doubleLetterImage = new Image(Box.class.getResource("/resources/doubleletter.png").toString(), imageSize, imageSize, false, false);
	private static final Image tripleLetterImage = new Image(Box.class.getResource("/resources/tripleletter.png").toString(), imageSize, imageSize, false, false);
	private static final Image doubleWordImage = new Image(Box.class.getResource("/resources/doubleword.png").toString(), imageSize, imageSize, false, false);
	private static final Image tripleWordImage = new Image(Box.class.getResource("/resources/tripleword.png").toString(), imageSize, imageSize, false, false);
	private static final Image tileImage = new Image(Box.class.getResource("/resources/tile.png").toString(), imageSize, imageSize, false, false);
	
	private Box()
	{
		super();
		setStyle("-fx-background-color: rgb(212,169,117); -fx-border-style: solid; -fx-border-width: 1; -fx-border-color: rgb(220,220,220);");
	}
	
	private static Box createBox(ImageView image)
	{
		Box box = new Box();
		box.getChildren().add(image);
		setAlignment(image, Pos.CENTER);
		return box;
	}
	
	public static Box getNormal()
	{
		return new Box();
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
	
	public void addTile(Tile tile)
	{
		if (tile == null)
		{
			getChildren().clear();
		}
		char c = tile.getLetter();
		Text letter = new Text(Character.toString(c));
		letter.setStyle("-fx-font-weight: bold");
		Text value = new Text(Integer.toString(Pool.getValue(tile)));
		ImageView image = new ImageView(tileImage);
		getChildren().addAll(image, value, letter);
		setAlignment(image, Pos.CENTER);
		setAlignment(letter, Pos.CENTER);
		setAlignment(value, Pos.TOP_LEFT);
		if (tile.isBlank())
		{
			Text blank = new Text(Character.toString(blankChar));
			getChildren().addAll(blank);
			setAlignment(image, Pos.TOP_RIGHT);
		}
	}
}
