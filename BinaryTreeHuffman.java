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
 * @param <E> generic type
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
	

	/* 
	 *  Return the data frequency
	 */
	public int getDataFrequency() {
		return dataFrequency;
	}

	/* 
	 *  Set the data frequency
	 */
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
	 * 
	 * @param currentCode existing string of bits 
	 * @param codeMap map to use to decode characters
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
	
	
	
	/* 
	 * Overriding toString to show the char and character frequency
	 * @see PS4.BinaryTree#toString()
	 */
	public String toString(){
		return getValue() + " freq: " + dataFrequency;
	}


	
}