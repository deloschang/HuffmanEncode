package PS4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	public final static String READ_INPUT = "/Users/deloschang/Documents/test.txt";
	public final static String COMPRESSED_PATH_NAME = "/Users/deloschang/Documents/testHuffmanCompress.txt";
	public final static String DECOMPRESSED_PATH_NAME = "/Users/deloschang/Documents/testHuffmanDecompress.txt";
	
	public HuffmanEncode(){
		
	}
	
	
	/*
	 * Generates key value frequency from input string 
	 * 
	 * @param input the input to read characters from 
	 * @return map with character as keys and character frequencies as integers
	 */
	public Map<Character, Integer> GenFrequency(BufferedReader input) throws IOException{
		Map<Character, Integer> returnMap = new HashMap<Character, Integer>();
		
		
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
	
	public PriorityQueue<BinaryTreeHuffman<Character>> buildTree(PriorityQueue<BinaryTreeHuffman<Character>> pq){
		// remove two lowest frequency trees first
		BinaryTreeHuffman<Character> lowFreqOne = pq.poll();
		BinaryTreeHuffman<Character> lowFreqTwo = pq.poll();
		BinaryTreeHuffman<Character> newTree;
		
		// sum of T1 and T2 frequencies
		int sumFreq = lowFreqOne.getDataFrequency() + lowFreqTwo.getDataFrequency();
		
		// create new tree by attaching T1 and T2  to new root
		newTree = new BinaryTreeHuffman<Character>(null, sumFreq, lowFreqOne, lowFreqTwo);
		
		// add back to priority queue
		pq.add(newTree);
		
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
	
	public static void compress(Map<Character, String> codeMap) throws IOException{
		BufferedReader writeInput = new BufferedReader(new FileReader(READ_INPUT));
		BufferedBitWriter bitOutput = new BufferedBitWriter(COMPRESSED_PATH_NAME);
		
		int nextChar = writeInput.read();
		
		while (nextChar != -1){
			// check nextChar against the codeMap
			// retrieve the huffman string
			char character = (char)nextChar;
			String huffmanString = codeMap.get(character);
			
			// iterate through Huffman string and write the bit to the file
			for (int i = 0; i < huffmanString.length(); i++){
				int bit = Character.digit(huffmanString.charAt(i), 10);
				bitOutput.writeBit(bit);
			}
			
			nextChar = writeInput.read();
		}
		
		bitOutput.close();
		writeInput.close();
	}
	
	
	public static void main(String[] args) throws IOException{
//		System.out.println(getFilePath()); // find the filepath
		BufferedReader input = new BufferedReader(new FileReader(READ_INPUT));
		
		HuffmanEncode encode = new HuffmanEncode();
		
//		System.out.println(encode.GenFrequency().toString());
		
		// Generate character frequencies
		Map<Character, Integer> frequencyMap = encode.GenFrequency(input);
		input.close();
		
		System.out.println(frequencyMap);
		
		// fix to class static // 
		// Create singleton trees and order in heap
		PriorityQueue<BinaryTreeHuffman<Character>> pq = encode.createHeap(frequencyMap);
		
		System.out.println(pq);
		
		
		// build the tree until there is 1 single code tree
		// decompose properly later
		PriorityQueue<BinaryTreeHuffman<Character>> pqNew = null;
		while (pq.size() != 1){
			pqNew = encode.buildTree(pq);
		}
		
		System.out.println(pqNew);
		BinaryTreeHuffman<Character> codeTree = pq.peek();
		
		// Map the strings in a single traversal
		Map<Character, String> codeMap = codeTree.mapCodes();
		System.out.println(codeMap);
		
		// Use codeMap to compress the original text
		compress(codeMap);
		
		// Use codeTree to decompress the compressed text
		BufferedBitReader bitInput = new BufferedBitReader(HuffmanEncode.COMPRESSED_PATH_NAME);
		BufferedWriter writeOutput =  new BufferedWriter(new FileWriter(HuffmanEncode.DECOMPRESSED_PATH_NAME));
		
		int firstBit = bitInput.readBit();
		
		while (firstBit != -1){
			codeTree.decodeHuffman(bitInput, writeOutput, firstBit);
			
			firstBit = bitInput.readBit();
		}
		
		bitInput.close();
		writeOutput.close();
		
		
		
		
		
		
		
		
	}
	
}