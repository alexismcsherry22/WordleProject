import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.stream.Stream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {	
	public static void main(String[] args) throws IOException, ParseException {
		WordleSetUp wordle = new WordleSetUp();
		GuessWord guess = new GuessWord();
		
		//Start Wordle Game
		loopGuesses(wordle, guess);
	}
	
	//This function is used to loop through each of the users guesses and output the results of their inputs
	static void loopGuesses(WordleSetUp word, GuessWord user) throws IOException, ParseException {
		while(++user.numOfGuesses <= 7) {
			//If the user has used up there 6 guesses, end the game
			if(user.numOfGuesses == 7) {
				System.out.println("Game Over!");
				System.out.println(String.format("The world was %s", word.answer));
				//GetWordDefinition(word.answer);
				break;
			}
			
			//The game begins and allows the user to perform a guess
			System.out.println("Guess the 5 Letter Secret Word");
			Scanner s = new Scanner(System.in);
			System.out.print("Your Guess: ");
			
			//Used to make sure the unputted word matches the casing of the answer
			user.guess = s.next().toLowerCase();	
			//Check that the user has entered more than 5 letters, counts as a guess
			if(user.guess.length() > 5) {
				System.out.println("Must be 5 letters");
			}
			//Check if the user has entered only text,
			//else tell the user it should only be letters
			if(user.guess.matches("[a-z]")) {
				continue;
			} else {
				System.out.println("Guess can only be letters");
			}
				
			//If the user guesses correctly, reward text and end game
			if(user.guess.equals(word.answer)) {
				System.out.println("Your guess is correct!");
				user.correct = true;
				//GetWordDefinition(word.answer);
				break;
			}
				
			//String arrays to check each individual letter in for both the guess and answer
			//so correct outputs can be shown
			String[] answer = word.answer.split("");
			String[] guess = user.guess.split("");
		
			for(int i = 0; i < guess.length; i++) {
				for (int j = 0; j < answer.length; j++) {
					//if the guessed letter matches the answer letter, 
					//and also matches the same index,
					//the user has found a letter in the correct position
					if(guess[i].equals(answer[j]) && i == j) {
						System.out.println(String.format("You found a letter in the correct position: %s", guess[i]));
					}
					//else the user has found letter that matches the answer but is not the same index,
					//tell the user they found a letter but its in the wrong position
					else if(guess[i] != answer[j] && Stream.of(guess[i]).anyMatch(answer[j]::equals) && i != j) {
						System.out.println(String.format("You found a letter in the wrong position: %s", guess[i]));
					}					
				}
			}
			//if the user has not guessed the word, tell them they have not guessed the answer
			if(word.answer != user.guess) {
				System.out.println("Your guess is incorrect!");
				user.correct = false;
			}
		}
	}
	
	//This function is used to get the defintion of the secret word at the end of a game (not working)
	static void GetWordDefinition(String word) throws IOException, ParseException {
		URL url = new URL(String.format("https://api.dictionaryapi.dev/api/v2/entries/en/%s", word));
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		
		//Get response code
		int responsecode = conn.getResponseCode();
		if(responsecode != 200) {
			throw new RuntimeException(String.format("HttpResponseCode: %i", responsecode));
		} else {
			String inline = "";
			Scanner s = new Scanner(url.openStream());
			
			//Write all the JSON data into a string using a scanner
			while(s.hasNext()) {
				inline += s.nextLine();
			}
			s.close();
			
			//Using JSON simple library parse the string into a JSON object
			JSONParser parse = new JSONParser();
			JSONObject data = (JSONObject) parse.parse(inline);
			
			//Get the required object from the above created object
			JSONObject obj = (JSONObject) data.get("meanings");
			obj.get("definitions");
			obj.get("defintion");
			
			//Get the required data using its key
			System.out.println(obj);
		}
		
		
	}
}
