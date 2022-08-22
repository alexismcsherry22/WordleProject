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
	public List<String> wordsArray = new ArrayList<String>();
	//public ArrayList<String> dictionary = new ArrayList<String>();

	public WordleSetUp() throws FileNotFoundException {
		GenerateWord();
	}
	
	private void GenerateWord() throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		FileReader fileReader;
		File file = new File("D:/Alexis Nology-Forte/Eclipse Workplace/WordleProject/Wordle/src/Word-List.json");
		
		try {
			fileReader = new FileReader(file);
			JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
			
			if(jsonArray != null) {
				for(int i = 0; i < jsonArray.size(); i++) {
					wordsArray.add((String) jsonArray.get(i));
				}
			}
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

		answer = wordsArray.get((int) (Math.random() * wordsArray.size()));

		System.out.println(answer);
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
