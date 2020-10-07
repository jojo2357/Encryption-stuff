import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

//Encrypts sourceText on a cipher that the user inputs
class Encryptor{
	public static void main(String[] args) throws IOException{
		File inFile = new File("startedWith.txt");
		FileReader inReader = new FileReader(inFile);
		char[] inData = new char[30000];
		while (inReader.read(inData) != -1){}
		String sourceText = new String(inData);
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