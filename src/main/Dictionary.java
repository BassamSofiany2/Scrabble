
package main;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary
{
	private static InputStream file = Dictionary.class.getResourceAsStream("/resources/sowpods.txt");
	private HashSet<String> dictionary;
	private Scanner scanner;
	
	public Dictionary()
	{
		dictionary = new HashSet<String>();
		try
		{
			scanner = new Scanner(file);
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				dictionary.add(line);
			}
			scanner.close();
		}
		catch(Exception e)
		{
			System.out.println("Error: File Not Found");
		}
	}
	
	public boolean isWord(String word)
	{
		return dictionary.contains(word);
	}
}
