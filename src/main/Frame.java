
package main;

import java.util.ArrayList;

public class Frame
{
	public static int capacity = 7; // The maximum number tiles in the frame
	
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
	
	public Tile getTile(char letter)
	{
		return tiles.get(tiles.indexOf(new Tile(letter)));
	}
	
	// Return the actual reference to the tiles (NOT A COPY).
	public ArrayList<Tile> getTiles()
	{
		return tiles;
	}
	
	public Tile popTile(char letter)
	{
		Tile tile = getTile(letter);
		remove(tile);
		return tile;
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
		ArrayList<Tile> copyFrame = new ArrayList<Tile>(tiles);
		for (int i = 0 ; i < letters.length() ; i++)
		{
			Tile tile = new Tile(letters.charAt(i));
			if (copyFrame.indexOf(tile) == -1)
			{
				return false;
			}
			copyFrame.remove(tile);
		}
		return true;
	}
	
	// Add given tile to frame.
	public boolean add(Tile tile)
	{
		if (tiles.size() == capacity)
		{
			return false;
		}
		tiles.add(tile);
		return true;
	}

	// Add tile using given letter to frame.
	public boolean add(char letter)
	{
		add(new Tile(letter));
		return true;
	}
	

	// Add tiles using given string/letters to frame.
	public boolean add(String letters)
	{
		if ((tiles.size() + letters.length()) > capacity)
		{
			return false;
		}
		for (int i = 0 ; i < letters.length() ; i++)
		{
			add(letters.charAt(i));
		}
		return true;
	}
	
	// Add given list tiles to frame.
	public boolean add(ArrayList<Tile> tiles)
	{
		if ((this.tiles.size() + tiles.size()) > capacity)
		{
			return false;
		}
		this.tiles.addAll(tiles);
		return true;
	}
	
	public void exchange(String letters, Pool pool)
	{
		for (int i = 0 ; i < letters.length() ; i++)
		{
			char c = letters.charAt(i);
			Tile tile = popTile(c);
			pool.addTile(tile);
		}
		refill(pool);
	}
	
	public boolean isEmpty()
	{
		return (tiles.size() == 0);
	}
	
	@Override
	public String toString()
	{
		return tiles.toString();
	}
}
