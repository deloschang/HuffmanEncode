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
 * Huffman compresses text from a file 
 * Can also decompress text from the file
 * 
 * @author Delos Chang
 */

public class HuffmanEncode{ 
	// Instance Variable File Names 

	// normal text file
	private final static String READ_INPUT = "/Users/deloschang/Documents/eclipseworkspace/cs10proj/src/PS4/test.txt";
	private final static String COMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
			"cs10proj/src/PS4/test_compress.txt";
	private final static String DECOMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
			"cs10proj/src/PS4/test_decompress.txt";

	// USConstitution compressed
	//	private final static String READ_INPUT = "/Users/deloschang/Documents/eclipseworkspace/cs10proj/src/PS4/USConstitution.txt";
	//	private final static String COMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/USConstitution_compress.txt";
	//	private final static String DECOMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/USConstitution_decompress.txt";

	// War and Peace compressed
	//	private final static String READ_INPUT = "/Users/deloschang/Documents/eclipseworkspace/cs10proj/src/PS4/WarAndPeace.txt";
	//	private final static String COMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/WarAndPeace_compress.txt";
	//	private final static String DECOMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/WarAndPeace_decompress.txt";

	// Empty text file (boundary case)
	//	private final static String READ_INPUT = "/Users/deloschang/Documents/eclipseworkspace/cs10proj/src/PS4/empty.txt";
	//	private final static String COMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/empty_compress.txt";
	//	private final static String DECOMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/empty_decompress.txt";

	// Text file with just spaces (boundary case)
	//	private final static String READ_INPUT = "/Users/deloschang/Documents/eclipseworkspace/cs10proj/src/PS4/emptyspaces.txt";
	//	private final static String COMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/emptyspaces_compress.txt";
	//	private final static String DECOMPRESSED_PATH_NAME = "/Users/deloschang/Documents/eclipseworkspace/" +
	//			"cs10proj/src/PS4/emptyspaces_decompress.txt";


	/*
	 * Generates key value frequency from input string 
	 * 
	 * @param input the input to read characters from 
	 * @return map with character as keys and character frequencies as integers
	 */
	public static Map<Character, Integer> GenFrequency(BufferedReader input) throws IOException{
		Map<Character, Integer> returnMap = new HashMap<Character, Integer>();

		try { 
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
		} catch (IOException ex){
			System.out.println(ex + " exception occurred!");
		} finally { 
			input.close();
		}

		// Return the frequency Map 
		return returnMap;
	}

