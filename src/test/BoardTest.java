
package test;

import main.*;

public class BoardTest
{
	private static Board board;
	private static Frame frame;

	private static void testErrorCode(String word, Location loc, ErrorCode expected)
	{
		try
		{
			ErrorCode got = board.isLegal(frame, loc, word);
			if (got != expected)
			{
				System.out.printf("Expected: %s\nRecieved: %s\nTest Failed\n\n", expected, got);
			}
			else
			{
				System.out.printf("Expected: %s\nRecieved: %s\nTest Passed\n\n", expected, got);
			}
		}
		catch(Exception e)
		{
			System.out.printf("Expected: %s\nRecieved: EXCEPTION\nTest Failed\n\n", expected);
		}
	}
	
	public static void main(String[] args)
	{
		board = new Board();
		frame = new Frame();
		
		frame.add("TESTING");
		Location loc = new Location(0, 0, Direction.VERTICAL);
		
		// Run test for each error code:
		
		// WORD_MUST_START_AT_CENTER
		testErrorCode("TESTING", loc, ErrorCode.WORD_MUST_START_AT_CENTER);
		
		// WORD_TOO_SHORT
		loc.setRowColumn(7, 7);
		testErrorCode("T", loc, ErrorCode.WORD_TOO_SHORT);
		
		// WORD_OUT_OF_BOUND
		testErrorCode("TESTINGTESTING", loc, ErrorCode.WORD_OUT_OF_BOUND);
		
		// UNAVAILABLE_LETTERS_USED
		loc.setRowColumn(7, 7);
		testErrorCode("TESTS", loc, ErrorCode.UNAVAILABLE_LETTERS_USED);
		
		board.placeWord(frame, loc, "TESTING");
		frame.add("TESTING");
		
		// CONFLICTING_WORD
		testErrorCode("TESTS", loc, ErrorCode.CONFLICTING_WORD);
		
		// NO_LETTERS_USED
		testErrorCode("TESTING", loc, ErrorCode.NO_LETTERS_USED);
		
		// DISCONNECTED_WORD
		loc.setRowColumn(0, 0);
		testErrorCode("TEST", loc, ErrorCode.DISCONNECTED_WORD);
		
		
		// Test Print
		board.reset();
		System.out.println("Expecting: Empty Grid");
		board.print();
		loc.setRowColumn(7, 7);
		loc.setDirection(Direction.HORIZONTAL);
		board.placeWord(frame, loc, "TESTING");
		System.out.println("\n\nExpecting: Grid with 'TESTING' at center in the horizontal direction");
		board.print();
	}
}
