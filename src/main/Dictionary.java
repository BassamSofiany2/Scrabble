
package main;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary
{
//	private static String filename = Box.class.getResource("/resources/sowpods.txt").toString();
	private HashSet<String> dictionary;
	private Scanner scanner;
	
	public Dictionary()
	{
		dictionary = new HashSet<String>();
		File file = new File("sowpods.txt");
		try
		{
			scanner = new Scanner(file);
			System.out.println("Error: File");
			while(scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				dictionary.add(line);
			}
//			scanner.close();
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
