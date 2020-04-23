
package main;

public class Tile
{
	private char letter;
	private boolean blank; // Letter if blank tile
	public Tile(char letter)
	{
		this.letter = letter;
	}
	
	public char getLetter()
	{
		return letter;
	}
	
	public void setBlank(boolean flag)
	{
		blank = flag;
	}
	
	public boolean isBlank()
	{
		return blank;
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
