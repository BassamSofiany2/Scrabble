
package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Board
{
	private static int rows = 15;
	private static int columns = 15;
	private static int middle = 7;
	private static Square[][] squares = // The value representation of each square.
	{
			{Square.TRIPLE_WORD,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_WORD		},
			{Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL			},
			{Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL			},
			{Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	},
			{Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL			},
			{Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL			},
			{Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL			},
			{Square.TRIPLE_WORD,	Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_WORD,		},
			{Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL			},
			{Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL			},
			{Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL			},
			{Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER	},
			{Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.DOUBLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL			},
			{Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_LETTER,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.DOUBLE_WORD,		Square.NORMAL			},
			{Square.TRIPLE_WORD,	Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_WORD,		Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.NORMAL,			Square.TRIPLE_WORD		}
    };
	
	private Tile[][] tiles; // Matrix representing the currently placed tiles (or null).
	private int turns; // The number of words placed
	
	private ArrayList<Location> lastMove; // A list of locations, which where affected by the last move
	
	public Board()
	{
		reset();
	}
	
	// Create a fresh empty board
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
                    System.out.print(tile + "\t");
                }
            }
            System.out.println();
        }
	}
	
	// Check if the word can be placed from the frame at the location (include direction)
	// Doesnt check for valid characters in the word
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
				connected = true; // The word is connected to existing words.
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
	// Requires the word to be legal.
	public void placeWord(Frame frame, Location location, String word)
	{
		boolean horizontal = location.isHorizontal();
		int row = location.getRow();
		int col = location.getColumn();
		lastMove = new ArrayList<Location>();
		for (int i = 0 ; i < word.length() ; i++)
		{
			if (tiles[row][col] == null) // No tile at this location
			{
				Tile tile = frame.popTile(word.charAt(i));
				tiles[row][col] = tile;
				lastMove.add(new Location(row, col));
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

	// Place the word having blanks from the frame at the location (location also includes direction)
	// Requires the word to be legal.
	public void placeWord(Frame frame, Location location, String word, String forBlanks)
	{
		boolean horizontal = location.isHorizontal();
		int row = location.getRow();
		int col = location.getColumn();
		int blankIndex = 0;
		lastMove = new ArrayList<Location>();
		for (int i = 0 ; i < word.length() ; i++)
		{
			if (tiles[row][col] == null) // No tile at this location
			{
				Tile tile = frame.popTile(word.charAt(i));
				if (tile.isBlank())
				{
					tile.setLetter(forBlanks.charAt(blankIndex));
					blankIndex++;
				}
				tiles[row][col] = tile;
				lastMove.add(new Location(row, col));
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
	
	// Returns the Square type (double word etc) at the provided location.
	public static Square typeAt(int row, int col)
	{
		return squares[row][col];
	}
	
	// Returns the tile placed at the provided location.
	public Tile at(int row, int col)
	{
		return tiles[row][col];
	}
	
	// Returns the score for an extra word was created, otherwise 0
	private int additionalScore(int row, int col, char c, boolean horizontal, Dictionary dictionary)
	{
		char[] line = new char[15];
		int index;
		if (horizontal)
		{
			for (int i = 0 ; i < 15 ; i++)
			{
				if (tiles[i][col] == null)
				{
					line[i] = '\0';
				}
				else
				{
					line[i] = tiles[i][col].getLetter();
				}
			}
			line[row] = c;
			index = row;
		}
		else
		{
			for (int i = 0 ; i < 15 ; i++)
			{
				if (tiles[row][i] == null)
				{
					line[i] = '\0';
				}
				else
				{
					line[i] = tiles[row][i].getLetter();
				}
			}
			line[col] = c;
			index = col;
		}
		int left = index;
		while (left > 0)
		{
			if (line[left - 1] != '\0')
			{
				left--;
			}
			else
			{
				break;
			}
		}
		int right = index;
		while (right < 15)
		{
			if (line[right] != '\0')
			{
				right++;
			}
			else
			{
				break;
			}
		}
		if ((right - 1) == left)
		{
			return 0;
		}
		else
		{
			String word = new String(Arrays.copyOfRange(line, left, right));
			if (dictionary.isWord(word))
			{
				int score = 0;
				int wordBonus = 1;
				for (int i = left ; i < right ; i++)
				{
					Square s;
					if (horizontal)
					{
						s = squares[i][col];
					}
					else
					{
						s = squares[row][i];
					}
					score += s.getLetterMultiplier() * Pool.getValue(line[i]);
					wordBonus *= s.getWordMultiplier();
				}
				score *= wordBonus;
				return score;
			}
			else
			{
				return 0;
			}
		}
	}
	
	// Returns the score of placing the word at location.
	// Does not place the word
	public int getScore(Location location, String word, Dictionary dictionary)
	{
		boolean horizontal = location.isHorizontal();
		int row = location.getRow();
		int col = location.getColumn();
		int score = 0;
		int wordBonus = 1;
		int used = 0;
		for (int i = 0 ; i < word.length() ; i++)
		{
			score += squares[row][col].getLetterMultiplier() * Pool.getValue(word.charAt(i));
			wordBonus *= squares[row][col].getWordMultiplier();
			if (tiles[row][col] == null)
			{
				// Find perpendicular words to add their score as well
				score += additionalScore(row, col, word.charAt(i), horizontal, dictionary);
				used++;
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
		score *= wordBonus;
		if (used == Frame.capacity) // Known as the BINGO. Award 50 points for using complete frame
		{
			score += 50;
		}
		return score;
	}
	
	// Check if its the first turn
	public boolean isFirstTurn()
	{
		return (turns == 0);
	}
	
	// Undo the last move
	public void undoLast(Frame frame)
	{
		for(Location location : lastMove)
		{
			Tile tile = tiles[location.getRow()][location.getColumn()];
			frame.add(tile);
			tiles[location.getRow()][location.getColumn()] = null;
		}
		lastMove = null;
		turns--;
	}
}
