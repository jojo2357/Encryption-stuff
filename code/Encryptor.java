import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

//Encrypts sourceText on a cipher that the user inputs
class Encryptor{
	private static final String sourceText = "About a year ago, I developed my very own computer game based off the mobile games Pocket Planes (2012, Nimblebit llc.) and Pocket Trains (2013, Nimblebit llc.). I used QB64, a variant of Quick Basic, to program the game on my laptop. In making my game, I wanted the game to save the user’s progress so that when they reopened the game, they could pick up where they left off. Like many other languages, QB64 allows the program to detect when the red X has been clicked to close the program. When this happens, the game saves all of the user’s data to a text file before closing the window. When the user reopens the game, the program retrieves data from the file and play continues.";

	public static void main(String[] args) throws IOException{
		HashMap<Character, Character> cipher = new HashMap<Character, Character>();
		Scanner kb = new Scanner(System.in);
		for (char first = 'a'; first <= 'z'; first++){
			for (char lookFor = 'a'; lookFor <= 'z'; lookFor++)
				if (!cipher.containsValue(lookFor))
					System.out.print(lookFor + " ");
			System.out.println();
			System.out.println(first + ": ");
			cipher.put(new Character(first), new Character(kb.nextLine().charAt(0)));
		}
		kb.close();
		StringBuilder outStr = new StringBuilder("");
		for (char ch : sourceText.toCharArray())
			if (cipher.containsKey(Character.toLowerCase(ch)))
				outStr.append(cipher.get(Character.toLowerCase(ch)));
			else
				outStr.append(ch);
		File outFile = new File("cipherCreated.txt");
		FileWriter outWriter = new FileWriter(outFile);
		outWriter.append(outStr.toString());
		outWriter.close();
	}
}