package Project_2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		
		Huffman_Node huff = new Huffman_Node();
		Decode_Huffman dhuff = new Decode_Huffman();
		
		
		String inputFile = "H:\\Queens College\\Data Structures\\project 2\\input.txt";
		String compressedOutputFile = "H:\\Queens College\\Data Structures\\project 2\\compressedOutput.txt";
		String decomOutputFile = "H:\\Queens College\\Data Structures\\project 2\\DecompressedOutput.txt";
		String freqTableOutput = "H:\\Queens College\\Data Structures\\project 2\\FrequencyTable.txt";
		
		IO read = new IO();
		
		Scanner sc = new Scanner(System.in);
		String choice = null;
		System.out.println("This program compresses/encodes the data from input file to an output file.");
		do {
			
			System.out.println("Choose Command (Enter no. associated with commands): "
					+ "\n1 - Encode/Compress input from a file"
					+ "\n2 - Decode the compressed data"
					+ "");
			
			int command = sc.nextInt();
			
			switch(command) {
			
			case 1:
				
				IO writeCompData = new IO(compressedOutputFile);
				IO writeFreqtable = new IO(freqTableOutput);
				
				//Reading plain text from input file
				String plainInput = read.readFile(inputFile);
				
				//creating char and frequency table
				HashMap<Character, Integer> char_freq = huff.char_counter(plainInput);
				
				//creating huffman tree
				Huffman_Node tree_root = huff.tree_maker(char_freq);
				
				//creating char and char code table
				HashMap<Character, String> code_table = huff.createCode_table(tree_root);
				
				//writing char code table into new file
				for(char c: code_table.keySet()) {
					writeFreqtable.writeFile( c + code_table.get(c));
				}
				
				//creating encoded string from input (to 1's and 0's)
				String encoded = huff.encode(code_table, plainInput);
				System.out.println(encoded);
				
				//converting 1's and 0's to compressed string
				String encodedString = huff.encodedToChars(encoded);
				//System.out.println(encodedString);
				
				writeCompData.writeFile(encodedString);
				
				writeCompData.close();
				writeFreqtable.close();
				
				break;
			
			case 2:
				
				IO writeToDecompFile = new IO(decomOutputFile);
				
				//reading compressed input 
				String compressedinput = read.readFile(compressedOutputFile);
				
				// converting the compressed input to 1's and 0's again
				String encodedAgain = dhuff.toEncoded(compressedinput);
				System.out.println(encodedAgain);
				
				// creating the char code table again from file
				HashMap<Character, String> code_table_new = huff.newCodeTable();
				
				// Decompressing the compressed data
				String original = dhuff.toOriginal(code_table_new, encodedAgain);
				
				writeToDecompFile.writeFile(original);
				
				writeToDecompFile.close();
				
				break;
				
			default: 
				System.out.println("Invalid Command!");
				break;
			
			}
			
			System.out.println("Tasks Successfully Completed.");
			System.out.println("Enter again(y/n): ");
			choice = sc.next();
			
		} while(choice.equalsIgnoreCase("y"));
		

		System.out.println("Thank you.\n\nProgram Terminated!!!");
	}

}
