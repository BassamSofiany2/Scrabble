
package test;

import main.*;

public class PlayerTest
{
	private static Player player;
	private static Pool pool;
	private static Frame frame;
	
	private static void playerTest()
	{
		try
		{
			String name = player.getName();
			if (name != "Test Player")
			{
				throw new Exception("Player getName() Test Failed");
			}
			System.out.println("Player getName() Test Passed");
			player.setName("Changed Name");
			if (player.getName() != "Changed Name")
			{
				throw new Exception("Player setName() Test Failed");
			}
			System.out.println("Player setName() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			int score = player.getScore();
			if (score != 0)
			{
				throw new Exception("Player getScore() Test Failed");
			}
			System.out.println("Player getScore() Test Passed");
			player.changeScore(10);
			if (player.getScore() != 10)
			{
				throw new Exception("Player changeScore() Test Failed");
			}
			System.out.println("Player changeScore() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		boolean flag = false;
		try
		{
			frame = player.getFrame();
			if (frame == null)
			{
				throw new Exception("Player getFrame() Test Failed");
			}
			System.out.println("Player getFrame() Test Passed");
			flag = true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			player.reset();
			frame = player.getFrame();
			if (player.getScore() != 0 || (frame == null && flag))
			{
				throw new Exception("Player reset() Test Failed");
			}
			System.out.println("Player reset() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private static void poolTest()
	{
		boolean flag = false;
		try
		{
			int size = pool.getSize();
			if (size != 100)
			{
				throw new Exception("Pool getSize() Test Failed");
			}
			System.out.println("Pool getSize() Test Passed");
			flag = true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			if (pool.isEmpty())
			{
				throw new Exception("Pool isEmpty() Test Failed");
			}
			System.out.println("Pool isEmpty() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			Tile tile = pool.drawTile();
			if (tile == null || (pool.getSize() != 99 && flag == true))
			{
				throw new Exception("Pool drawTile() Test Failed");
			}
			System.out.println("Pool drawTile() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			int value = Pool.getValue('A');
			if (value != 1)
			{
				throw new Exception("Pool getValue() Test Failed");
			}
			System.out.println("Pool getValue() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private static void frameTest()
	{
		boolean flag = false;
		try
		{
			if (frame.isEmpty() == false)
			{
				throw new Exception("Frame isEmpty() Test Failed");
			}
			System.out.println("Frame isEmpty() Test Passed");
			flag = true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		String letters;
		try
		{
			frame.refill(pool);
			if (frame.isEmpty() && flag)
			{
				throw new Exception("Frame refill() Test Failed");
			}
			System.out.println("Frame refill() Test Passed");
			letters = frame.getLetters();
			if (letters == null)
			{
				throw new Exception("Frame getLetters() Test Failed");
			}
			System.out.println("Frame getLetters() Test Passed");
			if (!frame.hasLetters(letters))
			{
				throw new Exception("Frame hasLetters() Test Failed");
			}
			System.out.println("Frame hasLetters() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			if (frame.getTiles() == null)
			{
				throw new Exception("Frame getTiles() Test Failed");
			}
			System.out.println("Frame getTiles() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		try
		{
			letters = frame.getLetters();
			frame.remove(letters);
			if (!frame.isEmpty() && letters != null && flag)
			{
				throw new Exception("Frame remove() Test Failed");
			}
			System.out.println("Frame remove() Test Passed");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private static void testCreation()
	{
		try
		{
			player = new Player("Test Player");
			pool = new Pool();
			System.out.println("Creation Test Passed.");
		}
		catch(Exception e)
		{
			System.out.println("Creation Test Failed.");
		}
	}
	
	public static void main(String[] args)
	{
		testCreation(); // Objects Creation Test
		
		if (player != null)
		{
			playerTest(); // Player Test
		}
		else
		{
			System.out.println("Player Creation Failed. Unable to run any other test on Player");
		}
		
		if (pool != null)
		{
			poolTest(); // Pool Test
		}
		else
		{
			System.out.println("Pool Creation Failed. Unable to run any other test on Pool");
		}
		
		frame = new Frame();
		if (frame != null && pool != null)
		{
			frameTest(); // Frame Test
		}
		else
		{
			System.out.println("Frame/Pool Creation Failed. Unable to run any other test on Frame");
		}
	}
}
