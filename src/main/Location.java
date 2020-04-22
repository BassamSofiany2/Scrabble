
package main;

public class Location
{
	private int row;
	private int column;
	private Direction direction;
	
	public Location(int row, int col, Direction dir)
	{
		this.row = row;
		this.column = col;
		this.direction = dir;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public boolean isVertical()
	{
		return (direction == Direction.VERTICAL);
	}
	
	public boolean isHorizontal()
	{
		return !isVertical();
	}
}
