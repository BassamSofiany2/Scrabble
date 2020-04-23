
package main;

public class Board
{
	private static int rows = 15;
	private static int columns = 15;
	private static int middle = 7;
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
	private int turns; // The number of words placed
	
	public Board()
	{
		reset();
	}
	
	public void reset()
	{
		tiles = new Tile[rows][columns];
		turns = 0;
	}
	
	// Print the board to console ('-' for empty)
	public void print()
	{
		for (Tile[] row : tiles)
        {
            for (Tile tile : row)
            {
                if (tile == null)
                {
                    System.out.print("-\t");
                }
                else
                {
                    System.out.print(tile.getLetter() + "\t");
                }
            }
            System.out.println();
        }
	}
	
	public ErrorCode isLegal(Frame frame, Location location, String word)
	{
		int row = location.getRow();
		int col = location.getColumn();
		int n = word.length();
		
		// First turn checks.
		if (turns == 0 && (row != middle || col != middle))
		{
			return ErrorCode.WORD_MUST_START_AT_CENTER;
		}
		
		// Word Length check.
		if (n < 2)
		{
			return ErrorCode.WORD_TOO_SHORT;
		}
		
		// Out of bound check.
		if (row < 0 || col < 0 || row >= rows || col >= columns
			|| (location.isHorizontal() && (col + n) > columns)
			||	(location.isVertical() && (row + n) > rows))
		{
			return ErrorCode.WORD_OUT_OF_BOUND;
		}
		
		String lettersUsed = "";
		boolean connected = false;
		
		// Check if the word conflicts with an existing word.
		for (int i = 0; i < n ; i++)
		{
			if (tiles[row][col] != null)
			{
				if (tiles[row][col].getLetter() != word.charAt(i))
				{
					return ErrorCode.CONFLICTING_WORD;
				}
				connected = true; // The word is connected existing words.
			}
			else
			{
				lettersUsed += word.charAt(i);
			}
			if (location.isHorizontal())
			{
				col++;
			}
			else
			{
				row++;
			}
		}

		// Number of letters used check.
		if (lettersUsed.length() == 0)
		{
			return ErrorCode.NO_LETTERS_USED;
		}
		
		// Check availability of used letters
		if (!frame.hasLetters(lettersUsed))
		{
			return ErrorCode.UNAVAILABLE_LETTERS_USED;
		}
		
		// Check if the word is connected to the existing words
		if (turns > 0 && !connected)
		{
			return ErrorCode.DISCONNECTED_WORD;
		}
		
		return ErrorCode.SUCCESS;
	}
	
	// Place the word from the frame at the location (location also includes direction)
	public void placeWord(Frame frame, Location location, String word)
	{
		boolean horizontal = location.isHorizontal();
		int row = location.getRow();
		int col = location.getColumn();
		for (int i = 0 ; i < word.length() ; i++)
		{
			if (tiles[row][col] == null) // No tile at this location
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
		turns++;
	}
	
	public int getRows()
	{
		return rows;
	}
	
	public int getColumns()
	{
		return columns;
	}
	
	public static Square typeAt(int row, int col)
	{
		return squares[row][col];
	}
	
	public Tile at(int row, int col)
	{
		return tiles[row][col];
	}
}
