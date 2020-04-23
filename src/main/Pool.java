
package main;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class Pool
{
	public static final char blankChar = '@';
	// Represents the values of each tile, first index for blank tile (@) and then A-Z.
	private static final int[] tileValues = {0, 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
	// Represents the initial count of each tile, index same as tileValues.
	private static final int[] tilesCount = {2, 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
	private static final Random generator = new Random();
	
	private ArrayList<Tile> pool; // The actual pool of tiles.
	public Pool()
	{
		reset();
	}
	public int getSize()
	{
		return pool.size();
	}
	
	// Reset the pool to initial conditions.
	public void reset()
	{
		int count = Arrays.stream(tilesCount).sum();
		pool = new ArrayList<Tile>(count);
		int done = 0;
		for (int i = 0 ; i < tilesCount.length ; i++)
		{
			for (int j = 0 ; j < tilesCount[i] ; j++)
			{
				Tile tile = new Tile((char)(i + blankChar));
				pool.add(done, tile);
				done++;
			}
		}
	}
	
	public boolean isEmpty()
	{
		return (pool.size() == 0);
	}
	
	public Tile drawTile()
	{
		if (pool.size() == 0)
		{
			return null;
		}
		int index = generator.nextInt(pool.size());
		Tile tile = pool.get(index);
		pool.remove(index);
		return tile;
	}

	public static int getValue(Tile tile)
	{
		return tileValues[tile.getLetter() - blankChar];
	}
	
	public static int getValue(char letter)
	{
		System.out.print(letter);
		return tileValues[letter - blankChar];
	}
	
	public void addTile(Tile tile)
	{
		pool.add(tile);
	}
	
	@Override
	public String toString()
	{
		return pool.toString();
	}
}
