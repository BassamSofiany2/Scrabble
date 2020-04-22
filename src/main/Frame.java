
package main;

import java.util.ArrayList;

public class Frame
{
	private static int capacity = 7; // The maximum number tiles in the frame
	
	private ArrayList<Tile> tiles;
	
	public Frame()
	{
		tiles = new ArrayList<Tile>(capacity);
	}
	
	// Remove tiles based on a given character/letter.
	public void remove(char letter)
	{
		Tile tile = new Tile(letter);
		tiles.remove(tile);
	}
	
	// Remove tiles based on given string/letters.
	public void remove(String letters)
	{
		for (int i = 0 ; i < letters.length() ; i++)
		{
			remove(letters.charAt(i));
		}
	}
	
	// Remove a single tile from frame.
	public void remove(Tile tile)
	{
		this.tiles.remove(tile);
	}
	
	// Remove a given list of tiles from frame.
	public void remove(ArrayList<Tile> tiles)
	{
		this.tiles.removeAll(tiles);
	}
	
	// Return a string of letters representing the frame.
	public String getLetters()
	{
		char[] letters = new char[tiles.size()];
		for (int i = 0 ; i < tiles.size() ; i++)
		{
			letters[i] = tiles.get(i).getLetter();
		}
		String lettersStr = new String(letters);
		return lettersStr;
	}
	
	// Return the actual reference to the tiles (NOT A COPY).
	public ArrayList<Tile> getTiles()
	{
		return tiles;
	}
	
	// Draw tiles from pool until frame is full.
	public void refill(Pool pool)
	{
		for (int i = tiles.size() ; i < capacity ; i++)
		{
			tiles.add(pool.drawTile());
		}
	}
	
	// Check if the string of letters exist in the frame.
	public boolean hasLetters(String letters)
	{
		for (int i = 0 ; i < letters.length() ; i++)
		{
			Tile tile = new Tile(letters.charAt(i));
			if (tiles.indexOf(tile) == -1)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isEmpty()
	{
		return (tiles.size() != 0);
	}
}
