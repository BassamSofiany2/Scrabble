
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
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public void setColumn(int col)
	{
		column = col;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
	
	public void setDirection(Direction dir)
	{
		direction = dir;
	}
	
	public boolean isVertical()
	{
		return (direction == Direction.VERTICAL);
	}
	
	public boolean isHorizontal()
	{
		return !isVertical();
	}
	
	public void setRowColumn(int row, int col)
	{
		this.row = row;
		this.column = col;
	}
}
