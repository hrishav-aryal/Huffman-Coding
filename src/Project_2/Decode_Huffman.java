package Project_2;

import java.util.HashMap;

public class Decode_Huffman {

	Huffman_Node hn = new Huffman_Node();
	
	public Decode_Huffman() {
		
	}
	
	//converting compressed string to 1's and 0's
	public String toEncoded(String str) {
		
		HashMap<Integer, Character> map = hn.base64Encode_table();
		HashMap<Character, Integer> newMap = new HashMap<Character, Integer>();
		String encodedStr = "";
		
		for(int i: map.keySet()) {
			newMap.put(map.get(i), i);
		}
				
		for(int i=0; i<str.length(); i++) {
			String bin = "";
			
			if(str.charAt(i) == '=') {
				bin += '0';
			}else {
				int dec = newMap.get(str.charAt(i));
				bin = convertToBinary(dec);
				
				if(i!=str.length()-1) {
					int padd = 6 - bin.length();
					if(padd!=0) {
						for(int j=0; j<padd; j++)
							bin = '0' + bin;
					}
				}
			}
			
			encodedStr += bin;
		}
		
		return encodedStr;
	}
	
	//helper funtion 
	public static String convertToBinary(int x) {
		String binary = "";
		while(x>0) {
			binary = (x%2) + binary;
			x = x/2;
		}
		
		return binary;
	}
	
	// converting the encoded text to original text
	public String toOriginal(HashMap<Character, String> map, String encoded) {
		
		HashMap<String, Character> imap = invertCodeTable(map);
		
		
		String block = "";
		String origText = "";
		
		for(int i=0; i<encoded.length(); i++) {
			block += encoded.charAt(i);
			
			if(imap.containsKey(block)) {
				origText += imap.get(block);
				block = "";
			}
		}
		
		return origText;
	}
	
	//inverting the char code table to decompress
	public HashMap<String, Character> invertCodeTable(HashMap<Character, String> map) {
		HashMap<String, Character> invertedMap = new HashMap<String, Character>();
		
		for(char ch: map.keySet()) {
			invertedMap.put(map.get(ch), ch);
		}
		
		return invertedMap;
	}
	
}
