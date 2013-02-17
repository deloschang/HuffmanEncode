package PS4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Binary Tree that stores two values
 * one for character and one for frequency
 * 
 * @author Delos Chang 
 * @param <E>
 */
public class BinaryTreeHuffman<Character> extends BinaryTree<Character>{
	private int dataFrequency;
	
	/* 
	 *  Constructs singleton tree
	 *  storing two values: character and frequency
	 */
	public BinaryTreeHuffman(Character dataChar, int dataFrequency) {
		super(dataChar);
		this.setDataFrequency(dataFrequency);
	}
	
	/* 
	 *  Constructs tree with right/left branches
	 *  storing two values: character and frequency
	 */
	public BinaryTreeHuffman(Character dataChar, int dataFrequency, 
			BinaryTreeHuffman<Character> leftTree, BinaryTreeHuffman<Character> rightTree) {
		super(dataChar, leftTree, rightTree);
		this.setDataFrequency(dataFrequency);
	}
	

	public int getDataFrequency() {
		return dataFrequency;
	}

	public void setDataFrequency(int dataFrequency) {
		this.dataFrequency = dataFrequency;
	}
	
	/* 
	 * Traverses the tree and maps characters to their Huffman strings
	 * 
	 * @return map with character and codes as key value pairs
	 */
	public Map<Character, String> mapCodes(){
		// get code tree
		// don't traverse an emptyTree
		
		if (getLeft() == null && getRight() == null)
			return null;
		
		Map<Character, String> codeMap = new HashMap<Character, String>();
		
		// traverse tree and populate map at leaf
		traverseTree("", codeMap);
		return codeMap;
		
	}
	
	/*
	 * Recursive helper that traverses tree to Huffman encoded strings
	 */
	public void traverseTree(String currentCode, Map<Character, String> codeMap){
		
		// traverse each leaf for the values
		if (isLeaf()){
			codeMap.put(getValue(), currentCode);
		}
		
		if (getLeft() != null){
			((BinaryTreeHuffman<Character>) getLeft()).traverseTree(currentCode + "0", codeMap);
		}
		
		if (getRight() != null){
			((BinaryTreeHuffman<Character>) getRight()).traverseTree(currentCode + "1", codeMap);
		}
		
		
	}
	
	
	public void decodeHuffman(BufferedBitReader bitInput, BufferedWriter writeOutput, int bit) throws IOException{
		
		// save root first
//		BinaryTreeHuffman<Character> root = this;
		
//		System.out.println(bit);
		if (isLeaf()){
			System.out.println(this.getValue());
			// write the output
			// ?????
			writeOutput.write(getValue());
			return;
			
			
//			int nextBit = bitInput.readBit();
//			System.out.println(nextBit);
//			
//			if (nextBit != -1){
//				// go back to root and continue
//				decodeHuffman(bitInput, writeOutput);
//			} else { 
//				bitInput.close();
//				writeOutput.close();
//				return;
//			}
			
		}

//		int nextBit = bitInput.readBit();
//		System.out.println(nextBit);

//		if (nextBit == 0){
		if (bit == 0 ){
			if (getLeft().isInner()){
				int nextBit = bitInput.readBit();
				((BinaryTreeHuffman<Character>)getLeft()).decodeHuffman(bitInput, writeOutput, nextBit);
			} else { 
				((BinaryTreeHuffman<Character>)getLeft()).decodeHuffman(bitInput, writeOutput, bit);
				
			}
		}

//		if (nextBit == 1){
		if (bit == 1 ){
			if (getRight().isInner()){
				int nextBit = bitInput.readBit();
				((BinaryTreeHuffman<Character>)getRight()).decodeHuffman(bitInput, writeOutput, nextBit);
			} else { 
				((BinaryTreeHuffman<Character>)getRight()).decodeHuffman(bitInput, writeOutput, bit);
			}
		}
	}
	
	/* 
	 * Overriding toString to show the char and character frequency
	 * @see PS4.BinaryTree#toString()
	 */
	public String toString(){
		return getValue() + " freq: " + dataFrequency;
	}


	
}