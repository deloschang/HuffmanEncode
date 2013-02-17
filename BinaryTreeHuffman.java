package PS4;

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
	 * Overriding toString to show the char and character frequency
	 * @see PS4.BinaryTree#toString()
	 */
	public String toString(){
		return getValue() + " freq: " + dataFrequency;
	}


	
}