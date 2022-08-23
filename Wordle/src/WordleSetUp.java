import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WordleSetUp {
	public String answer;
	public LinkedHashMap<Character, Integer> letters = new LinkedHashMap<>();
	//Stores the words that the player will have a chance of guessing
	public List<String> wordsArray = new ArrayList<String>();
	//public ArrayList<String> dictionary = new ArrayList<String>();

	//Constructor that when used automatically starts the Generate Word function
	public WordleSetUp() throws FileNotFoundException {
		GenerateWord();
	}
	
	//This function is used to get and assign the secret word for the user to guess from the json file
	private void GenerateWord() throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		FileReader fileReader;
		//Change the file path to suit your system path to the Word-List.json file
		File file = new File("D:/Alexis Nology-Forte/Eclipse Workplace/WordleProject/Wordle/src/Word-List.json");
		
		try {
			fileReader = new FileReader(file);
			JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
			
			//If data has been found and exists in the JSONArray, add to the wordsArray
			if(jsonArray != null) {
				for(int i = 0; i < jsonArray.size(); i++) {
					wordsArray.add((String) jsonArray.get(i));
				}
			}
			//Catch any exceptions for when the file is not found or if there is no data
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Generate a random word from the string and use that word as the answer
		answer = wordsArray.get((int) (Math.random() * wordsArray.size()));

		//temporary checker
		System.out.println(answer);
		
		//This loop is used to correctly place each letter in the right position and to make sure that 
		//the inputted guesses output correct feedback
		int k;
		for (int i = 0; i < 5; i++) {
			k = 1;
			for(int j = 0; j < 5; j++) {
				if (i != j && answer.charAt(i) == answer.charAt(j)) {
					k++;
				}
				letters.put(answer.charAt(i), k);
			}
		}
		
	}
	
	//Make a way to use an api to fetch for 
	//the definition of an answer regardless
	//of the user guessing correctly or not

}
