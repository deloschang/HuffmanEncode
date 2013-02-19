package PS4;

import java.util.Comparator;

/**
 * 
 * Class that compares two trees for character frequency
 * implementing the Comparator interface
 * 
 * @author Delos Chang 
 * @param <E> generic type
 */

public class TreeComparator<E> implements Comparator<BinaryTreeHuffman<E>>{
	
	public int compare(BinaryTreeHuffman<E> treeOne, BinaryTreeHuffman<E> treeTwo){
		if (treeOne.getDataFrequency() < treeTwo.getDataFrequency()){
			return -1; 
		} else if (treeOne.getDataFrequency() > treeTwo.getDataFrequency()){ 
			return 1;
		} else { 
			return 0;
		}
	}
	
}