package PS4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JFileChooser;


public class HuffmanEncode{ 
//	private BufferedReader input;
	
	private BufferedReader input;

	public HuffmanEncode(){
		
	}
	
	
	/*
	 * Generates key value frequency from input string 
	 * 
	 * @return map with character as keys and character frequencies as integers
	 */
	public Map<Character, Integer> GenFrequency() throws IOException{
		Map<Character, Integer> returnMap = new HashMap<Character, Integer>();
		
		BufferedReader input = new BufferedReader(new FileReader("/Users/deloschang/Documents/test.txt"));
		
		// Load the first character
		int nextChar = input.read();
		while (nextChar != -1){
			// read each character one at a time
			char character = (char)nextChar;
			
			// if character is in map already, add +1 
			if (returnMap.containsKey(character)){
				returnMap.put(character, returnMap.get(character) + 1);
			} else { 
				// otherwise, initialize
				returnMap.put(character, 1);
			}
			
			// load next character
			nextChar = input.read();
			
		}
		
		input.close();
		return returnMap;
		
	}
	
	/**
	   * Puts up a fileChooser and gets path name for file to be opened.
	   * Returns an empty string if the user clicks "cancel".
	   * @return path name of the file chosen	
	   */
	public static String getFilePath() {
		//Create a file chooser
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)  {
			File file = fc.getSelectedFile();
			String pathName = file.getAbsolutePath();
			return pathName;
		} else
			return "";
	}
	
	public static void main(String[] args) throws IOException{
//		System.out.println(getFilePath()); // find the filepath
		HuffmanEncode encode = new HuffmanEncode();
		
		System.out.println(encode.GenFrequency().toString());
		
	}
	
}