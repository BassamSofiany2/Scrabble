
package main;

public class Player
{
	private String name;
	private int score;
	private Frame frame; // The player's current tiles.
	
	public Player(String name)
	{
		this.name = name;
		reset();
	}
	
	public void reset()
	{
		score = 0;
		frame = new Frame();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	// Can be used to increase or decrease a player's score
	public void changeScore(int by)
	{
		score += by;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getName()
	{
		return name;
	}
	
	// Return the actual reference to the frame (NOT A COPY).
	public Frame getFrame()
	{
		return frame;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
