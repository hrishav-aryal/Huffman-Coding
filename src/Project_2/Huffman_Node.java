package Project_2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Queue;


public class Huffman_Node {

	char ch;
	int freq;
	Huffman_Node left;
	Huffman_Node right;
	
	String outputFile = "H:\\Queens College\\Data Structures\\project 2\\FrequencyTable.txt";
	IO read = new IO();
	
	public Huffman_Node() {
		
	}
	
	public Huffman_Node(char c, int cc) {
		ch = c;
		freq = cc;
	}
	
	public Huffman_Node(char c, int fr, Huffman_Node l, Huffman_Node r) {
		ch = c;
		freq = fr;
		left = l;
		right = r;
	}
	
	public Huffman_Node(Huffman_Node l, Huffman_Node r) {
		left = l;
		right = r;
		freq = l.freq + r.freq;
		ch = 'N';
		
	}
	
	//character counting function
	public HashMap<Character, Integer> char_counter(String input) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for(int i=0; i<input.length(); i++) {
			char ch = input.charAt(i);
			
			if(map.containsKey(ch)) {
				int count  = (Integer) map.get(ch);
				map.put(ch, ++count);
			} else {
				map.put(ch,1);
			}
		}
		
		return map;
	}
	
	//making actual huffman tree
	public Huffman_Node tree_maker(HashMap<Character, Integer> map) {
		
		PriorityQueue prq = new PriorityQueue();
		
		for(char c : map.keySet()) {
			prq.insert(new Huffman_Node(c, map.get(c)));
		}
		
		while(prq.size>1) {
			Huffman_Node n1 = prq.removeMin();
			Huffman_Node n2 = prq.removeMin();
			
			Huffman_Node newNode = new Huffman_Node(n1, n2);
			prq.insert(newNode);
		}
		
		return prq.root.data;
	}
	
	// creating char code table
	public HashMap<Character, String> createCode_table(Huffman_Node node) {
		HashMap<Character, String> map = new HashMap<Character, String>();
		
		createCodeTable(node, "", map);
		
		return map;
		
	}
	
	
	public void createCodeTable(Huffman_Node node, String s, HashMap<Character, String> map) {
		
		if(node.left == null && node.right == null) {
			map.put(node.ch, s);
			return;
		}
		
		createCodeTable(node.left, s + "0", map);
		createCodeTable(node.right, s + "1", map);
		
	}
	
	//encoding to 1's and 0's
	public String encode(HashMap<Character, String> map, String input) {
		StringBuilder sb = new StringBuilder();		
		
		for(int i=0; i<input.length(); i++) {
			sb.append(map.get(input.charAt(i)));
		}
		
		return sb.toString();
	}
	
	//converting string of 1's and 0's to compressed string
	public String encodedToChars(String en) {
		
		String[] grps = convertToSixBits(en);
		HashMap<Integer, Character> map = base64Encode_table();
		
		char c[] = new char[grps.length];
		String encd = "";
		
		for(int i=0; i<grps.length; i++) {
			int val = convertToDec(grps[i]);
			c[i] = getDec_Char(map, val);
			if(i != grps.length-1) {
				encd = encd + c[i];
			}
		
		}
		
		int d = convertToDec(grps[grps.length - 1]);
		char chh = getDec_Char(map, d);
		
		String b = Decode_Huffman.convertToBinary(d);
		int pad = grps[grps.length - 1].length() - b.length();
		while(pad!=0) {
			encd += '=';
			pad--;
		}
		encd += chh;
		
		return encd;
	}
	
	// helper function
	public String[] convertToSixBits(String encoded) {
		int count = 0,newcount = 0, i = 0;
		int length = 0;
		if(encoded.length() % 6 == 0) {
			length = encoded.length()/6;
		} else {
			length = (encoded.length()/6) + 1;
		}
		String groups[] = new String[length];
		String block = "";
		
		while(true) {
			if(encoded.length() - count == 0) break;
			
			if(encoded.length() - count < 6) {
				int pad = 6 - encoded.length() + count;
				block = encoded.substring(count);
				groups[i] = block;
				break;
			}
			
			newcount = count + 6;
			block = encoded.substring(count,newcount);
			count = newcount;
			groups[i++] = block;
			
		}
		
		return groups;
	}
	
	// helper function
	public int convertToDec(String str) {
		int binary = Integer.parseInt(str);
		
		int dec = 0, power = 0;
		
		while(binary != 
				0) {
			dec += ((binary%10) * Math.pow(2, power));
			power++;
			binary = binary / 10;
		}
		
		return dec;
	}
	
	//helper function
	public char getDec_Char(HashMap<Integer, Character> map, int dec) {
		return (char)map.get(dec);
	}
	
	// hard coding the char and corresponding decimal value
	public HashMap<Integer, Character> base64Encode_table() {
		char[] ch = {'A', 'B', 'C','D', 'E', 'F','G', 'H', 'I', 'J', 'K', 'L','M', 'N', 'O'
				,'P', 'Q', 'R','S','T', 'U','V', 'W', 'X','Y', 'Z', 'a','b', 'c', 'd','e', 'f', 'g'
				,'h', 'i', 'j','k', 'l', 'm','n', 'o', 'p','q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
		
		HashMap<Integer, Character> map = new HashMap<Integer, Character>();
		for(int i=0; i<64; i++) {
			map.put(i, ch[i]);
		}
		map.put(64, '=');
		
		 return map;
	}
	
	// creating new char code table from file
	public HashMap<Character, String> newCodeTable(){
		HashMap<Character, String> table = new HashMap<Character, String>();
		read.openReadFile(outputFile);
		String line = read.readLine();
		while(line != null) {
			table.put(line.charAt(0), line.substring(1));
			line = read.readLine();
		}
				
		read.readClose();
		return table;
	}
	
	//helper function to check/debug
	public void printTree(Huffman_Node root) {
	    String[] s = { "Tree(inorder)=" };
	    recursivePrintHeap(root, s);
	    System.out.println(s[0]); // array is passed by reference, string is not
	  }

	  private void recursivePrintHeap(Huffman_Node n, String[] s) {
	    // prints the heap in order
	    if (n == null)
	      return;
	    recursivePrintHeap(n.left, s);
	    s[0] += "(" + n.ch + ":" + n.freq+ ")";
	    recursivePrintHeap(n.right, s);
	  }
	
}
