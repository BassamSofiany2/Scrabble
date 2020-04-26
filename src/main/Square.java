
package main;

// An enum of the 5 types of squares in scrabble.
public enum Square
{
	NORMAL(1, 1),
	DOUBLE_LETTER(2, 1),
	TRIPLE_LETTER(3, 1),
    DOUBLE_WORD(1, 2),
	TRIPLE_WORD(1, 3);

    private final int letterMultiplier;
    private final int wordMultiplier;
    
    private Square(int letterMultiplier, int wordMultiplier)
    {
    	this.letterMultiplier = letterMultiplier;
    	this.wordMultiplier = wordMultiplier;
    }
    
    public int getLetterMultiplier()
    {
    	return this.letterMultiplier;
    }
    
    public int getWordMultiplier()
    {
    	return this.wordMultiplier;
    }
}
