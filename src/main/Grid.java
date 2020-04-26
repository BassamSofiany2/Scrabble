package main;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

// Class representing the Graphical View of the board
public class Grid extends GridPane
{
	private int rows = 15;
	private int columns = 15;
	
	public Board board;
	private Box[][] grid;
	public Grid(Board board)
	{
		super();
		this.board = board;
		reset();
	}

	public void reset()
	{
		getChildren().clear();
		this.grid = new Box[rows][columns];
		
		// Create the reference lines
		for (int j = 0 ; j < this.columns ; j++)
		{
			Character c = new Character((char)(65 + j));
			Integer i = new Integer(j + 1);
			StackPane c_text = new StackPane(); // The columns alphabet labels
			c_text.getChildren().add(new Text(c.toString()));
			StackPane i_text = new StackPane(); // The rows integer labels
			i_text.getChildren().add(new Text(i.toString()));
			i_text.setAlignment(Pos.CENTER_RIGHT);
			c_text.setAlignment(Pos.TOP_CENTER);
	        setRowIndex(c_text, rows);
	        setColumnIndex(c_text, j + 1);
	        setRowIndex(i_text, j);
	        setColumnIndex(i_text, 0);
			getChildren().addAll(i_text, c_text);
		}
		
		// Fill the grid with boxes
		for (int i = 0 ; i < this.rows ; i++)
		{
			for (int j = 0 ; j < this.columns ; j++)
			{
				Box box;
				Square square = Board.typeAt(i, j);
	        	if (square == Square.DOUBLE_LETTER)
	        	{
	        		box = Box.getDoubleLetter();
	        	}
	        	else if (square == Square.TRIPLE_LETTER)
	        	{
	        		box = Box.getTripleLetter();
	        	}
	        	else if (square == Square.DOUBLE_WORD)
	        	{
	        		box = Box.getDoubleWord();
	        	}
	        	else if (square == Square.TRIPLE_WORD)
	        	{
	        		box = Box.getTripleWord();
	        	}
	        	else
	        	{
	        		box = Box.getNormal();
	        	}
	        	setRowIndex(box, i);
		        setColumnIndex(box, j + 1);
		        grid[i][j] = box;
		        getChildren().add(box);
			}
		}
	}

	// Update the Graphical View with the board data.
	public void update()
	{
		for (int i = 0 ; i < this.rows ; i++)
		{
			for (int j = 0 ; j < this.columns ; j++)
			{
				grid[i][j].addTile(board.at(i, j));
			}
		}
	}
}