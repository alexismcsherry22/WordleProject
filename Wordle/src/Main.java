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
		
		loopGuesses(wordle, guess);
	}
	
	static void loopGuesses(WordleSetUp word, GuessWord user) throws IOException, ParseException {
		while(++user.numOfGuesses <= 7) {
			if(user.numOfGuesses == 7) {
				System.out.println("Game Over!");
				System.out.println(String.format("The world was %s", word.answer));
				//GetWordDefinition(word.answer);
				break;
			}
			
			System.out.println("Guess the 5 Letter Secret Word");
			Scanner s = new Scanner(System.in);
			System.out.print("Your Guess: ");
			
			user.guess = s.next().toLowerCase();		
			if(user.guess.length() > 5) {
				System.out.println("Must be 5 letters");
			}
				
			if(user.guess.equals(word.answer)) {
				System.out.println("Your guess is correct!");
				user.correct = true;
				//GetWordDefinition(word.answer);
				break;
			}
				
			String[] answer = word.answer.split("");
			String[] guess = user.guess.split("");
		
			for(int i = 0; i < guess.length; i++) {
				for (int j = 0; j < answer.length; j++) {
					if(guess[i].equals(answer[j]) && i == j) {
						System.out.println(String.format("You found a correct letter %s", guess[i]));
					}
					if(guess[i] != answer[j] && Stream.of(guess[i]).anyMatch(answer[j]::equals) && i != j) {
						System.out.println(String.format("This letter is in the wrong position %s", guess[i]));
					}					
				}
			}
			if(word.answer != user.guess) {
				System.out.println("Your guess is not correct!");
				user.correct = false;
			}
		}
	}
	
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
