import java.io.*;
import java.util.Scanner;

// counts how many of each character appear in a file
class FrequencyCounter{
	public static void main(String args[]) throws IOException{
		int freqs[] = new int[26];
		File inFile = new File("startedWith.txt");
		Scanner reader = new Scanner(inFile);
		String paragraph = "";
		while (reader.hasNextLine()){
			paragraph += reader.nextLine();
		}
		for (int i = 0; i < paragraph.length(); i++)
			if (Character.toLowerCase(paragraph.charAt(i)) >= 'a' && Character.toLowerCase(paragraph.charAt(i)) <= 'z')
				freqs[Character.toLowerCase(paragraph.charAt(i)) - 'a']++;
		for (int i = 0; i < 26; i++)
			System.out.println((char)(i + 'a') + ", " + freqs[i]);
	}
}