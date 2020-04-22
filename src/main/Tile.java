
package main;

public class Tile
{
	private char letter;
	public Tile(char letter)
	{
		this.letter = letter;
	}
	public char getLetter()
	{
		return letter;
	}
	
	@Override
	public boolean equals(Object o) // Required for making comparisons.
	{
		return (getClass() == o.getClass() && getLetter() == ((Tile)(o)).getLetter());
	}
	
	@Override
	public String toString()
	{
		char[] temp = {letter};
		String str = new String(temp);
		return str;
	}
}
