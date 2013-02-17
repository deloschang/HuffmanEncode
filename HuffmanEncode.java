package PS4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFileChooser;


public class HuffmanEncode{ 
	private BufferedReader input;
	
	public HuffmanEncode() throws FileNotFoundException{
		BufferedReader input = 
				new BufferedReader(new FileReader("/Users/deloschang/Documents/test.txt"));
	}
	
	/*
	 * Generates key value frequency from input string 
	 */
	public Map<String,Integer> GenFrequency() throws IOException{
		Map<String, Integer> returnMap = new TreeMap<String, Integer>();
		
		// read each character one at a time
		while (input.read() != -1){
			char character = (char)input.read();
			
			
		}
		
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
		System.out.println(encode.GenFrequency());
		
	}
	
}