package PS4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JFileChooser;

/**
 * Huffman encodes text from a file 
 * 
 * @author Delos Chang
 */

public class HuffmanEncode{ 
//	private BufferedReader input;
	
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
	
	public PriorityQueue<BinaryTreeHuffman<Character>> createHeap(Map<Character, Integer> frequencyMap){
		// 1st parameter is initial size
		// 2nd parameter is a comparator
		
		Comparator<BinaryTreeHuffman<Character>> comparator = new TreeComparator();
		
		PriorityQueue<BinaryTreeHuffman<Character>> pq = 
				// initial size?
				new PriorityQueue<BinaryTreeHuffman<Character>>(10, comparator);
		
		Set<Character> keySet = frequencyMap.keySet();
		
		for (Character character : keySet){
			
			// Create singleton trees with char and char frequency
			int charFrequency = frequencyMap.get(character);
			BinaryTreeHuffman<Character> singleton = new BinaryTreeHuffman<Character>(character, charFrequency);
			
			// add singleton to Priority Queue
			pq.add(singleton);
			
		}
		
		return pq;
	}
	
	/**
	   * Puts up a fileChooser and gets path name for file to be opened.
	   * Returns an empty string if the user clicks "cancel".
	   * 
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
		
//		System.out.println(encode.GenFrequency().toString());
		
		// Generate character frequencies
		Map<Character, Integer> frequencyMap = encode.GenFrequency();
		System.out.println(frequencyMap);
		
		// fix to class static // 
		// Create singleton trees and order in heap
		PriorityQueue<BinaryTreeHuffman<Character>> pq = encode.createHeap(frequencyMap);
		
		System.out.println(pq);
		
		
		
		
		
		
	}
	
}