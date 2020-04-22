
package main;

public class Board
{
	private static int rows = 15;
	private static int columns = 15;
	private static Square[][] squares = // The value representation of each square.
	{
            {Square.DOUBLE_LETTER,  Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_WORD,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER},
            {Square.NORMAL,        Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,             Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL},
            {Square.NORMAL,        Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,             Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL},
            {Square.TRIPLE_WORD,    Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,       Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_LETTER,   Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_WORD},
            {Square.NORMAL,        Square.NORMAL,         Square.NORMAL,         Square.NORMAL,             Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL},
            {Square.NORMAL,        Square.TRIPLE_LETTER,   Square.NORMAL,         Square.NORMAL,             Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL},
            {Square.NORMAL,        Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,       Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.NORMAL},
            {Square.DOUBLE_LETTER,  Square.NORMAL,         Square.NORMAL,         Square.NORMAL,             Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER},
            {Square.NORMAL,        Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_LETTER,       Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.NORMAL},
            {Square.NORMAL,        Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,             Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_LETTER,   Square.NORMAL},
            {Square.NORMAL,       Square.NORMAL,        Square.NORMAL,         Square.NORMAL,             Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL},
            {Square.TRIPLE_WORD,   Square.NORMAL,        Square.NORMAL,         Square.DOUBLE_LETTER,       Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.TRIPLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_WORD},
            {Square.NORMAL,       Square.DOUBLE_WORD,    Square.NORMAL,         Square.NORMAL,             Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL},
            {Square.NORMAL,       Square.NORMAL,        Square.DOUBLE_WORD,     Square.NORMAL,             Square.NORMAL,         Square.TRIPLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_WORD,     Square.NORMAL,         Square.NORMAL},
            {Square.DOUBLE_LETTER, Square.NORMAL,        Square.NORMAL,         Square.TRIPLE_WORD,         Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER,   Square.NORMAL,         Square.NORMAL,         Square.NORMAL,         Square.TRIPLE_WORD,     Square.NORMAL,         Square.NORMAL,         Square.DOUBLE_LETTER},
    };
	private Tile[][] tiles; // Matrix representing the currently placed tiles (or null).
	
	public Board()
	{
		reset();
	}
	
	public void reset()
	{
		tiles = new Tile[rows][columns];
	}
	
	public void placeWord(Frame frame, Location location, String word)
	{
		boolean horizontal = location.isHorizontal();
		int row = location.getRow();
		int col = location.getColumn();
		for (int i = 0 ; i < word.length() ; i++)
		{
			if (tiles[row][col] == null)
			{
				Tile tile = frame.popTile(word.charAt(i));
				tiles[row][col] = tile;
			}
			
			if (horizontal)
			{
				col++;
			}
			else
			{
				row++;
			}
		}
	}
}