	/*
	 * Creates priority queue heap from the frequency map
	 * 
	 * @param frequencyMap frequency map of characters
	 * @return priority queue as a heap from the map
	 */
	public static PriorityQueue<BinaryTreeHuffman<Character>> createHeap(Map<Character, Integer> frequencyMap){
		Comparator<BinaryTreeHuffman<Character>> comparator = new TreeComparator();

		PriorityQueue<BinaryTreeHuffman<Character>> pq = 
				new PriorityQueue<BinaryTreeHuffman<Character>>(1, comparator);

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

	/*
	 * Loops the buildTree method until one tree is built recursively
	 * for decomposition
	 * 
	 * @param pq priority queue of heap made from createHeap
	 * @return priority queue single combined tree
	 */
	public static PriorityQueue<BinaryTreeHuffman<Character>> buildTreeHelper(
			PriorityQueue<BinaryTreeHuffman<Character>> pq){
		PriorityQueue<BinaryTreeHuffman<Character>> pqNew = null;

		try { 
			while (pq.size() != 1){
				pqNew = buildTree(pq);
			}
		} catch (NullPointerException ex ){
			System.out.println(ex + " exception occurred");
		} finally { 
			return pqNew;
		}
	}

	/*
	 * Help combine code trees
	 * 
	 * @param pq priority queue of heap made from createHeap
	 * @return priority queue with new combined trees
	 */
	public static PriorityQueue<BinaryTreeHuffman<Character>> buildTree(
			PriorityQueue<BinaryTreeHuffman<Character>> pq){
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
	 * @author Prof. Drysdale
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

	/*
	 * Compress the text
	 * 
	 * @param codeMap Use the codemap to write text into bits
	 */
	public static void compress(Map<Character, String> codeMap) throws IOException{
		BufferedReader writeInput = new BufferedReader(new FileReader(READ_INPUT));
		BufferedBitWriter bitOutput = new BufferedBitWriter(COMPRESSED_PATH_NAME);

		try { 
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
		} catch (IOException ex) {
			System.out.println(ex + " occurred!");
		} finally {

			bitOutput.close();
			writeInput.close();
		}
	}

	/*
	 * Loops each bit and decompresses
	 * for decomposition
	 * 
	 * @param bitInput    input to read from
	 * @param writeOutput output to write to
	 * @param codeTree  codetree to decode with
	 */
	public static void startDecompress(BufferedBitReader bitInput, BufferedWriter writeOutput,
			BinaryTreeHuffman<Character> codeTree) throws IOException {

		try { 
			int firstBit = bitInput.readBit();

			// Decode the Text by looping through each bit
			while (firstBit != -1){
				decodeHuffman(bitInput, writeOutput, firstBit, codeTree);
				firstBit = bitInput.readBit();
			}
		} catch (IOException ex) {
			System.out.println(ex + " exception occurred");
		} finally { 
			bitInput.close();
			writeOutput.close();
		}
	}

	/*
	 * Decompress the text
	 * 
	 * @param bitInput    input to read from
	 * @param writeOutput output to write to
	 * @param bit current bit to look at 
	 * @param codeTree  codetree to decode with
	 */
	public static void decodeHuffman(BufferedBitReader bitInput, BufferedWriter writeOutput, int bit, 
			BinaryTreeHuffman<Character> codeTree) throws IOException{

		if (codeTree.isLeaf()){
			writeOutput.write(codeTree.getValue());
			return;
		}


		try {
			// if 0, go to left tree
			if (bit == 0 ){
				if (codeTree.getLeft().isInner()){
					int nextBit = bitInput.readBit();
					decodeHuffman(bitInput, writeOutput, nextBit, (BinaryTreeHuffman<Character>) codeTree.getLeft());
				} else { 
					decodeHuffman(bitInput, writeOutput, bit, (BinaryTreeHuffman<Character>) codeTree.getLeft());
				}
			}

			// if 1, go to right tree
			if (bit == 1 ){
				if (codeTree.getRight().isInner()){
					int nextBit = bitInput.readBit();
					decodeHuffman(bitInput, writeOutput, nextBit, (BinaryTreeHuffman<Character>) codeTree.getRight());
				} else { 
					decodeHuffman(bitInput, writeOutput, bit, (BinaryTreeHuffman<Character>) codeTree.getRight());
				}
			}
		} catch (IOException ex){
			System.out.println(ex + " exception occurred!");
		} 
	}


	public static void main(String[] args) throws IOException{
		// Helper to find the file path
		//		System.out.println(getFilePath()); // find the filepath

		BufferedReader input = new BufferedReader(new FileReader(READ_INPUT));

		// Generate character frequencies
		Map<Character, Integer> frequencyMap = GenFrequency(input);
		System.out.println("Frequency Map:");
		System.out.println(frequencyMap);
		System.out.println("\n");

		// Create singleton trees and order in heap
		PriorityQueue<BinaryTreeHuffman<Character>> pq = createHeap(frequencyMap);
		System.out.println("Priority Queue as Heap: ");
		System.out.println(pq);
		System.out.println("\n");


		// Combine tree until there is 1 single code tree
		PriorityQueue<BinaryTreeHuffman<Character>> pqNew = buildTreeHelper(pq);

		// Grab the newly created codeTree
		BinaryTreeHuffman<Character> codeTree;
		Map<Character, String> codeMap;
		if (pqNew != null){
			codeTree = pqNew.peek();
			codeMap = codeTree.mapCodes();
		} else { 
			codeMap = null;
			codeTree = null;
		}

		// Map the strings in a single traversal
		System.out.println("Code Map");
		System.out.println(codeMap);
		System.out.println("\n");

		// Compress original text using the code map
		compress(codeMap);

		// Use codeTree to decompress the compressed text
		BufferedBitReader bitInput = new BufferedBitReader(HuffmanEncode.COMPRESSED_PATH_NAME);
		BufferedWriter writeOutput =  new BufferedWriter(new FileWriter(HuffmanEncode.DECOMPRESSED_PATH_NAME));

		// Begin Decompressing
		// inputclose and outputclose in function
		startDecompress(bitInput, writeOutput, codeTree);


	}

}