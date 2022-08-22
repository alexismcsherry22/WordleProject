import java.util.*;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {	
	public static void main(String[] args) throws FileNotFoundException {
		WordleSetUp wordle = new WordleSetUp();
		GuessWord guess = new GuessWord();
		
		loopGuesses(wordle, guess);
	}
	
	static void loopGuesses(WordleSetUp word, GuessWord user) {
		//Change from while to just increment and
		//have a safe guard to stop this and tell user that
		//they have failed the game
		while(++user.numOfGuesses <= 7) {
			if(user.numOfGuesses == 7) {
				System.out.println("Game Over!");
				System.out.println(String.format("The world was %s", word.answer));
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
				break;
			}
				
			String[] answer = word.answer.split("");
			String[] guess = user.guess.split("");
			
//			List<String> answer = new ArrayList<>(Arrays.asList(word.answer.split("")));
//			List<String> guess = new ArrayList<>(Arrays.asList(user.guess.split("")));
				
			for(int i = 0; i < guess.length; i++) {
				for (int j = 0; j < answer.length; j++) {
				//need to make this work properly
					if(guess[i] == answer[i]) {
						System.out.println(String.format("You found a correct letter %s", guess[i]));
					}
					//This for some reason has the correct letter
					else if(Stream.of(guess[i]).anyMatch(answer[j]::equals)&& guess[i] != answer[j]) {
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
}
